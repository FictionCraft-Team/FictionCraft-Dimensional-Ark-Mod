package wintersteve25.dautils.common.blocks.machines.forge_anvil;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import wintersteve25.dautils.DAUtils;
import wintersteve25.dautils.common.blocks.machines.DABaseItemInventoryTile;
import wintersteve25.dautils.common.crafting.ForgeAnvilRecipe;

import javax.annotation.Nullable;

public class TileForgeAnvil extends DABaseItemInventoryTile implements ITickable {

    private static boolean isCrafting = false;
    private static int totalHammer = 0;
    private static int remainingHammer = 0;

    @Override
    public void update() {
        if (!world.isRemote) {
            ItemStack itemStack1 = itemHandler.getStackInSlot(0);
            ItemStack itemStack2 = itemHandler.getStackInSlot(1);
            ItemStack itemStack3 = itemHandler.getStackInSlot(2);
            ItemStack itemStack4 = itemHandler.getStackInSlot(3);
            ForgeAnvilRecipe recipe = ForgeAnvilRecipe.getRecipe(itemStack1, itemStack2, itemStack3, itemStack4);
            if (recipe != null) {
                if (remainingHammer > 0) {
                    DAUtils.logger.info(remainingHammer);
                    isCrafting = true;
                    if (itemStack1.isEmpty() || itemStack2.isEmpty() || itemStack3.isEmpty() || itemStack4.isEmpty()) {
                        remainingHammer = 0;
                        totalHammer = 0;
                        isCrafting = false;
                        markDirty();
                    }
                    if (remainingHammer <= 1) {
                        DAUtils.logger.info("craft");
                        for (int i = 0; i < getInvSize(); i++) {
                            itemHandler.extractItem(i, 1, false);
                        }
                        spawnOutput(world, pos.getX(), pos.getY() + 1, pos.getZ(), recipe.getItemOutput());
                        isCrafting = false;
                        remainingHammer = 0;
                        totalHammer = 0;
                        markDirty();
                        return;
                    }
                } else {
                    totalHammer = recipe.getHammerAmounts();
                    remainingHammer = totalHammer;
                    markDirty();
                }
            }
        }
    }

    public void hammerOnce(ItemStack hammerItem, EntityPlayer player) {
        if (remainingHammer <= 0) {
            return;
        }

        if(!world.isRemote) {
            if (isCrafting) {
                ItemStack itemStack1 = itemHandler.getStackInSlot(0);
                ItemStack itemStack2 = itemHandler.getStackInSlot(1);
                ItemStack itemStack3 = itemHandler.getStackInSlot(2);
                ItemStack itemStack4 = itemHandler.getStackInSlot(3);
                ForgeAnvilRecipe recipe = ForgeAnvilRecipe.getRecipe(itemStack1, itemStack2, itemStack3, itemStack4);
                if (recipe != null) {
                    if (remainingHammer >= 0) {
                        hammerItem.damageItem(1, player);
                        remainingHammer--;
                        this.world.playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
                    }
                }
            }
        }
    }

    public void spawnOutput(World world, double x, double y, double z, ItemStack stack) {
        EntityItem entityitem = new EntityItem(world, x, y, z, stack);
        entityitem.motionX = 0D;
        entityitem.motionY = 0D;
        entityitem.motionZ = 0D;
        entityitem.setPickupDelay(20);

        world.spawnEntity(entityitem);
    }

    @Override
    public boolean addItem(@Nullable EntityPlayer player, ItemStack heldItem, @Nullable EnumHand hand) {
        for (int i = 0; i < getInvSize(); i++) {
            if (itemHandler.getStackInSlot(i).isEmpty()) {
                ItemStack itemAdd = heldItem.copy();
                itemHandler.insertItem(i, itemAdd, false);
                if(player == null || !player.capabilities.isCreativeMode) {
                    heldItem.shrink(heldItem.getCount());
                }
                markDirty();
                break;
            }
        }
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            itemHandler.deserializeNBT(compound.getCompoundTag("items"));
        }

        isCrafting = compound.getBoolean("isCrafting");

        remainingHammer = compound.getInteger("remainingHammers");
        totalHammer = compound.getInteger("totalHammers");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemHandler.serializeNBT());

        compound.setBoolean("isCrafting", isCrafting);

        compound.setInteger("remainingHammers", remainingHammer);
        compound.setInteger("totalHammers", totalHammer);

        return compound;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTag = super.getUpdateTag();

        nbtTag.setTag("itemsInAnvil", itemHandler.serializeNBT());

        nbtTag.setInteger("hammersLeft", remainingHammer);
        nbtTag.setInteger("hammersTotal", totalHammer);

        return nbtTag;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        if (packet.getNbtCompound().hasKey("itemsInAnvil")) {
            itemHandler.deserializeNBT(packet.getNbtCompound().getCompoundTag("itemsInAnvil"));
        }

        remainingHammer = packet.getNbtCompound().getInteger("hammersLeft");
        totalHammer = packet.getNbtCompound().getInteger("hammersTotal");
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
        }
        return super.getCapability(capability, facing);
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

    public boolean hasEmptySlot() {
        for (int i = 0; i < getInvSize(); i++) {
            if (itemHandler.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }


    public int getRemainingHammer() {
        return remainingHammer;
    }
}
