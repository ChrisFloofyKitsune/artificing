package chrisclark13.minecraft.artificing.inventory;

import java.util.ArrayList;

import chrisclark13.minecraft.artificing.item.ItemRune;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.customslots.inventory.ContainerMultiSlotItem;
import chrisclark13.minecraft.customslots.inventory.GridSlot;
import chrisclark13.minecraft.customslots.inventory.SlotMultiSlotItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerArtificingTable extends ContainerMultiSlotItem {
    
    protected TileArtificingTable artificingTable;
    private ArrayList<SlotMultiSlotItem> gridSlots;
    
    public final static int GRID_CENTER_X = 104;
    public final static int GRID_CENTER_Y = 73;
    
    public ContainerArtificingTable(InventoryPlayer inventoryPlayer,
            TileArtificingTable artificingTable) {
        super(artificingTable.grid);
        
        this.artificingTable = artificingTable;
        
        final int INPUT_X = 8;
        final int INPUT_Y = 65;
        final int OUTPUT_X = 184;
        final int OUTPUT_Y = 65;
        
        addSlotToContainer(new SlotArtificingInput(this, 0, INPUT_X, INPUT_Y));
        addSlotToContainer(new SlotArtificingOutput(artificingTable, 1,
                OUTPUT_X, OUTPUT_Y));
        
        gridSlots = artificingTable.grid.createSlotsForContainer(this, 0, 0, 16, 16);
        
        for (SlotMultiSlotItem slot : gridSlots) {
            addSlotToContainer(slot);
        }
        
        addPlayerInventory(inventoryPlayer);
        this.updateArtificingGridSize();
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }
    
    protected void addPlayerInventory(InventoryPlayer inventoryPlayer) {
        final int INVENTORY_X = 24;
        final int INVENTORY_Y = 154;
        final int HOTBAR_Y = 212;
        
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
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex) {
        
        ItemStack newItemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);
        
        if (slot != null) {
            if (slot instanceof SlotMultiSlotItem) {
                slot = ((SlotMultiSlotItem) slot).getParentSlotIfExists();
            }
            
            if (slot.getHasStack()) {
                ItemStack itemStack = slot.getStack();
                newItemStack = itemStack.copy();
                
                if (slotIndex < artificingTable.getSizeInventory()) {
                    if (!this.mergeItemStack(itemStack, artificingTable.getSizeInventory(),
                            inventorySlots.size(), true))
                        return null;
                } else {
                    int startIndex = (slot.getStack().getItem() instanceof ItemRune) ? TileArtificingTable.INVENTORY_SIZE
                            : 0;
                    if (!this.mergeItemStack(itemStack, startIndex,
                            artificingTable.getSizeInventory(), false)) {
                        return null;
                    }
                }
                
                if (itemStack.stackSize == 0) {
                    slot.putStack((ItemStack) null);
                } else {
                    slot.onSlotChanged();
                }
                
                if (itemStack.stackSize == newItemStack.stackSize) {
                    return null;
                }
                
                slot.onPickupFromSlot(entityPlayer, newItemStack);
            }
        }
        
        return newItemStack;
    }

    public void updateArtificingGridSize() {
        int gridWidth = artificingTable.getCurrentGridWidth();
        int gridHeight = artificingTable.getCurrentGridHeight();
        
        for (SlotMultiSlotItem slot : gridSlots) {
            GridSlot gridSlot = slot.getGridSlot();
            if (gridSlot.getGridX() >= gridWidth || gridSlot.getGridY() >= gridHeight) {
                slot.x = -slot.width;
                slot.y = -slot.height;
                slot.enabled = false;
            } else {
                slot.x = GRID_CENTER_X - (gridWidth * slot.width) / 2
                        + gridSlot.getGridX() * slot.width;
                slot.y = GRID_CENTER_Y - (gridHeight * slot.height) / 2
                        + gridSlot.getGridY() * slot.height;
                slot.enabled = true;
            }
        }
        
    }
}
