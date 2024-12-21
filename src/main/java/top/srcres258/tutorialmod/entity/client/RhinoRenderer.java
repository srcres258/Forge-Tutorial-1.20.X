package top.srcres258.tutorialmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import top.srcres258.tutorialmod.TutorialMod;
import top.srcres258.tutorialmod.entity.custom.RhinoEntity;

public class RhinoRenderer extends MobRenderer<RhinoEntity, RhinoModel<RhinoEntity>> {
    public RhinoRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new RhinoModel<>(pContext.bakeLayer(ModModelLayers.RHINO_LAYER)), 2F);
    }

    @Override
    public ResourceLocation getTextureLocation(RhinoEntity pEntity) {
        return new ResourceLocation(TutorialMod.MOD_ID, "textures/entity/rhino.png");
    }

    @Override
    public void render(
            RhinoEntity pEntity,
            float pEntityYaw,
            float pPartialTicks,
            PoseStack pPoseStack,
            MultiBufferSource pBuffer,
            int pPackedLight
    ) {
        if (pEntity.isBaby()) {
            pPoseStack.scale(0.5F, 0.5F, 0.5F);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
