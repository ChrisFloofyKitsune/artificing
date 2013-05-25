package chrisclark13.minecraft.artificing.item.crafting;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import com.google.common.math.DoubleMath;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

import chrisclark13.minecraft.artificing.core.helper.LocalizationHelper;
import chrisclark13.minecraft.artificing.core.helper.RuneHelper;
import chrisclark13.minecraft.artificing.inventory.InventoryArtificingGrid;
import chrisclark13.minecraft.artificing.item.ModItems;
import chrisclark13.minecraft.artificing.lib.Strings;
import chrisclark13.minecraft.multislotitems.groups.ItemGroup;

public class ArtificingCraftingManager {
    
    private InventoryArtificingGrid grid;
    private List<ItemGroup> itemGroups;
    
    private boolean error;
    private LinkedList<String> errorMessages;
    
    private ItemStack result;
    private LinkedList<EnchantmentData> enchantments;
    private int levelsNeeded;
    
    private static RuneItemGroupComparer runeComparer = new RuneItemGroupComparer();
    private static EnchantmentComparator enchantmentComparator = new EnchantmentComparator();
    
    private final int ENCHANTMENT_LEVEL_MOD = -10;
    private final float ENCHANTMENT_LEVEL_MULTIPLIER = 0.5f;
    
    private final int ENCHANTMENT_LEVEL_OVER_MOD = -10;
    private final float ENCHANTMENT_LEVEL_OVER_MULTIPLIER = 1.0f;
    
    public ArtificingCraftingManager(InventoryArtificingGrid grid) {
        this.grid = grid;
        
        error = false;
        errorMessages = new LinkedList<>();
        
        result = null;
        enchantments = new LinkedList<>();
        levelsNeeded = 0;
    }
    
    public void updateEnchantments(ItemStack itemStack) {
        error = false;
        errorMessages.clear();
        
        result = null;
        enchantments.clear();
        levelsNeeded = 0;
        
        if (itemStack == null) {
            error = true;
            addErrorMessage(LocalizationHelper.getLocalizedString(Strings.ERROR_NO_INPUT));
        }
        
        // List of enchantments already on the ItemStack
        List<EnchantmentData> enchList;
        if (itemStack != null) {
            enchList = RuneHelper.getEnchantments(itemStack);
            
            if (!itemStack.isItemEnchantable() && enchList.isEmpty()) {
                error = true;
                addErrorMessage(LocalizationHelper
                        .getLocalizedString(Strings.ARTIFICING_ERROR_UNENCHANTABLE));
            }
        } else {
            enchList = Collections.emptyList();
        }
        
        itemGroups = ItemGroup.createItemGroupsFromGrid(grid, runeComparer);
        
        // Parse ItemGroups to build the enchantments list
        for (ItemGroup group : itemGroups) {
            int exponetialLevels = 0;
            
            EnchantmentData data = null;
            Enchantment ench = null;
            for (ItemStack stack : group.getItemStacks()) {
                EnchantmentData runeData = ModItems.rune.getEnchantmentData(stack);
                if (runeData != null) {
                    exponetialLevels += Math.pow(2, runeData.enchantmentLevel - 1);
                    
                    if (ench == null) {
                        ench = runeData.enchantmentobj;
                        
                        for (EnchantmentData _data : enchList) {
                            if (runeData.enchantmentobj.effectId == _data.enchantmentobj.effectId) {
                                exponetialLevels += Math.pow(2, _data.enchantmentLevel - 1);
                            }
                        }
                    }
                }
            }
            
            double level = DoubleMath.log2(exponetialLevels) + 1d;
            if (ench != null) {
                if (MathHelper.floor_double(level) != level) {
                    error = true;
                    String s = LocalizationHelper
                            .getLocalizedString(Strings.ARTIFICING_ERROR_LEVELS);
                    s = String.format(
                            s,
                            StatCollector.translateToLocal(ench.getName()),
                            StatCollector.translateToLocal("enchantment.level."
                                    + MathHelper.ceiling_double_int(level)));
                    addErrorMessage(s);
                    data = new EnchantmentData(ench, MathHelper.floor_double(level));
                    enchantments.add(data);
                } else {
                    data = new EnchantmentData(ench, MathHelper.floor_double(level));
                    if (listContainsEnchantment(ench)) {
                        error = true;
                        String s = LocalizationHelper
                                .getLocalizedString(Strings.ARTIFICING_ERROR_DUPLICATE);
                        s = String.format(s, StatCollector.translateToLocal(ench.getName()));
                        addErrorMessage(s);
                    }
                    enchantments.add(data);
                }
            }
        }
        
        if (enchantments.isEmpty()) {
            error = true;
            errorMessages.add(LocalizationHelper
                    .getLocalizedString(Strings.ARTIFICING_ERROR_NO_RUNES));
        }
        
        for (EnchantmentData data : enchList) {
            if (!listContainsEnchantment(data.enchantmentobj)) {
                enchantments.add(data);
            }
            
        }
        
        Collections.sort(enchantments, enchantmentComparator);
        if (itemStack != null) {
            for (EnchantmentData data : enchantments) {
                if (!data.enchantmentobj.canApply(itemStack)) {
                    error = true;
                    String s = LocalizationHelper
                            .getLocalizedString(Strings.ARTIFICING_ERROR_INVALID);
                    s = String.format(s,
                            StatCollector.translateToLocal(data.enchantmentobj.getName()),
                            itemStack.getDisplayName());
                   addErrorMessage(s);
                }
            }
        }
        
        // Now add the enchantments to the itemStack if we have no errors
        if (!error) {
            result = itemStack.copy();
            RuneHelper.setEnchantments(result, enchantments);
            
            for (EnchantmentData data : enchantments) {
                if (data.enchantmentLevel > data.enchantmentobj.getMaxLevel()) {
                    int normalLevels = data.enchantmentobj.getMinEnchantability(data.enchantmentobj
                            .getMaxLevel());
                    int overLevels = data.enchantmentobj.getMinEnchantability(data.enchantmentobj
                            .getMaxLevel() - data.enchantmentLevel)
                            - normalLevels + ENCHANTMENT_LEVEL_OVER_MOD;
                    overLevels = MathHelper.floor_double(overLevels
                            * ENCHANTMENT_LEVEL_OVER_MULTIPLIER);
                    levelsNeeded += overLevels;
                }
                
                int levels = data.enchantmentobj.getMinEnchantability(data.enchantmentLevel)
                        + ENCHANTMENT_LEVEL_MOD;
                
                levels = MathHelper.floor_float(levels * ENCHANTMENT_LEVEL_MULTIPLIER);
                levelsNeeded += levels;
            }
        }
    }
    
    private boolean listContainsEnchantment(Enchantment ench) {
        
        for (EnchantmentData data : enchantments) {
            if (data.enchantmentobj.effectId == ench.effectId) {
                return true;
            }
        }
        
        return false;
    }
    
    private void addErrorMessage(String error) {
        if (!errorMessages.contains(error)) {
            errorMessages.add(error);
        }
    }
    
    public boolean hasErrors() {
        return error;
    }
    
    public LinkedList<String> getErrorMessages() {
        return errorMessages;
    }
    
    public ItemStack getResult() {
        return result;
    }
    
    public int getLevelsNeeded() {
        return levelsNeeded;
    }
    
    public List<EnchantmentData> getEnchantments() {
        return enchantments;
    }
    
    private static class EnchantmentComparator implements Comparator<EnchantmentData> {
        
        @Override
        public int compare(EnchantmentData o1, EnchantmentData o2) {
            return o1.enchantmentobj.getTranslatedName(1).compareToIgnoreCase(
                    o2.enchantmentobj.getTranslatedName(1));
        }
        
    }
}
