package com.novanix.gamermod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.novanix.gamermod.common.armor.MorphiteMaterial;
import com.novanix.gamermod.common.items.DonGerosMaskItem;
import com.novanix.gamermod.common.items.GamerGlassesItem;
import com.novanix.gamermod.common.items.MorphiteItem;
import com.novanix.gamermod.common.items.NoteBlockTunerItem;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegister 
{
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GamerMod.MOD_ID);

	private static final Logger LOGGER = LogManager.getLogger();
	
	public static final Item OAK_TILES = registerBlock(BlockRegister.OAK_TILES, CreativeModeTab.TAB_BUILDING_BLOCKS);
	
	public static final Item OAK_TILE_STAIRS = registerBlock(BlockRegister.OAK_TILE_STAIRS, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final Item OAK_TILE_SLAB = registerBlock(BlockRegister.OAK_TILE_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	
	public static final Item METAL_CAKE = registerBlock(BlockRegister.METAL_CAKE, CreativeModeTab.TAB_DECORATIONS);
	public static final Item FROG_STATUE = registerBlock(BlockRegister.FROG_STATUE, CreativeModeTab.TAB_REDSTONE);
	
	
	public static final Item DON_GEROS_MASK = register("don_geros_mask", new DonGerosMaskItem(new Properties().tab(CreativeModeTab.TAB_COMBAT)));
	public static final Item GAMER_GLASSES = register("gamer_glasses", new GamerGlassesItem());
	public static final Item MORPHITE = register("morphite", new MorphiteItem(new Properties().tab(CreativeModeTab.TAB_COMBAT)));
	
	public static final Item NOTEBLOCK_TUNER = register("noteblock_tuner", new NoteBlockTunerItem(new Properties().tab(CreativeModeTab.TAB_TOOLS)));
	
	
	
	public static final MorphiteMaterial MORPHITE_MATERIAL = new MorphiteMaterial();
	
	public static void register()
	{
		GamerMod.LOGGER.info("Registering Items");
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		GamerMod.LOGGER.info("Adding ItemRegister to the eventbus");
		FMLJavaModLoadingContext.get().getModEventBus().register(ItemRegister.class);
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
	
	@SubscribeEvent
	public static void registerItemColors(ColorHandlerEvent.Item event)
	{
	    event.getItemColors().register(NoteBlockTunerItem::getTunerTagColor, NOTEBLOCK_TUNER);
	    GamerMod.LOGGER.info("Registered Item Colors");
	}
}
