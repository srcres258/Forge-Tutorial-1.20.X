package top.srcres258.tutorialmod.worldgen.biome.surface;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import top.srcres258.tutorialmod.block.ModBlocks;
import top.srcres258.tutorialmod.worldgen.biome.ModBiomes;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource SAPPHIRE = makeStateRule(ModBlocks.SAPPHIRE_BLOCK.get());
    private static final SurfaceRules.RuleSource RAW_SAPPHIRE = makeStateRule(ModBlocks.RAW_SAPPHIRE_BLOCK.get());

    public static SurfaceRules.RuleSource makeRules() {
        var isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);
        var grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);
        return SurfaceRules.sequence(
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                                SurfaceRules.isBiome(ModBiomes.TEST_BIOME),
                                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, RAW_SAPPHIRE)
                        ),
                        SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SAPPHIRE)
                ),

                // Default to a grass and dirt surface.
                SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, grassSurface)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
