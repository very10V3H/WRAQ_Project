package com.Very.very.Events.MainEvents;

import com.Very.very.Items.Prefix.PrefixInfo;
import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.EntropyPackets.EntropyS2CPacket;
import com.Very.very.NetWorking.Packets.ManaSyncS2CPacket;
import com.Very.very.NetWorking.Packets.PrefixPackets.PrefixS2CPacket;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Mod.EventBusSubscriber
public class LoginInEvent {
    @SubscribeEvent
    public static void Login0(PlayerEvent.PlayerLoggedInEvent event) throws ParseException {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("欢迎来到 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA))));
            CompoundTag data = player.getPersistentData();
            for(int i = 0; i < Utils.AttributeName.length; i++){
                if(data.contains(Utils.AttributeName[i])){
                    data.putDouble(Utils.AttributeName[i],0);
                }
            }
            List<ServerPlayer> list = event.getEntity().getServer().getPlayerList().getPlayers();
            for (ServerPlayer serverPlayer : list) {
                CompoundTag TmpData = serverPlayer.getPersistentData();
                PrefixInfo prefixInfo = new PrefixInfo(TmpData.getString("Prefix"), serverPlayer.experienceLevel);
                Utils.prefixInfoMap.put(serverPlayer.getName().getString(), prefixInfo);
            }
            for (ServerPlayer serverPlayer : list) {
                for (String key : Utils.prefixInfoMap.keySet()) {
                    ModNetworking.sendToClient(new PrefixS2CPacket(key, Utils.prefixInfoMap.get(key).getPrefix(), Utils.prefixInfoMap.get(key).getLevel()), serverPlayer);
                }
            }

            data.putString(StringUtils.Login.Status,StringUtils.Login.Offline);
            ServerPlayer serverPlayer = (ServerPlayer) player;
            if (Utils.IpArrayList.contains(serverPlayer.getIpAddress())) {
                serverPlayer.connection.disconnect(Component.literal("同一个IP已经有账户在线了。").withStyle(ChatFormatting.RED));
            }

            if (Utils.IpLoginMap.containsKey(serverPlayer.getName().getString()) && Utils.IpLoginMap.get(serverPlayer.getName().getString()).equals(serverPlayer.getIpAddress())) {
                data.putString(StringUtils.Login.Status,StringUtils.Login.Online);
                Compute.FormatMSGSend(player,Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                        Component.literal("欢迎回到维瑞阿契！已为您自动登录。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
            }

            data.putDouble("DX",player.getX());
            data.putDouble("DY",player.getY());
            data.putDouble("DZ",player.getZ());
            data.putString("Name",player.getName().getString());

            for (String TickString : StringUtils.TickStringArray) {
                if (data.contains(TickString)) data.putInt(TickString,0);
            }
            if (!data.contains("Green")) data.putInt("Green",-1);
            if (!data.contains(StringUtils.ForestRune.ForestRune)) data.putInt(StringUtils.ForestRune.ForestRune,-1);
            if (!data.contains("volcanoRune")) data.putInt("volcanoRune",-1);
            if (!data.contains("ManaRune")) data.putInt("ManaRune",-1);
            if (!data.contains("MANA") || !data.contains("MAXMANA")) {
                data.putInt("MANA",100);
                data.putInt("MAXMANA",100);
                ModNetworking.sendToClient(new ManaSyncS2CPacket(9,100),(ServerPlayer) player);
            }
            if (!data.contains(StringUtils.SkillPoint_Total)) data.putInt(StringUtils.SkillPoint_Total,player.experienceLevel/2);
            if (!data.contains(StringUtils.AbilityPoint_Total)) data.putInt(StringUtils.AbilityPoint_Total,player.experienceLevel/2);
            if (!data.contains("ID_Card")) {
                player.addItem(Moditems.ID_Card.get().getDefaultInstance());
                data.putBoolean("ID_Card",false);
            }

            if (data.contains(StringUtils.Entropy.Forest) && data.getInt(StringUtils.Entropy.Forest) > 0
                    || data.contains(StringUtils.Entropy.Volcano) && data.getInt(StringUtils.Entropy.Volcano) > 0 ) {
                ModNetworking.sendToClient(new EntropyS2CPacket(data.getInt(StringUtils.Entropy.Forest),data.getInt(StringUtils.Entropy.Volcano)),serverPlayer);
            } // 次元熵客户端值赋予

            if(data.contains("SignIn"))
            {
                String DateString = data.getString("SignIn");
                SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date1 = tmpDate.parse(DateString);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date1);
                cal.add(Calendar.HOUR_OF_DAY,22);
                Calendar cal1 = Calendar.getInstance();
                if(cal1.after(cal))
                {
                    player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("击杀一只怪物来获取签到奖励！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    data.putBoolean("SignAward",true);
                }
                else
                {
                    String DateString0 = data.getString("SignIn");
                    SimpleDateFormat tmpDate0 = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date date10 = tmpDate0.parse(DateString0);
                    Calendar cal0 = Calendar.getInstance();
                    cal0.setTime(date10);
                    cal0.add(Calendar.HOUR_OF_DAY,22);
                    int year = cal0.get(Calendar.YEAR);
                    int month = cal0.get(Calendar.MONTH)+1;
                    int day = cal0.get(Calendar.DAY_OF_MONTH);
                    int hour = cal0.get(Calendar.HOUR_OF_DAY);
                    int minutes = cal0.get(Calendar.MINUTE);
                    int seconds = cal0.get(Calendar.SECOND);
                    player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("签到奖励将在 "+year+"年"+month+"月"+day+"日"+hour+"时"+minutes+"分"+seconds+"秒 可用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                }
            }
            else
            {
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("击杀一只怪物来获取签到奖励！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                data.putBoolean("SignAward",true);
            }
            if(!player.getTags().contains("player")) player.addTag("player");
            if (data.getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline)) {
                if (data.contains(StringUtils.Login.Password)) {
                    player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("使用/vmd login (密码)来登录").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

                }
                else {
                    if(!data.contains("FirstReward")) {
                        player.addItem(Moditems.ForNew.get().getDefaultInstance());
                    }
                    data.putBoolean("FirstReward",true);
                    player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("使用/vmd register (密码) (密码)来注册").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                }
            }
        }
    }
    @SubscribeEvent
    public static void LogOut(PlayerEvent.PlayerLoggedOutEvent event)
    {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            CompoundTag data = serverPlayer.getPersistentData();
            data.putString(StringUtils.Login.Status,StringUtils.Login.Offline);
            data.putInt("SLTIME",-1);
            data.putInt("PFTIME",-1);
            data.putInt("SVTIME",-1);
            Utils.IpArrayList.remove(serverPlayer.getIpAddress());
        }
    }
}
