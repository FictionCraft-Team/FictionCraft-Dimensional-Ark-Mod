package wintersteve25.dautils.common.crafting.recipes;

import wintersteve25.dautils.common.crafting.SacrificerRecipe;

import java.util.List;

public class SacrificerRecipes {
    public static boolean hasInited = false;

    public static List<SacrificerRecipe> getRecipeList() {
        if (!hasInited) {
            init();
            hasInited = true;
        }
        return SacrificerRecipe.sacrificerRecipe;
    }

    public static void init() {
    }
}
