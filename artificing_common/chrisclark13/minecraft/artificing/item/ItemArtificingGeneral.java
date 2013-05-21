package chrisclark13.minecraft.artificing.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import chrisclark13.minecraft.artificing.Artificing;
import chrisclark13.minecraft.artificing.lib.Reference;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;

public class ItemArtificingGeneral extends Item {
    public ItemArtificingGeneral(int id) {
        super(id - Reference.SHIFTED_ID_RANGE_CORRECTION);
        this.setMaxDamage(0);
        this.setNoRepair();
        //this.setCreativeTab(Artificing.tabArtificing);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1)));
    }
    
    public Icon registerIconName(IconRegister iconRegister, String name) {
        return iconRegister.registerIcon(Reference.MOD_ID + ":" + name);
    }
}