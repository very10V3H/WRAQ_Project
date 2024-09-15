package com.very.wraq.events.instance;


import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.util.struct.Instance;
import com.very.wraq.common.util.struct.PlayerTeam;
import com.very.wraq.events.core.LoginInEvent;
import com.very.wraq.process.func.item.InventoryOperation;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class Nether {
    @SubscribeEvent
    public static void NetherEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.NETHER))) {

            if (Utils.instanceList.get(3).isChallenge()) {
                Instance instance = Utils.instanceList.get(3);
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
                                    new ClientboundSetTitleTextPacket(Component.literal("下界副本挑战").withStyle(CustomStyle.styleOfNether));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("在五分钟内击杀所有怪物!").withStyle(ChatFormatting.RED));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        } else {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("下界副本挑战").withStyle(CustomStyle.styleOfNether));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("做好准备，离怪物出现还有 " + (10 - instanceTick / 20) + " 秒!"));
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
                    Utils.NetherInstanceFlag = true;

                    Mob mob1 = new WitherSkeleton(EntityType.WITHER_SKELETON, level);
                    Mob mob2 = new WitherSkeleton(EntityType.WITHER_SKELETON, level);
                    MobAttributeSet1(mob1, difficultyEnhanceRate, playerNum);
                    MobAttributeSet1(mob2, difficultyEnhanceRate, playerNum);


                    Mob mob3 = new ZombifiedPiglin(EntityType.ZOMBIFIED_PIGLIN, level);
                    Mob mob4 = new ZombifiedPiglin(EntityType.ZOMBIFIED_PIGLIN, level);
                    MobAttributeSet2(mob3, difficultyEnhanceRate, playerNum);
                    MobAttributeSet2(mob4, difficultyEnhanceRate, playerNum);

                    instance.setMobList(new ArrayList<>() {{
                        add(mob1);
                        add(mob2);
                        add(mob3);
                        add(mob4);
                    }});

                    level.addFreshEntity(mob1);
                    level.addFreshEntity(mob2);
                }

                if (instanceTick > 200 && instance.getMobList() != null) {
                    instance.getMobList().forEach(mob -> {
                        if (mob != null && mob.isAlive()) {
                            Element.ElementProvider(mob, Element.fire, 3);
                        }
                    });
                }

                if (instanceTick > 200 && !instance.getMobList().get(0).isAlive() && !instance.getMobList().get(1).isAlive()
                        && Utils.NetherInstanceFlag) {

                    playerList.forEach(player -> {
                        Compute.sendFormatMSG(player, Component.literal("隐藏副本").withStyle(CustomStyle.styleOfNether),
                                Component.literal("第二层怪物已刷新！").withStyle(ChatFormatting.WHITE));
                    });

                    level.addFreshEntity(instance.getMobList().get(2));
                    level.addFreshEntity(instance.getMobList().get(3));

                    Utils.NetherInstanceFlag = false;
                }


                MutableComponent difficulty = Component.literal("");
                switch (instance.getDifficulty()) {
                    case 0 -> difficulty = Component.literal("一般").withStyle(ChatFormatting.GREEN);
                    case 1 -> difficulty = Component.literal("困难").withStyle(ChatFormatting.LIGHT_PURPLE);
                    case 2 -> difficulty = Component.literal("炼狱").withStyle(ChatFormatting.RED);
                }

                // 击杀奖励
                if (instanceTick > 200 && Utils.instanceKillCount[3] >= 4) {
                    Utils.instanceKillCount[3] = 0;
                    double rank;
                    MutableComponent appraise;
                    int Seconds = (instanceTick - 200) / 20;
                    if (Seconds <= 30) {
                        rank = 2;
                        appraise = Component.literal("传说").withStyle(ChatFormatting.GOLD);
                    } else if (Seconds <= 60) {
                        rank = 1.75;
                        appraise = Component.literal("史诗").withStyle(ChatFormatting.LIGHT_PURPLE);
                    } else if (Seconds <= 90) {
                        rank = 1.5;
                        appraise = Component.literal("优秀").withStyle(ChatFormatting.AQUA);
                    } else if (Seconds <= 120) {
                        rank = 1.25;
                        appraise = Component.literal("良好").withStyle(ChatFormatting.GREEN);
                    } else {
                        rank = 1;
                        appraise = Component.literal("合格").withStyle(ChatFormatting.GRAY);
                    }


                    Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("队伍:").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(playerTeam.getTeamName() + " 成功挑战了 ")).
                                    append(instance.getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                    append(difficulty).
                                    append(Component.literal("，并获得了").withStyle(ChatFormatting.WHITE)).
                                    append(appraise).
                                    append(Component.literal("评价")));

                    playerListGetByName.forEach(player -> {
                        SingleRewardToPlayer(player, difficultyEnhanceRate, playerNum, rank, false);
                    });
                    playerListGetByName.forEach(player1 -> {
                        CompoundTag data1 = player1.getPersistentData();
                        data1.putInt(StringUtils.InstanceTimes.Nether, data1.getInt(StringUtils.InstanceTimes.Nether) + 1);
                    });

                    instance.reset();
                    Compute.EndTp(playerListGetByName, new Vec3(28, 33, 264));
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
                    instance.reset();
                    Utils.instanceKillCount[3] = 0;
                    Compute.EndTp(playerListGetByName, new Vec3(28, 33, 264));
                }
            }
        }
    }

    public static void MobAttributeSet1(Mob mob, int difficulty, int playerNum) {
        mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(38400 * difficulty * (1 + (playerNum - 1) * 0.75));
        mob.setHealth(mob.getMaxHealth());
        mob.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(80 * difficulty);
        mob.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorNSHelmet.get().getDefaultInstance());
        mob.setItemSlot(EquipmentSlot.CHEST, ModItems.ArmorNetherInstanceChest.get().getDefaultInstance());
        mob.setItemSlot(EquipmentSlot.LEGS, ModItems.ArmorNetherInstanceLeggings.get().getDefaultInstance());
        mob.setItemSlot(EquipmentSlot.FEET, ModItems.ArmorNetherInstanceBoots.get().getDefaultInstance());
        mob.setItemSlot(EquipmentSlot.MAINHAND, Items.NETHERITE_SWORD.getDefaultInstance());
        mob.moveTo(-1.5, 80, 245.5);
    }

    public static void MobAttributeSet2(Mob mob, int difficulty, int playerNum) {
        mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(66666 * difficulty * (1 + (playerNum - 1) * 0.75));
        mob.setHealth(mob.getMaxHealth());
        mob.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(80 * difficulty);
        mob.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorNSHelmet.get().getDefaultInstance());
        mob.setItemSlot(EquipmentSlot.CHEST, ModItems.ArmorNetherInstanceChest.get().getDefaultInstance());
        mob.setItemSlot(EquipmentSlot.LEGS, ModItems.ArmorNetherInstanceLeggings.get().getDefaultInstance());
        mob.setItemSlot(EquipmentSlot.FEET, ModItems.ArmorNetherInstanceBoots.get().getDefaultInstance());
        mob.setItemSlot(EquipmentSlot.MAINHAND, Items.GOLDEN_SWORD.getDefaultInstance());
        mob.moveTo(-18.5, 88, 255.5);
    }

    public static void SingleRewardToPlayer(Player player, int difficultyEnhanceRate, int playerNum, double rank, boolean isMopUp) {
        ItemStack itemStack = ModItems.Ruby.get().getDefaultInstance();
        ItemStack itemStack1 = ModItems.NetherQuartz.get().getDefaultInstance();
        itemStack.setCount(30 + (int) (rank) * difficultyEnhanceRate);
        itemStack1.setCount(30 + (int) (rank) * difficultyEnhanceRate);
        Random random = new Random();
        if (random.nextDouble() <= 0.025 * (playerNum - 1) * difficultyEnhanceRate) {
            Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("你通过组队挑战副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                            append(ModItems.NetherQuartz.get().getDefaultInstance().getDisplayName()));
            InventoryOperation.itemStackGive(player, new ItemStack(ModItems.NetherQuartz.get(), 30 + (int) (rank) * difficultyEnhanceRate));
        }

        if (LoginInEvent.playerDailyInstanceReward(player, 3)) {
            Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("每日首次通关副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                            append(ModItems.NetherQuartz.get().getDefaultInstance().getDisplayName()).
                            append(ModItems.Ruby.get().getDefaultInstance().getDisplayName()));
            InventoryOperation.itemStackGive(player, new ItemStack(ModItems.NetherQuartz.get(), 96));
            InventoryOperation.itemStackGive(player, new ItemStack(ModItems.Ruby.get(), 96));

        }

        InventoryOperation.itemStackGive(player, itemStack);

        InventoryOperation.itemStackGive(player, itemStack1);

        if (difficultyEnhanceRate == 4) {
/*            ItemStack NetherGem = ModItems.NetherGem.get().getDefaultInstance();
            NetherGem.getOrCreateTagElement(Utils.MOD_ID);

            for (int i = 0; i < 6; i++) {
                Compute.RandomAttributeProvider(NetherGem, 1, rank, player);
            }

            Compute.formatBroad(player.level(), Component.literal("隐藏副本").withStyle(ChatFormatting.RED),
                    Component.literal("").withStyle(ChatFormatting.WHITE).
                            append(Component.literal(player.getName().getString() + "获得了").withStyle(ChatFormatting.WHITE)).
                            append(NetherGem.getDisplayName()));
            InventoryOperation.itemStackGive(player, NetherGem);*/
        }
    }
}
