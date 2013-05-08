package chrisclark13.minecraft.multislotitems.inventory;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerMultiSlotItem extends Container {

    private InventoryMultiSlotItemGrid grid;

    public ContainerMultiSlotItem(InventoryMultiSlotItemGrid grid) {
        this.grid = grid;
    }

    @Override
    public boolean func_94531_b(Slot slot) {
        return !(slot instanceof SlotMultiSlotItem);
    }

    @Override
    public ItemStack slotClick(int slotId, int clickAction, int slotAction, EntityPlayer player) {

        // We only want to handle clicking for SlotMultiSlotItem slots
        if (slotId <= 0 || slotId > inventorySlots.size() || !(inventorySlots.get(slotId) instanceof SlotMultiSlotItem)) {
            return super.slotClick(slotId, clickAction, slotAction, player);
        }

        //If this slot is a SlotMSI slot, then call it again with the right slot to do things on
        Slot slot = ((SlotMultiSlotItem) inventorySlots.get(slotId)).getLinkedSlot();
        return super.slotClick(slot.slotNumber, clickAction, slotAction, player);
        
    }

    @Override
    /**
     * transferStackInSlot method ready to go to work with SlotMultiSlotItems</br>
     * If you make a subclass with additional inventory beyond the MSI grid, you want to copy this and 
     * change grid.getSizeInventory() to yourInventoryHere.getSizeInventory
     */
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex) {

        ItemStack newItemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null) {
            if (slot instanceof SlotMultiSlotItem) {
                slot = ((SlotMultiSlotItem) slot).getLinkedSlot();
            }
            if (slot.getHasStack()) {
                ItemStack itemStack = slot.getStack();
                newItemStack = itemStack.copy();

                if (slotIndex < grid.getSizeInventory()) {
                    if (!this.mergeItemStack(itemStack, grid.getSizeInventory(),
                            inventorySlots.size(), true))
                        return null;
                } else if (!this.mergeItemStack(itemStack, 0, grid.getSizeInventory(), false))
                    return null;

                if (itemStack.stackSize == 0) {
                    slot.putStack((ItemStack) null);
                }
                
                slot.onSlotChanged();
            }
        }

        return newItemStack;
    }

    public InventoryMultiSlotItemGrid getGrid() {
        return grid;
    }

    /**
     * merges provided ItemStack with the first avaliable one in the
     * container/player inventory</br>
     * same as before except that it now checks slot.isItemValid before placing a stack into a slot.
     */
    protected boolean mergeItemStack(ItemStack par1ItemStack, int startIndex, int endIndex,
            boolean reverseOrder) {
        boolean success = false;
        int k = startIndex;

        if (reverseOrder) {
            k = endIndex - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (par1ItemStack.isStackable()) {
            while (par1ItemStack.stackSize > 0
                    && (!reverseOrder && k < endIndex || reverseOrder && k >= startIndex)) {
                slot = (Slot) this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 != null
                        && itemstack1.itemID == par1ItemStack.itemID
                        && (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() == itemstack1
                                .getItemDamage())
                        && ItemStack.areItemStackTagsEqual(par1ItemStack, itemstack1)) {
                    int l = itemstack1.stackSize + par1ItemStack.stackSize;

                    if (l <= par1ItemStack.getMaxStackSize()) {
                        par1ItemStack.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        success = true;
                    } else if (itemstack1.stackSize < par1ItemStack.getMaxStackSize()) {
                        par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize()
                                - itemstack1.stackSize;
                        itemstack1.stackSize = par1ItemStack.getMaxStackSize();
                        slot.onSlotChanged();
                        success = true;
                    }
                }

                if (reverseOrder) {
                    --k;
                } else {
                    ++k;
                }
            }
        }

        if (par1ItemStack.stackSize > 0) {
            if (reverseOrder) {
                k = endIndex - 1;
            } else {
                k = startIndex;
            }

            while (!reverseOrder && k < endIndex || reverseOrder && k >= startIndex) {
                slot = (Slot) this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 == null && slot.isItemValid(par1ItemStack)) {
                    slot.putStack(par1ItemStack.copy());
                    slot.onSlotChanged();
                    par1ItemStack.stackSize = 0;
                    success = true;
                    break;
                }

                if (reverseOrder) {
                    --k;
                } else {
                    ++k;
                }
            }
        }

        return success;
    }
}
