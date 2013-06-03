package chrisclark13.minecraft.artificing.client.gui.inventory.guidepages;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.lib.Textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;

public class GuiGuidePageButton extends GuiButton {

    private boolean leftFacing;
    
    public GuiGuidePageButton(int id, boolean leftFacing) {
        super(id, 0, 0, 20, 10, "");
        
        this.leftFacing = leftFacing;
    }
    
    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
            FontRenderer fontrenderer = par1Minecraft.fontRenderer;
            par1Minecraft.renderEngine.bindTexture(Textures.GUIDE_BOOK);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            
            int i = (this.leftFacing) ? 0 : 1;
            int k = (this.enabled) ? 1 : 0;

            Tessellator tessellator = Tessellator.instance;
            
            double uScale = 1F / 256F;
            double vScale = 1F / 256F;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(xPosition, yPosition, 0, (width * 2 * k) * uScale, (180 + height * 2 * i) * vScale);
            tessellator.addVertexWithUV(xPosition, yPosition + height, 0, (width * 2 * k) * uScale, (180 + height * 2 * i + height * 2) * vScale);
            tessellator.addVertexWithUV(xPosition + width, yPosition + height, 0, (width * 2 * k + width * 2) * uScale, (180 + height * 2 * i + height * 2) * vScale);
            tessellator.addVertexWithUV(xPosition + width, yPosition, 0, (width * 2 * k + width * 2) * uScale, (180 + height * 2 * i) * vScale);
            tessellator.draw();
            
            this.mouseDragged(par1Minecraft, par2, par3);
        }
    }
    
    
}
