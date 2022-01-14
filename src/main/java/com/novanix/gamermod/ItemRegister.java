package com.novanix.gamermod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.novanix.gamermod.common.armor.GamerGlassesItem;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegister 
{
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GamerMod.MOD_ID);

	private static final Logger LOGGER = LogManager.getLogger();
	
	public static final Item OAK_TILES = registerBlock(BlockRegister.OAK_TILES, CreativeModeTab.TAB_BUILDING_BLOCKS);
	
	public static final Item METAL_CAKE = registerBlock(BlockRegister.METAL_CAKE, CreativeModeTab.TAB_DECORATIONS);
	public static final Item GAMER_GLASSES = register("gamer_glasses", new GamerGlassesItem());
	
	public static void register()
	{
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	public static Item register(String name, Item item)
	{
		ITEMS.register(name, () -> item);
		return item;
	}

	public static Item registerBlock(Block block, CreativeModeTab tab)
	{
		BlockItem item = new BlockItem(block, new Item.Properties().tab(tab));
		//Item blockitem = block.asItem().
		register(BlockRegister.getBlockName(block), item);
		
		return item;
	}
}
