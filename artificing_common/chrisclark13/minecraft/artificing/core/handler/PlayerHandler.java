package chrisclark13.minecraft.artificing.core.handler;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;

import chrisclark13.minecraft.artificing.core.APlayerStats;
import chrisclark13.minecraft.artificing.item.ModItems;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.IPlayerTracker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraftforge.common.FakePlayer;


public class PlayerHandler implements IPlayerTracker {

    public ConcurrentHashMap<String, APlayerStats> playerStats = new ConcurrentHashMap<String, APlayerStats>();

    @Override
    public void onPlayerLogin(EntityPlayer entityplayer) {

        APlayerStats stats = getPlayerStats(entityplayer.username);
        stats.player = new WeakReference<EntityPlayer>(entityplayer);

        NBTTagCompound tags = entityplayer.getEntityData();
        if (!tags.hasKey("Artificing")) {
            tags.setCompoundTag("Artificing", new NBTTagCompound());
        }

        stats.book = tags.getCompoundTag("Artificing").getBoolean("startingbook");
        if (!stats.book) {
            tags.getCompoundTag("Artificing").setBoolean("startingbook", true);
            ItemStack book = new ItemStack(ModItems.startingBook);
            if (!entityplayer.inventory.addItemStackToInventory(new ItemStack(ModItems.startingBook))) {
                spawnItemAtPlayer(entityplayer, new ItemStack(ModItems.startingBook));
                //System.out.println("Attempt to give the book");
            }
        }
        playerStats.put(entityplayer.username, stats);
    }

    @Override
    public void onPlayerLogout(EntityPlayer entityplayer) {

        if (entityplayer != null) {
            getPlayerStats(entityplayer.username);
            playerStats.remove(entityplayer.username);
        }
    }

    @Override
    public void onPlayerChangedDimension(EntityPlayer player) {

        // NULL
    }

    @Override
    public void onPlayerRespawn(EntityPlayer entityplayer) {

        APlayerStats stats = getPlayerStats(entityplayer.username);
        NBTTagCompound tTag = new NBTTagCompound();
        tTag.setBoolean("startingbook", stats.book);

    }

    public APlayerStats getPlayerStats(String username) {

        APlayerStats stats = playerStats.get(username);
        if (stats == null) {
            stats = new APlayerStats();
            playerStats.put(username, stats);
        }
        return stats;
    }

    public static void spawnItemAtPlayer(EntityPlayer player, ItemStack stack) {

        EntityItem entityitem = new EntityItem(player.worldObj, player.posX + 0.5D,
                player.posY + 0.5D, player.posZ + 0.5D, stack);
        player.worldObj.spawnEntityInWorld(entityitem);
        if (!(player instanceof FakePlayer))
            entityitem.onCollideWithPlayer(player);
    }

}
