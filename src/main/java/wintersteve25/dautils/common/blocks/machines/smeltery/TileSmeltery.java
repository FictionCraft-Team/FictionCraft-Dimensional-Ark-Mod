package wintersteve25.dautils.common.blocks.machines.smeltery;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import wintersteve25.dautils.client.particles.OrbParticle;
import wintersteve25.dautils.common.blocks.machines.DABaseItemInventoryTile;
import wintersteve25.dautils.common.crafting.SmelteryRecipe;
import wintersteve25.dautils.common.item.heat_orbs.ItemHeatOrb;

import javax.annotation.Nullable;

public class TileSmeltery extends DABaseItemInventoryTile implements ITickable {

    private final int capacity = Fluid.BUCKET_VOLUME*4;
    private final FluidTank outputTank = new FluidTank(capacity){
        @Override
        protected void onContentsChanged() {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            markDirty();
        }
    };
    private final ItemStackHandler orbHandler = createHandler();


    public static boolean isCrafting = false;
    private boolean hasOrb = false;
    private int orbTier = -1;
    private int progress = 0;
    private int totalProgress = 0;

    private int baseProcessingSpeed = 1;

    public static boolean itemContentRemoved = false;
    public static boolean orbRemoved = false;

    @Override
    public void update() {
        if (!world.isRemote) {
            if (orbHandler.getStackInSlot(0).getItem() instanceof ItemHeatOrb) {
                this.hasOrb = true;
                ItemHeatOrb orb = (ItemHeatOrb) orbHandler.getStackInSlot(0).getItem();
                this.orbTier = orb.getOrbTier();
                markDirty();
            }
            if (orbHandler.getStackInSlot(0).isEmpty()) {
                this.hasOrb = false;
                this.orbTier = -1;
                markDirty();
            }
            if (hasOrb) {
                orbParticle();
                orbParticle();
                ItemStack itemStack = itemHandler.getStackInSlot(0);
                SmelteryRecipe recipe = SmelteryRecipe.getRecipe(itemStack, this.orbTier);
                if (recipe != null) {
                    FluidStack outputFluid = recipe.getFluidOutput().copy();
                    if (progress > 0) {
                        isCrafting = true;
                        progress-=baseProcessingSpeed;

                        if (itemStack.isEmpty() || !hasOrb || orbTier < recipe.getRequiredOrbTier()) {
                            resetAllProgress();
                        }

                        if (progress <= 0) {
                            outputTank.fill(outputFluid, true);
                            itemHandler.extractItem(0, 1, false);
                            resetAllProgress();
                        }

                    } else {
                        if (outputTank.fill(outputFluid, true) != 0) {
                            totalProgress = recipe.getProcessTime();
                            progress = totalProgress;
                            markDirty();
                        }
                    }
                } if (itemContentRemoved) {
                    isCrafting = false;
                    progress = 0;
                    totalProgress = 0;
                    itemContentRemoved = false;
                    markDirty();
                }
            } if (orbRemoved) {
                isCrafting = false;
                progress = 0;
                totalProgress = 0;
                orbRemoved = false;
                markDirty();
            }
        }
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

    @SideOnly(Side.CLIENT)
    public void orbParticle() {
        double xpos = pos.getX() + 0.5;
        double ypos = pos.getY() + 1.5;
        double zpos = pos.getZ() + 0.5;
        double velocityX = 0;
        double velocityY = 0;
        double velocityZ = 0;

        Item orbItem = orbHandler.getStackInSlot(0).getItem();

        if(orbItem instanceof ItemHeatOrb) {
            ItemHeatOrb orb = (ItemHeatOrb) orbItem;

            OrbParticle orbParticle = new OrbParticle(this.world, xpos, ypos, zpos, velocityX, velocityY, velocityZ, orb);
            Minecraft.getMinecraft().effectRenderer.addEffect(orbParticle);
        }
    }

    public boolean addOrb(@Nullable EntityPlayer player, ItemStack heldItem, @Nullable EnumHand hand) {
        if (orbHandler.getStackInSlot(0).isEmpty()) {
            if (heldItem.getItem() instanceof ItemHeatOrb) {
                ItemStack itemAdd = heldItem.copy();
                orbHandler.insertItem(0, itemAdd, false);
                if(player == null || !player.capabilities.isCreativeMode) {
                    heldItem.shrink(heldItem.getCount());
                }
                markDirty();
            }
        }
        return true;
    }

    public void removeOrb(EntityPlayer player) {
        ItemStack stackAt = orbHandler.getStackInSlot(0);
        if(!stackAt.isEmpty()) {
            ItemStack copy = stackAt.copy();
            ItemHandlerHelper.giveItemToPlayer(player, copy);
            orbHandler.extractItem(0, copy.getCount(), false);
            player.world.updateComparatorOutputLevel(getPos(), null);
        }
    }

    public void dropOrb(IBlockState state) {
        if(orbHandler != null) {
            ItemStack itemstack = orbHandler.getStackInSlot(0);

            if(!itemstack.isEmpty()) {
                net.minecraft.inventory.InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemstack);
            }

            world.updateComparatorOutputLevel(pos, state.getBlock());
        }
    }

    public ItemStackHandler getOrbHandler() {
        return this.orbHandler;
    }

    public void resetAllProgress() {
        this.isCrafting = false;
        this.progress = 0;
        this.totalProgress = 0;
        markDirty();
    }

    @Override
    public int getInvSize() {
        return 1;
    }

    public boolean hasItem() {
        for (int i = 0; i < getInvSize(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean isThereOrb() {
        return hasOrb;
    }

    public FluidTank getOutputTank() {
        return outputTank;
    }

    public int getRemainingTicks() {
        return progress;
    }

    public int getTotalProgress() {
        return totalProgress;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            itemHandler.deserializeNBT(compound.getCompoundTag("items"));
        }
        if (compound.hasKey("orb")) {
            orbHandler.deserializeNBT(compound.getCompoundTag("orb"));
        }

        outputTank.readFromNBT(compound.getCompoundTag("outTank"));

        hasOrb = compound.getBoolean("hasOrb");
        progress = compound.getInteger("progress");
        totalProgress = compound.getInteger("totalProgress");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemHandler.serializeNBT());
        compound.setTag("orb", orbHandler.serializeNBT());

        NBTTagCompound outTankNBT = new NBTTagCompound();
        outputTank.writeToNBT(outTankNBT);
        compound.setTag("outTank", outTankNBT);

        compound.setBoolean("hasOrb", hasOrb);
        compound.setInteger("progress", progress);
        compound.setInteger("totalProgress", totalProgress);

        return compound;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTag = super.getUpdateTag();
        NBTTagCompound outTankNBT = new NBTTagCompound();

        nbtTag.setTag("itemsInSmeltery", itemHandler.serializeNBT());
        nbtTag.setTag("orbInSmeltery", orbHandler.serializeNBT());

        outputTank.writeToNBT(outTankNBT);
        nbtTag.setTag("outputFluids", outTankNBT);

        nbtTag.setBoolean("orb", hasOrb);
        nbtTag.setInteger("progressTime", progress);
        nbtTag.setInteger("totalProgressTime", totalProgress);

        return nbtTag;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        if (packet.getNbtCompound().hasKey("itemsInSmeltery")) {
            itemHandler.deserializeNBT(packet.getNbtCompound().getCompoundTag("itemsInSmeltery"));
        }
        if (packet.getNbtCompound().hasKey("orbInSmeltery")) {
            orbHandler.deserializeNBT(packet.getNbtCompound().getCompoundTag("orbInSmeltery"));
        }

        outputTank.readFromNBT(packet.getNbtCompound().getCompoundTag("outputFluids"));

        hasOrb = packet.getNbtCompound().getBoolean("hasOrb");
        progress = packet.getNbtCompound().getInteger("progress");
        totalProgress = packet.getNbtCompound().getInteger("totalProgress");
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if (outputTank.getFluid() != null) {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(outputTank);
            }
        }
        return super.getCapability(capability, facing);
    }
}
