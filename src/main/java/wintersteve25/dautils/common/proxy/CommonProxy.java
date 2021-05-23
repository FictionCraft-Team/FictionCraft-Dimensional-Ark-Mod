package wintersteve25.dautils.common.proxy;

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
import wintersteve25.dautils.common.item.ItemBloodNote;

@Mod.EventBusSubscriber
public class CommonProxy {
    private static final String MODID = DAUtils.MODID;

    public void preInit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(DAUtils.instance, new GuiProxy());
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        DAUtils.getLogger().info("Registering Items");
        IForgeRegistry<Item> ir = event.getRegistry();
        if (DAConfig.registerBloodNote) {
            ir.register(new ItemBloodNote("blood_note"));
        }
    }
}