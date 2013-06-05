package chrisclark13.minecraft.artificing.tileentity;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import chrisclark13.minecraft.artificing.core.helper.RuneHelper;
import chrisclark13.minecraft.artificing.inventory.InventoryArtificingGrid;
import chrisclark13.minecraft.artificing.item.crafting.ArtificingCraftingManager;
import chrisclark13.minecraft.artificing.lib.Homestuck;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;

public class TileArtificingTable extends TileArtificingGeneral implements ISidedInventory {

	public final static int INVENTORY_SIZE = 2;
	public final static int INPUT_SLOT_INDEX = 0;
	public final static int OUTPUT_SLOT_INDEX = 1;
	
	private ItemStack[] inventory;
	
	public InventoryArtificingGrid grid;

	
	
	private static final Random RAND = new Random();
	
	/**
	 * Used in TileArtificingTableRender to control the animations
	 */
	public int tickCount;
	
	public ArrayList<Integer> colors;
	public ArrayList<Character> characters;
	private static final int NUMBER_NAMES = 6;
	private boolean hasUpdatedAtLeastOnce = false;
	
	public ArtificingCraftingManager manager;
	
	public TileArtificingTable() {
		inventory = new ItemStack[INVENTORY_SIZE];
		grid = new InventoryArtificingGrid(this, 7, 7);
		
		//Desyncs the animations between multiple tile entities
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

	    if (slot == OUTPUT_SLOT_INDEX) {
	        
	    }
	    
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
	public void onInventoryChanged() {
	    
	    manager.updateEnchantments(getStackInSlot(INPUT_SLOT_INDEX));
	    
	    inventory[OUTPUT_SLOT_INDEX] = manager.getResult();
	    
	    colors.clear();
	    for (EnchantmentData data : manager.getEnchantments()) {
	        colors.add(RuneHelper.getEnchantmentColor(data.enchantmentobj));
	    }
	    
	    characters.clear();
	    for (int i = 0; i < NUMBER_NAMES; i++) {
	        String name = Homestuck.TROLL_NAMES[RAND.nextInt(Homestuck.TROLL_NAMES.length)];
	        for (int j = 0; j < name.length(); j++) {
	            characters.add(name.charAt(j));
	        }
	    }
	    
	    super.onInventoryChanged();
	}
	
	private void onOutputRemoved() {
	    
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

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        switch (ForgeDirection.getOrientation(side)) {
            case UP:
                return new int[] {INPUT_SLOT_INDEX};
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
        return AxisAlignedBB.getAABBPool().getAABB(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 2, zCoord + 1);
    }
    
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
    }

    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
        readFromNBT(packet.customParam1);
    }
}
