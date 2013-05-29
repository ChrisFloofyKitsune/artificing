package chrisclark13.minecraft.artificing.client.gui.inventory.guidepages;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.lib.Textures;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;


public class GuiGuideBook extends GuiScreen {

    private double pageHeight  = 0;  // image height of the page
    private double pageWidth   = 0;  // image width of the page
    private int    imageHeight = 180; // size of the image
    private int    imageWidth  = 292; // size of the image
    private int    pages       = 4;  // number of pages in total
    private int    curPage     = 0;  // page that the player is currently on

    /** Draw the page screen */
    public void drawBackground(int par1) {

        double uScale = 1 / imageWidth;
        double vScale = 1 / imageHeight;

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        Tessellator tessellator = Tessellator.instance;
        mc.renderEngine.bindTexture(Textures.GUIDE_BOOK);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y, 0, 0 * uScale, 0 * vScale);
        tessellator.addVertexWithUV(x + imageWidth, y, 0, (0 + imageWidth) * uScale, 0 * vScale);
        tessellator.addVertexWithUV(x + imageWidth, y + imageHeight, 0, (0 + imageWidth) * uScale,
                (0 + imageHeight) * vScale);
        tessellator.addVertexWithUV(x, y + imageHeight, 0, 0 * uScale, (0 + imageHeight) * vScale);
        tessellator.draw();

    }

    public boolean doesGuiPauseGame() {

        return false;
    }

}
