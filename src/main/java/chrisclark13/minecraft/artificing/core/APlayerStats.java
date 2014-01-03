package chrisclark13.minecraft.artificing.core;

import java.lang.ref.WeakReference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLCommonHandler;

/**@author Cyntain
 * Where the player handler checks if the player has the book or not
 * */
public class APlayerStats {
    
    public WeakReference<EntityPlayer> player;
    public boolean book;

}
