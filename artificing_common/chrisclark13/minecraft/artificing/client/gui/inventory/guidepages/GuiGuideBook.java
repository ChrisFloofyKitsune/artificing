package chrisclark13.minecraft.artificing.client.gui.inventory.guidepages;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.client.gui.GuiTab;
import chrisclark13.minecraft.artificing.client.gui.GuiTabList;
import chrisclark13.minecraft.artificing.client.gui.TabDrawType;
import chrisclark13.minecraft.artificing.client.gui.TabSide;
import chrisclark13.minecraft.artificing.core.handler.XMLHandler;
import chrisclark13.minecraft.artificing.core.proxy.ClientProxy;
import chrisclark13.minecraft.artificing.item.ItemGuideBook;
import chrisclark13.minecraft.artificing.lib.Reference;
import chrisclark13.minecraft.artificing.lib.Strings;
import chrisclark13.minecraft.artificing.lib.Textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
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
    private final int PAGE_Y = 17;
    private final int HEADER_FOOTER_HEIGHT = 10;
    
    private static final String HEADER = "§0-- %s§0 --";
    private static final String FOOTER = "§0--- %s§0 ---";
    
    private EntityPlayer player;
    private ItemStack book;
    
    private ArrayList<GuiGuideSection> sections;
    private ArrayList<GuiGuidePage> pages;
    private int currentPage;
    private int guiLeft;
    private int guiTop;
    
    private GuiGuidePageButton leftButton;
    private GuiGuidePageButton rightButton;
    private GuiTabList tabList;
    
    
    public GuiGuideBook(EntityPlayer player, ItemStack book) {
        this.player = player;
        this.book = book;
        this.sections = XMLHandler.parseGuideBookXML(getXMLPathFromStack(book));
        this.pages = new ArrayList<>();
        
        this.currentPage = 0;
        
        this.leftButton = new GuiGuidePageButton(0, true);
        this.rightButton = new GuiGuidePageButton(1, false);
    }
    
    private String getXMLPathFromStack(ItemStack stack) {
        switch (MathHelper.clamp_int(stack.getItemDamage(), 0, 2)) {
            case ItemGuideBook.STARTING_BOOK_META:
            default:
                return Strings.GUIDE_STARTING_XML;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        super.initGui();
        guiLeft = (width - bookWidth) / 2;
        guiTop = (height - bookHeight) / 2;
        
        buttonList.add(leftButton);
        buttonList.add(rightButton);
        
        this.updatePages();
        
        tabList = new GuiTabList();
        for (int i = 0; i < sections.size(); i++) {
            if (sections.get(i).hasBookmark) {
                GuiGuideSectionTab tab = new GuiGuideSectionTab(2, guiLeft + RIGHT_PAGE_X
                        + GuiGuidePage.PAGE_WIDTH + i % 3, guiTop + 15 + 15 * i, sections
                        .get(i), TabSide.RIGHT, TabDrawType.FRONT);
                sections.get(i).bookmark = tab;
                buttonList.add(tab);
                tabList.addTab(tab);
            }
        }
        
        this.updateSectionTabs();
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float par3) {
        this.drawDefaultBackground();
        this.drawBackground();
        
        for (Object o : buttonList) {
            if (o instanceof GuiTab) {
                ((GuiTab) o).drawBackground(mc, mouseX, mouseY);
            }
        }
        
        super.drawScreen(mouseX, mouseY, par3);
        
        // If we haven't updated the pages, don't draw anything!
        if (pages.isEmpty()) {
            return;
        }
        
        // Make sure that we don't break the page count.
        currentPage = MathHelper.clamp_int(currentPage, 0, pages.size() - 1);
        currentPage = (currentPage % 2 == 0) ? currentPage : currentPage - 1;
        
        leftButton.enabled = currentPage > 0;
        rightButton.enabled = currentPage + 2 < pages.size();
        
        // Draw the pages.
        GuiGuidePage leftPage = pages.get(currentPage);
        if (leftPage != null) {
            leftPage.drawContent(mc, mouseX, mouseY);
            leftPage.drawForegroundContent(mc, mouseX, mouseY);
            
            // Draw left page header and footer
            if (currentPage != 0) {
                int yOffset = (HEADER_FOOTER_HEIGHT - ClientProxy.unicodeFontRenderer.FONT_HEIGHT) / 2;
                
                String headerString = String.format(HEADER, book.getDisplayName());
                int lineWidth = ClientProxy.unicodeFontRenderer.getStringWidth(headerString);
                int x = guiLeft + LEFT_PAGE_X + (GuiGuidePage.PAGE_WIDTH - lineWidth) / 2;
                int y = guiTop + PAGE_Y - HEADER_FOOTER_HEIGHT + yOffset;
                
                ClientProxy.unicodeFontRenderer.drawString(headerString, x, y, Reference.BLACK);
                
                String footerString = String.format(FOOTER, Integer.toString(currentPage));
                lineWidth = ClientProxy.unicodeFontRenderer.getStringWidth(footerString);
                x = guiLeft + LEFT_PAGE_X + (GuiGuidePage.PAGE_WIDTH - lineWidth) / 2;
                y = guiTop + PAGE_Y + GuiGuidePage.PAGE_HEIGHT + yOffset;
                ClientProxy.unicodeFontRenderer.drawString(footerString, x, y, Reference.BLACK);
            }
        }
        
        if (currentPage + 1 < pages.size()) {
            GuiGuidePage rightPage = pages.get(currentPage + 1);
            if (rightPage != null) {
                rightPage.drawContent(mc, mouseX, mouseY);
                rightPage.drawForegroundContent(mc, mouseX, mouseY);
                
                // Draw right page header/footer
                int yOffset = (HEADER_FOOTER_HEIGHT - ClientProxy.unicodeFontRenderer.FONT_HEIGHT) / 2;
                
                String headerString = String.format(HEADER, rightPage.section.name);
                int lineWidth = ClientProxy.unicodeFontRenderer.getStringWidth(headerString);
                int x = guiLeft + RIGHT_PAGE_X + (GuiGuidePage.PAGE_WIDTH - lineWidth) / 2;
                int y = guiTop + PAGE_Y - HEADER_FOOTER_HEIGHT + yOffset;
                
                ClientProxy.unicodeFontRenderer.drawString(headerString, x, y, Reference.BLACK);
                
                String footerString = String.format(FOOTER, Integer.toString(currentPage + 1));
                lineWidth = ClientProxy.unicodeFontRenderer.getStringWidth(footerString);
                x = guiLeft + RIGHT_PAGE_X + (GuiGuidePage.PAGE_WIDTH - lineWidth) / 2;
                y = guiTop + PAGE_Y + GuiGuidePage.PAGE_HEIGHT + yOffset;
                ClientProxy.unicodeFontRenderer.drawString(footerString, x, y, Reference.BLACK);
            }
        }
        
        for (Object o : buttonList) {
           if (o instanceof GuiTab) {
               ((GuiTab) o).func_82251_b(mouseX, mouseY);
           }
        }
    }
    
    /** Draw the page screen */
    public void drawBackground() {
        
        double uScale = 1d / (double) imageWidth;
        double vScale = 1d / (double) imageHeight;
        Tessellator tessellator = Tessellator.instance;
        mc.renderEngine.func_110577_a(Textures.GUIDE_BOOK);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(guiLeft, guiTop, 0, 0, 0);
        tessellator.addVertexWithUV(guiLeft, guiTop + bookHeight, 0, 0, bookHeight * vScale);
        tessellator.addVertexWithUV(guiLeft + bookWidth, guiTop + bookHeight, 0,
                bookWidth * uScale, bookHeight * vScale);
        tessellator.addVertexWithUV(guiLeft + bookWidth, guiTop, 0, bookWidth * uScale, 0);
        tessellator.draw();
    }
    
    @Override
    protected void actionPerformed(GuiButton guiButton) {
        if (guiButton.id == 0) {
            currentPage = Math.max(currentPage - 2, 0);
            this.updateSectionTabs();
        } else if (guiButton.id == 1) {
            currentPage = Math.min(currentPage + 2, pages.size() - 1);
            this.updateSectionTabs();
        } else if (guiButton.id == 2) {
            currentPage = ((GuiGuideSectionTab) guiButton).section.startPageIndex;
        }
    }
    
    private void updateSectionTabs() {
        for (GuiGuideSection section : sections) {
            if (section.bookmark != null) {
                int endIndex = section.startPageIndex + section.getPages().size() - 1;
                if (currentPage >= section.startPageIndex && currentPage <= endIndex) {
                    tabList.setActiveTab(section.bookmark);
                }
            }
        }
    }
    
    public boolean doesGuiPauseGame() {
        
        return false;
    }
    
    private void updatePages() {
        pages.clear();
        
        int pageNum = 0;
        for (GuiGuideSection section : sections) {
            section.startPageIndex = pageNum;
            for (GuiGuidePage page : section.getPages()) {
                page.x = guiLeft + ((pageNum % 2 == 0) ? LEFT_PAGE_X : RIGHT_PAGE_X);
                page.y = guiTop + PAGE_Y;
                
                pageNum++;
                pages.add(page);
            }
        }
        
        leftButton.xPosition = guiLeft + LEFT_PAGE_X + 2;
        leftButton.yPosition = guiTop + PAGE_Y + GuiGuidePage.PAGE_HEIGHT - 2;
        
        rightButton.xPosition = guiLeft + RIGHT_PAGE_X + GuiGuidePage.PAGE_WIDTH - 20 - 2;
        rightButton.yPosition = guiTop + PAGE_Y + GuiGuidePage.PAGE_HEIGHT - 2;
    }
}
