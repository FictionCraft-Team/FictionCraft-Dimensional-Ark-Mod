package wintersteve25.dautils.common.compat.ct;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wintersteve25.dautils.common.crafting.SacrificerRecipe;

@ZenRegister
@ZenClass("mods.dautils.Sacrificer")
public class SacrificerCTCompat {

    @ZenMethod
    public static void addRecipe(IItemStack input, int blood, int time) {
        CraftTweakerAPI.logInfo("Adding a ItemStack Sacrificer Recipe");
        SacrificerRecipe.addRecipe(InputHelper.toStack(input), blood, time);
    }
}
