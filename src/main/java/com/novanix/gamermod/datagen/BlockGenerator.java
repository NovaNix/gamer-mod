package com.novanix.gamermod.datagen;

import com.novanix.gamermod.BlockRegister;
import com.novanix.gamermod.GamerMod;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
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
		simpleBlock(BlockRegister.OAK_TILES);
		stairsBlock((StairBlock) BlockRegister.OAK_TILE_STAIRS, "oak_tile", mcLoc("gamermod:block/oak_tiles"));
		slabBlock((SlabBlock) BlockRegister.OAK_TILE_SLAB, BlockRegister.OAK_TILES.getRegistryName(), mcLoc("gamermod:block/oak_tiles"));
		
	}

}
