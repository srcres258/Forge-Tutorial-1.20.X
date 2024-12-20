package top.srcres258.tutorialmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import top.srcres258.tutorialmod.item.ModItems;

public class CornCropBlock extends CropBlock {
    public static final int FIRST_STAGE_MAX_AGE = 7;
    public static final int SECOND_STAGE_MAX_AGE = 1;

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] {
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    };

    public static final IntegerProperty AGE = IntegerProperty.create(
            "age", 0, FIRST_STAGE_MAX_AGE + SECOND_STAGE_MAX_AGE);

    public CornCropBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_AGE[getAge(pState)];
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) {
            return;
        }
        if (pLevel.getRawBrightness(pPos, 0) >= 9) {
            var currentAge = getAge(pState);

            if (currentAge < getMaxAge()) {
                var growthSpeed = getGrowthSpeed(this, pLevel, pPos);

                if (ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int) (25F / growthSpeed) + 1) == 0)) {
                    if (currentAge == FIRST_STAGE_MAX_AGE) {
                        if (pLevel.getBlockState(pPos.above()).is(Blocks.AIR)) {
                            pLevel.setBlock(pPos.above(), getStateForAge(currentAge + 1), 2);
                        }
                    } else {
                        pLevel.setBlock(pPos, getStateForAge(currentAge + 1), 2);
                    }

                    ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            }
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return super.mayPlaceOn(state, world, pos);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return super.canSurvive(pState, pLevel, pPos) || pLevel.getBlockState(pPos.below()).is(this)
                && pLevel.getBlockState(pPos.below()).getValue(AGE) == FIRST_STAGE_MAX_AGE;
    }

    @Override
    public void growCrops(Level pLevel, BlockPos pPos, BlockState pState) {
        var nextAge = getAge(pState) + getBonemealAgeIncrease(pLevel);
        var maxAge = getMaxAge();
        if (nextAge > maxAge) {
            nextAge = maxAge;
        }

        if (getAge(pState) == FIRST_STAGE_MAX_AGE && pLevel.getBlockState(pPos.above()).is(Blocks.AIR)) {
            pLevel.setBlock(pPos.above(), getStateForAge(nextAge), 2);
        } else {
            pLevel.setBlock(pPos, getStateForAge(nextAge - SECOND_STAGE_MAX_AGE), 2);
        }
    }

    @Override
    public int getMaxAge() {
        return FIRST_STAGE_MAX_AGE + SECOND_STAGE_MAX_AGE;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.CORN_SEEDS.get();
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}
