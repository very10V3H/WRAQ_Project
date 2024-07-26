package com.very.wraq.events.instance;

import com.very.wraq.events.core.LoginInEvent;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Struct.Instance;
import com.very.wraq.common.Utils.Struct.PlayerTeam;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
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
import net.minecraft.world.entity.monster.Zombie;
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
import java.util.concurrent.atomic.AtomicBoolean;

@Mod.EventBusSubscriber
public class Lightning {
    @SubscribeEvent
    public static void PlainEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {

            if (Utils.instanceList.get(1).isChallenge()) {
                Instance instance = Utils.instanceList.get(1);
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
                                    new ClientboundSetTitleTextPacket(Component.literal("唤雷岛挑战").withStyle(CustomStyle.styleOfLightingIsland));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("在一分钟内尽可能多地击杀怪物。").withStyle(ChatFormatting.RED));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        } else {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("唤雷岛挑战").withStyle(CustomStyle.styleOfLightingIsland));
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
                    MobSpawn(instance, level, difficultyEnhanceRate, playerNum);
                }

                if (instanceTick > 200 && instanceTick % 10 == 0) {
                    AtomicBoolean AllMobDead = new AtomicBoolean(true);
                    instance.getMobList().forEach(mob -> {
                        if (mob.isAlive()) AllMobDead.set(false);
                    });
                    if (AllMobDead.get()) {
                        instance.getMobList().clear();
                        MobSpawn(instance, level, difficultyEnhanceRate, playerNum);
                    }
                }
                if (instanceTick > 200 && instance.getMobList() != null) {
                    instance.getMobList().forEach(mob -> {
                        if (mob != null && mob.isAlive()) Element.ElementProvider(mob, Element.lightning, 3);
                    });
                }

                MutableComponent difficulty = Component.literal("");
                switch (instance.getDifficulty()) {
                    case 0 -> difficulty = Component.literal("一般").withStyle(ChatFormatting.GREEN);
                    case 1 -> difficulty = Component.literal("困难").withStyle(ChatFormatting.LIGHT_PURPLE);
                    case 2 -> difficulty = Component.literal("炼狱").withStyle(ChatFormatting.RED);
                }

                if (instanceTick > 200) {
                    if (instanceTick % 200 == 0) {
                        playerListGetByName.forEach(player -> {
                            Compute.sendFormatMSG(player, Component.literal("唤雷").withStyle(CustomStyle.styleOfLightingIsland),
                                    Component.literal("距离挑战结束，还有").withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("" + (1400 - instanceTick) / 20 + "秒").withStyle(ChatFormatting.WHITE)));
                        });
                    }
                }

                // 击杀奖励
                if (instanceTick >= 1400) {
                    playerListGetByName.forEach(player -> {
                        SingleRewardToPlayer(player, difficultyEnhanceRate, playerNum, false);
                    });

                    Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("队伍:").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(playerTeam.getTeamName() + " 成功挑战了 ")).
                                    append(instance.getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                    append(difficulty).
                                    append(Component.literal("共击杀了").withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal("" + Utils.instanceKillCount[1]).withStyle(CustomStyle.styleOfLightingIsland)).
                                    append(Component.literal("只唤雷守卫。").withStyle(ChatFormatting.WHITE)));
                    playerListGetByName.forEach(player -> {
                        CompoundTag data = player.getPersistentData();
                        data.putInt(StringUtils.InstanceTimes.Lightning, data.getInt(StringUtils.InstanceTimes.Lightning) + 1);
                    });
                    instance.reset();
                    Utils.instanceKillCount[1] = 0;
                    Compute.EndTp(playerListGetByName, new Vec3(1384, 70, 554));
                }

                // 失败
                if (instanceTick == 6000 || playerListGetByName.size() == 0 || instance.getDeadTimes() >= playerListGetByName.size()) {
                    Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("队伍：" + playerTeam.getTeamName()).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("挑战副本:").withStyle(ChatFormatting.WHITE)).
                                    append(instance.getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                    append(difficulty).
                                    append(Component.literal("失败。").withStyle(ChatFormatting.WHITE)));
                    Utils.instanceKillCount[1] = 0;
                    instance.reset();
                    Compute.EndTp(playerListGetByName, new Vec3(1384, 70, 554));
                }
            }
        }
    }

    public static void MobAttributeSet(Mob mob, int difficultyRate, int playerNum) {
        Compute.SetMobCustomName(mob, ModItems.ArmorLZHelmet.get(),
                Component.literal("唤雷守卫").withStyle(CustomStyle.styleOfLightingIsland));

        mob.setItemSlot(EquipmentSlot.HEAD, Compute.FoilAddItemStack(ModItems.ArmorLZHelmet.get().getDefaultInstance()));
        mob.setItemSlot(EquipmentSlot.CHEST, Compute.FoilAddItemStack(ModItems.ArmorLZChest.get().getDefaultInstance()));
        mob.setItemSlot(EquipmentSlot.LEGS, Compute.FoilAddItemStack(ModItems.ArmorLZLeggings.get().getDefaultInstance()));
        mob.setItemSlot(EquipmentSlot.FEET, Compute.FoilAddItemStack(ModItems.ArmorLZBoots.get().getDefaultInstance()));
        mob.setItemSlot(EquipmentSlot.MAINHAND, Items.IRON_SWORD.getDefaultInstance());
        mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(2000 * difficultyRate * (1 + (playerNum - 1) * 0.75));
        mob.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(60 * difficultyRate);
        mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        mob.setHealth(mob.getMaxHealth());

    }

    public static void MobSpawn(Instance instance, Level level, int difficultyEnhanceRate, int playerNum) {
        instance.setMobList(new ArrayList<>() {{
            add(new Zombie(EntityType.ZOMBIE, level));
            add(new Zombie(EntityType.ZOMBIE, level));
            add(new Zombie(EntityType.ZOMBIE, level));
            add(new Zombie(EntityType.ZOMBIE, level));
        }});

        Mob mob0 = instance.getMobList().get(0);
        Mob mob1 = instance.getMobList().get(1);
        Mob mob2 = instance.getMobList().get(2);
        Mob mob3 = instance.getMobList().get(3);

        MobAttributeSet(mob0, difficultyEnhanceRate, playerNum);
        MobAttributeSet(mob1, difficultyEnhanceRate, playerNum);
        MobAttributeSet(mob2, difficultyEnhanceRate, playerNum);
        MobAttributeSet(mob3, difficultyEnhanceRate, playerNum);

        mob0.moveTo(1423, 84, 559);
        mob1.moveTo(1435, 84, 559);
        mob2.moveTo(1435, 84, 571);
        mob3.moveTo(1423, 84, 571);

        level.addFreshEntity(mob0);
        level.addFreshEntity(mob1);
        level.addFreshEntity(mob2);
        level.addFreshEntity(mob3);
    }

    public static void SingleRewardToPlayer(Player player, int difficultyEnhanceRate, int playerNum, boolean isMopUp) {
        Random random = new Random();
        if (!isMopUp) {
            if (random.nextDouble() <= 0.025 * (playerNum - 1) * difficultyEnhanceRate) {
                Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("你通过组队挑战副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                                append(ModItems.LightningSoul.get().getDefaultInstance().getDisplayName()));
                Compute.itemStackGive(player, new ItemStack(ModItems.LightningSoul.get(), Utils.instanceKillCount[1] * difficultyEnhanceRate));

            }
        }

        Compute.itemStackGive(player, new ItemStack(ModItems.LightningSoul.get(), isMopUp ? 96 : Utils.instanceKillCount[1] * difficultyEnhanceRate));

        if (LoginInEvent.playerDailyInstanceReward(player, 1)) {
            Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("每日首次通关副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                            append(ModItems.LightningSoul.get().getDefaultInstance().getDisplayName()));
            Compute.itemStackGive(player, new ItemStack(ModItems.LightningSoul.get(), 192));
        }
    }
}
