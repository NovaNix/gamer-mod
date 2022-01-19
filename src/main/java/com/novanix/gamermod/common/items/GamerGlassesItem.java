package com.novanix.gamermod.common.items;

import java.util.function.Consumer;

import com.novanix.gamermod.client.models.GamerGlassesModel;
import com.novanix.gamermod.client.models.ModelArmor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
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
			@SuppressWarnings("unchecked")
			@Override
			public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A _default)
		    {
				// Borrowed from botania since the whole entity layer system makes no sense to me
				var entityModels = Minecraft.getInstance().getEntityModels();
				var root = entityModels.bakeLayer(GamerGlassesModel.LAYER_LOCATION);
				return (A) new ModelArmor(root, armorSlot);
		    }

	    
		});
	}

}
