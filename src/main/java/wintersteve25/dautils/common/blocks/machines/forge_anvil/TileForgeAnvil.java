package wintersteve25.dautils.common.blocks.machines.forge_anvil;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleCrit;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import wintersteve25.dautils.DAUtils;
import wintersteve25.dautils.client.particles.AnvilParticle;
import wintersteve25.dautils.common.blocks.machines.DABaseItemInventoryTile;
import wintersteve25.dautils.common.crafting.ForgeAnvilRecipe;
import wintersteve25.dautils.common.crafting.ForgeAnvilSharpenRecipe;
import wintersteve25.dautils.common.item.DAItems;

import javax.annotation.Nullable;

public class TileForgeAnvil extends DABaseItemInventoryTile implements ITickable {

    public static boolean isCrafting = false;
    public static boolean isCraftingSharpen = false;

    private static int totalHammer = 0;
    private static int remainingHammer = 0;
    private static int totalSharpen = 0;
    private static int remainingSharpen = 0;

    private boolean playedSound = false;
    private boolean playedSoundSharpen = false;

    public ItemStackHandler hammerHandler = createHandler();
    public ItemStackHandler sharpenerHandler = createHandler();

    public boolean hasHammer = false;
    public boolean hasSharpener = false;

    public boolean itemHandlerContentRemove = false;
    public boolean itemHandlerContentRemove2 = false;

    @Override
    public void update() {
        if (!world.isRemote) {
            hasHammer = hammerHandler.getStackInSlot(0).isItemEqualIgnoreDurability(new ItemStack(DAItems.Hammer));
            hasSharpener = sharpenerHandler.getStackInSlot(0).isItemEqualIgnoreDurability(new ItemStack(DAItems.SharpeningTool));

            sharpenCraft();
            hammerCraft();
        }
    }

    public void sharpenCraft() {
        ItemStack input = getItemHandler().getStackInSlot(0);
        ForgeAnvilSharpenRecipe recipe = ForgeAnvilSharpenRecipe.getRecipe(input);
        if (recipe != null) {
            if (!playedSoundSharpen) {
                this.world.playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
                playedSoundSharpen = true;
            }
            if (remainingSharpen > 0) {
                isCraftingSharpen = true;
                if (remainingSharpen <= 1) {
                    for (int i = 0; i < getInvSize(); i++) {
                        getItemHandler().extractItem(i, 1, false);
                    }
                    spawnOutput(world, pos.getX(), pos.getY() + 1, pos.getZ(), recipe.getItemOutput());
                    isCraftingSharpen = false;
                    remainingSharpen = 0;
                    totalSharpen = 0;
                    playedSoundSharpen = false;
                    markDirty();
                }
            } else {
                totalSharpen = recipe.getSharpenAmount();
                remainingSharpen = totalSharpen;
                markDirty();
            }
        } else {
            if (itemHandlerContentRemove || isCrafting) {
                isCraftingSharpen = false;
                remainingSharpen = 0;
                totalSharpen = 0;
                playedSoundSharpen = false;
                itemHandlerContentRemove = false;
                markDirty();
            }
        }
    }

    public void hammerCraft() {
        ItemStack input1 = getItemHandler().getStackInSlot(0);
        ItemStack input2 = getItemHandler().getStackInSlot(1);
        ItemStack input3 = getItemHandler().getStackInSlot(2);
        ItemStack input4 = getItemHandler().getStackInSlot(3);
        ForgeAnvilRecipe recipe = ForgeAnvilRecipe.getRecipe(input1, input2, input3, input4);
        if (recipe != null) {
            if (!playedSound) {
                this.world.playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
                playedSound = true;
            }
            if (remainingHammer > 0) {
                isCrafting = true;
                if (remainingHammer <= 1) {
                    for (int i = 0; i < getInvSize(); i++) {
                        getItemHandler().extractItem(i, 1, false);
                    }
                    spawnOutput(world, pos.getX(), pos.getY() + 1, pos.getZ(), recipe.getItemOutput());
                    isCrafting = false;
                    remainingHammer = 0;
                    totalHammer = 0;
                    playedSound = false;
                    markDirty();
                }
            } else {
                totalHammer = recipe.getHammerAmounts();
                remainingHammer = totalHammer;
                markDirty();
            }
        } else {
            if (itemHandlerContentRemove2) {
                isCrafting = false;
                remainingHammer = 0;
                totalHammer = 0;
                playedSound = false;
                itemHandlerContentRemove = false;
                markDirty();
            }
        }
    }

    @Override
    public boolean addItem(@Nullable EntityPlayer player, ItemStack heldItem, @Nullable EnumHand hand) {
        for (int i = 0; i < getInvSize(); i++) {
            if (itemHandler.getStackInSlot(i).isEmpty()) {
                ItemStack itemAdd = heldItem.copy();
                itemAdd.setCount(1);
                itemHandler.insertItem(i, itemAdd, false);
                if(player == null || !player.capabilities.isCreativeMode) {
                    heldItem.shrink(1);
                }
                markDirty();
                break;
            }
        }
        return true;
    }

    public boolean add(@Nullable EntityPlayer player, ItemStack heldItem, @Nullable EnumHand hand, ItemStackHandler handler) {
        if (handler.getStackInSlot(0).isEmpty()) {
            ItemStack itemAdd = heldItem.copy();
            itemAdd.setCount(1);
            handler.insertItem(0, itemAdd, false);
            if(player == null || !player.capabilities.isCreativeMode) {
                heldItem.shrink(1);
            }
            markDirty();
        }
        return true;
    }

    public void remove(EntityPlayer player, ItemStackHandler handler) {
        ItemStack stackAt = handler.getStackInSlot(0);
        if(!stackAt.isEmpty()) {
            ItemStack copy = stackAt.copy();
            ItemHandlerHelper.giveItemToPlayer(player, copy);
            handler.extractItem(0, copy.getCount(), false);
            player.world.updateComparatorOutputLevel(getPos(), null);
        }
    }

    public void drop(IBlockState state, ItemStackHandler handler) {
        if(handler != null) {
            ItemStack itemstack = handler.getStackInSlot(0);

            if(!itemstack.isEmpty()) {
                net.minecraft.inventory.InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemstack);
            }
            world.updateComparatorOutputLevel(pos, state.getBlock());
        }
    }

    public void hammerOnce(ItemStack hammerItem, EntityPlayer player) {
        if (remainingHammer < 0) {
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
                        if (player.getCooldownTracker().getCooldown(hammerItem.getItem(), 0) <= 0) {
                            hammerItem.damageItem(1, player);
                            player.getCooldownTracker().setCooldown(hammerItem.getItem(), 25);
                            remainingHammer--;
                            this.world.playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
                            spawnParticles();
                            spawnParticles();
                            spawnParticles();
                            spawnParticles();
                            spawnParticles();
                            spawnParticles();
                        }
                    }
                }
            }
        }
    }

    public void sharpenOnce(ItemStack sharpenerItem, EntityPlayer player) {
        if (remainingSharpen < 0) {
            return;
        }

        if(!world.isRemote) {
            if (isCraftingSharpen) {
                ItemStack itemStack = itemHandler.getStackInSlot(0);

                ForgeAnvilSharpenRecipe recipe = ForgeAnvilSharpenRecipe.getRecipe(itemStack);

                if (recipe != null) {
                    if (remainingSharpen >= 0) {
                        if (player.getCooldownTracker().getCooldown(sharpenerItem.getItem(), 0) <= 0) {
                            sharpenerItem.damageItem(1, player);
                            player.getCooldownTracker().setCooldown(sharpenerItem.getItem(), 25);
                            remainingSharpen--;
                            this.world.playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
                            spawnParticles();
                        }
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles() {
        double xpos = pos.getX() + 0.005;
        double ypos = pos.getY() + 0.000005;
        double zpos = pos.getZ() + 0.00005;
        double velocityX = 0.00000005;
        double velocityY = 0.00000005;
        double velocityZ = 0.00000005;
        AnvilParticle anvilParticle = new AnvilParticle(this.world, xpos, ypos, zpos, velocityX, velocityY, velocityZ);
        Minecraft.getMinecraft().effectRenderer.addEffect(anvilParticle);
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
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            itemHandler.deserializeNBT(compound.getCompoundTag("items"));
        }
        if (compound.hasKey("hammer")) {
            hammerHandler.deserializeNBT(compound.getCompoundTag("hammer"));
        }
        if (compound.hasKey("sharpener")) {
            sharpenerHandler.deserializeNBT(compound.getCompoundTag("sharpener"));
        }

        isCrafting = compound.getBoolean("isCrafting");

        remainingHammer = compound.getInteger("remainingHammers");
        totalHammer = compound.getInteger("totalHammers");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemHandler.serializeNBT());
        compound.setTag("hammer", hammerHandler.serializeNBT());
        compound.setTag("sharpener", sharpenerHandler.serializeNBT());

        compound.setBoolean("isCrafting", isCrafting);

        compound.setInteger("remainingHammers", remainingHammer);
        compound.setInteger("totalHammers", totalHammer);

        return compound;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTag = super.getUpdateTag();

        nbtTag.setTag("itemsInAnvil", itemHandler.serializeNBT());
        nbtTag.setTag("hammerInAnvil", hammerHandler.serializeNBT());
        nbtTag.setTag("sharpenerInAnvil", sharpenerHandler.serializeNBT());

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
        if (packet.getNbtCompound().hasKey("hammerInAnvil")) {
            hammerHandler.deserializeNBT(packet.getNbtCompound().getCompoundTag("hammerInAnvil"));
        }
        if (packet.getNbtCompound().hasKey("sharpenerInAnvil")) {
            sharpenerHandler.deserializeNBT(packet.getNbtCompound().getCompoundTag("sharpenerInAnvil"));
        }

        remainingHammer = packet.getNbtCompound().getInteger("hammersLeft");
        totalHammer = packet.getNbtCompound().getInteger("hammersTotal");
    }

    @Override
    public int getInvSize() {
        return 4;
    }

    public ItemStackHandler getHammerHandler() {
        return this.hammerHandler;
    }

    public ItemStackHandler getSharpenerHandler() {
        return this.sharpenerHandler;
    }

    public int getRemainingSharpen() {
        return remainingSharpen;
    }

    public int getTotalSharpen() {
        return totalSharpen;
    }

    public int getRemainingHammer() {
        return remainingHammer;
    }

    public int getTotalHammer() {
        return totalHammer;
    }

    public ItemStack getOutputItem() {
        ItemStack itemStack1 = itemHandler.getStackInSlot(0);
        ItemStack itemStack2 = itemHandler.getStackInSlot(1);
        ItemStack itemStack3 = itemHandler.getStackInSlot(2);
        ItemStack itemStack4 = itemHandler.getStackInSlot(3);
        ForgeAnvilRecipe recipe = ForgeAnvilRecipe.getRecipe(itemStack1, itemStack2, itemStack3, itemStack4);

        return recipe.getItemOutput();
    }

    public ItemStack getOutputSharpeningItem() {
        ItemStack itemStack = itemHandler.getStackInSlot(0);
        ForgeAnvilSharpenRecipe recipe = ForgeAnvilSharpenRecipe.getRecipe(itemStack);

        return recipe.getItemOutput();
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
