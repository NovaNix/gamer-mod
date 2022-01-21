package com.novanix.gamermod.datagen;

import com.novanix.gamermod.BlockRegister;
import com.novanix.gamermod.GamerMod;
import com.novanix.gamermod.common.blocks.ScreenBlock;

import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
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
		
		screenBlock(BlockRegister.SCREEN, 
								mcLoc("gamermod:block/screen_top"), 
								mcLoc("gamermod:block/screen_bottom"), 
								mcLoc("gamermod:block/screen_single"), 
								mcLoc("gamermod:block/screen_side"));
		screenBlock(BlockRegister.SMOOTH_SCREEN, 
								mcLoc("gamermod:block/smooth_screen_top"), 
								mcLoc("gamermod:block/smooth_screen_middle"), 
								mcLoc("gamermod:block/smooth_screen_bottom"), 
								mcLoc("gamermod:block/screen_single"), 
								mcLoc("gamermod:block/screen_side"));
		
		//simpleBlock(BlockRegister.DON_GEROS_MASK);
	}
	
	public void screenBlock(Block block, ResourceLocation top, ResourceLocation bottom, ResourceLocation single, ResourceLocation edge)
	{
		screenBlock(block, top, top, bottom, single, edge);
	}
	
	public void screenBlock(Block block, ResourceLocation top, ResourceLocation middle, ResourceLocation bottom, ResourceLocation single, ResourceLocation edge)
	{
		String baseName = block.getRegistryName().toString();
		
        ModelFile post = models().panePost(baseName + "_post", top, edge);
        
        ModelFile side_top = models().paneSide(baseName + "_side_top", top, edge);
        ModelFile sideAlt_top = models().paneSideAlt(baseName + "_side_alt_top", top, edge);
        ModelFile noSide_top = models().paneNoSide(baseName + "_noside_top", top);
        ModelFile noSideAlt_top = models().paneNoSideAlt(baseName + "_noside_alt_top", top);
        
        ModelFile side_middle = models().paneSide(baseName + "_side_middle", middle, edge);
        ModelFile sideAlt_middle = models().paneSideAlt(baseName + "_side_alt_middle", middle, edge);
        ModelFile noSide_middle = models().paneNoSide(baseName + "_noside_middle", middle);
        ModelFile noSideAlt_middle = models().paneNoSideAlt(baseName + "_noside_alt_middle", middle);
        
        ModelFile side_bottom = models().paneSide(baseName + "_side_bottom", bottom, edge);
        ModelFile sideAlt_bottom = models().paneSideAlt(baseName + "_side_alt_bottom", bottom, edge);
        ModelFile noSide_bottom = models().paneNoSide(baseName + "_noside_bottom", bottom);
        ModelFile noSideAlt_bottom = models().paneNoSideAlt(baseName + "_noside_alt_bottom", bottom);
        
        ModelFile side_single = models().paneSide(baseName + "_side_single", single, edge);
        ModelFile sideAlt_single = models().paneSideAlt(baseName + "_side_alt_single", single, edge);
        ModelFile noSide_single = models().paneNoSide(baseName + "_noside_single", single);
        ModelFile noSideAlt_single = models().paneNoSideAlt(baseName + "_noside_alt_single", single);
        
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block).part().modelFile(post).addModel().end();
        
        // Add condition for single screen
        screenPart(builder, false, false, post, side_single, sideAlt_single, noSide_single, noSideAlt_single);
        
        // Add condition for bottom screen
        screenPart(builder, true, false, post, side_bottom, sideAlt_bottom, noSide_bottom, noSideAlt_bottom);
        
        // Add condition for top screen
        screenPart(builder, false, true, post, side_top, sideAlt_top, noSide_top, noSideAlt_top);
		
        // Add condition for middle screen
        screenPart(builder, true, true, post, side_middle, sideAlt_middle, noSide_middle, noSideAlt_middle);
	}
	
	private void screenPart(MultiPartBlockStateBuilder builder, boolean top, boolean bottom, ModelFile post, ModelFile side, ModelFile sideAlt, ModelFile noSide, ModelFile noSideAlt)
	{
		PipeBlock.PROPERTY_BY_DIRECTION.entrySet().forEach(e -> {
			Direction dir = e.getKey();
			if (dir.getAxis().isHorizontal())
			{
				boolean alt = dir == Direction.SOUTH;
				builder.part()
					.modelFile(alt || dir == Direction.WEST ? sideAlt : side)
						.rotationY(dir.getAxis() == Axis.X ? 90 : 0)
						.addModel()
							.condition(e.getValue(), true)
							.condition(ScreenBlock.UP, top)
							.condition(ScreenBlock.DOWN, bottom)
							.end()
					.part()
					.modelFile(alt || dir == Direction.EAST ? noSideAlt : noSide)
						.rotationY(dir == Direction.WEST ? 270 : dir == Direction.SOUTH ? 90 : 0)
						.addModel()
							.condition(e.getValue(), false)
							.condition(ScreenBlock.UP, top)
							.condition(ScreenBlock.DOWN, bottom);
			}
		});
	}
	
    public void paneBlock(IronBarsBlock block, ResourceLocation pane, ResourceLocation edge) {
        paneBlockInternal(block, block.getRegistryName().toString(), pane, edge);
    }

    public void paneBlock(IronBarsBlock block, String name, ResourceLocation pane, ResourceLocation edge) {
        paneBlockInternal(block, name + "_pane", pane, edge);
    }

    private void paneBlockInternal(IronBarsBlock block, String baseName, ResourceLocation pane, ResourceLocation edge) {
        ModelFile post = models().panePost(baseName + "_post", pane, edge);
        ModelFile side = models().paneSide(baseName + "_side", pane, edge);
        ModelFile sideAlt = models().paneSideAlt(baseName + "_side_alt", pane, edge);
        ModelFile noSide = models().paneNoSide(baseName + "_noside", pane);
        ModelFile noSideAlt = models().paneNoSideAlt(baseName + "_noside_alt", pane);
        paneBlock(block, post, side, sideAlt, noSide, noSideAlt);
    }

    public void paneBlock(IronBarsBlock block, ModelFile post, ModelFile side, ModelFile sideAlt, ModelFile noSide, ModelFile noSideAlt) {
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block)
                .part().modelFile(post).addModel().end();
        PipeBlock.PROPERTY_BY_DIRECTION.entrySet().forEach(e -> {
            Direction dir = e.getKey();
            if (dir.getAxis().isHorizontal()) {
                boolean alt = dir == Direction.SOUTH;
                builder.part().modelFile(alt || dir == Direction.WEST ? sideAlt : side).rotationY(dir.getAxis() == Axis.X ? 90 : 0).addModel()
                    .condition(e.getValue(), true).end()
                .part().modelFile(alt || dir == Direction.EAST ? noSideAlt : noSide).rotationY(dir == Direction.WEST ? 270 : dir == Direction.SOUTH ? 90 : 0).addModel()
                    .condition(e.getValue(), false);
            }
        });
    }

}
