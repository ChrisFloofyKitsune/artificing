package chrisclark13.minecraft.artificing.inventory;

import java.util.ArrayList;

import com.google.common.collect.ObjectArrays;

import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.artificing.tileentity.TileResearchTable;
import chrisclark13.minecraft.multislotitems.inventory.ContainerMultiSlotItem;
import chrisclark13.minecraft.multislotitems.inventory.InventoryMultiSlotItemGrid;
import chrisclark13.minecraft.multislotitems.inventory.SlotMultiSlotItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


public class ContainerResearchTable extends Container {

    protected TileResearchTable researchTable;

    public ContainerResearchTable(InventoryPlayer inventoryPlayer, TileResearchTable researchTable) {

        for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(inventoryPlayer, inventoryColumnIndex
                        + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18,
                        144 + inventoryRowIndex * 18));
            }
        }
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < 9; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(inventoryPlayer, actionBarSlotIndex,
                    8 + actionBarSlotIndex * 18, 202));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {

        return true;
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

                if (slotIndex < researchTable.getSizeInventory()) {
                    if (!this.mergeItemStack(itemStack, researchTable.getSizeInventory(),
                            inventorySlots.size(), true))
                        return null;
                } else if (!this.mergeItemStack(itemStack, 0, researchTable.getSizeInventory(),
                        false))
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
