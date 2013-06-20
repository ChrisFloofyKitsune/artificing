package chrisclark13.minecraft.artificing.network.packet;


public enum PacketType {
    AT_CHARGE_UPDATE(PacketATChargeUpdate.class);
    
    public final Class<? extends PacketArtificing> clazz;
    
    private PacketType(Class<? extends PacketArtificing> clazz) {
        this.clazz = clazz;
    }
}
