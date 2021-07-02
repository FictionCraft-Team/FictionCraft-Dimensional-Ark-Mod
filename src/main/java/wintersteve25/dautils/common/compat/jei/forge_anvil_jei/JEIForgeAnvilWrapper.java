package wintersteve25.dautils.common.compat.jei.forge_anvil_jei;

import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.FluidStack;
import wintersteve25.dautils.common.crafting.ForgeAnvilRecipe;

import javax.annotation.Nonnull;
import java.util.List;

@SuppressWarnings("deprecation")
public class JEIForgeAnvilWrapper implements IRecipeWrapper {
    private final ItemStack itemInput1;
    private final ItemStack itemInput2;
    private final ItemStack itemInput3;
    private final ItemStack itemInput4;
    private final ItemStack itemOutput;
    private final int hammerAmount;

    public JEIForgeAnvilWrapper(ForgeAnvilRecipe recipe) {
        this.itemInput1 = recipe.getItemInput1().copy();
        this.itemInput2 = recipe.getItemInput2().copy();
        this.itemInput3 = recipe.getItemInput3().copy();
        this.itemInput4 = recipe.getItemInput4().copy();
        this.itemOutput = recipe.getItemOutput().copy();
        this.hammerAmount = recipe.getHammerAmounts();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (!itemInput1.isEmpty()) {
            ingredients.setInput(ItemStack.class, itemInput1);
        }
        if (!itemInput2.isEmpty()) {
            ingredients.setInput(ItemStack.class, itemInput2);
        }
        if (!itemInput3.isEmpty()) {
            ingredients.setInput(ItemStack.class, itemInput3);
        }
        if (!itemInput4.isEmpty()) {
            ingredients.setInput(ItemStack.class, itemInput4);
        }
        if (!itemOutput.isEmpty()) {
            ingredients.setOutput(ItemStack.class, itemOutput);
        }
    }

    @Nonnull
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        List<String> tooltip = Lists.newArrayList();
        if (mouseX >= 74 && mouseY >= 14 && mouseX <= 96 && mouseY <= 37) {
            tooltip.add(I18n.translateToLocalFormatted("jei.dautils.forge_anvil.hammerAmount", hammerAmount-1));
        }
        return tooltip;
    }

    public ItemStack getItemInput1() {
        return itemInput1;
    }
    public ItemStack getItemInput2() {
        return itemInput2;
    }
    public ItemStack getItemInput3() {
        return itemInput3;
    }
    public ItemStack getItemInput4() {
        return itemInput4;
    }
    public ItemStack getItemOutput() {
        return itemOutput;
    }
}
