package chrisclark13.minecraft.multislotitems;

import net.minecraft.item.ItemStack;

public interface IMultiSlotItem {
	//public void registerImages(MultiSlotItemRegistry msiRegistry);
	/**
	 * Get the image to draw for this multiSlotItem based on itemStack;
	 * @param itemStack
	 * @return
	 */
	public String getImagePath(ItemStack itemStack);
	
	/**
	 * Get the slots this ItemStack fills up in the inventory</br>
	 * The returned string should be in this format:</br>
	 * <code>" # \n"</br>
	 * "#X#\n"</br>
	 * " # \n"</br></code>
	 * Empty slots are represented by spaces, the center of the multiSlotItem is represented by an X.</br>
	 * All other non-space non-'X' characters represent spaces filled by the item.
	 * @param itemStack itemStack is passed so you can differentiate based on metadata and NBT tags
	 * @return String in the above format
	 */
	public String getSlotSignature(ItemStack itemStack);
}
