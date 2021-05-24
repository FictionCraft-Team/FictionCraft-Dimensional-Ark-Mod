package wintersteve25.dautils.common.blocks;

import net.minecraftforge.fml.common.registry.GameRegistry;
import wintersteve25.dautils.common.blocks.machines.forge_anvil.BlockForgeAnvil;

public class DABlockObjectHolders {

    @GameRegistry.ObjectHolder("dautils:forge_anvil")
    public static BlockForgeAnvil forgeAnvil;

    public static void initializeModels() {
        forgeAnvil.initModel();
    }
}
