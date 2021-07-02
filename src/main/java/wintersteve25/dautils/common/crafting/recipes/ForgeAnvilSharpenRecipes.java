package wintersteve25.dautils.common.crafting.recipes;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import wintersteve25.dautils.common.crafting.ForgeAnvilRecipe;
import wintersteve25.dautils.common.crafting.ForgeAnvilSharpenRecipe;

import java.util.List;

public class ForgeAnvilSharpenRecipes {
    public static boolean hasInited = false;

    //public static ForgeAnvilSharpenRecipe recipeTest;

    public static List<ForgeAnvilSharpenRecipe> getRecipeList() {
        if (!hasInited) {
            init();
            hasInited = true;
        }
        return ForgeAnvilSharpenRecipe.sharpenRecipes;
    }

    public static void init() {
        //recipeTest = ForgeAnvilSharpenRecipe.addRecipe(new ItemStack(Items.APPLE), new ItemStack(Items.DIAMOND), 5);
    }
}
