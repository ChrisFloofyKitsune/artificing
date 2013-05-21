package chrisclark13.minecraft.artificing.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import chrisclark13.minecraft.artificing.Artificing;
import chrisclark13.minecraft.artificing.block.*;
import chrisclark13.minecraft.artificing.lib.Reference;
import chrisclark13.minecraft.artificing.lib.Strings;
import chrisclark13.minecraft.artificing.tileentity.TileArtificingTable;
import chrisclark13.minecraft.artificing.tileentity.TileResearchTable;


public class BlockResearchTable extends BlockArtificingGeneral {

    public Icon          iconTop, iconBottom;
    public static String FACES[] = { "Side" , "Top" , "Bottom" };

    public BlockResearchTable(int id) {

        super(id, Material.rock);
        this.setUnlocalizedName(Strings.RESEARCH_TABLE_NAME);
        this.setCreativeTab(Artificing.tabArtificing);
    }
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
                    EntityPlayer player, int idk, float what, float these, float are) {
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            
            //Null check and Sneaking check
            if (tileEntity == null || player.isSneaking()) {
                    return false;
            }
            
            //Open GUI
            player.openGui(Artificing.instance, 2, world, x, y, z);
            return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int metadata) {

        return side == ForgeDirection.UP.ordinal() ? iconTop : side == ForgeDirection.DOWN
                .ordinal() ? iconBottom : this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {

        this.blockIcon = iconRegister.registerIcon(Reference.MOD_ID + ":"
                + Strings.RESEARCH_TABLE_NAME + FACES[0]);
        iconTop = iconRegister.registerIcon(Reference.MOD_ID + ":"
                + Strings.RESEARCH_TABLE_NAME + FACES[1]);
        iconBottom = iconRegister.registerIcon(Reference.MOD_ID + ":"
                + Strings.RESEARCH_TABLE_NAME + FACES[2]);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {

        dropItems(world, x, y, z);
        super.breakBlock(world, x, y, z, par5, par6);
    }

    private void dropItems(World world, int x, int y, int z) {

        Random rand = new Random();

        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (!(tileEntity instanceof IInventory)) {
            return;
        }

        IInventory inventory = (IInventory) tileEntity;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack item = inventory.getStackInSlot(i);

            if (item != null && item.stackSize > 0) {
                float rx = rand.nextFloat() * 0.8F + 0.1F;
                float ry = rand.nextFloat() * 0.8F + 0.1F;
                float rz = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz,
                        new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));

                if (item.hasTagCompound()) {
                    entityItem.getEntityItem().setTagCompound(
                            (NBTTagCompound) item.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                item.stackSize = 0;
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world) {

        return new TileResearchTable();
    }

}
