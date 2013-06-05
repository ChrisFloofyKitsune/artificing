package chrisclark13.minecraft.artificing.inventory;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;

import chrisclark13.minecraft.artificing.core.helper.RuneHelper;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;

public class SlotArtificingOutput extends Slot {

    private IInventory craftingMatrix;
    private static Random rand = new Random();
    private static final float PENALTY_CHANCE = 0.5f;
    
	public SlotArtificingOutput(IInventory inventory, IInventory craftingMatrix, int id, int displayX,
			int displayY) {
		super(inventory, id, displayX, displayY);
		
		this.craftingMatrix = craftingMatrix;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return false;
	}
	
	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack itemStack) {
	    super.onPickupFromSlot(player, itemStack);
	    
	    for (int i = 0; i < craftingMatrix.getSizeInventory(); i++) {
	        if (craftingMatrix.getStackInSlot(i) != null) {
	            craftingMatrix.decrStackSize(i, 1);
	        }
	    }
	    
	    inventory.decrStackSize(TileArtificingTable.INPUT_SLOT_INDEX, 1);
	}
	
	
}
