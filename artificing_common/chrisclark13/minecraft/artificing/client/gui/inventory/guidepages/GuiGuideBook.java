package chrisclark13.minecraft.artificing.client.gui.inventory.guidepages;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.client.gui.GuiContent;
import chrisclark13.minecraft.artificing.lib.Textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;


public class GuiGuideBook extends GuiScreen {

    private int pageHeight  = 180;
    private int pageWidth   = 146;
    private int imageHeight = 180;
    private int imageWidth  = 292;
    private int pages       = 4;
    private int curPage     = 0;
    private int x;
    private int y;

    @Override
    public void drawScreen(int par1, int par2, float par3) {

        x = (width - imageWidth) / 2;
        y = (height - imageHeight) / 2;

        GuiGuidePage page = new GuiGuidePage(x, y);
        this.drawDefaultBackground();
        super.drawScreen(par1, par2, par3);
        this.drawBackground();
        page.drawContent(mc, x, y);
        page.drawForegroundContent(mc, x, y);
    }

    /** Draw the page screen */
    public void drawBackground() {

        double uScale = 1d / (double) imageWidth;
        double vScale = 1d / (double) imageHeight;
        x = (width - imageWidth) / 2;
        y = (height - imageHeight) / 2;
        Tessellator tessellator = Tessellator.instance;
        mc.renderEngine.bindTexture(Textures.GUIDE_BOOK);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y, 0, 0 * uScale, 0 * vScale);
        tessellator.addVertexWithUV(x, y + imageHeight, 0, 0 * uScale, (0 + imageHeight) * vScale);
        tessellator.addVertexWithUV(x + imageWidth, y + imageHeight, 0, (0 + imageWidth) * uScale,
                (0 + imageHeight) * vScale);
        tessellator.addVertexWithUV(x + imageWidth, y, 0, (0 + imageWidth) * uScale, 0 * vScale);
        tessellator.draw();
    }

    public boolean doesGuiPauseGame() {

        return false;
    }

    public class GuiGuidePage extends GuiContent {

        public GuiGuidePage(int x, int y) {

            super(x, y, pageWidth, pageHeight);
            this.drawOwnBackground = false;

        }

        @Override
        public void drawForeground(Minecraft minecraft, int mouseX, int mouseY) {
            mc.fontRenderer.drawStringWithShadow("HAYO", 10, 10, 0x0);
        }

    }
}
