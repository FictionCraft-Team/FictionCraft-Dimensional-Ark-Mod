package wintersteve25.dautils.client.particles;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TextureeStitcher {
    @SubscribeEvent
    public void stitcherEventPre(TextureStitchEvent.Pre event) {
        event.getMap().registerSprite(AnvilParticle.particle);

        event.getMap().registerSprite(OrbParticle.white);
        event.getMap().registerSprite(OrbParticle.red);
        event.getMap().registerSprite(OrbParticle.orange);
        event.getMap().registerSprite(OrbParticle.blue);
    }
}
