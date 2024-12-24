package top.srcres258.tutorialmod.util;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import top.srcres258.tutorialmod.TutorialMod;

public class ModWoodTypes {
    public static final WoodType PINE = WoodType.register(new WoodType(TutorialMod.MOD_ID + ":pine",
            BlockSetType.OAK));
}
