package chrisclark13.minecraft.artificing.inventory;

import chrisclark13.minecraft.artificing.lib.ItemIds;
import chrisclark13.minecraft.customslots.inventory.ContainerMultiSlotItem;
import chrisclark13.minecraft.customslots.inventory.GridSlot;
import chrisclark13.minecraft.customslots.inventory.SlotMultiSlotItem;
import net.minecraft.item.ItemStack;

public class SlotArtificingGrid extends SlotMultiSlotItem {
    
	public SlotArtificingGrid(ContainerMultiSlotItem container, GridSlot gridSlot, int displayX,
			int displayY, int slotWidth, int slotHeight) {
		super(container, gridSlot, displayX, displayY, slotWidth, slotHeight);
	}
	
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return super.isItemValid(itemStack) && itemStack.itemID == ItemIds.RUNE;
	}
	
	@Override
    public int getSlotStackLimit() {
        return 1;
    }
}
