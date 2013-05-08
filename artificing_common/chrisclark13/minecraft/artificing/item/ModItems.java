package chrisclark13.minecraft.artificing.item;

import chrisclark13.minecraft.artificing.lib.ItemIds;

public class ModItems {
    //public item references
    public static ItemRune rune;
    
    public static void init() {
        //Initialize items
        rune = new ItemRune(ItemIds.RUNE);
    }
}

