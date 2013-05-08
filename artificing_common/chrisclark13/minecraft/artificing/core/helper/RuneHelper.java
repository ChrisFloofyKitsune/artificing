package chrisclark13.minecraft.artificing.core.helper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import chrisclark13.minecraft.artificing.item.ModItems;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class RuneHelper {
    
    private static final String DEFAULT = "default";
    public static HashMap<String, Integer> enchantmentColors;
    
    //Rune overlay colors
    public static void initColors() {
        enchantmentColors = new HashMap<String, Integer>();
        
        //TODO Set up loading from a config file made for just this purpose
        setEnchantmentColor(DEFAULT, 0x00CC13);
        setEnchantmentColor("enchantment.damage.all", 0xBBBBBB);
        setEnchantmentColor("enchantment.damage.undead", 0xFFFF00);
        setEnchantmentColor("enchantment.damage.arthropods", 0x3333AA);
        setEnchantmentColor("enchantment.knockback", 0xFF6600);
        setEnchantmentColor("enchantment.fire", 0xFF0000);
        setEnchantmentColor("enchantment.protect.all", 0xBBBBBB);
        setEnchantmentColor("enchantment.protect.fire", 0xFF0000);
        setEnchantmentColor("enchantment.protect.fall", 0x88CCFF);
        setEnchantmentColor("enchantment.protect.explosion", 0x666666);
        setEnchantmentColor("enchantment.protect.projectile", 0xDD8811);
        setEnchantmentColor("enchantment.oxygen", 0x88CCFF);
        setEnchantmentColor("enchantment.waterWorker", 0x0000FF);
        setEnchantmentColor("enchantment.digging", 0x008800);
        setEnchantmentColor("enchantment.untouching", 0x880088);
        setEnchantmentColor("enchantment.durability", 0xBBBBBB);
        setEnchantmentColor("enchantment.lootBonus", 0x00FFFF);
        setEnchantmentColor("enchantment.lootBonusDigger", 0x00FFFF);
        setEnchantmentColor("enchantment.arrowDamage", 0xAAAAAA);
        setEnchantmentColor("enchantment.arrowFire", 0xFF0000);
        setEnchantmentColor("enchantment.arrowKnockback", 0xFF6600);
        setEnchantmentColor("enchantment.arrowInfinite", 0x119988);
        setEnchantmentColor("enchantment.thorns", 0x996633);
        
        
        /*
        enchantment.damage.all=Sharpness
        enchantment.damage.undead=Smite
        enchantment.damage.arthropods=Bane of Arthropods
        enchantment.knockback=Knockback
        enchantment.fire=Fire Aspect
        enchantment.protect.all=Protection
        enchantment.protect.fire=Fire Protection
        enchantment.protect.fall=Feather Falling
        enchantment.protect.explosion=Blast Protection
        enchantment.protect.projectile=Projectile Protection
        enchantment.oxygen=Respiration
        enchantment.waterWorker=Aqua Affinity
        enchantment.digging=Efficiency
        enchantment.untouching=Silk Touch
        enchantment.durability=Unbreaking
        enchantment.lootBonus=Looting
        enchantment.lootBonusDigger=Fortune
        enchantment.arrowDamage=Power
        enchantment.arrowFire=Flame
        enchantment.arrowKnockback=Punch
        enchantment.arrowInfinite=Infinity
        enchantment.thorns=Thorns
        */
    }
    
    public static void setEnchantmentColor(Enchantment enchantment, int color) {
        setEnchantmentColor(enchantment.getName(), color);
    }
    
    public static void setEnchantmentColor(String enchantmentUnlocalizedName, int color) {
        enchantmentColors.put(enchantmentUnlocalizedName, color);
    }
    
    public static int getEnchantmentColor(Enchantment enchantment) {
        return enchantment == null ? null : getEnchantmentColor(enchantment.getName());
    }
    
    public static int getEnchantmentColor(String enchantmentUnlocalizedName) {
        return enchantmentUnlocalizedName == null ? enchantmentColors.get(DEFAULT) : enchantmentColors.containsKey(enchantmentUnlocalizedName) ? enchantmentColors.get(enchantmentUnlocalizedName) : enchantmentColors.get(DEFAULT);
    }
    
    public static int getDefaultColor() {
        return enchantmentColors.get(DEFAULT);
    }
    
    
    
    @SuppressWarnings("rawtypes")
    public static List getAllRunes() {
        
        LinkedList<ItemStack> itemStacks = new LinkedList<ItemStack>();
        for (Enchantment enchantment : Enchantment.enchantmentsList) {
            if (enchantment != null) {
                for (int level = enchantment.getMinLevel(); level <= enchantment.getMaxLevel(); level++) {
                    itemStacks.add(ModItems.rune.getItemStackWithEnchantment(enchantment, level));
                }
            }
        }
        
        return itemStacks;
    }
    
    
    
    
}
