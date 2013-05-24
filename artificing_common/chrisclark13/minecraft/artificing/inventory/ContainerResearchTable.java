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
    
    public TileResearchTable researchTable;
    public Slot centerSlot;
    public SlotResearchRing[] innerSlots;
    public SlotResearchRing[] outerSlots;
    public Slot[] outputSlots;
    
    public ContainerResearchTable(InventoryPlayer inventoryPlayer, TileResearchTable researchTable) {
        final int CENTER_SLOT_X = 80;
        final int CENTER_SLOT_Y = 58;
        
        final int OUTPUT_SLOT_X = 178;
        final int OUTPUT_SLOT_Y = 22;
        this.researchTable = researchTable;
        
        addSlotToContainer(new Slot(researchTable, 0, CENTER_SLOT_X, CENTER_SLOT_Y));
        
        innerSlots = new SlotResearchRing[researchTable.INNER_SLOT_END
                - researchTable.INNER_SLOT_START + 1];
        for (int i = 0; i < 3; i++) {
            SlotResearchRing slot = new SlotResearchRing(researchTable, i
                    + researchTable.INNER_SLOT_START);
            innerSlots[i] = slot;
            addSlotToContainer(slot);
        }
        
        outerSlots = new SlotResearchRing[researchTable.OUTER_SLOT_END
                - researchTable.OUTER_SLOT_START + 1];
        for (int i = 0; i < 3; i++) {
            SlotResearchRing slot = new SlotResearchRing(researchTable, i
                    + researchTable.OUTER_SLOT_START);
            outerSlots[i] = slot;
            addSlotToContainer(slot);
        }
        
        for (int i = 0; i < 5; i++) {
            addSlotToContainer(new Slot(researchTable, i + researchTable.OUTER_SLOT_END + 1,
                    OUTPUT_SLOT_X, OUTPUT_SLOT_Y  + i * 18));
        }
        
        addPlayerInventory(inventoryPlayer);
    }
    
    protected void addPlayerInventory(InventoryPlayer inventoryPlayer) {
        final int INVENTORY_X = 8;
        final int INVENTORY_Y = 144;
        final int HOTBAR_Y = 202;
        
        for (int slotVertical = 0; slotVertical < 3; slotVertical++) {
            for (int slotHorizontal = 0; slotHorizontal < 9; slotHorizontal++) {
                addSlotToContainer(new Slot(inventoryPlayer, slotHorizontal + (slotVertical * 9)
                        + 9, INVENTORY_X + slotHorizontal * 18, INVENTORY_Y + slotVertical * 18));
            }
        }
        
        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            addSlotToContainer(new Slot(inventoryPlayer, hotbarSlot, INVENTORY_X + hotbarSlot * 18,
                    HOTBAR_Y));
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
