package chrisclark13.minecraft.artificing.client.gui.inventory.guidepages;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import chrisclark13.minecraft.artificing.client.gui.GuiContent;

public class GuiGuidePage extends GuiContent {
    
    public static final int PAGE_WIDTH = 110;
    public static final int PAGE_HEIGHT = 165;
    public int pageNumber;

    public GuiGuidePage(int x, int y) {

        super(x, y, PAGE_WIDTH, PAGE_HEIGHT);
        this.drawOwnBackground = false;

    }
    
    public void addTextContent(int x, int y, String text) {
        this.addTextContent(x, y, PAGE_WIDTH - x, text);
    }
    
    public void addTextContent(int x, int y, int lineWidth, String text) {
        lineWidth = Math.min(lineWidth, PAGE_WIDTH - x);
        
        children.add(new GuiGuideText(x, y, lineWidth, PAGE_HEIGHT - y, text));
    }
    
    private class GuiGuideText extends GuiContent {

        private String text;
        private int textColor;
        
        public GuiGuideText(int x, int y, int width, int height, String text) {
            super(x, y, width, height);
            this.text = text;
            
            this.drawOwnBackground = false;
        }
        
        @Override
        protected void drawForeground(Minecraft minecraft, int mouseX, int mouseY) {
            minecraft.fontRenderer.drawSplitString(text, 0, 0, this.width, 0);
        }
    }
}