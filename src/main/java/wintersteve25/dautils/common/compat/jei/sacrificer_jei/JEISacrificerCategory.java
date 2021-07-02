package wintersteve25.dautils.common.compat.jei.sacrificer_jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import wintersteve25.dautils.DAUtils;
import wintersteve25.dautils.common.compat.jei.DAJEIPlugin;

public class JEISacrificerCategory implements IRecipeCategory<JEISacrificerWrapper> {

    private final IDrawable background;

    public JEISacrificerCategory(IGuiHelper iGuiHelper) {
        ResourceLocation bgRL = new ResourceLocation( "dautils:textures/gui/sacrificer_jei.png");
        background = iGuiHelper.createDrawable(bgRL, 0, 0, 65, 32, 147, 166);
    }

    @Override
    public String getUid() {
        return DAJEIPlugin.SACRIFICERPLUGINID;
    }

    @Override
    public String getTitle() {
        return I18n.translateToLocal("jei.dautils.sacrificer");
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
    public void setRecipe(IRecipeLayout recipeLayout, JEISacrificerWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();

        guiItemStackGroup.init(0, true, 8, 7);
        guiItemStackGroup.set(0, recipeWrapper.getItemInput());
    }
}
