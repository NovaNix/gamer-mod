package com.novanix.gamermod.common.armor;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class MorphiteMaterial implements ArmorMaterial
{

	@Override
	public int getDurabilityForSlot(EquipmentSlot pSlot) 
	{
		return 240;
	}

	@Override
	public int getDefenseForSlot(EquipmentSlot pSlot) 
	{
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	public int getEnchantmentValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SoundEvent getEquipSound() 
	{
		return SoundEvents.ARMOR_EQUIP_NETHERITE;
	}

	@Override
	public Ingredient getRepairIngredient() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() 
	{
		return "morphite";
	}

	@Override
	public float getToughness() 
	{
		return 2;
	}

	@Override
	public float getKnockbackResistance() 
	{
		return 0;
	}

}
