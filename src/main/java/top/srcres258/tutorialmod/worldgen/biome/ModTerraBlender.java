package top.srcres258.tutorialmod.worldgen.biome;

import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;
import top.srcres258.tutorialmod.TutorialMod;

public class ModTerraBlender {
    public static void registerBiomes() {
        Regions.register(new ModOverworldRegion(new ResourceLocation(TutorialMod.MOD_ID, "overworld"), 5));
    }
}
