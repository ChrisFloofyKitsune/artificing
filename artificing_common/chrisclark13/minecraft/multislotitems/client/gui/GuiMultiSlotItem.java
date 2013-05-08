package chrisclark13.minecraft.multislotitems.client.gui;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.multislotitems.MultiSlotItemHelper;
import chrisclark13.minecraft.multislotitems.MultiSlotItemSlotSignature;
import chrisclark13.minecraft.multislotitems.inventory.SlotMultiSlotItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class GuiMultiSlotItem extends GuiContainer {

	public GuiMultiSlotItem(Container par1Container) {
		super(par1Container);
	}

	@Override
	protected void drawSlotInventory(Slot slot) {

		if (!(slot instanceof SlotMultiSlotItem) || ((SlotMultiSlotItem) slot).getLinkedSlot().getStack() == null) {
			super.drawSlotInventory(slot);
			return;
		}
	
		SlotMultiSlotItem slotMSI = (SlotMultiSlotItem) slot;
		ItemStack itemStack = slotMSI.getLinkedSlot().getStack();
		String imagePath = MultiSlotItemHelper.getImagePath(itemStack);
		int x = slot.xDisplayPosition;
        int y = slot.yDisplayPosition;

		this.zLevel = 100.0F;
        itemRenderer.zLevel = 100.0F;
		
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        
		if (imagePath != null) {
			// If the parent slot exists and there is a imagePath, then the
			// parentSlot will handle drawing it
			if (slotMSI.getGridSlot().getParentSlot() == null) {
				
			    GL11.glDisable(GL11.GL_DEPTH_TEST);
			    GL11.glDisable(GL11.GL_LIGHTING);
			    MultiSlotItemSlotSignature sig = MultiSlotItemHelper.getSignature(itemStack);
				//this.drawTexturedModalRect(x + sig.getRelativeLeft() * 16, y + sig.getRelativeTop() * 16, 0, 0, sig.getWidth() * 16, sig.getHeight());
				this.mc.renderEngine.bindTexture(imagePath);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.drawImage(x  + sig.getRelativeLeft() * 16, y  + sig.getRelativeTop() * 16, 0, 0, sig.getWidth() * 16, sig.getHeight() * 16);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				
				//Copied from RenderItem so I could do the rendering scaled to the image.
				//I have no idea what fourth of this stuff does.
				if (itemStack.hasEffect())
	            {
	                GL11.glDepthFunc(GL11.GL_GREATER);
	                GL11.glDisable(GL11.GL_LIGHTING);
	                GL11.glDepthMask(false);
	                mc.renderEngine.bindTexture("%blur%/misc/glint.png");
	                this.zLevel -= 50.0F;
	                GL11.glEnable(GL11.GL_BLEND);
	                GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_DST_COLOR);
	                //May want to change this around!
	                GL11.glColor4f(0.5F, 0.25F, 0.8F, 1.0F);
	                this.renderGlint(x + sig.getRelativeLeft() * 16 - 2, y + sig.getRelativeTop() * 16 - 2, sig.getWidth() * 16 + 4, sig.getHeight() * 16 + 4);
	                GL11.glDisable(GL11.GL_BLEND);
	                GL11.glDepthMask(true);
	                this.zLevel += 50.0F;
	                GL11.glEnable(GL11.GL_LIGHTING);
	                GL11.glDepthFunc(GL11.GL_LEQUAL);
	            }
			}
		} else {
			// If the imagePath is null you get a whole bunch of the same image
			// all over the place! XD
			itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.renderEngine, itemStack, x, y);
		}
		
		//Render the overlay on the parent slot
		if (slotMSI.getGridSlot().getParentSlot() == null) {
			itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, itemStack, x, y);
		}
		
		itemRenderer.zLevel = 0.0F;
        this.zLevel = 0.0F;

	}
	
	/**
	 * Copied from RenderItem because it was private before.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void renderGlint(int x, int y, int width, int height)
    {
        for (int j1 = 0; j1 < 2; ++j1)
        {
            if (j1 == 0)
            {
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
            }

            if (j1 == 1)
            {
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
            }

            float f = 0.00390625F;
            float f1 = 0.00390625F;
            float f2 = (float)(Minecraft.getSystemTime() % (long)(3000 + j1 * 1873)) / (3000.0F + (float)(j1 * 1873)) * 256.0F;
            float f3 = 0.0F;
            Tessellator tessellator = Tessellator.instance;
            float f4 = 4.0F;

            if (j1 == 1)
            {
                f4 = -1.0F;
            }

            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + height), (double)this.zLevel, (double)((f2 + (float)height * f4) * f), (double)((f3 + (float)height) * f1));
            tessellator.addVertexWithUV((double)(x + width), (double)(y + height), (double)this.zLevel, (double)((f2 + (float)width + (float)height * f4) * f), (double)((f3 + (float)height) * f1));
            tessellator.addVertexWithUV((double)(x + width), (double)(y + 0), (double)this.zLevel, (double)((f2 + (float)width) * f), (double)((f3 + 0.0F) * f1));
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)this.zLevel, (double)((f2 + 0.0F) * f), (double)((f3 + 0.0F) * f1));
            tessellator.draw();
        }
    }
	
	public void drawImage(int x, int y, int u, int v, int width, int height)
    {
	    float f = 1F / (float)width;
        float f1 = 1F / (float)height;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + height), (double)this.zLevel, (double)((float)(u + 0) * f), (double)((float)(v + height) * f1));
        tessellator.addVertexWithUV((double)(x + width), (double)(y + height), (double)this.zLevel, (double)((float)(u + width) * f), (double)((float)(v + height) * f1));
        tessellator.addVertexWithUV((double)(x + width), (double)(y + 0), (double)this.zLevel, (double)((float)(u + width) * f), (double)((float)(v + 0) * f1));
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)this.zLevel, (double)((float)(u + 0) * f), (double)((float)(v + 0) * f1));
        tessellator.draw();
    }
}
