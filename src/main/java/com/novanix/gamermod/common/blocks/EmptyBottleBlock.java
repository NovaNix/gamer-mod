package com.novanix.gamermod.common.blocks;

import java.util.stream.Stream;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EmptyBottleBlock extends VanillaPlaceableBlock
{

	private static VoxelShape BASE = Block.box(5, 0, 5, 11, 4, 11);
	private static VoxelShape NECK = Block.box(7, 4, 7, 9, 5, 9);
	private static VoxelShape TOP = Block.box(6, 5, 6, 10, 6, 10);
	private static VoxelShape CORK = Block.box(7, 6, 7, 9, 7, 9);
	
	private static VoxelShape COLLISION = Shapes.or(BASE, NECK, TOP, CORK);
	
	public EmptyBottleBlock(Properties p_49795_)
	{
		super(Items.GLASS_BOTTLE, p_49795_);
	}

	@Override
	protected boolean shouldPlace(UseOnContext context)
	{
		return context.getPlayer().isCrouching();
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) 
	{
		return COLLISION;
	}
	
	@Override
	public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos)
	{
		return true;
	}

}
