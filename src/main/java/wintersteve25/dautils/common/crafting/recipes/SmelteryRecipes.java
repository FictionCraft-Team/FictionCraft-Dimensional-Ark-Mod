package wintersteve25.dautils.common.crafting.recipes;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import wintersteve25.dautils.common.crafting.SmelteryRecipe;

import java.util.List;

public class SmelteryRecipes {
    public static boolean hasInited = false;

    //public static SmelteryRecipe recipeTest;
    
    public static List<SmelteryRecipe> getRecipeList() {
        if (!hasInited) {
            init();
            hasInited = true;
        }
        return SmelteryRecipe.smelteryRecipes;
    }

    public static void init() {
        //recipeTest = SmelteryRecipe.addRecipe(new ItemStack(Items.IRON_INGOT), new FluidStack(FluidRegistry.WATER, 100), 0, 40);
    }

}
