package chrisclark13.minecraft.artificing.inventory;

import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotArtificingOutput extends Slot {

    private IInventory craftingMatrix;
    
	public SlotArtificingOutput(IInventory inventory, IInventory craftingMatrix, int id, int displayX,
			int displayY) {
		super(inventory, id, displayX, displayY);
		
		this.craftingMatrix = craftingMatrix;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return false;
	}
	
	@Override
	public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
	    super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
	    
	    for (int i = 0; i < craftingMatrix.getSizeInventory(); i++) {
	        if (craftingMatrix.getStackInSlot(i) != null) {
	            craftingMatrix.decrStackSize(i, 1);
	        }
	    }
	    
	    inventory.decrStackSize(TileArtificingTable.INPUT_SLOT_INDEX, 1);
	}
	
	
}
