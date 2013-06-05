package chrisclark13.minecraft.artificing.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.client.gui.GuiContent;
import chrisclark13.minecraft.artificing.client.gui.GuiTab;
import chrisclark13.minecraft.artificing.client.gui.GuiTabSidebarContent;
import chrisclark13.minecraft.artificing.client.gui.TabDrawType;
import chrisclark13.minecraft.artificing.client.gui.TabSide;
import chrisclark13.minecraft.artificing.inventory.ContainerArtificingTable;
import chrisclark13.minecraft.artificing.lib.Textures;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.multislotitems.client.gui.GuiMultiSlotItem;


public class GuiArtificingTable extends GuiMultiSlotItem {

    private ContainerArtificingTable containerArtificing;
    private TileArtificingTable      artificingTable;

    private GuiContentErrorMessages  errorContent;
    private GuiTabSidebarContent     errorTab;
    
    private GuiContentArtificingGrid gridContent;

    public GuiArtificingTable(InventoryPlayer inventoryPlayer, TileArtificingTable artificingTable) {

        super(new ContainerArtificingTable(inventoryPlayer, artificingTable));
        ySize = 236;

        containerArtificing = (ContainerArtificingTable) this.inventorySlots;
        this.artificingTable = artificingTable;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {

        super.initGui();

        errorContent = new GuiContentErrorMessages(guiLeft + xSize, guiTop + 10, 90, ySize - 20);
        errorTab = new GuiTabSidebarContent(-1, guiLeft + xSize, guiTop + 10, "TEST",
                TabSide.RIGHT, TabDrawType.FRONT, errorContent);
        buttonList.add(errorTab);
        
        gridContent = new GuiContentArtificingGrid();
        gridContent.setGridSizeAndPosition(6, 6, guiLeft, guiTop);
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

        GL11.glPushMatrix();
        {
            GL11.glTranslatef(-guiLeft, -guiTop, 0);
            for (Object o : buttonList) {
                if (o instanceof GuiTabSidebarContent) {
                    ((GuiTabSidebarContent) o).drawContentForeground(mc, mouseX, mouseY);
                }
            }

            for (Object o : buttonList) {
                ((GuiButton) o).func_82251_b(mouseX, mouseY);
            }
            
            gridContent.drawForegroundContent(mc, mouseX, mouseY);
        }
        GL11.glPopMatrix();

        GL11.glEnable(GL11.GL_LIGHTING);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        int errorCount = artificingTable.manager.getErrorMessages().size();
        
        if (errorCount > 0) {
            errorContent.backgroundColor = 0xAA0000;
            errorContent.textColor = 0xFF0000;
            errorTab.color = 0xAA0000;
            errorTab.iconColor = 0xFF0000;
            errorTab.setTabIcon(Textures.GUI_ICONS, 16, 0);
            errorTab.displayString = "§c" + errorCount + " error" + ((errorCount > 1) ? "s" : "")
                    + "!";
        } else {
            errorContent.backgroundColor = 0xFFFFFF;
            errorContent.textColor = 0x404040;
            errorTab.color = 0xFFFFFF;
            errorTab.iconColor = 0xFFFFFF;
            errorTab.setTabIcon(Textures.GUI_ICONS, 0, 0);
            errorTab.displayString = "No errors";
        }

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
        
        gridContent.drawContent(mc, mouseX, mouseY);

    }

    private class GuiContentErrorMessages extends GuiContent {

        public int textColor;

        public GuiContentErrorMessages(int x, int y, int width, int height) {

            super(x, y, width, height);
        }

        @Override
        protected void drawForeground(Minecraft minecraft, int mouseX, int mouseY) {

            int errorCount = artificingTable.manager.getErrorMessages().size();
            if (errorCount > 0) {
                int line = 0;
                for (String string : artificingTable.manager.getErrorMessages()) {
                    if (line > 18) {
                        fontRenderer.drawString(errorCount + " more...", 3, 5 + line
                                * fontRenderer.FONT_HEIGHT, textColor);
                        break;
                    }
                    for (Object o : fontRenderer
                            .listFormattedStringToWidth(string, this.width - 10)) {
                        fontRenderer.drawString((String) o, 3, 5 + line * fontRenderer.FONT_HEIGHT,
                                textColor);
                        line++;
                    }
                    line++;
                    errorCount--;
                }
            } else {
                fontRenderer.drawString("No errors.", 3, 5, textColor);
            }
        }

    }
}
