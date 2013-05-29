package chrisclark13.minecraft.artificing.client.gui;

import java.util.ArrayList;

public class GuiTabList {
    private ArrayList<GuiTab> tabs;
    public GuiTab activeTab;
    public GuiTab prevActiveTab;
    
    public GuiTabList() {
        tabs = new ArrayList<>();
        activeTab = null;
    }
    
    public void addTab(GuiTab tab) {
        tabs.add(tab);
        tab.parentList = this;
    }
    
    public void removeTab(GuiTab tab) {
        tabs.remove(tab);
        tab.parentList = null;
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<GuiTab> getTabs() {
        return (ArrayList<GuiTab>) tabs.clone();
    }
    
    public void setActiveTab(GuiTab tab) {
        
        prevActiveTab = activeTab;
        activeTab = tab;
        
        if (prevActiveTab != null && prevActiveTab != tab) {
            prevActiveTab.setActive(false);
        }
        

        for (GuiTab guiTab : tabs) {
            guiTab.setActive(guiTab == tab);
        }
    }
    
    public void shiftTabPositions(int xShift, int yShift) {
        for (GuiTab guiTab : tabs) {
            guiTab.xPosition += xShift;
            guiTab.yPosition += yShift;
        }
    }
}
