package chrisclark13.minecraft.multislotitems.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMultiSlotItem extends Slot {

    private ContainerMultiSlotItem container;
	private MultiSlotItemGridSlot gridSlot;
	
	public SlotMultiSlotItem(ContainerMultiSlotItem container, MultiSlotItemGridSlot gridSlot, int slotId, int displayX, int displayY) {
		super(gridSlot.getGrid(), slotId,
				displayX, displayY);
		
		this.container = container;
		this.gridSlot = gridSlot;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return gridSlot.isItemStackValid(par1ItemStack);
	}
	
	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		gridSlot.updateSlot();
	}
	
	public MultiSlotItemGridSlot getGridSlot() {
		return gridSlot;
	}
	
	public SlotMultiSlotItem getLinkedSlot() {
	    return (SlotMultiSlotItem) (gridSlot.getParentSlot() == null ? this : container.getSlotFromInventory(gridSlot.getGrid(), gridSlot.getParentSlot().getSlotId()));
	}
}
