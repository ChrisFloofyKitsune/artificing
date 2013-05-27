package chrisclark13.minecraft.artificing.client.gui;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.lib.Textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.common.ForgeDirection;

public class GuiTab extends GuiButton {

    private final int DEFAULT_SIZE_SHORT = 28;
    private final int DEFAULT_SIZE_LONG = 29;
    private boolean pressed;
    private String texture;
    
    private boolean hasIcon;
    private String iconTexture;
    private double uMin;
    private double vMin;
    private double uMax;
    private double vMax;
    
    public ForgeDirection side;
    public GuiTabList parentList;
    private int listId;
    private boolean active;
    
    public GuiTab(int id, int displayX, int displayY) {
        this(id, displayX, displayY, ForgeDirection.EAST);
    }
    
    public GuiTab(int id, int xPosition, int yPosition, ForgeDirection side) {
        super(id, xPosition, yPosition, "");
        this.side = side;
        this.pressed = false;
        texture = Textures.GUI_PARTS;
        
        switch (side) {
            case UP:
            case NORTH:
                this.yPosition -= DEFAULT_SIZE_LONG;
                this.width = DEFAULT_SIZE_SHORT;
                this.height = DEFAULT_SIZE_LONG;
                break;
            default:
            case EAST:
                this.width = DEFAULT_SIZE_LONG;
                this.height = DEFAULT_SIZE_SHORT;
                break;
            case DOWN:
            case SOUTH:
                this.width = DEFAULT_SIZE_SHORT;
                this.height = DEFAULT_SIZE_LONG;
                break;
            case WEST:
                this.xPosition -= DEFAULT_SIZE_LONG;
                this.width = DEFAULT_SIZE_LONG;
                this.height = DEFAULT_SIZE_SHORT;
                break;
        }
    }
    
    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
            FontRenderer fontrenderer = par1Minecraft.fontRenderer;
            par1Minecraft.renderEngine.bindTexture(texture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int k = this.getHoverState(this.field_82253_i);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            this.mouseDragged(par1Minecraft, par2, par3);
            int l = 14737632;

            if (!this.enabled)
            {
                l = -6250336;
            }
            else if (this.field_82253_i)
            {
                l = 16777120;
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
        }
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft minecraft, int mouseX, int mouseY) {
        if (pressed && !isMouseOver(mouseX, mouseY)) {
            pressed = false;
        }
        
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int par1, int par2) {
        if (pressed) {
            pressed = false;
        }
        
        pressed = false;
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY)
    {
        if (super.mousePressed(minecraft, mouseX, mouseY)) {
            pressed = true;
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }
    
    public void activate() {
        
    }
    
    public void deactivate() {
        
    }
}
