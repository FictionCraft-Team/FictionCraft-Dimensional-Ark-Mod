package wintersteve25.dautils.common.compat.ct;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.oredict.IOreDictEntry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wintersteve25.dautils.common.crafting.SmelteryRecipe;

@ZenRegister
@ZenClass("mods.dautils.utils.Smeltery")
public class SmelteryCTCompat {

    @ZenMethod
    public static void addRecipe(IItemStack itemInput1, ILiquidStack fluidOutput, int minOrbTier, int processTime) {
        SmelteryRecipe.addRecipe(InputHelper.toStack(itemInput1), InputHelper.toFluid(fluidOutput), minOrbTier, processTime);
    }

    //TO FIX!
    @ZenMethod
    public static void addRecipe(IOreDictEntry itemInput1, ILiquidStack fluidOutput, int minOrbTier, int processTime) {
        SmelteryRecipe.addRecipe(InputHelper.toString(itemInput1), InputHelper.toFluid(fluidOutput), minOrbTier, processTime);
    }
}
