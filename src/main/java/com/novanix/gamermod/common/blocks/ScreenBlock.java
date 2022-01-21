package com.novanix.gamermod.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ScreenBlock extends CrossCollisionBlock
{

	public static final BooleanProperty UP = BlockStateProperties.UP;
	public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
	
	public ScreenBlock(Properties p_49795_) {
		super(1.0F, 1.0F, 16.0F, 16.0F, 16.0F, p_49795_);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext pContext)
	{
		BlockGetter blockgetter = pContext.getLevel();
		BlockPos blockpos = pContext.getClickedPos();
		FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
		BlockPos blockpos1 = blockpos.north();
		BlockPos blockpos2 = blockpos.south();
		BlockPos blockpos3 = blockpos.west();
		BlockPos blockpos4 = blockpos.east();
		
		BlockPos blockpos5 = blockpos.above();
		BlockPos blockpos6 = blockpos.below();
		
		BlockState blockstate = blockgetter.getBlockState(blockpos1);
		BlockState blockstate1 = blockgetter.getBlockState(blockpos2);
		BlockState blockstate2 = blockgetter.getBlockState(blockpos3);
		BlockState blockstate3 = blockgetter.getBlockState(blockpos4);
		
		BlockState blockstate5 = blockgetter.getBlockState(blockpos5);
		BlockState blockstate6 = blockgetter.getBlockState(blockpos6);
		
		return this.defaultBlockState()
						.setValue(NORTH, Boolean.valueOf(this.attachsTo(blockstate, blockstate.isFaceSturdy(blockgetter, blockpos1, Direction.SOUTH))))
						.setValue(SOUTH, Boolean.valueOf(this.attachsTo(blockstate1, blockstate1.isFaceSturdy(blockgetter, blockpos2, Direction.NORTH))))
						.setValue(WEST, Boolean.valueOf(this.attachsTo(blockstate2, blockstate2.isFaceSturdy(blockgetter, blockpos3, Direction.EAST))))
						.setValue(EAST, Boolean.valueOf(this.attachsTo(blockstate3, blockstate3.isFaceSturdy(blockgetter, blockpos4, Direction.WEST))))
						.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER))
						.setValue(UP, blockstate5.getBlock() instanceof ScreenBlock)
						.setValue(DOWN, blockstate6.getBlock() instanceof ScreenBlock);
	}
	
	/**
	 * Update the provided state given the provided neighbor direction and neighbor
	 * state, returning a new state. For example, fences make their connections to
	 * the passed in state if possible, and wet concrete powder immediately returns
	 * its solidified counterpart. Note that this method should ideally consider
	 * only the specific direction passed in.
	 */
	@Override
	public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos)
	{
		if (pState.getValue(WATERLOGGED))
		{
			pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
		}

		if (pFacing.getAxis().isVertical())
		{
			return pState.setValue(pFacing == Direction.UP ? UP : DOWN, pFacingState.getBlock() instanceof ScreenBlock);
		}
		
		else
		{
			return pState.setValue(PROPERTY_BY_DIRECTION.get(pFacing), Boolean.valueOf(this.attachsTo(pFacingState, pFacingState.isFaceSturdy(pLevel, pFacingPos, pFacing.getOpposite()))));
		}
		
	}

	public VoxelShape getVisualShape(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext)
	{
		return Shapes.empty();
	}

	public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide)
	{
		if (pAdjacentBlockState.is(this))
		{
			if (!pSide.getAxis().isHorizontal())
			{
				return true;
			}

			if (pState.getValue(PROPERTY_BY_DIRECTION.get(pSide)) && pAdjacentBlockState.getValue(PROPERTY_BY_DIRECTION.get(pSide.getOpposite())))
			{
				return true;
			}
		}

		return super.skipRendering(pState, pAdjacentBlockState, pSide);
	}
	
	public final boolean attachsTo(BlockState pState, boolean pSolidSide)
	{
		return !isExceptionForConnection(pState) && pSolidSide || pState.getBlock() instanceof IronBarsBlock || pState.getBlock() instanceof ScreenBlock || pState.is(BlockTags.WALLS);
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) 
	{
		pBuilder.add(NORTH, EAST, WEST, SOUTH, UP, DOWN, WATERLOGGED);
	}

}
