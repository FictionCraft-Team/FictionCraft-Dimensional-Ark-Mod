package wintersteve25.dautils.client.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import wintersteve25.dautils.common.item.heat_orbs.ItemHeatOrb;

import java.awt.*;

public class OrbParticle extends Particle {

    public static final ResourceLocation white = new ResourceLocation("dautils:entity/orb/white");
    public static final ResourceLocation red = new ResourceLocation("dautils:entity/orb/red");
    public static final ResourceLocation orange = new ResourceLocation("dautils:entity/orb/orange");
    public static final ResourceLocation blue = new ResourceLocation("dautils:entity/orb/blue");

    public ItemHeatOrb orb;

    public OrbParticle(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, ItemHeatOrb orb) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);

        this.particleAlpha = 50;

        this.particleGravity = 1;
        this.particleMaxAge = 10;

        this.orb = orb;

        TextureAtlasSprite white = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(this.white.toString());
        setParticleTexture(white);
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


        if(onGround) {
            this.setExpired();
        }

        if(prevPosY == posY && motionY > 0) {
            setExpired();
        }

        if (particleMaxAge-- == 0) {
            setExpired();
        }

        if (orb.getColor() == Color.red) {
            TextureAtlasSprite red = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(this.red.toString());
            setParticleTexture(red);
        } else if (orb.getColor() == Color.orange) {
            TextureAtlasSprite orange = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(this.orange.toString());
            setParticleTexture(orange);
        } else if (orb.getColor() == Color.blue) {
            TextureAtlasSprite orange = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(this.blue.toString());
            setParticleTexture(orange);
        }
    }
}
