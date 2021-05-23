package wintersteve25.dautils.common.ct;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wintersteve25.dautils.common.lib.Math;

@ZenRegister
@ZenClass("mods.dautils.Math")
public class MathCTCompat {
    @ZenMethod
    public static double randomInRange(double min, double max) {
        return Math.randomInRange(min, max);
    }
}
