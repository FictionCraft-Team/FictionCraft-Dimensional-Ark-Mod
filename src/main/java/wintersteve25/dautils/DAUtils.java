package wintersteve25.dautils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.LogManager;
import wintersteve25.dautils.common.compat.top.TOPCap;
import wintersteve25.dautils.common.item.DAItems;
import wintersteve25.dautils.common.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = DAUtils.MODID, name = DAUtils.NAME, version = DAUtils.VERSION, dependencies = "required-after:forge@[14.23.3.2847,)")
public class DAUtils {
    public static final String MODID = "dautils";
    public static final String NAME = "FictionCraft: Dimensional-ARK";
    public static final String VERSION = "1.0.2";

    @SidedProxy(clientSide = "wintersteve25.dautils.client.proxy.ClientProxy", serverSide = "wintersteve25.dautils.common.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static DAUtils instance;

    public static Logger logger;

    public static Logger getLogger() {
        if(logger == null){
            logger = LogManager.getFormatterLogger(MODID);
        }
        return logger;
    }

    public static CreativeTabs DAUtilsTab = new CreativeTabs("DAUtils") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(DAItems.BloodNote);
        }
    };

    public static void registerTOP() {
        if (Loader.isModLoaded("theoneprobe")) {
            TOPCap.register();
        }
    }

    //stages
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }


}
