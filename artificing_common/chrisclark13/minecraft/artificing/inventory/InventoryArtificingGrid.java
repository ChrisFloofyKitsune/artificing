package chrisclark13.minecraft.artificing.inventory;

import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.multislotitems.inventory.ContainerMultiSlotItem;
import chrisclark13.minecraft.multislotitems.inventory.InventoryMultiSlotItemGrid;
import chrisclark13.minecraft.multislotitems.inventory.GridSlot;
import chrisclark13.minecraft.multislotitems.inventory.SlotMultiSlotItem;

public class InventoryArtificingGrid extends InventoryMultiSlotItemGrid {

	private TileArtificingTable artificingTable;
	public InventoryArtificingGrid(TileArtificingTable artificingTable, int width, int height) {
		super(width, height);
		
		this.artificingTable = artificingTable;
	}
	
	@Override
	protected SlotMultiSlotItem createSlotForContainer(ContainerMultiSlotItem container,
	        GridSlot gridSlot, int displayX, int displayY, int slotWidth,
	        int slotHeight) {
	    // TODO Auto-generated method stub
	    return new SlotArtificingGrid(container, gridSlot, displayX, displayY, slotWidth, slotHeight);
	}
	
	@Override
	public void onInventoryChanged() {
	    super.onInventoryChanged();
	    artificingTable.onInventoryChanged();
	}
}
