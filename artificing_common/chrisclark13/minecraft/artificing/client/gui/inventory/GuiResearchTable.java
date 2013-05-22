package chrisclark13.minecraft.artificing.client.gui.inventory;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.client.renderer.ArtificingRenderHelper;
import chrisclark13.minecraft.artificing.inventory.ContainerArtificingTable;
import chrisclark13.minecraft.artificing.inventory.ContainerResearchTable;
import chrisclark13.minecraft.artificing.tileentity.TileResearchTable;
import chrisclark13.minecraft.multislotitems.client.gui.GuiMultiSlotItem;


public class GuiResearchTable extends GuiMultiSlotItem {
    
    public GuiResearchTable(InventoryPlayer inventoryPlayer, TileResearchTable ResearchTable) {
        super(new ContainerResearchTable(inventoryPlayer, ResearchTable));
        xSize = 199;
        ySize = 226;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
        // draw text and stuff here
        // the parameters for drawString are: string, x, y, color
        fontRenderer.drawString("Research Table", 8, 6, 0x404040);
        // draws "Inventory" or your regional equivalent
        fontRenderer.drawString(
                StatCollector.translateToLocal("container.inventory"), 8,
                ySize - 96 + 2, 0x404040);
        
    }

    

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2,
            int par3) {
        /* Does not currently render the first ring */
        Tessellator tessellator = Tessellator.instance;
        GL11.glPushMatrix();
        GL11.glTranslatef(0.5f, 0.5f, 0);         
        mc.renderEngine.bindTexture("/mods/artificing/textures/gui/rings.png");
        tessellator.addVertexWithUV(0, 0, 0, 0, 0);
        tessellator.addVertexWithUV(0, 0, 0, 0, 1);
        tessellator.addVertexWithUV(0, 0, 0, 0.5, 1);
        tessellator.addVertexWithUV(0, 0, 0, 1, 0.5);
        GL11.glTranslatef(-0.5F, -0.5f, 0);   
        GL11.glPopMatrix();
       
        
        // draw your Gui here, only thing you need to change is the path
        // int texture = mc.renderEngine.getTexture("/gui/trap.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine
                .bindTexture("/mods/artificing/textures/gui/researchTable.png"); 
        // this.mc.renderEngine.bindTexture(texture);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}

