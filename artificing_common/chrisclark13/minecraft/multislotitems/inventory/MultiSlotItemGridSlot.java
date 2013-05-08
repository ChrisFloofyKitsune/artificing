package chrisclark13.minecraft.multislotitems.inventory;

import java.util.HashSet;

import chrisclark13.minecraft.multislotitems.MultiSlotItemHelper;
import chrisclark13.minecraft.multislotitems.MultiSlotItemSlotSignature;

import net.minecraft.item.ItemStack;

public class MultiSlotItemGridSlot {
	private InventoryMultiSlotItemGrid grid;
	private int slotId;
	private int gridX;
	private int gridY;
	private boolean enabled;

	private ItemStack itemStack;

	// Info used in connecting to other slots
	private MultiSlotItemGridSlot parentSlot;
	private HashSet<MultiSlotItemGridSlot> childrenSlots;

	public MultiSlotItemGridSlot(InventoryMultiSlotItemGrid grid, int slotId,
			int gridX, int gridY) {

		this.grid = grid;
		this.slotId = slotId;
		this.gridX = gridX;
		this.gridY = gridY;
		this.enabled = true;

		this.itemStack = null;

		this.parentSlot = null;
		this.childrenSlots = new HashSet<>();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public void setItemStack(ItemStack itemStack) {
		if (this.itemStack != itemStack) {
		    if (itemStack != null && !MultiSlotItemHelper.getSignature(itemStack).isOneByOne()) {
		        bindChildSlots(MultiSlotItemHelper.getSignature(itemStack));
		    }
		}
	    
	    this.itemStack = itemStack;
		
		this.updateSlot();
	}

	public int getSlotId() {
		return slotId;
	}

	public InventoryMultiSlotItemGrid getGrid() {
		return grid;
	}

	public int getGridX() {
		return gridX;
	}

	public int getGridY() {
		return gridY;
	}

	public MultiSlotItemGridSlot getParentSlot() {
		return parentSlot;
	}

	/**
	 * Use sparingly since changing this around may case weird crap to happen.</br>
	 * Detaches itself from it's existing parentSlot if it has one.
	 * 
	 * @param slot
	 *            New parentSlot
	 */
	public void setParentSlot(MultiSlotItemGridSlot slot) {
		if (slot == this || slot == parentSlot) {
			return;
		}
		
		if (parentSlot != null) {
			parentSlot.childrenSlots.remove(this);
		}

		parentSlot = slot;
		parentSlot.childrenSlots.add(this);
	}

	public HashSet<MultiSlotItemGridSlot> getChildrenSlots() {
		return childrenSlots;
	}

	/**
	 * Should be called each time setItemStack is called or whenever needed to
	 * make sure that all slots are up to date.</br> Makes sure that slots
	 * aren't locked when they shouldn't be
	 */
	public void updateSlot() {
		// Redirect update to parent slot.
		if (parentSlot != null) {
			parentSlot.updateSlot();

			// If a slot has a parentSlot and childrenSlots, something went
			// wrong!!
			if (!childrenSlots.isEmpty()) {
				System.err.println("A MultiSlotItemGridSlot has a parentSlot "
						+ "and childrenSlots! Something went wrong!!");
			}
		} else {
			if (itemStack == null && !childrenSlots.isEmpty()) {
				freeChildSlots();
			}
			
			if (itemStack != null && childrenSlots.isEmpty() && !MultiSlotItemHelper.getSignature(itemStack).isOneByOne()) {
				bindChildSlots(MultiSlotItemHelper.getSignature(itemStack));
			}
		}
	}

	// Bind the child slots specified in the signature to this slot
	private void bindChildSlots(MultiSlotItemSlotSignature sig) {
		
		if (!childrenSlots.isEmpty()) {
			freeChildSlots();
		}
			
		for (int row = 0; row < sig.getHeight(); row++) {
			for (int column = 0; column < sig.getWidth(); column++) {
				if (sig.isSlotDesignated(column, row)) {
					MultiSlotItemGridSlot slot = grid.getGridSlotAt(gridX
							+ column + sig.getRelativeLeft(),
							gridY + row + sig.getRelativeTop());
					if (slot == null) {
						System.err.println("A MSIGridSlot at (" + gridX + "," + gridY + ") is trying to bind a slot" +
								" that doesn't exist at (" + (gridX
										+ column + sig.getRelativeLeft()) + "," +
								(gridY + row + sig.getRelativeTop()) + ")!");
					} else if (slot != this) {
						slot.parentSlot = this;
						childrenSlots.add(slot);
					}
				}
			}
		}
	}

	// Free the child slots that have been bound to this slot
	private void freeChildSlots() {
		for (MultiSlotItemGridSlot slot : childrenSlots) {
			slot.parentSlot = null;
		}

		childrenSlots.clear();
	}

	public boolean isItemStackValid(ItemStack itemStack) {
		if (ItemStack.areItemStacksEqual(getItemStack(), itemStack)) {
			return true;
		} else {
			return grid.isSpaceForItemAt(gridX, gridY, itemStack);
		}
	}
	
	public boolean isOpen() {
		return (parentSlot == null && itemStack == null);
	}

}
