package chrisclark13.minecraft.artificing.inventory;

import chrisclark13.minecraft.multislotitems.inventory.IDisableableSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotResearchRing extends Slot implements IDisableableSlot{

    public SlotResearchRing(IInventory par1iInventory, int par2) {
        super(par1iInventory, par2, 0, 0);
        
    }
    
    @Override
    public boolean isDisabled() {
        return false;
    }

    @Override
    public boolean getDrawItemStack() {
        return true;
    }

    @Override
    public boolean getDrawHighlight() {
        return false;
    }

    @Override
    public boolean isClickable() {
        return true;
    }
    
}
