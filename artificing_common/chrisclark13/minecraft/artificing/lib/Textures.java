package chrisclark13.minecraft.artificing.lib;

import chrisclark13.minecraft.artificing.core.helper.ResourceLocationHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class Textures {
	
	public static final ResourceLocation VANILLA_BLOCK_TEXTURE_SHEET = TextureMap.field_110575_b;
    public static final ResourceLocation VANILLA_ITEM_TEXTURE_SHEET = TextureMap.field_110576_c;
	public static final ResourceLocation VANILLA_GLINT	 		= new ResourceLocation("textures/misc/enchanted_item_glint.png");
    
    public static final String MSI_LOC							= "textures/items/multislot/";
    public static final ResourceLocation RUNES				    = ResourceLocationHelper.create("textures/runes.png");

    public static final ResourceLocation ARTIFICING_TABLE		= ResourceLocationHelper.create("textures/gui/artificingTable.png");
    public static final ResourceLocation RESEARCH_TABLE    		= ResourceLocationHelper.create("textures/gui/researchTable.png");
    public static final ResourceLocation GUIDE_BOOK        	 	= ResourceLocationHelper.create("textures/gui/guideBook.png");
    public static final ResourceLocation RINGS             	 	= ResourceLocationHelper.create("textures/gui/rings.png");
    public static final ResourceLocation STAR               	= ResourceLocationHelper.create("textures/blocks/star.png");

    public static final ResourceLocation MODEL_RESEARCH_TABLE 	= ResourceLocationHelper.create("textures/models/researchTable.png");
    public static final ResourceLocation MODEL_DISENCHANTER   	= ResourceLocationHelper.create("textures/models/disenchanter.png");
    
    public static final ResourceLocation GUI_PARTS            	= ResourceLocationHelper.create("textures/gui/guiParts.png");
    public static final ResourceLocation TAB_PARTS            	= ResourceLocationHelper.create("textures/gui/tabParts.png");
    public static final ResourceLocation GUI_ICONS            	= ResourceLocationHelper.create("textures/gui/guiIcons.png");
    public static final ResourceLocation GUIDE_BOOK_TAB_PARTS 	= ResourceLocationHelper.create("textures/gui/guideBookTabParts.png");
}
