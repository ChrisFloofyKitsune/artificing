package chrisclark13.minecraft.multislotitems;

import java.util.HashMap;

/**
 * Registry for Items that don't implement IMultiSlotItem, like vanilla items and other mod items</br>
 * Should only be used after checking to see if an Item implements IMultiSlotItem or not because if it
 * does that item will likely return better information based on ItemStack data rather than an
 * unlocalized name.
 * 
 * @author ChrisClark13
 *
 */
public class MultiSlotItemRegistry {
	public static HashMap<String, Entry> entryMap = new HashMap<>();
	
	public static void registerUnlocalizedName(String unlocalizedName, String imagePath, String slotSingature) {
		entryMap.put(unlocalizedName, new Entry(imagePath, slotSingature));
	}
	
	public static String getImagePath(String unlocalizedName) {
		return entryMap.containsKey(unlocalizedName) ? entryMap.get(unlocalizedName).imagePath : null;
	}
	
	public static String getSignatureString(String unlocalizedName) {
		return entryMap.containsKey(unlocalizedName) ? entryMap.get(unlocalizedName).slotSingature : null;
	}
	
	private MultiSlotItemRegistry() {};
	
	private static class Entry {
		String imagePath;
		String slotSingature;
		
		private Entry(String imagePath, String slotSingature) {
			this.imagePath = imagePath;
			this.slotSingature = slotSingature;
		}
	}
}
