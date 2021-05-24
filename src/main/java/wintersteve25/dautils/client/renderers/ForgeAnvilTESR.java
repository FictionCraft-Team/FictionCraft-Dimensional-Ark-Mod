package wintersteve25.dautils.client.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import wintersteve25.dautils.common.blocks.machines.forge_anvil.TileForgeAnvil;
import wintersteve25.dautils.common.lib.Math;

public class ForgeAnvilTESR extends TileEntitySpecialRenderer<TileForgeAnvil> {
    @Override
    public void render(TileForgeAnvil tileEntity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        renderItem(tileEntity, x, y, z);
    }

    private void renderItem(TileForgeAnvil tile, double x, double y, double z) {
        if (tile == null) {
            return;
        }
        for (int i = 0; i < tile.getInvSize(); i++) {
            Float random = Math.randomInRange(0, 0.2f);
            if(tile.getItemHandler().getStackInSlot(i).isEmpty()) {
                return;
            }
            GlStateManager.pushMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.color(1, 1, 1, 1);
            GlStateManager.disableBlend();
            GlStateManager.translate((float) x, (float) y, (float) z);
            GlStateManager.translate(0.5F, 0.25F, 0.5F);
            GlStateManager.translate(0F, 0F, 0.0F);
            GlStateManager.rotate(105F, 0.4F + random, 1F, 0F + random);
            Minecraft.getMinecraft().getRenderItem().renderItem(tile.getItemHandler().getStackInSlot(i), ItemCameraTransforms.TransformType.GROUND);
            GlStateManager.popMatrix();
        }
    }
}
