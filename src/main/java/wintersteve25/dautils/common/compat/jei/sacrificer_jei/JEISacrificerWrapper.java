package wintersteve25.dautils.common.compat.jei.sacrificer_jei;

import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import wintersteve25.dautils.common.crafting.SacrificerRecipe;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.List;

@SuppressWarnings("deprecation")
public class JEISacrificerWrapper implements IRecipeWrapper {
    private final ItemStack itemInput;
    private final int blood;
    private final int time;
    private final String infoString;

    public JEISacrificerWrapper(SacrificerRecipe recipe) {
        this.itemInput = recipe.getItemInput().copy();
        this.blood = recipe.getBlood();
        this.time = recipe.getTime();
        infoString = I18n.translateToLocalFormatted("jei.dautils.sacrificer.info", blood);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (!itemInput.isEmpty()) {
            ingredients.setInput(ItemStack.class, itemInput);
        }
    }

    @Nonnull
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        List<String> tooltip = Lists.newArrayList();
        if (mouseX >= 31 && mouseY >= 11 && mouseX <= 44 && mouseY <= 19) {
            tooltip.add(I18n.translateToLocalFormatted("jei.dautils.sacrificer.time",  time));
        }
        return tooltip;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString(infoString, 35 - minecraft.fontRenderer.getStringWidth(infoString) / 2, 25, Color.gray.getRGB());
    }

    public ItemStack getItemInput() {
        return itemInput;
    }
}
