package wintersteve25.dautils.common.compat.jei.forge_anvil_sharpen_jei;

import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import wintersteve25.dautils.common.crafting.ForgeAnvilRecipe;
import wintersteve25.dautils.common.crafting.ForgeAnvilSharpenRecipe;

import javax.annotation.Nonnull;
import java.util.List;

@SuppressWarnings("deprecation")
public class JEISharpenWrapper implements IRecipeWrapper {
    private final ItemStack itemInput;
    private final ItemStack itemOutput;
    private final int sharpenAmount;

    public JEISharpenWrapper(ForgeAnvilSharpenRecipe recipe) {
        this.itemInput = recipe.getItemInput().copy();
        this.itemOutput = recipe.getItemOutput().copy();
        this.sharpenAmount = recipe.getSharpenAmount();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (!itemInput.isEmpty()) {
            ingredients.setInput(ItemStack.class, itemInput);
        }
        if (!itemOutput.isEmpty()) {
            ingredients.setOutput(ItemStack.class, itemOutput);
        }
    }

    @Nonnull
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        List<String> tooltip = Lists.newArrayList();
        if (mouseX >= 30 && mouseY >= 10 && mouseX <= 57 && mouseY <= 21) {
            tooltip.add(I18n.translateToLocalFormatted("jei.dautils.forge_anvil.sharpenAmount", sharpenAmount-1));
        }
        return tooltip;
    }

    public ItemStack getItemInput() {
        return itemInput;
    }
    public ItemStack getItemOutput() {
        return itemOutput;
    }
}
