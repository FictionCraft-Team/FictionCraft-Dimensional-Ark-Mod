package wintersteve25.dautils.client.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import wintersteve25.dautils.common.blocks.machines.forge_anvil.TileForgeAnvil;
import wintersteve25.dautils.common.lib.Math;

public class ForgeAnvilTESR extends TileEntitySpecialRenderer<TileForgeAnvil> {
    //Super janky, but it works :)
    @Override
    public void render(TileForgeAnvil tileEntity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        renderItem1(tileEntity, x, y, z);
        renderItem2(tileEntity, x, y, z);
        renderItem3(tileEntity, x, y, z);
        renderItem4(tileEntity, x, y, z);
    }

    private void renderItem1(TileForgeAnvil tile, double x, double y, double z) {
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
        GlStateManager.translate(0.8F, 0.80F, 0.3F);
        GlStateManager.scale(0.6D, 0.6D, 0.6D);
        GlStateManager.rotate(90, 0.5F + random, 0F, 0F);
        Minecraft.getMinecraft().getRenderItem().renderItem(tile.getItemHandler().getStackInSlot(0), ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.popMatrix();
    }
    private void renderItem2(TileForgeAnvil tile, double x, double y, double z) {
        if (tile == null) {
            return;
        }
        Float random = Math.randomInRange(0, 0.2f);
        if(tile.getItemHandler().getStackInSlot(1).isEmpty()) {
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.disableBlend();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.translate(0.68F, 0.83F, 0.35F);
        GlStateManager.scale(0.6D, 0.6D, 0.6D);
        GlStateManager.rotate(90, 0.5F + random, 0F, 0F);
        Minecraft.getMinecraft().getRenderItem().renderItem(tile.getItemHandler().getStackInSlot(1), ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.popMatrix();
    }
    private void renderItem3(TileForgeAnvil tile, double x, double y, double z) {
        if (tile == null) {
            return;
        }
        Float random = Math.randomInRange(0, 0.2f);
        if(tile.getItemHandler().getStackInSlot(2).isEmpty()) {
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.disableBlend();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.translate(0.45F, 0.85F, 0.5F);
        GlStateManager.scale(0.6D, 0.6D, 0.6D);
        GlStateManager.rotate(90, 0.5F + random, 0F, 0F);
        Minecraft.getMinecraft().getRenderItem().renderItem(tile.getItemHandler().getStackInSlot(2), ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.popMatrix();
    }
    private void renderItem4(TileForgeAnvil tile, double x, double y, double z) {
        if (tile == null) {
            return;
        }
        Float random = Math.randomInRange(0, 0.2f);
        if(tile.getItemHandler().getStackInSlot(3).isEmpty()) {
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.disableBlend();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.translate(0.2F, 0.85F, 0.63F);
        GlStateManager.scale(0.6D, 0.6D, 0.6D);
        GlStateManager.rotate(90, 0.5F + random, 0F, 0F);
        Minecraft.getMinecraft().getRenderItem().renderItem(tile.getItemHandler().getStackInSlot(3), ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.popMatrix();
    }
}
