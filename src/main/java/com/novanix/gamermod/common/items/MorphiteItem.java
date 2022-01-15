package com.novanix.gamermod.common.items;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MorphiteItem extends Item
{

	public MorphiteItem(Properties pProperties) {
		super(pProperties);
		// TODO Auto-generated constructor stub
	}
	
	public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity)
    {
        return true;
    }

	
	
}
