package wintersteve25.dautils.common.crafting;

import net.minecraft.item.ItemStack;
import wintersteve25.dautils.client.renderers.SacrificerTESR;

import java.util.ArrayList;
import java.util.List;

public class SacrificerRecipe extends DASimpleRecipe{
    private ItemStack itemInput;
    private int blood;
    private int time;

    public static final List<SacrificerRecipe> sacrificerRecipe = new ArrayList<>();

    public SacrificerRecipe(ItemStack itemInput, int blood, int time) {
        this.itemInput = itemInput;
        this.blood = blood;
        this.time = time;

        if (itemInput == null) {
            throw new IllegalArgumentException("Item input can not be null!");
        }
    }

    public boolean isRecipeMatch(ItemStack itemInput, boolean matchSize) {
        return areStacksTheSame(itemInput, getItemInput(), matchSize);
    }

    public static SacrificerRecipe addRecipe(ItemStack itemInput, int blood, int time) {
        SacrificerRecipe recipe = new SacrificerRecipe(itemInput, blood, time);
        sacrificerRecipe.add(recipe);
        return recipe;
    }

    public static SacrificerRecipe removeRecipe(ItemStack itemInput1) {
        SacrificerRecipe recipe = getRecipe(itemInput1);
        sacrificerRecipe.remove(recipe);
        return recipe;
    }

    public static SacrificerRecipe getRecipe(ItemStack itemInput1) {
        for (SacrificerRecipe recipes : sacrificerRecipe) {
            if(recipes.isRecipeMatch(itemInput1, true)) {
                return recipes;
            }
        }
        return null;
    }

    public ItemStack getItemInput() {
        return this.itemInput;
    }
    public int getBlood() {
        return this.blood;
    }
    public int getTime() {
        return time;
    }
}
