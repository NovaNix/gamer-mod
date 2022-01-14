package com.novanix.gamermod;

import java.util.HashMap;

import com.novanix.gamermod.common.blocks.MetalCakeBlock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegister 
{

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, GamerMod.MOD_ID);
	
	// Contains the names used to register each block
	// This exists because the name is required to create BlockItems
	// Theres probably a better way to handle this, but Forge's documentation doesnt provide an example of creating block items
	private static final HashMap<Block, String> blockNames = new HashMap<Block, String>();
	
	public static final Block OAK_TILES = register("oak_tiles", new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
	
	public static final Block OAK_TILE_STAIRS = register("oak_tile_stairs", new StairBlock(() -> OAK_TILES.defaultBlockState(), BlockBehaviour.Properties.copy(OAK_TILES)));
	
	public static final Block OAK_TILE_SLAB = register("oak_tile_slab", new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
	
	public static final Block METAL_CAKE = register("metal_cake", new MetalCakeBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 1200.0F).sound(SoundType.ANVIL)));
	
	
	public static void register()
	{
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	public static Block register(String name, Block block)
	{
		blockNames.put(block, name);
		BLOCKS.register(name, () -> block);
		return block;
	}
	
	public static String getBlockName(Block b)
	{
		return blockNames.get(b);
	}
}
