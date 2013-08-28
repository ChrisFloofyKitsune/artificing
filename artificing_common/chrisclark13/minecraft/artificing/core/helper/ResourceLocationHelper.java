package chrisclark13.minecraft.artificing.core.helper;

import chrisclark13.minecraft.artificing.lib.Reference;
import net.minecraft.util.ResourceLocation;

public class ResourceLocationHelper {
	
	public static ResourceLocation create(String location) {
		return new ResourceLocation(Reference.MOD_ID, location);
	}
	
	
	private ResourceLocationHelper() {
	}
}
