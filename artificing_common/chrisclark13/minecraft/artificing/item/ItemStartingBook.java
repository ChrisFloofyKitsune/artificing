package chrisclark13.minecraft.artificing.item;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chrisclark13.minecraft.artificing.Artificing;
import chrisclark13.minecraft.artificing.core.helper.LocalizationHelper;
import chrisclark13.minecraft.artificing.core.helper.RuneHelper;
import chrisclark13.minecraft.artificing.lib.GuiIds;
import chrisclark13.minecraft.artificing.lib.Reference;
import chrisclark13.minecraft.artificing.lib.Strings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class ItemStartingBook extends ItemArtificingGeneral {

    public ItemStartingBook(int id) {

        super(id);
        this.setMaxStackSize(1);
        this.setUnlocalizedName(Strings.BOOK_NAME);
        this.setCreativeTab(Artificing.tabArtificing);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {

//        player.openGui(Artificing.instance, GuiIds.STARTING_BOOK, world, 0, 0, 0);

        return itemstack;
    }

    @SuppressWarnings({ "unchecked" , "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean debug) {
        /* Change what you would like about this. TODO make a stingsUtil to wrap the text, do the if statement and add colour to the text using the /#### things*/
        list.add("Author: Cyntain (IIV) of Minecraftia"); // you will get your own book :P
        list.add("");
        list.add("Press " + "§6" + "shift" + "§7" + " to see more information");
        if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            
            list.clear();
            list.add(LocalizationHelper.getLocalizedString(this.getUnlocalizedName()));
            list.add("Author: Cyntain (IIV) of Minecraftia");
            list.add("");
            list.add("This book gives information about how to get");
            list.add("started with Artificing; including recipes,");
            list.add("what is what, and a guide to the world");
        }

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister iconregister) {

        this.itemIcon = iconregister.registerIcon(Reference.MOD_ID + ":" + "bookStarting");
    }
}
