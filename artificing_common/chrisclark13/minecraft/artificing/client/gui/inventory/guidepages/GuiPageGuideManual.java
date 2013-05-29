package chrisclark13.minecraft.artificing.client.gui.inventory.guidepages;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.client.gui.GuiContent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;


public class GuiPageGuideManual extends GuiContent {

    protected FontRenderer fontRenderer;

    private int            pageWidth  = 146;
    private int            pageHeight = 180;
    private String         text       = "testing the pages";

    public GuiPageGuideManual(int x, int y, int pageWidth, int pageHeight) {
   
        super(x, y, pageWidth, pageHeight);

        drawOwnBackground = false;
    }

   
    
        

    @Override
    public void drawForeground(Minecraft minecraft, int mouseX, int mouseY) {

        GL11.glDisable(GL11.GL_LIGHTING);

        int line = 0;
        int num = 10;
        for (int i = 0; i > num; ++i) {
            if (line > 20) {
                fontRenderer.drawStringWithShadow(text, pageWidth, line * 9, 0xFF0000);

            }

            for (Object o : fontRenderer.listFormattedStringToWidth(text, 100)) {
                GL11.glColor4f(1, 1, 1, 1);
                fontRenderer.drawStringWithShadow((String) o, pageWidth, line
                        * fontRenderer.FONT_HEIGHT, 0xFF0000);
                line++;
            }
        }
        drawForegroundContent(minecraft, mouseX, mouseY);
        GL11.glEnable(GL11.GL_LIGHTING);
        
    }
}
