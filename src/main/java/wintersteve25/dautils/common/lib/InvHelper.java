package wintersteve25.dautils.common.lib;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import wintersteve25.dautils.common.blocks.machines.DABaseItemInventoryTile;

/**
 * The codes in this class is taken from Botania 1.12.2 (InventoryHelper.java) by Vazkii
 * Full credits to him
 * However it is slightly modified
 *
 * Download his mod Botania here: https://www.curseforge.com/minecraft/mc-mods/botania
 */
public class InvHelper {
    public static IItemHandler getInventory(World world, BlockPos pos, EnumFacing side) {
        TileEntity te = world.getTileEntity(pos);

        if(te == null)
            return null;

        IItemHandler ret = te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side) ?
                te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side) : null;

        if(ret == null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
            ret = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        return ret;
    }

    public static void dropInventory(DABaseItemInventoryTile inv, World world, IBlockState state, BlockPos pos, int inventorySize) {
        if(inv != null) {
            for(int i = 0; i < inventorySize; i++) {
                ItemStack itemstack = inv.getItemHandler().getStackInSlot(i);

                if(!itemstack.isEmpty()) {
                    net.minecraft.inventory.InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemstack);
                }
            }

            world.updateComparatorOutputLevel(pos, state.getBlock());
        }
    }

    public static void withdrawFromInventory(DABaseItemInventoryTile inv, EntityPlayer player, int inventorySize) {
        for(int i = 0; i < inventorySize; i++) {
            ItemStack stackAt = inv.getItemHandler().getStackInSlot(i);
            if(!stackAt.isEmpty()) {
                ItemStack copy = stackAt.copy();
                ItemHandlerHelper.giveItemToPlayer(player, copy);
                inv.getItemHandler().extractItem(i, copy.getCount(), false);
                player.world.updateComparatorOutputLevel(inv.getPos(), null);
                break;
            }
        }
    }

}
