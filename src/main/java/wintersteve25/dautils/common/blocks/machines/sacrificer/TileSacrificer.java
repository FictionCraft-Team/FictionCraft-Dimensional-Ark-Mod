package wintersteve25.dautils.common.blocks.machines.sacrificer;

import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleLava;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wintersteve25.dautils.common.blocks.machines.DABaseItemInventoryTile;
import wintersteve25.dautils.common.crafting.SacrificerRecipe;

import javax.annotation.Nullable;
import java.util.Objects;

public class TileSacrificer extends DABaseItemInventoryTile implements ITickable {

    public boolean isCrafting = false;
    private static int totalTicks = 0;
    private static int remainingTicks = 0;
    public boolean hasItem = false;
    public boolean itemHandlerContentRemove = false;

    private EntityPlayer player;

    private SoulNetwork network;

    @Override
    public void update() {
        if (!world.isRemote) {
            if (player != null) {
                network = NetworkHelper.getSoulNetwork(player);
            }

            hasItem = !itemHandler.getStackInSlot(0).isEmpty();
            craft();
        }
    }

    public void craft() {
        ItemStack input = getItemHandler().getStackInSlot(0);
        SacrificerRecipe recipe = SacrificerRecipe.getRecipe(input);

        if (recipe != null) {
            if (remainingTicks > 0) {
                remainingTicks--;
                isCrafting = true;
                if (network != null) {
                    network.add(new SoulTicket(recipe.getBlood()), Integer.MAX_VALUE);
                }
                spawnParticles();
                if (remainingTicks <= 0) {
                    getItemHandler().extractItem(0, 1, false);
                    isCrafting = false;
                    remainingTicks = 0;
                    totalTicks = 0;
                    markDirty();
                }
            } else {
                totalTicks = recipe.getTime();
                remainingTicks = totalTicks;
                markDirty();
            }
        } else {
            if (itemHandlerContentRemove || isCrafting) {
                isCrafting = false;
                remainingTicks = 0;
                totalTicks = 0;
                itemHandlerContentRemove = false;
                markDirty();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles() {
        double xpos = pos.getX() + 0.005;
        double ypos = pos.getY() + 0.000005;
        double zpos = pos.getZ() + 0.00005;
        double velocityX = 0.00000005;
        double velocityY = 0.0005;
        double velocityZ = 0.00000005;
        Minecraft.getMinecraft().effectRenderer.addEffect(Objects.requireNonNull(new ParticleLava.Factory().createParticle(0, world, xpos, ypos, zpos, velocityX, velocityY, velocityZ, 0)));
    }

    @Override
    public boolean addItem(@Nullable EntityPlayer player, ItemStack heldItem, @Nullable EnumHand hand) {
        this.player = player;
        if (itemHandler.getStackInSlot(0).isEmpty()) {
            ItemStack itemAdd = heldItem.copy();
            itemAdd.setCount(1);
            itemHandler.insertItem(0, itemAdd, false);
            if(player == null || !player.capabilities.isCreativeMode) {
                heldItem.shrink(1);
            }
            markDirty();
        }
        return true;
    }

    @Override
    public int getInvSize() {
        return 1;
    }
}
