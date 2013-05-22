package chrisclark13.minecraft.artificing.client.renderer.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.FMLClientHandler;

import chrisclark13.minecraft.artificing.block.ModBlocks;
import chrisclark13.minecraft.artificing.client.renderer.ArtificingRenderHelper;
import chrisclark13.minecraft.artificing.client.renderer.RuneRenderer;
import chrisclark13.minecraft.artificing.item.crafting.ArtificingManager;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TileArtificingTableRenderer extends TileEntitySpecialRenderer {
    
    // This method is called when minecraft renders a tile entity
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double d, double d1, double d2, float f) {
        GL11.glPushMatrix();
        // This will move our renderer so that it will be on proper place in the
        // world
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        TileArtificingTable artificingTable = (TileArtificingTable) tileEntity;
        /*
         * Note that true tile entity coordinates (tileEntity.xCoord, etc) do
         * not match to render coordinates (d, etc) that are calculated as [true
         * coordinates] - [player coordinates (camera coordinates)]
         */
        renderTable(artificingTable, ModBlocks.artificingTable, tileEntity.worldObj, tileEntity.xCoord, tileEntity.yCoord,
                tileEntity.zCoord, f);
        
        GL11.glPopMatrix();
    }
    
    // And this method actually renders your tile entity
    public void renderTable(TileArtificingTable table, Block block, World world, int x, int y, int z, float partialTicks) {
        Tessellator tessellator = Tessellator.instance;
        
        // This will make your block brightness dependent from surroundings
        // lighting.
        float f = block.getBlockBrightness(world, x, y + 1, z);
        int l = world.getLightBrightnessForSkyBlocks(x, y + 1, z, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        tessellator.setColorOpaque_F(f, f, f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) l1, (float) l2);
        
        float animTimer = table.tickCount + partialTicks;
        
        GL11.glPushMatrix();
        {
            GL11.glTranslatef(0.5F, 0, 0.5F);
            // This line actually rotates the renderer.
            
            int angle = 0;
            ForgeDirection direction = ForgeDirection.getOrientation(table.getBlockMetadata());
            if (direction != null) {
                if (direction == ForgeDirection.NORTH) {
                    angle = 180;
                } else if (direction == ForgeDirection.SOUTH) {
                    angle = 0;
                } else if (direction == ForgeDirection.WEST) {
                    angle = -90;
                } else if (direction == ForgeDirection.EAST) {
                    angle = 90;
                }
            }
            
            GL11.glRotatef(angle, 0F, 1F, 0F);
            GL11.glTranslatef(-0.5F, 0, -0.5F);
            // bindTextureByName("yourTexturePath");
            
            /*
             * Place your rendering code here.
             */
            
            GL11.glRotatef(-90F, 1.0F, 0F, 0F);
            GL11.glTranslatef(0F, -1f, 1F);
            
            GL11.glPushMatrix();
            {
                final float TILT_DEGREES = 5F;
                final float ANIM_PERIOD = 60F;
                
                float sinTimer = MathHelper.sin((float) (((animTimer % ANIM_PERIOD) / ANIM_PERIOD) * 2 * Math.PI));
                float cosTimer = MathHelper.cos((float) (((animTimer % ANIM_PERIOD) / ANIM_PERIOD) * 2 * Math.PI));
                float hover = (sinTimer + 1) / 4F / 16F + (1F / 16F);
                float xTilt = cosTimer * TILT_DEGREES;
                float yTilt = sinTimer * TILT_DEGREES;
                float zTilt = sinTimer * TILT_DEGREES;
                GL11.glTranslatef(4F / 16F, 4f / 16F, hover);
                GL11.glScalef(0.5F, 0.5F, 0.5F);
                
                GL11.glTranslatef(0.5F, 0.5F, 1F / 32F);
                GL11.glRotatef(xTilt, 1, 0, 0);
                GL11.glRotatef(yTilt, 0, 1, 0);
                GL11.glRotatef(zTilt, 0, 0, 1);
                GL11.glTranslatef(-0.5F, -0.5F, -(1F / 32F));
                
                if (table.getStackInSlot(table.INPUT_SLOT_INDEX) != null) {
                    ArtificingRenderHelper.render3DItemStackForDisplay(table.getStackInSlot(table.INPUT_SLOT_INDEX));
                }
            }
            GL11.glPopMatrix();
            
            GL11.glPushMatrix();
            {
                final float ANIM_PERIOD = 13F * 20F;
                
                GL11.glTranslatef(2f / 16F, 2F / 16F, 1F / 256F);
                GL11.glScalef(0.75F, 0.75F, 0.75F);
                
                float rotation = ((animTimer % ANIM_PERIOD) / ANIM_PERIOD) * 360;
                
                GL11.glTranslatef(0.5f, 0.5f, 0);
                GL11.glRotatef(rotation, 0, 0, 1);
                GL11.glTranslatef(-0.5F, -0.5f, 0);
                
                bindTextureByName("/mods/artificing/textures/blocks/star.png");
                ArtificingRenderHelper.drawImage(0, 0, 1, 1);
            }
            GL11.glPopMatrix();
            
//            GL11.glPushMatrix();
//            {
//                GL11.glTranslatef(0, 0, 2F / 256F);
//                RuneRenderer.renderRunes("test", 0x000000);
//            }
//            GL11.glPopMatrix();
            
        }
        GL11.glPopMatrix();
    }
}
