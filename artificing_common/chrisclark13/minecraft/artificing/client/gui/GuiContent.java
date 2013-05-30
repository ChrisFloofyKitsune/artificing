package chrisclark13.minecraft.artificing.client.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.lib.Textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;

public class GuiContent extends Gui {
    public static final String DEFAULT_TEXTURE = Textures.GUI_PARTS;
    public static final int DEFAULT_TEXTURE_POS = 0;
    public static final int DEFAULT_TEXTURE_PART_SIZE = 16;
    public static final int DEFAULT_TEXTURE_SIZE = 256;
    
    public boolean show;
    public boolean drawOwnBackground;
    public ArrayList<GuiContent> children;
    
    public String backgroundTexture;
    public int textureU;
    public int textureV;
    public int texturePartWidth;
    public int texturePartHeight;
    public int textureWidth;
    public int textureHeight;
    
    public int backgroundColor;
    
    public boolean drawBackgroundTopEdge;
    public boolean drawBackgroundRightEdge;
    public boolean drawBackgroundBottomEdge;
    public boolean drawBackgroundLeftEdge;
    
    public int x;
    public int y;
    public int width;
    public int height;
    public int topSpacing;
    public int leftSpacing;
    
    public GuiContent(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        this.topSpacing = 0;
        this.leftSpacing = 0;
        
        this.show = true;
        this.drawOwnBackground = true;
        this.children = new ArrayList<>();
        
        this.backgroundTexture = DEFAULT_TEXTURE;
        this.backgroundColor = 0xFFFFFF;
        this.textureU = DEFAULT_TEXTURE_POS;
        this.textureV = DEFAULT_TEXTURE_POS;
        this.texturePartWidth = DEFAULT_TEXTURE_PART_SIZE;
        this.texturePartHeight = DEFAULT_TEXTURE_PART_SIZE;
        this.textureWidth = DEFAULT_TEXTURE_SIZE;
        this.textureHeight = DEFAULT_TEXTURE_SIZE;
        
        this.drawBackgroundTopEdge = true;
        this.drawBackgroundRightEdge = true;
        this.drawBackgroundBottomEdge = true;
        this.drawBackgroundLeftEdge = true;
    }
    
    public void drawContent(Minecraft minecraft, int mouseX, int mouseY) {
        if (this.show) {
            
            mouseX -= this.x + leftSpacing;
            mouseY -= this.y + topSpacing;
            
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glTranslatef(this.x, this.y, 0);
            
            if (this.drawOwnBackground) {
                this.drawBackgroundTexture(minecraft);
            }
            
            GL11.glTranslatef(leftSpacing, topSpacing, 0);
            
            this.draw(minecraft, mouseX, mouseY);
            
            for (GuiContent child : children) {
                child.drawContent(minecraft, mouseX, mouseY);
            }
            
            //GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }
    
    public void drawForegroundContent(Minecraft minecraft, int mouseX, int mouseY) {
        if (this.show) {
            
            mouseX -= this.x + leftSpacing;
            mouseY -= this.y + topSpacing;
            
            GL11.glPushMatrix();
            GL11.glTranslatef(this.x + this.leftSpacing, this.y + this.topSpacing, 0);
            GL11.glDisable(GL11.GL_LIGHTING);
            
            for (GuiContent child : children) {
                child.drawForegroundContent(minecraft, mouseX, mouseY);
            }
            
            this.drawForeground(minecraft, mouseX, mouseY);
            
            //GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }
    
    protected void draw(Minecraft minecraft, int mouseX, int mouseY) {
    }
    
    protected void drawForeground(Minecraft minecraft, int mouseX, int mouseY) {
    }
    
    protected void drawBackgroundTexture(Minecraft minecraft) {
        minecraft.renderEngine.bindTexture(backgroundTexture);
        
        float r = ((backgroundColor >> 16) & 0xFF) / 255F;
        float g = ((backgroundColor >> 8) & 0xFF) / 255F;
        float b = ((backgroundColor) & 0xFF) / 255F;
        
        GL11.glColor4f(r, g, b, 1f);
        
        int i = 0;
        int j = 0;
        int u = 0;
        int v = 0;
        int drawWidth = 0;
        int drawHeight = 0;
        boolean widthTooSmall = this.width <= texturePartWidth * 2;
        boolean heightTooSmall = this.height <= texturePartHeight * 2;
        
        while (j < this.height) {
            if (heightTooSmall) {
                if (j == 0) {
                    drawHeight = this.height / 2;
                    if (drawBackgroundTopEdge) {
                        v = textureV;
                    } else {
                        v = textureV + texturePartHeight;
                    }
                } else {
                    drawHeight = this.height - this.height / 2;
                    if (drawBackgroundRightEdge) {
                        v = (textureV + texturePartHeight * 3) - drawHeight;
                    } else {
                        v = (textureV + texturePartHeight * 2) - drawHeight;
                    }
                }
            } else {
                if (j == 0) {
                    drawHeight = texturePartHeight;
                    if (drawBackgroundTopEdge) {
                        v = textureV;
                    } else {
                        v = textureV + texturePartHeight;
                    }
                } else if (j + texturePartHeight < this.height) {
                    if (j + texturePartHeight * 2 >= this.height) {
                        drawHeight = (this.height - texturePartHeight) - j;
                    } else {
                        drawHeight = texturePartHeight;
                    }
                    
                    v = textureV + texturePartHeight;
                } else {
                    drawHeight = texturePartHeight;
                    if (drawBackgroundBottomEdge) {
                        v = textureV + texturePartHeight * 2;
                    } else {
                        v = textureV + texturePartHeight;
                    }
                }
            }
            
            while (i < this.width) {
                if (widthTooSmall) {
                    if (i == 0) {
                        drawWidth = this.width / 2;
                        if (drawBackgroundLeftEdge) {
                            u = textureU;
                        } else {
                            u = textureU + texturePartWidth;
                        }
                    } else {
                        drawWidth = this.width - this.width / 2;
                        if (drawBackgroundRightEdge) {
                            u = (textureU + texturePartWidth * 3) - drawWidth;
                        } else {
                            u = (textureU + texturePartWidth * 2) - drawWidth;
                        }
                    }
                } else {
                    if (i == 0) {
                        drawWidth = texturePartWidth;
                        if (drawBackgroundLeftEdge) {
                            u = textureU;
                        } else {
                            u = textureU + texturePartWidth;
                        }
                    } else if (i + texturePartWidth < this.width) {
                        if (i + texturePartWidth * 2 >= this.width) {
                            drawWidth = (this.width - texturePartWidth) - i;
                        } else {
                            drawWidth = texturePartWidth;
                        }
                        
                        u = textureU + texturePartWidth;
                    } else {
                        drawWidth = texturePartWidth;
                        if (drawBackgroundRightEdge) {
                            u = textureU + texturePartWidth * 2;
                        } else {
                            u = textureU + texturePartWidth;
                        }
                    }
                }
                
                drawTexturePart(i, j, u, v, drawWidth, drawHeight);
                
                i += drawWidth;
            }
            
            i = 0;
            j += drawHeight;
        }
    }
    
    protected void drawTexturePart(int x, int y, int u, int v, int width, int height) {
        float uScale = 1F / (float) textureWidth;
        float vScale = 1F / (float) textureHeight;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + height), (double) this.zLevel,
                (double) ((float) (u + 0) * uScale), (double) ((float) (v + height) * vScale));
        tessellator.addVertexWithUV((double) (x + width), (double) (y + height),
                (double) this.zLevel, (double) ((float) (u + width) * uScale),
                (double) ((float) (v + height) * vScale));
        tessellator.addVertexWithUV((double) (x + width), (double) (y + 0), (double) this.zLevel,
                (double) ((float) (u + width) * uScale), (double) ((float) (v + 0) * vScale));
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) this.zLevel,
                (double) ((float) (u + 0) * uScale), (double) ((float) (v + 0) * vScale));
        tessellator.draw();
    }
}
