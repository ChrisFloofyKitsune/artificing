package chrisclark13.minecraft.artificing.item;

import net.minecraft.item.Item;
import chrisclark13.minecraft.artificing.lib.ItemIds;

public class ModItems {
    //public item references
    public static ItemRune rune;
    public static ItemStartingBook startingBook;
    
    public static void init() {
        //Initialize items
        rune = new ItemRune(ItemIds.RUNE);
        startingBook = new ItemStartingBook(ItemIds.BOOK);
    }
}

