package com.novanix.gamermod.common.items;

import java.util.List;

import javax.annotation.Nullable;

import com.novanix.gamermod.GamerMod;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RenderTooltipEvent.Color;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;


@Mod.EventBusSubscriber(modid = GamerMod.MOD_ID, bus = Bus.FORGE)
public class NoteBlockTunerItem extends Item
{

	public static boolean showBar = true;
	
	public NoteBlockTunerItem(Properties pProperties) 
	{
		super(pProperties);
	}
	
	@Override
	public ItemStack getDefaultInstance() 
	{
		ItemStack stack = super.getDefaultInstance();
		//stack.setTag
	
		setTunerNote(stack, 0);
		
		return stack;
	}
	
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) 
	{
		ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
	
		if (pPlayer.isCrouching())
		{
			int updated = incrementTunerNote(itemstack);
			
			//GamerMod.LOGGER.info("Updated tuner note: " + updated);
			
			// TODO Make it so it plays a noise when it updates to show the frequency
			
			return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
		}
		
		else
		{
			return InteractionResultHolder.fail(itemstack);
		}
	}
	
//	@Override
//	public InteractionResult useOn(UseOnContext pContext) 
//	{
//		
//		GamerMod.LOGGER.info("On Used Called");
//		
//		
//	
//	}
	
	@Override
	public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context)
    {
		//GamerMod.LOGGER.info("On First Used Called");
		
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = level.getBlockState(pos);
		
		if (state.getBlock() == Blocks.NOTE_BLOCK)
		{
			if (level.isClientSide) 
				return InteractionResult.SUCCESS;
			
			int tunerNote = getTunerNote(stack);
			
			int current = state.getValue(NoteBlock.NOTE);
			//int updated = net.minecraftforge.common.ForgeHooks.onNoteChange(level, pos, state, current, tunerNote);
			
			state = state.setValue(NoteBlock.NOTE, tunerNote);
	        level.setBlock(pos, state, 3);
	        
			// Play the block event
			if (level.getBlockState(pos.above()).isAir()) 
			{
		         level.blockEvent(pos, Blocks.NOTE_BLOCK, 0, 0);
		    }
			
			return InteractionResult.SUCCESS;
			
		}
		
		else
		{
			return InteractionResult.PASS;
		}
		
    }
	
	@Override
	public boolean isBarVisible(ItemStack pStack) 
	{
		return showBar;
	}
	
	@Override
	public int getBarWidth(ItemStack pStack) 
	{
		return Math.round(13f * (((float) getTunerNote(pStack)) / 23f));
	}

	@Override
	public int getBarColor(ItemStack pStack) 
	{
		return getTunerColor(getTunerNote(pStack));
	}
	
	public static void setTunerNote(ItemStack pStack, int note)
	{
		pStack.getOrCreateTag().putInt("note", note);
	}
	
	// Returns the new note
	public static int incrementTunerNote(ItemStack pStack)
	{
		int current = getTunerNote(pStack);
		
		int updated = (current + 1) % 24; 
		
		setTunerNote(pStack, updated);
		
		return updated;
	}
	
	public static int getTunerNote(ItemStack pStack)
	{
		int note = pStack.getOrCreateTag().getInt("note");
		
		return note;
	}
	
	public static int getTunerTagColor(ItemStack pStack, int pTintIndex)
	{
		if (pTintIndex == 0)
		{
			return 0xffffff;
		}
		//GamerMod.LOGGER.info("Setting tuner color...");
		
		return getTunerColor(getTunerNote(pStack));
	}
	
	public static int getTunerColor(int noteID)
	{
		double note = ((double) noteID) / 24d;
		
		//GamerMod.LOGGER.info("Note: " + note);
		
		// RGB Calculation code is pulled directly from the NoteParticle class
		
	    float rCol = Math.max(0.0F, Mth.sin((((float)note) + 0.0F) * ((float)Math.PI * 2F)) * 0.65F + 0.35F);
	    float gCol = Math.max(0.0F, Mth.sin((((float)note) + 0.33333334F) * ((float)Math.PI * 2F)) * 0.65F + 0.35F);
	    float bCol = Math.max(0.0F, Mth.sin((((float)note) + 0.6666667F) * ((float)Math.PI * 2F)) * 0.65F + 0.35F);
		 
		return Mth.color(rCol, gCol, bCol);
	}
	
	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) 
	{
		pTooltip.add(new TextComponent(getNoteAsText(getTunerNote(pStack))));
	}
	
	private static String[] noteNames = new String[] 
			{
					"F#",
			        "G",
			        "G#",
			        "A",
			        "A#",
			        "B",
			        "C",
			        "C#",
			        "D",
			        "D#",
			        "E",
			        "F"
			};
	
	public static String getNoteAsText(int note)
	{
		String noteName = noteNames[note % 12];
		
		String octiveName = note < 12 ? "Low" : note == 24 ? "High" : "Mid";
		
		return octiveName + " " + noteName;
	}

}
