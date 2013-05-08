package chrisclark13.minecraft.artificing.inventory;

import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.multislotitems.inventory.InventoryMultiSlotItemGrid;

public class InventoryArtificingGrid extends InventoryMultiSlotItemGrid {

	TileArtificingTable parent;
	public InventoryArtificingGrid(TileArtificingTable parent, int width, int height) {
		super(width, height);
	}
}
