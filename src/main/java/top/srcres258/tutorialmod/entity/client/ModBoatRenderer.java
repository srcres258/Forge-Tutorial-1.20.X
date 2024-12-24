package top.srcres258.tutorialmod.entity.client;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.entity.custom.ModBoatEntity;
import top.srcres258.tutorialmod.entity.custom.ModChestBoatEntity;

import java.util.Map;
import java.util.stream.Stream;

public class ModBoatRenderer extends BoatRenderer {
    private final Map<ModBoatEntity.Type, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

    public ModBoatRenderer(EntityRendererProvider.Context pContext, boolean pChestBoat) {
        super(pContext, pChestBoat);
        boatResources = Stream.of(ModBoatEntity.Type.values())
                .collect(ImmutableMap.toImmutableMap(type -> type,
                        type -> Pair.of(new ResourceLocation(TutorialMod.MOD_ID,
                                getTextureLocation(type, pChestBoat)),
                                createBoatModel(pContext, type, pChestBoat))));
    }

    private static String getTextureLocation(ModBoatEntity.Type pType, boolean pChestBoat) {
        return pChestBoat ?
                "textures/entity/chest_boat/" + pType.getName() + ".png" :
                "textures/entity/boat/" + pType.getName() + ".png";
    }

    private ListModel<Boat> createBoatModel(
            EntityRendererProvider.Context pContext,
            ModBoatEntity.Type pType,
            boolean pChestBoat
    ) {
        var mll = pChestBoat ? ModBoatRenderer.createChestBoatModelName(pType) : ModBoatRenderer.createBoatModelName(pType);
        var mp = pContext.bakeLayer(mll);
        return pChestBoat ? new ChestBoatModel(mp) : new BoatModel(mp);
    }

    public static ModelLayerLocation createBoatModelName(ModBoatEntity.Type pType) {
        return createLocation("boat/" + pType.getName(), "main");
    }

    public static ModelLayerLocation createChestBoatModelName(ModBoatEntity.Type pType) {
        return createLocation("chest_boat/" + pType.getName(), "main");
    }

    private static ModelLayerLocation createLocation(String pPath, String pModel) {
        return new ModelLayerLocation(new ResourceLocation(TutorialMod.MOD_ID, pPath), pModel);
    }

    @Override
    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
        if (boat instanceof ModBoatEntity modBoat) {
            return boatResources.get(modBoat.getModVariant());
        } else if (boat instanceof ModChestBoatEntity modChestBoat) {
            return boatResources.get(modChestBoat.getModVariant());
        } else {
            return null;
        }
    }
}
