package com.novanix.gamermod;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.novanix.gamermod.client.models.GamerGlassesModel;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LayerRegister 
{

	private static final DeferredRegister<> LAYERS = DeferredRegister.create(ForgeRegistries.BLOCKS, GamerMod.MOD_ID);
	
	
	public static ModelLayerLocation register(String name, Supplier<LayerDefinition> definition)
	{
		ModelLayerLocation location = new ModelLayerLocation(new ResourceLocation(GamerMod.MOD_ID, name), name);
	}
	
	public static void register()
	{
		
	}
	
	public static void registerLayerDefinition( event)
	{
		ImmutableMap.Builder<ModelLayerLocation, LayerDefinition> builder = ImmutableMap.builder();

		event.registerLayerDefinition(, GamerGlassesModel::createBodyLayer);
        
	}
	
}
