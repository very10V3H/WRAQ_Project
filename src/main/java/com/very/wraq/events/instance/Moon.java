package com.very.wraq.events.instance;

import com.very.wraq.common.Compute;
import com.very.wraq.common.fast.Tick;
import com.very.wraq.common.registry.ModEntityType;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.util.struct.Boss2Damage;
import com.very.wraq.common.util.struct.Instance;
import com.very.wraq.common.util.struct.PlayerTeam;
import com.very.wraq.events.core.LoginInEvent;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.process.func.damage.Damage;
import com.very.wraq.process.func.item.InventoryOperation;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
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

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber
public class Moon {
    public static ServerBossEvent BossInfo1;
    public static ServerBossEvent BossInfo2;

    @SubscribeEvent
    public static void MoonEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            int InstanceIndex = 7;
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
                Style style = CustomStyle.styleOfMoon;
                playerList.forEach(player -> {
                    if (event.level.getServer().getPlayerList().getPlayerByName(player.getName().getString()) != null)
                        playerListGetByName.add(event.level.getServer().getPlayerList().getPlayerByName(player.getName().getString()));
                });
                int playerNum = playerListGetByName.size();
                double MaxHealth = 1000000 * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75);
                // 挑战前提示
                if (instanceTick <= 200) {
                    if (instanceTick % 20 == 0) {
                        if (instanceTick == 200) {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("尘月宫").withStyle(style));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("在十分钟内击败阿尔忒弥斯!").withStyle(ChatFormatting.RED));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        } else {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("尘月宫").withStyle(style));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("做好准备，离Boss出现还有 " + (10 - instanceTick / 20) + " 秒!"));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        }
                        if (instanceTick < 200) {
                            List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(instance.getTeleportPosition(), 100, 100, 100));
                            list.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
                        }
                    }
                }

                // 怪物生成
                if (instanceTick == 200) {

                    instance.setMobList(new ArrayList<>() {{
                        add(new Stray(EntityType.STRAY, level));
                        add(new Stray(EntityType.STRAY, level));
                    }});

                    Mob entity = instance.getMobList().get(0);
                    MobSpawn.setMobCustomName(entity, ModItems.MobArmorMoonAttack.get(),
                            Component.literal("阿尔忒弥斯 - 明镜").withStyle(style));

                    entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(MaxHealth);
                    entity.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6000);
                    entity.setHealth(entity.getMaxHealth());
                    entity.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorMoonAttack.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorMoonChest.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorMoonLeggings.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorMoonBoots.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.OFFHAND, ModItems.MoonShield.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.MAINHAND, ModItems.MoonKnife.get().getDefaultInstance());

                    entity.moveTo(153, 183, 1593);
                    level.addFreshEntity(entity);
                    BossInfo1 = (ServerBossEvent) (new ServerBossEvent(entity.getDisplayName(), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
                    playerListGetByName.forEach(player -> {
                        BossInfo1.addPlayer((ServerPlayer) player);
                    });

                    Mob entity1 = instance.getMobList().get(1);
                    MobSpawn.setMobCustomName(entity1, ModItems.MobArmorMoonMana.get(),
                            Component.literal("阿尔忒弥斯 - 天镜").withStyle(style));

                    entity1.getAttribute(Attributes.MAX_HEALTH).setBaseValue(MaxHealth);
                    entity1.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6000);
                    entity1.setHealth(entity1.getMaxHealth());
                    entity1.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorMoonMana.get().getDefaultInstance());
                    entity1.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorMoonChest.get().getDefaultInstance());
                    entity1.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorMoonLeggings.get().getDefaultInstance());
                    entity1.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorMoonBoots.get().getDefaultInstance());
                    entity1.setItemSlot(EquipmentSlot.MAINHAND, Items.BOW.getDefaultInstance());

                    entity1.moveTo(179, 178, 1586);
                    level.addFreshEntity(entity1);
                    BossInfo2 = (ServerBossEvent) (new ServerBossEvent(entity1.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
                    playerListGetByName.forEach(player -> {
                        BossInfo2.addPlayer((ServerPlayer) player);
                    });
                    Utils.MoonDamageList.clear();
                }

                if (instanceTick > 200 && instance.getMobList().get(0) != null && BossInfo1 != null) {
                    Mob mob = instance.getMobList().get(0);
                    BossInfo1.setProgress(mob.getHealth() / mob.getMaxHealth());
                }

                if (instanceTick > 200 && instance.getMobList().get(1) != null && BossInfo2 != null) {
                    Mob mob = instance.getMobList().get(1);
                    BossInfo2.setProgress(mob.getHealth() / mob.getMaxHealth());
                }

                if (instanceTick > 200 && instance.getMobList().get(0) != null && instance.getMobList().get(1) != null) {
                    Mob AttackMob = instance.getMobList().get(0);
                    Mob ManaMob = instance.getMobList().get(1);
                    if (instanceTick % 280 == 0) {
                        Skill1(AttackMob, ManaMob, playerListGetByName);
                    }
                    if (instanceTick % 280 == 140) {
                        Skill2(AttackMob, ManaMob, playerListGetByName);
                    }
                    Skill3(AttackMob, ManaMob, playerListGetByName);
                    IfFall(AttackMob, ManaMob, playerListGetByName, 175, instance.getTeleportPosition());
                } // AI

                MutableComponent difficulty = Component.literal("");
                switch (instance.getDifficulty()) {
                    case 0 -> difficulty = Component.literal("一般").withStyle(ChatFormatting.GREEN);
                    case 1 -> difficulty = Component.literal("困难").withStyle(ChatFormatting.LIGHT_PURPLE);
                    case 2 -> difficulty = Component.literal("炼狱").withStyle(ChatFormatting.RED);
                }

                // 击杀奖励
                if (instanceTick >= 200 && instance.getMobList().get(0) != null
                        && instance.getMobList().get(1) != null && Utils.instanceKillCount[InstanceIndex] >= 2) {
                    Utils.instanceKillCount[InstanceIndex] = 0;
                    playerListGetByName.forEach(player -> {
                        SingleRewardToPlayer(player, difficultyEnhanceRate, playerNum, false);
                    }); // 物品奖励

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

                        Utils.MoonDamageList.sort(Comparator.comparing(Boss2Damage::getDamage).reversed());
                        AtomicInteger index = new AtomicInteger(1);

                        Utils.MoonDamageList.forEach(boss2Damage -> {
                            if (boss2Damage.getPlayer() != null) {
                                Player player1 = boss2Damage.getPlayer();
                                double damage = boss2Damage.getDamage();
                                if (playerListGetByName.size() == 1) {
                                    if (damage / (MaxHealth * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75)) >= 0.9
                                            && (instanceTick - 200) <= 600) {
                                        CompoundTag data = player1.getPersistentData();
                                        if (data.getInt(StringUtils.PlayerInstanceProgress) < 8) {
                                            data.putInt(StringUtils.PlayerInstanceProgress, 8);
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
                                                append(Component.literal("  DMG:" + damage + "[" + String.format("%.2f", damage * 100 / (MaxHealth * 2)) + "%]").withStyle(ChatFormatting.WHITE)));
                                index.incrementAndGet();
                            }
                        });
                    });

                    playerListGetByName.forEach(player -> {
                        CompoundTag data = player.getPersistentData();
                        data.putInt(StringUtils.InstanceTimes.Moon, data.getInt(StringUtils.InstanceTimes.Moon) + 1);
                    });
                    instance.reset();
                    Utils.MoonDamageList.clear();
                    Compute.EndTp(playerListGetByName, new Vec3(184, 125, 1513));
                    if (BossInfo1 != null) playerListGetByName.forEach(player -> {
                        BossInfo1.removePlayer((ServerPlayer) player);
                    });
                    BossInfo1 = null;
                    if (BossInfo2 != null) playerListGetByName.forEach(player -> {
                        BossInfo2.removePlayer((ServerPlayer) player);
                    });
                    BossInfo2 = null;
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
                    Utils.MoonDamageList.clear();
                    Compute.EndTp(playerListGetByName, new Vec3(184, 125, 1513));
                    if (BossInfo1 != null) playerListGetByName.forEach(player -> {
                        BossInfo1.removePlayer((ServerPlayer) player);
                    });
                    BossInfo1 = null;
                    if (BossInfo2 != null) playerListGetByName.forEach(player -> {
                        BossInfo2.removePlayer((ServerPlayer) player);
                    });
                    BossInfo2 = null;
                }
            }
        }
    }

    public static void Skill1(Mob AttackMob, Mob ManaMob, List<Player> playerList) {
        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                new ClientboundSetTitleTextPacket(Component.literal("潮汐之源").withStyle(CustomStyle.styleOfMoon));
        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                new ClientboundSetSubtitleTextPacket(Component.literal(""));
        playerList.forEach(player -> ((ServerPlayer) player).connection.send(clientboundSetTitleTextPacket));
        playerList.forEach(player -> ((ServerPlayer) player).connection.send(clientboundSetSubtitleTextPacket));
        if (AttackMob.isAlive()) {
            Compute.RepelPlayer(AttackMob, AttackMob.position(), 6, 3, 3);
            ParticleProvider.DisperseParticle(AttackMob.position(), (ServerLevel) AttackMob.level(), 1, 1, 120, ModParticles.LONG_LIGHTNINGISLAND.get(), 1);
            ParticleProvider.DisperseParticle(AttackMob.position(), (ServerLevel) AttackMob.level(), 1.5, 1, 120, ModParticles.LONG_LIGHTNINGISLAND.get(), 1);
        }
        if (ManaMob.isAlive()) {
            for (int i = 0; i < 16; i++) {
                ManaArrow manaArrow = new ManaArrow(ModEntityType.NEW_ARROW.get(), ManaMob, ManaMob.level());
                manaArrow.shootFromRotation(ManaMob, ManaMob.getXRot(), ManaMob.getYRot() + 360 * i / 16.0f, 1, 1.5f, 1);
                ManaMob.level().addFreshEntity(manaArrow);
            }
            ParticleProvider.DisperseParticle(ManaMob.position(), (ServerLevel) ManaMob.level(), 1, 1, 120, ModParticles.LONG_LIGHTNINGISLAND.get(), 1);
            ParticleProvider.DisperseParticle(ManaMob.position(), (ServerLevel) ManaMob.level(), 1.5, 1, 120, ModParticles.LONG_LIGHTNINGISLAND.get(), 1);

        }
    }

    public static void Skill2(Mob AttackMob, Mob ManaMob, List<Player> playerList) {
        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                new ClientboundSetTitleTextPacket(Component.literal("阴晴圆缺").withStyle(CustomStyle.styleOfMoon));
        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                new ClientboundSetSubtitleTextPacket(Component.literal(""));
        playerList.forEach(player -> ((ServerPlayer) player).connection.send(clientboundSetTitleTextPacket));
        playerList.forEach(player -> ((ServerPlayer) player).connection.send(clientboundSetSubtitleTextPacket));
        if (playerList.size() == 0) return;
        Player HealthHighPlayer = playerList.get(0);
        Player HealthLowPlayer = playerList.get(0);
        double HighNum = 0;
        double LowNum = 1;
        for (Player player : playerList) {
            double rate = player.getHealth() / player.getMaxHealth();
            if (rate > HighNum) {
                HighNum = rate;
                HealthHighPlayer = player;
            }
            if (rate < LowNum) {
                LowNum = rate;
                HealthLowPlayer = player;
            }
        }
        if (AttackMob.isAlive()) {
            Damage.manaDamageToPlayer(AttackMob, HealthLowPlayer, HealthLowPlayer.getMaxHealth() * 0.5);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthLowPlayer, 1, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthLowPlayer, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthLowPlayer, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthLowPlayer, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthLowPlayer, 0, 0.4, 8, ParticleTypes.WITCH, 0);

        }
        if (ManaMob.isAlive()) {
            Damage.manaDamageToPlayer(ManaMob, HealthHighPlayer, HealthHighPlayer.getMaxHealth() * 0.5);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthHighPlayer, 1, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthHighPlayer, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthHighPlayer, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthHighPlayer, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(HealthHighPlayer, 0, 0.4, 8, ParticleTypes.WITCH, 0);
        }
    }

    public static void Skill3(Mob AttackMob, Mob ManaMob, List<Player> playerList) {

        if (AttackMob.isAlive() && ManaMob.isAlive()) {
            double attackMobRate = AttackMob.getHealth() / AttackMob.getMaxHealth();
            double manaMobRate = ManaMob.getHealth() / ManaMob.getMaxHealth();
            if (Math.abs(attackMobRate - manaMobRate) > 0.5) {
                ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                        new ClientboundSetTitleTextPacket(Component.literal("月食").withStyle(CustomStyle.styleOfMoon));
                ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                        new ClientboundSetSubtitleTextPacket(Component.literal(""));
                playerList.forEach(player -> ((ServerPlayer) player).connection.send(clientboundSetTitleTextPacket));
                playerList.forEach(player -> ((ServerPlayer) player).connection.send(clientboundSetSubtitleTextPacket));
                AttackMob.heal(AttackMob.getMaxHealth());
                ManaMob.heal(ManaMob.getMaxHealth());
                ParticleProvider.EntityEffectVerticleCircleParticle(AttackMob, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(AttackMob, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(AttackMob, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(AttackMob, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(AttackMob, 0, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(ManaMob, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(ManaMob, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(ManaMob, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(ManaMob, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(ManaMob, 0, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                playerList.forEach(player -> {
                    Damage.manaDamageToPlayer(ManaMob, player, player.getMaxHealth() * 0.25);
                    Damage.manaDamageToPlayer(AttackMob, player, player.getMaxHealth() * 0.25);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player, 0, 0.4, 8, ParticleTypes.WITCH, 0);
                });
            }
        }
    }

    public static void IfFall(Mob AttackMob, Mob ManaMob, List<Player> playerList, double YLimit, Vec3 Pos) {
        if (AttackMob.isAlive() && AttackMob.getY() < YLimit) AttackMob.moveTo(Pos);
        if (ManaMob.isAlive() && ManaMob.getY() < YLimit) ManaMob.moveTo(Pos);
        playerList.forEach(player -> {
            if (player.position().distanceTo(Pos) < 150 && player.getY() < YLimit) {
                Damage.manaDamageToPlayer(ManaMob, player, player.getMaxHealth() * 0.25);
                Damage.manaDamageToPlayer(AttackMob, player, player.getMaxHealth() * 0.25);
                ((ServerPlayer) player).teleportTo(player.getServer().getLevel(Level.OVERWORLD), Pos.x, Pos.y, Pos.z, 179, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 0, 0.4, 8, ParticleTypes.WITCH, 0);
            }
        });
    }

    public static WeakHashMap<Player, Integer> attackImmuneMSGMap = new WeakHashMap<>();

    public static boolean isMoonAttackImmune(Player player, Mob mob) {
        if (player.isCreative()) return false;
        if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorMoonAttack.get())) {
            if (player.distanceTo(mob) > 6 && attackImmuneMSGMap.getOrDefault(player, 0) < Tick.get()) {
                attackImmuneMSGMap.put(player, Tick.get() + 100);
                Compute.sendFormatMSG(player, Component.literal("尘月宫").withStyle(CustomStyle.styleOfMoon),
                        Component.literal("似乎这个距离对阿尔忒弥斯的伤害将会减半。").withStyle(CustomStyle.styleOfMoon1));
                return true;
            }
        }
        return false;
    }

    public static WeakHashMap<Player, Integer> manaImmuneMSGMap = new WeakHashMap<>();

    public static boolean isMoonManaImmune(Player player, Mob mob) {
        if (player.isCreative()) return false;
        if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorMoonMana.get())) {
            if (player.distanceTo(mob) < 6 && manaImmuneMSGMap.getOrDefault(player, 0) < Tick.get()) {
                manaImmuneMSGMap.put(player, Tick.get() + 100);
                Compute.sendFormatMSG(player, Component.literal("尘月宫").withStyle(CustomStyle.styleOfMoon),
                        Component.literal("似乎这个距离对阿尔忒弥斯的伤害将会减半。").withStyle(CustomStyle.styleOfMoon1));

                return true;
            }
        }
        return false;
    }

    public static void SingleRewardToPlayer(Player player, int difficultyEnhanceRate, int playerNum, boolean isMopUp) {
        InventoryOperation.itemStackGive(player, new ItemStack(ModItems.MoonLoot.get(), difficultyEnhanceRate));
        if (!isMopUp) {
            Random random = new Random();
            if (random.nextDouble() <= 0.025 * (playerNum - 1) * difficultyEnhanceRate) {
                Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("你通过组队挑战副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                                append(ModItems.MoonLoot.get().getDefaultInstance().getDisplayName()));
                InventoryOperation.itemStackGive(player, new ItemStack(ModItems.MoonLoot.get(), 2));
            }
        }

        if (LoginInEvent.playerDailyInstanceReward(player, 7)) {
            Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("每日首次通关副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                            append(ModItems.MoonLoot.get().getDefaultInstance().getDisplayName()));
            InventoryOperation.itemStackGive(player, new ItemStack(ModItems.MoonLoot.get(), 24));
        }

    }
}












