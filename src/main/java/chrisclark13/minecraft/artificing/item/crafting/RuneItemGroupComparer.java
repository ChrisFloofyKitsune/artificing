package chrisclark13.minecraft.artificing.item.crafting;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.ItemStack;
import chrisclark13.minecraft.artificing.item.ItemRune;
import chrisclark13.minecraft.artificing.item.ModItems;
import chrisclark13.minecraft.customslots.msi.ItemGroup;
import chrisclark13.minecraft.customslots.msi.ItemGroupComparer;

public class RuneItemGroupComparer extends ItemGroupComparer {
    @Override
    public boolean canMerge(ItemGroup a, ItemGroup b) {
        ItemStack aStack = (a.getParentSlots().isEmpty()) ? null : a.getParentSlots().get(0).getItemStack();
        ItemStack bStack = (b.getParentSlots().isEmpty()) ? null : b.getParentSlots().get(0).getItemStack();
        
        if (aStack == null || bStack == null) {
            return false;
        }
        
        if (aStack.getItem() instanceof ItemRune && bStack.getItem() instanceof ItemRune) {
            EnchantmentData aData = ModItems.rune.getEnchantmentData(aStack);
            EnchantmentData bData = ModItems.rune.getEnchantmentData(bStack);
            
            if (aData == null || bData == null) {
                return aData == null && bData == null;
            }
            
            return aData.enchantmentobj.effectId == bData.enchantmentobj.effectId;
        }
        
        return super.canMerge(a, b);
    }
}
