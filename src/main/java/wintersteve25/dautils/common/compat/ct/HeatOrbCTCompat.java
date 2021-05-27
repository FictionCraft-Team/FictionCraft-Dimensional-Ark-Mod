package wintersteve25.dautils.common.compat.ct;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.item.Item;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wintersteve25.dautils.DAUtils;
import wintersteve25.dautils.common.item.DAItems;
import wintersteve25.dautils.common.item.heat_orbs.ItemHeatOrb;

@ZenRegister
@ZenClass("mods.dautils.utils.HeatOrbs")
public class HeatOrbCTCompat {

    @ZenMethod
    public static void createOrb(String name, int tier) {
        Item itemHeatOrb;
        itemHeatOrb = DAItems.initItem(new ItemHeatOrb(tier).setRegistryName(name).setTranslationKey(DAUtils.MODID + "." + name));
    }
}
