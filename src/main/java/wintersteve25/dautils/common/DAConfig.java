package wintersteve25.dautils.common;

import net.minecraftforge.common.config.Config;
import wintersteve25.dautils.DAUtils;

@Config(modid = DAUtils.MODID)
public class DAConfig {
    @Config.Comment({"Should the blood note be registered?"})
    @Config.RequiresMcRestart
    public static boolean registerBloodNote = true;

    @Config.Comment({"Should the weapon forging part of the mod be registered?"})
    @Config.RequiresMcRestart
    public static boolean weaponForging = true;

    @Config.Comment({"Should the forge anvil be registered?"})
    @Config.RequiresMcRestart
    public static boolean registerForgeAnvil = true;

    @Config.Comment({"Should the forge hammer be registered?"})
    @Config.RequiresMcRestart
    public static boolean registerHammer = true;
}
