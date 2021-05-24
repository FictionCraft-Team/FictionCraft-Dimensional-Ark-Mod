package wintersteve25.dautils.common.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wintersteve25.dautils.DAUtils;
import wintersteve25.dautils.common.proxy.GuiProxy;

public class ItemBloodNote extends Item{

    public ItemBloodNote() {
        setRegistryName("blood_note");
        setTranslationKey(DAUtils.MODID + ".blood_note");
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack heldItem = playerIn.getHeldItem(handIn);

        if (playerIn != null && worldIn.isRemote) {
            playerIn.openGui(DAUtils.instance, GuiProxy.BLOODNOTEGUIID, playerIn.getEntityWorld(), (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
            return ActionResult.newResult(EnumActionResult.SUCCESS, heldItem);
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, heldItem);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
