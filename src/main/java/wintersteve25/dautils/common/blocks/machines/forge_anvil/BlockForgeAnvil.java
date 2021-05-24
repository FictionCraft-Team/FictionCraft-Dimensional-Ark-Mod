package wintersteve25.dautils.common.blocks.machines.forge_anvil;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wintersteve25.dautils.DAUtils;
import wintersteve25.dautils.client.renderers.ForgeAnvilTESR;
import wintersteve25.dautils.common.blocks.machines.DABaseDirectionalBlock;
import wintersteve25.dautils.common.item.DAItems;
import wintersteve25.dautils.common.lib.InvHelper;
import wintersteve25.dautils.common.lib.Utils;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("deprecation")
public class BlockForgeAnvil extends DABaseDirectionalBlock implements ITileEntityProvider {

    public static final ResourceLocation FORGE_ANVIL = Utils.resourceLocationHelper("forge_anvil");
    private static final AxisAlignedBB HITBOX = new AxisAlignedBB(0.0625, 0, 0.0625 * 3, 0.0625 * 15, 0.0625 * 13, 0.0625 * 12);

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
                        return true;
                    }
                } else if (!itemstack.isEmpty() && !itemstack.isItemEqual(new ItemStack(DAItems.Hammer))) {
                    return tileForgeAnvil.addItem(playerIn, itemstack, hand);
                } else if (itemstack.isEmpty() && tileForgeAnvil.hasItem() && playerIn.isSneaking()) {
                    InvHelper.withdrawFromInventory(tileForgeAnvil, playerIn, tileForgeAnvil.getInvSize());
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileForgeAnvil) {
            TileForgeAnvil tileForgeAnvil = (TileForgeAnvil) te;
            for (int i = 0; i < 1; i++) {
                if (!tileForgeAnvil.getItemHandler().getStackInSlot(i).isEmpty()) {
                    InvHelper.dropInventory(tileForgeAnvil, worldIn, state, pos, tileForgeAnvil.getInvSize());
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
}
