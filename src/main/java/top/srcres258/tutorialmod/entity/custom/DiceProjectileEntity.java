package top.srcres258.tutorialmod.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import top.srcres258.tutorialmod.block.ModBlocks;
import top.srcres258.tutorialmod.block.custom.DiceBlock;
import top.srcres258.tutorialmod.entity.ModEntities;
import top.srcres258.tutorialmod.item.ModItems;

public class DiceProjectileEntity extends ThrowableItemProjectile {
    public DiceProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public DiceProjectileEntity(Level pLevel) {
        super(ModEntities.DICE_PROJECTILE.get(), pLevel);
    }

    public DiceProjectileEntity(Level pLevel, LivingEntity pLivingEntity) {
        super(ModEntities.DICE_PROJECTILE.get(), pLivingEntity, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.DICE.get();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if (!level().isClientSide()) {
            level().broadcastEntityEvent(this, (byte) 3);
            level().setBlock(blockPosition(), ((DiceBlock) ModBlocks.DICE_BLOCK.get()).getRandomBlockState(), 3);
        }

        super.onHitBlock(pResult);
    }
}
