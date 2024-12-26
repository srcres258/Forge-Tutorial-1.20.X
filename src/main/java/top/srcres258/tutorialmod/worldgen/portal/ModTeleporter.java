package top.srcres258.tutorialmod.worldgen.portal;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.ITeleporter;
import top.srcres258.tutorialmod.block.ModBlocks;
import top.srcres258.tutorialmod.block.custom.ModPortalBlock;

import java.util.function.Function;

public class ModTeleporter implements ITeleporter {
    public static BlockPos thisPos = BlockPos.ZERO;
    public static boolean insideDimension = true;

    public ModTeleporter(BlockPos pos, boolean insideDim) {
        thisPos = pos;
        insideDimension = insideDim;
    }

    @Override
    public Entity placeEntity(
            Entity entity,
            ServerLevel currentWorld,
            ServerLevel destWorld,
            float yaw,
            Function<Boolean, Entity> repositionEntity
    ) {
        entity = repositionEntity.apply(false);
        var y = 61;

        if (!insideDimension) {
            y = thisPos.getY();
        }

        var destPos = new BlockPos(thisPos.getX(), y, thisPos.getZ());

        var tries = 0;
        while (
                destWorld.getBlockState(destPos).getBlock() != Blocks.AIR
                && !destWorld.getBlockState(destPos).canBeReplaced(Fluids.WATER)
                && destWorld.getBlockState(destPos.above()).getBlock() != Blocks.AIR
                && !destWorld.getBlockState(destPos.above()).canBeReplaced(Fluids.WATER)
                && tries < 25
        ) {
            destPos = destPos.above(2);
            tries++;
        }

        if (insideDimension) {
            var doSetBlock = true;
            for (var checkPos : BlockPos.betweenClosed(destPos.below(10).west(10),
                    destPos.above(10).east(10))) {
                if (destWorld.getBlockState(checkPos).getBlock() instanceof ModPortalBlock) {
                    doSetBlock = false;
                    break;
                }
            }
            if (doSetBlock) {
                destWorld.setBlock(destPos, ModBlocks.MOD_PORTAL.get().defaultBlockState(), 3);
            }
        }

        return entity;
    }
}
