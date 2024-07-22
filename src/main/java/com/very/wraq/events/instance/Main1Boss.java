package com.very.wraq.events.instance;

import com.very.wraq.entities.entities.MainBoss.MainBoss;
import com.very.wraq.events.core.LoginInEvent;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.ModEntityType;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Struct.Instance;
import com.very.wraq.common.Utils.Struct.PlayerTeam;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
public class Main1Boss {
    @SubscribeEvent
    public static void Main1BossEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {

            if (Utils.instanceList.get(2).isChallenge()) {
                Instance instance = Utils.instanceList.get(2);
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
                Random r = new Random();
                if (instanceTick <= 200) {
                    if (instanceTick % 20 == 0) {
                        if (instanceTick == 200) {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("Main1Boss挑战").withStyle(CustomStyle.styleOfSnow));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("在五分钟内击杀Main1Boss!").withStyle(ChatFormatting.RED));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        } else {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("Main1Boss挑战").withStyle(CustomStyle.styleOfSnow));
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

                if (instanceTick <= 200 && instanceTick >= 60 && instanceTick % 20 == 0) {
                    Vec3 endVec3 = new Vec3(-164 + 0.5, 115, 1417 + 0.5);
                    Vec3 startVec3[] = {
                            new Vec3(-174 + 0.5, 117 + 0.5, 1427 + 0.5),
                            new Vec3(-178 + 0.5, 118 + 0.5, 1417 + 0.5),
                            new Vec3(-174 + 0.5, 117 + 0.5, 1407 + 0.5),
                            new Vec3(-164 + 0.5, 118 + 0.5, 1403 + 0.5),
                            new Vec3(-154 + 0.5, 117 + 0.5, 1407 + 0.5),
                            new Vec3(-150 + 0.5, 118 + 0.5, 1417 + 0.5),
                            new Vec3(-154 + 0.5, 117 + 0.5, 1427 + 0.5),
                            new Vec3(-164 + 0.5, 118 + 0.5, 1431 + 0.5),
                    };
                    ParticleProvider.LineParticle(level, 50, startVec3[(instanceTick - 60) / 20], endVec3, ParticleTypes.WAX_OFF);
                }

                if (instanceTick == 200) {
                    Vec3 LightVec[] = {
                            new Vec3(-169 + 0.5, 114, 1422 + 0.5),
                            new Vec3(-169 + 0.5, 114, 1412 + 0.5),
                            new Vec3(-159 + 0.5, 114, 1412 + 0.5),
                            new Vec3(-459 + 0.5, 114, 1422 + 0.5)
                    };
                    for (int i = 0; i < 4; i++) {
                        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                        lightningBolt.setVisualOnly(true);
                        lightningBolt.moveTo(LightVec[i]);
                        level.addFreshEntity(lightningBolt);
                    }
                    instance.setMobList(new ArrayList<>() {{
                        add(new MainBoss(ModEntityType.HETITY.get(), level));
                    }});
                    Mob entity = instance.getMobList().get(0);

                    Compute.SetMobCustomName(entity, ModItems.ArmorMain1Boss.get(),
                            Component.literal("四元方块").withStyle(CustomStyle.styleOfSnow));
                    entity.setHealth(entity.getMaxHealth());
                    entity.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorMain1Boss.get().getDefaultInstance());
                    entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10000 * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75));
                    entity.heal(entity.getMaxHealth());
                    entity.moveTo(-164 + 0.5, 115, 1417 + 0.5);
                    level.addFreshEntity(entity);
                }

                MutableComponent difficulty = Component.literal("");
                switch (instance.getDifficulty()) {
                    case 0 -> difficulty = Component.literal("一般").withStyle(ChatFormatting.GREEN);
                    case 1 -> difficulty = Component.literal("困难").withStyle(ChatFormatting.LIGHT_PURPLE);
                    case 2 -> difficulty = Component.literal("炼狱").withStyle(ChatFormatting.RED);
                }

                if (instanceTick >= 200 && instance.getMobList().get(0) != null && Utils.instanceKillCount[2] >= 1) {
                    Utils.instanceKillCount[2] = 0;
                    playerListGetByName.forEach(player -> {
                        SingleRewardToPlayer(player, difficultyEnhanceRate, playerNum, false);
                    });
                    instance.reset();
                    Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("队伍:").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(playerTeam.getTeamName() + " 成功挑战了 ")).
                                    append(instance.getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                    append(difficulty));
                    Compute.EndTp(playerListGetByName, new Vec3(-169, 114, 1394));
                }

                if (instanceTick == 6000 || playerListGetByName.size() == 0 || instance.getDeadTimes() >= playerListGetByName.size()) {
                    Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("队伍：" + playerTeam.getTeamName()).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("挑战副本:").withStyle(ChatFormatting.WHITE)).
                                    append(instance.getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                    append(difficulty).

                                    append(Component.literal("失败。").withStyle(ChatFormatting.WHITE)));
                    instance.reset();
                    Utils.instanceKillCount[2] = 0;
                    Compute.EndTp(playerListGetByName, new Vec3(-169, 114, 1394));
                }
            }
        }
    }

    public static ItemStack Main1GemsGive(Player player, int difficultyEnhanceRate) {
        Random r = new Random();
        ItemStack itemStack1 = Items.AIR.getDefaultInstance();
        switch (r.nextInt(4)) {

        }
        return itemStack1;
    }

    public static void SingleRewardToPlayer(Player player, int difficultyEnhanceRate, int playerNum, boolean isMopUp) {
        Random r = new Random();
        Level level = player.level();
        CompoundTag data = player.getPersistentData();

        ItemStack itemStack = ModItems.Main1Crystal.get().getDefaultInstance();
        Compute.itemStackGive(player, itemStack);

        if (!isMopUp) {
            if (r.nextDouble() <= 0.025 * (playerNum - 1) * difficultyEnhanceRate) {
                ItemStack itemStack1 = Main1GemsGive(player, difficultyEnhanceRate);
                Compute.formatMSGSend(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("你通过组队挑战副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                                append(itemStack1.getDisplayName()));
                Compute.itemStackGive(player, itemStack1);
            }
        }

        if (LoginInEvent.playerDailyInstanceReward(player, 2)) {
            ItemStack itemStack1 = Main1GemsGive(player, 6);
            Compute.formatMSGSend(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("每日首次通关副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                            append(itemStack1.getDisplayName()));


            Compute.itemStackGive(player, itemStack1);

        }

        Compute.ExpPercentGetAndMSGSend(player, 0.3 * difficultyEnhanceRate, PlayerAttributes.expUp(player), 40);

        ItemStack itemStack1 = Main1GemsGive(player, difficultyEnhanceRate);

        Compute.formatBroad(level, Component.literal("BOSS").withStyle(ChatFormatting.DARK_RED),
                Component.literal(player.getName().getString() + "击败了MainIBoss,").withStyle(ChatFormatting.WHITE).
                        append(Component.literal(player.getName().getString() + "获得了").withStyle(ChatFormatting.WHITE)).
                        append(itemStack1.getDisplayName()));

        Compute.itemStackGive(player, itemStack1);

        CompoundTag data1 = player.getPersistentData();
        data1.putInt(StringUtils.InstanceTimes.Main1Boss, data1.getInt(StringUtils.InstanceTimes.Main1Boss) + 1);

        if (!data.contains("KillCountOfHBoss")) data.putInt("KillCountOfHBoss", 1);
        else data.putInt("KillCountOfHBoss", data.getInt("KillCountOfHBoss") + 1);
        if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
        else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);

    }
}
