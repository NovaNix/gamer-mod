package com.novanix.gamermod.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MetalCakeBlock extends FallingBlock
{

	public static final DamageSource CAKE_DAMAGE = new DamageSource("cake").damageHelmet(); 
	
	public MetalCakeBlock(Properties p_49795_) 
	{
		super(p_49795_);
	}
	
	public VoxelShape getShape(BlockState p_51222_, BlockGetter p_51223_, BlockPos p_51224_, CollisionContext p_51225_) {
	      return Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
	}
	
	@Override
	protected void falling(FallingBlockEntity pFallingEntity) {
	      pFallingEntity.setHurtsEntities(2.0F, 40);
	}
	
	// Plays the land sound? Pulled from anvil code
	@Override
	public void onLand(Level p_48793_, BlockPos p_48794_, BlockState p_48795_, BlockState p_48796_, FallingBlockEntity p_48797_) 
	{
		if (!p_48797_.isSilent()) 
		{
			p_48793_.levelEvent(1031, p_48794_, 0);
		}
	}
	
	@Override
	public DamageSource getFallDamageSource() 
	{
		return CAKE_DAMAGE;
	}

}
