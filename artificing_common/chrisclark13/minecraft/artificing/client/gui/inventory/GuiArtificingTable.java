package chrisclark13.minecraft.artificing.client.gui.inventory;

import java.util.List;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.inventory.ContainerArtificingTable;
import chrisclark13.minecraft.artificing.item.crafting.RuneItemGroupComparer;
import chrisclark13.minecraft.artificing.lib.Textures;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.multislotitems.client.gui.GuiMultiSlotItem;
import chrisclark13.minecraft.multislotitems.groups.ItemGroup;
import chrisclark13.minecraft.multislotitems.inventory.MultiSlotItemGridSlot;
import chrisclark13.minecraft.multislotitems.inventory.SlotMultiSlotItem;

public class GuiArtificingTable extends GuiMultiSlotItem {
    
    private ContainerArtificingTable containerArtificing;
    private TileArtificingTable artificingTable;
    
    public GuiArtificingTable(InventoryPlayer inventoryPlayer, TileArtificingTable artificingTable) {
        super(new ContainerArtificingTable(inventoryPlayer, artificingTable));
        ySize = 226;
        
        containerArtificing = (ContainerArtificingTable) this.inventorySlots;
        this.artificingTable = artificingTable;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
        // draw text and stuff here
        // the parameters for drawString are: string, x, y, color
        fontRenderer.drawString("Artificing Table", 8, 6, 0x404040);
        // draws "Inventory" or your regional equivalent
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8,
                ySize - 96 + 2, 0x404040);
        
        int line = 0;
        int errors = artificingTable.manager.getErrorMessages().size();
        for (String string : artificingTable.manager.getErrorMessages()) {
            if (line > 20) {
                fontRenderer.drawStringWithShadow(errors + " more.." , xSize, line * fontRenderer.FONT_HEIGHT, 0xFF0000);
                break;
            }
            for (Object o : fontRenderer.listFormattedStringToWidth(string, 100)) {
                GL11.glColor4f(1, 1, 1, 1);
                fontRenderer.drawStringWithShadow((String) o, xSize, line * fontRenderer.FONT_HEIGHT, 0xFF0000);
                line++;
            }
            line++;
            errors--;
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        // draw your Gui here, only thing you need to change is the path
        // int texture = mc.renderEngine.getTexture("/gui/trap.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(Textures.ARTIFICING_TABLE);
        // this.mc.renderEngine.bindTexture(texture);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
