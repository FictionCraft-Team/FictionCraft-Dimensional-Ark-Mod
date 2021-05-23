package wintersteve25.dautils.common.lib;

import net.minecraft.util.ResourceLocation;
import wintersteve25.dautils.DAUtils;

public class Utils {
    public static ResourceLocation resourceLocationHelper(String pathIn) {
        return new ResourceLocation(DAUtils.MODID, pathIn);
    }
}
