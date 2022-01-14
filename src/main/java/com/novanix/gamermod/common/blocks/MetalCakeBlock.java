package com.novanix.gamermod.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MetalCakeBlock extends Block
{

	public MetalCakeBlock(Properties p_49795_) 
	{
		super(p_49795_);
	}
	
	public VoxelShape getShape(BlockState p_51222_, BlockGetter p_51223_, BlockPos p_51224_, CollisionContext p_51225_) {
	      return Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
	}

}
