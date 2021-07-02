package wintersteve25.dautils.common.blocks.machines.smeltery;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wintersteve25.dautils.DAUtils;
import wintersteve25.dautils.client.renderers.SmelteryTESR;
import wintersteve25.dautils.common.blocks.machines.DABaseDirectionalBlock;
import wintersteve25.dautils.common.blocks.machines.forge_anvil.TileForgeAnvil;
import wintersteve25.dautils.common.compat.top.TOPInfoProvider;
import wintersteve25.dautils.common.item.DAItems;
import wintersteve25.dautils.common.item.heat_orbs.ItemHeatOrb;
import wintersteve25.dautils.common.lib.InvHelper;
import wintersteve25.dautils.common.lib.Utils;

import javax.annotation.Nullable;

public class BlockSmeltery extends DABaseDirectionalBlock implements ITileEntityProvider, TOPInfoProvider {
    public static final ResourceLocation SMELTERY = Utils.resourceLocationHelper("smeltery");
    //private static final AxisAlignedBB HITBOX = new AxisAlignedBB(0.0625, 0, 0.0625 * 3, 0.0625 * 15, 0.0625 * 13, 0.0625 * 12);

    public BlockSmeltery() {
        super(Material.ROCK);
        setRegistryName(SMELTERY);
        setTranslationKey(DAUtils.MODID + ".smeltery");
        setHarvestLevel("pickaxe", 2);
        setHardness(2.5f);
        setResistance(6f);
        setSoundType(SoundType.STONE);
        setCreativeTab(DAUtils.DAUtilsTab);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileSmeltery();
    }

    @Override
    public boolean hasTileEntity() {
        return true;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            ItemStack itemstack = playerIn.getHeldItem(hand);

            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TileSmeltery) {
                TileSmeltery tileSmeltery = (TileSmeltery) te;
                if (itemstack.getItem() instanceof ItemHeatOrb) {
                    return tileSmeltery.addOrb(playerIn, itemstack, hand);
                } else if (!itemstack.isEmpty() && !FluidUtil.interactWithFluidHandler(playerIn, hand, worldIn, pos, facing)) {
                    return tileSmeltery.addItem(playerIn, itemstack, hand);
                } else if (itemstack.isEmpty() && tileSmeltery.hasItem() && playerIn.isSneaking()) {
                    InvHelper.withdrawFromInventory(tileSmeltery, playerIn, tileSmeltery.getInvSize());
                    tileSmeltery.itemContentRemoved = true;
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        if (!worldIn.isRemote) {
            ItemStack itemstack = playerIn.getHeldItem(playerIn.getActiveHand());

            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TileSmeltery) {
                TileSmeltery tileSmeltery = (TileSmeltery) te;
                if (itemstack.isEmpty() && tileSmeltery.isThereOrb() && playerIn.isSneaking()) {
                    tileSmeltery.removeOrb(playerIn);
                    tileSmeltery.orbRemoved = true;
                }
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileSmeltery) {
            TileSmeltery tileSmeltery = (TileSmeltery) te;
            for (int i = 0; i < tileSmeltery.getInvSize(); i++) {
                if (!tileSmeltery.getItemHandler().getStackInSlot(i).isEmpty()) {
                    InvHelper.dropInventory(tileSmeltery, worldIn, state, pos, tileSmeltery.getInvSize());
                    tileSmeltery.dropOrb(state);
                }
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileSmeltery.class, new SmelteryTESR());
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof TileSmeltery) {
            TileSmeltery tileSmeltery = (TileSmeltery) te;
            if (tileSmeltery.isCrafting) {
                probeInfo.horizontal()
                        .progress(tileSmeltery.getRemainingTicks() %100, tileSmeltery.getTotalProgress(), probeInfo.defaultProgressStyle().prefix(I18n.translateToLocal("top.dautils.smeltery.remainingTicks")));
            }
        }
    }
}
