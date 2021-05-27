package wintersteve25.dautils.client.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;
import wintersteve25.dautils.common.blocks.machines.smeltery.TileSmeltery;
import wintersteve25.dautils.common.lib.Math;

public class SmelteryTESR extends TileEntitySpecialRenderer<TileSmeltery> {

    public static final float TANK_THICKNESS = 0.1f;

    @Override
    public void render(TileSmeltery te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.disableBlend();
        GlStateManager.translate((float) x, (float) y, (float) z);

        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        renderOutputFluid(te);

        GlStateManager.popMatrix();

        renderItem(te, x, y, z);
    }

    private void renderItem(TileSmeltery tile, double x, double y, double z) {
        if (tile == null) {
            return;
        }
        Float random = Math.randomInRange(0, 0.2f);
        if(tile.getItemHandler().getStackInSlot(0).isEmpty()) {
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.disableBlend();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.translate(0.5F, 1.05F, 0.5F);
        GlStateManager.scale(0.8D, 0.8D, 0.8D);
        GlStateManager.rotate(90, 0.5F + random, 0F, 0F);
        Minecraft.getMinecraft().getRenderItem().renderItem(tile.getItemHandler().getStackInSlot(0), ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.popMatrix();
    }

    private void renderOrb(TileSmeltery tile, double x, double y, double z) {
        if (tile == null) {
            return;
        }
        if(tile.getOrbHandler().getStackInSlot(0).isEmpty()) {
            return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.disableBlend();
        GlStateManager.translate(0.5F, 1.05F, 0.5F);
        GlStateManager.scale(0.8D, 0.8D, 0.8D);
        Minecraft.getMinecraft().getRenderItem().renderItem(tile.getItemHandler().getStackInSlot(0), ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.popMatrix();
    }

    private void renderOutputFluid(TileSmeltery tile) {
        if (tile == null) {
            return;
        }

        FluidStack fluidStack = tile.getOutputTank().getFluid();
        if (fluidStack == null) {
            return;
        }

        Fluid fluid = fluidStack.getFluid();
        if (fluid == null) {
            return;
        }

        float scale = (0.625F / tile.getOutputTank().getCapacity()) * tile.getOutputTank().getFluidAmount();;

        if (scale > 0.0f) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder renderer = tessellator.getBuffer();
            ResourceLocation still = fluid.getStill();
            TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(still.toString());

            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

            GlStateManager.color(1, 1, 1, .5f);
            renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);

            float u1 = sprite.getMinU();
            float v1 = sprite.getMinV();
            float u2 = sprite.getMaxU();
            float v2 = sprite.getMaxV();

            // Top
            renderer.pos(TANK_THICKNESS,  scale + TANK_THICKNESS, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
            renderer.pos(TANK_THICKNESS, scale + TANK_THICKNESS, 1-TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
            renderer.pos(1-TANK_THICKNESS, scale + TANK_THICKNESS, 1-TANK_THICKNESS).tex(u2, v2).color(255, 255, 255, 128).endVertex();
            renderer.pos(1-TANK_THICKNESS, scale + TANK_THICKNESS, TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();

            tessellator.draw();

            net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
        }
    }
}
