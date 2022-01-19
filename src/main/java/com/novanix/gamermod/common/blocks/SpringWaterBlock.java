package com.novanix.gamermod.common.blocks;

import java.util.function.Supplier;

import com.novanix.gamermod.GamerMod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.Level;

public class SpringWaterBlock extends LiquidBlock
{

	public SpringWaterBlock(Supplier<? extends FlowingFluid> pFluid, Properties pProperties) 
	{
		super(pFluid, pProperties);
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity)
	{
		GamerMod.LOGGER.info("Entity entered spring water");
		
		if (entity instanceof LivingEntity)
		{
			LivingEntity lentity = (LivingEntity) entity;
			
			lentity.addEffect(new MobEffectInstance(MobEffects.REGENERATION));
			
			GamerMod.LOGGER.info("Applying Effect");
		}
	}
	
}
