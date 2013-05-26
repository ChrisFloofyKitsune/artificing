package chrisclark13.minecraft.artificing.core.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import chrisclark13.minecraft.artificing.client.renderer.item.ItemResearchTableRenderer;
import chrisclark13.minecraft.artificing.client.renderer.tileentity.TileArtificingTableRenderer;
import chrisclark13.minecraft.artificing.client.renderer.tileentity.TileResearchTableRenderer;
import chrisclark13.minecraft.artificing.lib.BlockIds;
import chrisclark13.minecraft.artificing.lib.RenderIds;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.artificing.tileentity.TileResearchTable;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerTileEntities() {
        super.registerTileEntities();
        
        ClientRegistry.bindTileEntitySpecialRenderer(TileArtificingTable.class, new TileArtificingTableRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileResearchTable.class, new TileResearchTableRenderer());
    }
    
    @Override
    public void initRendering() {
        RenderIds.researchTableRenderId = RenderingRegistry.getNextAvailableRenderId();
        
        MinecraftForgeClient.registerItemRenderer(BlockIds.RESEARCH_TABLE_ID, new ItemResearchTableRenderer());
    }
}
