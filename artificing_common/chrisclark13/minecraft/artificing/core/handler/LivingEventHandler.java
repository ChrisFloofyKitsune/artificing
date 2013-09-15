package chrisclark13.minecraft.artificing.core.handler;

import java.util.Random;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import chrisclark13.minecraft.artificing.core.helper.LogHelper;
import chrisclark13.minecraft.artificing.enchantment.ModEnchantments;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class LivingEventHandler {

	private static Random rand = new Random();
	
	@ForgeSubscribe
	public void onLivingAttackEvent(LivingAttackEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.entityLiving;
			LogHelper.log(Level.INFO, "Got a living attack event agaisnt: " + player.getDisplayName());
			
			int blinkGuardLevel = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.blinkGuard.effectId, player.getItemInUse());
			if (player.isBlocking() && blinkGuardLevel > 0) {
				if (event.source.getSourceOfDamage() instanceof EntityLivingBase) {
					EntityLivingBase entity = (EntityLivingBase)event.source.getSourceOfDamage();
					if (event.source.isProjectile()) {
						
					} else {
						LogHelper.log(Level.INFO, String.format("Blink Guard Activated @ (%.2f, %.2f, %.2f) on %s", player.posX, player.posY, player.posZ, FMLCommonHandler.instance().getSide()));
						for (int i = 0; i < 64; i++) {
							if (teleportPlayerAroundEntity(player, entity)) {
								break;
							}
						}
						LogHelper.log(Level.INFO, String.format("Teleported to (%.2f, %.2f, %.2f)", player.posX, player.posY, player.posZ));
					}
				}
				
			}
		}
	}
	
	/**
     * Teleport a player around the entity that attacked them.
     */
    protected boolean teleportPlayerAroundEntity(EntityPlayer player, Entity entity)
    {
        Vec3 vec3 = player.worldObj.getWorldVec3Pool().getVecFromPool(player.posX - entity.posX, player.posY + (double)player.getEyeHeight() - entity.posY + (double)entity.getEyeHeight(), player.posZ - entity.posZ);
        vec3 = vec3.normalize();
        double prevX = player.posX;
        double prevY = player.posY;
        double prevZ = player.posZ;
        double minDistance = 3.0D;
        double maxDistance = 8.0D;
        double distance = MathHelper.getRandomDoubleInRange(rand, minDistance, maxDistance);
//        double x = entity.posX - vec3.xCoord * distance;
//        double y = entity.posY - vec3.yCoord * distance;
//        double z = entity.posZ - vec3.yCoord * distance;
        if (!player.worldObj.isRemote)
        	player.setPositionAndUpdate(player.posX, player.posY + 20, player.posZ);
        player.worldObj.playSoundEffect(prevX, prevY, prevZ, "mob.endermen.portal", 1.0F, 1.0F);
        player.playSound("mob.endermen.portal", 1.0F, 1.0F);
        return true;
        //return this.teleportPlayerTo(player, x, y, z);
    }

    /**
     * Teleport the player
     */
    protected boolean teleportPlayerTo(EntityPlayer player, double x, double y, double z)
    {
        EnderTeleportEvent event = new EnderTeleportEvent(player, x, y, z, 0);
        if (MinecraftForge.EVENT_BUS.post(event)){
            return false;
        }

        double prevX = player.posX;
        double prevY = player.posY;
        double prevZ = player.posZ;
        player.posX = event.targetX;
        player.posY = event.targetY;
        player.posZ = event.targetZ;
        boolean successfulTeleport = false;
        int blockX = MathHelper.floor_double(player.posX);
        int blockY = MathHelper.floor_double(player.posY);
        int blockZ = MathHelper.floor_double(player.posZ);
        int blockId;

        if (player.worldObj.blockExists(blockX, blockY, blockZ))
        {
            boolean blockFound = false;

            while (!blockFound && blockY > 0)
            {
                blockId = player.worldObj.getBlockId(blockX, blockY - 1, blockZ);

                if (blockId != 0 && Block.blocksList[blockId].blockMaterial.blocksMovement())
                {
                    blockFound = true;
                }
                else
                {
                    --player.posY;
                    --blockY;
                }
            }

            if (blockFound)
            {
                player.setPosition(player.posX, player.posY, player.posZ);

                if (player.worldObj.getCollidingBoundingBoxes(player, player.boundingBox).isEmpty() && !player.worldObj.isAnyLiquid(player.boundingBox))
                {
                    successfulTeleport = true;
                }
            }
        }

        if (!successfulTeleport)
        {
            player.setPosition(prevX, prevY, prevZ);
            return false;
        }
        else
        {
            short short1 = 128;

            for (blockId = 0; blockId < short1; ++blockId)
            {
                double d6 = (double)blockId / ((double)short1 - 1.0D);
                float f = (rand.nextFloat() - 0.5F) * 0.2F;
                float f1 = (rand.nextFloat() - 0.5F) * 0.2F;
                float f2 = (rand.nextFloat() - 0.5F) * 0.2F;
                double d7 = prevX + (player.posX - prevX) * d6 + (rand.nextDouble() - 0.5D) * (double)player.width * 2.0D;
                double d8 = prevY + (player.posY - prevY) * d6 + rand.nextDouble() * (double)player.height;
                double d9 = prevZ + (player.posZ - prevZ) * d6 + (rand.nextDouble() - 0.5D) * (double)player.width * 2.0D;
                player.worldObj.spawnParticle("portal", d7, d8, d9, (double)f, (double)f1, (double)f2);
            }

            player.worldObj.playSoundEffect(prevX, prevY, prevZ, "mob.endermen.portal", 1.0F, 1.0F);
            player.playSound("mob.endermen.portal", 1.0F, 1.0F);
            return true;
        }
    }
}
