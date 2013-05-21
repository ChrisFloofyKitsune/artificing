package chrisclark13.minecraft.artificing.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileArtificingGeneral extends TileEntity {

    private ForgeDirection orientation;
    private String customName;

    public TileArtificingGeneral() {
        orientation = ForgeDirection.SOUTH;
        customName = "";
    }

    public ForgeDirection getOrientation() {

        return orientation;
    }

    public void setOrientation(ForgeDirection orientation) {

        this.orientation = orientation;
    }

    public void setOrientation(int orientation) {

        this.orientation = ForgeDirection.getOrientation(orientation);
    }

    public boolean hasCustomName() {

        return customName != null && customName.length() > 0;
    }

    public String getCustomName() {

        return customName;
    }

    public void setCustomName(String customName) {

        this.customName = customName;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {

        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {

        super.readFromNBT(nbtTagCompound);

        if (nbtTagCompound.hasKey("Direction")) {
            orientation = ForgeDirection.getOrientation(nbtTagCompound.getByte("Direction"));
        }

        if (nbtTagCompound.hasKey("CustomName")) {
            customName = nbtTagCompound.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {

        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setByte("Direction", (byte) orientation.ordinal());

        if (this.hasCustomName()) {
            nbtTagCompound.setString("CustomName", customName);
        }
    }
    
    
}