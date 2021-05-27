package wintersteve25.dautils.client.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class AnvilParticle extends Particle {
    private final ResourceLocation particle = new ResourceLocation("dautils:entity/anvil_particles");

    public AnvilParticle(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        final float ALPHA_VALUE = 100F;
        this.particleAlpha = ALPHA_VALUE;

        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(particle.toString());
        setParticleTexture(sprite);
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        return 0xFFFFFF;
    }

    @Override
    public boolean shouldDisableDepth() {
        return false;
    }

    @Override
    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        move(motionX, motionY, motionZ);

        if(onGround) {
            this.setExpired();
        }

        if(prevPosY == posY && motionY > 0) {
            setExpired();
        }

        if(this.particleAge-- <= -10) {
            this.setExpired();
        }
    }
}
