package chrisclark13.minecraft.artificing.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import chrisclark13.minecraft.artificing.enchantment.EnchantmentDamageArtificing.DamageType;

public class ModEnchantments {
	
	public static EnchantmentDamageArtificing slayer;
	public static EnchantmentDamageArtificing hunter;
	public static EnchantmentDamageArtificing disruption;
	public static EnchantmentDamageArtificing purge;
	
	public static EnchantmentBlinkGuard blinkGuard;
	
	public static void init() {
		slayer = new EnchantmentDamageArtificing(100, 2, DamageType.HUMAN);
		slayer.addEffectiveEntity(EntityPlayer.class);
		slayer.addEffectiveEntity(EntityVillager.class);
		slayer.addEffectiveEntity(EntityWitch.class);
		Enchantment.addToBookList(slayer);
		
		hunter = new EnchantmentDamageArtificing(101, 5, DamageType.ANIMAL);
		hunter.addEffectiveEntity(EntityAnimal.class);
		hunter.addEffectiveEntity(EntityWaterMob.class);
		hunter.addEffectiveEntity(EntityBat.class);
		hunter.addEffectiveEntity(EntityDragon.class);
		Enchantment.addToBookList(hunter);
		
		disruption = new EnchantmentDamageArtificing(102, 5, DamageType.MAGICAL);
		disruption.addEffectiveEntity(EntityBlaze.class);
		disruption.addEffectiveEntity(EntityEnderman.class);
		Enchantment.addToBookList(disruption);
		
		purge = new EnchantmentDamageArtificing(103, 5, DamageType.ABERRATION);
		purge.addEffectiveEntity(EntityCreeper.class);
		purge.addEffectiveEntity(EntitySlime.class);
		purge.addEffectiveEntity(EntityGhast.class);
		Enchantment.addToBookList(purge);
		
		blinkGuard = new EnchantmentBlinkGuard(104, 1);
		Enchantment.addToBookList(blinkGuard);
	}
	
	private ModEnchantments() {
	}
}
