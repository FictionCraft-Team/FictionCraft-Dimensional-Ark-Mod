package wintersteve25.dautils.common.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import wintersteve25.dautils.DAUtils;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wintersteve25.dautils.common.DAConfig;
import wintersteve25.dautils.common.blocks.DABlockObjectHolders;
import wintersteve25.dautils.common.blocks.machines.forge_anvil.BlockForgeAnvil;
import wintersteve25.dautils.common.blocks.machines.forge_anvil.TileForgeAnvil;
import wintersteve25.dautils.common.crafting.recipes.ForgeAnvilRecipes;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(DAUtils.instance, new GuiProxy());
        ForgeAnvilRecipes.getRecipeList();
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        DAUtils.getLogger().info("Registering Items");
        IForgeRegistry<Item> ir = event.getRegistry();

        if (DAConfig.weaponForging) {
            if (DAConfig.registerForgeAnvil) {
                ir.register(new ItemBlock(DABlockObjectHolders.forgeAnvil).setRegistryName(BlockForgeAnvil.FORGE_ANVIL));
            }
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        DAUtils.getLogger().info("Registering Blocks");
        IForgeRegistry<Block> br = event.getRegistry();
        if (DAConfig.weaponForging) {
            if (DAConfig.registerForgeAnvil) {
                br.register(new BlockForgeAnvil());
                TileEntity.register(DAUtils.MODID + ":forge_anvil", TileForgeAnvil.class);
            }
        }
    }
}