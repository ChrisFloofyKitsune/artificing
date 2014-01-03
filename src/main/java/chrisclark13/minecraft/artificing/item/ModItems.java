package chrisclark13.minecraft.artificing.item;

import net.minecraft.item.Item;
import chrisclark13.minecraft.artificing.block.ModBlocks;
import chrisclark13.minecraft.artificing.lib.ItemIds;

public class ModItems {
    //public item references
    public static ItemRune rune;
    public static ItemGuideBook guideBook;
    
    public static void init() {
        //Initialize items
        rune = new ItemRune(ItemIds.RUNE);
        guideBook = new ItemGuideBook(ItemIds.BOOK);
        
        Item.itemsList[ModBlocks.artificingTable.blockID] = new ItemArtificingTableBlock(ModBlocks.artificingTable);
    }
}

