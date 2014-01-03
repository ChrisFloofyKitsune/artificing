package chrisclark13.minecraft.artificing.item.trait;

public class Trait {
    enum Type {
        /**
         * Trait for all armor enchantments
         */
        ARMOR,
        
        /**
         * Trait for all weapon enchantments
         */
        WEAPONRY,
        
        /**
         * Trait for all tool enchantments
         */
        TOOL,
        
        /**
         * Trait for all bow enchantments
         */
        BOW,
        
        /**
         * Trait for enchantments dealing with air, movement, and projectiles.
         * 
         * Enchantments: Leaping, Haste, Feather Fall, Projectile Protection
         */
        AIR,
        
        /**
         * Trait for enchantments dealing with the End, and teleporting.
         */
        ENDER,
        
        /**
         * Trait for enchantments dealing with explosions.
         */
        EXPLOSIVE,
        
        /**
         * Trait for enchantments dealing with fire
         */
        FIRE,
        
        /**
         * Trait for enchantments dealing with mining and the earth
         */
        MINER,
        
        /**
         * Trait for all "general" enchantments.
         */
        NEUTRAL,
        
        /**
         * Trait for enchantments dealing with animals and players.
         */
        REAPER,
        
        /**
         * Trait reserved for those really epic enchantments.
         */
        VALOR,
        
        /**
         * Trait for enchantments dealing with evil mobs and the like
         */
        VILE,
        
        /**
         * Trait for generic-y enchantments dealing with combat and the like,
         * but still not 100% generic
         */
        WARRIOR, 
        
        /**
         * Trait for enchantments dealing with water and ice.
         */
        WATER
    }
    
    public final Type type;
    public final int level;
    
    public Trait(Type type, int level) {
        this.type = type;
        this.level = level;
    }
    
    @Override
    public int hashCode() {
        return (type.name() + level).hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Trait) {
            Trait o = (Trait) obj;
            return o.type.equals(this.type) && o.level == this.level;
        }
        return false;
    }
}
