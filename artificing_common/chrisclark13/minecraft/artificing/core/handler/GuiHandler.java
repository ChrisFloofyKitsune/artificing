package chrisclark13.minecraft.artificing.core.handler;

import chrisclark13.minecraft.artificing.client.gui.inventory.GuiArtificingTable;
import chrisclark13.minecraft.artificing.client.gui.inventory.GuiResearchTable;
import chrisclark13.minecraft.artificing.client.gui.inventory.guidepages.GuiGuideBook;
import chrisclark13.minecraft.artificing.inventory.ContainerArtificingTable;
import chrisclark13.minecraft.artificing.inventory.ContainerResearchTable;
import chrisclark13.minecraft.artificing.item.ItemGuideBook;
import chrisclark13.minecraft.artificing.lib.GuiIds;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.artificing.tileentity.TileResearchTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    
    /**
     * Get correct Container for GuiID
     */
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GuiIds.ARTIFICING_TABLE) {
            return new ContainerArtificingTable(player.inventory,
                    (TileArtificingTable) world.getBlockTileEntity(x, y, z));
        } else if (ID == GuiIds.DISENCHANTER) {
            return null;
        } else if (ID == GuiIds.RESEARCH_TABLE) {
            return new ContainerResearchTable(player.inventory,
                    (TileResearchTable) world.getBlockTileEntity(x, y, z));
        } else if( ID == GuiIds.GUIDE_BOOK) {
            return null;
        }else{
            return null;
        }
    }
    
    /**
     * Get correct GUI for GuiID
     */
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GuiIds.ARTIFICING_TABLE) {
            return new GuiArtificingTable(player.inventory,
                    (TileArtificingTable) world.getBlockTileEntity(x, y, z));
        } else if (ID == GuiIds.DISENCHANTER) {
            return null;
        } else if (ID == GuiIds.RESEARCH_TABLE) {
            return new GuiResearchTable(player.inventory,
                    (TileResearchTable) world.getBlockTileEntity(x, y, z));
        } else if( ID == GuiIds.GUIDE_BOOK) {
            ItemStack stack = player.getCurrentEquippedItem();
            if (stack.getItem() instanceof ItemGuideBook) {
                return new GuiGuideBook(player, stack);
            } else {
                return null;
            }
        }else{
            return null;
        }
    }
    
}
