package com.novanix.gamermod.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators 
{
	
	// Pulled from https://wiki.mcjty.eu/modding/index.php?title=Tut14_Ep7

	@SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new BlockGenerator(generator, event.getExistingFileHelper()));
        generator.addProvider(new ItemGenerator(generator, event.getExistingFileHelper()));
        generator.addProvider(new LanguageGenerator(generator,"en_us"));
        //generator.addProvider(new LootTables(generator));
    }
	
}
