package net.andychen.mineterra.block.entity;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.andychen.mineterra.recipe.HellforgeRecipe;
import net.andychen.mineterra.screen.HellforgeScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class HellforgeBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final PropertyDelegate propertyDelegate;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(6, ItemStack.EMPTY);
    private final Object2IntOpenHashMap<Identifier> recipesUsed;
    private int cookTime = 0;
    private int maxCookTime = 400;
    private int fuel = 0;
    private int maxFuel = 64;

    public HellforgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HELLFORGE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch (index) {
                    case 0:
                        return HellforgeBlockEntity.this.cookTime;
                    case 1:
                        return HellforgeBlockEntity.this.maxCookTime;
                    case 2:
                        return HellforgeBlockEntity.this.fuel;
                    case 3:
                        return HellforgeBlockEntity.this.maxFuel;
                    default:
                        return 0;
                }
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        HellforgeBlockEntity.this.cookTime = value;
                        break;
                    case 1:
                        HellforgeBlockEntity.this.maxCookTime = value;
                        break;
                    case 2:
                        HellforgeBlockEntity.this.fuel = value;
                        break;
                    case 3:
                        HellforgeBlockEntity.this.maxFuel = value;
                        break;
                }
            }

            public int size() {
                return 4;
            }
        };
        this.recipesUsed = new Object2IntOpenHashMap();
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, HellforgeBlockEntity entity) {
        if (world.isClient()) {
            return;
        }

        if (canInsertFuel(entity)) {
            insertFuel(entity);
            markDirty(world, blockPos, blockState);
        }

        if (hasRecipe(entity)) {
            Optional<HellforgeRecipe> recipe = getRecipe(entity);
            entity.setLastRecipe(recipe.get());
            entity.maxCookTime = recipe.get().getCookingTime();
            entity.cookTime++;
            markDirty(world, blockPos, blockState);
            if (entity.cookTime >= entity.maxCookTime && entity.fuel > 0) {
                craftItem(entity);
            }
        } else {
            entity.resetProgress();
            markDirty(world, blockPos, blockState);
        }
    }

    private static void insertFuel(HellforgeBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        if (canInsertFuel(entity)) {
            entity.removeStack(0, 1);
            entity.setStack(0, new ItemStack(Items.BUCKET, entity.getStack(0).getCount() + 1));
            entity.fuel += 16;
            entity.world.playSound(null, entity.getPos(), SoundEvents.ITEM_BUCKET_EMPTY_LAVA, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
    }

    private static boolean canInsertFuel(HellforgeBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        return entity.getStack(0).getItem() == Items.LAVA_BUCKET && entity.fuel + 16 <= entity.maxFuel;
    }

    private static void craftItem(HellforgeBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<HellforgeRecipe> recipe = getRecipe(entity);

        if (hasRecipe(entity)) {
            for (int slot = 1; slot < 5; slot++) {
                entity.getStack(slot).decrement(1);
            }

            if (entity.getStack(5).isEmpty()) {
                entity.setStack(5, recipe.get().getOutput());
            } else if (entity.getStack(5).isOf(recipe.get().getOutput().getItem())) {
                entity.getStack(5).increment(1);
            }
            --entity.fuel;

            Random random = new Random();
            entity.world.playSound(null, entity.getPos(), SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 1.0f, 0.6F + random.nextFloat() * 0.15F);
            entity.world.playSound(null, entity.getPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 0.9F + random.nextFloat() * 0.15F);
        }

        entity.resetProgress();
    }

    private static Optional<HellforgeRecipe> getRecipe(HellforgeBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }
        return entity.getWorld().getRecipeManager().getFirstMatch(HellforgeRecipe.Type.INSTANCE, inventory, entity.getWorld());
    }

    private static boolean hasRecipe(HellforgeBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<HellforgeRecipe> match = getRecipe(entity);
        boolean hasFuel = entity.fuel > 0;

        return match.isPresent() && hasFuel && canInsertAmountIntoOutputSlot(inventory) && canInsertItemIntoOutputSlot(inventory, match.get().getOutput().getItem());
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, Item output) {
        return inventory.getStack(5).getItem() == output || inventory.getStack(5).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(5).getMaxCount() > inventory.getStack(5).getCount();
    }

    public void setLastRecipe(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            Identifier identifier = recipe.getId();
            this.recipesUsed.addTo(identifier, 1);
        }
    }

    private static void dropExperience(ServerWorld world, Vec3d pos, int multiplier, float experience) {
        int i = MathHelper.floor((float) multiplier * experience);
        float f = MathHelper.fractionalPart((float) multiplier * experience);
        if (f != 0.0F && Math.random() < (double) f) {
            ++i;
        }

        ExperienceOrbEntity.spawn(world, pos, i);
    }

    public void dropExperienceForRecipesUsed(ServerPlayerEntity player) {
        List<Recipe<?>> list = this.getRecipesUsedAndDropExperience(player.getWorld(), player.getPos());
        player.unlockRecipes(list);
        this.recipesUsed.clear();
    }

    public List<Recipe<?>> getRecipesUsedAndDropExperience(ServerWorld world, Vec3d pos) {
        List<Recipe<?>> list = Lists.newArrayList();
        ObjectIterator var4 = this.recipesUsed.object2IntEntrySet().iterator();

        while (var4.hasNext()) {
            Object2IntMap.Entry<Identifier> entry = (Object2IntMap.Entry) var4.next();
            world.getRecipeManager().get((Identifier) entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                dropExperience(world, pos, entry.getIntValue(), ((HellforgeRecipe) recipe).getExperience());
            });
        }

        return list;
    }

    private void resetProgress() {
        this.cookTime = 0;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.mineterra.hellforge");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new HellforgeScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    public PropertyDelegate getPropertyDelegate() {
        return this.propertyDelegate;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putInt("hellforge.cookTime", this.cookTime);
        nbt.putInt("hellforge.fuel", this.fuel);
        NbtCompound nbtCompound = new NbtCompound();
        this.recipesUsed.forEach((identifier, count) -> {
            nbtCompound.putInt(identifier.toString(), count);
        });
        nbt.put("hellforge.recipesUsed", nbtCompound);

    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, this.inventory);
        super.readNbt(nbt);
        this.cookTime = nbt.getInt("hellforge.cookTime");
        this.fuel = nbt.getInt("hellforge.fuel");
        NbtCompound nbtCompound = nbt.getCompound("hellforge.recipesUsed");
        Iterator var3 = nbtCompound.getKeys().iterator();

        while (var3.hasNext()) {
            String string = (String) var3.next();
            this.recipesUsed.put(new Identifier(string), nbtCompound.getInt(string));
        }
    }
}
