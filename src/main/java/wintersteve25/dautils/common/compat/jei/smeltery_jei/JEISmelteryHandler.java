package wintersteve25.dautils.common.compat.jei.smeltery_jei;

import mezz.jei.api.IModRegistry;
import net.minecraft.item.ItemStack;
import wintersteve25.dautils.common.blocks.DABlockObjectHolders;
import wintersteve25.dautils.common.compat.jei.DAJEIPlugin;
import wintersteve25.dautils.common.crafting.ForgeAnvilRecipe;
import wintersteve25.dautils.common.crafting.SmelteryRecipe;
import wintersteve25.dautils.common.crafting.recipes.ForgeAnvilRecipes;
import wintersteve25.dautils.common.crafting.recipes.SmelteryRecipes;
import wintersteve25.dautils.common.item.DAItems;

import javax.annotation.Nonnull;

public class JEISmelteryHandler {
    public static void handler(@Nonnull IModRegistry registry) {
        registry.addRecipeCatalyst(new ItemStack(DABlockObjectHolders.smeltery), DAJEIPlugin.SMELTERYPLUGINID);
        registry.addRecipeCatalyst(new ItemStack(DAItems.OrbsLava), DAJEIPlugin.SMELTERYPLUGINID);
        registry.addRecipeCatalyst(new ItemStack(DAItems.OrbsBlazing), DAJEIPlugin.SMELTERYPLUGINID);
        registry.addRecipeCatalyst(new ItemStack(DAItems.OrbsScorching), DAJEIPlugin.SMELTERYPLUGINID);
        registry.addRecipes(SmelteryRecipes.getRecipeList(), DAJEIPlugin.SMELTERYPLUGINID);
        registry.handleRecipes(SmelteryRecipe.class, JEISmelteryWrapper::new, DAJEIPlugin.SMELTERYPLUGINID);
    }
}
