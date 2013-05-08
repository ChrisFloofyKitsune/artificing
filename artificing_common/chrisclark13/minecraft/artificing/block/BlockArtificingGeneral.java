package chrisclark13.minecraft.artificing.block;

import chrisclark13.minecraft.artificing.Artificing;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockArtificingGeneral extends Block {
    public BlockArtificingGeneral(int id, Material material) {
        super(id, material);
        this.setCreativeTab(Artificing.tabArtificing);
    }
}
