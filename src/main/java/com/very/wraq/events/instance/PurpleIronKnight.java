package com.very.wraq.events.instance;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.util.struct.Boss2Damage;
import com.very.wraq.common.util.struct.Instance;
import com.very.wraq.common.util.struct.PlayerTeam;
import com.very.wraq.events.core.LoginInEvent;
import com.very.wraq.process.func.MobEffectAndDamageMethods;
import com.very.wraq.process.func.item.InventoryOperation;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber
public class PurpleIronKnight {
    public static ServerBossEvent BossInfo;

    @SubscribeEvent
    public static void PurpleIronKnightEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {

            int InstanceIndex = 11;
            double MaxHealth = 5 * Math.pow(10, 8); // 生命值
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
                                    new ClientboundSetTitleTextPacket(Component.literal("紫晶骑士").withStyle(CustomStyle.styleOfPurpleIron));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("在十分钟内击杀紫晶骑士!").withStyle(ChatFormatting.RED));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        } else {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("紫晶骑士").withStyle(CustomStyle.styleOfPurpleIron));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("做好准备，离Boss出现还有 " + (10 - instanceTick / 20) + " 秒!"));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        }

                        if (instanceTick < 200) {
                            List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(instance.getTeleportPosition(), 70, 70, 70));
                            list.removeIf(mob -> mob.position().distanceTo(instance.getTeleportPosition()) < 32);
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
                    Compute.setMobCustomName(entity, ModItems.MobArmorPurpleIronKnightHelmet.get(),
                            Component.literal("紫晶骑士").withStyle(CustomStyle.styleOfPurpleIron));

                    entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(MaxHealth * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75));
                    entity.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(15000);
                    entity.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4);
                    entity.setHealth(entity.getMaxHealth());
                    entity.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorPurpleIronKnightHelmet.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorPurpleIronKnightChest.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorPurpleIronKnightLeggings.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorPurpleIronKnightBoots.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.MAINHAND, ModItems.PurpleIronSword.get().getDefaultInstance());

                    entity.moveTo(instance.getTeleportPosition());
                    level.addFreshEntity(entity);

                    BossInfo = (ServerBossEvent) (new ServerBossEvent(entity.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
                    playerListGetByName.forEach(player -> {
                        BossInfo.addPlayer((ServerPlayer) player);
                    });

                    Utils.PurpleIronKnightDamageList.clear();
                }

                if (instanceTick > 200 && instance.getMobList().get(0) != null && BossInfo != null) {
                    Mob mob = instance.getMobList().get(0);
                    BossInfo.setProgress(mob.getHealth() / mob.getMaxHealth());
                    if (mob.isAlive()) Element.ElementProvider(mob, Element.stone, 3);
                }

                if (instanceTick > 200 && instance.getMobList().get(0) != null) {
                    Mob mob = instance.getMobList().get(0);
                    mob.removeAllEffects();
                    if (instanceTick % 100 == 0) for (int i = 0; i < 10; i++)
                        LineAttack(mob, instance.getTeleportPosition(), 32, 10000 * difficultyEnhanceRate, 1);
                    if (instanceTick % 100 == 50) {
                        for (int i = 0; i < 10; i++)
                            CircleAttack(mob, instance.getTeleportPosition(), 4, 10000 * difficultyEnhanceRate, 1);
                        playerListGetByName.forEach(player -> {
                            MobEffectAndDamageMethods.DelayCircleParticleAttack(mob, player.position(), 4, 40, 10000 * difficultyEnhanceRate, 1,
                                    ModParticles.PurpleIronOneTick.get(), ModParticles.PurpleIronBig.get());
                        });
                    }
                } // AI

                MutableComponent difficulty = Component.literal("");
                switch (instance.getDifficulty()) {
                    case 0 -> difficulty = Component.literal("一般").withStyle(ChatFormatting.GREEN);
                    case 1 -> difficulty = Component.literal("困难").withStyle(ChatFormatting.LIGHT_PURPLE);
                    case 2 -> difficulty = Component.literal("炼狱").withStyle(ChatFormatting.RED);
                }

                // 击杀奖励
                if (instanceTick >= 200 && instance.getMobList().get(0) != null && Utils.instanceKillCount[InstanceIndex] >= 1) {
                    Utils.instanceKillCount[InstanceIndex] = 0;
                    playerListGetByName.forEach(player -> {
                        singleRewardToPlayer(player, difficultyEnhanceRate, playerNum, false);
                    });

                    Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("队伍:").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(playerTeam.getTeamName() + " 用时:").withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal(String.format("%.2f", (instanceTick - 200) * 0.05) + "s ").withStyle(CustomStyle.styleOfBloodMana)).
                                    append(Component.literal("成功挑战了").withStyle(ChatFormatting.WHITE)).
                                    append(instance.getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                    append(difficulty));

                    playerListGetByName.forEach(player -> {
                        Compute.sendFormatMSG(player, Component.literal("副本").withStyle(ChatFormatting.RED),
                                Component.literal("  征讨伤害排名如下：").withStyle(ChatFormatting.WHITE));

                        Utils.PurpleIronKnightDamageList.sort(Comparator.comparing(Boss2Damage::getDamage).reversed());
                        AtomicInteger index = new AtomicInteger(1);

                        Utils.PurpleIronKnightDamageList.forEach(boss2Damage -> {
                            if (boss2Damage.getPlayer() != null) {
                                Player player1 = boss2Damage.getPlayer();
                                double damage = boss2Damage.getDamage();
                                if (playerListGetByName.size() == 1) {
                                    if (damage / (MaxHealth * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75)) >= 0.9
                                            && (instanceTick - 200) <= 600) {
                                        CompoundTag data = player1.getPersistentData();
                                        if (data.getInt(StringUtils.PlayerInstanceProgress) < 7) {
                                            data.putInt(StringUtils.PlayerInstanceProgress, 7);
                                            Compute.formatBroad(level, Component.literal("试炼").withStyle(ChatFormatting.RED),
                                                    Component.literal("").withStyle(ChatFormatting.WHITE).
                                                            append(player1.getDisplayName()).
                                                            append(Component.literal(" 完成了 ").withStyle(ChatFormatting.WHITE)).
                                                            append(instance.getInstanceName()).
                                                            append(Component.literal(" 试炼!").withStyle(ChatFormatting.WHITE)));
                                        }
                                    }
                                }
                                Compute.sendFormatMSG(player, Component.literal(index + ".").withStyle(ChatFormatting.RED),
                                        Component.literal(" ").withStyle(ChatFormatting.WHITE).
                                                append(player1.getDisplayName()).
                                                append(Component.literal("  DMG:" + damage + "[" + String.format("%.2f", damage * 100 / (MaxHealth * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75))) + "%]").withStyle(ChatFormatting.WHITE)));
                                index.incrementAndGet();
                            }
                        });
                    });

                    playerListGetByName.forEach(player -> {
                        CompoundTag data = player.getPersistentData();
                        data.putInt(StringUtils.InstanceTimes.PurpleKnight, data.getInt(StringUtils.InstanceTimes.PurpleKnight) + 1);
                    });

                    instance.reset();
                    Utils.PurpleIronKnightDamageList.clear();
                    Compute.EndTp(playerListGetByName, new Vec3(2099, 86, 1219));
                    if (BossInfo != null) playerListGetByName.forEach(player -> {
                        BossInfo.removePlayer((ServerPlayer) player);
                    });
                    BossInfo = null;
                }

                // 失败
                if (instanceTick == 12200 || playerListGetByName.size() == 0 || instance.getDeadTimes() >= playerListGetByName.size()) {
                    Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("队伍：" + playerTeam.getTeamName()).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("挑战副本:").withStyle(ChatFormatting.WHITE)).
                                    append(instance.getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                    append(difficulty).
                                    append(Component.literal(" 失败。").withStyle(ChatFormatting.WHITE)));

                    Utils.instanceKillCount[InstanceIndex] = 0;
                    instance.reset();
                    Utils.PurpleIronKnightDamageList.clear();
                    Compute.EndTp(playerListGetByName, new Vec3(2099, 86, 1219));
                    if (BossInfo != null) playerListGetByName.forEach(player -> {
                        BossInfo.removePlayer((ServerPlayer) player);
                    });
                    BossInfo = null;
                }
            }
        }
    }

    public static void LineAttack(Mob mob, Vec3 center, double radius, double damage, int type) {
        Random random = new Random();
        double angle0 = random.nextDouble() * 2 * Math.PI;
        double angle1 = angle0 + random.nextDouble(0.3, 0.7) * 2 * Math.PI;
        double offsetX = radius * Math.cos(angle0);
        double offsetZ = radius * Math.sin(angle0);
        Vec3 dot1 = center.add(offsetX, 0, offsetZ);
        offsetX = radius * Math.cos(angle1);
        offsetZ = radius * Math.sin(angle1);
        Vec3 dot2 = center.add(offsetX, 0, offsetZ);
        MobEffectAndDamageMethods.DelayLineParticleAttack(mob, dot1, dot2, 1, 40, damage, type,
                ModParticles.PurpleIronOneTick.get(), ModParticles.PurpleIronBig.get());
    }

    public static void CircleAttack(Mob mob, Vec3 center, double radius, double damage, int type) {
        Random random = new Random();
        Vec3 pos = center.add(random.nextDouble(64) - 32, 0, random.nextDouble(64) - 32);
        MobEffectAndDamageMethods.DelayCircleParticleAttack(mob, pos, radius, 40, damage, type,
                ModParticles.PurpleIronOneTick.get(), ModParticles.PurpleIronBig.get());
    }

    public static void SkillBaseEffectProvide(Mob mob, Player player) {
        if (!mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorPurpleIronKnightHelmet.get())) return;
        MobEffectAndDamageMethods.PlayerDamageDecreaseProvide(mob, player, 0.95, 100, ModItems.PurpleIronBud3.get().getDefaultInstance());

        // effects
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 3, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 3, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 3, false, false));
    }

    public static void SkillAdditionEffectProvide(Mob mob) {
        if (!mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorPurpleIronKnightHelmet.get())) return;
        List<Player> players = mob.level().getEntitiesOfClass(Player.class, AABB.ofSize(mob.position(), 64, 64, 64));
        players.forEach(player -> {
            ClientboundSoundPacket clientboundSoundPacket =
                    new ClientboundSoundPacket(Holder.direct(SoundEvents.AMETHYST_BLOCK_HIT), SoundSource.MASTER, mob.getX(), mob.getY(), mob.getZ(), 1, 1, 1);
            ((ServerPlayer) player).connection.send(clientboundSoundPacket);
        });
    }

    public static String RewardGetLimit = "PurpleIronKnightRewardGetLimit";
    public static int RewardGetTimes = 24;

    public static boolean PlayerRewardGetJudge(Player player) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(RewardGetLimit) || data.getInt(RewardGetLimit) < RewardGetTimes) {
            data.putInt(RewardGetLimit, data.getInt(RewardGetLimit) + 1);
            Compute.sendFormatMSG(player, Component.literal("紫晶").withStyle(CustomStyle.styleOfPurpleIron),
                    Component.literal("当日可用奖励获取次数: ").withStyle(ChatFormatting.WHITE).
                            append(Component.literal(data.getInt(RewardGetLimit) + "/" + RewardGetTimes).withStyle(CustomStyle.styleOfPurpleIron)));
            return true;
        }
        Compute.sendFormatMSG(player, Component.literal("紫晶").withStyle(CustomStyle.styleOfPurpleIron),
                Component.literal("当日可用奖励获取次数已耗尽。").withStyle(ChatFormatting.WHITE));
        return false;
    }

    public static void RefreshRewardGetTimes(Player player) {
        player.getPersistentData().putInt(RewardGetLimit, 0);
    }

    public static void singleRewardToPlayer(Player player, int difficultyEnhanceRate, int playerNum, boolean isMopUp) {
        Level level = player.level();
        Random random = new Random();
        if (PlayerRewardGetJudge(player)) {
            InventoryOperation.itemStackGive(player, new ItemStack(ModItems.PurpleIronBud1.get(), difficultyEnhanceRate * 2));

            if (random.nextDouble() <= 0.01) {
                ItemStack itemStack = null;
                switch (random.nextInt(3)) {
                    case 0 -> itemStack = ModItems.PurpleIronSword.get().getDefaultInstance();
                    case 1 -> itemStack = ModItems.PurpleIronBow.get().getDefaultInstance();
                    case 2 -> itemStack = ModItems.PurpleIronSceptre.get().getDefaultInstance();
                }

                Compute.formatBroad(level, Component.literal("紫晶骑士").withStyle(CustomStyle.styleOfPurpleIron),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 获得了 ").withStyle(ChatFormatting.WHITE)).
                                append(itemStack.getDisplayName()));
                InventoryOperation.itemStackGive(player, itemStack);
            }

            if (random.nextDouble() <= 0.1) {
                ItemStack itemStack = new ItemStack(ModItems.PurpleIronBud2.get(), 1);
                Compute.formatBroad(level, Component.literal("紫晶骑士").withStyle(CustomStyle.styleOfPurpleIron),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 获得了 ").withStyle(ChatFormatting.WHITE)).
                                append(itemStack.getDisplayName()));
                InventoryOperation.itemStackGive(player, itemStack);
            }

            if (!isMopUp) {
                if (random.nextDouble() <= 0.025 * (playerNum - 1) * difficultyEnhanceRate) {
                    Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal("你通过组队挑战副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                                    append(ModItems.PurpleIronBud1.get().getDefaultInstance().getDisplayName()));
                    InventoryOperation.itemStackGive(player, new ItemStack(ModItems.PurpleIronBud1.get(), 4));
                }
            }

            if (LoginInEvent.playerDailyInstanceReward(player, 11)) {
                Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("每日首次通关副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                                append(ModItems.PurpleIronBud2.get().getDefaultInstance().getDisplayName()));
                InventoryOperation.itemStackGive(player, new ItemStack(ModItems.PurpleIronBud2.get(), 1));
            }
        }
    }
}












