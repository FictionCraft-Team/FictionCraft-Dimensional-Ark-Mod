package wintersteve25.dautils.common.compat.jei.forge_anvil_jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.Fluid;
import wintersteve25.dautils.DAUtils;
import wintersteve25.dautils.common.compat.jei.DAJEIPlugin;

public class JEIForgeAnvilCategory implements IRecipeCategory<JEIForgeAnvilWrapper> {

    private final IDrawable background;

    public JEIForgeAnvilCategory(IGuiHelper iGuiHelper) {
        ResourceLocation bgRL = new ResourceLocation( "dautils:textures/gui/anvil_jei.png");
        background = iGuiHelper.createDrawable(bgRL, 0, 0, 130, 50, 147, 166);
    }

    @Override
    public String getUid() {
        return DAJEIPlugin.FORGEANVILPLUGINID;
    }

    @Override
    public String getTitle() {
        return I18n.translateToLocal("jei.dautils.forge_anvil");
    }

    @Override
    public String getModName() {
        return DAUtils.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, JEIForgeAnvilWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();

        guiItemStackGroup.init(0, true, 1, 29);
        guiItemStackGroup.set(0, recipeWrapper.getItemInput1());

        guiItemStackGroup.init(1, true, 19, 20);
        guiItemStackGroup.set(1, recipeWrapper.getItemInput2());

        guiItemStackGroup.init(2, true, 37, 11);
        guiItemStackGroup.set(2, recipeWrapper.getItemInput3());

        guiItemStackGroup.init(3, true, 55, 1);
        guiItemStackGroup.set(3, recipeWrapper.getItemInput4());

        guiItemStackGroup.init(4, false, 107, 18);
        guiItemStackGroup.set(4, recipeWrapper.getItemOutput());
    }
}
