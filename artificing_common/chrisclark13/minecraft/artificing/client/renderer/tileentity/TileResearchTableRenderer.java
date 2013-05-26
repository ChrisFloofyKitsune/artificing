package chrisclark13.minecraft.artificing.client.renderer.tileentity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

import chrisclark13.minecraft.artificing.client.model.ModelResearchTable;
import chrisclark13.minecraft.artificing.lib.Textures;
import chrisclark13.minecraft.artificing.tileentity.TileResearchTable;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileResearchTableRenderer extends TileEntitySpecialRenderer {
    
    private ModelResearchTable modelResearchTable = new ModelResearchTable();
    
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
        if (tileEntity instanceof TileResearchTable) {
            
            TileResearchTable table = (TileResearchTable) tileEntity;
            
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            {
                GL11.glTranslated(x, y, z);
                
                GL11.glTranslatef(0.5F, 0, 0.5F);
                // This line actually rotates the renderer.
                
                int _angle = 0;
                ForgeDirection direction = ForgeDirection.getOrientation(table.getBlockMetadata());
                if (direction != null) {
                    if (direction == ForgeDirection.NORTH) {
                        _angle = 180;
                    } else if (direction == ForgeDirection.SOUTH) {
                        _angle = 0;
                    } else if (direction == ForgeDirection.WEST) {
                        _angle = -90;
                    } else if (direction == ForgeDirection.EAST) {
                        _angle = 90;
                    }
                }
                
                GL11.glRotatef(_angle, 0F, 1F, 0F);
                GL11.glTranslatef(-0.5F, 0, -0.5F);
                
                modelResearchTable.render();
                
            }
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
        
    }
    
}
