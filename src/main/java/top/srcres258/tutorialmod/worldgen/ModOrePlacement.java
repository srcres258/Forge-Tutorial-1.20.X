package top.srcres258.tutorialmod.worldgen;

import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModOrePlacement {
    public static List<PlacementModifier> orePlacement(
            PlacementModifier modifier0,
            PlacementModifier modifier1
    ) {
        return List.of(modifier0, InSquarePlacement.spread(), modifier1, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(
            int pCount,
            PlacementModifier pHeightRange
    ) {
        return orePlacement(CountPlacement.of(pCount), pHeightRange);
    }

    public static List<PlacementModifier> rareOrePlacement(
            int pChance,
            PlacementModifier pHeightRange
    ) {
        return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
    }
}
