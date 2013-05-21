package chrisclark13.minecraft.artificing.inventory;

import chrisclark13.minecraft.artificing.lib.ItemIds;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.multislotitems.inventory.ContainerMultiSlotItem;
import chrisclark13.minecraft.multislotitems.inventory.MultiSlotItemGridSlot;
import chrisclark13.minecraft.multislotitems.inventory.SlotMultiSlotItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotArtificingGrid extends SlotMultiSlotItem {

	public SlotArtificingGrid(ContainerMultiSlotItem container, MultiSlotItemGridSlot gridSlot, int displayX,
			int displayY, int slotWidth, int slotHeight) {
		super(container, gridSlot, displayX, displayY, slotWidth, slotHeight);
	}
	
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return super.isItemValid(itemStack) && itemStack.itemID == ItemIds.RUNE;
	}
}
