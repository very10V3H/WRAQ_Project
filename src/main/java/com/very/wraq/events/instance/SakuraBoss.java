package com.very.wraq.events.instance;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModEntityType;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.util.struct.Boss2Damage;
import com.very.wraq.common.util.struct.Instance;
import com.very.wraq.common.util.struct.PlayerTeam;
import com.very.wraq.entities.entities.Boss2.Boss2;
import com.very.wraq.events.core.LoginInEvent;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.process.func.item.InventoryOperation;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber
public class SakuraBoss {
    @SubscribeEvent
    public static void SakuraBossEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {

            if (Utils.instanceList.get(4).isChallenge()) {
                Instance instance = Utils.instanceList.get(4);
                instance.addTick();
                int instanceTick = instance.getTick();
                PlayerTeam playerTeam = instance.getCurrentChallengePlayerTeam();
                List<Player> playerList = playerTeam.getPlayerList();
                List<Player> playerListGetByName = new ArrayList<>();
                Level level = event.level;
                int[] difficultyEnhance = {1, 2, 4};
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
                                    new ClientboundSetTitleTextPacket(Component.literal("突见忍挑战").withStyle(CustomStyle.styleOfSakura));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("在五分钟内击杀突见忍!").withStyle(ChatFormatting.RED));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        } else {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("突见忍挑战").withStyle(CustomStyle.styleOfSakura));
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
                    instance.setMobList(new ArrayList<>() {{
                        add(new Boss2(ModEntityType.Boss2.get(), level));
                    }});
                    Mob entity = instance.getMobList().get(0);
                    MobSpawn.setMobCustomName(entity, ModItems.ArmorBoss2.get(),
                            Component.literal("突见忍").withStyle(CustomStyle.styleOfSakura));
                    entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(Boss2.MaxHealth * (1 + Utils.Boss2DeadTimes) * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75));
                    entity.setHealth(entity.getMaxHealth());
                    entity.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorBoss2.get().getDefaultInstance());
                    entity.moveTo(1941, 167, 1047);
                    level.addFreshEntity(entity);

                    playerListGetByName.forEach(player -> {
                        if (player != null) {
                            Compute.sendFormatMSG(player, Component.literal("黄金屋").withStyle(ChatFormatting.GOLD),
                                    Component.literal("突见忍出现在了黄金屋！").withStyle(ChatFormatting.WHITE));
                            if (Utils.Boss2DeadTimes > 0)
                                Compute.sendFormatMSG(player, Component.literal("黄金屋").withStyle(ChatFormatting.GOLD),
                                        Component.literal("突见忍的实力提升了").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal((Utils.Boss2DeadTimes) + "倍").withStyle(ChatFormatting.RED)));
                        }
                    });

                    Utils.boss2DamageList.clear();
                }

                MutableComponent difficulty = Component.literal("");
                switch (instance.getDifficulty()) {
                    case 0 -> difficulty = Component.literal("一般").withStyle(ChatFormatting.GREEN);
                    case 1 -> difficulty = Component.literal("困难").withStyle(ChatFormatting.LIGHT_PURPLE);
                    case 2 -> difficulty = Component.literal("炼狱").withStyle(ChatFormatting.RED);
                }

                // 击杀奖励
                if (instanceTick >= 200 && instance.getMobList().get(0) != null && Utils.instanceKillCount[4] >= 1) {
                    Utils.instanceKillCount[4] = 0;
                    playerListGetByName.forEach(player -> {
                        singleRewardToPlayer(player, difficultyEnhanceRate, playerNum, false);
                    });
                    playerListGetByName.forEach(player1 -> {
                        if (player1 != null) {
                            CompoundTag data1 = player1.getPersistentData();
                            data1.putInt(StringUtils.InstanceTimes.SakuraBoss, data1.getInt(StringUtils.InstanceTimes.SakuraBoss) + 1);
                        }
                    });
                    playerListGetByName.forEach(player -> {
                        Compute.sendFormatMSG(player, Component.literal("黄金屋").withStyle(ChatFormatting.GOLD),
                                Component.literal("突见忍被击败了！").withStyle(ChatFormatting.WHITE));
                        Compute.sendFormatMSG(player, Component.literal("黄金屋").withStyle(ChatFormatting.GOLD),
                                Component.literal("  征讨伤害排名如下：").withStyle(ChatFormatting.WHITE));

                        Utils.boss2DamageList.sort(Comparator.comparing(Boss2Damage::getDamage).reversed());
                        AtomicInteger index = new AtomicInteger(1);
                        Utils.boss2DamageList.forEach(boss2Damage -> {
                            Player player1 = boss2Damage.getPlayer();
                            double damage = boss2Damage.getDamage();
                            Compute.sendFormatMSG(player, Component.literal(index + ".").withStyle(ChatFormatting.RED),
                                    Component.literal(" ").withStyle(ChatFormatting.WHITE).
                                            append(player1.getDisplayName()).
                                            append(Component.literal("  DMG:" + damage + "[" + String.format("%.2f",
                                                    damage * 100 / (Boss2.MaxHealth * (1 + Utils.Boss2DeadTimes) * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75))) + "%]").withStyle(ChatFormatting.WHITE)));
                        });
                    });

                    Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("队伍:").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(playerTeam.getTeamName() + " 用时:").withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal(String.format("%.2f", (instanceTick - 200) * 0.05) + "s ").withStyle(CustomStyle.styleOfBloodMana)).
                                    append(Component.literal("成功挑战了").withStyle(ChatFormatting.WHITE)).
                                    append(instance.getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                    append(difficulty));
                    Compute.formatBroad(level, Component.literal("黄金屋").withStyle(ChatFormatting.GOLD),
                            Component.literal("下次挑战的突见忍的实力将会被提升").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal((Utils.Boss2DeadTimes + 1) + "倍").withStyle(ChatFormatting.RED)));

                    if (Utils.Boss2DeadTimes < 100) Utils.Boss2DeadTimes++;
                    instance.reset();
                    Utils.boss2DamageList.clear();

                    Compute.EndTp(playerListGetByName, new Vec3(1923, 169, 1043));
                }

                // 超时
                if (instanceTick == 6000 || playerListGetByName.size() == 0 || instance.getDeadTimes() >= playerListGetByName.size()) {
                    Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("队伍：" + playerTeam.getTeamName()).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("挑战副本:").withStyle(ChatFormatting.WHITE)).
                                    append(instance.getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                    append(difficulty).

                                    append(Component.literal("失败。").withStyle(ChatFormatting.WHITE)));
                    Utils.instanceKillCount[4] = 0;
                    instance.reset();

                    Compute.EndTp(playerListGetByName, new Vec3(1923, 169, 1043));
                }
            }
        }
    }

    public static void singleRewardToPlayer(Player player, int difficultyEnhanceRate, int playerNum, boolean isMopUp) {
        InventoryOperation.itemStackGive(player, new ItemStack(ModItems.Boss2Piece.get(), difficultyEnhanceRate));
        Random random = new Random();
        if (!isMopUp) {
            if (random.nextDouble() <= 0.025 * (playerNum - 1) * difficultyEnhanceRate) {
                Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("你通过组队挑战副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                                append(ModItems.Boss2Piece.get().getDefaultInstance().getDisplayName()));
                InventoryOperation.itemStackGive(player, new ItemStack(ModItems.Boss2Piece.get(), 1));
            }
        }

        if (LoginInEvent.playerDailyInstanceReward(player, 4)) {
            Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("每日首次通关副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                            append(ModItems.Boss2Piece.get().getDefaultInstance().getDisplayName()));

            InventoryOperation.itemStackGive(player, new ItemStack(ModItems.Boss2Piece.get(), 6));

        }

        if (random.nextDouble() < 0.01) {
            InventoryOperation.itemStackGive(player, ModItems.GoldenShieldForgeDraw.get().getDefaultInstance());
        }
    }
}
