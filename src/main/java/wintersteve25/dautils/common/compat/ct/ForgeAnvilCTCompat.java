package wintersteve25.dautils.common.compat.ct;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wintersteve25.dautils.common.crafting.ForgeAnvilRecipe;

@ZenRegister
@ZenClass("mods.dautils.utils.ForgeAnvil")
public class ForgeAnvilCTCompat {
    @ZenMethod
    public static void addRecipe(IItemStack itemInput1, IItemStack itemInput2, IItemStack itemInput3, IItemStack itemInput4, IItemStack itemOutput) {
        ForgeAnvilRecipe.addRecipe(InputHelper.toStack(itemInput1), InputHelper.toStack(itemInput2), InputHelper.toStack(itemInput3), InputHelper.toStack(itemInput4), InputHelper.toStack(itemOutput));
    }

    @ZenMethod
    public static void addRecipe(IItemStack itemInput1, IItemStack itemInput2, IItemStack itemInput3, IItemStack itemInput4, IItemStack itemOutput, int hammerAmount) {
        ForgeAnvilRecipe.addRecipe(InputHelper.toStack(itemInput1), InputHelper.toStack(itemInput2), InputHelper.toStack(itemInput3), InputHelper.toStack(itemInput4), InputHelper.toStack(itemOutput), hammerAmount);
    }
}
