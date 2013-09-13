package chrisclark13.minecraft.artificing.core.helper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import chrisclark13.minecraft.artificing.item.ItemRune;
import chrisclark13.minecraft.artificing.item.ModItems;
import chrisclark13.minecraft.artificing.lib.Homestuck;
import chrisclark13.minecraft.artificing.lib.Textures;
import chrisclark13.minecraft.multislotitems.core.MultiSlotItemRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

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

		// References ho!
		setEnchantmentColor(ENCHANTMENT_DEFAULT, 0x00CC13);
		setEnchantmentColor("enchantment.damage.all", Homestuck.GREY);
		setEnchantmentColor("enchantment.damage.undead", Homestuck.GOLD);
		setEnchantmentColor("enchantment.damage.arthropods", Homestuck.COBALT);
		setEnchantmentColor("enchantment.knockback", Homestuck.BRONZE);
		setEnchantmentColor("enchantment.fire", Homestuck.RED);
		setEnchantmentColor("enchantment.protect.all", Homestuck.GREY);
		setEnchantmentColor("enchantment.protect.fire", Homestuck.RED);
		setEnchantmentColor("enchantment.protect.fall", Homestuck.BRONZE);
		setEnchantmentColor("enchantment.protect.explosion", Homestuck.PURPLE);
		setEnchantmentColor("enchantment.protect.projectile", Homestuck.INDIGO);
		setEnchantmentColor("enchantment.oxygen", Homestuck.FUCHSIA);
		setEnchantmentColor("enchantment.waterWorker", Homestuck.VIOLET);
		setEnchantmentColor("enchantment.digging", Homestuck.OLIVE);
		setEnchantmentColor("enchantment.untouching", Homestuck.COBALT);
		setEnchantmentColor("enchantment.durability", Homestuck.GREY);
		setEnchantmentColor("enchantment.lootBonus", Homestuck.TEAL);
		setEnchantmentColor("enchantment.lootBonusDigger", Homestuck.TEAL);
		setEnchantmentColor("enchantment.arrowDamage", Homestuck.GREY);
		setEnchantmentColor("enchantment.arrowFire", Homestuck.RED);
		setEnchantmentColor("enchantment.arrowKnockback", Homestuck.BRONZE);
		setEnchantmentColor("enchantment.arrowInfinite", Homestuck.GOLD);
		setEnchantmentColor("enchantment.thorns", Homestuck.JADE);

		/*
		 * setEnchantmentColor(ENCHANTMENT_DEFAULT, 0x00CC13);
		 * setEnchantmentColor("enchantment.damage.all", 0xBBBBBB);
		 * setEnchantmentColor("enchantment.damage.undead", 0xFFFF00);
		 * setEnchantmentColor("enchantment.damage.arthropods", 0x3333AA);
		 * setEnchantmentColor("enchantment.knockback", 0xFF6600);
		 * setEnchantmentColor("enchantment.fire", 0xFF0000);
		 * setEnchantmentColor("enchantment.protect.all", 0xBBBBBB);
		 * setEnchantmentColor("enchantment.protect.fire", 0xFF0000);
		 * setEnchantmentColor("enchantment.protect.fall", 0x88CCFF);
		 * setEnchantmentColor("enchantment.protect.explosion", 0x666666);
		 * setEnchantmentColor("enchantment.protect.projectile", 0xDD8811);
		 * setEnchantmentColor("enchantment.oxygen", 0x88CCFF);
		 * setEnchantmentColor("enchantment.waterWorker", 0x0000FF);
		 * setEnchantmentColor("enchantment.digging", 0x008800);
		 * setEnchantmentColor("enchantment.untouching", 0x880088);
		 * setEnchantmentColor("enchantment.durability", 0xBBBBBB);
		 * setEnchantmentColor("enchantment.lootBonus", 0x00FFFF);
		 * setEnchantmentColor("enchantment.lootBonusDigger", 0x00FFFF);
		 * setEnchantmentColor("enchantment.arrowDamage", 0xAAAAAA);
		 * setEnchantmentColor("enchantment.arrowFire", 0xFF0000);
		 * setEnchantmentColor("enchantment.arrowKnockback", 0xFF6600);
		 * setEnchantmentColor("enchantment.arrowInfinite", 0x119988);
		 * setEnchantmentColor("enchantment.thorns", 0x996633);
		 */

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

	public static void setEnchantmentColor(String enchantmentUnlocalizedName,
			int color) {
		enchantmentColors.put(enchantmentUnlocalizedName, color);
	}

	public static int getEnchantmentColor(Enchantment enchantment) {
		return enchantment == null ? null : getEnchantmentColor(enchantment
				.getName());
	}

	public static int getEnchantmentColor(String enchantmentUnlocalizedName) {
		return enchantmentUnlocalizedName == null ? enchantmentColors
				.get(ENCHANTMENT_DEFAULT) : enchantmentColors
				.containsKey(enchantmentUnlocalizedName) ? enchantmentColors
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
		MultiSlotItemRegistry.registerUnlocalizedName(ENCHANTMENT_DEFAULT
				+ ".1", ResourceLocationHelper.create(Textures.MSI_LOC
				+ "runeAll1.png"), "X\n" + "#");
		MultiSlotItemRegistry.registerUnlocalizedName(ENCHANTMENT_DEFAULT
				+ ".2", ResourceLocationHelper.create(Textures.MSI_LOC
				+ "runeAll2.png"), " #\n" + "#X");
		MultiSlotItemRegistry.registerUnlocalizedName(ENCHANTMENT_DEFAULT
				+ ".3", ResourceLocationHelper.create(Textures.MSI_LOC + "runeAll3.png"), "##\n" + "X#\n"
				+ "# ");
		MultiSlotItemRegistry.registerUnlocalizedName(ENCHANTMENT_DEFAULT
				+ ".4", ResourceLocationHelper.create(Textures.MSI_LOC + "runeAll4.png"), "#  #\n" + "##X#\n"
				+ "#  #");
		MultiSlotItemRegistry.registerUnlocalizedName(ENCHANTMENT_DEFAULT
				+ ".5", ResourceLocationHelper.create(Textures.MSI_LOC + "runeAll5.png"), "##  \n" + " ###\n"
				+ " X##\n" + " ###\n" + "##  ");

		// Armor
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.armor.1",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeArmor1.png"), "X\n" + "#");
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.armor.2",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeArmor2.png"), "#\n" + "X\n" + "#");
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.armor.3",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeArmor3.png"), " # \n" + "#X#\n" + "##");
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.armor.4",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeArmor4.png"), "###\n" + " X \n"
						+ "###\n" + " # ");
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.armor.5",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeArmor5.png"), "###\n" + " # \n"
						+ "#X#\n" + " # \n" + "###");

		// Armor_Head
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_head.1", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorHead1.png"), "X\n" + "#");
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_head.2", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorHead2.png"), "#\n" + "X\n" + "#");
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_head.3", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorHead3.png"), " # \n" + "#X#\n" + "##");
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_head.4", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorHead4.png"), "###\n" + " X \n" + "###\n"
						+ " # ");
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_head.5", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorHead5.png"), "###\n" + " # \n" + "#X#\n"
						+ " # \n" + "###");

		// Armor_Torso
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_torso.1", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorTorso1.png"), "X\n" + "#");
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_torso.2", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorTorso2.png"), "#\n" + "X\n" + "#");
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_torso.3", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorTorso3.png"), " # \n" + "#X#\n" + "##");
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_torso.4", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorTorso4.png"), "###\n" + " X \n" + "###\n"
						+ " # ");
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_torso.5", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorTorso5.png"), "###\n" + " # \n" + "#X#\n"
						+ " # \n" + "###");

		// Armor_Legs
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_legs.1", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorLegs1.png"), "X\n" + "#");
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_legs.2", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorLegs2.png"), "#\n" + "X\n" + "#");
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_legs.3", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorLegs3.png"), " # \n" + "#X#\n" + "##");
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_legs.4", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorLegs4.png"), "###\n" + " X \n" + "###\n"
						+ " # ");
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_legs.5", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorLegs5.png"), "###\n" + " # \n" + "#X#\n"
						+ " # \n" + "###");

		// Armor_Feet
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_feet.1", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorFeet1.png"), "X\n" + "#");
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_feet.2", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorFeet2.png"), "#\n" + "X\n" + "#");
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_feet.3", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorFeet3.png"), " # \n" + "#X#\n" + "##");
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_feet.4", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorFeet4.png"), "###\n" + " X \n" + "###\n"
						+ " # ");
		MultiSlotItemRegistry.registerUnlocalizedName(
				"enchantment.armor_feet.5", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeArmorFeet5.png"), "###\n" + " # \n" + "#X#\n"
						+ " # \n" + "###");

		// Tool/Digger
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.digger.1",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeTool1.png"), "X#");
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.digger.2",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeTool2.png"), " #\n" + "#X");
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.digger.3",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeTool3.png"), "#X#\n" + "# #");
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.digger.4",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeTool4.png"), "###\n" + "#X#\n" + "###");
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.digger.5",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeTool5.png"), " # \n" + "####\n"
						+ "#X#\n" + "####\n");

		// Weapon
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.weapon.1",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeWeapon1.png"), "X#");
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.weapon.2",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeWeapon2.png"), "X#\n" + "# ");
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.weapon.3",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeWeapon3.png"), "#X##\n" + "   #");
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.weapon.4",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeWeapon4.png"), "##  \n" + " #X#\n"
						+ " ###");
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.weapon.5",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeWeapon5.png"), "## # \n" + "##X##\n"
						+ "# ###");

		// Bow
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.bow.1",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeBow1.png"), "X#");
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.bow.2",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeBow2.png"), "X#\n" + "# ");
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.bow.3",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeBow3.png"), "#X##\n" + "   #");
		MultiSlotItemRegistry
				.registerUnlocalizedName("enchantment.bow.4", ResourceLocationHelper.create(Textures.MSI_LOC
						+ "runeBow4.png"), "##  \n" + " #X#\n" + " ###");
		MultiSlotItemRegistry.registerUnlocalizedName("enchantment.bow.5",
				ResourceLocationHelper.create(Textures.MSI_LOC + "runeBow5.png"), "## # \n" + "##X##\n"
						+ "# ###");
	}

	public static ResourceLocation getEnchantmentImageResource(EnchantmentData data) {
		return MultiSlotItemRegistry.getImageResource(getEnchantmentMSIName(data));
	}

	public static String getEnchantmentSignatureString(EnchantmentData data) {
		return MultiSlotItemRegistry
				.getSignatureString(getEnchantmentMSIName(data));
	}

	public static String getEnchantmentMSIName(EnchantmentData data) {
		if (data != null) {

			int level = MathHelper.clamp_int(data.enchantmentLevel, 1,
					data.enchantmentobj.getMaxLevel());

			if (data.enchantmentobj.getMaxLevel() < 5) {
				level = MathHelper.ceiling_float_int(((float) level)
						/ data.enchantmentobj.getMaxLevel() * 4f);
			}

			String name = data.enchantmentobj.getName() + "." + level;

			// Check for unique enchantment entry
			if (MultiSlotItemRegistry.hasEntry(name)) {
				return name;
				// Check for general entry
			} else {
				// Don't want a locale change to break the code
				name = "enchantment."
						+ data.enchantmentobj.type.name()
								.toLowerCase(Locale.US) + "." + level;
				if (MultiSlotItemRegistry.hasEntry(name)) {
					return name;
				}
			}
			return ENCHANTMENT_DEFAULT + "." + ((level > 5) ? 5 : level);
		}

		return ENCHANTMENT_DEFAULT + ".1";
	}

	@SuppressWarnings("rawtypes")
	public static List getAllRunes() {

		LinkedList<ItemStack> itemStacks = new LinkedList<ItemStack>();
		for (Enchantment enchantment : Enchantment.enchantmentsList) {
			if (enchantment != null) {
				for (int level = enchantment.getMinLevel(); level <= enchantment
						.getMaxLevel(); level++) {
					itemStacks.add(ModItems.rune.getRuneWithEnchantment(
							enchantment, level));
				}
			}
		}

		return itemStacks;
	}

	public static List<EnchantmentData> getEnchantments(ItemStack itemStack) {
		LinkedList<EnchantmentData> list = new LinkedList<>();

		if (itemStack.itemID == Item.enchantedBook.itemID) {
			NBTTagList tagList = ((ItemEnchantedBook) itemStack.getItem())
					.func_92110_g(itemStack);
			if (tagList != null) {
				for (int i = 0; i < tagList.tagCount(); i++) {
					NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
					list.add(new EnchantmentData(tag.getShort("id"), tag
							.getShort("lvl")));
				}
			}
		} else if (itemStack.getItem() instanceof ItemRune) {
			EnchantmentData data = ModItems.rune.getEnchantmentData(itemStack);
			if (data != null) {
				list.add(data);
			}
		} else {
			NBTTagList tagList = itemStack.getEnchantmentTagList();
			if (tagList != null) {
				for (int i = 0; i < tagList.tagCount(); i++) {
					NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
					list.add(new EnchantmentData(tag.getShort("id"), tag
							.getShort("lvl")));
				}
			}
		}

		return list;
	}

	public static void setEnchantments(ItemStack itemStack,
			List<EnchantmentData> list) {
		clearEnchantments(itemStack);

		String name;
		if (itemStack.itemID == Item.enchantedBook.itemID) {
			name = "StoredEnchantments";
		} else if (itemStack.getItem() instanceof ItemRune) {
			ModItems.rune.setEnchantmentData(itemStack, list.get(0));
			return;
		} else {
			name = "ench";
		}

		NBTTagList tagList = new NBTTagList();
		for (EnchantmentData data : list) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setShort("id", (short) data.enchantmentobj.effectId);
			tag.setShort("lvl", (short) data.enchantmentLevel);
			tagList.appendTag(tag);
		}

		if (!itemStack.hasTagCompound()) {
			itemStack.setTagCompound(new NBTTagCompound());
		}

		itemStack.getTagCompound().setTag(name, tagList);
	}

	public static void clearEnchantments(ItemStack itemStack) {
		if (itemStack.itemID == Item.enchantedBook.itemID) {
			if (itemStack.hasTagCompound()
					&& itemStack.getTagCompound().hasKey("StoredEnchantments"))
				itemStack.getTagCompound().removeTag("StoredEnchantments");
		} else if (itemStack.getItem() instanceof ItemRune) {
			if (itemStack.hasTagCompound()
					&& itemStack.getTagCompound().hasKey(
							ItemRune.TAG_ENCHANTMENT))
				itemStack.getTagCompound().removeTag(ItemRune.TAG_ENCHANTMENT);
		} else {
			if (itemStack.hasTagCompound()
					&& itemStack.getTagCompound().hasKey("ench"))
				itemStack.getTagCompound().removeTag("ench");
		}
	}

	public static int getEnchantabilityLevelForArtificing(ItemStack itemStack) {
		int enchantability = itemStack.getItem().getItemEnchantability();

		boolean enchantExists = false;
		for (Enchantment ench : Enchantment.enchantmentsList) {
			// If there is even a single enchantment that can be applied,
			// boost the level
			if (ench != null && ench.canApply(itemStack)) {
				enchantExists = true;
				break;
			}
		}

		if (!enchantExists) {
			return 0;
		}

		// If it has a base enchantability of just one (like the bow and book)
		// give it a boost to make it equal to iron.
		if (enchantability == 1) {
			enchantability = 15;
			// Boost tools like shears and fishing poles
		} else if (enchantability == 0) {
			enchantability = 10;
		}

		return MathHelper.ceiling_float_int((float) enchantability / 5F);
	}
}
