package wintersteve25.dautils.client.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import wintersteve25.dautils.DAUtils;
import wintersteve25.dautils.client.particles.TextureeStitcher;
import wintersteve25.dautils.common.blocks.DABlockObjectHolders;
import wintersteve25.dautils.common.proxy.CommonProxy;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        MinecraftForge.EVENT_BUS.register(new TextureeStitcher());
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @SubscribeEvent
    public static void modelRegistration(ModelRegistryEvent event) {
        DAUtils.getLogger().info("Registering Models");
        DABlockObjectHolders.initializeModels();
    }
}