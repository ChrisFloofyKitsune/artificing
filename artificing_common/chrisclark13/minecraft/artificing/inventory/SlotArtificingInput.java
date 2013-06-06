package chrisclark13.minecraft.artificing.inventory;

import chrisclark13.minecraft.artificing.core.helper.RuneHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotArtificingInput extends Slot {
    
    private ContainerArtificingTable containerArtificingTable;
    private static final int[][] GRID_SIZES_BY_LEVEL = { { 0, 0 }, { 3, 3 }, { 4, 4 }, { 5, 5 },
            { 6, 6 }, { 7, 7 }, { 8, 7 }, { 9, 7 } };
    
    public SlotArtificingInput(ContainerArtificingTable containerArtificingTable, int id, int x,
            int y) {
        super(containerArtificingTable.artificingTable, id, x, y);
        this.containerArtificingTable = containerArtificingTable;
    }
    
    @Override
    public void onSlotChanged() {
        super.onSlotChanged();
        
        ItemStack itemStack = this.getStack();
        
        if (itemStack != null) {
            
            int level = RuneHelper.getEnchantabilityLevelForArtificing(itemStack);
            level = (level >= GRID_SIZES_BY_LEVEL.length) ? GRID_SIZES_BY_LEVEL.length - 1 : level;
            
            containerArtificingTable.setArtificingGridSize(GRID_SIZES_BY_LEVEL[level][0],
                    GRID_SIZES_BY_LEVEL[level][1]);
        } else {
            containerArtificingTable.setArtificingGridSize(0, 0);
        }
    }
}
