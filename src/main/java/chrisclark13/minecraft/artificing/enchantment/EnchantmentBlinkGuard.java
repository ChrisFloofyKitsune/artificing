package chrisclark13.minecraft.artificing.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public class EnchantmentBlinkGuard extends Enchantment {

	protected EnchantmentBlinkGuard(int id, int weight) {
		super(id, weight, EnumEnchantmentType.weapon);
		this.setName("blinkGuard");
	}

	@Override
	public int getMinEnchantability(int level) {
		return 10 + 15 * (level - 1);
	}
	
	@Override
	public int getMaxEnchantability(int level) {
		return getMinEnchantability(level) + 15;
	}
	
	@Override
	public int getMaxLevel() {
		return 5;
	}
	
	@Override
	public boolean canApply(ItemStack par1ItemStack) {
		return par1ItemStack.getItemUseAction() == EnumAction.block;
	}
	
}
