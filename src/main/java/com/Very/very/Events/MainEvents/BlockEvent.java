package com.Very.very.Events.MainEvents;

import com.Very.very.NetWorking.PlayerCallBack;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModBlocks;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Queue;

@Mod.EventBusSubscriber
public class BlockEvent {
    @SubscribeEvent
    public static void ForgingBlockDetect(PlayerInteractEvent.RightClickBlock event)
    {
        if(!event.getEntity().level().isClientSide)
        {
            Player player = event.getEntity();
            BlockPos blockPos = event.getHitVec().getBlockPos();
            BlockState blockState = player.level().getBlockState(blockPos);
            if(Compute.BlockLimitContainBlockPos(blockPos) && !player.isCreative()) {
                Compute.FormatMSGSend(player,Component.literal("方块").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                        Component.literal("这个方块正在被使用。"));
                event.setCanceled(true);
            }
            else
            {
                if(blockState.getBlock().equals(ModBlocks.FIRST_BLOCK.get())){
                    PlayerCallBack playerCallBack = new PlayerCallBack(blockPos,player,3);
                    Utils.playerCallBackQueue.add(playerCallBack);
                }
                if(blockState.getBlock().equals(ModBlocks.HBREWING_BLOCK.get())){
                    PlayerCallBack playerCallBack = new PlayerCallBack(blockPos,player,3);
                    Utils.playerCallBackQueue.add(playerCallBack);
                }
            }
            ItemStack Sword = player.getItemInHand(InteractionHand.MAIN_HAND);
            if(Sword.is(Moditems.forestsword0.get()) || Sword.is(Moditems.forestsword1.get()) ||
                    Sword.is(Moditems.forestsword2.get()) || Sword.is(Moditems.forestsword3.get())){
                event.setCanceled(true);
            }
        }
    }
    @SubscribeEvent
    public static void PreventRightClick(PlayerInteractEvent.RightClickBlock event)
    {
        Player player = event.getEntity();
        BlockPos blockPos = event.getHitVec().getBlockPos();
        BlockState blockState = player.level().getBlockState(blockPos);
        Item result = blockState.getBlock().asItem();
        if(!player.isCreative())
        {
            if(result.equals(Items.ACACIA_TRAPDOOR) ||
                    result.equals(Items.BIRCH_TRAPDOOR) ||
                    result.equals(Items.IRON_TRAPDOOR) ||
                    result.equals(Items.OAK_TRAPDOOR) ||
                    result.equals(Items.CRIMSON_TRAPDOOR) ||
                    result.equals(Items.DARK_OAK_TRAPDOOR) ||
                    result.equals(Items.JUNGLE_TRAPDOOR) ||
                    result.equals(Items.MANGROVE_TRAPDOOR) ||
                    result.equals(Items.SPRUCE_TRAPDOOR) ||
                    result.equals(Items.WARPED_TRAPDOOR) ||
                    result.equals(Items.CHEST)
            ) event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void BlockPlace(net.minecraftforge.event.level.BlockEvent.EntityPlaceEvent event)
    {
        BlockPos blockPos = event.getPos();
        if(event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            if(!player.level().isClientSide)
            {
                if (!player.level().isClientSide && !player.isCreative()) {
                    Level level = player.level();
                    if (level.equals(level.getServer().getLevel(Level.END))) {
                        event.setCanceled(true);
                    }
                }
                else {
                    if(Utils.blockPosBreakQueue.contains(blockPos) && player.level().equals(player.getServer().getLevel(Level.OVERWORLD)))
                    {
                        event.setCanceled(true);
                        player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("此处是原始地图方块位置，不能放置方块。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    }
                    else
                    {
                        if(!player.isCreative()) Utils.blockPosQueue.add(blockPos);
                    }
                    if(Utils.netherBlockPosBreakQueue.contains(blockPos) && player.level().equals(player.getServer().getLevel(Level.NETHER)))
                    {
                        event.setCanceled(true);
                        player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("此处是原始地图方块位置，不能放置方块。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    }
                    else
                    {
                        if(!player.isCreative()) Utils.netherBlockPosQueue.add(blockPos);
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void BlockPlaceTick(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer() && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD)))
        {
            int TickCount =  event.level.getServer().getTickCount();
            if(TickCount % 1200 == 0)
            {
                Queue<BlockPos> queue = Utils.blockPosQueue;
                if(queue.size() > 100)
                {
                    for(int i = queue.size() ; i > 100 ; i--)
                    {
                        event.level.destroyBlock(queue.remove(),false);
                    }
                }
            }
            if(TickCount % 6000 == 0)
            {
                Queue <BlockPos> posQueue = Utils.blockPosBreakQueue;
                Queue <BlockState> stateQueue = Utils.blockStateQueue;
                while(!posQueue.isEmpty())
                {
                    event.level.setBlockAndUpdate(posQueue.remove(),stateQueue.remove());
                }
            }
        }
        if(event.side.isServer() && event.level.equals(event.level.getServer().getLevel(Level.NETHER)))
        {
            int TickCount =  event.level.getServer().getTickCount();
            if(TickCount % 1200 == 0)
            {
                Queue<BlockPos> queue = Utils.netherBlockPosQueue;
                if(queue.size() > 100)
                {
                    for(int i = queue.size() ; i > 100 ; i--)
                    {
                        event.level.destroyBlock(queue.remove(),false);
                    }
                }
            }
            if(TickCount % 6000 == 0)
            {
                Queue <BlockPos> posQueue = Utils.netherBlockPosBreakQueue;
                Queue <BlockState> stateQueue = Utils.netherBlockStateQueue;
                while(!posQueue.isEmpty())
                {
                    event.level.setBlockAndUpdate(posQueue.remove(),stateQueue.remove());
                }
            }
        }
    }
    @SubscribeEvent
    public static void Dig(net.minecraftforge.event.level.BlockEvent.BreakEvent event)
    {
        Player player = event.getPlayer();
        BlockPos blockPos = event.getPos();
        BlockState blockState = event.getState();
        if (!event.getPlayer().level().isClientSide && !player.isCreative()) {
            Level level = player.level();
            if (level.equals(level.getServer().getLevel(Level.END))) {
                event.setCanceled(true);
            }
        }
        if(!player.level().isClientSide && !player.isCreative() && !Utils.blockPosQueue.contains(blockPos))
        {
            Item BlockItem = blockState.getBlock().asItem();
            if(Utils.ItemCheck.size() == 0) Utils.ItemCheckInit();
            if(!player.isCreative() && blockPos.getX() >= -9 && blockPos.getX() <= 104 && blockPos.getY() >= 70 && blockPos.getY() <= 204 && blockPos.getZ() >= 991 && blockPos.getZ() <= 1099 )
            {
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("天空城区域受结界保护，不能破坏这里的方块T_T").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                event.setCanceled(true);
            }
            else
            {
                if(Utils.ItemCheck.containsKey(BlockItem) && Utils.ItemCheck.get(BlockItem))
                {
                    if(!BlockItem.equals(Items.GRASS) && !BlockItem.equals(Items.TALL_GRASS)) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("此类方块被用来保护地图了，它不能被破坏T_T").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    event.setCanceled(true);
                }
                else
                {
                    if(player.level().equals(player.getServer().getLevel(Level.OVERWORLD))){
                        Utils.blockPosBreakQueue.add(blockPos);
                        Utils.blockStateQueue.add(blockState);
                    }
                    if(player.level().equals(player.getServer().getLevel(Level.NETHER))){
                        Utils.netherBlockPosBreakQueue.add(blockPos);
                        Utils.netherBlockStateQueue.add(blockState);
                    }
                    if(event.getState().getBlock().asItem().equals(MinecartItem.byId(45)))
                    {
                        ItemStack itemStack = Items.RAW_IRON.getDefaultInstance();
                        event.getPlayer().addItem(itemStack);
                    }
                    else
                    {
                        if(event.getState().getBlock().asItem().equals(MinecartItem.byId(46)))
                        {
                            ItemStack itemStack = Items.RAW_IRON.getDefaultInstance();
                            event.getPlayer().addItem(itemStack);
                        }
                        else
                        {
                            if(event.getState().getBlock().asItem().equals(MinecartItem.byId(49)))
                            {
                                ItemStack itemStack = Items.RAW_GOLD.getDefaultInstance();
                                event.getPlayer().addItem(itemStack);
                            }
                            else
                            {
                                if(event.getState().getBlock().asItem().equals(MinecartItem.byId(50)))
                                {
                                    ItemStack itemStack = Items.RAW_GOLD.getDefaultInstance();
                                    event.getPlayer().addItem(itemStack);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
