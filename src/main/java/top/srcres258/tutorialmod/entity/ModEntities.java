package top.srcres258.tutorialmod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.entity.custom.DiceProjectileEntity;
import top.srcres258.tutorialmod.entity.custom.ModBoatEntity;
import top.srcres258.tutorialmod.entity.custom.ModChestBoatEntity;
import top.srcres258.tutorialmod.entity.custom.RhinoEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TutorialMod.MOD_ID);

    public static final RegistryObject<EntityType<RhinoEntity>> RHINO =
            ENTITY_TYPES.register("rhino", () -> EntityType.Builder.of(RhinoEntity::new, MobCategory.CREATURE)
                    .sized(2.5F, 2.5F)
                    .build("rhino"));

    public static final RegistryObject<EntityType<ModBoatEntity>> MOD_BOAT =
            ENTITY_TYPES.register("mod_boat", () -> EntityType.Builder
                    .<ModBoatEntity>of(ModBoatEntity::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F)
                    .build("mod_boat"));
    public static final RegistryObject<EntityType<ModChestBoatEntity>> MOD_CHEST_BOAT =
            ENTITY_TYPES.register("mod_chest_boat", () -> EntityType.Builder
                    .<ModChestBoatEntity>of(ModChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F)
                    .build("mod_chest_boat"));

    public static final RegistryObject<EntityType<DiceProjectileEntity>> DICE_PROJECTILE =
            ENTITY_TYPES.register("dice_projectile", () -> EntityType.Builder
                    .<DiceProjectileEntity>of(DiceProjectileEntity::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.5F)
                    .build("dice_projectile"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
