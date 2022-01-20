package com.novanix.gamermod.common.blocks;

import java.util.HashMap;

import javax.annotation.Nullable;

import com.novanix.gamermod.GamerMod;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GamerMod.MOD_ID)
public abstract class VanillaPlaceableBlock extends Block
{

	private static HashMap<Item, VanillaPlaceableBlock> placeables = new HashMap<Item, VanillaPlaceableBlock>();
	
	private Item placeableItem;
	
	public VanillaPlaceableBlock(Item placeableItem, Properties p_49795_) {
		super(p_49795_);
		this.placeableItem = placeableItem;
		
		placeables.put(placeableItem, this);
	}

	@Override
	public Item asItem()
	{
		return placeableItem;
	}
	
	protected abstract boolean shouldPlace(UseOnContext context);
	
	@SubscribeEvent
	public static void onPlayerItemUse(PlayerInteractEvent.RightClickBlock event)
	{
		Item item = event.getItemStack().getItem();
		
		if (placeables.containsKey(item))
		{
			VanillaPlaceableBlock block = placeables.get(item);
			UseOnContext context = RightClickBlockToUseOnContext(event);
			
			if (block.shouldPlace(context))
			{
				InteractionResult result = block.useOn(context);
				
				if (result.consumesAction())
				{
					event.setCanceled(true);
				}
			}
			
		}
	}
	
	public InteractionResult useOn(UseOnContext pContext) 
	{
		return this.place(new BlockPlaceContext(pContext));
	}

	public InteractionResult place(BlockPlaceContext pContext)
	{
		if (!pContext.canPlace())
		{
			return InteractionResult.FAIL;
		} else
		{
			BlockPlaceContext blockplacecontext = this.updatePlacementContext(pContext);
			if (blockplacecontext == null)
			{
				return InteractionResult.FAIL;
			} else
			{
				BlockState blockstate = this.getPlacementState(blockplacecontext);
				if (blockstate == null)
				{
					return InteractionResult.FAIL;
				} else if (!this.placeBlock(blockplacecontext, blockstate))
				{
					return InteractionResult.FAIL;
				} else
				{
					BlockPos blockpos = blockplacecontext.getClickedPos();
					Level level = blockplacecontext.getLevel();
					Player player = blockplacecontext.getPlayer();
					ItemStack itemstack = blockplacecontext.getItemInHand();
					BlockState blockstate1 = level.getBlockState(blockpos);
					if (blockstate1.is(blockstate.getBlock()))
					{
						blockstate1 = this.updateBlockStateFromTag(blockpos, level, itemstack, blockstate1);
						this.updateCustomBlockEntityTag(blockpos, level, player, itemstack, blockstate1);
						blockstate1.getBlock().setPlacedBy(level, blockpos, blockstate1, player, itemstack);
						if (player instanceof ServerPlayer)
						{
							CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, blockpos, itemstack);
						}
					}

					level.gameEvent(player, GameEvent.BLOCK_PLACE, blockpos);
					SoundType soundtype = blockstate1.getSoundType(level, blockpos, pContext.getPlayer());
					level.playSound(player, blockpos,
							this.getPlaceSound(blockstate1, level, blockpos, pContext.getPlayer()), SoundSource.BLOCKS,
							(soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
					if (player == null || !player.getAbilities().instabuild)
					{
						itemstack.shrink(1);
					}

					return InteractionResult.sidedSuccess(level.isClientSide);
				}
			}
		}
	}

	@Deprecated
	// Forge: Use more sensitive version {@link BlockItem#getPlaceSound(BlockState,
	// IBlockReader, BlockPos, Entity) }
	protected SoundEvent getPlaceSound(BlockState pState)
	{
		return pState.getSoundType().getPlaceSound();
	}

	// Forge: Sensitive version of BlockItem#getPlaceSound
	protected SoundEvent getPlaceSound(BlockState state, Level world, BlockPos pos, Player entity)
	{
		return state.getSoundType(world, pos, entity).getPlaceSound();
	}
	   
	@Nullable
	public BlockPlaceContext updatePlacementContext(BlockPlaceContext pContext)
	{
		return pContext;
	}

	protected boolean updateCustomBlockEntityTag(BlockPos pPos, Level pLevel, @Nullable Player pPlayer, ItemStack pStack, BlockState pState)
	{
		return BlockItem.updateCustomBlockEntityTag(pLevel, pPlayer, pPos, pStack);
	}

	@Nullable
	protected BlockState getPlacementState(BlockPlaceContext pContext)
	{
		BlockState blockstate = getStateForPlacement(pContext);
		return blockstate != null && this.canPlace(pContext, blockstate) ? blockstate : null;
	}

	private BlockState updateBlockStateFromTag(BlockPos pPos, Level pLevel, ItemStack pStack, BlockState pState)
	{
		BlockState blockstate = pState;
		CompoundTag compoundtag = pStack.getTag();
		if (compoundtag != null)
		{
			CompoundTag compoundtag1 = compoundtag.getCompound("BlockStateTag");
			StateDefinition<Block, BlockState> statedefinition = pState.getBlock().getStateDefinition();

			for (String s : compoundtag1.getAllKeys())
			{
				Property<?> property = statedefinition.getProperty(s);
				if (property != null)
				{
					String s1 = compoundtag1.get(s).getAsString();
					blockstate = updateState(blockstate, property, s1);
				}
			}
		}

		if (blockstate != pState)
		{
			pLevel.setBlock(pPos, blockstate, 2);
		}

		return blockstate;
	}

	private static <T extends Comparable<T>> BlockState updateState(BlockState pState, Property<T> pProperty,
			String pValueIdentifier)
	{
		return pProperty.getValue(pValueIdentifier).map((p_40592_) -> {
			return pState.setValue(pProperty, p_40592_);
		}).orElse(pState);
	}

	protected boolean canPlace(BlockPlaceContext pContext, BlockState pState)
	{
		Player player = pContext.getPlayer();
		CollisionContext collisioncontext = player == null ? CollisionContext.empty() : CollisionContext.of(player);
		return (!this.mustSurvive() || pState.canSurvive(pContext.getLevel(), pContext.getClickedPos()))
				&& pContext.getLevel().isUnobstructed(pState, pContext.getClickedPos(), collisioncontext);
	}
	
	protected boolean placeBlock(BlockPlaceContext pContext, BlockState pState)
	{
		return pContext.getLevel().setBlock(pContext.getClickedPos(), pState, 11);
	}
	
	protected boolean mustSurvive()
	{
		return true;
	}
	
	public static UseOnContext RightClickBlockToUseOnContext(PlayerInteractEvent.RightClickBlock event)
	{
		return new UseOnContext(event.getPlayer(), event.getHand(), event.getHitVec());
	}
	
	
}
