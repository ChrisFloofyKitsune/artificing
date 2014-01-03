package chrisclark13.minecraft.artificing.client.gui.inventory.guidepages;

import chrisclark13.minecraft.artificing.client.gui.GuiTab;
import chrisclark13.minecraft.artificing.client.gui.TabDrawType;
import chrisclark13.minecraft.artificing.client.gui.TabSide;
import chrisclark13.minecraft.artificing.lib.Textures;

public class GuiGuideSectionTab extends GuiTab {

    GuiGuideSection section;
    
    public GuiGuideSectionTab(int id, int xPosition, int yPosition, GuiGuideSection section,
            TabSide side, TabDrawType type) {
        super(id, xPosition, yPosition, section.name, side, type);

        this.section = section;
        this.color = section.bookmarkColor;
        
        this.width = 14;
        this.height = 14;
        this.overhang = 1;
        
        this.texture = Textures.GUIDE_BOOK_TAB_PARTS;
        this.textureWidth = 120;
        this.textureHeight = 120;
    }
    
    @Override
    public void mouseReleased(int par1, int par2) {
        if (pressed) {
            if (this.parentList != null) {
                if (!this.active) {
                    parentList.setActiveTab(this);
                }
            } else if (!this.active) {
                this.setActive(true);
            }
        }
        
        pressed = false;
    }
}
