package chrisclark13.minecraft.artificing.block;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;
import chrisclark13.minecraft.artificing.item.ItemArtificingTableBlock;
import chrisclark13.minecraft.artificing.lib.BlockIds;
import chrisclark13.minecraft.artificing.lib.Strings;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.artificing.tileentity.TileDisenchanter;
import chrisclark13.minecraft.artificing.tileentity.TileResearchTable;


public class ModBlocks {

    public static BlockArtificingTable artificingTable;
    public static BlockResearchTable   researchTable;
    public static BlockDisenchanter    disenchanter;

    public static void init() {

        artificingTable = new BlockArtificingTable(BlockIds.artificingTableID);
        researchTable = new BlockResearchTable(BlockIds.researchTableID);
        disenchanter = new BlockDisenchanter(BlockIds.disenchanterID);
        
        GameRegistry.registerBlock(artificingTable, Strings.ARTIFICING_TABLE_NAME);
        GameRegistry.registerTileEntity(TileArtificingTable.class, Strings.ARTIFICING_TABLE_NAME);
        Item.itemsList[BlockIds.artificingTableID] = new ItemArtificingTableBlock(artificingTable);
        
        GameRegistry.registerBlock(researchTable, Strings.RESEARCH_TABLE_NAME);
        GameRegistry.registerTileEntity(TileResearchTable.class, Strings.RESEARCH_TABLE_NAME);
        
        GameRegistry.registerBlock(disenchanter, Strings.DISENCHANTER_NAME);
        GameRegistry.registerTileEntity(TileDisenchanter.class, Strings.DISENCHANTER_NAME);
    }
}
