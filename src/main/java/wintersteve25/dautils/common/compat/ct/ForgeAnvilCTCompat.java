package wintersteve25.dautils.common.compat.ct;

import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wintersteve25.dautils.common.crafting.ForgeAnvilRecipe;
import wintersteve25.dautils.common.crafting.ForgeAnvilSharpenRecipe;

@ZenRegister
@ZenClass("mods.dautils.ForgeAnvil")
public class ForgeAnvilCTCompat {
    @ZenMethod
    public static void addRecipe(IItemStack itemInput1, IItemStack itemInput2, IItemStack itemInput3, IItemStack itemInput4, IItemStack itemOutput) {
        CraftTweakerAPI.logInfo("Adding a Forge Anvil Recipe");
        ForgeAnvilRecipe.addRecipe(InputHelper.toStack(itemInput1), InputHelper.toStack(itemInput2), InputHelper.toStack(itemInput3), InputHelper.toStack(itemInput4), InputHelper.toStack(itemOutput));
    }

    @ZenMethod
    public static void addRecipe(IItemStack itemInput1, IItemStack itemInput2, IItemStack itemInput3, IItemStack itemInput4, IItemStack itemOutput, int hammerAmount) {
        CraftTweakerAPI.logInfo("Adding a Forge Anvil Recipe");
        ForgeAnvilRecipe.addRecipe(InputHelper.toStack(itemInput1), InputHelper.toStack(itemInput2), InputHelper.toStack(itemInput3), InputHelper.toStack(itemInput4), InputHelper.toStack(itemOutput), hammerAmount);
    }

    //TO TEST
    @ZenMethod
    public static void removeRecipe(IItemStack itemInput1, IItemStack itemInput2, IItemStack itemInput3, IItemStack itemInput4) {
        CraftTweakerAPI.logInfo("Removing a Forge Anvil Recipe");
        ForgeAnvilRecipe.removeRecipe(InputHelper.toStack(itemInput1), InputHelper.toStack(itemInput2), InputHelper.toStack(itemInput3), InputHelper.toStack(itemInput4));
    }

    @ZenMethod
    public static void addSharpeningRecipe(IItemStack itemInput, IItemStack itemOutput, int sharpeningAmount) {
        CraftTweakerAPI.logInfo("Adding a Forge Anvil Sharpening Recipe");
        ForgeAnvilSharpenRecipe.addRecipe(InputHelper.toStack(itemInput), InputHelper.toStack(itemOutput), sharpeningAmount);
    }

    @ZenMethod
    public static void addSharpeningRecipe(IItemStack itemInput, IItemStack itemOutput) {
        CraftTweakerAPI.logInfo("Adding a Forge Anvil Sharpening Recipe");
        ForgeAnvilSharpenRecipe.addRecipe(InputHelper.toStack(itemInput), InputHelper.toStack(itemOutput));
    }

    //TO TEST
    @ZenMethod
    public static void removeSharpeningRecipe(IItemStack itemInput) {
        CraftTweakerAPI.logInfo("Removing a Forge Anvil Sharpening Recipe");
        ForgeAnvilSharpenRecipe.removeRecipe(InputHelper.toStack(itemInput));
    }
}
