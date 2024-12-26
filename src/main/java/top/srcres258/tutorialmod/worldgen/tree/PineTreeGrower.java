package top.srcres258.tutorialmod.worldgen.tree;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import top.srcres258.tutorialmod.worldgen.ModConfiguredFeatures;

public class PineTreeGrower extends AbstractTreeGrower {
    @Override
    protected @Nullable ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(
            RandomSource pRandom,
            boolean pHasFlowers
    ) {
        return ModConfiguredFeatures.PINE_KEY;
    }
}
