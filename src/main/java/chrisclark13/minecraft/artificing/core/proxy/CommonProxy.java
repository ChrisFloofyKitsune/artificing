package chrisclark13.minecraft.artificing.core.proxy;

import net.minecraftforge.common.MinecraftForge;
import chrisclark13.minecraft.artificing.core.handler.LivingEventHandler;
import chrisclark13.minecraft.artificing.lib.Strings;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.artificing.tileentity.TileDisenchanter;
import chrisclark13.minecraft.artificing.tileentity.TileResearchTable;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

	public LivingEventHandler livingEventHandler;

	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileArtificingTable.class, Strings.TE_ARTIFICING_TABLE_NAME);
		GameRegistry.registerTileEntity(TileResearchTable.class, Strings.TE_RESEARCH_TABLE_NAME);
		GameRegistry.registerTileEntity(TileDisenchanter.class, Strings.TE_DISENCHANTER_NAME);
	}
	
	public void registerEventHandlers() {
		
	}
	
	public void initRendering() {
	    
	}
}
