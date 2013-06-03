package chrisclark13.minecraft.artificing.client.gui.inventory.guidepages;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import chrisclark13.minecraft.artificing.client.gui.GuiContent;
import chrisclark13.minecraft.artificing.client.gui.UnicodeFontRenderer;
import chrisclark13.minecraft.artificing.core.proxy.ClientProxy;

public class GuiGuidePage extends GuiContent {
    
    public static final int PAGE_WIDTH = 116;
    public static final int PAGE_HEIGHT = 165;
    public int pageNumber;

    public GuiGuidePage() {

        super(0, 0, PAGE_WIDTH, PAGE_HEIGHT);
        this.drawOwnBackground = false;

    }
    
    public void addTextContent(int x, int y, String text) {
        this.addTextContent(x, y, PAGE_WIDTH - x, text);
    }
    
    public void addTextContent(int x, int y, int lineWidth, String text) {
        lineWidth = Math.min(lineWidth, PAGE_WIDTH - x);
        
        children.add(new GuiGuideText(x, y, lineWidth, PAGE_HEIGHT - y, text));
    }
    
    public void addImageContent(int x, int y, int width, int height, String imagePath) {
        children.add(new GuiGuideImage(x, y, width, height, imagePath));
    }
    
    private class GuiGuideText extends GuiContent {

        private String text;
        
        public GuiGuideText(int x, int y, int width, int height, String text) {
            super(x, y, width, height);
            this.text = text;
            
            this.drawOwnBackground = false;
        }
        
        @Override
        protected void drawForeground(Minecraft minecraft, int mouseX, int mouseY) {
            ClientProxy.unicodeFontRenderer.drawSplitString(text, 0, 0, this.width, 0xFF000000);
        }
    }
    
    private class GuiGuideImage extends GuiContent {
        
        private String imagePath;
        
        public GuiGuideImage(int x, int y, int width, int height, String imagePath) {
            super(x, y, width, height);
            this.drawOwnBackground = false;
        }

        @Override
        protected void draw(Minecraft minecraft, int mouseX, int mouseY) {
            Tessellator tessellator = Tessellator.instance;
            minecraft.renderEngine.bindTexture(imagePath);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(0, 0, 0, 0, 0);
            tessellator.addVertexWithUV(0, height, 0, 0, 1);
            tessellator.addVertexWithUV(width, height, 0, 1, 1);
            tessellator.addVertexWithUV(width, 0, 0, 1, 0);
            tessellator.draw();
        }
    }
}