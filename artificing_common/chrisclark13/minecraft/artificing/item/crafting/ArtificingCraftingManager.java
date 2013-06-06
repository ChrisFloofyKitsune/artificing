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
    
    private static final float OVER_LEVELS_MULTIPLIER = 2;
    private InventoryArtificingGrid grid;
    private List<ItemGroup> itemGroups;
    
    private boolean error;
    private LinkedList<String> errorMessages;
    
    private ItemStack result;
    private LinkedList<EnchantmentData> enchantments;
    private LinkedList<EnchantmentData> previousEnchantments;
    private int levelsNeeded;
    
    private static RuneItemGroupComparer runeComparer = new RuneItemGroupComparer();
    private static EnchantmentComparator enchantmentComparator = new EnchantmentComparator();
    
    public ArtificingCraftingManager(InventoryArtificingGrid grid) {
        this.grid = grid;
        
        error = false;
        errorMessages = new LinkedList<>();
        
        result = null;
        enchantments = new LinkedList<>();
        previousEnchantments = new LinkedList<>();
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
        previousEnchantments.clear();
        if (itemStack != null) {
            previousEnchantments.addAll(RuneHelper.getEnchantments(itemStack));
            
            if (!itemStack.isItemEnchantable() && previousEnchantments.isEmpty()) {
                error = true;
                addErrorMessage(LocalizationHelper
                        .getLocalizedString(Strings.ARTIFICING_ERROR_UNENCHANTABLE));
            }
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
                        
                        for (EnchantmentData _data : previousEnchantments) {
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
        
        for (EnchantmentData data : previousEnchantments) {
            if (!listContainsEnchantment(data.enchantmentobj)) {
                enchantments.add(data);
            }
            
        }
        
        // Check if the enchantments are incompatible with the current item
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
        
        // Check to see if any enchantments are incompatible with eachother
        for (int i = 0; i < enchantments.size() - 1; i++) {
            for (int j = i + 1; j < enchantments.size(); j++) {
                if (!enchantments.get(i).enchantmentobj
                        .canApplyTogether(enchantments.get(j).enchantmentobj)) {
                    error = true;
                    String s = LocalizationHelper
                            .getLocalizedString(Strings.ARTIFICING_ERROR_INCOMPATIBLE);
                    s = String.format(s,
                            StatCollector.translateToLocal(enchantments.get(i).enchantmentobj
                                    .getName()),
                            StatCollector.translateToLocal(enchantments.get(j).enchantmentobj
                                    .getName()));
                    errorMessages.add(s);
                }
            }
        }
        
        // Now add the enchantments to the itemStack if we have no errors
        if (!error) {
            result = itemStack.copy();
            RuneHelper.setEnchantments(result, enchantments);
            
            for (EnchantmentData data : enchantments) {
                if (data.enchantmentLevel > data.enchantmentobj.getMaxLevel()) {
                    int overLevels = data.enchantmentLevel - data.enchantmentobj.getMaxLevel();
                    overLevels *= getCostPerLevel(data) * OVER_LEVELS_MULTIPLIER;
                    
                    levelsNeeded += overLevels;
                }
                
                int levels = MathHelper.ceiling_float_int(getCostPerLevel(data)
                        * data.enchantmentLevel);
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
    
    private float getCostPerLevel(EnchantmentData data) {
        switch (data.itemWeight) {
            case 1:
                return 8f;
            case 2:
                return 4f;
            case 3:
                return 3f;
            case 4:
                return 2.5f;
            case 5:
                return 2f;
            case 6:
                return 1.75f;
            case 7:
                return 1.5f;
            case 8:
                return 1.25f;
            case 9:
                return 1.125f;
            default:
                return 1f;
        }
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
    
    public List<EnchantmentData> getPreviousEnchantments() {
        return previousEnchantments;
    }
    
    public List<ItemGroup> getItemGroups() {
        return itemGroups;
    }
    
    private static class EnchantmentComparator implements Comparator<EnchantmentData> {
        
        @Override
        public int compare(EnchantmentData o1, EnchantmentData o2) {
            return o1.enchantmentobj.getTranslatedName(1).compareToIgnoreCase(
                    o2.enchantmentobj.getTranslatedName(1));
        }
        
    }
}
