package com.novanix.gamermod.common.blocks;

import com.novanix.gamermod.ItemRegister;
import com.novanix.gamermod.SoundRegister;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FrogStatueBlock extends Block
{

	// Note: a lot of the directional code was pulled from the anvil class
	
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public static final IntegerProperty NOTE = BlockStateProperties.NOTE;
	
	private static final VoxelShape BASE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D);
	private static final VoxelShape BODY_1 = Block.box(3, 6, 4, 13, 12, 12);
	private static final VoxelShape BODY_2 = Block.box(4, 6, 3, 12, 12, 13);
	
	private static final VoxelShape Z_AXIS_AABB = Shapes.or(BASE, BODY_1);
	private static final VoxelShape X_AXIS_AABB = Shapes.or(BASE, BODY_2);
	
	public FrogStatueBlock(Properties p_49795_) 
	{
		super(p_49795_);
		this.registerDefaultState(this.stateDefinition.any().
				setValue(FACING, Direction.NORTH).
				setValue(NOTE, Integer.valueOf(12)).
				setValue(POWERED, Boolean.valueOf(false)));
		
	}
	
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) 
	{
		Direction direction = pState.getValue(FACING);
	    return direction.getAxis() == Direction.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
	}
	
	public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
		boolean flag = pLevel.hasNeighborSignal(pPos);
		if (flag != pState.getValue(POWERED)) {
			if (flag) {
				this.playNote(pLevel, pPos);
			}

			pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(flag)), 3);
		}

	}

	private void playNote(Level pLevel, BlockPos pPos) 
	{
		if (pLevel.getBlockState(pPos.above()).isAir()) 
		{
			pLevel.blockEvent(pPos, this, 0, 0);
		}
	}

	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) 
	{
		if (pLevel.isClientSide) 
		{
			return InteractionResult.SUCCESS;
		} 
		
		else 
		{
			
			ItemStack headItem = pPlayer.getItemBySlot(EquipmentSlot.HEAD);
			
			if (headItem.getItem().equals(ItemRegister.DON_GEROS_MASK))
			{
				int _new = net.minecraftforge.common.ForgeHooks.onNoteChange(pLevel, pPos, pState, pState.getValue(NOTE), pState.cycle(NOTE).getValue(NOTE));
				if (_new == -1)
					return InteractionResult.FAIL;
				pState = pState.setValue(NOTE, _new);
				pLevel.setBlock(pPos, pState, 3);
				
				pPlayer.awardStat(Stats.TUNE_NOTEBLOCK);
			}
			
			this.playNote(pLevel, pPos);
			
			return InteractionResult.CONSUME;
		}
	}

	public void attack(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) 
	{
		if (!pLevel.isClientSide) 
		{
			this.playNote(pLevel, pPos);
			pPlayer.awardStat(Stats.PLAY_NOTEBLOCK);
		}
	}
	
	@Override
	public boolean triggerEvent(BlockState pState, Level pLevel, BlockPos pPos, int pId, int pParam) 
	{
		// Commented out code below is what I think is used for activating onNoteBlock events or something idk
		
//		net.minecraftforge.event.world.NoteBlockEvent.Play e = new net.minecraftforge.event.world.NoteBlockEvent.Play(
//				pLevel, pPos, pState, pState.getValue(NOTE), pState.getValue(INSTRUMENT));
//		if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(e))
//			return false;
		//pState = pState.setValue(NOTE, e.getVanillaNoteId()).setValue(INSTRUMENT, e.getInstrument());
		int i = pState.getValue(NOTE);
		float f = (float) Math.pow(2.0D, (double) (i - 12) / 12.0D);
		pLevel.playSound((Player) null, pPos, SoundRegister.FROG_RIBBIT, SoundSource.RECORDS, 3.0F, f);
		pLevel.addParticle(ParticleTypes.NOTE, (double) pPos.getX() + 0.5D, (double) pPos.getY() + 1.2D, (double) pPos.getZ() + 0.5D, (double) i / 24.0D, 0.0D, 0.0D);
		return true;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext pContext) 
	{
		return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getClockWise());
	}
	
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
	      pBuilder.add(FACING);
	      pBuilder.add(POWERED);
	      pBuilder.add(NOTE);
	   }

}
