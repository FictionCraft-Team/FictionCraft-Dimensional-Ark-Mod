package wintersteve25.dautils.common.compat.jei.smeltery_jei;

import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.FluidStack;
import wintersteve25.dautils.common.crafting.ForgeAnvilRecipe;
import wintersteve25.dautils.common.crafting.SmelteryRecipe;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("deprecation")
public class JEISmelteryWrapper implements IRecipeWrapper {
    private final List<ItemStack> itemInput;
    private final int orbTier;
    private final int processTime;

    private final FluidStack output;

    public JEISmelteryWrapper(SmelteryRecipe recipe) {
        this.itemInput = Collections.singletonList(((ItemStack) recipe.getItemInput()).copy());
        this.orbTier = recipe.getRequiredOrbTier();
        this.processTime = recipe.getProcessTime();

        this.output = recipe.getFluidOutput();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (!itemInput.isEmpty()) {
            ingredients.setInput(ItemStack.class, itemInput);
        }
        if (output.getFluid() != null) {
            ingredients.setOutput(FluidStack.class, output);
        }
    }

    @Nonnull
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        List<String> tooltip = Lists.newArrayList();
        if (mouseX >= 49 && mouseY >= 1 && mouseX <= 60 && mouseY <= 12) {
            tooltip.add(I18n.translateToLocalFormatted("jei.dautils.smeltery.orbTier", orbTier));
        }

        if (mouseX >= 75 && mouseY >= 28 && mouseX <= 88 && mouseY <= 36) {
            tooltip.add(I18n.translateToLocalFormatted("jei.dautils.smeltery.processTime", processTime));
        }
        return tooltip;
    }

    public List<ItemStack> getItemInput() {
        return itemInput;
    }
    public int getOrbTier() {
        return orbTier;
    }
    public int getProcessTime() {
        return processTime;
    }
    public FluidStack getFluidOutput() {
        return output;
    }
}
