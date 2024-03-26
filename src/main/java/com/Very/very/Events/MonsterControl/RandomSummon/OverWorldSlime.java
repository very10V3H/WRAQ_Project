package com.Very.very.Events.MonsterControl.RandomSummon;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class OverWorldSlime {
    @SubscribeEvent
    public static void SlimeSummon (TickEvent.PlayerTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
            Player player = event.player;
            Level level = player.level();
            Level OverWorld = player.getServer().getLevel(Level.OVERWORLD);
            int PlayerExpLevel = player.experienceLevel;
            MinecraftServer server = player.getServer();
            Random random = new Random();
            if (!player.isCreative() && random.nextDouble(1) < 0.2 * (1 + Compute.PlayerLuckyUp(player)) && server.getTickCount() % 6000 == 0 && level.equals(OverWorld) && player.onGround()) {
                List<Slime> playerSlimeList = Utils.PlayerRandomSlimeMap.get(player);
                if (playerSlimeList == null || playerSlimeList.size() == 0) {
                    playerSlimeList = new ArrayList<>();
                    for (int i = 0; i < 3; i ++) {
                        Slime slime = new Slime(EntityType.SLIME,level);
                        slime.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorRandomSlime.get().getDefaultInstance());
                        Compute.SetMobCustomName(slime, ModItems.ArmorRandomSlime.get(), Component.literal("史莱姆").withStyle(ChatFormatting.GREEN));
                        slime.setSize(1,true);
                        slime.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(PlayerExpLevel * 2);
                        slime.getAttribute(Attributes.MAX_HEALTH).setBaseValue(50 * PlayerExpLevel);
                        slime.setHealth(50 * PlayerExpLevel);
                        playerSlimeList.add(slime);
                    }
                }
                for (Slime slime : playerSlimeList) {
                    slime = new Slime(EntityType.SLIME,level);
                    slime.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorRandomSlime.get().getDefaultInstance());
                    Compute.SetMobCustomName(slime, ModItems.ArmorRandomSlime.get(), Component.literal("史莱姆").withStyle(ChatFormatting.GREEN));
                    slime.setSize(1,true);
                    slime.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(PlayerExpLevel * 2);
                    slime.getAttribute(Attributes.MAX_HEALTH).setBaseValue(50 * PlayerExpLevel);
                    slime.setHealth(50 * PlayerExpLevel);
                    slime.moveTo(player.getX() + random.nextInt(3) - 1.5,player.getY(),player.getZ() + random.nextInt(3) - 1.5);
                    Utils.SlimeRewardPlayer.put(slime,player);
                    level.addFreshEntity(slime);
                }
                Utils.PlayerRandomSlimeMap.put(player,playerSlimeList);
                ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.FIREWORK_ROCKET_LAUNCH), SoundSource.PLAYERS,player.getX(),player.getY(),player.getZ(),1,1,0);
                ServerPlayer serverPlayer = (ServerPlayer) player;
                serverPlayer.connection.send(clientboundSoundPacket);
                Compute.FormatMSGSend(player,Component.literal("随机事件").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                        Component.literal("有几只可爱的史莱姆生成在你的附近！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
            }
        }
    }
}
