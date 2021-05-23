package wintersteve25.dautils.common;

import net.minecraftforge.common.config.Config;
import wintersteve25.dautils.DAUtils;

@Config(modid = DAUtils.MODID)
public class DAConfig {
    @Config.Comment({"Should the blood note be registered?"})
    @Config.RequiresMcRestart
    public static boolean registerBloodNote = true;
}
