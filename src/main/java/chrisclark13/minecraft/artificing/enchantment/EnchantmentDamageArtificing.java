package chrisclark13.minecraft.artificing.enchantment;

import java.util.HashSet;

import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.EntityLivingBase;

public class EnchantmentDamageArtificing extends EnchantmentDamage {
	
	public enum DamageType {
		HUMAN(7, 9, 20, 2.5f), ANIMAL(5, 8, 20, 2.5f), MAGICAL(7, 9, 20, 2.5f), ABERRATION(5, 8, 20, 2.5f);
		
		private final int baseEnchantability;
		private final int levelEnchantability;
		private final int thresholdEnchantability;
		private final float damageBonusModifier;
		
		public HashSet<Class<? extends EntityLivingBase>> effectiveEntities = new HashSet<>();
		public HashSet<Class<? extends EntityLivingBase>> excludedEntities = new HashSet<>();
		
		private DamageType(int baseEnchantability, int levelEnchantability, int thresholdEnchantability, float damageBonusModifier) {
			this.baseEnchantability = baseEnchantability;
			this.levelEnchantability = levelEnchantability;
			this.thresholdEnchantability = thresholdEnchantability;
			this.damageBonusModifier = damageBonusModifier;
		}
		
		public int getMinEnchantability(int level) {
			return baseEnchantability+ (level - 1) + levelEnchantability * level;
		}
		
		public int getMaxEnchantability(int level) {
			return getMinEnchantability(level) + thresholdEnchantability;
		}
		
		public float getBonusDamage(int level, EntityLivingBase entity) {
			if (effectiveEntities.contains(entity.getClass()) && !excludedEntities.contains(entity.getClass())) {
				return level * damageBonusModifier;
			} else {
				return 0f;
			}
		}
	}
	
	public final DamageType damageType;
	
	public EnchantmentDamageArtificing(int id, int weight, DamageType damageType) {
		super(id, weight, 0);
		this.damageType = damageType;
	}
	
	 /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int level)
    {
        return this.damageType.getMinEnchantability(level);
    }

    /**
     * Returns the maximum value of enchantability needed on the enchantment level passed.
     */
    public int getMaxEnchantability(int level)
    {
        return this.damageType.getMaxEnchantability(level);
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 5;
    }

    /**
     * Calculates the (magic) damage done by the enchantment on a living entity based on level and entity passed.
     */
    public float calcModifierLiving(int level, EntityLivingBase entity)
    {
        return damageType.getBonusDamage(level, entity);
    }

    /**
     * Return the name of key in translation table of this enchantment.
     */
    public String getName()
    {
        return "enchantment.damage." + damageType.toString().toLowerCase();
    }	
    
    public void addEffectiveEntity(Class<? extends EntityLivingBase> entityClass) {
    	this.damageType.effectiveEntities.add(entityClass);
    }
    
    public void addExcludedEntity(Class<? extends EntityLivingBase> entityClass) {
    	this.damageType.excludedEntities.add(entityClass);
    }
}
