package com.novanix.gamermod;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.novanix.gamermod.client.models.GamerGlassesModel;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LayerRegister 
{

	public static void register()
	{
		GamerMod.LOGGER.info("Registering Layers");
		FMLJavaModLoadingContext.get().getModEventBus().register(LayerRegister.class);
	}

	@SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) 
	{
        event.registerLayerDefinition(GamerGlassesModel.LAYER_LOCATION, GamerGlassesModel::createBodyLayer);
    }
	
}
