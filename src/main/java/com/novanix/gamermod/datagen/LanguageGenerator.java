package com.novanix.gamermod.datagen;

import com.novanix.gamermod.GamerMod;
import com.novanix.gamermod.ItemRegister;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageGenerator extends LanguageProvider
{

	public LanguageGenerator(DataGenerator gen, String locale) 
	{
		super(gen, GamerMod.MOD_ID, locale);
	}

	@Override
	protected void addTranslations() 
	{
		add(ItemRegister.OAK_TILES, "Oak Tiles");
		
		add(ItemRegister.OAK_TILE_STAIRS, "Oak Tile Stairs");
		add(ItemRegister.OAK_TILE_SLAB, "Oak Tile Slab");
		
		add(ItemRegister.FROG_STATUE, "Frog Statue");
		
		add(ItemRegister.METAL_CAKE, "Metal Cake");
		
		//blockItem(ItemRegister.DON_GEROS_MASK, "gamermod:block/don_geros_mask");
		
		add(ItemRegister.MORPHITE, "Morphite");
	}

	
	
}
