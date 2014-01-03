package chrisclark13.minecraft.artificing.tileentity;

public class TileDisenchanter extends TileArtificingGeneral {
    public int tickCount = 0;
    
    @Override
    public void updateEntity() {
        super.updateEntity();
        
        tickCount++;
    }
}
