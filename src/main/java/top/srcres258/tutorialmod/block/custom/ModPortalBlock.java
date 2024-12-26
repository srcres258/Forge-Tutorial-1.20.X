package top.srcres258.tutorialmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import top.srcres258.tutorialmod.worldgen.dimension.ModDimensions;
import top.srcres258.tutorialmod.worldgen.portal.ModTeleporter;

public class ModPortalBlock extends Block {
    public ModPortalBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.canChangeDimensions()) {
            handleKaupenPortal(pPlayer, pPos);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.CONSUME;
        }
    }

    private void handleKaupenPortal(Entity pPlayer, BlockPos pPos) {
        if (pPlayer.level() instanceof ServerLevel serverLevel) {
            var server = serverLevel.getServer();
            var resKey = pPlayer.level().dimension() == ModDimensions.KAUPENDIM_LEVEL_KEY ?
                    Level.OVERWORLD : ModDimensions.KAUPENDIM_LEVEL_KEY;

            var portalDimension = server.getLevel(resKey);
            if (portalDimension != null && !pPlayer.isPassenger()) {
                if (resKey == ModDimensions.KAUPENDIM_LEVEL_KEY) {
                    pPlayer.changeDimension(portalDimension, new ModTeleporter(pPos, true));
                } else {
                    pPlayer.changeDimension(portalDimension, new ModTeleporter(pPos, false));
                }
            }
        }
    }
}
