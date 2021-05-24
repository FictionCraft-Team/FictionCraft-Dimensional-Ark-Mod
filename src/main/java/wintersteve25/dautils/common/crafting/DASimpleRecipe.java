package wintersteve25.dautils.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public abstract class DASimpleRecipe {

    //Originally taken from primal tech WoodenBasinRecipe.java, by vadis365 with permission, full credits to them. Download PrimalTech here:https://www.curseforge.com/minecraft/mc-mods/primal-tech
    public static boolean areStacksTheSame(ItemStack stack1, ItemStack stack2, boolean matchSize) {
        if (stack1.isEmpty() && !stack2.isEmpty() || !stack1.isEmpty() && stack2.isEmpty())
            return false;

        if (stack1.getItem() == stack2.getItem())
            if (stack1.getItemDamage() == stack2.getItemDamage() || isWildcard(stack1.getItemDamage()) || isWildcard(stack2.getItemDamage()))
                if (!matchSize || stack1.getCount() == stack2.getCount()) {
                    if (stack1.hasTagCompound() && stack2.hasTagCompound())
                        return stack1.getTagCompound().equals(stack2.getTagCompound());
                    return stack1.hasTagCompound() == stack2.hasTagCompound();
                }
        return false;
    }

    private static boolean isWildcard(int meta) {
        return meta == OreDictionary.WILDCARD_VALUE;
    }
}
