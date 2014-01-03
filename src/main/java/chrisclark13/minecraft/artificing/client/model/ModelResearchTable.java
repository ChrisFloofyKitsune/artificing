package chrisclark13.minecraft.artificing.client.model;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.lib.Models;
import chrisclark13.minecraft.artificing.lib.Textures;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelResearchTable {
    
    protected ResourceLocation tableTexturePath;
    private IModelCustom modelResearchTable;
    private final float MODEL_SCALE = 1F / 8F;
    
    public ModelResearchTable() {
        tableTexturePath = Textures.MODEL_RESEARCH_TABLE;
        modelResearchTable = AdvancedModelLoader.loadModel(Models.RESEARCH_TABLE);
    }
    
    public void render() {
        GL11.glPushMatrix();
        GL11.glTranslatef(0, 0, 1);
        GL11.glScalef(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(tableTexturePath);
        modelResearchTable.renderPart("Table_Cube");
        GL11.glPopMatrix();
    }
    
}
