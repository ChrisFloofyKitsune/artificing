package chrisclark13.minecraft.artificing.client.gui;

import net.minecraft.client.Minecraft;

public class GuiTabSidebarContent extends GuiTab {

    public GuiContent content;
    
    public GuiTabSidebarContent(int id, int xPosition, int yPosition, String hoverString, TabSide side, TabDrawType type, GuiContent content) {
        super(id, xPosition, yPosition, hoverString, side, type);
        this.content = content;
        
        switch (this.side) {
            case TOP:
                this.content.drawBackgroundBottomEdge = false;
                this.content.height += overhang;
                break;
            case RIGHT:
                this.content.drawBackgroundLeftEdge = false;
                this.content.x -= overhang;
                this.content.width += overhang;
                this.content.leftSpacing += overhang;
                break;
            case BOTTOM:
                this.content.drawBackgroundTopEdge = false;
                this.content.y -= overhang;
                this.content.height += overhang;
                this.content.topSpacing += overhang;
                break;
            case LEFT:
                this.content.drawBackgroundRightEdge = false;
                this.content.width += overhang;
                break;
        }
    }
    
    @Override
    public void activate() {
        if (parentList != null) {
            switch (this.side) {
                case TOP:
                    parentList.shiftTabPositions(0, -(content.height - overhang));
                    break;
                case RIGHT:
                    parentList.shiftTabPositions((content.width - overhang), 0);
                    break;
                case LEFT:
                    parentList.shiftTabPositions(-(content.width - overhang), 0);
                case BOTTOM:
                    parentList.shiftTabPositions(0, (content.height - overhang));
                    break;
            }
        } else {
            switch (this.side) {
                case TOP:
                    this.yPosition += -(content.height - overhang);
                    break;
                case RIGHT:
                    this.xPosition += (content.width - overhang);
                    break;
                case LEFT:
                    this.xPosition += -(content.width - overhang);
                    break;
                case BOTTOM:
                    this.yPosition += (content.height - overhang);
                    break;
            }
        }
    }
    
    @Override
    public void deactivate() {
        if (parentList != null) {
            switch (this.side) {
                case TOP:
                    parentList.shiftTabPositions(0,(content.height - overhang));
                    break;
                case RIGHT:
                    parentList.shiftTabPositions(-(content.width - overhang), 0);
                    break;
                case LEFT:
                    parentList.shiftTabPositions((content.width - overhang), 0);
                    break;
                case BOTTOM:
                    parentList.shiftTabPositions(0, -(content.height - overhang));
                    break;
            }
        } else {
            switch (this.side) {
                case TOP:
                    this.yPosition += (content.height - overhang);
                    break;
                case RIGHT:
                    this.xPosition += -(content.width - overhang);
                    break;
                case LEFT:
                    this.xPosition += (content.width - overhang);
                    break;
                case BOTTOM:
                    this.yPosition += -(content.height - overhang);
                    break;
            }
        }
    }
    
    public void drawContentBackground(Minecraft minecraft, int mouseX, int mouseY) {
        if (active && drawButton) {
            content.textureV = (pressed) ? 48 : 0;
            content.drawContent(minecraft, mouseX, mouseY);
        }
    }
    
    public void drawContentForeground(Minecraft minecraft, int mouseX, int mouseY) {
        if (active && drawButton) {
            content.textureV = (pressed) ? 48 : 0;
            content.drawForegroundContent(minecraft, mouseX, mouseY);
        }
    }
}
