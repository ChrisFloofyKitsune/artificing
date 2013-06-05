package chrisclark13.minecraft.artificing.inventory;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotArtificingInput extends Slot {

    private ContainerArtificingTable containerArtificingTable;
    
    public SlotArtificingInput(ContainerArtificingTable containerArtificingTable, int id, int x, int y) {
        super(containerArtificingTable.artificingTable, id, x, y);
        this.containerArtificingTable = containerArtificingTable;
    }
    
    @Override
    public void putStack(ItemStack par1ItemStack) {
        super.putStack(par1ItemStack);
    }
    
}
