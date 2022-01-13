package com.novanix.gamermod.common.armor;

import java.util.function.Consumer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;

public class GamerGlassesItem extends Item
{

	public GamerGlassesItem() 
	{
		super(new Properties().durability(919).fireResistant().tab(CreativeModeTab.TAB_COMBAT));
		
		//renderProperties = new IItemRenderProperties() {};
	}
	
	@Override
	public EquipmentSlot getEquipmentSlot(ItemStack stack)
	{
		return EquipmentSlot.HEAD;
	}
	
	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) 
	{
		consumer.accept(new IItemRenderProperties() 
		{
			@Override
			public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A _default)
		    {
				HumanoidModel Humanoid = new HumanoidModel(entityLiving.) 
		        return null;
		    }

	    
		});
	}

}
