package wintersteve25.dautils.common.item;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wintersteve25.dautils.common.DAConfig;

public class DAItems {
    @GameRegistry.ObjectHolder("dautils:blood_note")
    public static ItemBloodNote bloodNote;

    @SideOnly(Side.CLIENT)
    public static void initializeModel() {
        if (DAConfig.registerBloodNote) {
            bloodNote.initModel();
        }
    }
}
