package chrisclark13.minecraft.artificing.item;

import chrisclark13.minecraft.artificing.block.BlockArtificingTable;
import chrisclark13.minecraft.artificing.lib.Reference;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemArtificingTableBlock extends ItemBlock {

    public ItemArtificingTableBlock(BlockArtificingTable table) {
        super(table.blockID - Reference.SHIFTED_ID_RANGE_CORRECTION);
        this.setMaxDamage(TileArtificingTable.MAX_CHARGE);
        this.setHasSubtypes(true);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        String s = super.getUnlocalizedName();
        
        int damage = itemStack.getItemDamage();
        for (int i = 0; i < TileArtificingTable.CHARGE_STATUS_THRESHOLDS.length; i++) {
            int threshold = TileArtificingTable.CHARGE_STATUS_THRESHOLDS[i];
            if (damage >= threshold) {
                s += TileArtificingTable.CHARGE_STATUS_NAMES[i];
                break;
            }
        }
        
        return s;
    }
    
    @Override
    public int getMetadata(int metadata) {
        return metadata;
    }    
}
