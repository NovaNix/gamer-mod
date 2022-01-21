package com.novanix.gamermod.datagen;

import com.novanix.gamermod.BlockRegister;
import com.novanix.gamermod.GamerMod;
import com.novanix.gamermod.ItemRegister;

import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemGenerator extends ItemModelProvider
{

	public ItemGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) 
	{
		super(generator, GamerMod.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() 
	{
		blockItem(ItemRegister.OAK_TILES, "gamermod:block/oak_tiles");
		
		blockItem(ItemRegister.OAK_TILE_STAIRS, "gamermod:block/oak_tile_stairs");
		blockItem(ItemRegister.OAK_TILE_SLAB, "gamermod:block/oak_tile_slab");
		
		blockItem(ItemRegister.FROG_STATUE, "gamermod:block/frog_statue");
		
		generatedItem(ItemRegister.METAL_CAKE, mcLoc("gamermod:item/metal_cake"));
		
		//blockItem(ItemRegister.DON_GEROS_MASK, "gamermod:block/don_geros_mask");
		
		generatedItem(ItemRegister.MORPHITE, mcLoc("gamermod:item/morphite"));
		
		blockItem(ItemRegister.ROPE, "gamermod:block/rope");
		
		generatedItem(ItemRegister.SCREEN, mcLoc("gamermod:block/screen_bottom"));
		generatedItem(ItemRegister.SMOOTH_SCREEN, mcLoc("gamermod:block/screen_single"));
	}

	
	protected void generatedItem(Item item, ResourceLocation texture)
	{
		getBuilder(item.getRegistryName().getPath())
        .parent(new UncheckedModelFile("item/generated"))
        .texture("layer0", texture);
	}
	
	protected void blockItem(Item item, String blockPath)
	{
		getBuilder(item.getRegistryName().getPath())
        .parent(getExistingFile(mcLoc(blockPath)));
//        .texture("all", mcLoc("block/dirt"))
//        .texture("top", mcLoc("block/stone"))
//        .element()
//            .cube("#all")
//            .face(Direction.UP)
//                .texture("#top")
//                .tintindex(0)
//                .end()
//            .end();
	}
}
