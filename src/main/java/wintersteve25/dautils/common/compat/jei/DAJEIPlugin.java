package wintersteve25.dautils.common.compat.jei;

import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import wintersteve25.dautils.common.compat.jei.forge_anvil_jei.JEIForgeAnvilCategory;
import wintersteve25.dautils.common.compat.jei.forge_anvil_jei.JEIForgeAnvilHandler;

@JEIPlugin
public class DAJEIPlugin implements IModPlugin {
    public static final String FORGEANVILPLUGINID = "dautils.forge_anvil";

    @Override
    public void register(IModRegistry registry) {
        JEIForgeAnvilHandler.handler(registry);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers jeiHelper = registry.getJeiHelpers();
        IGuiHelper iGuiHelper = jeiHelper.getGuiHelper();
        registry.addRecipeCategories(new JEIForgeAnvilCategory(iGuiHelper));
    }
}
