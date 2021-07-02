package wintersteve25.dautils.common.compat.jei.sacrificer_jei;

import mezz.jei.api.IModRegistry;
import net.minecraft.item.ItemStack;
import wintersteve25.dautils.common.blocks.DABlockObjectHolders;
import wintersteve25.dautils.common.compat.jei.DAJEIPlugin;
import wintersteve25.dautils.common.crafting.ForgeAnvilSharpenRecipe;
import wintersteve25.dautils.common.crafting.SacrificerRecipe;
import wintersteve25.dautils.common.crafting.recipes.ForgeAnvilSharpenRecipes;
import wintersteve25.dautils.common.crafting.recipes.SacrificerRecipes;
import wintersteve25.dautils.common.item.DAItems;

import javax.annotation.Nonnull;

public class JEISacrificerHandler {
    public static void handler(@Nonnull IModRegistry registry) {
        registry.addRecipeCatalyst(new ItemStack(DABlockObjectHolders.sacrificer), DAJEIPlugin.SACRIFICERPLUGINID);
        registry.addRecipes(SacrificerRecipes.getRecipeList(), DAJEIPlugin.SACRIFICERPLUGINID);
        registry.handleRecipes(SacrificerRecipe.class, JEISacrificerWrapper::new, DAJEIPlugin.SACRIFICERPLUGINID);
    }
}
