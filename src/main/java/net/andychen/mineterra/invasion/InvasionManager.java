package net.andychen.mineterra.invasion;

import com.google.common.collect.Maps;
import net.andychen.mineterra.entity.custom.InvaderEntity;
import net.andychen.mineterra.util.access.ServerWorldMixinAccess;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.GameRules;
import net.minecraft.world.PersistentState;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Map;

public class InvasionManager extends PersistentState {
    private static final String INVASIONS = "invasions";
    private final Map<Integer, Invasion> invasions = Maps.newHashMap();
    private final ServerWorld world;
    private int nextAvailableId;
    private int currentTime;

    public InvasionManager(ServerWorld world) {
        this.world = world;
        this.nextAvailableId = 1;
        this.markDirty();
    }

    public Invasion getInvasion(int id) {
        return (Invasion)this.invasions.get(id);
    }

    public void tick() {
        ++this.currentTime;
        Iterator iterator = this.invasions.values().iterator();

        while(iterator.hasNext()) {
            Invasion invasion = (Invasion)iterator.next();
            if (this.world.getGameRules().getBoolean(GameRules.DISABLE_RAIDS)) {
                invasion.invalidate();
            }

            if (invasion.hasStopped()) {
                iterator.remove();
                this.markDirty();
            } else {
                invasion.tick();
            }
        }

        if (this.currentTime % 200 == 0) {
            this.markDirty();
        }
    }

    public static boolean isValidInvaderFor(InvaderEntity invader, Invasion invasion) {
        if (invader != null && invasion != null && invasion.getWorld() != null) {
            return invader.isAlive() && invader.canJoinInvasion() && invader.getDespawnCounter() <= 2400 && invader.world.getDimension() == invader.getWorld().getDimension();
        } else {
            return false;
        }
    }

    @Nullable
    public Invasion startInvasion(ServerPlayerEntity player) {
        if (player.isSpectator()) {
            return null;
        } else if (this.world.getGameRules().getBoolean(GameRules.DISABLE_RAIDS)) {
            return null;
        } else {
            DimensionType dimensionType = player.world.getDimension();
            if (!dimensionType.hasRaids()) {
                return null;
            } else {
                BlockPos blockPos = player.getBlockPos();

                Invasion invasion = this.getOrCreateInvasion(player.getWorld(), blockPos);
                boolean bl = false;
                if (!invasion.hasStarted()) {
                    if (!this.invasions.containsKey(invasion.getInvasionId())) {
                        this.invasions.put(invasion.getInvasionId(), invasion);
                    }
                    bl = true;
                }

                if (bl) {
                    invasion.start(player);
                    if (!invasion.hasSpawned()) {
                        player.incrementStat(Stats.RAID_TRIGGER);
                        Criteria.VOLUNTARY_EXILE.trigger(player);
                    }
                }

                this.markDirty();
                return invasion;
            }
        }
    }

    private Invasion getOrCreateInvasion(ServerWorld world, BlockPos pos) {
        Invasion invasion = ((ServerWorldMixinAccess)world).getInvasionAt(pos);
        return invasion != null ? invasion : new Invasion(this.nextId(), world, pos);
    }

    public static InvasionManager fromNbt(ServerWorld world, NbtCompound nbt) {
        InvasionManager invasionManager = new InvasionManager(world);
        invasionManager.nextAvailableId = nbt.getInt("NextAvailableID");
        invasionManager.currentTime = nbt.getInt("Tick");
        NbtList nbtList = nbt.getList("Invasions", 10);

        for(int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            Invasion invasion = new Invasion(world, nbtCompound);
            invasionManager.invasions.put(invasion.getInvasionId(), invasion);
        }

        return invasionManager;
    }

    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt("NextAvailableID", this.nextAvailableId);
        nbt.putInt("Tick", this.currentTime);
        NbtList nbtList = new NbtList();
        Iterator var3 = this.invasions.values().iterator();

        while(var3.hasNext()) {
            Invasion invasion = (Invasion)var3.next();
            NbtCompound nbtCompound = new NbtCompound();
            invasion.writeNbt(nbtCompound);
            nbtList.add(nbtCompound);
        }

        nbt.put("Invasions", nbtList);
        return nbt;
    }

    public static String nameFor(RegistryEntry<DimensionType> dimensionTypeEntry) {
        return dimensionTypeEntry.matchesKey(DimensionTypes.THE_END) ? "invasions_end" : "invasions";
    }

    private int nextId() {
        return ++this.nextAvailableId;
    }

    public boolean getInvasionsIsEmpty() {
        return this.invasions.isEmpty();
    }

    @Nullable
    public Invasion getInvasionAt(BlockPos pos, int searchDistance) {
        Invasion invasion = null;
        double d = (double)searchDistance;
        Iterator var6 = this.invasions.values().iterator();

        while(var6.hasNext()) {
            Invasion invasion2 = (Invasion)var6.next();
            double e = invasion2.getCenter().getSquaredDistance(pos);
            if (invasion2.isActive() && e < d) {
                invasion = invasion2;
                d = e;
            }
        }

        return invasion;
    }
}

