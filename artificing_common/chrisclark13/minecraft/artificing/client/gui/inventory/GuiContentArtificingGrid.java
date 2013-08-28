package chrisclark13.minecraft.artificing.client.gui.inventory;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import chrisclark13.minecraft.artificing.client.gui.GuiContent;
import chrisclark13.minecraft.artificing.inventory.ContainerArtificingTable;
import chrisclark13.minecraft.artificing.lib.Textures;

public class GuiContentArtificingGrid extends GuiContent {
    
    private final static int GRID_SQUARE_SIZE = 16;
    private final static int BORDER_SIZE = 2;
    
    public GuiContentArtificingGrid() {
        super(ContainerArtificingTable.GRID_CENTER_X, ContainerArtificingTable.GRID_CENTER_Y, 0, 0);
        
        this.backgroundTexture = Textures.GUI_PARTS;
        this.textureU = 130;
        this.textureV = 2;
    }
    
    @Override
    protected void draw(Minecraft minecraft, int mouseX, int mouseY) {
        minecraft.renderEngine.func_110577_a(backgroundTexture);
        
        float r = ((backgroundColor >> 16) & 0xFF) / 255F;
        float g = ((backgroundColor >> 8) & 0xFF) / 255F;
        float b = ((backgroundColor) & 0xFF) / 255F;
        
        GL11.glColor4f(r, g, b, 1f);
        
        // Draw border, assumes width or height is always a multiple of texturePartWidth or texturePartHeight.
        if (width > 0 && height > 0) {
            // Draw corner pieces
            drawTexturePart(-BORDER_SIZE, -BORDER_SIZE, textureU - BORDER_SIZE, textureV
                    - BORDER_SIZE, BORDER_SIZE, BORDER_SIZE);
            drawTexturePart(width, -BORDER_SIZE, textureU + texturePartWidth * 3, textureV - BORDER_SIZE, BORDER_SIZE, BORDER_SIZE);
            drawTexturePart(width, height, textureU + texturePartWidth
                    * 3, textureV + texturePartHeight * 3, BORDER_SIZE,
                    BORDER_SIZE);
            drawTexturePart(-BORDER_SIZE, height, textureU - BORDER_SIZE, textureV
                    + texturePartHeight * 3, BORDER_SIZE, BORDER_SIZE);
            
            // Draw x pieces
            for (int i = 0; i < width; i += texturePartWidth) {
                int u = (i == 0) ? textureU : (i + texturePartWidth >= width) ? textureU + texturePartWidth * 2 : textureU + texturePartWidth;
                drawTexturePart(i, -BORDER_SIZE, u, textureV - BORDER_SIZE,
                        texturePartWidth, BORDER_SIZE);
                drawTexturePart(i, height, u, textureV + texturePartHeight
                        * 3, texturePartWidth, BORDER_SIZE);
            }
            
            // Draw y pieces
            for (int i = 0; i < height; i += texturePartHeight) {
                int v = (i == 0) ? textureV : (i + texturePartHeight >= height) ? textureV + texturePartHeight * 2 : textureV + texturePartHeight;
                drawTexturePart(-BORDER_SIZE, i, textureU - BORDER_SIZE, v,
                        BORDER_SIZE, texturePartHeight);
                drawTexturePart(width, i, textureU + texturePartWidth * 3, v,
                        BORDER_SIZE, texturePartHeight);
            }
        }
    }
    
    public void setGridSizeAndPosition(int gridWidth, int gridHeight, int guiLeft, int guiTop) {
        this.width = gridWidth * GRID_SQUARE_SIZE;
        this.height = gridHeight * GRID_SQUARE_SIZE;
        
        this.x = guiLeft + ContainerArtificingTable.GRID_CENTER_X - width / 2;
        this.y = guiTop + ContainerArtificingTable.GRID_CENTER_Y - height / 2;
    }
    
}
