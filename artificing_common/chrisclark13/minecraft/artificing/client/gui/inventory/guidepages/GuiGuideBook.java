package chrisclark13.minecraft.artificing.client.gui.inventory.guidepages;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;


public class GuiGuideBook extends GuiScreen {

    private double pageHeight  = 0; // image height of the page
    private double pageWidth   = 0; // image width of the page
    private int    imageHeight = 0; // size of the image
    private int    imageWidth  = 0; // size of the image
    private int    pages       = 4; // number of pages in total
    private int    curPage     = 0; // page that the player is currently on

    /** Draw the page screen */
    public void drawBackground(int par1) {

        double uScale = 1 / imageWidth;
        double vScale = 1 / imageHeight;
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        Tessellator tessellator = Tessellator.instance;
        /* Right page */
        GL11.glPushMatrix();
        {
            mc.renderEngine.bindTexture("");// need image path
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(pageWidth, pageHeight, 0, (pageWidth) * uScale,
                    (pageHeight) * vScale);
            tessellator.addVertexWithUV(pageWidth, (pageHeight - pageHeight), 0, 0, 0.5);
            tessellator.addVertexWithUV((pageWidth - pageWidth), pageHeight, 0, 0.5, 0);
            tessellator
                    .addVertexWithUV((pageWidth - pageWidth), (pageHeight - pageHeight), 0, 0, 0);
            tessellator.draw();
        }
        GL11.glPopMatrix();
    }

    public boolean doesGuiPauseGame() {

        return false;
    }

}
