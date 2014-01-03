package chrisclark13.minecraft.artificing.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;




import chrisclark13.minecraft.artificing.lib.Models;
import chrisclark13.minecraft.artificing.lib.Reference;
import chrisclark13.minecraft.artificing.lib.Textures;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDisenchanter {
    
    protected ResourceLocation texturePath;
    private IModelCustom modelDisenchanter;
    private final float MODEL_SCALE = 1F / 8F;
    
    private final float SECTION_HEIGHT = 6F / 8F;
    
    private final float PYLON_HEIGHT = 7F;
    private final float PYLON_POS = 1F;
    
    public final float CENTER_PERIOD = 1.0f;
    public final float MIN_ROTATION_PERIOD = 0.5f;
    
    public ModelDisenchanter() {
        texturePath = Textures.MODEL_DISENCHANTER;
        modelDisenchanter = AdvancedModelLoader.loadModel(Models.DISENCHANTER);
    }
    
    public void render() {
        double centerPeriod = CENTER_PERIOD * (float) Reference.MILLS_PER_SECOND;
        double centerTimer = (((double) Minecraft.getSystemTime() % centerPeriod) / centerPeriod);
        
        double centerPeriod2 = CENTER_PERIOD * 4 * (float) Reference.MILLS_PER_SECOND;
        double centerTimer2 = (((double) Minecraft.getSystemTime() % centerPeriod2) / centerPeriod);
        
        //Used to make the center stop when fully extended and fully retracted.
        if (centerTimer2 >= 1 && centerTimer2 < 2) {
            centerTimer = 1f;
        } else if (centerTimer2 >= 2 && centerTimer2 < 3) {
            centerTimer = 1f - centerTimer;
        } else if (centerTimer2 >= 3 && centerTimer2 < 4){
            centerTimer = 0f;
        }
        
        float rotPeriod = MIN_ROTATION_PERIOD * 4 * (float) Reference.MILLS_PER_SECOND;
        float rotation = (((float) Minecraft.getSystemTime() % rotPeriod) / rotPeriod) * 360;
        
        this.renderAnim((float) centerTimer, rotation);
    }
    
    public void renderAnim(float centerTimer, float rotationAngle) {
        
        centerTimer *= 3;
        GL11.glPushMatrix();
        {
            GL11.glTranslatef(0, 0, 1);
            GL11.glScalef(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(texturePath);
            modelDisenchanter.renderPart("Disenchanter");
            
            float height;
            GL11.glPushMatrix();
            {
                height = Math.max(Math.min(centerTimer, 1f), 0f);
                GL11.glTranslatef(0, height * SECTION_HEIGHT, 0);
                modelDisenchanter.renderPart("Center1");
            }
            GL11.glPopMatrix();
            
            GL11.glPushMatrix();
            {
                height = Math.max(Math.min(centerTimer, 2f), 0f);
                GL11.glTranslatef(0, height * SECTION_HEIGHT, 0);
                modelDisenchanter.renderPart("Center2");
            }
            GL11.glPopMatrix();
            
            GL11.glPushMatrix();
            {
                height = Math.max(Math.min(centerTimer, 3f), 0f);
                GL11.glTranslatef(0, height * SECTION_HEIGHT, 0);
                modelDisenchanter.renderPart("Center3");
            }
            GL11.glPopMatrix();
            
            GL11.glPushMatrix();
            {
                GL11.glTranslatef(PYLON_POS, PYLON_HEIGHT, -PYLON_POS);
                GL11.glRotatef(rotationAngle, 1, 0, 0);
                GL11.glRotatef(rotationAngle, 0, 0, -1);
                modelDisenchanter.renderPart("Diamond");
            }
            GL11.glPopMatrix();
            
            GL11.glPushMatrix();
            {
                GL11.glTranslatef(8F - PYLON_POS, PYLON_HEIGHT, -PYLON_POS);
                GL11.glRotatef(rotationAngle, -1, 0, 0);
                GL11.glRotatef(rotationAngle, 0, 0, -1);
                modelDisenchanter.renderPart("Diamond");
            }
            GL11.glPopMatrix();
            
            GL11.glPushMatrix();
            {
                GL11.glTranslatef(PYLON_POS, PYLON_HEIGHT, -8F + PYLON_POS);
                GL11.glRotatef(rotationAngle, 1, 0, 0);
                GL11.glRotatef(rotationAngle, 0, 0, 1);
                modelDisenchanter.renderPart("Diamond");
            }
            GL11.glPopMatrix();
            
            GL11.glPushMatrix();
            {
                GL11.glTranslatef(8F - PYLON_POS, PYLON_HEIGHT, -8F + PYLON_POS);
                GL11.glRotatef(rotationAngle, -1, 0, 0);
                GL11.glRotatef(rotationAngle, 0, 0, 1);
                modelDisenchanter.renderPart("Diamond");
            }
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }
    
}
