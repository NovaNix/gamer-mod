package com.novanix.gamermod.common.blocks;

import com.novanix.gamermod.GamerMod;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;

public class BucketBlock extends VanillaPlaceableBlock
{

	public BucketBlock(Item placeableItem, Properties p_49795_)
	{
		super(placeableItem, p_49795_);
		// TODO Auto-generated constructor stub
	}
	
	public VoxelShape getShape(BlockState p_51222_, BlockGetter p_51223_, BlockPos p_51224_, CollisionContext p_51225_) 
	{
		return Block.box(4, 0, 4, 12, 8, 12);
	}

	@Override
	protected boolean shouldPlace(UseOnContext context)
	{
		BlockPos pos = context.getClickedPos();
		
		BlockPos adjacentBlock = pos.relative(context.getClickedFace());
		
		BlockState blockstate = context.getLevel().getBlockState(adjacentBlock);
		
		BlockHitResult blockhitresult = getPlayerPOVHitResult(context.getLevel(), context.getPlayer(), ClipContext.Fluid.SOURCE_ONLY);
		
		//GamerMod.LOGGER.info("Hit result type: " + blockhitresult.getType());
		
		BlockPos blockpos = blockhitresult.getBlockPos();
        Direction direction = blockhitresult.getDirection();
        BlockPos blockpos1 = blockpos.relative(direction);
        
        BlockState blockstate1 = context.getLevel().getBlockState(blockpos);
        return !(blockstate1.getBlock() instanceof BucketPickup);

	}
	
	protected static BlockHitResult getPlayerPOVHitResult(Level pLevel, Player pPlayer, ClipContext.Fluid pFluidMode)
	{
		float f = pPlayer.getXRot();
		float f1 = pPlayer.getYRot();
		Vec3 vec3 = pPlayer.getEyePosition();
		float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
		float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
		float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
		float f5 = Mth.sin(-f * ((float) Math.PI / 180F));
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		double d0 = pPlayer.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue();
		
		Vec3 vec31 = vec3.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
		return pLevel.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, pFluidMode, pPlayer));
	}

}
