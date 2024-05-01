package com.very.wraq.events.core;

import com.very.wraq.customized.players.bow.Wcndymlgb.WcndymlgbCurios;
import com.very.wraq.customized.players.sceptre.Black_Feisa_.BlackFeisaCurios3;
import com.very.wraq.events.instance.CastleSecondFloor;
import com.very.wraq.files.FileHandler;
import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.misc.AnimationPackets.AnimationTickResetS2CPacket;
import com.very.wraq.netWorking.unSorted.ClientLevelS2CPacket;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.ClientUtils;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Mod.EventBusSubscriber
public class VariousEvents {
    @SubscribeEvent
    public static void ChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        ServerPlayer serverPlayer = (ServerPlayer) player;
        if (!player.level().isClientSide) {
            ModNetworking.sendToClient(new AnimationTickResetS2CPacket(),(ServerPlayer) player);
            if (WcndymlgbCurios.IsPlayer(player)) WcndymlgbCurios.Remove();
            BlackFeisaCurios3.RemoveAllay(player);
        }
    }
    @SubscribeEvent
    public static void Clone(PlayerEvent.Clone event)
    {
        if (!event.getEntity().level().isClientSide) {
            if (event.isWasDeath()) {
                Player player = event.getOriginal();
                ServerPlayer serverPlayer = (ServerPlayer) player;
                CompoundTag data = player.getPersistentData();
                for(int i = 0; i < Utils.AttributeName.length; i++){
                    if(data.contains(Utils.AttributeName[i])){
                        data.putDouble(Utils.AttributeName[i],0);
                    }
                }
                for (String string : StringUtils.Crest.CrestList) {
                    if (data.contains(string)) data.putInt(string,0);
                }
                Compute.CuriosAttribute.ResetCuriosList(player);
                Compute.Broad(event.getEntity().level(), Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal(event.getEntity().getName().getString()+"在探索过程中身负重伤，经过救治恢复了活力。").withStyle(ChatFormatting.WHITE)));
                event.getEntity().getPersistentData().merge(event.getOriginal().getPersistentData());
                serverPlayer.teleportTo(serverPlayer.getServer().getLevel(Level.OVERWORLD),437.5,69,916.6,0,0);
                ModNetworking.sendToClient(new AnimationTickResetS2CPacket(),(ServerPlayer) event.getEntity());
                Utils.instanceList.forEach(instance -> {
                    if (instance.getCurrentChallengePlayerTeam() != null && instance.getCurrentChallengePlayerTeam().getPlayerList().contains(player)) {
                        instance.addDeadTimes();
                    }
                });
                Utils.PlayerDeadTimeMap.put(player.getName().getString(),player.getServer().getTickCount() + 6000);
            }
            else {
                Player player = event.getOriginal();
                ServerPlayer serverPlayer = (ServerPlayer) player;
                CompoundTag data = player.getPersistentData();
                for(int i = 0; i < Utils.AttributeName.length; i++){
                    if(data.contains(Utils.AttributeName[i])){
                        data.putDouble(Utils.AttributeName[i],0);
                    }
                }
                for (String string : StringUtils.Crest.CrestList) {
                    if (data.contains(string)) data.putInt(string,0);
                }
                Compute.CuriosAttribute.ResetCuriosList(player);
                event.getEntity().getPersistentData().merge(event.getOriginal().getPersistentData());
                ModNetworking.sendToClient(new AnimationTickResetS2CPacket(),(ServerPlayer) event.getEntity());
            }
        }
    }
    @SubscribeEvent
    public static void TossCheck(ItemTossEvent event) throws IOException {
        ItemStack itemStack = event.getEntity().getItem();
        Player player = event.getPlayer();
        CompoundTag data = itemStack.getTagElement(Utils.MOD_ID);
        boolean Dropped = true;
        if (!player.isCreative() && !event.getPlayer().level().isClientSide) {
            if(data != null && data.contains(InventoryCheck.owner) && !data.getString(InventoryCheck.owner).equals(player.getName().getString())) {
                ItemStack item = event.getEntity().getItem();
                event.getPlayer().addItem(item);
                event.setCanceled(true);
                Dropped = false;
            }
            if(Utils.MainHandTag.containsKey(itemStack.getItem()) || Utils.OffHandTag.containsKey(itemStack.getItem())
                    || Utils.ArmorTag.containsKey(itemStack.getItem()) || Utils.CustomizedList.contains(itemStack.getItem())){
                ItemStack item = event.getEntity().getItem();
                event.getPlayer().addItem(item);
                event.setCanceled(true);
                Dropped = false;
            }
            if (itemStack.getItem().toString().contains("backpack") && !player.isCreative()) {
                event.getPlayer().addItem(itemStack);
                event.setCanceled(true);
                Dropped = false;
            }
            if (Dropped) {
                FileHandler.WRAQLogWrite(player,StringUtils.LogsType.ItemDrop,event.getEntity().getItem().toString());
            }
        }
    }
    @SubscribeEvent
    public static void PickUpItem(PlayerEvent.ItemPickupEvent event) throws IOException {
        if (!event.getEntity().level().isClientSide) FileHandler.WRAQLogWrite(event.getEntity(),StringUtils.LogsType.ItemPick,event.getStack().toString());
        if (!event.getEntity().level().isClientSide && event.getStack().is(ModItems.Value.get())) {
            Compute.ItemStackRemove(event.getEntity().getInventory(),ModItems.Value.get(),
                    Compute.ItemStackCount(event.getEntity().getInventory(),ModItems.Value.get()));
        }
        CastleSecondFloor.PlayerPickItem(event.getEntity(),event.getStack());

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
            if (SecondIndex == 0) return;
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
            int xp = Integer.parseInt(Name.substring(FirstLvIndex + 4,SecondLvIndex));
            if (SecondPrefixIndex == 0) return;
            String NewName = Name.substring(SecondLvIndex+1);
            String Prefix = Name.substring(1,SecondPrefixIndex);
            if (Utils.PrefixColorMap.containsKey(Prefix)) {
                event.setMessage(Component.literal("["+showTime+"]").withStyle(ChatFormatting.GRAY).
                        append(Component.literal("|").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD)).
                        append(Component.literal(Prefix).withStyle(Utils.PrefixColorMap.get(Prefix))).
                        append(Component.literal("|").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD)).
                        append(Component.literal("[").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD)).
                        append(Component.literal(Level).withStyle(Utils.levelStyleList.get(xp/25))).
                        append(Component.literal("]").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD)).
                        append(Component.literal(NewName).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" >> ").withStyle(ChatFormatting.GRAY)).
                        append(Component.literal(NewMSG).withStyle(ChatFormatting.WHITE)));
            }
            if (Utils.PrefixStyleMap.containsKey(Prefix)) {
                event.setMessage(Component.literal("["+showTime+"]").withStyle(ChatFormatting.GRAY).
                        append(Component.literal("|").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD)).
                        append(Component.literal(Prefix).withStyle(Utils.PrefixStyleMap.get(Prefix))).
                        append(Component.literal("|").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD)).
                        append(Component.literal("[").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD)).
                        append(Component.literal(Level).withStyle(Utils.levelStyleList.get(xp/25))).
                        append(Component.literal("]").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD)).
                        append(Component.literal(NewName).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" >> ").withStyle(ChatFormatting.GRAY)).
                        append(Component.literal(NewMSG).withStyle(ChatFormatting.WHITE)));
            }
            ClientUtils.Sounds = 12;
        }
    }
    @SubscribeEvent
    public static void Dimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        Player player = event.getEntity();
        if (player.experienceLevel < 50 && event.getTo().equals(ServerLevel.NETHER)) {
            player.changeDimension(player.level().getServer().getLevel(Level.OVERWORLD));
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("50级后再来探索吧！").withStyle(ChatFormatting.WHITE)));
        }
        else {
            if (!player.level().isClientSide) {
                if (event.getTo().equals(ServerLevel.OVERWORLD)) ModNetworking.sendToClient(new ClientLevelS2CPacket(0),(ServerPlayer) player);
                if (event.getTo().equals(ServerLevel.NETHER)) ModNetworking.sendToClient(new ClientLevelS2CPacket(1),(ServerPlayer) player);
                if (event.getTo().equals(ServerLevel.END)) ModNetworking.sendToClient(new ClientLevelS2CPacket(2),(ServerPlayer) player);
            }
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
            if (data.contains("Prefix")) Prefix = data.getString("Prefix");
            if (Utils.PrefixColorMap.containsKey(Prefix)) {
                event.setDisplayname(Component.literal("|").withStyle(ChatFormatting.GOLD).
                        append(Component.literal(Prefix).withStyle(Utils.PrefixColorMap.get(Prefix))).
                        append(Component.literal("|").withStyle(ChatFormatting.GOLD)).
                        append(Component.literal("[Lv."+player.experienceLevel+"]").withStyle(Utils.levelStyleList.get(player.experienceLevel / 25))).
                        append(Component.literal(event.getDisplayname().getString()).withStyle(CustomStyle.styleOfBloodMana)));
            }
            if (Utils.PrefixStyleMap.containsKey(Prefix)) {
                event.setDisplayname(Component.literal("|").withStyle(ChatFormatting.GOLD).
                        append(Component.literal(Prefix).withStyle(Utils.PrefixStyleMap.get(Prefix))).
                        append(Component.literal("|").withStyle(ChatFormatting.GOLD)).
                        append(Component.literal("[Lv."+player.experienceLevel+"]").withStyle(Utils.levelStyleList.get(player.experienceLevel / 25))).
                        append(Component.literal(event.getDisplayname().getString()).withStyle(CustomStyle.styleOfBloodMana)));
            }
        }
        if (player.level().isClientSide) {
            String Prefix = "初来乍到";
            int level = 0;
            if (ClientUtils.clientPrefixInfoMap.containsKey(player.getName().getString())) {
                Prefix = ClientUtils.clientPrefixInfoMap.get(player.getName().getString()).getPrefix();
                level = ClientUtils.clientPrefixInfoMap.get(player.getName().getString()).getLevel();
            }
            if (Utils.PrefixColorMap.containsKey(Prefix)) {
                event.setDisplayname(Component.literal("|").withStyle(ChatFormatting.GOLD).
                        append(Component.literal(Prefix).withStyle(Utils.PrefixColorMap.get(Prefix))).
                        append(Component.literal("|").withStyle(ChatFormatting.GOLD)).
                        append(Component.literal("[Lv."+level+"]").withStyle(Utils.levelStyleList.get(level / 25))).
                        append(Component.literal(event.getDisplayname().getString()).withStyle(CustomStyle.styleOfBloodMana)));
            }
            if (Utils.PrefixStyleMap.containsKey(Prefix)) {
                event.setDisplayname(Component.literal("|").withStyle(ChatFormatting.GOLD).
                        append(Component.literal(Prefix).withStyle(Utils.PrefixStyleMap.get(Prefix))).
                        append(Component.literal("|").withStyle(ChatFormatting.GOLD)).
                        append(Component.literal("[Lv."+level+"]").withStyle(Utils.levelStyleList.get(level / 25))).
                        append(Component.literal(event.getDisplayname().getString()).withStyle(CustomStyle.styleOfBloodMana)));
            }
        }
    }

    @SubscribeEvent
    public static void TabNameFormat(PlayerEvent.TabListNameFormat event) {
        event.setPhase(EventPriority.LOWEST);
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            CompoundTag data = player.getPersistentData();
            String Prefix = "初来乍到";
            if (data.contains("Prefix")) Prefix = data.getString("Prefix");
            if (Utils.PrefixColorMap.containsKey(Prefix)) {
                event.setDisplayName(player.getDisplayName());
            }
            if (Utils.PrefixStyleMap.containsKey(Prefix)) {
                event.setDisplayName(player.getDisplayName());
            }
        }
        if (player.level().isClientSide) {
            String Prefix = "初来乍到";
            if (Utils.PrefixColorMap.containsKey(Prefix)) {
                event.setDisplayName(player.getDisplayName());
            }
            if (Utils.PrefixStyleMap.containsKey(Prefix)) {
                event.setDisplayName(player.getDisplayName());
            }
        }
    }
}
