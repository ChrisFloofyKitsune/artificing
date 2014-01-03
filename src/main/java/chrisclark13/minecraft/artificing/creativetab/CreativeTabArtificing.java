package chrisclark13.minecraft.artificing.creativetab;

import java.util.List;

import chrisclark13.minecraft.artificing.core.helper.RuneHelper;
import chrisclark13.minecraft.artificing.lib.BlockIds;
import chrisclark13.minecraft.artificing.lib.ItemIds;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;

public class CreativeTabArtificing extends CreativeTabs {
    public CreativeTabArtificing(int par1, String par2Str) {
        super(par1, par2Str);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getTabIconItemIndex() {
        return BlockIds.artificingTableID;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllReleventItems(List displayList) {
        super.displayAllReleventItems(displayList);
        displayList.addAll(RuneHelper.getAllRunes());
    }
}
