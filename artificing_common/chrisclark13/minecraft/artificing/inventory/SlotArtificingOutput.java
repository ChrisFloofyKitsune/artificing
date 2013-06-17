package chrisclark13.minecraft.artificing.inventory;

import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotArtificingOutput extends Slot {
    
    private TileArtificingTable table;
    private IInventory grid;
    
    public SlotArtificingOutput(TileArtificingTable table, int id, int displayX, int displayY) {
        super(table, id, displayX, displayY);
        
        this.table = table;
        this.grid = table.grid;
    }
    
    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
        return false;
    }
    
    @Override
    public boolean canTakeStack(EntityPlayer player) {
        return player.experienceLevel >= table.manager.getLevelsNeeded();
    }
    
    @Override
    public void onPickupFromSlot(EntityPlayer player, ItemStack itemStack) {
        super.onPickupFromSlot(player, itemStack);
        
        if (!player.capabilities.isCreativeMode) {
            player.addExperienceLevel(-table.manager.getLevelsNeeded());
        }
        
        for (int i = 0; i < grid.getSizeInventory(); i++) {
            if (grid.getStackInSlot(i) != null) {
                grid.decrStackSize(i, 1);
            }
        }
        
        table.decrStackSize(TileArtificingTable.INPUT_SLOT_INDEX, 1);
        table.addCharge(1);
    }
}
