package com.novanix.gamermod.common.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GamerGlassesItem extends Item
{

	public GamerGlassesItem() 
	{
		super(new Properties().durability(919).fireResistant().tab(CreativeModeTab.TAB_COMBAT));
	}
	
	@Override
	public EquipmentSlot getEquipmentSlot(ItemStack stack)
	{
		return EquipmentSlot.HEAD;
	}

}
