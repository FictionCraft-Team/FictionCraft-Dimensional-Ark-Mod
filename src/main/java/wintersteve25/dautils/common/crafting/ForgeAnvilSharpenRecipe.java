package wintersteve25.dautils.common.crafting;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ForgeAnvilSharpenRecipe extends DASimpleRecipe{
    private ItemStack itemInput;
    private ItemStack itemOutput;
    private int sharpenAmount;

    public static final List<ForgeAnvilSharpenRecipe> sharpenRecipes = new ArrayList<ForgeAnvilSharpenRecipe>();

    public ForgeAnvilSharpenRecipe(ItemStack itemInput, ItemStack itemOutput, int sharpenAmount) {
        this.itemInput = itemInput;
        this.itemOutput = itemOutput;
        this.sharpenAmount = sharpenAmount;
    }

    public ForgeAnvilSharpenRecipe(ItemStack itemInput, ItemStack itemOutput) {
        this.itemInput = itemInput;
        this.itemOutput = itemOutput;
        this.sharpenAmount = 6;
    }

    public boolean isRecipeMatch(ItemStack itemInput, boolean matchSize) {
        if(areStacksTheSame(itemInput, getItemInput(), matchSize)) {
            return true;
        }
        return false;
    }

    public static ForgeAnvilSharpenRecipe addRecipe(ItemStack itemInput, ItemStack itemOutput, int sharpenAmount) {
        ForgeAnvilSharpenRecipe recipe = new ForgeAnvilSharpenRecipe(itemInput, itemOutput, sharpenAmount);
        sharpenRecipes.add(recipe);
        return recipe;
    }

    public static ForgeAnvilSharpenRecipe addRecipe(ItemStack itemInput, ItemStack itemOutput) {
        ForgeAnvilSharpenRecipe recipe = new ForgeAnvilSharpenRecipe(itemInput, itemOutput);
        sharpenRecipes.add(recipe);
        return recipe;
    }

    public static ForgeAnvilSharpenRecipe removeRecipe(ItemStack itemInput) {
        ForgeAnvilSharpenRecipe recipe = getRecipe(itemInput);
        sharpenRecipes.remove(recipe);
        return recipe;
    }

    public static ForgeAnvilSharpenRecipe getRecipe(ItemStack itemInput) {
        for (ForgeAnvilSharpenRecipe recipes : sharpenRecipes) {
            if(recipes.isRecipeMatch(itemInput, true)) {
                return recipes;
            }
        }
        return null;
    }

    public ItemStack getItemInput() {
        return this.itemInput;
    }
    public ItemStack getItemOutput() {
        return this.itemOutput;
    }
    public int getSharpenAmount() {
        return this.sharpenAmount;
    }
}
