package wintersteve25.dautils.common.crafting;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ForgeAnvilRecipe extends DASimpleRecipe{
    private ItemStack itemInput1;
    private ItemStack itemInput2;
    private ItemStack itemInput3;
    private ItemStack itemInput4;
    private ItemStack itemOutput;
    private int hammerAmounts;

    public static final List<ForgeAnvilRecipe> forgeAnvilRecipes = new ArrayList<ForgeAnvilRecipe>();

    public ForgeAnvilRecipe(ItemStack itemInput1, ItemStack itemInput2, ItemStack itemInput3, ItemStack itemInput4, ItemStack itemOutput, int hammerAmounts) {
        this.itemInput1 = itemInput1;
        this.itemInput2 = itemInput2;
        this.itemInput3 = itemInput3;
        this.itemInput4 = itemInput4;
        this.itemOutput = itemOutput;
        this.hammerAmounts = hammerAmounts;

        if (itemInput1 == null || itemInput2 == null || itemInput3 == null || itemInput4 == null) {
            throw new IllegalArgumentException("Item inputs can not be null!");
        }

        if (hammerAmounts == 0) {
            throw new IllegalArgumentException("Hammer amount can not be 0");
        }
    }

    public ForgeAnvilRecipe(ItemStack itemInput1, ItemStack itemInput2, ItemStack itemInput3, ItemStack itemInput4, ItemStack itemOutput) {
        this.itemInput1 = itemInput1;
        this.itemInput2 = itemInput2;
        this.itemInput3 = itemInput3;
        this.itemInput4 = itemInput4;
        this.itemOutput = itemOutput;
        this.hammerAmounts = 6;

        if (itemInput1 == null || itemInput2 == null || itemInput3 == null || itemInput4 == null) {
            throw new IllegalArgumentException("Item inputs can not be null!");
        }
    }

    public boolean isRecipeMatch(ItemStack itemInput1, ItemStack itemInput2, ItemStack itemInput3, ItemStack itemInput4, boolean matchSize) {
        if(areStacksTheSame(itemInput1, getItemInput1(), matchSize) && areStacksTheSame(itemInput2, getItemInput2(), matchSize) && areStacksTheSame(itemInput3, getItemInput3(), matchSize) && areStacksTheSame(itemInput4, getItemInput4(), matchSize)) {
            return true;
        }
        return false;
    }

    public static ForgeAnvilRecipe addRecipe(ItemStack itemInput1, ItemStack itemInput2, ItemStack itemInput3, ItemStack itemInput4, ItemStack itemOutput, int hammerAmounts) {
        ForgeAnvilRecipe recipe = new ForgeAnvilRecipe(itemInput1, itemInput2, itemInput3, itemInput4, itemOutput, hammerAmounts);
        forgeAnvilRecipes.add(recipe);
        return recipe;
    }

    public static ForgeAnvilRecipe addRecipe(ItemStack itemInput1, ItemStack itemInput2, ItemStack itemInput3, ItemStack itemInput4, ItemStack itemOutput) {
        ForgeAnvilRecipe recipe = new ForgeAnvilRecipe(itemInput1, itemInput2, itemInput3, itemInput4, itemOutput);
        forgeAnvilRecipes.add(recipe);
        return recipe;
    }

    public static ForgeAnvilRecipe removeRecipe(ItemStack itemInput1, ItemStack itemInput2, ItemStack itemInput3, ItemStack itemInput4) {
        ForgeAnvilRecipe recipe = getRecipe(itemInput1, itemInput2, itemInput3, itemInput4);
        forgeAnvilRecipes.remove(recipe);
        return recipe;
    }

    public static ForgeAnvilRecipe getRecipe(ItemStack itemInput1, ItemStack itemInput2, ItemStack itemInput3, ItemStack itemInput4) {
        for (ForgeAnvilRecipe recipes : forgeAnvilRecipes) {
            if(recipes.isRecipeMatch(itemInput1, itemInput2, itemInput3, itemInput4, true)) {
                return recipes;
            }
        }
        return null;
    }

    public ItemStack getItemInput1() {
        return this.itemInput1;
    }
    public ItemStack getItemInput2() {
        return this.itemInput2;
    }
    public ItemStack getItemInput3() {
        return this.itemInput3;
    }
    public ItemStack getItemInput4() {
        return this.itemInput4;
    }
    public ItemStack getItemOutput() {
        return this.itemOutput;
    }
    public int getHammerAmounts() {
        return this.hammerAmounts;
    }
}
