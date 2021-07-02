package wintersteve25.dautils.common.blocks.machines.sacrificer;

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
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wintersteve25.dautils.DAUtils;
import wintersteve25.dautils.client.renderers.SacrificerTESR;
import wintersteve25.dautils.common.blocks.machines.DABaseDirectionalBlock;
import wintersteve25.dautils.common.lib.InvHelper;
import wintersteve25.dautils.common.lib.Utils;

import javax.annotation.Nullable;

public class BlockSacrificer extends DABaseDirectionalBlock implements ITileEntityProvider{

    public static final ResourceLocation SACRIFICER = Utils.resourceLocationHelper("sacrificer");

    public BlockSacrificer() {
        super(Material.ROCK);
        setRegistryName(SACRIFICER);
        setTranslationKey(DAUtils.MODID + ".sacrificer");
        setHarvestLevel("pickaxe", 2);
        setHardness(2.5f);
        setResistance(6f);
        setSoundType(SoundType.ANVIL);
        setCreativeTab(DAUtils.DAUtilsTab);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileSacrificer();
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
            if (te instanceof TileSacrificer) {
                TileSacrificer tileSacrificer = (TileSacrificer) te;
                if (!itemstack.isEmpty()) {
                    return tileSacrificer.addItem(playerIn, itemstack, hand);
                } else if (itemstack.isEmpty() && tileSacrificer.hasItem && playerIn.isSneaking()) {
                    if (!tileSacrificer.isCrafting) {
                        InvHelper.withdrawFromInventory(tileSacrificer, playerIn, tileSacrificer.getInvSize());
                        tileSacrificer.itemHandlerContentRemove = true;
                        return true;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileSacrificer) {
            TileSacrificer tileSacrificer = (TileSacrificer) te;
            for (int i = 0; i < tileSacrificer.getInvSize(); i++) {
                if (!tileSacrificer.getItemHandler().getStackInSlot(i).isEmpty()) {
                    if (!tileSacrificer.isCrafting) {
                        InvHelper.dropInventory(tileSacrificer, worldIn, state, pos, tileSacrificer.getInvSize());
                    }
                }
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileSacrificer.class, new SacrificerTESR());
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
