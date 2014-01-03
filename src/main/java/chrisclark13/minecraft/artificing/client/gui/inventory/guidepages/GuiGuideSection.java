package chrisclark13.minecraft.artificing.client.gui.inventory.guidepages;

import java.util.ArrayList;

public class GuiGuideSection {
    
    public String name;
    public boolean hasBookmark;
    public int bookmarkColor;
    public GuiGuideSectionTab bookmark;
    private ArrayList<GuiGuidePage> pages;
    public int startPageIndex;
    
    public GuiGuideSection(String sectionName, boolean hasBookmark, int bookmarkColor) {
        this.name = sectionName;
        this.hasBookmark = hasBookmark;
        this.bookmarkColor = bookmarkColor;
        this.bookmark = null;
        
        pages = new ArrayList<>();
        startPageIndex = 0;
    }
    
    public void addPage(GuiGuidePage page) {
        pages.add(page);
        
        page.section = this;
    }
    
    public ArrayList<GuiGuidePage> getPages() {
        return pages;
    }
}
