package wintersteve25.dautils.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.oredict.OreDictionary;
import wintersteve25.dautils.DAUtils;

import java.util.ArrayList;
import java.util.List;

public class SmelteryRecipe extends DASimpleRecipe{
    private FluidStack output;
    private Object itemInput;
    private int requiredOrbTier;
    private int processTime;

    public static final List<SmelteryRecipe> smelteryRecipes = new ArrayList<SmelteryRecipe>();

    public SmelteryRecipe(Object itemInput, FluidStack output, int requiredOrbTier, int processTime) {
        this.output = output;
        this.requiredOrbTier = requiredOrbTier;
        this.processTime = processTime;

        if (itemInput == null) {
            throw new IllegalArgumentException("Item input can not be null!");
        }
        if (itemInput instanceof ItemStack) {
            this.itemInput = ((ItemStack) itemInput).copy();
        } else if (itemInput instanceof String) {
            this.itemInput = OreDictionary.getOres((String) itemInput);
        } else {
            throw new IllegalArgumentException("Invalid item input, must be an ore dictionary or itemstack");
        }
    }
    public boolean isRecipeMatch(Object itemInput, int requiredOrbTier) {
        DAUtils.logger.info("check orb tier");
        if (requiredOrbTier < getRequiredOrbTier()) {
            return false;
        }

        DAUtils.logger.info("is stacks same");
        if (itemInput instanceof ItemStack) {
            ItemStack itemStack = ((ItemStack) itemInput);
            return areStacksTheSame((ItemStack) (getItemInput()), itemStack, false);
        } else if (itemInput instanceof String) {
            List<ItemStack> list = OreDictionary.getOres((String) itemInput);
            for (ItemStack stack : list) {
                return areStacksTheSame((ItemStack) (getItemInput()), stack, false);
            }
        }
        return false;
    }
    public boolean isRecipeMatch(Object itemInput) {
        if (itemInput == null || !itemInput.equals(getItemInput())) {
            return false;
        }
        if (itemInput instanceof ItemStack) {
            ItemStack itemStack = ((ItemStack) itemInput);
            return areStacksTheSame((ItemStack) (getItemInput()), itemStack, false);
        } else if (itemInput instanceof String) {
            List<ItemStack> list = OreDictionary.getOres((String) itemInput);
            for (ItemStack stack : list) {
                return areStacksTheSame((ItemStack) (getItemInput()), stack, false);
            }
        }
        return false;
    }

    public static SmelteryRecipe addRecipe(Object inputItem, FluidStack outputFluid, int requiredOrbTier, int processTime) {
        SmelteryRecipe recipe = new SmelteryRecipe(inputItem, outputFluid, requiredOrbTier, processTime);
        smelteryRecipes.add(recipe);
        return recipe;
    }

    public static void removeRecipe(ItemStack item) {
        SmelteryRecipe recipeToRemove = getRecipe(item);
        smelteryRecipes.remove(recipeToRemove);
    }

    public static SmelteryRecipe getRecipe(Object item, int requiredOrbTier) {
        for (SmelteryRecipe recipes : smelteryRecipes) {
            if(recipes.isRecipeMatch(item, requiredOrbTier)) {
                return recipes;
            }
        }
        return null;
    }

    public static SmelteryRecipe getRecipe(Object item) {
        for (SmelteryRecipe recipes : smelteryRecipes) {
            if(recipes.isRecipeMatch(item)) {
                return recipes;
            }
        }
        return null;
    }


    public Object getItemInput() {
        return itemInput;
    }

    public FluidStack getFluidOutput() {
        return output;
    }

    public int getRequiredOrbTier() {
        return requiredOrbTier;
    }

    public int getProcessTime() {
        return processTime;
    }
}
