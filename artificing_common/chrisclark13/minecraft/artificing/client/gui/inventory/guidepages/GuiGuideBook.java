package chrisclark13.minecraft.artificing.client.gui.inventory.guidepages;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.core.helper.GuideBookHelper;
import chrisclark13.minecraft.artificing.item.ItemGuideBook;
import chrisclark13.minecraft.artificing.lib.Strings;
import chrisclark13.minecraft.artificing.lib.Textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class GuiGuideBook extends GuiScreen {
    
    private int bookWidth = 256;
    private int bookHeight = 180;
    private int imageHeight = 256;
    private int imageWidth = 256;
    private final int LEFT_PAGE_X = 11;
    private final int RIGHT_PAGE_X = 129;
    private final int PAGE_Y = 7;
    
    private EntityPlayer player;
    private ItemStack book;
    
    private ArrayList<GuiGuidePage> pages;
    private int currentPage;
    private int guiLeft;
    private int guiTop;
    
    public GuiGuideBook(EntityPlayer player, ItemStack book) {
        this.player = player;
        this.book = book;
        this.pages = GuideBookHelper.getPagesFromXML(getXMLPathFromStack(book));
        
        this.currentPage = 0;
    }
    
    private String getXMLPathFromStack(ItemStack stack) {
        switch (MathHelper.clamp_int(stack.getItemDamage(), 0, 2)) {
            case ItemGuideBook.STARTING_BOOK_META:
            default:
                return Strings.GUIDE_STARTING_XML;
        }
    }
    
    @Override
    public void setWorldAndResolution(Minecraft par1Minecraft, int par2, int par3) {
        super.setWorldAndResolution(par1Minecraft, par2, par3);
        guiLeft = (width - bookWidth) / 2;
        guiTop = (height - bookHeight) / 2;
        
        for (int i = 0; i < pages.size(); i++) {
            pages.get(i).x = guiLeft + ((i % 2 == 0) ? LEFT_PAGE_X : RIGHT_PAGE_X);
            pages.get(i).y = guiTop + PAGE_Y;
        }
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float par3) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, par3);
        this.drawBackground();
        
        currentPage = (currentPage % 2 == 0) ? currentPage : currentPage - 1;
        currentPage = MathHelper.clamp_int(currentPage, 0, pages.size());
        
        GuiGuidePage leftPage = pages.get(currentPage);
        if (leftPage != null) {
            leftPage.drawContent(mc, mouseX, mouseY);
            leftPage.drawForegroundContent(mc, mouseX, mouseY);
        }
        
        if (currentPage + 1 < pages.size()) {
            GuiGuidePage rightPage = pages.get(currentPage + 1);
            if (rightPage != null) {
                rightPage.drawContent(mc, mouseX, mouseY);
                rightPage.drawForegroundContent(mc, mouseX, mouseY);
            }
        }
    }
    
    /** Draw the page screen */
    public void drawBackground() {
        
        double uScale = 1d / (double) imageWidth;
        double vScale = 1d / (double) imageHeight;
        Tessellator tessellator = Tessellator.instance;
        mc.renderEngine.bindTexture(Textures.GUIDE_BOOK);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(guiLeft, guiTop, 0, 0, 0);
        tessellator.addVertexWithUV(guiLeft, guiTop + bookHeight, 0, 0, bookHeight * vScale);
        tessellator.addVertexWithUV(guiLeft + bookWidth, guiTop + bookHeight, 0,
                bookWidth * uScale, bookHeight * vScale);
        tessellator.addVertexWithUV(guiLeft + bookWidth, guiTop, 0, bookWidth * uScale, 0);
        tessellator.draw();
    }
    
    public boolean doesGuiPauseGame() {
        
        return false;
    }
}
