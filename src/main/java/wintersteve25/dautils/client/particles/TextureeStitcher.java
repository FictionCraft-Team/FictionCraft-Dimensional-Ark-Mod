package wintersteve25.dautils.client.particles;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ParticleStitcher {
    @SubscribeEvent
    public void stitcherEventPre(TextureStitchEvent.Pre event) {
        ResourceLocation anvilParticles = new ResourceLocation("dautils:entity/anvil_particles");
        event.getMap().registerSprite(anvilParticles);
    }
}
