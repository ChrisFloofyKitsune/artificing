package chrisclark13.minecraft.artificing.client.gui.inventory.guidepages;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import chrisclark13.minecraft.artificing.client.gui.GuiContent;
import chrisclark13.minecraft.artificing.core.helper.ResourceLocationHelper;
import chrisclark13.minecraft.artificing.core.proxy.ClientProxy;

public class GuiGuidePage extends GuiContent {
    
    public static final int PAGE_WIDTH = 116;
    public static final int PAGE_HEIGHT = 145;
    public int pageNumber;
    public GuiGuideSection section;
    
    public GuiGuidePage() {
        super(0, 0, PAGE_WIDTH, PAGE_HEIGHT);
        this.drawOwnBackground = false;
    }
    
    public void addTextContent(int x, int y, String text) {
        this.addTextContent(x, y, PAGE_WIDTH - x, GuideTextAlignment.LEFT, text);
    }
    
    public void addTextContent(int x, int y, int lineWidth, GuideTextAlignment alignment, String text) {
        lineWidth = Math.min(lineWidth, PAGE_WIDTH - x);
        
        children.add(new GuiGuideText(x, y, lineWidth, PAGE_HEIGHT - y, alignment, text));
    }
    
    public void addImageContent(int x, int y, int width, int height, String imagePath) {
        children.add(new GuiGuideImage(x, y, width, height, ResourceLocationHelper.create((imagePath == null) ? "" : imagePath)));
    }
    
    private class GuiGuideText extends GuiContent {

        private String text;
        private GuideTextAlignment alignment;
        
        private static final int BLACK = 0xFF000000;
        
        public GuiGuideText(int x, int y, int width, int height, GuideTextAlignment alignment, String text) {
            super(x, y, width, height);
            this.text = ClientProxy.unicodeFontRenderer.trimStringNewline(text);;
            this.alignment = alignment;
            
            this.drawOwnBackground = false;
        }
        
        @Override
        protected void drawForeground(Minecraft minecraft, int mouseX, int mouseY) {
            int line = 0;
            int fontHeight = ClientProxy.unicodeFontRenderer.FONT_HEIGHT;
            for(String s : text.split("\n")) {
                for(Object o : ClientProxy.unicodeFontRenderer.listFormattedStringToWidth(s, width)) {
                    
                    String text = ClientProxy.unicodeFontRenderer.trimStringNewline((String) o);
                    int stringWidth = ClientProxy.unicodeFontRenderer.getStringWidth(text);
                    switch (alignment) {
                        case LEFT:
                            ClientProxy.unicodeFontRenderer.drawString(text, 0, line * fontHeight, BLACK);
                            break;
                        case CENTER:
                            ClientProxy.unicodeFontRenderer.drawString(text, (width - stringWidth) / 2, line * fontHeight, BLACK);
                            break;
                        case RIGHT:
                            ClientProxy.unicodeFontRenderer.drawString(text, width - stringWidth, line * fontHeight, BLACK);
                            break;
                    }
                    line++;
                }
            }
        }
    }
    
    private class GuiGuideImage extends GuiContent {
        
        private ResourceLocation imageResource;
        
        public GuiGuideImage(int x, int y, int width, int height, ResourceLocation imageResouce) {
            super(x, y, width, height);
            this.imageResource = imageResouce;
            
            this.drawOwnBackground = false;
        }

        @Override
        protected void draw(Minecraft minecraft, int mouseX, int mouseY) {
            Tessellator tessellator = Tessellator.instance;
            minecraft.renderEngine.bindTexture(imageResource);
//            minecraft.renderEngine.bindTexture(Textures.GUI_PARTS);
//            System.out.println(imagePath);
            
//            this.drawTexturedModalRect(0, 0, 0, 0, width, height);
            
            GL11.glColor4f(1, 1, 1, 1);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(0, 0, 0, 0, 0);
            tessellator.addVertexWithUV(0, height, 0, 0, 1);
            tessellator.addVertexWithUV(width, height, 0, 1, 1);
            tessellator.addVertexWithUV(width, 0, 0, 1, 0);
            tessellator.draw();
        }
    }
}