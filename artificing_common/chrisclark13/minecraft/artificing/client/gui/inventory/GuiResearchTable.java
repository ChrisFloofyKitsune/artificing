package chrisclark13.minecraft.artificing.client.gui.inventory;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.inventory.ContainerResearchTable;
import chrisclark13.minecraft.artificing.inventory.SlotResearchRing;
import chrisclark13.minecraft.artificing.lib.Reference;
import chrisclark13.minecraft.artificing.lib.Textures;
import chrisclark13.minecraft.artificing.tileentity.TileResearchTable;
import chrisclark13.minecraft.multislotitems.client.gui.GuiMultiSlotItem;

public class GuiResearchTable extends GuiMultiSlotItem {
    
    public ContainerResearchTable conResearchTable;
    public TileResearchTable researchTable;
    private float animTimer;
    private static final float ANIM_PERIOD = 20 * Reference.TICKS_IN_SECOND;
    
    private static final int TEX_RING_SLOT_X = 217;
    private static final int TEX_RING_SLOT_Y = 140;
    
    public float[] innerSlotLocations;
    public float[] outerSlotLocations;
    
    public GuiResearchTable(InventoryPlayer inventoryPlayer, TileResearchTable researchTable) {
        
        super(new ContainerResearchTable(inventoryPlayer, researchTable));
        xSize = 199;
        ySize = 226;
        
        this.conResearchTable = (ContainerResearchTable) inventorySlots;
        this.researchTable = researchTable;
        
        animTimer = 0;
        
        innerSlotLocations = new float[conResearchTable.innerSlots.length * 2];
        outerSlotLocations = new float[conResearchTable.outerSlots.length * 2];
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
        
        // draw text and stuff here
        // the parameters for drawString are: string, x, y, color
        fontRenderer.drawString("Research", 8, 6, 0x404040);
        fontRenderer.drawString("Table", 8, 15, 0x404040);
        // draws "Inventory" or your regional equivalent
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8,
                ySize - 96 + 2, 0x404040);
        
    }
    
    @Override
    protected void drawSlotInventory(Slot slot) {
        if (!(slot instanceof SlotResearchRing)) {
            super.drawSlotInventory(slot);
            return;
        }
        
        this.zLevel = 100.0F;
        itemRenderer.zLevel = 100.0F;
        
        GL11.glPushMatrix();
        int index;
        
        if (slot.getSlotIndex() >= researchTable.INNER_SLOT_START
                && slot.getSlotIndex() <= researchTable.INNER_SLOT_END) {
            index = slot.getSlotIndex() - researchTable.INNER_SLOT_START;
            GL11.glTranslatef(innerSlotLocations[2 * index], innerSlotLocations[2 * index + 1], 0);
        }
        
        if (slot.getSlotIndex() >= researchTable.OUTER_SLOT_START
                && slot.getSlotIndex() <= researchTable.OUTER_SLOT_END) {
            index = slot.getSlotIndex() - researchTable.OUTER_SLOT_START;
            GL11.glTranslatef(outerSlotLocations[2 * index], outerSlotLocations[2 * index + 1], 0);
        }
        
        if (slot.getHasStack()) {
            ItemStack itemStack = slot.getStack();
            itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.renderEngine, itemStack, 2, 2);
            itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, itemStack, 2, 2);
        }
        
        if (slot == this.getTheSlot()) {
            
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            
            mc.renderEngine.bindTexture("%blur%" + Textures.RESEARCH_TABLE);
            float[] colors = unpackColorsFromInt(0x80FFFFFF);
            GL11.glColor4f(colors[1], colors[2], colors[3], colors[0]);
            this.drawTexturedModalRect(0, 0, TEX_RING_SLOT_X, TEX_RING_SLOT_Y + 20, 20, 20);
            
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
        }
        GL11.glPopMatrix();
        
        this.zLevel = 0F;
        itemRenderer.zLevel = 0F;
    }
    
    @Override
    protected boolean isMouseOverSlot(Slot slot, int mouseX, int mouseY) {
        if (slot instanceof SlotResearchRing) {
            if (slot.getSlotIndex() >= researchTable.INNER_SLOT_START
                    && slot.getSlotIndex() <= researchTable.INNER_SLOT_END) {
                int index = slot.getSlotIndex() - researchTable.INNER_SLOT_START;
                return isPointInCircle(innerSlotLocations[2 * index] + 10,
                        innerSlotLocations[2 * index + 1] + 10, 10, mouseX, mouseY);
            } else if (slot.getSlotIndex() >= researchTable.OUTER_SLOT_START
                    && slot.getSlotIndex() <= researchTable.OUTER_SLOT_END) {
                int index = slot.getSlotIndex() - researchTable.OUTER_SLOT_START;
                return isPointInCircle(outerSlotLocations[2 * index] + 10,
                        outerSlotLocations[2 * index + 1] + 10, 10, mouseX, mouseY);
            } else {
                return false;
            }
        } else {
            return super.isMouseOverSlot(slot, mouseX, mouseY);
        }
    }
    
    protected boolean isPointInCircle(float centerX, float centerY, float radius, int pointX,
            int pointY) {
        int k1 = this.guiLeft;
        int l1 = this.guiTop;
        pointX -= k1;
        pointY -= l1;
        return MathHelper.sqrt_float((centerX - pointX) * (centerX - pointX) + (centerY - pointY)
                * (centerY - pointY)) <= radius;
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int par2, int par3) {
        
        animTimer = ((researchTable.tickCount + partialTicks) % ANIM_PERIOD) / ANIM_PERIOD;
        
        // draw your Gui here, only thing you need to change is the path
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(Textures.RESEARCH_TABLE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        Tessellator tessellator = Tessellator.instance;
        
        // Render rings
        final float RADIUS = 255F / 2F;
        final float SCALE = 0.5f;
        final int CENTER_X = 88;
        final int CENTER_Y = 66;
        float rotation = animTimer * 360;
        
        mc.renderEngine.bindTexture(Textures.RINGS);
        GL11.glPushMatrix();
        {
            GL11.glTranslatef(x + CENTER_X, y + CENTER_Y, 0);
            GL11.glRotatef(-rotation, 0, 0, 1);
            GL11.glScalef(SCALE, SCALE, 1);
            GL11.glTranslatef(-RADIUS, -RADIUS, 0);
            
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(0, 0, 0, 0, 0);
            tessellator.addVertexWithUV(0, 255, 0, 0, 1);
            tessellator.addVertexWithUV(255, 255, 0, 0.5, 1);
            tessellator.addVertexWithUV(255, 0, 0, 0.5, 0);
            tessellator.draw();
        }
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        {
            GL11.glTranslatef(x + CENTER_X, y + CENTER_Y, 0);
            GL11.glRotatef(rotation, 0, 0, 1);
            GL11.glScalef(SCALE, SCALE, 1);
            GL11.glTranslatef(-RADIUS, -RADIUS, 0);
            
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(0, 0, 0, 0.5, 0);
            tessellator.addVertexWithUV(0, 255, 0, 0.5, 1);
            tessellator.addVertexWithUV(255, 255, 0, 1, 1);
            tessellator.addVertexWithUV(255, 0, 0, 1, 0);
            tessellator.draw();
        }
        GL11.glPopMatrix();
        
        mc.renderEngine.bindTexture(Textures.RESEARCH_TABLE);
        GL11.glPushMatrix();
        {
            GL11.glTranslatef(x + CENTER_X, y + CENTER_Y, 0);
            
            final int RING_SLOT_SIZE = 20;
            final float INNER_SLOT_RADIUS = 39.5f;
            final float INNER_ROT_OFFSET = -30f;
            final float OUTER_SLOT_RADIUS = 57.5f;
            final float OUTER_ROT_OFFSET = 90f;
            
            for (int i = 0; i < 3; i++) {
                float rotationStep = (float) (Math
                        .toRadians((i * 120 + rotation + INNER_ROT_OFFSET) % 360));
                float slotX = (MathHelper.cos(rotationStep) * INNER_SLOT_RADIUS) - RING_SLOT_SIZE
                        / 2f;
                float slotY = (MathHelper.sin(rotationStep) * INNER_SLOT_RADIUS) - RING_SLOT_SIZE
                        / 2f;
                
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(slotX, slotY, 0);
                    this.drawTexturedModalRect(0, 0, TEX_RING_SLOT_X, TEX_RING_SLOT_Y,
                            RING_SLOT_SIZE, RING_SLOT_SIZE);
                }
                GL11.glPopMatrix();
                
                innerSlotLocations[2 * i] = slotX + CENTER_X;
                innerSlotLocations[(2 * i) + 1] = slotY + CENTER_Y;
            }
            
            for (int i = 0; i < 3; i++) {
                float rotationStep = (float) (Math
                        .toRadians((i * 120 + rotation + OUTER_ROT_OFFSET) % 360));
                float slotX = (MathHelper.cos(-rotationStep) * OUTER_SLOT_RADIUS) - RING_SLOT_SIZE
                        / 2f;
                float slotY = (MathHelper.sin(-rotationStep) * OUTER_SLOT_RADIUS) - RING_SLOT_SIZE
                        / 2f;
                
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(slotX, slotY, 0);
                    this.drawTexturedModalRect(0, 0, TEX_RING_SLOT_X, TEX_RING_SLOT_Y,
                            RING_SLOT_SIZE, RING_SLOT_SIZE);
                }
                GL11.glPopMatrix();
                
                outerSlotLocations[2 * i] = slotX + CENTER_X;
                outerSlotLocations[(2 * i) + 1] = slotY + CENTER_Y;
            }
        }
        GL11.glPopMatrix();
    }
    
}
