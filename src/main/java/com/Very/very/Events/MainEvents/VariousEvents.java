package com.Very.very.Events.MainEvents;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.MusicPlayerPackets.MusicIdolS2CPacket;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber
public class VariousEvents {
    @SubscribeEvent
    public static void Clone(PlayerEvent.Clone event)
    {
        if(event.isWasDeath())
        {
            Player player = event.getOriginal();
            CompoundTag data = player.getPersistentData();
            for(int i = 0; i < Utils.AttributeName.length; i++){
                if(data.contains(Utils.AttributeName[i])){
                    data.putDouble(Utils.AttributeName[i],0);
                }
            }
            Compute.Broad(event.getEntity().level(), Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal(event.getEntity().getName().getString()+"在探索过程中身负重伤，经过救治恢复了活力。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            event.getEntity().getPersistentData().merge(event.getOriginal().getPersistentData());
        }
        else {
            Player player = event.getOriginal();
            CompoundTag data = player.getPersistentData();
            for(int i = 0; i < Utils.AttributeName.length; i++){
                if(data.contains(Utils.AttributeName[i])){
                    data.putDouble(Utils.AttributeName[i],0);
                }
            }
            event.getEntity().getPersistentData().merge(event.getOriginal().getPersistentData());
        }
    }
    @SubscribeEvent
    public static void TossCheck(ItemTossEvent event)
    {
        ItemStack itemStack = event.getEntity().getItem();
        Player player = event.getPlayer();
        CompoundTag data = itemStack.getTagElement(Utils.MOD_ID);
        if(player.getPersistentData().getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline))
        {
            player.sendSystemMessage(Component.literal("Please Input Password First!"));
            ItemStack item = event.getEntity().getItem();
            event.getPlayer().addItem(item);
        }
        if(data != null && data.contains("Owner") && !data.getString("Owner").equals(player.getName().getString())) {
            ItemStack item = event.getEntity().getItem();
            event.getPlayer().addItem(item);
            event.setCanceled(true);
        }
        if(Utils.MainHandTag.containsKey(itemStack.getItem()) || Utils.OffHandTag.containsKey(itemStack.getItem())
                || Utils.ArmorTag.containsKey(itemStack.getItem())){
            ItemStack item = event.getEntity().getItem();
            event.getPlayer().addItem(item);
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void Chat1(ClientChatReceivedEvent event)
    {
        if (!event.isSystem()) {
            Calendar calendar = Calendar.getInstance();
            Date time = calendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            String showTime = format.format(time);
            String MSG = event.getMessage().getString();
            boolean FindFirstIndexFlag = false;
            int FirstIndex = 0;
            int SecondIndex = 0;
            for (int i = 0; i < MSG.length(); i++) {
                if (MSG.charAt(i) == '<' && !FindFirstIndexFlag) {
                    FirstIndex = i;
                    FindFirstIndexFlag = true;
                }
                if (MSG.charAt(i) == '>' && FindFirstIndexFlag) {
                    SecondIndex = i;
                    break;
                }
            }
            String NewMSG = MSG.substring(SecondIndex+1);
            String Name = MSG.substring(FirstIndex+1,SecondIndex);
            int FirstLvIndex = 0;
            int SecondLvIndex = 0;
            for (int i = 0; i < Name.length(); i++) {
                if (Name.charAt(i) == '[') FirstLvIndex = i;
                if (Name.charAt(i) == ']') SecondLvIndex = i;
            }
            int SecondPrefixIndex = 0;
            for (int i = 1; i < Name.length(); i++) {
                if (Name.charAt(i) == '|') {
                    SecondPrefixIndex = i;
                    break;
                }
            }
            String Level = Name.substring(FirstLvIndex+1,SecondLvIndex);
            String NewName = Name.substring(SecondLvIndex+1);
            String Prefix = Name.substring(1,SecondPrefixIndex);
            if (Utils.PrefixColorMap.isEmpty()) Utils.PrefixColorMapInit();
            if (Utils.PrefixStyleMap.isEmpty()) Utils.PrefixStyleMapInit();
            if (Utils.PrefixColorMap.containsKey(Prefix)) {
                event.setMessage(Component.literal("["+showTime+"]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).
                        append(Component.literal("|").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD)).
                        append(Component.literal(Prefix).withStyle(ChatFormatting.RESET).withStyle(Utils.PrefixColorMap.get(Prefix))).
                        append(Component.literal("|").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD)).
                        append(Component.literal("[").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD)).
                        append(Component.literal(Level).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).
                        append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD)).
                        append(Component.literal(NewName).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" >> ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).
                        append(Component.literal(NewMSG).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            }
            if (Utils.PrefixStyleMap.containsKey(Prefix)) {
                event.setMessage(Component.literal("["+showTime+"]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).
                        append(Component.literal("|").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD)).
                        append(Component.literal(Prefix).withStyle(ChatFormatting.RESET).withStyle(Utils.PrefixStyleMap.get(Prefix))).
                        append(Component.literal("|").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD)).
                        append(Component.literal("[").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD)).
                        append(Component.literal(Level).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).
                        append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD)).
                        append(Component.literal(NewName).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" >> ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).
                        append(Component.literal(NewMSG).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            }
        }
    }
    @SubscribeEvent
    public static void Dimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        Player player = event.getEntity();
        if(player.experienceLevel < 50 && event.getTo().equals(ServerLevel.NETHER))
        {
            player.changeDimension(player.level().getServer().getLevel(Level.OVERWORLD));
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("50级后再来探索吧！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        }
    }
    @SubscribeEvent
    public static void Xp(PlayerXpEvent.LevelChange event)
    {
        Player player = event.getEntity();
        int Levels = player.experienceLevel;
        CompoundTag data = player.getPersistentData();

    }
    @SubscribeEvent
    public static void XpP(PlayerXpEvent.XpChange event)
    {
        event.setCanceled(true);
    }
    @SubscribeEvent
    public static void PreventBreak(BlockEvent.FarmlandTrampleEvent event)
    {
        event.setCanceled(true);
    }

/*    @SubscribeEvent
    public static void MusicPlayerServerSide(TickEvent.LevelTickEvent event)
    {
        if(event.level.getServer().getTickCount() % 12000 == 0 && event.side.isServer() && event.phase.equals(TickEvent.Phase.START))
        {
            List<ServerPlayer> playerList = event.level.getServer().getPlayerList().getPlayers();
            Iterator<ServerPlayer> iterator = playerList.iterator();
            while(iterator.hasNext())
            {
                ModNetworking.sendToClient(new MusicIdolS2CPacket(true),iterator.next());
            }
        }
    }*/
/*    @SubscribeEvent
    public static void MusicPlayerClientSide(TickEvent.PlayerTickEvent event)
    {
        if(event.side.isClient())
        {
            if(ClientUtils.MusicPlayerIdol)
            {
                ClientUtils.MusicPlayerIdol = false;
                Player player = event.player;
                player.level().playLocalSound(player.getX(),player.getY(),player.getZ(), ModSounds.IDOL.get(), SoundSource.RECORDS,1f,1f,true);
            }
        }
    }*/
    @SubscribeEvent
    public static void EnchantmentBook(PlayerInteractEvent.RightClickItem event)
    {
        ItemStack itemStack = event.getItemStack();
        if(event.getSide().isServer() && itemStack.getOrCreateTag().contains("StoredEnchantments") && event.getHand().equals(InteractionHand.MAIN_HAND))
        {
            Player player = event.getEntity();
            player.setItemInHand(InteractionHand.MAIN_HAND, Items.AIR.getDefaultInstance());
            Compute.ExpPercentGetAndMSGSend(player,0.1,0,player.experienceLevel);
        }
    }
    @SubscribeEvent
    public static void NameFormat(PlayerEvent.NameFormat event) {
        event.setPhase(EventPriority.LOWEST);
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            CompoundTag data = player.getPersistentData();
            String Prefix = "初来乍到";
            if (Utils.PrefixColorMap.isEmpty()) Utils.PrefixColorMapInit();
            if (Utils.PrefixStyleMap.isEmpty()) Utils.PrefixStyleMapInit();
            if (data.contains("Prefix")) Prefix = data.getString("Prefix");
            if (Utils.PrefixColorMap.containsKey(Prefix)) {
                event.setDisplayname(Component.literal("|").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD).
                        append(Component.literal(Prefix).withStyle(ChatFormatting.RESET).withStyle(Utils.PrefixColorMap.get(Prefix))).
                        append(Component.literal("|").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)).
                        append(Component.literal("[Lv."+player.experienceLevel+"]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).
                        append(event.getDisplayname()));
            }
            if (Utils.PrefixStyleMap.containsKey(Prefix)) {
                event.setDisplayname(Component.literal("|").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD).
                        append(Component.literal(Prefix).withStyle(ChatFormatting.RESET).withStyle(Utils.PrefixStyleMap.get(Prefix))).
                        append(Component.literal("|").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)).
                        append(Component.literal("[Lv."+player.experienceLevel+"]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).
                        append(event.getDisplayname()));
            }
        }
        if (player.level().isClientSide) {
            String Prefix = "初来乍到";
            int level = 0;
            if (Utils.PrefixColorMap.isEmpty()) Utils.PrefixColorMapInit();
            if (Utils.PrefixStyleMap.isEmpty()) Utils.PrefixStyleMapInit();
            if (ClientUtils.clientPrefixInfoMap.containsKey(player.getName().getString())) {
                Prefix = ClientUtils.clientPrefixInfoMap.get(player.getName().getString()).getPrefix();
                level = ClientUtils.clientPrefixInfoMap.get(player.getName().getString()).getLevel();
            }
            if (Utils.PrefixColorMap.containsKey(Prefix)) {
                event.setDisplayname(Component.literal("|").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD).
                        append(Component.literal(Prefix).withStyle(ChatFormatting.RESET).withStyle(Utils.PrefixColorMap.get(Prefix))).
                        append(Component.literal("|").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)).
                        append(Component.literal("[Lv."+level+"]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).
                        append(event.getDisplayname()));
            }
            if (Utils.PrefixStyleMap.containsKey(Prefix)) {
                event.setDisplayname(Component.literal("|").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD).
                        append(Component.literal(Prefix).withStyle(ChatFormatting.RESET).withStyle(Utils.PrefixStyleMap.get(Prefix))).
                        append(Component.literal("|").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)).
                        append(Component.literal("[Lv."+level+"]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).
                        append(event.getDisplayname()));
            }
        }
    }
}
