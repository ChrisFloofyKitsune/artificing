package chrisclark13.minecraft.artificing.inventory;

import chrisclark13.minecraft.artificing.lib.ItemIds;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.multislotitems.inventory.ContainerMultiSlotItem;
import chrisclark13.minecraft.multislotitems.inventory.IDisableableSlot;
import chrisclark13.minecraft.multislotitems.inventory.GridSlot;
import chrisclark13.minecraft.multislotitems.inventory.SlotMultiSlotItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotArtificingGrid extends SlotMultiSlotItem implements IDisableableSlot {
    
	public SlotArtificingGrid(ContainerMultiSlotItem container, GridSlot gridSlot, int displayX,
			int displayY, int slotWidth, int slotHeight) {
		super(container, gridSlot, displayX, displayY, slotWidth, slotHeight);
	}
	
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return super.isItemValid(itemStack) && itemStack.itemID == ItemIds.RUNE;
	}

    @Override
    public boolean isDisabled() {
        return !this.gridSlot.isEnabled();
    }

    @Override
    public boolean getDrawItemStack() {
        return this.gridSlot.isEnabled();
    }

    @Override
    public boolean getDrawHighlight() {
        return this.gridSlot.isEnabled();
    }

    @Override
    public boolean isClickable() {
        return this.gridSlot.isEnabled();
    }
    
    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
