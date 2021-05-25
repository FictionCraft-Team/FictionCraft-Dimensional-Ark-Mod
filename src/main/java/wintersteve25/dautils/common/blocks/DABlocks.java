package wintersteve25.dautils.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class DABlocks {

    static List<Block> blocksDA = new ArrayList<>();
    static List<Block> blocksDATile = new ArrayList<>();
    static List<Block> blocksDASpecial = new ArrayList<>();
    static List<Item> itemBlocksDA = new ArrayList<>();
    static {
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModelsNormal(ModelRegistryEvent e) {
        for (Block b : blocksDA) {
            initModelNormal(b);
        }

        for (Block b : blocksDATile) {
            initModelNormal(b);
        }
    }
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event){
        for (Block b : blocksDA){
            event.getRegistry().register(b);
        }

        for (Block b : blocksDATile){
            event.getRegistry().register(b);
        }

        for (Block b : blocksDASpecial){
            event.getRegistry().register(b);
        }
    }
    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        for (Item ib : itemBlocksDA) {
            event.getRegistry().register(ib);
        }
    }
    @SideOnly(Side.CLIENT)
    private static void initModelNormal(Block b) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), 0, new ModelResourceLocation(b.getRegistryName(), "inventory"));
    }
    private static Block initBlockNormal(Block b) {
        blocksDA.add(b);
        itemBlocksDA.add(new ItemBlock(b).setRegistryName(b.getRegistryName()));
        return b;
    }

    private static Block initBlockTile(Block b) {
        blocksDATile.add(b);
        itemBlocksDA.add(new ItemBlock(b).setRegistryName(b.getRegistryName()));
        return b;
    }

    private static Block initBlockSpecial(Block b) {
        blocksDASpecial.add(b);
        itemBlocksDA.add(new ItemBlock(b).setRegistryName(b.getRegistryName()));
        return b;
    }
}
