package chrisclark13.minecraft.artificing.client.gui.inventory.guidepages;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.lib.Textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;

public class GuiGuideBook extends GuiScreen {
    
    private int bookWidth = 256;
    private int bookHeight = 180;
    private int imageHeight = 256;
    private int imageWidth = 256;
    private final int LEFT_PAGE_X = 11;
    private final int RIGHT_PAGE_X = 135;
    private final int PAGE_Y = 7;
    
    private int pages = 4;
    private int curPage = 0;
    private int guiLeft;
    private int guiTop;
    
    @Override
    public void setWorldAndResolution(Minecraft par1Minecraft, int par2, int par3) {
        super.setWorldAndResolution(par1Minecraft, par2, par3);
        guiLeft = (width - bookWidth) / 2;
        guiTop = (height - bookHeight) / 2;
    }
    
    @Override
    public void drawScreen(int par1, int par2, float par3) {
        GuiGuidePage page = new GuiGuidePage(guiLeft + LEFT_PAGE_X, guiTop + PAGE_Y);
        this.drawDefaultBackground();
        super.drawScreen(par1, par2, par3);
        this.drawBackground();
        page.drawContent(mc, guiLeft, guiTop);
        page.drawForegroundContent(mc, guiLeft, guiTop);
    }
    
    /** Draw the page screen */
    public void drawBackground() {
        
        double uScale = 1d / (double) imageWidth;
        double vScale = 1d / (double) imageHeight;
        Tessellator tessellator = Tessellator.instance;
        mc.renderEngine.bindTexture("/mods/Artificing/textures/gui/guideBookNew.png");
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(guiLeft, guiTop, 0, 0, 0);
        tessellator.addVertexWithUV(guiLeft, guiTop + bookHeight, 0, 0, bookHeight * vScale);
        tessellator.addVertexWithUV(guiLeft + bookWidth, guiTop + bookHeight, 0, bookWidth
                * uScale, bookHeight * vScale);
        tessellator.addVertexWithUV(guiLeft + bookWidth, guiTop, 0, bookWidth * uScale, 0);
        tessellator.draw();
    }
    
    public boolean doesGuiPauseGame() {
        
        return false;
    }
}
