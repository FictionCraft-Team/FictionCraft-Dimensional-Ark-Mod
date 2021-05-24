package wintersteve25.dautils.common.compat.ct;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wintersteve25.dautils.common.crafting.ForgeAnvilRecipe;

@ZenRegister
@ZenClass("mods.dautils.utils.ForgeAnvil")
public class ForgeAnvilCTCompat {
    @ZenMethod
    public static void addRecipe(ItemStack itemInput1, ItemStack itemInput2, ItemStack itemInput3, ItemStack itemInput4, ItemStack itemOutput) {
        ForgeAnvilRecipe.addRecipe(itemInput1, itemInput2, itemInput3, itemInput4, itemOutput);
    }

    @ZenMethod
    public static void addRecipe(ItemStack itemInput1, ItemStack itemInput2, ItemStack itemInput3, ItemStack itemInput4, ItemStack itemOutput, int hammerAmount) {
        ForgeAnvilRecipe.addRecipe(itemInput1, itemInput2, itemInput3, itemInput4, itemOutput, hammerAmount);
    }

    @ZenMethod
    public static void removeRecipe(ItemStack itemInput1, ItemStack itemInput2, ItemStack itemInput3, ItemStack itemInput4) {
        ForgeAnvilRecipe.removeRecipe(itemInput1, itemInput2, itemInput3, itemInput4);
    }
}
