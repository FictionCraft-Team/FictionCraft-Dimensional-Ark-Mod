package wintersteve25.dautils.common.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wintersteve25.dautils.DAUtils;
import wintersteve25.dautils.common.DAConfig;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class DAItems {

    public static Item Hammer;
    public static Item BloodNote;

    static List<Item> itemsDA = new ArrayList<>();

    static {
        if (DAConfig.weaponForging) {
            if (DAConfig.registerHammer) {
                Hammer = initItem(new Item().setRegistryName("hammer").setTranslationKey(DAUtils.MODID + ".hammer").setMaxDamage(90));
            }
        }
        if (DAConfig.registerBloodNote) {
            BloodNote = initItem(new ItemBloodNote());
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent e) {
        for (Item i : itemsDA) {
            initModel(i);
        }
    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event){
        for (Item i : itemsDA){
            event.getRegistry().register(i);
        }
    }
    @SideOnly(Side.CLIENT)
    private static void initModel(Item i) {
        ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation(i.getRegistryName(), "inventory"));
    }
    private static Item initItem(Item i) {
        itemsDA.add(i);
        return i;
    }
}
