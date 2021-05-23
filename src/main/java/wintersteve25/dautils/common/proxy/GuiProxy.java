package wintersteve25.dautils.common.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import wintersteve25.dautils.common.item.GuiBloodNote;

import javax.annotation.Nullable;

public class GuiProxy implements IGuiHandler {

    public static final int BLOODNOTEGUIID = 0;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (player != null) {
            if (world != null) {
                if (ID == BLOODNOTEGUIID) {
                    return new GuiBloodNote();
                }
            }
        }
        return null;
    }
}
