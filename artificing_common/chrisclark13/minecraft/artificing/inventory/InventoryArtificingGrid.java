package chrisclark13.minecraft.artificing.inventory;

import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.multislotitems.inventory.ContainerMultiSlotItem;
import chrisclark13.minecraft.multislotitems.inventory.InventoryMultiSlotItemGrid;
import chrisclark13.minecraft.multislotitems.inventory.MultiSlotItemGridSlot;
import chrisclark13.minecraft.multislotitems.inventory.SlotMultiSlotItem;

public class InventoryArtificingGrid extends InventoryMultiSlotItemGrid {

	TileArtificingTable parent;
	public InventoryArtificingGrid(TileArtificingTable parent, int width, int height) {
		super(width, height);
		
		this.parent = parent;
	}
	
	@Override
	protected SlotMultiSlotItem createSlotForContainer(ContainerMultiSlotItem container,
	        MultiSlotItemGridSlot gridSlot, int displayX, int displayY, int slotWidth,
	        int slotHeight) {
	    // TODO Auto-generated method stub
	    return new SlotArtificingGrid(container, gridSlot, displayX, displayY, slotWidth, slotHeight);
	}
}
