package chrisclark13.minecraft.artificing.network.packet;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;

import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class PacketATChargeUpdate extends PacketArtificing {
    
    private int x;
    private int y;
    private int z;
    private int charge;
    
    public PacketATChargeUpdate() {
        super(PacketType.AT_CHARGE_UPDATE, false);
    }
    
    public PacketATChargeUpdate(int x, int y, int z, int charge) {
        super(PacketType.AT_CHARGE_UPDATE, false);
        this.x = x;
        this.y = y;
        this.z = z;
        this.charge = charge;
    }
    
    @Override
    protected void writeData(ByteArrayDataOutput out) throws IOException {
        out.writeInt(x);
        out.writeShort(y);
        out.writeInt(z);
        out.writeInt(charge);
    }
    
    @Override
    protected void readData(ByteArrayDataInput in) throws IOException {
        x = in.readInt();
        y = in.readShort();
        z = in.readInt();
        charge = in.readInt();
    }
    
    @Override
    public void execute(INetworkManager manager, EntityPlayer player, Side side) {
        if (side == Side.CLIENT) {
            TileArtificingTable table = (TileArtificingTable) player.worldObj.getBlockTileEntity(x,
                    y, z);
            
            if (table != null) {
                table.setCharge(charge);
                table.updateGrid();
            }
        }
        
    }
    
}
