package wintersteve25.dautils.common.compat.jei.forge_anvil_jei;

import mezz.jei.api.IModRegistry;
import net.minecraft.item.ItemStack;
import wintersteve25.dautils.common.blocks.DABlockObjectHolders;
import wintersteve25.dautils.common.compat.jei.DAJEIPlugin;
import wintersteve25.dautils.common.crafting.ForgeAnvilRecipe;
import wintersteve25.dautils.common.crafting.recipes.ForgeAnvilRecipes;
import wintersteve25.dautils.common.item.DAItems;

import javax.annotation.Nonnull;

public class JEIForgeAnvilHandler {
    public static void handler(@Nonnull IModRegistry registry) {
        registry.addRecipeCatalyst(new ItemStack(DABlockObjectHolders.forgeAnvil), DAJEIPlugin.FORGEANVILPLUGINID);
        registry.addRecipeCatalyst(new ItemStack(DAItems.Hammer), DAJEIPlugin.FORGEANVILPLUGINID);
        registry.addRecipes(ForgeAnvilRecipes.getRecipeList(), DAJEIPlugin.FORGEANVILPLUGINID);
        registry.handleRecipes(ForgeAnvilRecipe.class, JEIForgeAnvilWrapper::new, DAJEIPlugin.FORGEANVILPLUGINID);
    }
}
