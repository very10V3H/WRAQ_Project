package com.Very.very.Events.Instance;

import com.Very.very.Events.FightEvents.MonsterAttackEvent;
import com.Very.very.Events.MainEvents.LoginInEvent;
import com.Very.very.Process.Particle.ParticleProvider;
import com.Very.very.Render.Particles.ModParticles;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Struct.Boss2Damage;
import com.Very.very.ValueAndTools.Utils.Struct.Instance;
import com.Very.very.ValueAndTools.Utils.Struct.PlayerTeam;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber
public class Devil {
    public static ServerBossEvent BossInfo;
    public static double DevilAttackUp = 0;
    public static boolean DevilSkill3Flag = true;
    public static boolean DevilSkill4Flag = true;
    public static double DevilBaseAttack = 3000;

    @SubscribeEvent
    public static void DevilEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {

            int InstanceIndex = 6;
            double MaxHealth = 7000000;
            if (Utils.instanceList.get(InstanceIndex).isChallenge()) {
                Instance instance = Utils.instanceList.get(InstanceIndex);
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
                                    new ClientboundSetTitleTextPacket(Component.literal("旧世复生魔王").withStyle(Utils.styleOfBloodMana));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("在十分钟内击杀旧世复生魔王!").withStyle(ChatFormatting.RED));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        } else {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("旧世复生魔王").withStyle(Utils.styleOfBloodMana));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("做好准备，离Boss出现还有 " + (10 - instanceTick / 20) + " 秒!"));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        }
                        if (instanceTick < 200) {
                            List<Mob> list = level.getEntitiesOfClass(Mob.class,AABB.ofSize(new Vec3(1906,112,1242),100,100,100));
                            list.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
                        }
                    }
                }

                // 怪物生成
                if (instanceTick == 200) {

                    instance.setMobList(new ArrayList<>() {{
                        add(new Zombie(EntityType.ZOMBIE, level));
                    }});
                    Mob entity = instance.getMobList().get(0);
                    entity.setBaby(true);
                    Compute.SetMobCustomName(entity, ModItems.MobArmorDevilHelmet.get(),
                            Component.literal("旧世复生魔王").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBloodMana));

                    entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(MaxHealth * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75));
                    entity.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(DevilBaseAttack);
                    entity.setHealth(entity.getMaxHealth());
                    entity.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorDevilHelmet.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorEarthManaChest.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorEarthManaLeggings.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorEarthManaBoots.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.MAINHAND, ModItems.ManaKnife.get().getDefaultInstance());

                    entity.moveTo(1906, 112, 1242);
                    level.addFreshEntity(entity);
                    DevilSkill3Flag = true;
                    DevilSkill4Flag = true;
                    Utils.DevilDamageList.clear();

                    BossInfo = (ServerBossEvent) (new ServerBossEvent(entity.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
                    playerListGetByName.forEach(player -> {
                        BossInfo.addPlayer((ServerPlayer) player);
                    });
                }

                if (instanceTick > 200 && instance.getMobList().get(0) != null && BossInfo != null) {
                    Mob mob = instance.getMobList().get(0);
                    BossInfo.setProgress(mob.getHealth() / mob.getMaxHealth());
                }

                if (instanceTick > 200 && instance.getMobList().get(0) != null) {
                    Mob mob = instance.getMobList().get(0);
                    int Frequency = DevilSkill3Flag ? 160 : 80;
                    if (instanceTick % Frequency == 0) {
                        Random random = new Random();
                        if (random.nextDouble() < 0.5) {
                            Skill1(mob,playerListGetByName,difficultyEnhanceRate);
                        } else Skill2(mob,playerListGetByName,difficultyEnhanceRate);
                    }
                    Skill3(mob);
                    Skill4(mob,playerListGetByName,difficultyEnhanceRate);
                } // AI

                MutableComponent difficulty = Component.literal("");
                switch (instance.getDifficulty()) {
                    case 0 ->
                            difficulty = Component.literal("一般").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN);
                    case 1 ->
                            difficulty = Component.literal("困难").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE);
                    case 2 ->
                            difficulty = Component.literal("炼狱").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED);
                }

                // 击杀奖励
                if (instanceTick >= 200 && instance.getMobList().get(0) != null && Utils.instanceKillCount[InstanceIndex] == 1) {
                    Utils.instanceKillCount[InstanceIndex] = 0;
                    Random random = new Random();
                    playerListGetByName.forEach(player -> {
                        if (random.nextDouble() <= 0.25 * difficultyEnhanceRate) {
                            try {
                                Compute.ItemStackGive(player, new ItemStack(ModItems.DevilLoot.get(),1));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if (random.nextDouble() <= 0.025 * (playerNum - 1) * difficultyEnhanceRate) {
                            Compute.FormatMSGSend(player,Component.literal("额外奖励").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("你通过组队挑战副本，额外获得了:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                            append(ModItems.DevilLoot.get().getDefaultInstance().getDisplayName()));
                            try {
                                Compute.ItemStackGive(player, new ItemStack(ModItems.DevilLoot.get(),1));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if (LoginInEvent.playerDailyInstanceReward(player, 6)) {
                            Compute.FormatMSGSend(player, Component.literal("额外奖励").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("每日首次通关副本，额外获得了:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                            append(ModItems.DevilLoot.get().getDefaultInstance().getDisplayName()));

                            try {
                                Compute.ItemStackGive(player, new ItemStack(ModItems.DevilLoot.get(), 6));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    });

                    Compute.FormatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                            Component.literal("队伍:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(playerTeam.getTeamName() +" 用时:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal(String.format("%.2f", (instanceTick - 200) * 0.05) + "s ").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBloodMana)).
                                    append(Component.literal("成功挑战了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                    append(instance.getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).
                                    append(difficulty));

                    playerListGetByName.forEach(player -> {
                        Compute.FormatMSGSend(player,Component.literal("副本").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                                Component.literal("  征讨伤害排名如下：").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));

                        Utils.DevilDamageList.sort(Comparator.comparing(Boss2Damage::getDamage).reversed());
                        AtomicInteger index = new AtomicInteger(1);

                        Utils.DevilDamageList.forEach(boss2Damage -> {
                            if (boss2Damage.getPlayer() != null) {
                                Player player1 = boss2Damage.getPlayer();
                                double damage = boss2Damage.getDamage();
                                if (playerListGetByName.size() == 1) {
                                    if (damage / (MaxHealth * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75)) >= 0.9
                                            && (instanceTick - 200) <= 600) {
                                        CompoundTag data = player1.getPersistentData();
                                        if (data.getInt(StringUtils.PlayerInstanceProgress) < 7) {
                                            data.putInt(StringUtils.PlayerInstanceProgress,7);
                                            Compute.FormatBroad(level,Component.literal("试炼").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                                                    Component.literal("").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                            append(player1.getDisplayName()).
                                                            append(Component.literal(" 完成了 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                            append(instance.getInstanceName()).
                                                            append(Component.literal(" 试炼!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                        }
                                    }
                                }
                                Compute.FormatMSGSend(player,Component.literal(index + ".").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                                        Component.literal(" ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(player1.getDisplayName()).
                                                append(Component.literal("  DMG:" + damage + "[" + String.format("%.2f",damage * 100 / (MaxHealth * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75))) + "%]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                index.incrementAndGet();
                            }
                        });
                    });

                    playerListGetByName.forEach(player -> {
                        CompoundTag data = player.getPersistentData();
                        data.putInt(StringUtils.InstanceTimes.Devil,data.getInt(StringUtils.InstanceTimes.Devil) + 1);
                    });
                    instance.reset();
                    Utils.DevilDamageList.clear();
                    DevilAttackUp = 0;
                    Compute.EndTp(playerListGetByName,new Vec3(1888,113,1240));
                    if (BossInfo != null) playerListGetByName.forEach(player -> {
                        BossInfo.removePlayer((ServerPlayer) player);
                    });
                    BossInfo = null;
                }

                // 失败
                if (instanceTick == 12200 || playerListGetByName.size() == 0 || instance.getDeadTimes() >= playerListGetByName.size()) {
                    Compute.FormatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                            Component.literal("队伍：" + playerTeam.getTeamName()).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("挑战副本:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                    append(instance.getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).
                                    append(difficulty).
                                    append(Component.literal(" 失败。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    Utils.instanceKillCount[InstanceIndex] = 0;
                    instance.reset();
                    Utils.DevilDamageList.clear();
                    DevilAttackUp = 0;
                    Compute.EndTp(playerListGetByName,new Vec3(1888,113,1240));
                    if (BossInfo != null) playerListGetByName.forEach(player -> {
                        BossInfo.removePlayer((ServerPlayer) player);
                    });
                    BossInfo = null;
                }
            }
        }
    }
    public static void Skill1(Mob mob, List<Player> playerList, int difficulty) {
        Devil.DevilAttackUp += playerList.size() * difficulty * 100;
        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                new ClientboundSetTitleTextPacket(Component.literal("魔源奔流").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBloodMana));
        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                new ClientboundSetSubtitleTextPacket(Component.literal("魔王吸取大量法力值!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED));
        playerList.forEach(player -> {
            if (player.distanceTo(mob) < 50) {
                int ManaDecrease = difficulty * 100;
                if (Compute.PlayerCurrentManaNum(player) < ManaDecrease) {
                    ManaDecrease -= Compute.PlayerCurrentManaNum(player);
                    Compute.PlayerManaAddOrCost(player,- Compute.PlayerCurrentManaNum(player));
                    Compute.Damage.ManaDamageToPlayer(mob,player,30 * ManaDecrease);
                }
                else Compute.PlayerManaAddOrCost(player, - ManaDecrease);
                ((ServerPlayer) player).connection.send(clientboundSetTitleTextPacket);
                ((ServerPlayer) player).connection.send(clientboundSetSubtitleTextPacket);
            }
        });
        ParticleProvider.GatherParticle(mob.position(), (ServerLevel) mob.level(), 1, 6, 120, ModParticles.LONG_ENTROPY.get(), 0.25);
        ParticleProvider.GatherParticle(mob.position(), (ServerLevel) mob.level(), 1.5, 6, 120, ModParticles.LONG_ENTROPY.get(), 0.25);

    }
    public static void Skill2(Mob mob, List<Player> playerList, int difficulty) {
        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                new ClientboundSetTitleTextPacket(Component.literal("腥月之子").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBloodMana));
        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                new ClientboundSetSubtitleTextPacket(Component.literal("魔王吸取大量生命值!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED));
        playerList.forEach(player -> {
            if (player.distanceTo(mob) < 50) {
                MonsterAttackEvent.MonsterAttack(mob,player,1000 * difficulty);
                mob.heal(2500000 * difficulty);
                ((ServerPlayer) player).connection.send(clientboundSetTitleTextPacket);
                ((ServerPlayer) player).connection.send(clientboundSetSubtitleTextPacket);
            }
        });
        ParticleProvider.GatherParticle(mob.position(), (ServerLevel) mob.level(), 1, 6, 120, ModParticles.LONG_ENTROPY.get(), 0.25);
        ParticleProvider.GatherParticle(mob.position(), (ServerLevel) mob.level(), 1.5, 6, 120, ModParticles.LONG_ENTROPY.get(), 0.25);

    }
    public static void Skill3(Mob mob) {
        if (Devil.DevilSkill3Flag && mob.getHealth() < mob.getMaxHealth() * 0.25) {
            Devil.DevilSkill3Flag = false;
            mob.heal(mob.getMaxHealth() * 0.75f);
            ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) mob.level(), 1, 1, 120, ModParticles.LONG_ENTROPY.get(), 1);
            ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) mob.level(), 1.5, 1, 120, ModParticles.LONG_ENTROPY.get(), 1);
        }
    }
    public static void Skill4(Mob mob, List<Player> playerList, int difficulty) {
        if (Devil.DevilSkill4Flag && mob.getHealth() < mob.getMaxHealth() * 0.2) {
            Devil.DevilSkill4Flag = false;
            playerList.forEach(player -> {
                if (player.distanceTo(mob) < 50) {
                    Compute.Damage.ManaDamageToPlayer(mob,player,(DevilBaseAttack) * difficulty * 0.5);
                }
            });
            ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) mob.level(), 1, 1, 120, ModParticles.LONG_ENTROPY.get(), 1);
            ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) mob.level(), 1.5, 1, 120, ModParticles.LONG_ENTROPY.get(), 1);
        }
    }
}












