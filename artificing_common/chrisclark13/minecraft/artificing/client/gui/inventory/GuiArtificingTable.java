package chrisclark13.minecraft.artificing.client.gui.inventory;

import java.util.List;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.inventory.ContainerArtificingTable;
import chrisclark13.minecraft.artificing.item.crafting.RuneItemGroupComparer;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.multislotitems.client.gui.GuiMultiSlotItem;
import chrisclark13.minecraft.multislotitems.groups.ItemGroup;
import chrisclark13.minecraft.multislotitems.inventory.MultiSlotItemGridSlot;
import chrisclark13.minecraft.multislotitems.inventory.SlotMultiSlotItem;

public class GuiArtificingTable extends GuiMultiSlotItem {

    private ContainerArtificingTable containerArtificing;
    
	public GuiArtificingTable(InventoryPlayer inventoryPlayer,
			TileArtificingTable artificingTable) {
		super(new ContainerArtificingTable(inventoryPlayer, artificingTable));
		ySize = 226;
		
		containerArtificing = (ContainerArtificingTable) this.inventorySlots;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		// draw text and stuff here
		// the parameters for drawString are: string, x, y, color
		fontRenderer.drawString("Artificing Table", 8, 6, 0x404040);
		// draws "Inventory" or your regional equivalent
		fontRenderer.drawString(
				StatCollector.translateToLocal("container.inventory"), 8,
				ySize - 96 + 2, 0x404040);
		
//		List<ItemGroup> list = ItemGroup.createItemGroupsFromGrid(containerArtificing.getGrid(), new RuneItemGroupComparer());
//		
//		for (int i = 0; i < list.size(); i++) {
//		    ItemGroup group = list.get(i);
//		    int colorIndex = i % 15;
//		    
//		    for (int y = 0; y < group.getGrid().getHeight(); y++) {
//		        for (int x = 0; x < group.getGrid().getWidth(); x++) {
//		            int posX = 32 + x * 16;
//	                int posY = 17 + y * 16;
//	                
//	                if (group.isFilledAtPosition(x, y)) {
//	                    int color = packColorFrom3Floats(EntitySheep.fleeceColorTable[colorIndex]);
//	                    color = color & 0x77FFFFFF;
//	                    
//	                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
//	                    drawRect(posX, posY, posX + 16, posY + 16, color);
//	                }
//	                
//	                int lineX1 = 32 + group.getLeft() * 16;
//	                int lineY1 = 17 + group.getTop() * 16;
//	                int lineX2 = lineX1 + group.getWidth() * 16 - 1;
//	                int lineY2 = lineY1 + group.getHeight() * 16 - 1;
//	                
//	                drawHorizontalLine(lineX1, lineX2, lineY1, 0xFF00CC13);
//	                drawHorizontalLine(lineX1, lineX2, lineY2, 0xFF00CC13);
//	                drawVerticalLine(lineX1, lineY1, lineY2, 0xFF00CC13);
//	                drawVerticalLine(lineX2, lineY1, lineY2, 0xFF00CC13);
//		        }
//		    }
//		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
			int par3) {
		// draw your Gui here, only thing you need to change is the path
		// int texture = mc.renderEngine.getTexture("/gui/trap.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine
				.bindTexture("/mods/artificing/textures/gui/artificingTable.png");
		// this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
