package chrisclark13.minecraft.artificing.block;

import cpw.mods.fml.common.registry.GameRegistry;
import chrisclark13.minecraft.artificing.lib.BlockIds;
import chrisclark13.minecraft.artificing.lib.Strings;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;

public class ModBlocks {
    
    public static BlockArtificingTable artificingTable;
    
    public static void init() {
        artificingTable = new BlockArtificingTable(BlockIds.ARTIFICING_TABLE_ID);
    
        GameRegistry.registerBlock(artificingTable, Strings.ARTIFICING_TABLE_NAME);
        GameRegistry.registerTileEntity(TileArtificingTable.class, "");
    }
}
