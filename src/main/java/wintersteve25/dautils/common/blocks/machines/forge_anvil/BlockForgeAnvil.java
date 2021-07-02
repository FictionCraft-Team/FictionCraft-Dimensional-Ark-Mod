package wintersteve25.dautils.common.blocks.machines.forge_anvil;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wintersteve25.dautils.DAUtils;
import wintersteve25.dautils.client.renderers.ForgeAnvilTESR;
import wintersteve25.dautils.common.blocks.machines.DABaseDirectionalBlock;
import wintersteve25.dautils.common.compat.top.TOPInfoProvider;
import wintersteve25.dautils.common.item.DAItems;
import wintersteve25.dautils.common.lib.InvHelper;
import wintersteve25.dautils.common.lib.Utils;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("deprecation")
public class BlockForgeAnvil extends DABaseDirectionalBlock implements ITileEntityProvider, TOPInfoProvider {

    public static final ResourceLocation FORGE_ANVIL = Utils.resourceLocationHelper("forge_anvil");
    private static final AxisAlignedBB HITBOX = new AxisAlignedBB(-0.0625*2, 0, 0.0625 * 3, 0.0625 * 18, 0.0625 * 12, 0.0625 * 13);

    public BlockForgeAnvil() {
        super(Material.ANVIL);
        setRegistryName(FORGE_ANVIL);
        setTranslationKey(DAUtils.MODID + ".forge_anvil");
        setHarvestLevel("pickaxe", 2);
        setHardness(2.5f);
        setResistance(6f);
        setSoundType(SoundType.ANVIL);
        setCreativeTab(DAUtils.DAUtilsTab);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileForgeAnvil();
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
            if (te instanceof TileForgeAnvil) {
                TileForgeAnvil tileForgeAnvil = (TileForgeAnvil) te;
                if (itemstack.isItemEqualIgnoreDurability(new ItemStack(DAItems.Hammer))) {
                    if (tileForgeAnvil.getRemainingHammer() > 0) {
                        tileForgeAnvil.hammerOnce(itemstack, playerIn);
                    } else {
                        if (!tileForgeAnvil.isCrafting) {
                            tileForgeAnvil.add(playerIn, itemstack, hand, tileForgeAnvil.getHammerHandler());
                        }
                    }
                    return true;
                } else if (itemstack.isItemEqualIgnoreDurability(new ItemStack(DAItems.SharpeningTool))) {
                    if (tileForgeAnvil.getRemainingSharpen() > 0) {
                        tileForgeAnvil.sharpenOnce(itemstack, playerIn);
                    }
                    if (!tileForgeAnvil.isCraftingSharpen) {
                        tileForgeAnvil.add(playerIn, itemstack, hand, tileForgeAnvil.getSharpenerHandler());
                    }
                    return true;
                } else if (!itemstack.isEmpty() && !itemstack.isItemEqual(new ItemStack(DAItems.Hammer))) {
                    return tileForgeAnvil.addItem(playerIn, itemstack, hand);
                } else if (itemstack.isEmpty() && tileForgeAnvil.hasItem() && playerIn.isSneaking()) {
                    InvHelper.withdrawFromInventory(tileForgeAnvil, playerIn, tileForgeAnvil.getInvSize());
                    tileForgeAnvil.itemHandlerContentRemove = true;
                    tileForgeAnvil.itemHandlerContentRemove2 = true;
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
            if (te instanceof TileForgeAnvil) {
                TileForgeAnvil tileForgeAnvil = (TileForgeAnvil) te;
                if (itemstack.isEmpty() && tileForgeAnvil.hasHammer && playerIn.isSneaking()) {
                    tileForgeAnvil.remove(playerIn, tileForgeAnvil.getHammerHandler());
                    return;
                } if (itemstack.isEmpty() && tileForgeAnvil.hasSharpener && playerIn.isSneaking()) {
                    tileForgeAnvil.remove(playerIn, tileForgeAnvil.getSharpenerHandler());
                }
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileForgeAnvil) {
            TileForgeAnvil tileForgeAnvil = (TileForgeAnvil) te;
            for (int i = 0; i < tileForgeAnvil.getInvSize(); i++) {
                if (!tileForgeAnvil.getItemHandler().getStackInSlot(i).isEmpty()) {
                    InvHelper.dropInventory(tileForgeAnvil, worldIn, state, pos, tileForgeAnvil.getInvSize());
                }
                if (!tileForgeAnvil.getHammerHandler().getStackInSlot(0).isEmpty()) {
                    tileForgeAnvil.drop(state, tileForgeAnvil.getHammerHandler());
                }
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileForgeAnvil.class, new ForgeAnvilTESR());
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return HITBOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        super.addCollisionBoxToList(pos, entityBox, collidingBoxes, HITBOX);
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof TileForgeAnvil) {
            TileForgeAnvil tileForgeAnvil = (TileForgeAnvil) te;
            if (tileForgeAnvil.isCrafting) {
                probeInfo.horizontal()
                        .text(I18n.translateToLocal("top.dautils.forge_anvil.recipeAva"))
                        .item(tileForgeAnvil.getOutputItem());
                probeInfo.horizontal()
                        .item(new ItemStack(DAItems.Hammer))
                        .progress(tileForgeAnvil.getRemainingHammer()-1 %100, tileForgeAnvil.getTotalHammer()-1, probeInfo.defaultProgressStyle().suffix(I18n.translateToLocal("top.dautils.forge_anvil.hammerLeft")));
            }

            if (tileForgeAnvil.isCraftingSharpen) {
                probeInfo.horizontal()
                        .text(I18n.translateToLocal("top.dautils.sharpening.recipeAva"))
                        .item(tileForgeAnvil.getOutputSharpeningItem());
                probeInfo.horizontal()
                        .item(new ItemStack(DAItems.SharpeningTool))
                        .progress(tileForgeAnvil.getRemainingSharpen()-1 %100, tileForgeAnvil.getTotalSharpen()-1, probeInfo.defaultProgressStyle().suffix(I18n.translateToLocal("top.dautils.forge_anvil.hammerLeft")));
            }
        }
    }
}
