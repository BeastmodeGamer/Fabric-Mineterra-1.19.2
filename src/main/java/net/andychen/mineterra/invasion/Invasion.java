package net.andychen.mineterra.invasion;

import com.google.common.collect.Sets;
import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.entity.ModEntities;
import net.andychen.mineterra.entity.custom.InvaderEntity;
import net.andychen.mineterra.sounds.ModSounds;
import net.andychen.mineterra.util.access.ServerWorldMixinAccess;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.*;
import net.minecraft.entity.SpawnRestriction.Location;
import net.minecraft.entity.boss.BossBar.Color;
import net.minecraft.entity.boss.BossBar.Style;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public class Invasion {
    private static final Text EVENT_TEXT = Text.translatable("event.mineterra.invasion");
    private static final Text VICTORY_SUFFIX_TEXT = Text.translatable("event.mineterra.invasion.victory");
    private static final Text VICTORY_TITLE;

    static {
        VICTORY_TITLE = EVENT_TEXT.copy().append(" - ").append(VICTORY_SUFFIX_TEXT);
    }

    private final Set<InvaderEntity> currentInvaders = Sets.newHashSet();
    private final Set<UUID> heroesOfTheVillage = Sets.newHashSet();
    private final ServerWorld world;
    private final int id;
    private final ServerBossBar bar;
    private final Random random;
    private final int totalInvaderCount;
    private InvaderEntity currentCaptain = null;
    private long ticksActive;
    private BlockPos center;
    private boolean started;
    private int totalHealth;
    private boolean active;
    private int invadersSpawned;
    private int postInvasionTicks;
    private int preInvasionTicks;
    private Invasion.Status status;
    private int finishCooldown;
    private Optional<BlockPos> preCalculatedRavagerSpawnLocation;

    public Invasion(int id, ServerWorld world, BlockPos pos) {
        this.bar = new ServerBossBar(EVENT_TEXT, Color.RED, Style.NOTCHED_12);
        this.random = Random.create();
        this.preCalculatedRavagerSpawnLocation = Optional.empty();
        this.id = id;
        this.world = world;
        this.totalHealth = this.getTotalInvaders();
        this.active = true;
        this.preInvasionTicks = 300;
        this.bar.setPercent(0.0F);
        this.center = pos;
        this.totalInvaderCount = this.getTotalInvaders();
        this.status = Invasion.Status.ONGOING;
    }

    public Invasion(ServerWorld world, NbtCompound nbt) {
        this.bar = new ServerBossBar(EVENT_TEXT, Color.RED, Style.NOTCHED_12);
        this.random = Random.create();
        this.preCalculatedRavagerSpawnLocation = Optional.empty();
        this.world = world;
        this.id = nbt.getInt("Id");
        this.started = nbt.getBoolean("Started");
        this.active = nbt.getBoolean("Active");
        this.ticksActive = nbt.getLong("TicksActive");
        this.invadersSpawned = nbt.getInt("InvadersSpawned");
        this.preInvasionTicks = nbt.getInt("PreInvasionTicks");
        this.postInvasionTicks = nbt.getInt("PostInvasionTicks");
        this.totalHealth = nbt.getInt("TotalHealth");
        this.center = new BlockPos(nbt.getInt("CX"), nbt.getInt("CY"), nbt.getInt("CZ"));
        this.totalInvaderCount = nbt.getInt("TotalNumInvaders");
        this.status = Invasion.Status.fromName(nbt.getString("Status"));
        this.heroesOfTheVillage.clear();
        if (nbt.contains("HeroesOfTheVillage", 9)) {
            NbtList nbtList = nbt.getList("HeroesOfTheVillage", 11);

            for (int i = 0; i < nbtList.size(); ++i) {
                this.heroesOfTheVillage.add(NbtHelper.toUuid(nbtList.get(i)));
            }
        }

    }

    public static ItemStack getBanner() {
        ItemStack itemStack = new ItemStack(Items.YELLOW_BANNER);
        NbtCompound nbtCompound = new NbtCompound();
        NbtList nbtList = (new BannerPattern.Patterns()).add(BannerPatterns.CREEPER, DyeColor.RED).add(BannerPatterns.STRIPE_BOTTOM, DyeColor.BLACK).add(BannerPatterns.TRIANGLES_BOTTOM, DyeColor.WHITE)
                .add(BannerPatterns.BORDER, DyeColor.LIME).add(BannerPatterns.HALF_HORIZONTAL, DyeColor.GREEN).add(BannerPatterns.SKULL, DyeColor.GREEN)
                .add(BannerPatterns.RHOMBUS, DyeColor.LIME).add(BannerPatterns.HALF_HORIZONTAL, DyeColor.LIME).add(BannerPatterns.BORDER, DyeColor.BLACK).toNbt();
        nbtCompound.put("Patterns", nbtList);
        BlockItem.setBlockEntityNbt(itemStack, BlockEntityType.BANNER, nbtCompound);
        itemStack.addHideFlag(ItemStack.TooltipSection.ADDITIONAL);
        itemStack.setCustomName(Text.translatable("Goblin Warbanner").formatted(Formatting.GOLD));
        return itemStack;
    }

    public boolean isFinished() {
        return this.hasWon();
    }

    public boolean isPreInvasion() {
        return this.hasSpawned() && this.getInvaderCount() == 0 && this.preInvasionTicks > 0;
    }

    public boolean hasSpawned() {
        return this.invadersSpawned > 0;
    }

    public boolean hasStopped() {
        return this.status == Invasion.Status.STOPPED;
    }

    public boolean hasWon() {
        return this.status == Invasion.Status.VICTORY;
    }

    public int getTotalHealth() {
        return this.totalHealth;
    }

    public int getTotalInvaders() {
        int i = 80;
        for (ServerPlayerEntity player : this.world.getPlayers()) {
            i += 40;
        }
        return i;
    }

    public World getWorld() {
        return this.world;
    }

    public boolean hasStarted() {
        return this.started;
    }

    private Predicate<ServerPlayerEntity> isInInvasionDistance() {
        return (player) -> {
            BlockPos blockPos = player.getBlockPos();
            return player.isAlive() && ((ServerWorldMixinAccess) this.world).getInvasionAt(blockPos) == this;
        };
    }

    private void updateBarToPlayers() {
        Set<ServerPlayerEntity> set = Sets.newHashSet(this.bar.getPlayers());
        List<ServerPlayerEntity> list = this.world.getPlayers(this.isInInvasionDistance());
        Iterator var3 = list.iterator();

        ServerPlayerEntity serverPlayerEntity;
        while (var3.hasNext()) {
            serverPlayerEntity = (ServerPlayerEntity) var3.next();
            if (!set.contains(serverPlayerEntity)) {
                this.bar.addPlayer(serverPlayerEntity);
            }
        }

        var3 = set.iterator();

        while (var3.hasNext()) {
            serverPlayerEntity = (ServerPlayerEntity) var3.next();
            if (!list.contains(serverPlayerEntity)) {
                this.bar.removePlayer(serverPlayerEntity);
            }
        }

    }

    public void start(PlayerEntity player) {
        this.sendEventMessage("A goblin army is approaching!");
    }


    public void invalidate() {
        this.active = false;
        this.bar.clearPlayers();
        this.status = Invasion.Status.STOPPED;
        this.stopSound(SoundCategory.MUSIC, null);
    }

    public void tick() {
        if (!this.hasStopped()) {
            if (this.status == Invasion.Status.ONGOING) {
                boolean bl = this.active;
                this.active = this.world.isChunkLoaded(this.center);
                if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
                    this.invalidate();
                    return;
                }

                if (bl != this.active) {
                    this.bar.setVisible(this.active);
                }

                if (!this.active) {
                    return;
                }

                if (this.ticksActive == 1L || (this.ticksActive - 80) % 2030 == 0L) {
                    SoundEvent sound = this.ticksActive == 1L ? ModSounds.MUSIC_GOBLIN_ARMY : ModSounds.MUSIC_GOBLIN_ARMY_LOOP;
                    this.stopSound(SoundCategory.MUSIC, null);
                    for (ServerPlayerEntity serverPlayerEntity : this.bar.getPlayers()) {
                        this.world.playSound(null, serverPlayerEntity.getBlockPos(), sound, SoundCategory.MUSIC, 1.0f, 1.0f);
                    }
                }

                ++this.ticksActive;
                if (this.ticksActive >= 48000L) {
                    this.invalidate();
                    return;
                }

                int i = this.getInvaderCount(); // Get current enemies alive
                boolean bl2;
                if (this.invadersSpawned == 0 && this.shouldSpawnMoreInvaders()) {
                    if (this.preInvasionTicks > 0) {
                        bl2 = this.preCalculatedRavagerSpawnLocation.isPresent();
                        boolean bl3 = !bl2 && this.preInvasionTicks % 5 == 0;
                        if (bl2 && !this.world.shouldTickEntity((BlockPos) this.preCalculatedRavagerSpawnLocation.get())) {
                            bl3 = true;
                        }

                        if (bl3) {
                            int j = 0;
                            if (this.preInvasionTicks < 100) {
                                j = 1;
                            } else if (this.preInvasionTicks < 40) {
                                j = 2;
                            }

                            this.preCalculatedRavagerSpawnLocation = this.preCalculateRavagerSpawnLocation(j);
                        }

                        if (this.preInvasionTicks == 300 || this.preInvasionTicks % 20 == 0) {
                            this.updateBarToPlayers();
                        }

                        if (this.preInvasionTicks == 0) {
                            this.sendEventMessage("A goblin army has arrived!");
                        }

                        --this.preInvasionTicks;
                        this.bar.setPercent(MathHelper.clamp((float) (float) (300 - this.preInvasionTicks) / 300.0F, 0.0F, 1.0F));
                    }
                }


                if (this.ticksActive % 20L == 0L) {
                    this.updateBarToPlayers();
                    this.removeObsoleteInvaders();
                    if (i > 0) {
                        if (this.totalHealth <= 30) {
                            this.bar.setName(EVENT_TEXT.copy().append(" - ").append(Text.translatable("event.mineterra.invasion.invaders_remaining", new Object[]{this.totalHealth})));
                        } else {
                            this.bar.setName(EVENT_TEXT);
                        }
                    } else {
                        this.bar.setName(EVENT_TEXT);
                    }
                }

                bl2 = false;
                int k = 0;

                while (this.canSpawnInvaders()) {
                    BlockPos blockPos = this.preCalculatedRavagerSpawnLocation.isPresent() ? (BlockPos) this.preCalculatedRavagerSpawnLocation.get() : this.getRavagerSpawnLocation(k, 20);
                    if (blockPos != null) {
                        this.started = true;
                        this.spawnNextInvader(blockPos);
                        if (!bl2) {
                            this.playHorn(blockPos);
                            bl2 = true;
                        }
                    } else {
                        ++k;
                    }

                    if (k > 3) {
                        this.invalidate();
                        break;
                    }
                }

                if (this.hasStarted() && !this.shouldSpawnMoreInvaders() && i == 0) {
                    if (this.postInvasionTicks < 40) {
                        ++this.postInvasionTicks;
                    } else {
                        this.status = Invasion.Status.VICTORY;
                        Iterator var12 = this.heroesOfTheVillage.iterator();

                        while (var12.hasNext()) {
                            UUID uUID = (UUID) var12.next();
                            Entity entity = this.world.getEntity(uUID);
                            if (entity instanceof LivingEntity && !entity.isSpectator()) {
                                LivingEntity livingEntity = (LivingEntity) entity;
                                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HERO_OF_THE_VILLAGE, 48000, 1, false, false, true));
                                if (livingEntity instanceof ServerPlayerEntity) {
                                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) livingEntity;
                                    serverPlayerEntity.incrementStat(Stats.RAID_WIN);
                                    Criteria.HERO_OF_THE_VILLAGE.trigger(serverPlayerEntity);
                                }
                            }
                        }
                    }
                }

                this.markDirty();
            } else if (this.isFinished()) {
                ++this.finishCooldown;
                if (this.finishCooldown >= 600) {
                    this.invalidate();
                    return;
                }

                if (this.finishCooldown == 1) {
                    this.sendEventMessage("A goblin army has been defeated!");
                    for (ServerPlayerEntity serverPlayerEntity : this.bar.getPlayers()) {
                        this.world.playSound(null, serverPlayerEntity.getBlockPos(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.MUSIC, 1.0f, 1.0f);
                        this.world.playSound(null, serverPlayerEntity.getBlockPos(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MUSIC, 1.0f, 1.0f);
                    }
                    this.stopSound(SoundCategory.MUSIC, new Identifier(MineTerra.MOD_ID, "music.goblin_army"));
                    this.stopSound(SoundCategory.MUSIC, new Identifier(MineTerra.MOD_ID, "music.goblin_army_loop"));
                }

                if (this.finishCooldown % 20 == 0 || this.finishCooldown == 1) {
                    this.updateBarToPlayers();
                    this.bar.setVisible(true);
                    if (this.hasWon()) {
                        this.bar.setPercent(0.0F);
                        this.bar.setName(VICTORY_TITLE);
                    }
                }
            }
        }
    }

    private void sendEventMessage(String message) {
        for (ServerPlayerEntity serverPlayerEntity : this.getWorld().getServer().getPlayerManager().getPlayerList()) {
            serverPlayerEntity.sendMessage(Text.translatable(message).formatted(Formatting.LIGHT_PURPLE));
        }
    }

    private void stopSound(@Nullable SoundCategory category, @Nullable Identifier sound) {
        for (ServerPlayerEntity serverPlayerEntity : this.getWorld().getServer().getPlayerManager().getPlayerList()) {
            StopSoundS2CPacket stopSoundS2CPacket = new StopSoundS2CPacket(sound, category);
            serverPlayerEntity.networkHandler.sendPacket(stopSoundS2CPacket);
        }
    }

    private Optional<BlockPos> preCalculateRavagerSpawnLocation(int proximity) {
        for (int i = 0; i < 3; ++i) {
            BlockPos blockPos = this.getRavagerSpawnLocation(proximity, 1);
            if (blockPos != null) {
                return Optional.of(blockPos);
            }
        }

        return Optional.empty();
    }

    private boolean shouldSpawnMoreInvaders() {
        return this.getInvadersSpawned() != this.totalInvaderCount;
    }

    public int getInvadersSpawned() {
        return this.invadersSpawned;
    }

    private void removeObsoleteInvaders() {
        Iterator<InvaderEntity> iterator = this.currentInvaders.iterator();
        HashSet set = Sets.newHashSet();

        label54:
        while (iterator.hasNext()) {
            if (!iterator.hasNext()) {
                continue label54;
            }

            InvaderEntity invaderEntity = (InvaderEntity) iterator.next();
            BlockPos blockPos = invaderEntity.getBlockPos();
            if (!invaderEntity.isRemoved() && invaderEntity.world.getRegistryKey() == this.world.getRegistryKey() && !(this.center.getSquaredDistance(blockPos) >= 12544.0D)) {
                if (invaderEntity.age > 600) {
                    if (this.world.getEntity(invaderEntity.getUuid()) == null) {
                        set.add(invaderEntity);
                    }

                    if (!this.world.isNearOccupiedPointOfInterest(blockPos) && invaderEntity.getDespawnCounter() > 2400) {
                        invaderEntity.setOutOfInvasionCounter(invaderEntity.getOutOfInvasionCounter() + 1);
                    }

                    if (invaderEntity.getOutOfInvasionCounter() >= 30) {
                        set.add(invaderEntity);
                    }
                }
            } else {
                set.add(invaderEntity);
            }
        }

        Iterator var7 = set.iterator();

        while (var7.hasNext()) {
            InvaderEntity invaderEntity2 = (InvaderEntity) var7.next();
            this.removeFromInvasion(invaderEntity2, true);
        }

    }

    private void playHorn(BlockPos pos) {
        float f = 13.0F;
        Collection<ServerPlayerEntity> collection = this.bar.getPlayers();
        long l = this.random.nextLong();
        Iterator var7 = this.world.getPlayers().iterator();

        while (true) {
            ServerPlayerEntity serverPlayerEntity;
            double d;
            double e;
            double g;
            do {
                if (!var7.hasNext()) {
                    return;
                }

                serverPlayerEntity = (ServerPlayerEntity) var7.next();
                Vec3d vec3d = serverPlayerEntity.getPos();
                Vec3d vec3d2 = Vec3d.ofCenter(pos);
                d = Math.sqrt((vec3d2.x - vec3d.x) * (vec3d2.x - vec3d.x) + (vec3d2.z - vec3d.z) * (vec3d2.z - vec3d.z));
                e = vec3d.x + 13.0D / d * (vec3d2.x - vec3d.x);
                g = vec3d.z + 13.0D / d * (vec3d2.z - vec3d.z);
            } while (!(d <= 64.0D) && !collection.contains(serverPlayerEntity));

            serverPlayerEntity.networkHandler.sendPacket(new PlaySoundS2CPacket(SoundEvents.EVENT_RAID_HORN, SoundCategory.NEUTRAL, e, serverPlayerEntity.getY(), g, 64.0F, 1.0F, l));
        }
    }

    private void spawnNextInvader(BlockPos pos) {
        // int i = this.invadersSpawned + 1;
        Invasion.Member[] var6 = Invasion.Member.VALUES;
        int var7 = var6.length;

        int idx = 0;
        for (double r = Math.random() * 1; idx < var7 - 1; ++idx) {
            r -= var6[idx].spawnWeight;
            if (r <= 0.0) break;
        }

        Invasion.Member member = var6[idx];
        InvaderEntity invaderEntity = (InvaderEntity) member.type.create(this.world);
        if (this.currentCaptain == null && invaderEntity.canLead()) {
            invaderEntity.setPatrolLeader(true);
            this.setCaptain(invaderEntity);
        }

        this.addInvader(invaderEntity, pos, false, false);

        this.preCalculatedRavagerSpawnLocation = Optional.empty();
        ++this.invadersSpawned;
        this.updateBar();
        this.markDirty();
    }

    public void addInvader(InvaderEntity invader, @Nullable BlockPos pos, boolean existing, boolean countHealth) {
        boolean bl = this.addToInvasion(invader, countHealth);
        if (bl) {
            invader.setInvasion(this);
            invader.setAbleToJoinInvasion(true);
            invader.setOutOfInvasionCounter(0);
            if (!existing && pos != null) {
                invader.setPosition((double) pos.getX() + 0.5D, (double) pos.getY() + 1.0D, (double) pos.getZ() + 0.5D);
                invader.initialize(this.world, this.world.getLocalDifficulty(pos), SpawnReason.EVENT, (EntityData) null, (NbtCompound) null);
                invader.setOnGround(true);
                this.world.spawnEntityAndPassengers(invader);
            }
        }
    }

    public void updateBar() {
        this.bar.setPercent(MathHelper.clamp((float) this.totalHealth / this.totalInvaderCount, 0.0F, 1.0F));
    }

    private boolean canSpawnInvaders() {
        return this.preInvasionTicks == 0 && (this.invadersSpawned < this.totalInvaderCount) && this.getInvaderCount() < 30;
    }

    public int getInvaderCount() {
        return this.currentInvaders.size();
    }

    public void removeFromInvasion(InvaderEntity entity, boolean countHealth) {
        Set<InvaderEntity> set = this.currentInvaders;
        if (set != null) {
            boolean bl = set.remove(entity);
            if (bl) {
                if (countHealth) {
                    this.totalHealth -= 1;
                }

                entity.setInvasion((Invasion) null);
                this.updateBar();
                this.markDirty();
            }
        }

    }

    private void markDirty() {
        ((ServerWorldMixinAccess) this.world).getInvasionManager().markDirty();
    }

    @Nullable
    private BlockPos getRavagerSpawnLocation(int proximity, int tries) {
        int i = proximity == 0 ? 2 : 2 - proximity;
        Mutable mutable = new Mutable();

        for (int j = 0; j < tries; ++j) {
            float f = this.world.random.nextFloat() * 6.2831855F;
            int k = this.center.getX() + MathHelper.floor(MathHelper.cos(f) * 32.0F * (float) i) + this.world.random.nextInt(5);
            int l = this.center.getZ() + MathHelper.floor(MathHelper.sin(f) * 32.0F * (float) i) + this.world.random.nextInt(5);
            int m = this.world.getTopY(Type.WORLD_SURFACE, k, l);
            mutable.set(k, m, l);
            if (!this.world.isNearOccupiedPointOfInterest(mutable) || proximity >= 2) {
                if (this.world.isRegionLoaded(mutable.getX() - 10, mutable.getZ() - 10, mutable.getX() + 10, mutable.getZ() + 10) && this.world.shouldTickEntity(mutable) && (SpawnHelper.canSpawn(Location.ON_GROUND, this.world, mutable, EntityType.RAVAGER) || this.world.getBlockState(mutable.down()).isOf(Blocks.SNOW) && this.world.getBlockState(mutable).isAir())) {
                    return mutable;
                }
            }
        }

        return null;
    }

    public boolean addToInvasion(InvaderEntity entity, boolean countHealth) {
        Set<InvaderEntity> set = this.currentInvaders;
        InvaderEntity invaderEntity = null;
        Iterator var2 = this.currentInvaders.iterator();

        while (var2.hasNext()) {
            InvaderEntity invaderEntity2 = (InvaderEntity) var2.next();
            if (invaderEntity2.getUuid().equals(entity.getUuid())) {
                invaderEntity = invaderEntity2;
                break;
            }
        }

        if (invaderEntity != null) {
            set.remove(invaderEntity);
            set.add(entity);
        }

        set.add(entity);
        if (countHealth) {
            this.totalHealth += 1;
        }

        this.updateBar();
        this.markDirty();
        return true;
    }

    @Nullable
    public InvaderEntity getCaptain() {
        return this.currentCaptain;
    }

    public void setCaptain(InvaderEntity entity) {
        this.currentCaptain = entity;
        entity.equipStack(EquipmentSlot.HEAD, getBanner());
        entity.setEquipmentDropChance(EquipmentSlot.HEAD, 2.0F);
    }

    public void removeLeader() {
        this.currentCaptain = null;
    }

    public BlockPos getCenter() {
        return this.center;
    }

    private void setCenter(BlockPos center) {
        this.center = center;
    }

    public int getInvasionId() {
        return this.id;
    }

    public boolean isActive() {
        return this.active;
    }

    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt("Id", this.id);
        nbt.putBoolean("Started", this.started);
        nbt.putBoolean("Active", this.active);
        nbt.putLong("TicksActive", this.ticksActive);
        nbt.putInt("InvadersSpawned", this.invadersSpawned);
        nbt.putInt("PreInvasionTicks", this.preInvasionTicks);
        nbt.putInt("PostInvasionTicks", this.postInvasionTicks);
        nbt.putInt("TotalHealth", this.totalHealth);
        nbt.putInt("TotalNumInvaders", this.totalInvaderCount);
        nbt.putString("Status", this.status.getName());
        nbt.putInt("CX", this.center.getX());
        nbt.putInt("CY", this.center.getY());
        nbt.putInt("CZ", this.center.getZ());
        NbtList nbtList = new NbtList();
        Iterator var3 = this.heroesOfTheVillage.iterator();

        while (var3.hasNext()) {
            UUID uUID = (UUID) var3.next();
            nbtList.add(NbtHelper.fromUuid(uUID));
        }

        nbt.put("HeroesOfTheVillage", nbtList);
        return nbt;
    }

    public void addHero(Entity entity) {
        this.heroesOfTheVillage.add(entity.getUuid());
    }

    private enum Status {
        ONGOING,
        VICTORY,
        STOPPED;

        private static final Invasion.Status[] VALUES = values();

        Status() {
        }

        static Invasion.Status fromName(String name) {
            Invasion.Status[] var1 = VALUES;
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                Invasion.Status status = var1[var3];
                if (name.equalsIgnoreCase(status.name())) {
                    return status;
                }
            }

            return ONGOING;
        }

        public String getName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    private enum Member {
        GOBLIN_ARCHER(ModEntities.GOBLIN_ARCHER, 0.1999D),
        GOBLIN_PEON(ModEntities.GOBLIN_PEON, 0.178D),
        GOBLIN_SORCERER(ModEntities.GOBLIN_SORCERER, 0.1111D),
        GOBLIN_THIEF(ModEntities.GOBLIN_THIEF, 0.237D),
        GOBLIN_WARRIOR(ModEntities.GOBLIN_WARRIOR, 0.274D);

        static final Invasion.Member[] VALUES = values();
        final EntityType<? extends InvaderEntity> type;
        final double spawnWeight;

        Member(EntityType<? extends InvaderEntity> type, double spawnWeight) {
            this.type = type;
            this.spawnWeight = spawnWeight;
        }
    }
}
