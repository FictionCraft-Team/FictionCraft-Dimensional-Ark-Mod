package wintersteve25.dautils.common.compat.jei.smeltery_jei;

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

public class JEISmelteryCategory implements IRecipeCategory<JEISmelteryWrapper> {

    private final IDrawable background;
    private final IDrawable tankOverlay;

    public JEISmelteryCategory(IGuiHelper iGuiHelper) {
        ResourceLocation bgRL = new ResourceLocation( "dautils:textures/gui/smeltery_jei.png");
        background = iGuiHelper.createDrawable(bgRL, 0, 0, 116, 69, 147, 166);

        ResourceLocation tankRL = new ResourceLocation( "dautils:textures/gui/fluid_bar_four.png");
        tankOverlay = iGuiHelper.createDrawable(tankRL, 0, 0, 18, 64, 18, 64);
    }

    @Override
    public String getUid() {
        return DAJEIPlugin.SMELTERYPLUGINID;
    }

    @Override
    public String getTitle() {
        return I18n.translateToLocal("jei.dautils.smeltery");
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
    public void setRecipe(IRecipeLayout recipeLayout, JEISmelteryWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStackGroup = recipeLayout.getFluidStacks();

        guiItemStackGroup.init(0, true, 3, 24);
        guiItemStackGroup.set(0, recipeWrapper.getItemInput());

        guiFluidStackGroup.init(0, false, 94, 3, 19, 63,Fluid.BUCKET_VOLUME*4, true, null);
        guiFluidStackGroup.set(0, recipeWrapper.getFluidOutput());
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        tankOverlay.draw(minecraft, 93, 2);
    }
}
