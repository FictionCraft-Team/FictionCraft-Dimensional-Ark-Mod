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

    @Config.Comment({"Should the forge anvil be registered? (only if weaponForging is enabled)"})
    @Config.RequiresMcRestart
    public static boolean registerForgeAnvil = true;

    @Config.Comment({"Should the forge hammer be registered? (only if weaponForging is enabled)"})
    @Config.RequiresMcRestart
    public static boolean registerHammer = true;

    @Config.Comment({"Should the sharpener be registered? (only if weaponForging is enabled)"})
    @Config.RequiresMcRestart
    public static boolean registerSharpeningTool = true;

    @Config.Comment({"Should the smeltery be registered? (only if weaponForging is enabled)"})
    @Config.RequiresMcRestart
    public static boolean registerSmeltery = true;

    @Config.Comment({"Should the lava orbs be registered? (only if weaponForging is enabled)"})
    @Config.RequiresMcRestart
    public static boolean lavaOrb = true;

    @Config.Comment({"Should the blazing orbs be registered? (only if weaponForging is enabled)"})
    @Config.RequiresMcRestart
    public static boolean blazingOrb = true;

    @Config.Comment({"Should the scorching orbs be registered? (only if weaponForging is enabled)"})
    @Config.RequiresMcRestart
    public static boolean scorchingOrb = true;

    @Config.Comment({"Should the heat orbs be used as a flint and steel? (only if weaponForging is enabled)"})
    @Config.RequiresMcRestart
    public static boolean shouldOrbCreateFire = true;

    @Config.Comment({"Should the sacrificer be enabled?"})
    @Config.RequiresMcRestart
    public static boolean registerSacrificer = true;
}
