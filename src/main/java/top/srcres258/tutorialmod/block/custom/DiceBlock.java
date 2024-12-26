package top.srcres258.tutorialmod.block.custom;

import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class DiceBlock extends Block {
    public static DirectionProperty FACING = DirectionProperty.create("number",
            Direction.UP,
            Direction.NORTH,
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST,
            Direction.DOWN);

    public DiceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FACING, getRandomDirection());
    }

    public BlockState getRandomBlockState() {
        return defaultBlockState().setValue(FACING, getRandomDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    private Direction getRandomDirection() {
        var dirs = new Direction[] {
                Direction.UP,
                Direction.NORTH,
                Direction.EAST,
                Direction.SOUTH,
                Direction.WEST,
                Direction.DOWN
        };
        return dirs[RandomSource.create().nextIntBetweenInclusive(0, dirs.length - 1)];
    }
}
