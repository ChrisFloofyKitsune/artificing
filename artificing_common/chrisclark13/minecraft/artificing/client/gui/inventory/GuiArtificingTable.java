package chrisclark13.minecraft.artificing.client.gui.inventory;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.client.gui.GuiContent;
import chrisclark13.minecraft.artificing.client.gui.GuiTab;
import chrisclark13.minecraft.artificing.client.gui.GuiTabList;
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
    private GuiContent content;
    
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
//        buttonList.add(new GuiTab(-1, guiLeft, guiTop, "TESTY", TabSide.TOP));
//        buttonList.add(new GuiTab(-1, guiLeft + 29, guiTop, "TESTY", TabSide.TOP,
//                TabDrawType.MIDDLE));
//        buttonList.add(new GuiTab(-1, guiLeft + 29 * 2, guiTop, "TESTY", TabSide.TOP,
//                TabDrawType.END));
//        
//        buttonList.add(new GuiTab(-1, guiLeft + xSize, guiTop, "TESTY", TabSide.RIGHT));
//        buttonList.add(new GuiTab(-1, guiLeft + xSize, guiTop + 29, "TESTY", TabSide.RIGHT,
//                TabDrawType.MIDDLE));
//        buttonList.add(new GuiTab(-1, guiLeft + xSize, guiTop + 29 * 2, "TESTY", TabSide.RIGHT,
//                TabDrawType.END));
//        
//        buttonList.add(new GuiTab(-1, guiLeft, guiTop + ySize, "TESTY", TabSide.BOTTOM));
//        buttonList.add(new GuiTab(-1, guiLeft + 29, guiTop + ySize, "TESTY", TabSide.BOTTOM,
//                TabDrawType.MIDDLE));
//        buttonList.add(new GuiTab(-1, guiLeft + 29 * 2, guiTop + ySize, "TESTY", TabSide.BOTTOM,
//                TabDrawType.END));
//        
//        buttonList.add(new GuiTab(-1, guiLeft, guiTop, "TESTY", TabSide.LEFT));
//        buttonList.add(new GuiTab(-1, guiLeft, guiTop + 29, "TESTY", TabSide.LEFT,
//                TabDrawType.MIDDLE));
//        buttonList.add(new GuiTab(-1, guiLeft, guiTop + 29 * 2, "TESTY", TabSide.LEFT,
//                TabDrawType.END));
//        
//        int colors[] = { 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00 };
//        GuiTabList tabLists[] = { new GuiTabList(), new GuiTabList(), new GuiTabList(),
//                new GuiTabList() };
//        for (int i = 0; i < buttonList.size(); i++) {
//            ((GuiTab) buttonList.get(i)).setColor(colors[i / 3]);
//            tabLists[i / 3].addTab((GuiTab) buttonList.get(i));
//        }
        
        content = new GuiContent(guiLeft + xSize, guiTop, 8, ySize);
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
        
        for (Object o : buttonList) {
            GuiButton button = (GuiButton) o;
            button.func_82251_b(mouseX - guiLeft, mouseY - guiTop);
        }
        
        GL11.glEnable(GL11.GL_LIGHTING);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        
        for (Object o : buttonList) {
            if (o instanceof GuiTab) {
                ((GuiTab) o).drawBackground(mc, mouseX, mouseY);
            }
        }
        
        content.drawBackgroundTexture(mc);
        
        // draw your Gui here, only thing you need to change is the path
        // int texture = mc.renderEngine.getTexture("/gui/trap.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(Textures.ARTIFICING_TABLE);
        // this.mc.renderEngine.bindTexture(texture);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
    }
}
