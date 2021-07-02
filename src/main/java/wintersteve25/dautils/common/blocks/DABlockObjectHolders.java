package wintersteve25.dautils.common.blocks;

import net.minecraftforge.fml.common.registry.GameRegistry;
import wintersteve25.dautils.common.DAConfig;
import wintersteve25.dautils.common.blocks.machines.forge_anvil.BlockForgeAnvil;
import wintersteve25.dautils.common.blocks.machines.sacrificer.BlockSacrificer;
import wintersteve25.dautils.common.blocks.machines.smeltery.BlockSmeltery;

public class DABlockObjectHolders {

    @GameRegistry.ObjectHolder("dautils:forge_anvil")
    public static BlockForgeAnvil forgeAnvil;

    @GameRegistry.ObjectHolder("dautils:smeltery")
    public static BlockSmeltery smeltery;

    @GameRegistry.ObjectHolder("dautils:sacrificer")
    public static BlockSacrificer sacrificer;

    public static void initializeModels() {
        if (DAConfig.weaponForging) {
            if (DAConfig.registerForgeAnvil) {
                forgeAnvil.initModel();
            }
            if (DAConfig.registerSmeltery) {
                smeltery.initModel();
            }
        }
        if (DAConfig.registerSacrificer) {
            sacrificer.initModel();
        }
    }
}
