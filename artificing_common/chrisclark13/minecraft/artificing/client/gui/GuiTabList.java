package chrisclark13.minecraft.artificing.client.gui;

import java.util.ArrayList;

public class GuiTabList {
    private ArrayList<GuiTab> tabs;
    
    public GuiTabList() {
        tabs = new ArrayList<>();
    }
    
    public void addTab(GuiTab tab) {
        tabs.add(tab);
        tab.parentList = this;
    }
    
    public void removeTab(GuiTab tab) {
        tabs.remove(tab);
        tab.parentList = null;
    }
    
    public void setActiveTab(GuiTab tab) {
        for (GuiTab guiTab : tabs) {
            guiTab.setActive(guiTab == tab);
        }
    }
}
