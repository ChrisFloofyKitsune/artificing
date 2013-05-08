package chrisclark13.minecraft.multislotitems;

import net.minecraft.item.ItemStack;

public class MultiSlotItemHelper {
	public static boolean hasSignature(ItemStack itemStack) {
		if (itemStack.getItem() instanceof IMultiSlotItem) {
			return true;
		} else {
			String unlocalizedName = itemStack.getItemName();
			return (MultiSlotItemRegistry.getSignatureString(unlocalizedName) != null);
		}
	}
	
	public static MultiSlotItemSlotSignature getSignature(ItemStack itemStack) {
		String signature = "X";
		if (itemStack.getItem() instanceof IMultiSlotItem) {
			signature = ((IMultiSlotItem)itemStack.getItem()).getSlotSignature(itemStack);
		} else if (MultiSlotItemRegistry.getSignatureString(itemStack.getItemName()) != null) {
			signature = MultiSlotItemRegistry.getSignatureString(itemStack.getItemName());
		}
		
		
		return MultiSlotItemSlotSignature.getFromString(signature);
	}
	
	public static String getImagePath(ItemStack itemStack) {
		if (itemStack.getItem() instanceof IMultiSlotItem) {
			return ((IMultiSlotItem) itemStack.getItem()).getImagePath(itemStack);
		} else {
			return MultiSlotItemRegistry.getImagePath(itemStack.getItemName());
		}
	}
}
