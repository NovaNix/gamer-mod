package com.novanix.gamermod.datagen;

import com.novanix.gamermod.BlockRegister;
import com.novanix.gamermod.GamerMod;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockGenerator extends BlockStateProvider
{

	public BlockGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) 
	{
		super(gen, GamerMod.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() 
	{
		VariantBlockStateBuilder woodenTilesBuilder = getVariantBuilder(BlockRegister.OAK_TILES);
		
	}

}
