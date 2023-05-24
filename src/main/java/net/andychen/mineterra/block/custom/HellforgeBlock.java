package net.andychen.mineterra.block.custom;

import net.andychen.mineterra.block.entity.HellforgeBlockEntity;
import net.andychen.mineterra.block.entity.ModBlockEntities;
import net.andychen.mineterra.block.enums.DoublePart;
import net.andychen.mineterra.screen.HellforgeScreenHandler;
import net.andychen.mineterra.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class HellforgeBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING;
    public static final EnumProperty<DoublePart> PART = ModProperties.DOUBLE_PART;
    private static final VoxelShape MAIN = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 22.0, 16.0);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 22.0, 11.0);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(5.0, 0.0, 0.0, 16.0, 22.0, 16.0);
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 5.0, 16.0, 22.0, 16.0);
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 11.0, 22.0, 16.0);
    private static final DoubleBlockProperties.PropertyRetriever<HellforgeBlockEntity, Optional<NamedScreenHandlerFactory>> NAME_RETRIEVER;

    static {
        FACING = Properties.HORIZONTAL_FACING;
        NAME_RETRIEVER = new DoubleBlockProperties.PropertyRetriever<HellforgeBlockEntity, Optional<NamedScreenHandlerFactory>>() {
            public Optional<NamedScreenHandlerFactory> getFromBoth(HellforgeBlockEntity hellforgeBlockEntity, HellforgeBlockEntity hellforgeBlockEntity2) {
                final Inventory inventory = new DoubleInventory(hellforgeBlockEntity, hellforgeBlockEntity2);
                return Optional.of(new NamedScreenHandlerFactory() {
                    @Nullable
                    public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new HellforgeScreenHandler(i, playerInventory, inventory, hellforgeBlockEntity.getPropertyDelegate());
                    }

                    public Text getDisplayName() {
                        return Text.translatable("container.mineterra.hellforge");
                    }
                });
            }

            public Optional<NamedScreenHandlerFactory> getFrom(HellforgeBlockEntity hellforgeBlockEntity) {
                return Optional.of(hellforgeBlockEntity);
            }

            public Optional<NamedScreenHandlerFactory> getFallback() {
                return Optional.empty();
            }
        };
    }


    public HellforgeBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState) ((BlockState) ((BlockState) this.stateManager.getDefaultState()).with(PART, DoublePart.LEFT)));
    }

    private static Direction getDirectionTowardsOtherPart(DoublePart part, Direction direction) {
        return part == DoublePart.LEFT ? direction.getOpposite().rotateYClockwise() : direction.getOpposite().rotateYCounterclockwise();
    }

    public static Direction getOppositePartDirection(BlockState state) {
        Direction direction = (Direction) state.get(FACING);
        return state.get(PART) == DoublePart.RIGHT ? direction.rotateClockwise(Direction.Axis.Y) : direction;
    }

    public static DoubleBlockProperties.Type getDoubleBlockType(BlockState state) {
        return state.get(PART) == DoublePart.LEFT ? DoubleBlockProperties.Type.FIRST : DoubleBlockProperties.Type.SECOND;
    }

    public static Direction getFacing(BlockState state) {
        Direction direction = (Direction) state.get(FACING).getOpposite();
        return state.get(PART) == DoublePart.LEFT ? direction.rotateYClockwise() : direction.rotateYCounterclockwise();
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == getDirectionTowardsOtherPart((DoublePart) state.get(PART), (Direction) state.get(FACING))) {
            return neighborState.isOf(this) && neighborState.get(PART) != state.get(PART) ? (BlockState) state : Blocks.AIR.getDefaultState();
        } else {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            BlockPos blockPos = pos.offset(getDirectionTowardsOtherPart(state.get(PART), state.get(FACING)));
            world.setBlockState(blockPos, (BlockState) state.with(PART, DoublePart.RIGHT), 3);
            world.updateNeighbors(pos, Blocks.AIR);
            state.updateNeighbors(world, pos, 3);
        }
    }

    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && player.isCreative()) {
            DoublePart doublePart = (DoublePart) state.get(PART);
            if (doublePart == DoublePart.LEFT) {
                BlockPos blockPos = pos.offset(getDirectionTowardsOtherPart(doublePart, (Direction) state.get(FACING)));
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.isOf(this) && blockState.get(PART) == DoublePart.RIGHT) {
                    world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 35);
                    world.syncWorldEvent(player, 2001, blockPos, Block.getRawIdFromState(blockState));
                }
            }
        }

        super.onBreak(world, pos, state, player);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getPlayerFacing();
        BlockPos blockPos = ctx.getBlockPos();
        BlockPos blockPos2 = blockPos.offset(getDirectionTowardsOtherPart(DoublePart.LEFT, direction.getOpposite()));
        World world = ctx.getWorld();
        return world.getBlockState(blockPos2).canReplace(ctx) && world.getWorldBorder().contains(blockPos2) ? (BlockState) this.getDefaultState().with(FACING, direction.getOpposite()) : null;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(PART) == DoublePart.LEFT) {
            return MAIN;
        }
        Direction direction = getOppositePartDirection(state).getOpposite();
        switch (direction) {
            case NORTH:
                return NORTH_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case WEST:
                return WEST_SHAPE;
            default:
                return EAST_SHAPE;
        }
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(PART) == DoublePart.LEFT) {
            Direction direction = getDirectionTowardsOtherPart(DoublePart.LEFT, state.get(FACING));
            double d = (double) pos.getX() + ((pos.getX() - pos.offset(direction).getX()) / 2) + random.nextDouble();
            double e = (double) pos.getY() + 0.7D;
            double f = (double) pos.getZ() + ((pos.getZ() - pos.offset(direction).getZ()) / 2) + random.nextDouble();

            world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);

            if (random.nextInt(20) == 0) {
                world.addParticle(ParticleTypes.LAVA, d, e, f, 0.0D, 0.0D, 0.0D);
                world.playSound((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
            }

            if (random.nextInt(60) == 0) {
                world.playSound((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);

            }
        }
    }

    public long getRenderingSeed(BlockState state, BlockPos pos) {
        BlockPos blockPos = pos.offset((Direction) state.get(FACING), state.get(PART) == DoublePart.RIGHT ? 0 : 1);
        return MathHelper.hashCode(blockPos.getX(), pos.getY(), blockPos.getZ());
    }

    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, PART});
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState) state.with(FACING, rotation.rotate((Direction) state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation((Direction) state.get(FACING)));
    }

    /* BLOCK ENTITY */

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        if (state.get(PART) == DoublePart.LEFT) {
            return BlockRenderType.MODEL;
        }
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof HellforgeBlockEntity) {
                if (world instanceof ServerWorld) {
                    ItemScatterer.spawn(world, pos, (HellforgeBlockEntity) blockEntity);
                    ((HellforgeBlockEntity) blockEntity).getRecipesUsedAndDropExperience((ServerWorld) world, Vec3d.ofCenter(pos));
                }

                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    public DoubleBlockProperties.PropertySource<? extends HellforgeBlockEntity> getBlockEntitySource(BlockState state, World world, BlockPos pos) {
        return DoubleBlockProperties.toPropertySource((BlockEntityType) ModBlockEntities.HELLFORGE, HellforgeBlock::getDoubleBlockType, HellforgeBlock::getFacing, FACING, state, world, pos, (worldx, posx) -> false);
    }

    @Nullable
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return this.getBlockEntitySource(state, world, pos).apply(NAME_RETRIEVER).get();
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HellforgeBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.HELLFORGE, HellforgeBlockEntity::tick);
    }
}
