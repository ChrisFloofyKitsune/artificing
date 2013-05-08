package chrisclark13.minecraft.multislotitems.inventory;

import java.util.ArrayList;

import chrisclark13.minecraft.multislotitems.MultiSlotItemHelper;
import chrisclark13.minecraft.multislotitems.MultiSlotItemSlotSignature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryMultiSlotItemGrid implements IInventory {
	private MultiSlotItemGridSlot[] slots;
	private int width;
	private int height;

	public InventoryMultiSlotItemGrid(int width, int height) {
		this.width = width;
		this.height = height;

		slots = new MultiSlotItemGridSlot[width * height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				slots[x + (y * width)] = new MultiSlotItemGridSlot(this, x
						+ (y * width), x, y);
			}
		}
	}

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int slotId) {
		return slots[slotId].getItemStack();
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {

		ItemStack itemStack = getStackInSlot(slot);

		if (itemStack != null) {
			if (itemStack.stackSize <= amount) {
				setInventorySlotContents(slot, null);
				itemStack.stackSize = amount;
				return itemStack;
			} else {
				itemStack = itemStack.splitStack(amount);
				if (itemStack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
				return itemStack;
			}
		}

		return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {

		if (getStackInSlot(slot) != null) {
			ItemStack itemStack = getStackInSlot(slot);
			setInventorySlotContents(slot, null);
			return itemStack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		slots[i].setItemStack(itemstack);
		onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return "Multi-Slot Item Inventory";
	}

	@Override
	public boolean isInvNameLocalized() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void onInventoryChanged() {
		for (MultiSlotItemGridSlot slot : slots) {
			slot.updateSlot();
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemStack) {
		return slots[i].isItemStackValid(itemStack);
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isGridSlotOpenAt(int x, int y) {
		return getGridSlotAt(x, y) != null ? getGridSlotAt(x, y).isOpen()
				: false;
	}

	public MultiSlotItemGridSlot getGridSlotAt(int x, int y) {
		if (!isCoordsInBounds(x, y)) {
			return null;
		} else {
			return slots[x + (y * width)];
		}
	}

	public boolean isCoordsInBounds(int x, int y) {
		return (x >= 0 && x < width && y >= 0 && y < height);
	}

	public boolean isSpaceForItemAt(int x, int y, ItemStack itemStack) {
		MultiSlotItemSlotSignature sig = MultiSlotItemHelper
				.getSignature(itemStack);

		// If the signature is one by one, we just need to check one spot
		if (sig.isOneByOne() && isGridSlotOpenAt(x, y)) {
			return true;
		} else if (!isGridSlotOpenAt(x, y)){
			//If it's not just one by one, we should start off by checking this slot anyways
			return false;
		}

		// Start the serious in depth checking, if even one slot is blocked then
		// we can't place the itemStack here.
		for (int row = 0; row < sig.getHeight(); row++) {
			for (int column = 0; column < sig.getWidth(); column++) {
				if (sig.isSlotDesignated(column, row)
						&& !isGridSlotOpenAt(
								x + column + sig.getRelativeLeft(), y + row
										+ sig.getRelativeTop())) {
					return false;
				}
			}
		}

		return true;
	}

	public ArrayList<Slot> createSlotsForContainer(ContainerMultiSlotItem container, int displayX, int displayY) {
		ArrayList<Slot> containerSlots = new ArrayList<Slot>();

		int slotId = 0;
		for (MultiSlotItemGridSlot gridSlot : slots) {
			containerSlots.add(new SlotMultiSlotItem(container, gridSlot, slotId, displayX
					+ gridSlot.getGridX() * 16, displayY + gridSlot.getGridY()
					* 16));
			slotId++;
		}

		//System.out.println(containerSlots.toString());
		
		return containerSlots;
	}
	
	public void readFromNBT(NBTTagCompound tagCompound) {
		NBTTagList list = tagCompound.getTagList("GridInventory");
		
		//Slots will auto-bind children as they are loaded.
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
			short slot = tag.getShort("Slot");
			if (slot >= 0 && slot < getSizeInventory()) {
				if (tag.hasKey("Item")) {
					slots[slot].setItemStack(ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Item")));
				}
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound tagCompound) {
		NBTTagList list = new NBTTagList();
		
		//We just need to save the slots that actually have ItemStacks in them.
		for (int i = 0; i < this.getSizeInventory(); i++) {
			if (slots[i].getParentSlot() == null && slots[i].getItemStack() != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setShort("Slot", (short) i);
				tag.setCompoundTag("Item", slots[i].getItemStack().writeToNBT(new NBTTagCompound()));
				list.appendTag(tag);
			}
		}
		
		tagCompound.setTag("GridInventory", list);
	}
}
