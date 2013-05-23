package chrisclark13.minecraft.artificing.client.gui.inventory;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.inventory.ContainerResearchTable;
import chrisclark13.minecraft.artificing.lib.Reference;
import chrisclark13.minecraft.artificing.lib.Textures;
import chrisclark13.minecraft.artificing.tileentity.TileResearchTable;
import chrisclark13.minecraft.multislotitems.client.gui.GuiMultiSlotItem;

public class GuiResearchTable extends GuiMultiSlotItem {
    
    public ContainerResearchTable conResearchTable;
    public TileResearchTable researchTable;
    private float animTimer;
    private final float ANIM_PERIOD = 20 * Reference.TICKS_IN_SECOND;
    
    public GuiResearchTable(InventoryPlayer inventoryPlayer, TileResearchTable researchTable) {
        super(new ContainerResearchTable(inventoryPlayer, researchTable));
        xSize = 199;
        ySize = 226;
        
        this.conResearchTable = (ContainerResearchTable) inventorySlots;
        this.researchTable = researchTable;
        
        animTimer = 0;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
        // draw text and stuff here
        // the parameters for drawString are: string, x, y, color
        fontRenderer.drawString("Research Table", 8, 6, 0x404040);
        // draws "Inventory" or your regional equivalent
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8,
                ySize - 96 + 2, 0x404040);
        
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int par2, int par3) {
        
        animTimer = ((researchTable.tickCount + partialTicks) % ANIM_PERIOD) / ANIM_PERIOD;
        
        // draw your Gui here, only thing you need to change is the path
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture("/mods/artificing/textures/gui/researchTable.png");
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        Tessellator tessellator = Tessellator.instance;
        mc.renderEngine.bindTexture(Textures.RINGS);
        
        
        //Render rings
        final float RADIUS = 255F / 2F;
        final float SCALE = 0.5f;
        final int CENTER_X = 88;
        final int CENTER_Y = 66;
        float rotation = animTimer * 360;
        
        GL11.glPushMatrix();
        {
            GL11.glTranslatef(x + CENTER_X, y + CENTER_Y, 0);
            GL11.glRotatef(rotation, 0, 0, 1);
            GL11.glScalef(SCALE, SCALE, 1);
            GL11.glTranslatef(-RADIUS, -RADIUS, 0);
            
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(0, 0, 0, 0, 0);
            tessellator.addVertexWithUV(0, 255, 0, 0, 1);
            tessellator.addVertexWithUV(255, 255, 0, 0.5, 1);
            tessellator.addVertexWithUV(255, 0, 0, 0.5, 0);
            tessellator.draw();
        }
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        {
            GL11.glTranslatef(x + CENTER_X, y + CENTER_Y, 0);
            GL11.glRotatef(-rotation, 0, 0, 1);
            GL11.glScalef(SCALE, SCALE, 1);
            GL11.glTranslatef(-RADIUS, -RADIUS, 0);
            
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(0, 0, 0, 0.5, 0);
            tessellator.addVertexWithUV(0, 255, 0, 0.5, 1);
            tessellator.addVertexWithUV(255, 255, 0, 1, 1);
            tessellator.addVertexWithUV(255, 0, 0, 1, 0);
            tessellator.draw();
        }
        GL11.glPopMatrix();
    }
    
    
}
