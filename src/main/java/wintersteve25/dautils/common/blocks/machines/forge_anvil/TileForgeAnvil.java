package wintersteve25.dautils.common.blocks.machines.forge_anvil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import wintersteve25.dautils.common.blocks.machines.DABaseItemInventoryTile;

import javax.annotation.Nullable;

public class TileForgeAnvil extends DABaseItemInventoryTile implements ITickable {

    public static boolean isCrafting = false;
    public static int totalHammer = 0;
    public static int remainingHammer = 0;

    @Override
    public void update() {
        if (!world.isRemote) {

        }
    }

    @Override
    public boolean addItem(@Nullable EntityPlayer player, ItemStack heldItem, @Nullable EnumHand hand, boolean isCrafting) {
        for (int i = 0; i < getInvSize(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
            if (this.isCrafting) {
                return false;
            }
            if (itemHandler.getStackInSlot(i).isEmpty()) {
                ItemStack itemAdd = heldItem.copy();
                itemHandler.insertItem(i, itemAdd, false);
                if(player == null || !player.capabilities.isCreativeMode) {
                    heldItem.shrink(heldItem.getCount());
                }
                markDirty();
            }
        }
        return true;
    }

    @Override
    public int getInvSize() {
        return 4;
    }

    public boolean hasItem() {
        for (int i = 0; i < getInvSize(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
