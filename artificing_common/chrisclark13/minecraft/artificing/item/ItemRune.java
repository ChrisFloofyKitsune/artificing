package chrisclark13.minecraft.artificing.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import chrisclark13.minecraft.artificing.block.ModBlocks;
import chrisclark13.minecraft.artificing.core.helper.LocalizationHelper;
import chrisclark13.minecraft.artificing.core.helper.RuneHelper;
import chrisclark13.minecraft.artificing.lib.Strings;
import chrisclark13.minecraft.multislotitems.core.IMultiSlotItem;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Base item for all Rune items
 * 
 * Enchantment ID and level is stored in the the digits of the item's metadata.
 *  Enchantment ID = metadata / 100
 *  This allows for level from 1 to 100
 *  Level = (metadata % 100) + 1
 *  metadata = (Enchantment ID * 100) + (Level - 1)
 * 
 * @author Christian
 * 
 */
public class ItemRune extends ItemArtificingGeneral implements IMultiSlotItem {
    
    public static final String TAG_ENCHANTMENT_LEVEL = "lvl";
    public static final String TAG_ENCHANTMENT_ID = "id";
    public static final String TAG_ENCHANTMENT = "enchantment";
    
    public static Icon runeBase;
    public static Icon runeBaseCopper;
    public static Icon runeBaseSilver;
    public static Icon runeBaseGold;
    public static Icon runeBaseDiamond;
    public static Icon runeBaseEmerald;
    public static Icon runeBaseObsidian;
    public static Icon[] runeBaseQualityArray;
    
    public static Icon runeAllOverlay;
    public static Icon runeArmorOverlay;
    public static Icon runeArmorHeadOverlay;
    public static Icon runeArmorTorsoOverlay;
    public static Icon runeArmorLegsOverlay;
    public static Icon runeArmorFeetOverlay;
    public static Icon runeToolOverlay;
    public static Icon runeBowOverlay;
    public static Icon runeWeaponOverlay;
    
    public ItemRune(int id) {
        super(id);
        this.setMaxStackSize(16);
        this.setUnlocalizedName(Strings.RUNE_NAME);
    }
    
    @Override
    public boolean shouldPassSneakingClickToBlock(World world, int x, int y, int z) {
        return world.getBlockId(x, y, z) == ModBlocks.artificingTable.blockID;
    }
    
    public void setEnchantmentData(ItemStack itemStack, EnchantmentData data) {
        this.setEnchantmentData(itemStack, data.enchantmentobj, data.enchantmentLevel);
    }
    
    public void setEnchantmentData(ItemStack itemStack, int enchantmentId, int level) {
        if (Enchantment.enchantmentsList[enchantmentId] != null) {
            this.setEnchantmentData(itemStack, Enchantment.enchantmentsList[enchantmentId], level);
        }
    }
    
    public void setEnchantmentData(ItemStack itemStack, Enchantment enchantment, int level) {
        //The enchantment level on a Rune can never break limits, or be zero for that matter
        level = MathHelper.clamp_int(level, 1, enchantment.getMaxLevel());
        
        if (!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        
        NBTTagCompound enchantmentTag;
        if (itemStack.getTagCompound().hasKey(TAG_ENCHANTMENT)) {
            enchantmentTag = itemStack.getTagCompound().getCompoundTag(TAG_ENCHANTMENT);
        } else {
            enchantmentTag = new NBTTagCompound();
            itemStack.getTagCompound().setCompoundTag(TAG_ENCHANTMENT, enchantmentTag);
        }
        
        enchantmentTag.setShort(TAG_ENCHANTMENT_ID, (short) enchantment.effectId);
        enchantmentTag.setShort(TAG_ENCHANTMENT_LEVEL, (short) level);
    }
 
    public NBTTagCompound getEnchantmentTag(ItemStack itemStack) {
        if (itemStack.hasTagCompound()) {
            if (itemStack.stackTagCompound.hasKey(TAG_ENCHANTMENT)) {
                return itemStack.stackTagCompound.getCompoundTag(TAG_ENCHANTMENT);
            }
        }
        return null;
    }
    
    public EnchantmentData getEnchantmentData(ItemStack itemStack) {
        NBTTagCompound tag = this.getEnchantmentTag(itemStack);
        
        if (tag != null) {
            int id = tag.getShort(TAG_ENCHANTMENT_ID);
            int lvl = tag.getShort(TAG_ENCHANTMENT_LEVEL);
            if ((Enchantment.enchantmentsList[id] != null) && (lvl > 0)) {
                return new EnchantmentData(id, lvl);
            }
        }
        
        return null;
    }
    
    public ItemStack getRuneWithEnchantment(EnchantmentData data) {
        ItemStack itemStack = new ItemStack(this.itemID, 1, 0);
        this.setEnchantmentData(itemStack, data);
        return itemStack;
    }
    
    public ItemStack getRuneWithEnchantment(Enchantment enchantment, int level) {
        ItemStack itemStack = new ItemStack(this.itemID, 1, 0);
        this.setEnchantmentData(itemStack, enchantment, level);
        return itemStack;
    }
    
    public Icon getBaseIcon(ItemStack itemStack) {
        EnchantmentData data = this.getEnchantmentData(itemStack);
        
        if (data != null) {
            if (data.enchantmentobj.getMaxLevel() == 1) {
                return runeBaseEmerald;
            } else {
                //For mod enchantments with a maxLevel greater than five
                int level = (data.enchantmentLevel >= 1 ? data.enchantmentLevel : 1);
                
                if (data.enchantmentobj.getMaxLevel() == 5) {
                    return runeBaseQualityArray[level - 1];
                } else {
                    return runeBaseQualityArray[MathHelper.ceiling_float_int(((float) level) / data.enchantmentobj.getMaxLevel() * 5f) - 1];
                }
                
            }
        }
        
        return runeBaseObsidian;
    }
    
    public Icon getOverlayIcon(ItemStack itemStack) {
        NBTTagCompound tag = this.getEnchantmentTag(itemStack);
        
        if (tag != null) {
            Enchantment enchantment = Enchantment.enchantmentsList[tag.getShort(TAG_ENCHANTMENT_ID)];
            if (enchantment != null) {
                switch (enchantment.type) {
                    case all:
                        return runeAllOverlay;
                    case armor:
                        return runeArmorOverlay;
                    case armor_head:
                        return runeArmorHeadOverlay;
                    case armor_torso:
                        return runeArmorTorsoOverlay;
                    case armor_legs:
                        return runeArmorLegsOverlay;
                    case armor_feet:
                        return runeArmorFeetOverlay;
                    case digger:
                        return runeToolOverlay;
                    case bow:
                        return runeBowOverlay;
                    case weapon:
                        return runeWeaponOverlay;
                    default:
                        return runeAllOverlay;
                }
            }
        }
        
        return runeAllOverlay;
    }
    
    //Minecraft Overrides
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        //Rune base icons
        runeBase = this.registerIconName(iconRegister, Strings.RUNE_ICON_BASE);
        runeBaseCopper = this.registerIconName(iconRegister, Strings.RUNE_ICON_BASE_COPPER);
        runeBaseSilver = this.registerIconName(iconRegister, Strings.RUNE_ICON_BASE_SILVER);
        runeBaseGold = this.registerIconName(iconRegister, Strings.RUNE_ICON_BASE_GOLD);
        runeBaseDiamond = this.registerIconName(iconRegister, Strings.RUNE_ICON_BASE_DIAMOND);
        runeBaseEmerald = this.registerIconName(iconRegister, Strings.RUNE_ICON_BASE_EMERALD);
        runeBaseObsidian = this.registerIconName(iconRegister, Strings.RUNE_ICON_BASE_OBSIDIAN);
        
        runeBaseQualityArray = new Icon[] {runeBase, runeBaseCopper, runeBaseSilver, runeBaseGold, runeBaseDiamond};
        
        //Rune overlay icons
        runeAllOverlay = this.registerIconName(iconRegister, Strings.RUNE_ICON_ALL);
        runeArmorOverlay = this.registerIconName(iconRegister, Strings.RUNE_ICON_ARMOR);
        runeArmorHeadOverlay = this.registerIconName(iconRegister, Strings.RUNE_ICON_ARMOR_HEAD);
        runeArmorTorsoOverlay = this.registerIconName(iconRegister, Strings.RUNE_ICON_ARMOR_TORSO);
        runeArmorLegsOverlay = this.registerIconName(iconRegister, Strings.RUNE_ICON_ARMOR_LEGS);
        runeArmorFeetOverlay = this.registerIconName(iconRegister, Strings.RUNE_ICON_ARMOR_FEET);
        runeToolOverlay = this.registerIconName(iconRegister, Strings.RUNE_ICON_TOOL);
        runeBowOverlay = this.registerIconName(iconRegister, Strings.RUNE_ICON_BOW);
        runeWeaponOverlay = this.registerIconName(iconRegister, Strings.RUNE_ICON_WEAPON);
    }
     
    @Override
    public Icon getIcon(ItemStack stack, int pass) {
        if (pass == 1) {
            return this.getOverlayIcon(stack);
        } else {
            return this.getBaseIcon(stack);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int pass) {
        if (pass == 1) {
            NBTTagCompound tag = this.getEnchantmentTag(itemStack);
            if (tag != null) {
                return RuneHelper.getEnchantmentColor(Enchantment.enchantmentsList[tag.getShort(TAG_ENCHANTMENT_ID)].getName());
            } else {
                return RuneHelper.getDefaultColor();
            }
        } else {
            return 0xFFFFFF;
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    @Override
    public String getItemDisplayName(ItemStack itemStack) {
        EnchantmentData data = this.getEnchantmentData(itemStack);
        
        if (data != null) {
            return data.enchantmentobj.getTranslatedName(data.enchantmentLevel) + " " + LocalizationHelper.getLocalizedString(Strings.RUNE_NAME_APPEND);
        }
        
        return super.getItemDisplayName(itemStack);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player,
            List list, boolean debug) {
        super.addInformation(itemStack, player, list, debug);
        
        if (debug) {
            EnchantmentData data = getEnchantmentData(itemStack);
            if (data != null) {
                list.add("Name: " + data.enchantmentobj.getName());
                list.add("Type: " + data.enchantmentobj.type.name());
            }
            list.add("MSI: " + RuneHelper.getEnchantmentMSIName(data));
        }
        
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack itemStack) {
        return this.getEnchantmentTag(itemStack) != null;
    }

	@Override
	public ResourceLocation getImageResource(ItemStack itemStack) {
	    return RuneHelper.getEnchantmentImageResource(this.getEnchantmentData(itemStack));
	}

	@Override
	public String getSlotSignature(ItemStack itemStack) {
		return RuneHelper.getEnchantmentSignatureString(this.getEnchantmentData(itemStack));
	}
	
	@Override
	public int getImageColor(ItemStack itemStack) {
	    NBTTagCompound tag = this.getEnchantmentTag(itemStack);
        if (tag != null) {
            return RuneHelper.getEnchantmentColor(Enchantment.enchantmentsList[tag.getShort(TAG_ENCHANTMENT_ID)].getName());
        } else {
            return RuneHelper.getDefaultColor();
        }
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack itemStack) {
	    EnchantmentData data = getEnchantmentData(itemStack);
	    
	    if (data != null) {
	        int rarity = MathHelper.floor_float((float) data.enchantmentLevel / (float) data.enchantmentobj.getMaxLevel() * 3F);
	        switch (rarity) {
	            default:
	            case 0:
	                return EnumRarity.common;
	            case 1:
	                return EnumRarity.uncommon;
	            case 2:
	                return EnumRarity.rare;
	            case 3:
	                return EnumRarity.epic;
	        }
	        
	    }
	    
	    return EnumRarity.common;
	}
}
