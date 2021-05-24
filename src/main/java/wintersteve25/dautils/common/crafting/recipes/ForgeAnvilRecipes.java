package wintersteve25.dautils.common.crafting.recipes;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import wintersteve25.dautils.common.crafting.ForgeAnvilRecipe;

import java.util.List;

public class ForgeAnvilRecipes {
    public static boolean hasInited = false;



    public static ForgeAnvilRecipe recipeTest;



    public static List<ForgeAnvilRecipe> getRecipeList() {
        if (!hasInited) {
            init();
            hasInited = true;
        }
        return ForgeAnvilRecipe.forgeAnvilRecipes;
    }

    public static void init() {
        recipeTest = ForgeAnvilRecipe.addRecipe(new ItemStack(Items.APPLE), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_CHESTPLATE), new ItemStack(Items.DIAMOND), new ItemStack(Items.DIAMOND_AXE), 5);
    }
}
