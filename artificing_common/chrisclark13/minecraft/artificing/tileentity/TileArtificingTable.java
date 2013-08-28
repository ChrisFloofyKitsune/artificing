package chrisclark13.minecraft.artificing.tileentity;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import chrisclark13.minecraft.artificing.core.helper.RuneHelper;
import chrisclark13.minecraft.artificing.inventory.InventoryArtificingGrid;
import chrisclark13.minecraft.artificing.item.crafting.ArtificingCraftingManager;
import chrisclark13.minecraft.artificing.lib.Homestuck;
import chrisclark13.minecraft.artificing.network.packet.PacketATChargeUpdate;
import chrisclark13.minecraft.multislotitems.helper.MultiSlotItemHelper;
import chrisclark13.minecraft.multislotitems.inventory.GridSlot;
import chrisclark13.minecraft.multislotitems.inventory.SlotSignature;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;

public class TileArtificingTable extends TileArtificingGeneral implements ISidedInventory {
    
    public final static int INVENTORY_SIZE = 2;
    public final static int INPUT_SLOT_INDEX = 0;
    public final static int OUTPUT_SLOT_INDEX = 1;
    
    private ItemStack[] inventory;
    
    public InventoryArtificingGrid grid;
    public final static int GRID_WIDTH = 9;
    public final static int GRID_HEIGHT = 7;
    private static final int[][] GRID_SIZES_BY_LEVEL = { { 0, 0 }, { 3, 3 }, { 4, 4 }, { 5, 5 },
            { 6, 6 }, { 7, 7 }, { 8, 7 }, { 9, 7 } };
    
    private int currentGridWidth = 0;
    private int currentGridHeight = 0;
    
    private static final Random RAND = new Random();
    
    public static final String[] CHARGE_STATUS_NAMES = { ".depleted", ".almostDepleted",
            ".slightlyDepleted", "" };
    public static final int MAX_CHARGE = 36;
    public static final int[] CHARGE_STATUS_THRESHOLDS = { MAX_CHARGE, 24, 12, 0 };
    
    private int charge;
    
    /**
     * Used in TileArtificingTableRender to control the animations
     */
    public int tickCount;
    
    public ArrayList<Integer> colors;
    public ArrayList<Character> characters;
    private static final int NUMBER_NAMES = 6;
    public static final int CHARGE_RESTORED_PER_RUNE_LEVEL = 2;
    private boolean hasUpdatedAtLeastOnce = false;
    
    public ArtificingCraftingManager manager;
    
    public TileArtificingTable() {
        inventory = new ItemStack[INVENTORY_SIZE];
        grid = new InventoryArtificingGrid(this, GRID_WIDTH, GRID_HEIGHT);
        
        // Desyncs the animations between multiple tile entities
        tickCount = RAND.nextInt(40);
        
        colors = new ArrayList<>();
        characters = new ArrayList<>();
        
        manager = new ArtificingCraftingManager(grid);
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
        
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        
        this.onInventoryChanged();
    }
    
    @Override
    public void onInventoryChanged() {
        this.updateGrid();
        
        super.onInventoryChanged();
    }
    
    public void updateGrid() {
        ItemStack itemStack = getStackInSlot(INPUT_SLOT_INDEX);
        
        if (itemStack != null) {
            
            int level = RuneHelper.getEnchantabilityLevelForArtificing(itemStack);
            level = (level >= GRID_SIZES_BY_LEVEL.length) ? GRID_SIZES_BY_LEVEL.length - 1 : level;
            
            this.setArtificingGridSize(GRID_SIZES_BY_LEVEL[level][0], GRID_SIZES_BY_LEVEL[level][1]);
        } else {
            this.setArtificingGridSize(0, 0);
        }
        
        if (charge < MAX_CHARGE) {
            manager.updateEnchantments(itemStack);
        } else {
            manager.reset();
        }
        
        inventory[OUTPUT_SLOT_INDEX] = manager.getResult();
        
        colors.clear();
        for (EnchantmentData data : manager.getEnchantments()) {
            colors.add(RuneHelper.getEnchantmentColor(data.enchantmentobj));
        }
        
        characters.clear();
        if (charge < MAX_CHARGE) {
            
            int maxCharacterCount = MAX_CHARGE - charge;
            int characterCount = 0;
            
            for (int i = 0; i < NUMBER_NAMES && characterCount < maxCharacterCount; i++) {
                String name = Homestuck.TROLL_NAMES[RAND.nextInt(Homestuck.TROLL_NAMES.length)];
                for (int j = 0; j < name.length(); j++) {
                    characters.add(name.charAt(j));
                    characterCount++;
                    
                    if (characterCount >= maxCharacterCount) {
                        break;
                    }
                }
            }
        }
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
    public void closeChest() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void openChest() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
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
        
        currentGridWidth = tagCompound.getByte("GridWidth");
        currentGridHeight = tagCompound.getByte("GridHeight");
        charge = tagCompound.getByte("Charge");
        
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
        
        tagCompound.setByte("GridWidth", (byte) currentGridWidth);
        tagCompound.setByte("GridHeight", (byte) currentGridHeight);
        tagCompound.setByte("Charge", (byte) charge);
        
        grid.writeToNBT(tagCompound);
    }
    
    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        switch (ForgeDirection.getOrientation(side)) {
            case UP:
                return new int[] { INPUT_SLOT_INDEX };
            default:
                return new int[] {};
        }
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
        return false;
    }
    
    @Override
    public void updateEntity() {
        if (!hasUpdatedAtLeastOnce) {
            this.onInventoryChanged();
            hasUpdatedAtLeastOnce = true;
        }
        
        ++tickCount;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getAABBPool().getAABB(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 2,
                zCoord + 1);
    }
    
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 13, nbtTag);
    }
    
    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
        readFromNBT(packet.customParam1);
    }
    
    public void setArtificingGridSize(int gridWidth, int gridHeight) {
        
        currentGridWidth = gridWidth;
        currentGridHeight = gridHeight;
        
        // If we're ejecting everything, don't bother to check if the items
        // still fit.
        if (gridWidth == 0 || gridHeight == 0) {
            for (int i = 0; i < grid.getSizeInventory(); i++) {
                GridSlot slot = grid.getGridSlot(i);
                
                if (slot.getItemStack() != null) {
                    ejectItem(slot.getItemStack());
                    slot.setItemStack(null);
                }
                
                slot.setEnabled(false);
            }
        } else {
            for (int i = 0; i < grid.getSizeInventory(); i++) {
                GridSlot slot = grid.getGridSlot(i);
                
                if (slot.getItemStack() != null) {
                    SlotSignature sig = MultiSlotItemHelper.getSignature(slot.getItemStack());
                    int right = slot.getGridX() + sig.getRelativeRight();
                    int bottom = slot.getGridY() + sig.getRelativeBottom();
                    
                    if (right >= gridWidth || bottom >= gridHeight) {
                        ejectItem(slot.getItemStack());
                        slot.setItemStack(null);
                    }
                }
                
                if (slot.getGridX() >= gridWidth || slot.getGridY() >= gridHeight) {
                    slot.setEnabled(false);
                } else {
                    slot.setEnabled(true);
                }
                
            }
        }
    }
    
    private void ejectItem(ItemStack itemStack) {
        if (!worldObj.isRemote) {
            if (itemStack != null && itemStack.stackSize > 0) {
                ItemStack stack = itemStack.copy();
                
                float rx = RAND.nextFloat() * 0.8F + 0.1F;
                float ry = RAND.nextFloat() * 0.8F + 0.1F;
                float rz = RAND.nextFloat() * 0.8F + 0.1F;
                
                EntityItem entityItem = new EntityItem(worldObj, xCoord + rx, yCoord + ry + 1,
                        zCoord + rz, stack);
                
                float factor = 0.05F;
                entityItem.motionX = RAND.nextGaussian() * factor;
                entityItem.motionY = RAND.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = RAND.nextGaussian() * factor;
                worldObj.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }
    }
    
    public int getCurrentGridWidth() {
        return currentGridWidth;
    }
    
    public int getCurrentGridHeight() {
        return currentGridHeight;
    }
    
    public int getCharge() {
        return charge;
    }
    
    public void setCharge(int charge) {
        this.charge = Math.min(Math.max(0, charge), MAX_CHARGE);
    }
    
    public void addCharge(int charge) {
        this.setCharge(this.charge + charge);
    }
}
