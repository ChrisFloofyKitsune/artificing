package chrisclark13.minecraft.artificing.inventory;

import chrisclark13.minecraft.artificing.lib.ItemIds;
import chrisclark13.minecraft.multislotitems.inventory.ContainerMultiSlotItem;
import chrisclark13.minecraft.multislotitems.inventory.MultiSlotItemGridSlot;
import chrisclark13.minecraft.multislotitems.inventory.SlotMultiSlotItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotArtificingGrid extends SlotMultiSlotItem {

	public SlotArtificingGrid(ContainerMultiSlotItem container, MultiSlotItemGridSlot gridSlot, int slotId, int displayX,
			int displayY) {
		super(container, gridSlot, slotId, displayX, displayY);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
	
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return super.isItemValid(itemStack) && itemStack.itemID == ItemIds.RUNE;
	}
}
