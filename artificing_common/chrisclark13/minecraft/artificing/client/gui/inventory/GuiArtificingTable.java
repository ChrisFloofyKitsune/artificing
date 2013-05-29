package chrisclark13.minecraft.artificing.client.gui.inventory;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.client.gui.GuiContent;
import chrisclark13.minecraft.artificing.client.gui.GuiTab;
import chrisclark13.minecraft.artificing.client.gui.GuiTabList;
import chrisclark13.minecraft.artificing.client.gui.GuiTabSidebarContent;
import chrisclark13.minecraft.artificing.client.gui.TabDrawType;
import chrisclark13.minecraft.artificing.client.gui.TabSide;
import chrisclark13.minecraft.artificing.inventory.ContainerArtificingTable;
import chrisclark13.minecraft.artificing.item.crafting.RuneItemGroupComparer;
import chrisclark13.minecraft.artificing.lib.Textures;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.multislotitems.client.gui.GuiMultiSlotItem;
import chrisclark13.minecraft.multislotitems.groups.ItemGroup;
import chrisclark13.minecraft.multislotitems.inventory.MultiSlotItemGridSlot;
import chrisclark13.minecraft.multislotitems.inventory.SlotMultiSlotItem;

public class GuiArtificingTable extends GuiMultiSlotItem {
    
    private ContainerArtificingTable containerArtificing;
    private TileArtificingTable artificingTable;
    
    public GuiArtificingTable(InventoryPlayer inventoryPlayer, TileArtificingTable artificingTable) {
        super(new ContainerArtificingTable(inventoryPlayer, artificingTable));
        ySize = 226;
        
        containerArtificing = (ContainerArtificingTable) this.inventorySlots;
        this.artificingTable = artificingTable;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        super.initGui();
        
        GuiContent content = new GuiContentErrorMessages(guiLeft + xSize - 4, guiTop, 100, ySize);
        GuiTab tab = new GuiTabSidebarContent(-1, guiLeft + xSize, guiTop, "TEST", TabSide.RIGHT,
                TabDrawType.FRONT, content);
        tab.setTabIcon(Textures.GUI_ICONS, 16, 0);
        buttonList.add(tab);
        
        GuiContent content2 = new GuiContentErrorMessages(guiLeft + xSize - 4, guiTop, 80, ySize);
        GuiTab tab2 = new GuiTabSidebarContent(-1, guiLeft + xSize, guiTop + 29, "TEST 2",
                TabSide.RIGHT, TabDrawType.MIDDLE, content2);
        tab2.setTabIcon(new ItemStack(Item.swordDiamond));
        buttonList.add(tab2);
        
        GuiTabList list = new GuiTabList();
        list.addTab(tab);
        list.addTab(tab2);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        GL11.glDisable(GL11.GL_LIGHTING);
        
        // draw text and stuff here
        // the parameters for drawString are: string, x, y, color
        fontRenderer.drawString("Artificing Table", 8, 6, 0x404040);
        // draws "Inventory" or your regional equivalent
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8,
                ySize - 96 + 2, 0x404040);
        
        int line = 0;
        int errors = artificingTable.manager.getErrorMessages().size();
        for (String string : artificingTable.manager.getErrorMessages()) {
            if (line > 20) {
                fontRenderer.drawStringWithShadow(errors + " more..", xSize, line
                        * fontRenderer.FONT_HEIGHT, 0xFF0000);
                break;
            }
            for (Object o : fontRenderer.listFormattedStringToWidth(string, 100)) {
                GL11.glColor4f(1, 1, 1, 1);
                fontRenderer.drawStringWithShadow((String) o, xSize, line
                        * fontRenderer.FONT_HEIGHT, 0xFF0000);
                line++;
            }
            line++;
            errors--;
        }
        
        GL11.glPushMatrix();
        {
            GL11.glTranslatef(-guiLeft, -guiTop, 0);
            for (Object o : buttonList) {
                GuiButton button = (GuiButton) o;
                button.func_82251_b(mouseX, mouseY);
                if (button instanceof GuiTabSidebarContent) {
                    ((GuiTabSidebarContent) button).drawContentForeground(mc, mouseX, mouseY);
                }
            }
        }
        GL11.glPopMatrix();
        
        GL11.glEnable(GL11.GL_LIGHTING);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        
        for (Object o : buttonList) {
            if (o instanceof GuiTab) {
                ((GuiTab) o).drawBackground(mc, mouseX, mouseY);
            }
        }
        
        for (Object o : buttonList) {
            if (o instanceof GuiTabSidebarContent) {
                ((GuiTabSidebarContent) o).drawContentBackground(mc, mouseX, mouseY);
            }
        }
        
        // draw your Gui here, only thing you need to change is the path
        // int texture = mc.renderEngine.getTexture("/gui/trap.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(Textures.ARTIFICING_TABLE);
        // this.mc.renderEngine.bindTexture(texture);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
    }
    
    private class GuiContentErrorMessages extends GuiContent {
        
        public GuiContentErrorMessages(int x, int y, int width, int height) {
            super(x, y, width, height);
        }
        
        @Override
        protected void draw(Minecraft minecraft, int mouseX, int mouseY) {
            // if (mouseX > 6) {
            // this.width = mouseX;
            // }
            // if (mouseY > 6) {
            // this.height = mouseY;
            // }
        }
        
        @Override
        protected void drawForeground(Minecraft minecraft, int mouseX, int mouseY) {
            mc.fontRenderer.drawString("X: " + mouseX + "Y: " + mouseY, 5, 5, 0x404040);
        }
        
    }
}
