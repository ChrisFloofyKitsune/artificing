package chrisclark13.minecraft.artificing.core.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;
import chrisclark13.minecraft.artificing.client.gui.UnicodeFontRenderer;
import chrisclark13.minecraft.artificing.client.renderer.item.ItemDisenchanterRenderer;
import chrisclark13.minecraft.artificing.client.renderer.item.ItemResearchTableRenderer;
import chrisclark13.minecraft.artificing.client.renderer.tileentity.TileArtificingTableRenderer;
import chrisclark13.minecraft.artificing.client.renderer.tileentity.TileDisenchanterRenderer;
import chrisclark13.minecraft.artificing.client.renderer.tileentity.TileResearchTableRenderer;
import chrisclark13.minecraft.artificing.lib.BlockIds;
import chrisclark13.minecraft.artificing.lib.RenderIds;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.artificing.tileentity.TileDisenchanter;
import chrisclark13.minecraft.artificing.tileentity.TileResearchTable;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
    public static UnicodeFontRenderer unicodeFontRenderer;
    
    
    
    @Override
    public void registerTileEntities() {
        super.registerTileEntities();
        
        ClientRegistry.bindTileEntitySpecialRenderer(TileArtificingTable.class, new TileArtificingTableRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileResearchTable.class, new TileResearchTableRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileDisenchanter.class, new TileDisenchanterRenderer());
    }
    
    @Override
    public void initRendering() {
        Minecraft mc = Minecraft.getMinecraft();
        unicodeFontRenderer = new UnicodeFontRenderer(mc.gameSettings, "/font/default.png", mc.renderEngine, true);
        
        RenderIds.researchTableRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.disenchanterRenderId = RenderingRegistry.getNextAvailableRenderId();
        
        MinecraftForgeClient.registerItemRenderer(BlockIds.researchTableID, new ItemResearchTableRenderer());
        MinecraftForgeClient.registerItemRenderer(BlockIds.disenchanterID, new ItemDisenchanterRenderer());
    }
}
