package wintersteve25.dautils.client.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import wintersteve25.dautils.common.blocks.machines.sacrificer.TileSacrificer;

public class SacrificerTESR extends TileEntitySpecialRenderer<TileSacrificer> {

    @Override
    public void render(TileSacrificer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        renderItem(te, x, y, z);
    }

    private void renderItem(TileSacrificer tile, double x, double y, double z) {
        if (tile == null) {
            return;
        }
        if(tile.getItemHandler().getStackInSlot(0).isEmpty()) {
            return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.disableBlend();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.translate(0.5F, 0.8F, 0.5F);
        float rotation = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

        GlStateManager.rotate(rotation, 0F, 1F, 0F);
        Minecraft.getMinecraft().getRenderItem().renderItem(tile.getItemHandler().getStackInSlot(0), ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.popMatrix();
    }
}
