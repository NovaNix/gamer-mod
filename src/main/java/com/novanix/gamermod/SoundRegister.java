package com.novanix.gamermod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundRegister 
{

	private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, GamerMod.MOD_ID);
	
	public static final SoundEvent FROG_RIBBIT = register("frog_ribbit", "gamermod:frog_ribbit");
	
	private static SoundEvent register(String name, String path)
	{
		SoundEvent event = new SoundEvent(new ResourceLocation(path));
		
		SOUNDS.register(name, () -> event);
		
		return event;
	}
	
	public static void register()
	{
		SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
}
