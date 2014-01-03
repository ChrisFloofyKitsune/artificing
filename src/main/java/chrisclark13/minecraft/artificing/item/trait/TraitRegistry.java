package chrisclark13.minecraft.artificing.item.trait;

import java.util.Set;

import net.minecraft.item.ItemStack;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

public class TraitRegistry {
    private TraitRegistry() {
    }
    
    private static SetMultimap<String, Trait> traitMap;
    
    static {
        HashMultimap<String, Trait> map = HashMultimap.create();
        traitMap = Multimaps.synchronizedSetMultimap(map);
    }
    
    public static void addEntry(ItemStack itemStack, Trait trait) {
        String entry = itemStack.getUnlocalizedName();
        if (itemStack.getHasSubtypes() && !itemStack.getItem().isDamageable()) {
            entry += ":" + itemStack.getItemDamage();
        }
        
        traitMap.put(entry, trait);
    }
    
    public static Set<Trait> getEntries(ItemStack itemStack) {
        String entry = itemStack.getUnlocalizedName();
        if (itemStack.getHasSubtypes() && !itemStack.getItem().isDamageable()) {
            entry += ":" + itemStack.getItemDamage();
        }
        
        return traitMap.get(entry);
    }
}
