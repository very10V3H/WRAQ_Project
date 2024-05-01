package com.very.wraq.events.instance;

import com.very.wraq.events.core.LoginInEvent;
import com.very.wraq.process.element.Element;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Struct.Instance;
import com.very.wraq.valueAndTools.Utils.Struct.PlayerTeam;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class Plain {
    public static ServerBossEvent BossInfo;
    @SubscribeEvent
    public static void PlainEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {

            if (Utils.instanceList.get(0).isChallenge()) {
                Instance instance = Utils.instanceList.get(0);
                instance.addTick();
                int instanceTick = instance.getTick();
                PlayerTeam playerTeam = instance.getCurrentChallengePlayerTeam();
                List<Player> playerList = playerTeam.getPlayerList();
                List<Player> playerListGetByName = new ArrayList<>();
                Level level = event.level;
                int[] difficultyEnhance = {1,2,4};
                int difficultyEnhanceRate = difficultyEnhance[instance.getDifficulty()];
                playerList.forEach(player -> {
                    if (event.level.getServer().getPlayerList().getPlayerByName(player.getName().getString()) != null)
                        playerListGetByName.add(event.level.getServer().getPlayerList().getPlayerByName(player.getName().getString()));
                });
                int playerNum = playerListGetByName.size();
                // 挑战前提示
                if (instanceTick <= 200) {
                    if (instanceTick % 20 == 0) {
                        if (instanceTick == 200) {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("普莱尼挑战").withStyle(CustomStyle.styleOfPlain));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("在五分钟内击杀普莱尼!").withStyle(ChatFormatting.RED));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        }
                        else {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("普莱尼挑战").withStyle(CustomStyle.styleOfPlain));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("做好准备，离Boss出现还有 " + (10 - instanceTick / 20) + " 秒!"));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        }
                    }
                }

                // 怪物生成
                if (instanceTick == 200) {

                    instance.setMobList(new ArrayList<>(){{
                        add(new Stray(EntityType.STRAY,level));
                    }});
                    Mob entity = instance.getMobList().get(0);

                    Compute.SetMobCustomName(entity, ModItems.ArmorPlainBossHelmet.get(),
                            Component.literal("普莱尼").withStyle(CustomStyle.styleOfPlain));
                    entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1000 * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75));
                    entity.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(25 * difficultyEnhanceRate);
                    entity.setHealth(entity.getMaxHealth());
                    entity.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorPlainBossHelmet.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.CHEST, ModItems.ArmorPlainBossChest.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.LEGS, ModItems.ArmorPlainBossLeggings.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.FEET, ModItems.ArmorPlainBossBoots.get().getDefaultInstance());

                    entity.moveTo(347,70,1198);
                    level.addFreshEntity(entity);

                    BossInfo = (ServerBossEvent) (new ServerBossEvent(entity.getDisplayName(), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
                    playerListGetByName.forEach(player -> {
                        BossInfo.addPlayer((ServerPlayer) player);
                    });
                }

                if (instanceTick > 200 && instance.getMobList().get(0) != null && BossInfo != null) {
                    Mob mob = instance.getMobList().get(0);
                    BossInfo.setProgress(mob.getHealth() / mob.getMaxHealth());
                    if (mob.isAlive()) Element.ElementProvider(mob, Element.Life, 3);
                }

                if (instanceTick > 200 && instance.getMobList().get(0) != null && instanceTick % 100 == 0) {
                    Mob mob = instance.getMobList().get(0);
                    List<Player> players = level.getEntitiesOfClass(Player.class, AABB.ofSize(mob.position(),50,50,50));
                    players.forEach(player -> {
                        if (playerListGetByName.contains(player)) {
                            if (player.position().distanceTo(mob.position()) <= 6) {
                                player.heal(75 * difficultyEnhanceRate);
                            }
                            else {
                                Compute.Damage.ManaDamageToPlayer(mob,player,125 * difficultyEnhanceRate);
                                mob.heal(125 * difficultyEnhanceRate);
                            }
                        }
                    });
                    ParticleProvider.DisperseParticle(mob.position(),(ServerLevel) level,
                            1,1,120,ModParticles.LONG_PLAIN.get(), 1);
                    ParticleProvider.DisperseParticle(mob.position(),(ServerLevel) level,
                            1.5,1,120,ModParticles.LONG_PLAIN.get(), 1);
                }

                if (instanceTick > 200 && instance.getMobList().get(0) != null && instanceTick % 100 == 50) {
                    Mob mob = instance.getMobList().get(0);
                    List<Player> players = level.getEntitiesOfClass(Player.class, AABB.ofSize(mob.position(),20,20,20));
                    players.forEach(player -> {
                        if (playerListGetByName.contains(player)) {
                            if (player.position().distanceTo(mob.position()) <= 6) {
                                Compute.Damage.ManaDamageToPlayer(mob,player,200 * difficultyEnhanceRate);
                                mob.heal(200 * difficultyEnhanceRate);
                            }
                            else {
                                player.heal(100 * difficultyEnhanceRate);
                            }
                        }
                    });
                    ParticleProvider.DisperseParticle(mob.position(),(ServerLevel) level,
                            1,1,120,ModParticles.LONG_ENTROPY.get(), 1);
                    ParticleProvider.DisperseParticle(mob.position(),(ServerLevel) level,
                            1.5,1,120,ModParticles.LONG_ENTROPY.get(), 1);
                }

                MutableComponent difficulty = Component.literal("");
                switch (instance.getDifficulty()) {
                    case 0 -> difficulty = Component.literal("一般").withStyle(ChatFormatting.GREEN);
                    case 1 -> difficulty = Component.literal("困难").withStyle(ChatFormatting.LIGHT_PURPLE);
                    case 2 -> difficulty = Component.literal("炼狱").withStyle(ChatFormatting.RED);
                }

                // 击杀奖励
                if (instanceTick >= 200 && instance.getMobList().get(0) != null && Utils.instanceKillCount[0] == 1) {
                    Utils.instanceKillCount[0] = 0;
                    playerListGetByName.forEach(player -> {
                        SingleRewardToPlayer(player, difficultyEnhanceRate, playerNum, false);
                    });

                    playerListGetByName.forEach(player1 -> {
                        CompoundTag data1 = player1.getPersistentData();
                        data1.putInt(StringUtils.InstanceTimes.Plain,data1.getInt(StringUtils.InstanceTimes.Plain) + 1);
                    });
                    instance.reset();
                    Compute.FormatBroad(level,Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("队伍:").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(playerTeam.getTeamName() + " 成功挑战了 ")).
                                    append(instance.getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                    append(difficulty));
                    Compute.EndTp(playerListGetByName,new Vec3(358,64,1178));
                    if (BossInfo != null) playerListGetByName.forEach(player -> {
                        BossInfo.removePlayer((ServerPlayer) player);
                    });
                    BossInfo = null;

                }

                // 失败
                if (instanceTick == 6000 || playerListGetByName.size() == 0 || instance.getDeadTimes() >= playerListGetByName.size()) {
                    Compute.FormatBroad(level,Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("队伍：" + playerTeam.getTeamName()).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("挑战副本:").withStyle(ChatFormatting.WHITE)).
                                    append(instance.getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                    append(difficulty).
                                    append(Component.literal(" 失败。").withStyle(ChatFormatting.WHITE)));
                    Utils.instanceKillCount[0] = 0;
                    instance.reset();
                    Compute.EndTp(playerListGetByName,new Vec3(358,64,1178));
                    if (BossInfo != null) playerListGetByName.forEach(player -> {
                        BossInfo.removePlayer((ServerPlayer) player);
                    });
                    BossInfo = null;
                }
            }
        }
    }

    public static void SingleRewardToPlayer(Player player, int difficultyEnhanceRate, int playerNum, boolean isMopUp) {
        Random random = new Random();
        Level level = player.level();
        ItemStack itemStack = new ItemStack(Items.AIR);
        if (random.nextDouble() < 0.05 * (1 + Compute.PlayerLuckyUp(player)) * difficultyEnhanceRate) {
            switch (random.nextInt(4)) {
                case 0 -> itemStack = new ItemStack(ModItems.PlainAttackRing0.get());
                case 1 -> itemStack = new ItemStack(ModItems.PlainManaAttackRing0.get());
                case 2 -> itemStack = new ItemStack(ModItems.PlainHealthRing0.get());
                case 3 -> itemStack = new ItemStack(ModItems.PlainDefenceRing0.get());
            }
        }

        if (!isMopUp) {
            if (random.nextDouble() <= 0.025 * (playerNum - 1) * difficultyEnhanceRate) {
                Compute.FormatMSGSend(player,Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("你通过组队挑战副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                                append(ModItems.PlainBossSoul.get().getDefaultInstance().getDisplayName()));
                try {
                    Compute.ItemStackGive(player, new ItemStack(ModItems.PlainBossSoul.get(),1));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (LoginInEvent.playerDailyInstanceReward(player, 0)) {
            Compute.FormatMSGSend(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("每日首次通关副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                            append(ModItems.PlainBossSoul.get().getDefaultInstance().getDisplayName()));

            try {
                Compute.ItemStackGive(player, new ItemStack(ModItems.PlainBossSoul.get(), 8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        Compute.ExpPercentGetAndMSGSend(player,0.3 * difficultyEnhanceRate,0,20);
        if (!itemStack.is(Items.AIR)) {
            Compute.FormatBroad(level,Component.literal("副本").withStyle(ChatFormatting.RED),
                    Component.literal("").
                            append(player.getDisplayName()).
                            append(Component.literal("获得了:").withStyle(ChatFormatting.WHITE)).
                            append(itemStack.getDisplayName()));
            try {
                Compute.ItemStackGive(player,itemStack);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        try {
            Compute.ItemStackGive(player,new ItemStack(ModItems.PlainBossSoul.get()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
