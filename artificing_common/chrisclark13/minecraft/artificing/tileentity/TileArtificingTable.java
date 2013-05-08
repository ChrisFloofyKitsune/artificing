package chrisclark13.minecraft.artificing.tileentity;

import chrisclark13.minecraft.artificing.inventory.InventoryArtificingGrid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileArtificingTable extends TileEntity implements IInventory {

	private final int INVENTORY_SIZE = 2;

	private ItemStack[] inventory;
	public InventoryArtificingGrid grid;

	public TileArtificingTable() {
		inventory = new ItemStack[INVENTORY_SIZE];
		grid = new InventoryArtificingGrid(this, 7, 7);
	}

	@Override
	public int getSizeInventory() {
		return inventory.length + grid.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot < inventory.length) {
			return inventory[slot];
		} else {
			return grid.getStackInSlot(slot - inventory.length);
		}
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {

		if (slot >= inventory.length) {
			return grid.decrStackSize(slot, amount);
		}
		
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

		if (slot >= inventory.length) {
			return grid.getStackInSlotOnClosing(slot);
		}
		
		if (getStackInSlot(slot) != null) {
			ItemStack itemStack = getStackInSlot(slot);
			setInventorySlotContents(slot, null);
			return itemStack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack) {

		if (slot >= inventory.length) {
			grid.setInventorySlotContents(slot, itemStack);
			return;
		}
		
		inventory[slot] = itemStack;

		if (itemStack != null
				&& itemStack.stackSize > this.getInventoryStackLimit()) {
			itemStack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return "artificing.artificingTable";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
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
	public void closeChest() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return (i == 0);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		NBTTagList tagList = tagCompound.getTagList("ATInventory");
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < inventory.length) {
				setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(tag));
			}
		}
		
		grid.readFromNBT(tagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < inventory.length; i++) {
			ItemStack stack = getStackInSlot(i);
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("ATInventory", itemList);
		
		grid.writeToNBT(tagCompound);
	}
}
