package wintersteve25.dautils.common.item.blood_note;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import wintersteve25.dautils.DAUtils;
import wintersteve25.dautils.common.lib.Utils;

public class GuiBloodNote extends GuiScreen {
    public static final int WIDTH = 256;
    public static final int HEIGHT = 152;

    private int guiLeft;
    private int guiTop;

    private final ResourceLocation guiLocation = Utils.resourceLocationHelper("textures/gui/bloodnote.png");

    @Override
    public void initGui() {
        super.initGui();
        guiLeft = (this.width - WIDTH) / 2;
        guiTop = (this.height - HEIGHT) / 2;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        mc.getTextureManager().bindTexture(guiLocation);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, WIDTH, HEIGHT);
    }
}
