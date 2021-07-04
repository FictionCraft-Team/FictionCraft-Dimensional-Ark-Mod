package wintersteve25.dautils.common.compat.ct;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import erebus.recipes.OfferingAltarRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ModOnly("erebus")
@ZenClass("mods.dautils.erebus.OfferingAltar")
public class OfferingAltarErebus {
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient... inputs) {
        if (inputs == null) {
            CraftTweakerAPI.logError("inputs can not be null!");
            return;
        }

        OfferingAltarRecipe.addRecipe(InputHelper.toStack(output), InputHelper.toObject(inputs[0]), InputHelper.toObject(inputs[1]), InputHelper.toObject(inputs[2]));
    }
}
