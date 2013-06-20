package chrisclark13.minecraft.artificing.network;

import chrisclark13.minecraft.artificing.network.packet.PacketArtificing;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler implements IPacketHandler {
    
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        PacketArtificing packetA = PacketArtificing.createPacket(packet.data);
        EntityPlayer entityPlayer = (EntityPlayer) player;
        
        packetA.execute(manager, entityPlayer, (entityPlayer.worldObj.isRemote) ? Side.CLIENT : Side.SERVER);
    }
    
}
