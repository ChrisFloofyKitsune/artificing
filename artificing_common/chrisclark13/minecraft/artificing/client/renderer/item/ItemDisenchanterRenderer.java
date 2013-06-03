package chrisclark13.minecraft.artificing.client.renderer.item;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import chrisclark13.minecraft.artificing.client.model.ModelDisenchanter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

@SideOnly(Side.CLIENT)
public class ItemDisenchanterRenderer implements IItemRenderer {
    
    private ModelDisenchanter modelDisenchanter = new ModelDisenchanter();
    
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }
    
    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
            ItemRendererHelper helper) {
        return true;
    }
    
    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        
        switch (type) {
            case ENTITY:
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                    modelDisenchanter.render();
                }
                GL11.glPopMatrix();
                break;
            case EQUIPPED:
            case EQUIPPED_FIRST_PERSON:
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(0.5f, 0, 0.5f);
                    GL11.glRotatef(90, 0, 1, 0);
                    GL11.glTranslatef(-0.5f, 0, -0.5f);
                    modelDisenchanter.render();
                }
                GL11.glPopMatrix();
                break;
            case INVENTORY:
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(0.5f, 0, 0.5f);
                    GL11.glRotatef(90, 0, 1, 0);
                    GL11.glTranslatef(-0.5f, 0, -0.5f);
                    
                    GL11.glTranslatef(0, -0.1f, 0);
                    
                    modelDisenchanter.render();
                }
                GL11.glPopMatrix();
                break;
            default:
                modelDisenchanter.render();
                break;
        }
        
    }
    
}
