package top.srcres258.tutorialmod.item.custom;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import top.srcres258.tutorialmod.entity.custom.ModBoatEntity;
import top.srcres258.tutorialmod.entity.custom.ModChestBoatEntity;

import java.util.function.Predicate;

public class ModBoatItem extends Item {
    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    private final ModBoatEntity.Type type;
    private final boolean hasChest;

    public ModBoatItem(boolean pHasChest, ModBoatEntity.Type pType, Properties pProperties) {
        super(pProperties);
        this.hasChest = pHasChest;
        this.type = pType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(
            Level pLevel,
            Player pPlayer,
            InteractionHand pUsedHand
    ) {
        var itemStack = pPlayer.getItemInHand(pUsedHand);
        var hitResult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.ANY);
        if (hitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemStack);
        } else {
            var vec3 = pPlayer.getViewVector(1F);
            var list = pLevel.getEntities(pPlayer, pPlayer.getBoundingBox().expandTowards(
                    vec3.scale(5D)).inflate(1D), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                var vec31 = pPlayer.getEyePosition();

                for (var entity : list) {
                    var aabb = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (aabb.contains(vec31)) {
                        return InteractionResultHolder.pass(itemStack);
                    }
                }
            }

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                var boat = getBoat(pLevel, hitResult);
                if (boat instanceof ModChestBoatEntity modChestBoat) {
                    modChestBoat.setVariant(type);
                } else if (boat instanceof ModBoatEntity modBoat) {
                    modBoat.setVariant(type);
                }
                boat.setYRot(pPlayer.getYRot());
                if (!pLevel.noCollision(boat, boat.getBoundingBox())) {
                    return InteractionResultHolder.fail(itemStack);
                } else {
                    if (!pLevel.isClientSide()) {
                        pLevel.addFreshEntity(boat);
                        pLevel.gameEvent(pPlayer, GameEvent.ENTITY_PLACE, hitResult.getLocation());
                        if (!pPlayer.getAbilities().instabuild) {
                            itemStack.shrink(1);
                        }
                    }

                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
                }
            } else {
                return InteractionResultHolder.pass(itemStack);
            }
        }
    }

    private Boat getBoat(Level pLevel, HitResult pHitResult) {
        return this.hasChest ?
                new ModChestBoatEntity(pLevel,
                        pHitResult.getLocation().x,
                        pHitResult.getLocation().y,
                        pHitResult.getLocation().z) :
                new ModBoatEntity(pLevel,
                        pHitResult.getLocation().x,
                        pHitResult.getLocation().y,
                        pHitResult.getLocation().z);
    }
}
