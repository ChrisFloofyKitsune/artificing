package chrisclark13.minecraft.artificing.inventory;

import java.util.ArrayList;

import com.google.common.collect.ObjectArrays;

import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.multislotitems.inventory.ContainerMultiSlotItem;
import chrisclark13.minecraft.multislotitems.inventory.InventoryMultiSlotItemGrid;
import chrisclark13.minecraft.multislotitems.inventory.SlotMultiSlotItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerArtificingTable extends ContainerMultiSlotItem {

	protected TileArtificingTable artificingTable;

	public ContainerArtificingTable(InventoryPlayer inventoryPlayer,
			TileArtificingTable artificingTable) {
		super(artificingTable.grid);
		 
		this.artificingTable = artificingTable;

		final int INPUT_X = 8;
		final int INPUT_Y = 65;
		final int OUTPUT_X = 152;
		final int OUTPUT_Y = 65;

		addSlotToContainer(new Slot(artificingTable, 0, INPUT_X, INPUT_Y));
		addSlotToContainer(new SlotArtificingOutput(artificingTable, 1,
				OUTPUT_X, OUTPUT_Y));

		final int GRID_X = 32;
		final int GRID_Y = 17;

		ArrayList<Slot> gridSlots = artificingTable.grid.createSlotsForContainer(this, GRID_X, GRID_Y, 8, 8);
		
		for (Slot slot : gridSlots) {
			addSlotToContainer(slot);
		}
		

		addPlayerInventory(inventoryPlayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

	protected void addPlayerInventory(InventoryPlayer inventoryPlayer) {
		final int INVENTORY_X = 8;
		final int INVENTORY_Y = 144;
		final int HOTBAR_Y = 202;

		for (int slotVertical = 0; slotVertical < 3; slotVertical++) {
			for (int slotHorizontal = 0; slotHorizontal < 9; slotHorizontal++) {
				addSlotToContainer(new Slot(inventoryPlayer, slotHorizontal
						+ (slotVertical * 9) + 9, INVENTORY_X + slotHorizontal
						* 18, INVENTORY_Y + slotVertical * 18));
			}
		}

		for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
			addSlotToContainer(new Slot(inventoryPlayer, hotbarSlot,
					INVENTORY_X + hotbarSlot * 18, HOTBAR_Y));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex) {

        ItemStack newItemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null) {
            if (slot instanceof SlotMultiSlotItem) {
                slot = ((SlotMultiSlotItem) slot).getParentSlot();
            }
            if (slot.getHasStack()) {
                ItemStack itemStack = slot.getStack();
                newItemStack = itemStack.copy();

                if (slotIndex < artificingTable.getSizeInventory()) {
                    if (!this.mergeItemStack(itemStack, artificingTable.getSizeInventory(),
                            inventorySlots.size(), true))
                        return null;
                } else if (!this.mergeItemStack(itemStack, 0, artificingTable.getSizeInventory(), false))
                    return null;

                if (itemStack.stackSize == 0) {
                    slot.putStack((ItemStack) null);
                }
                
                slot.onSlotChanged();
            }
        }

        return newItemStack;
    }
}
