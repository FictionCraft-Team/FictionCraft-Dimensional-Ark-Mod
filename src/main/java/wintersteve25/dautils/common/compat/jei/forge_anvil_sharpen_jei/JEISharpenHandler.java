package wintersteve25.dautils.common.compat.jei.forge_anvil_sharpen_jei;

import mezz.jei.api.IModRegistry;
import net.minecraft.item.ItemStack;
import wintersteve25.dautils.common.blocks.DABlockObjectHolders;
import wintersteve25.dautils.common.compat.jei.DAJEIPlugin;
import wintersteve25.dautils.common.crafting.ForgeAnvilRecipe;
import wintersteve25.dautils.common.crafting.ForgeAnvilSharpenRecipe;
import wintersteve25.dautils.common.crafting.recipes.ForgeAnvilRecipes;
import wintersteve25.dautils.common.crafting.recipes.ForgeAnvilSharpenRecipes;
import wintersteve25.dautils.common.item.DAItems;

import javax.annotation.Nonnull;

public class JEISharpenHandler {
    public static void handler(@Nonnull IModRegistry registry) {
        registry.addRecipeCatalyst(new ItemStack(DABlockObjectHolders.forgeAnvil), DAJEIPlugin.SHARPENINGPLUGINID);
        registry.addRecipeCatalyst(new ItemStack(DAItems.SharpeningTool), DAJEIPlugin.SHARPENINGPLUGINID);
        registry.addRecipes(ForgeAnvilSharpenRecipes.getRecipeList(), DAJEIPlugin.SHARPENINGPLUGINID);
        registry.handleRecipes(ForgeAnvilSharpenRecipe.class, JEISharpenWrapper::new, DAJEIPlugin.SHARPENINGPLUGINID);
    }
}
