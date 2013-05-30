package chrisclark13.minecraft.artificing.client.gui.inventory.guidepages;

import net.minecraft.client.Minecraft;
import chrisclark13.minecraft.artificing.client.gui.GuiContent;

public class GuiGuidePage extends GuiContent {

    public static final int PAGE_WIDTH = 110;
    public static final int PAGE_HEIGHT = 165;

    public GuiGuidePage(int x, int y) {

        super(x, y, PAGE_WIDTH, PAGE_HEIGHT);
        this.drawOwnBackground = false;

    }

    @Override
    public void drawForeground(Minecraft minecraft, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawStringWithShadow("HAYO", 10, 10, 0x0);
    }

}