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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class LivingEventHandler {

	private static Random rand = new Random();

	@ForgeSubscribe
	public void onLivingHurtEvent(LivingHurtEvent event) {
		if (event.entityLiving instanceof EntityPlayerMP
				&& !event.entityLiving.worldObj.isRemote) {
			EntityPlayerMP player = (EntityPlayerMP) event.entityLiving;

			int blinkGuardLevel = EnchantmentHelper.getEnchantmentLevel(
					ModEnchantments.blinkGuard.effectId, player.getItemInUse());
			if (player.isBlocking() && blinkGuardLevel > 0) {
				this.handleBlinkGuard(player, event, blinkGuardLevel);
			}
		}
	}

	private void handleBlinkGuard(EntityPlayerMP player, LivingHurtEvent event,
			int blinkGuardLevel) {
		if (event.source.isProjectile()) {
			for (int i = 0; i < 64; i++) {
				if (teleportPlayerToAvoidProjectile(player)) {
					break;
				}
			}
			
			if (rand.nextDouble() <= blinkGuardLevel * 0.15D) {
				player.getItemInUse().damageItem(blinkGuardLevel, player);
				event.setCanceled(true);
			} else {
				int damage = Math.max(1, MathHelper.floor_float(blinkGuardLevel/2f));
				player.getItemInUse().damageItem(damage, player);
			}
			
		} else if (event.source.getSourceOfDamage() instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) event.source
					.getSourceOfDamage();
			
			for (int i = 0; i < 64; i++) {
				if (teleportPlayerAroundEntity(player, entity)) {
					break;
				}
			}
			
			if (rand.nextDouble() <= blinkGuardLevel * 0.10D) {
				player.getItemInUse().damageItem(blinkGuardLevel, player);
				event.setCanceled(true);
			} else {
				int damage = Math.max(1, MathHelper.floor_float(blinkGuardLevel/2f));
				player.getItemInUse().damageItem(damage, player);
			}
		}
	}

	private boolean teleportPlayerToAvoidProjectile(EntityPlayerMP player) {
		Vec3 vec = player.worldObj.getWorldVec3Pool().getVecFromPool(1, 0, 0);
		vec.xCoord *= MathHelper.getRandomDoubleInRange(rand, 2, 6);
		vec.rotateAroundY((float) MathHelper.getRandomDoubleInRange(rand, 0, 2 * Math.PI));
		
		double prevX = player.posX;
		double prevY = player.posY;
		double prevZ = player.posZ;
		
		double x = player.posX + vec.xCoord;
		double y = player.posY;
		double z = player.posZ + vec.zCoord;
		
		if (safeTeleportPlayerTo(player, x, y, z, 2)) {
			player.setPositionAndUpdate(player.posX, player.posY, player.posZ);
			player.worldObj.playSoundEffect(prevX, prevY, prevZ,
					"mob.endermen.portal", 1.0F, 1.0F);
			player.playSound("mob.endermen.portal", 1.0F, 1.0F);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Teleport a player around the entity that attacked them.
	 */
	private boolean teleportPlayerAroundEntity(EntityPlayerMP player,
			Entity entity) {
		Vec3 vectorToPlayer = player.worldObj.getWorldVec3Pool()
				.getVecFromPool(
						player.posX - entity.posX,
						(player.posY + player.height / 2.0)
								- (entity.posY + entity.height / 2.0),
						player.posZ - entity.posZ);
		double prevX = player.posX;
		double prevY = player.posY;
		double prevZ = player.posZ;
		double minDistance = entity.width + 1.5;
		double maxDistance = minDistance + 3.5;
		double distance = MathHelper.getRandomDoubleInRange(rand, minDistance,
				maxDistance);

		vectorToPlayer = vectorToPlayer.normalize();
		vectorToPlayer.xCoord *= distance;
		vectorToPlayer.yCoord *= -distance;
		vectorToPlayer.zCoord *= distance;

		vectorToPlayer
				.rotateAroundY((float) (Math.PI / 2.0 * rand.nextDouble() * Math.PI));

		double x = entity.posX + vectorToPlayer.xCoord;
		double y = entity.posY + vectorToPlayer.yCoord;
		double z = entity.posZ + vectorToPlayer.zCoord;
		int autocorrectAttempts = Math.max(2, MathHelper
				.ceiling_double_int(Math.abs(vectorToPlayer.yCoord) * 2D));

		if (safeTeleportPlayerTo(player, x, y, z, autocorrectAttempts)) {
			Vec3 vectorToEntity = player.worldObj.getWorldVec3Pool()
					.getVecFromPool(
							entity.posX - player.posX,
							(entity.posY + entity.height / 2.0)
									- (player.posY + player.height / 2.0),
							entity.posZ - player.posZ);
			float yaw = (float) (Math.atan2(vectorToEntity.zCoord,
					vectorToEntity.xCoord) * 180.0 / Math.PI) - 90.0f;

			player.playerNetServerHandler.setPlayerLocation(player.posX,
					player.posY, player.posZ, yaw, player.rotationPitch);
			player.worldObj.playSoundEffect(prevX, prevY, prevZ,
					"mob.endermen.portal", 1.0F, 1.0F);
			player.playSound("mob.endermen.portal", 1.0F, 1.0F);
			return true;
		} else {
			return false;
		}		
	}

	/**
	 * Teleport the player
	 */
	private boolean safeTeleportPlayerTo(EntityPlayerMP player, double x,
			double y, double z, int maxAutocorrectSteps) {

		World world = player.worldObj;
		double prevX = player.posX;
		double prevY = player.posY;
		double prevZ = player.posZ;
		player.posX = x;
		player.posY = y;
		player.posZ = z;
		boolean successfulTeleport = false;
		int blockX = MathHelper.floor_double(player.posX);
		int blockY = MathHelper.floor_double(player.posY);
		int blockZ = MathHelper.floor_double(player.posZ);
		int blockId = player.worldObj.getBlockId(blockX, blockY, blockZ);
		int autocorrectSteps = 0;
		boolean autocorrectUpwards = !(blockId == 0 || Block.blocksList[blockId]
				.isAirBlock(world, blockX, blockY, blockZ));

		// Check if block coords are in the world
		if (player.worldObj.blockExists(blockX, blockY, blockZ)) {
			boolean blockFound = false;

			if (autocorrectUpwards) {
				blockId = player.worldObj
						.getBlockId(blockX, blockY - 1, blockZ);
				boolean lastBlockSolid = (blockId != 0 && Block.blocksList[blockId]
						.isBlockSolidOnSide(world, blockX, blockY - 1, blockZ,
								ForgeDirection.UP));

				while (!blockFound && blockY < 256
						&& autocorrectSteps < maxAutocorrectSteps) {
					blockId = player.worldObj
							.getBlockId(blockX, blockY, blockZ);

					if (lastBlockSolid
							&& (blockId == 0 || Block.blocksList[blockId]
									.isAirBlock(world, blockX, blockY, blockZ))) {
						blockFound = true;
					} else {
						lastBlockSolid = (blockId != 0 && Block.blocksList[blockId]
								.isBlockSolidOnSide(world, blockX, blockY,
										blockZ, ForgeDirection.UP));
						player.posY++;
						blockY++;
						autocorrectSteps++;
					}
				}
			} else {
				while (!blockFound && blockY > 0
						&& autocorrectSteps < maxAutocorrectSteps) {
					blockId = player.worldObj.getBlockId(blockX, blockY - 1,
							blockZ);

					if (blockId != 0
							&& Block.blocksList[blockId].isBlockSolidOnSide(
									world, blockX, blockY - 1, blockZ,
									ForgeDirection.UP)) {
						blockFound = true;
					} else {
						player.posY--;
						blockY--;
						autocorrectSteps++;
					}
				}
			}

			if (blockFound) {
				player.setPosition(player.posX, player.posY, player.posZ);

				if (player.worldObj.getCollidingBoundingBoxes(player,
						player.boundingBox).isEmpty()
						&& !player.worldObj.isAnyLiquid(player.boundingBox)) {
					successfulTeleport = true;
				}
			}
		}

		if (!successfulTeleport) {
			player.setPosition(prevX, prevY, prevZ);
			return false;
		} else {
			short short1 = 128;

			for (blockId = 0; blockId < short1; ++blockId) {
				double d6 = (double) blockId / ((double) short1 - 1.0D);
				float f = (rand.nextFloat() - 0.5F) * 0.2F;
				float f1 = (rand.nextFloat() - 0.5F) * 0.2F;
				float f2 = (rand.nextFloat() - 0.5F) * 0.2F;
				double d7 = prevX + (player.posX - prevX) * d6
						+ (rand.nextDouble() - 0.5D) * (double) player.width
						* 2.0D;
				double d8 = prevY + (player.posY - prevY) * d6
						+ rand.nextDouble() * (double) player.height;
				double d9 = prevZ + (player.posZ - prevZ) * d6
						+ (rand.nextDouble() - 0.5D) * (double) player.width
						* 2.0D;
				player.worldObj.spawnParticle("portal", d7, d8, d9, (double) f,
						(double) f1, (double) f2);
			}

			player.worldObj.playSoundEffect(prevX, prevY, prevZ,
					"mob.endermen.portal", 1.0F, 1.0F);
			player.playSound("mob.endermen.portal", 1.0F, 1.0F);
			return true;
		}
	}
}
