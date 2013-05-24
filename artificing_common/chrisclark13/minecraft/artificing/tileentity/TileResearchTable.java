package chrisclark13.minecraft.artificing.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;


public class TileResearchTable extends TileArtificingGeneral implements ISidedInventory {

    private final int   INVENTORY_SIZE = 12;
    private ItemStack[] inventory;

    public final int INNER_SLOT_START = 1;
    public final int INNER_SLOT_END = 3;
    
    public final int OUTER_SLOT_START = 4;
    public final int OUTER_SLOT_END = 6;
    
    /**
     * Used in timing animations
     */
    public int tickCount;
    
    public TileResearchTable() {

        inventory = new ItemStack[INVENTORY_SIZE];
        tickCount = 0;
    }

    @Override
    public int getSizeInventory() {

        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {

        if (slot < inventory.length) {

            return inventory[slot];
        } else {
            return null;
        }
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
    public void setInventorySlotContents(int slot, ItemStack itemStack) {

        inventory[slot] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
    }

    @Override
    public String getInvName() {

        return "artificing.researchTable";
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

    }

    @Override
    public void closeChest() {

    }

    @Override
    public boolean isStackValidForSlot(int i, ItemStack itemstack) {

        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {

        super.readFromNBT(tagCompound);

        NBTTagList tagList = tagCompound.getTagList("RTInventory");
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
            byte slot = tag.getByte("Slot");
            if (slot >= 0 && slot < inventory.length) {
                setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(tag));
            }
        }

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
        tagCompound.setTag("RTInventory", itemList);

    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        
        int slots[] = new int[inventory.length];
        
        for (int i = 0; i < slots.length; i++) {
            slots[i] = i;
        }
        
        return slots;
    }

    @Override
    public boolean canInsertItem(int id, ItemStack itemstack, int side) {

        if (ForgeDirection.getOrientation(side) == ForgeDirection.UP) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canExtractItem(int id, ItemStack itemstack, int side) {
        if (ForgeDirection.getOrientation(side) == ForgeDirection.DOWN) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void updateEntity() {
        tickCount++;
    }
}

