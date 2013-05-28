package chrisclark13.minecraft.artificing.client.gui;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import chrisclark13.minecraft.artificing.lib.Textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;

public class GuiTab extends GuiButton {
    
    protected final int DEFAULT_SIZE = 28;
    protected final int DEFAULT_OVERHANG = 4;
    protected final int DEFAULT_TEXTURE_WIDTH = 256;
    protected final int DEFAULT_TEXTURE_HEIGHT = 256;
    
    protected boolean pressed;
    protected String texture;
    protected int textureWidth;
    protected int textureHeight;
    
    protected boolean hasIcon;
    protected String iconTexture;
    protected double uMin;
    protected double vMin;
    protected double uMax;
    protected double vMax;
    
    public TabSide side;
    public GuiTabList parentList;
    protected boolean active;
    protected TabDrawType type;
    protected int overhang;
    protected int color;
    
    public GuiTab(int id, int displayX, int displayY, String hoverString) {
        this(id, displayX, displayY, hoverString, TabSide.RIGHT, TabDrawType.FRONT);
    }
    
    public GuiTab(int id, int xPosition, int yPosition, String hoverString, TabSide side) {
        this(id, xPosition, yPosition, hoverString, side, TabDrawType.FRONT);
    }
    
    public GuiTab(int id, int xPosition, int yPosition, String hoverString, TabSide side,
            TabDrawType type) {
        super(id, xPosition, yPosition, hoverString);
        this.side = side;
        this.type = type;
        this.pressed = false;
        this.texture = Textures.TAB_PARTS;
        this.color = 0xFFFFFF;
        
        this.width = DEFAULT_SIZE;
        this.height = DEFAULT_SIZE;
        this.overhang = DEFAULT_OVERHANG;
        this.textureWidth = DEFAULT_TEXTURE_WIDTH;
        this.textureHeight = DEFAULT_TEXTURE_HEIGHT;
    }
    
    public void drawBackground(Minecraft minecraft, int mouseX, int mouseY) {
        if (this.drawButton && !this.active) {
            int textureX = 0;
            int textureY = 0;
            int xOffset = 0;
            int yOffset = 0;
            int widthOffset = 0;
            int heightOffset = 0;
            
            switch (this.side) {
                case TOP:
                    textureX = (!this.pressed) ? 0 : width * 3;
                    textureX += this.type.ordinal() * width;
                    textureY = textureHeight / 2;
                    yOffset = -height;
                    heightOffset = overhang;
                    break;
                case RIGHT:
                    textureX = (!this.pressed) ? (width + overhang) * 2 : (width + overhang) * 6;
                    textureY = this.type.ordinal() * height;
                    xOffset = -overhang;
                    widthOffset = overhang;
                    break;
                case BOTTOM:
                    textureX = (!this.pressed) ? 0 : width * 3;
                    textureX += this.type.ordinal() * width;
                    textureY = textureHeight / 2;
                    textureY += (height + overhang) * 2;
                    yOffset = -overhang;
                    heightOffset = overhang;
                    break;
                case LEFT:
                    textureX = (!this.pressed) ? 0 : (width + overhang) * 4;
                    textureY = this.type.ordinal() * height;
                    widthOffset = overhang;
                    xOffset = -width;
                    break;
            }
            
            minecraft.renderEngine.bindTexture(texture);
            
            float r = ((color >> 16) & 0xFF) / 255F;
            float g = ((color >> 8) & 0xFF) / 255F;
            float b = (color & 0xFF) / 255F;
            
            GL11.glColor4f(r, g, b, 1.0F);
            
            this.drawTabTexture(this.xPosition + xOffset, this.yPosition + yOffset,
                    textureX, textureY, width + widthOffset, height + heightOffset);
        }
    }
    
    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        this.field_82253_i = this.isMouseOver(mouseX, mouseY);
        this.mouseDragged(minecraft, mouseX, mouseY);
        
        if (this.drawButton && this.active) {
            int textureX = 0;
            int textureY = 0;
            int xOffset = 0;
            int yOffset = 0;
            int widthOffset = 0;
            int heightOffset = 0;
            
            switch (this.side) {
                case TOP:
                    textureX = (!this.pressed) ? 0 : width * 3;
                    textureX += this.type.ordinal() * width;
                    textureY = textureHeight / 2;
                    textureY += this.height + overhang;
                    yOffset = -height;
                    heightOffset = overhang;
                    break;
                case RIGHT:
                    textureX = (!this.pressed) ? (width + overhang) * 3 : (width + overhang) * 7;
                    textureY = this.type.ordinal() * height;
                    xOffset = -overhang;
                    widthOffset = overhang;
                    break;
                case BOTTOM:
                    textureX = (!this.pressed) ? 0 : width * 3;
                    textureX += this.type.ordinal() * width;
                    textureY = textureHeight / 2;
                    textureY += (height + overhang) * 3;
                    yOffset = -overhang;
                    heightOffset = overhang;
                    break;
                case LEFT:
                    textureX = (!this.pressed) ? (width + overhang) : (width + overhang) * 5;
                    textureY = this.type.ordinal() * height;
                    widthOffset = overhang;
                    xOffset = -width;
                    break;
            }
            
            minecraft.renderEngine.bindTexture(texture);
            
            float r = ((color >> 16) & 0xFF) / 255F;
            float g = ((color >> 8) & 0xFF) / 255F;
            float b = (color & 0xFF) / 255F;
            
            GL11.glColor4f(r, g, b, 1.0F);
            
            this.drawTabTexture(this.xPosition + xOffset, this.yPosition + yOffset,
                    textureX, textureY, width + widthOffset, height + heightOffset);
        }
    }
    
    @Override
    public void func_82251_b(int mouseX, int mouseY) {
        this.drawForeground(Minecraft.getMinecraft(), mouseX, mouseY);
    }
    
    public void drawForeground(Minecraft minecraft, int mouseX, int mouseY) {
        if (this.field_82253_i && this.drawButton) {
            this.drawHoveringText(Arrays.asList(displayString), mouseX, mouseY,
                    minecraft.fontRenderer);
        }
    }
    
    /**
     * Fired when the mouse button is dragged. Equivalent of
     * MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft minecraft, int mouseX, int mouseY) {
        if (pressed && !isMouseOver(mouseX, mouseY)) {
            pressed = false;
        }
        
    }
    
    /**
     * Fired when the mouse button is released. Equivalent of
     * MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int par1, int par2) {
        if (pressed) {
            if (this.parentList != null) {
                if (!this.active) {
                    parentList.setActiveTab(this);
                } else {
                    parentList.setActiveTab(null);
                }
            } else if (!this.active) {
                this.setActive(true);
            } else {
                this.setActive(false);
            }
        }
        
        pressed = false;
    }
    
    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of
     * MouseListener.mousePressed(MouseEvent e).
     */
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        if (this.enabled && this.drawButton && isMouseOver(mouseX, mouseY)) {
            pressed = true;
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isMouseOver(int mouseX, int mouseY) {
        int x = (this.side == TabSide.LEFT) ? xPosition - width : xPosition;
        int y = (this.side == TabSide.TOP) ? yPosition - height : yPosition;
        
        return mouseX >= x && mouseY >= y && mouseX < x + this.width && mouseY < y + this.height;
    }
    
    public void setActive(boolean active) {
        if (active && !this.active) {
            this.active = true;
            this.activate();
        } else if (!active && this.active) {
            this.active = false;
            this.deactivate();
        }
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setColor(int color) {
        this.color = color;
    }
    
    public void resetColor() {
        this.color = 0xFFFFFF;
    }
    
    public int getColor() {
        return color;
    }
    
    public void activate() {
        
    }
    
    public void deactivate() {
        
    }
    
    protected void drawHoveringText(List<String> par1List, int x, int y, FontRenderer font) {
        if (!par1List.isEmpty()) {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            
            int k = 0;
            Iterator<String> iterator = par1List.iterator();
            
            while (iterator.hasNext()) {
                String s = iterator.next();
                int l = font.getStringWidth(s);
                
                if (l > k) {
                    k = l;
                }
            }
            
            int i1 = x + 12;
            int j1 = y - 12;
            int k1 = 8;
            
            if (par1List.size() > 1) {
                k1 += 2 + (par1List.size() - 1) * 10;
            }
            
            // if (i1 + k > this.width)
            // {
            // i1 -= 28 + k;
            // }
            //
            // if (j1 + k1 + 6 > this.height)
            // {
            // j1 = this.height - k1 - 6;
            // }
            
            this.zLevel = 300.0F;
            // itemRenderer.zLevel = 300.0F;
            int l1 = -267386864;
            this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
            
            for (int k2 = 0; k2 < par1List.size(); ++k2) {
                String s1 = (String) par1List.get(k2);
                font.drawStringWithShadow(s1, i1, j1, color);
                
                if (k2 == 0) {
                    j1 += 2;
                }
                
                j1 += 10;
            }
            
            this.zLevel = 0.0F;
            // itemRenderer.zLevel = 0.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }
    
    protected void drawTabTexture(int x, int y, int u, int v, int width, int height)
    {
        float uScale = 1F / (float) textureWidth;
        float vScale = 1F / (float) textureHeight;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + height), (double)this.zLevel, (double)((float)(u + 0) * uScale), (double)((float)(v + height) * vScale));
        tessellator.addVertexWithUV((double)(x + width), (double)(y + height), (double)this.zLevel, (double)((float)(u + width) * uScale), (double)((float)(v + height) * vScale));
        tessellator.addVertexWithUV((double)(x + width), (double)(y + 0), (double)this.zLevel, (double)((float)(u + width) * uScale), (double)((float)(v + 0) * vScale));
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)this.zLevel, (double)((float)(u + 0) * uScale), (double)((float)(v + 0) * vScale));
        tessellator.draw();
    }
}
