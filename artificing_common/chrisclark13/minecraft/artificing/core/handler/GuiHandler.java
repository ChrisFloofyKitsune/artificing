package chrisclark13.minecraft.artificing.core.handler;

import chrisclark13.minecraft.artificing.client.gui.GuiArtificingTable;
import chrisclark13.minecraft.artificing.client.gui.inventory.GuiResearchTable;
import chrisclark13.minecraft.artificing.inventory.ContainerArtificingTable;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.artificing.tileentity.TileResearchTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	/**
	 * Get correct Container for GuiID
	 */
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID == 0) {
			return new ContainerArtificingTable(player.inventory,
					(TileArtificingTable) world.getBlockTileEntity(x, y, z));
		} else {
			return null;
		}
	}

	/**
	 * Get correct GUI for GuiID
	 */
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID == 0) {
			return new GuiArtificingTable(player.inventory, (TileArtificingTable) world.getBlockTileEntity(x, y, z));
		} else if(ID == 1) {
			return null;
		}else if(ID == 2){
		    return new GuiResearchTable(player.inventory, (TileResearchTable) world.getBlockTileEntity(x, y, z));
		}else {
		    return null;
		}
	}

}
