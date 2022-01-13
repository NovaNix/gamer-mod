package com.novanix.gamermod;

import java.util.HashMap;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
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
	
	private static final HashMap<Block, String> blockNames = new HashMap<Block, String>();
	
	public static final Block OAK_TILES = register("oak_tiles", new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	
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
