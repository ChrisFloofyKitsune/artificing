package chrisclark13.minecraft.artificing.block;

import chrisclark13.minecraft.artificing.Artificing;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingGeneral;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public abstract class BlockArtificingGeneral extends BlockContainer {
    public BlockArtificingGeneral(int id, Material material) {
        super(id, material);
        this.setCreativeTab(Artificing.tabArtificing);
    }
    
    /**
     * Sets the direction of the block when placed
     */
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack) {
        
        int direction = 0;
        int facing = MathHelper.floor_double(entityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        
        if (facing == 0) {
            direction = ForgeDirection.NORTH.ordinal();
        }
        else if (facing == 1) {
            direction = ForgeDirection.EAST.ordinal();
        }
        else if (facing == 2) {
            direction = ForgeDirection.SOUTH.ordinal();
        }
        else if (facing == 3) {
            direction = ForgeDirection.WEST.ordinal();
        }

        world.setBlockMetadataWithNotify(x, y, z, direction, 3);
        
        ((TileArtificingGeneral) world.getBlockTileEntity(x, y, z)).setOrientation(direction);
    }
    
    protected String getUnwrappedUnlocalizedName() {	
    	return this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(":") + 1);
    }
}
