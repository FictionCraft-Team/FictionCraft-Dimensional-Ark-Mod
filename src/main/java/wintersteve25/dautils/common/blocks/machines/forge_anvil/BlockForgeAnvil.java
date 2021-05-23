package wintersteve25.dautils.common.blocks.machines.forge_anvil;

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
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wintersteve25.dautils.DAUtils;
import wintersteve25.dautils.client.proxy.renderers.ForgeAnvilTESR;
import wintersteve25.dautils.common.blocks.machines.DABaseDirectionalBlock;
import wintersteve25.dautils.common.lib.InvHelper;
import wintersteve25.dautils.common.lib.Utils;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class BlockForgeAnvil extends DABaseDirectionalBlock {
    public static final ResourceLocation FORGE_ANVIL = Utils.resourceLocationHelper("forge_anvil");

    public BlockForgeAnvil() {
        super(Material.ANVIL);
        setRegistryName(FORGE_ANVIL);
        setTranslationKey(DAUtils.MODID + ".forge_anvil");
        setHarvestLevel("pickaxe", 2);
        setHardness(2.5f);
        setResistance(6f);
        setSoundType(SoundType.METAL);
        setCreativeTab(DAUtils.DAUtilsTab);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
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
                if (!itemstack.isEmpty()) {
                    return tileForgeAnvil.addItem(playerIn, itemstack, hand, tileForgeAnvil.isCrafting);
                } else if (itemstack.isEmpty() && tileForgeAnvil.hasItem() && playerIn.isSneaking()) {
                    InvHelper.withdrawFromInventory(tileForgeAnvil, playerIn);
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
                    InvHelper.dropInventory(tileForgeAnvil, worldIn, state, pos);
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
}
