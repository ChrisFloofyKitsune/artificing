package chrisclark13.minecraft.artificing.core.proxy;

import chrisclark13.minecraft.artificing.client.renderer.tileentity.TileArtificingTableRenderer;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerTileEntities() {
        super.registerTileEntities();
        
        ClientRegistry.bindTileEntitySpecialRenderer(TileArtificingTable.class, new TileArtificingTableRenderer());
        
    }
}
