package chrisclark13.minecraft.artificing.core.helper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import chrisclark13.minecraft.artificing.item.ModItems;
import chrisclark13.minecraft.artificing.lib.Textures;
import chrisclark13.minecraft.multislotitems.MultiSlotItemRegistry;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;

public class RuneHelper {

    private static final String ENCHANTMENT_DEFAULT = "enchantment.default";
    public static HashMap<String, Integer> enchantmentColors;

    public static void init() {
        initColors();
        initMultiSlotItems();
    }

    // Rune overlay colors
    public static void initColors() {
        enchantmentColors = new HashMap<String, Integer>();

        // TODO Set up loading from a config file made for just this purpose
        setEnchantmentColor(ENCHANTMENT_DEFAULT, 0x00CC13);
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
         * enchantment.damage.all=Sharpness enchantment.damage.undead=Smite
         * enchantment.damage.arthropods=Bane of Arthropods
         * enchantment.knockback=Knockback enchantment.fire=Fire Aspect
         * enchantment.protect.all=Protection enchantment.protect.fire=Fire
         * Protection enchantment.protect.fall=Feather Falling
         * enchantment.protect.explosion=Blast Protection
         * enchantment.protect.projectile=Projectile Protection
         * enchantment.oxygen=Respiration enchantment.waterWorker=Aqua Affinity
         * enchantment.digging=Efficiency enchantment.untouching=Silk Touch
         * enchantment.durability=Unbreaking enchantment.lootBonus=Looting
         * enchantment.lootBonusDigger=Fortune enchantment.arrowDamage=Power
         * enchantment.arrowFire=Flame enchantment.arrowKnockback=Punch
         * enchantment.arrowInfinite=Infinity enchantment.thorns=Thorns
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
        return enchantmentUnlocalizedName == null ? enchantmentColors.get(ENCHANTMENT_DEFAULT)
                : enchantmentColors.containsKey(enchantmentUnlocalizedName) ? enchantmentColors
                        .get(enchantmentUnlocalizedName) : enchantmentColors
                        .get(ENCHANTMENT_DEFAULT);
    }

    public static int getDefaultColor() {
        return enchantmentColors.get(ENCHANTMENT_DEFAULT);
    }

    public static void initMultiSlotItems() {

        // Unique Multi-Slot Item Image/Sigs

        // General MSI Images/Sigs
        // All/Default
        MultiSlotItemRegistry.registerUnlocalizedName(ENCHANTMENT_DEFAULT + ".1", Textures.MSI_LOC + "runeAll1.png",
                "X\n" + "#");
        MultiSlotItemRegistry.registerUnlocalizedName(ENCHANTMENT_DEFAULT + ".2", Textures.MSI_LOC + "runeAll2.png",
                " #\n" + "#X");
        MultiSlotItemRegistry.registerUnlocalizedName(ENCHANTMENT_DEFAULT + ".3", Textures.MSI_LOC + "runeAll3.png",
                "##\n" + "X#\n" + "# ");
        MultiSlotItemRegistry.registerUnlocalizedName(ENCHANTMENT_DEFAULT + ".4", Textures.MSI_LOC + "runeAll4.png",
                "#  #\n" + "##X#\n" + "#  #");
        MultiSlotItemRegistry.registerUnlocalizedName(ENCHANTMENT_DEFAULT + ".5", Textures.MSI_LOC + "runeAll5.png",
                "##  \n" + " ###\n" + " X##\n" + " ###\n" + "##  ");

        // Armor
        MultiSlotItemRegistry.registerUnlocalizedName("enchantment.armor.1", Textures.MSI_LOC + "runeArmor1.png",
                "X\n" + "#");
        MultiSlotItemRegistry.registerUnlocalizedName("enchantment.armor.2", Textures.MSI_LOC + "runeArmor2.png",
                "#\n" +"X\n" + "#");
        MultiSlotItemRegistry.registerUnlocalizedName("enchantment.armor.3", Textures.MSI_LOC + "runeArmor3.png",
                " # \n" + "#X#\n" + "##");
        MultiSlotItemRegistry.registerUnlocalizedName("enchantment.armor.4", Textures.MSI_LOC + "runeArmor4.png",
                "###\n" + " X \n" + "###\n" + " # ");
        MultiSlotItemRegistry.registerUnlocalizedName("enchantment.armor.5", Textures.MSI_LOC + "runeArmor5.png",
                "###\n" + " # \n" + "#X#\n" + " # \n" + "###");
        
        // Weapon
        MultiSlotItemRegistry.registerUnlocalizedName("enchantment.weapon.1", Textures.MSI_LOC + "runeWeapon1.png",
                "X#");
        MultiSlotItemRegistry.registerUnlocalizedName("enchantment.weapon.2", Textures.MSI_LOC + "runeWeapon2.png",
                "X#\n" + "# ");
        MultiSlotItemRegistry.registerUnlocalizedName("enchantment.weapon.3", Textures.MSI_LOC + "runeWeapon3.png",
                "#X##\n" + "   #");
        MultiSlotItemRegistry.registerUnlocalizedName("enchantment.weapon.4", Textures.MSI_LOC + "runeWeapon4.png",
                "##  \n" + " #X#\n" + " ###");
        MultiSlotItemRegistry.registerUnlocalizedName("enchantment.weapon.5", Textures.MSI_LOC + "runeWeapon5.png",
                "## # \n" + "##X##\n" + "# ###");
    }

    public static String getEnchantmentImagePath(EnchantmentData data) {

        

        if (data != null) {
            int level = MathHelper.clamp_int(data.enchantmentLevel, 1,
                    data.enchantmentobj.getMaxLevel());
            String name = data.enchantmentobj.getName() + "." + level;
            
            // Check for unique enchantment entry
            if (MultiSlotItemRegistry.hasEntry(name)) {
                return MultiSlotItemRegistry.getImagePath(name);
                // Check for general entry
            } else {
                // Don't want a locale change to break the code
                name = "enchantment." + data.enchantmentobj.type.name().toLowerCase(Locale.US)
                        + "." + level;
                if (MultiSlotItemRegistry.hasEntry(name)) {
                    return MultiSlotItemRegistry.getImagePath(name);
                }
            }
            
            return MultiSlotItemRegistry.getImagePath(ENCHANTMENT_DEFAULT + "."
                    + ((level > 5) ? 5 : level));
        }
        
        return MultiSlotItemRegistry.getImagePath(ENCHANTMENT_DEFAULT + ".1");
    }

    public static String getEnchantmentSignatureString(EnchantmentData data) {

        

        if (data != null) {
            
            int level = MathHelper.clamp_int(data.enchantmentLevel, 1,
                    data.enchantmentobj.getMaxLevel());
            String name = data.enchantmentobj.getName() + "." + level;
            
            // Check for unique enchantment entry
            if (MultiSlotItemRegistry.hasEntry(name)) {
                return MultiSlotItemRegistry.getSignatureString(name);
                // Check for general entry
            } else {
                // Don't want a locale change to break the code
                name = "enchantment." + data.enchantmentobj.type.name().toLowerCase(Locale.US)
                        + "." + level;
                if (MultiSlotItemRegistry.hasEntry(name)) {
                    return MultiSlotItemRegistry.getSignatureString(name);
                }
            }
            return MultiSlotItemRegistry.getSignatureString(ENCHANTMENT_DEFAULT + "."
                    + ((level > 5) ? 5 : level));
        }
        
        return MultiSlotItemRegistry.getSignatureString(ENCHANTMENT_DEFAULT + ".1");
        
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
