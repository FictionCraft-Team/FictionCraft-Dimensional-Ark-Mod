package wintersteve25.dautils.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class SmelteryRecipe extends DASimpleRecipe{
    private FluidStack output;
    private Object itemInput;
    private int requiredOrbTier;
    private int processTime;

    public static final List<SmelteryRecipe> smelteryRecipes = new ArrayList<>();

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
        if (requiredOrbTier < getRequiredOrbTier()) {
            return false;
        }
        if (itemInput instanceof ItemStack) {
            ItemStack itemStack = ((ItemStack) itemInput);
            if (getItemInput() instanceof ItemStack) {
                return areStacksTheSame((ItemStack) (getItemInput()), itemStack, false);
            }
            if (getItemInput() instanceof List) {
                List<ItemStack> stackList = (List<ItemStack>) getItemInput();
                for (ItemStack stack : stackList) {
                    return areStacksTheSame(stack, itemStack, false);
                }
            }
        } else if (itemInput instanceof String) {
            List<ItemStack> list = OreDictionary.getOres((String) itemInput);
            for (ItemStack stack : list) {
                if (getItemInput() instanceof ItemStack) {
                    return areStacksTheSame((ItemStack) (getItemInput()), stack, false);
                }
                if (getItemInput() instanceof List) {
                    List<ItemStack> stackList = (List<ItemStack>) getItemInput();
                    for (ItemStack inputs : stackList) {
                        return areStacksTheSame(inputs, stack, false);
                    }
                }
            }
        }
        return false;
    }

    public boolean isRecipeMatch(Object itemInput, FluidStack fluidOutput) {
        if (fluidOutput.isFluidEqual(getFluidOutput())) {
            if (itemInput instanceof ItemStack) {
                ItemStack itemStack = ((ItemStack) itemInput);
                if (getItemInput() instanceof ItemStack) {
                    return areStacksTheSame((ItemStack) (getItemInput()), itemStack, false);
                }
                if (getItemInput() instanceof List) {
                    List<ItemStack> stackList = (List<ItemStack>) getItemInput();
                    for (ItemStack stack : stackList) {
                        return areStacksTheSame(stack, itemStack, false);
                    }
                }
            } else if (itemInput instanceof String) {
                List<ItemStack> list = OreDictionary.getOres((String) itemInput);
                for (ItemStack stack : list) {
                    if (getItemInput() instanceof ItemStack) {
                        return areStacksTheSame((ItemStack) (getItemInput()), stack, false);
                    }
                    if (getItemInput() instanceof List) {
                        List<ItemStack> stackList = (List<ItemStack>) getItemInput();
                        for (ItemStack inputs : stackList) {
                            return areStacksTheSame(inputs, stack, false);
                        }
                    }
                }
            }
        }
        return false;
    }

    public static SmelteryRecipe addRecipe(ItemStack inputItem, FluidStack outputFluid, int requiredOrbTier, int processTime) {
        SmelteryRecipe recipe = new SmelteryRecipe(inputItem, outputFluid, requiredOrbTier, processTime);
        smelteryRecipes.add(recipe);
        return recipe;
    }

    public static SmelteryRecipe addRecipe(String inputItem, FluidStack outputFluid, int requiredOrbTier, int processTime) {
        SmelteryRecipe recipe = new SmelteryRecipe(inputItem, outputFluid, requiredOrbTier, processTime);
        smelteryRecipes.add(recipe);
        return recipe;
    }

    public static SmelteryRecipe removeRecipe(ItemStack itemInput, FluidStack fluidOutput) {
        SmelteryRecipe recipe = getRecipe(itemInput, fluidOutput);
        smelteryRecipes.remove(recipe);
        return recipe;
    }

    public static SmelteryRecipe removeRecipe(String itemInput, FluidStack fluidOutput) {
        SmelteryRecipe recipe = getRecipe(itemInput, fluidOutput);
        smelteryRecipes.remove(recipe);
        return recipe;
    }

    public static SmelteryRecipe getRecipe(Object item, int requiredOrbTier) {
        for (SmelteryRecipe recipes : smelteryRecipes) {
            if(recipes.isRecipeMatch(item, requiredOrbTier)) {
                return recipes;
            }
        }
        return null;
    }

    public static SmelteryRecipe getRecipe(Object item, FluidStack output) {
        for (SmelteryRecipe recipes : smelteryRecipes) {
            if(recipes.isRecipeMatch(item, output)) {
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
