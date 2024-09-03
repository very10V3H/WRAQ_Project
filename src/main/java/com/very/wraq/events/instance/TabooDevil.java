package com.very.wraq.events.instance;

import com.very.wraq.events.core.LoginInEvent;
import com.very.wraq.events.fight.MonsterAttackEvent;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.struct.Boss2Damage;
import com.very.wraq.common.util.struct.Instance;
import com.very.wraq.common.util.struct.PlayerTeam;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber
public class TabooDevil {
    public static ServerBossEvent BossInfo1;
    public static ServerBossEvent BossInfo2;
    public static double DevilAttackUp = 0;
    public static boolean DevilSkill3Flag = true;
    public static boolean DevilSkill4Flag = true;
    public static double DevilBaseAttack = 3000;
    public static int Stage = 0;
    public static boolean IsTaboo = false;

    @SubscribeEvent
    public static void DevilEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {

            int InstanceIndex = 8;
            double MaxHealth = 2000000;
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
                                    new ClientboundSetTitleTextPacket(Component.literal("旧世复生魔王").withStyle(CustomStyle.styleOfBloodMana));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("在十分钟内击杀旧世复生魔王!").withStyle(ChatFormatting.RED));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        } else {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("旧世复生魔王").withStyle(CustomStyle.styleOfBloodMana));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("做好准备，离Boss出现还有 " + (10 - instanceTick / 20) + " 秒!"));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        }
                        if (instanceTick < 200) {
                            List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(instance.getTeleportPosition(), 150, 150, 150));
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
                            Component.literal("旧世复生魔王").withStyle(CustomStyle.styleOfBloodMana));

                    entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(MaxHealth * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75));
                    entity.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(DevilBaseAttack);
                    entity.setHealth(entity.getMaxHealth());
                    entity.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorDevilHelmet.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorEarthManaChest.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorEarthManaLeggings.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorEarthManaBoots.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.MAINHAND, ModItems.manaKnife.get().getDefaultInstance());

                    entity.moveTo(1432, 80, 1170);
                    level.addFreshEntity(entity);
                    DevilSkill3Flag = true;
                    DevilSkill4Flag = true;
                    Stage = 0;
                    IsTaboo = false;
                    BossInfo1 = (ServerBossEvent) (new ServerBossEvent(entity.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
                    playerListGetByName.forEach(player -> {
                        BossInfo1.addPlayer((ServerPlayer) player);
                    });

                    Utils.TabooDevilDamageList.clear();
                }

                if (instanceTick > 200 && instance.getMobList().get(0) != null && BossInfo1 != null) {
                    Mob mob = instance.getMobList().get(0);
                    BossInfo1.setProgress(mob.getHealth() / mob.getMaxHealth());
                }

                if (instanceTick > 200 && instance.getMobList().get(0) != null && BossInfo2 != null) {
                    Mob mob = instance.getMobList().get(0);
                    BossInfo2.setProgress(mob.getHealth() / mob.getMaxHealth());
                }

                if (instanceTick > 200 && instance.getMobList().get(0) != null) {

                    Mob mob = instance.getMobList().get(0);
                    if (mob.isBaby()) {
                        int Frequency = DevilSkill3Flag ? 160 : 80;
                        if (instanceTick % Frequency == 0) {
                            Random random = new Random();
                            if (random.nextDouble() < 0.5) {
                                Skill1(mob, playerListGetByName, difficultyEnhanceRate);
                            } else Skill2(mob, playerListGetByName, difficultyEnhanceRate);
                        }
                        Skill3(mob);
                        Skill4(mob, playerListGetByName, difficultyEnhanceRate);

                        if (mob.getHealth() / mob.getMaxHealth() < 0.1 && MobNearbyHasTabooPiece(mob)) {
                            Mob entity = new Zombie(EntityType.HUSK, level);
                            Compute.SetMobCustomName(entity, ModItems.MobArmorTabooDevil.get(),
                                    Component.literal("新世禁法魔物").withStyle(CustomStyle.styleOfBloodMana));

                            entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(MaxHealth * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75 * 2));
                            entity.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(DevilBaseAttack);
                            entity.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4);
                            entity.setHealth(entity.getMaxHealth());

                            entity.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorTabooDevil.get().getDefaultInstance());
                            entity.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorEarthManaChest.get().getDefaultInstance());
                            entity.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorEarthManaLeggings.get().getDefaultInstance());
                            entity.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorEarthManaBoots.get().getDefaultInstance());
                            entity.setItemSlot(EquipmentSlot.MAINHAND, ModItems.manaKnife.get().getDefaultInstance());
                            entity.setItemSlot(EquipmentSlot.OFFHAND, ModItems.ManaShield.get().getDefaultInstance());

                            entity.moveTo(mob.position().add(0, 1, 0));
                            mob.remove(Entity.RemovalReason.KILLED);
                            instance.getMobList().set(0, entity);
                            mob = instance.getMobList().get(0);

                            level.addFreshEntity(mob);
                            RangeManaDamage(mob, playerListGetByName, 1500 * difficultyEnhanceRate, 20);
                            Compute.RepelPlayer(mob, mob.position(), 6, 3, 3);
                            Stage = 3;
                            IsTaboo = true;

                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("新世禁法魔物").withStyle(CustomStyle.styleOfBloodMana));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("强大的能量已被魔王吸收!").withStyle(CustomStyle.styleOfBloodMana));
                            ClientboundSoundPacket clientboundSoundPacket =
                                    new ClientboundSoundPacket(Holder.direct(SoundEvents.ENDER_DRAGON_GROWL), SoundSource.AMBIENT, mob.getX(), mob.getY(), mob.getZ(), 0.4f, 0.4f, 1);

                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                                serverPlayer.connection.send(clientboundSoundPacket);
                            });
                            BossInfo2 = (ServerBossEvent) (new ServerBossEvent(entity.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
                            playerListGetByName.forEach(player -> {
                                BossInfo2.addPlayer((ServerPlayer) player);
                            });
                            if (BossInfo1 != null) playerListGetByName.forEach(player -> {
                                BossInfo1.removePlayer((ServerPlayer) player);
                            });
                            BossInfo1 = null;
                        }
                    } else {
                        mob.removeAllEffects();
                        if (instanceTick % 20 == 0) {
                            RangeManaDamage(mob, playerListGetByName, 1000 * difficultyEnhanceRate, 20);
                            if (Stage > 0 && mob.getHealth() / mob.getMaxHealth() < 0.25 * Stage && DetectBlockNum(level) == 0) {
                                CreateBlock(level);

                                ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                        new ClientboundSetTitleTextPacket(Component.literal("逸散魔物方块已生成").withStyle(CustomStyle.styleOfBloodMana));
                                ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                        new ClientboundSetSubtitleTextPacket(Component.literal("摧毁魔物方块，阻止其为禁法魔物回复生命值！").withStyle(CustomStyle.styleOfBloodMana));
                                ClientboundSoundPacket clientboundSoundPacket =
                                        new ClientboundSoundPacket(Holder.direct(SoundEvents.GENERIC_EXPLODE), SoundSource.AMBIENT, mob.getX(), mob.getY(), mob.getZ(), 0.4f, 0.4f, 1);

                                playerList.forEach(player -> {
                                    ServerPlayer serverPlayer = (ServerPlayer) player;
                                    serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                    serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                                    serverPlayer.connection.send(clientboundSoundPacket);
                                });

                                Stage--;
                            }
                            if (DetectBlockNum(level) > 0) {
                                mob.heal((float) (mob.getMaxHealth() * 0.01 * DetectBlockNum(level))); // 根据方块数量治疗
                                Compute.ParticleEffectAddOnEntity(mob, ParticleTypes.COMPOSTER);
                                BlockParticle(level, mob);
                            }
                        }
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
                    Random random = new Random();
                    if (IsTaboo) {
                        playerListGetByName.forEach(player -> {
                            singleRewardToPlayer(player, difficultyEnhanceRate, playerNum, false);
                        });

                        Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                                Component.literal("队伍:").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(playerTeam.getTeamName() + " 用时:").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal((instanceTick - 200) / 20 + "s ").withStyle(CustomStyle.styleOfBloodMana)).
                                        append(Component.literal("成功挑战了 ").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal("禁法召唤 - ").withStyle(CustomStyle.styleOfBloodMana)).
                                        append(instance.getInstanceName()).
                                        append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                        append(difficulty));

                        playerListGetByName.forEach(player -> {
                            Compute.sendFormatMSG(player, Component.literal("副本").withStyle(ChatFormatting.RED),
                                    Component.literal("  征讨伤害排名如下：").withStyle(ChatFormatting.WHITE));

                            Utils.TabooDevilDamageList.sort(Comparator.comparing(Boss2Damage::getDamage).reversed());
                            AtomicInteger index = new AtomicInteger(1);

                            Utils.TabooDevilDamageList.forEach(boss2Damage -> {
                                if (boss2Damage.getPlayer() != null) {
                                    Player player1 = boss2Damage.getPlayer();
                                    double damage = boss2Damage.getDamage();
                                    if (playerListGetByName.size() == 1) {
                                        if (damage / (MaxHealth * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75)) >= 0.9
                                                && (instanceTick - 200) <= 600) {
                                            CompoundTag data = player1.getPersistentData();
                                            if (data.getInt(StringUtils.PlayerInstanceProgress) < 9) {
                                                data.putInt(StringUtils.PlayerInstanceProgress, 9);
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
                            data.putInt(StringUtils.InstanceTimes.Taboo, data.getInt(StringUtils.InstanceTimes.Taboo) + 1);
                        });
                        if (BossInfo2 != null) playerListGetByName.forEach(player -> {
                            BossInfo2.removePlayer((ServerPlayer) player);
                        });
                        BossInfo2 = null;
                    } else {
                        playerListGetByName.forEach(player -> {
                            if (random.nextDouble() <= 0.25 * difficultyEnhanceRate) {
                                Compute.itemStackGive(player, new ItemStack(ModItems.DevilLoot.get(), 1));
                            }
                            if (random.nextDouble() <= 0.025 * (playerNum - 1) * difficultyEnhanceRate) {
                                Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                                        Component.literal("你通过组队挑战副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                                                append(ModItems.DevilLoot.get().getDefaultInstance().getDisplayName()));
                                Compute.itemStackGive(player, new ItemStack(ModItems.DevilLoot.get(), 1));
                            }
                        });

                        Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                                Component.literal("队伍:").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(playerTeam.getTeamName() + " 用时:").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(String.format("%.2f", (instanceTick - 200) * 0.05) + "s ").withStyle(CustomStyle.styleOfBloodMana)).
                                        append(Component.literal("成功挑战了").withStyle(ChatFormatting.WHITE)).
                                        append(instance.getInstanceName()).
                                        append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                        append(difficulty));
                        if (BossInfo1 != null) playerListGetByName.forEach(player -> {
                            BossInfo1.removePlayer((ServerPlayer) player);
                        });
                        BossInfo1 = null;
                    }
                    instance.reset();
                    Utils.TabooDevilDamageList.clear();
                    DevilAttackUp = 0;
                    ResetBlock(level);
                    Compute.EndTp(playerListGetByName, new Vec3(1391, 71, 1169));

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
                    Utils.TabooDevilDamageList.clear();
                    DevilAttackUp = 0;
                    ResetBlock(level);
                    Compute.EndTp(playerListGetByName, new Vec3(1391, 71, 1169));
                    if (BossInfo1 != null) {
                        playerListGetByName.forEach(player -> {
                            BossInfo1.removePlayer((ServerPlayer) player);
                        });
                        BossInfo1 = null;
                    }
                    if (BossInfo2 != null) {
                        playerListGetByName.forEach(player -> {
                            BossInfo2.removePlayer((ServerPlayer) player);
                        });
                        BossInfo2 = null;
                    }
                }
            }
        }
    }

    public static void Skill1(Mob mob, List<Player> playerList, int difficulty) {
        TabooDevil.DevilAttackUp += playerList.size() * difficulty * 100;
        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                new ClientboundSetTitleTextPacket(Component.literal("魔源奔流").withStyle(CustomStyle.styleOfBloodMana));
        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                new ClientboundSetSubtitleTextPacket(Component.literal("魔王吸取大量法力值!").withStyle(ChatFormatting.RED));
        playerList.forEach(player -> {
            if (player.distanceTo(mob) < 50) {
                int ManaDecrease = difficulty * 100;
                if (Compute.PlayerCurrentManaNum(player) < ManaDecrease) {
                    ManaDecrease -= Compute.PlayerCurrentManaNum(player);
                    Compute.playerManaAddOrCost(player, -Compute.PlayerCurrentManaNum(player));
                    Compute.Damage.manaDamageToPlayer(mob, player, 30 * ManaDecrease);
                } else Compute.playerManaAddOrCost(player, -ManaDecrease);
                ((ServerPlayer) player).connection.send(clientboundSetTitleTextPacket);
                ((ServerPlayer) player).connection.send(clientboundSetSubtitleTextPacket);
            }
        });
        ParticleProvider.GatherParticle(mob.position(), (ServerLevel) mob.level(), 1, 6, 120, ModParticles.LONG_ENTROPY.get(), 0.25);
        ParticleProvider.GatherParticle(mob.position(), (ServerLevel) mob.level(), 1.5, 6, 120, ModParticles.LONG_ENTROPY.get(), 0.25);
    }

    public static void Skill2(Mob mob, List<Player> playerList, int difficulty) {
        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                new ClientboundSetTitleTextPacket(Component.literal("腥月之子").withStyle(CustomStyle.styleOfBloodMana));
        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                new ClientboundSetSubtitleTextPacket(Component.literal("魔王吸取大量生命值!").withStyle(ChatFormatting.RED));
        playerList.forEach(player -> {
            if (player.distanceTo(mob) < 50) {
                MonsterAttackEvent.monsterAttack(mob, player, 2000 * difficulty);
                mob.heal(2500000 * difficulty);
                ((ServerPlayer) player).connection.send(clientboundSetTitleTextPacket);
                ((ServerPlayer) player).connection.send(clientboundSetSubtitleTextPacket);
            }
        });
        ParticleProvider.GatherParticle(mob.position(), (ServerLevel) mob.level(), 1, 6, 120, ModParticles.LONG_ENTROPY.get(), 0.25);
        ParticleProvider.GatherParticle(mob.position(), (ServerLevel) mob.level(), 1.5, 6, 120, ModParticles.LONG_ENTROPY.get(), 0.25);
    }

    public static void Skill3(Mob mob) {
        if (TabooDevil.DevilSkill3Flag && mob.getHealth() < mob.getMaxHealth() * 0.25) {
            TabooDevil.DevilSkill3Flag = false;
            mob.heal(mob.getMaxHealth() * 0.75f);
            ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) mob.level(), 1, 1, 120, ModParticles.LONG_ENTROPY.get(), 1);
            ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) mob.level(), 1.5, 1, 120, ModParticles.LONG_ENTROPY.get(), 1);
        }
    }

    public static void Skill4(Mob mob, List<Player> playerList, int difficulty) {
        if (TabooDevil.DevilSkill4Flag && mob.getHealth() < mob.getMaxHealth() * 0.2) {
            TabooDevil.DevilSkill4Flag = false;
            playerList.forEach(player -> {
                if (player.distanceTo(mob) < 50) {
                    Compute.Damage.manaDamageToPlayer(mob, player, (DevilBaseAttack) * difficulty * 0.5);
                }
            });
            ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) mob.level(), 1, 1, 120, ModParticles.LONG_ENTROPY.get(), 1);
            ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) mob.level(), 1.5, 1, 120, ModParticles.LONG_ENTROPY.get(), 1);
        }
    }

    public static boolean MobNearbyHasTabooPiece(Mob mob) {
        List<ItemEntity> itemEntityList = mob.level().getEntitiesOfClass(ItemEntity.class, AABB.ofSize(mob.position(), 15, 15, 15));
        for (ItemEntity itemEntity : itemEntityList) {
            if (itemEntity.distanceTo(mob) < 6 && itemEntity.getItem().is(ModItems.IntensifiedDevilBlood.get())) {
                itemEntity.remove(Entity.RemovalReason.KILLED);
                return true;
            }
        }
        return false;
    }

    public static BlockPos[] blockPosList = {
            new BlockPos(1404, 70, 1142),
            new BlockPos(1404, 70, 1196),
            new BlockPos(1458, 70, 1142),
            new BlockPos(1458, 70, 1196)
    };

    public static void CreateBlock(Level level) {
        for (BlockPos blockPos : blockPosList) {
            level.setBlockAndUpdate(blockPos, Blocks.CRYING_OBSIDIAN.defaultBlockState());
        }
    }

    public static int DetectBlockNum(Level level) {
        int count = 0;
        for (BlockPos blockPos : blockPosList) {
            if (level.getBlockState(blockPos).is(Blocks.CRYING_OBSIDIAN)) count++;
        }
        return count;
    }

    public static void BlockParticle(Level level, Mob mob) {
        for (BlockPos blockPos : blockPosList) {
            if (level.getBlockState(blockPos).is(Blocks.CRYING_OBSIDIAN)) {
                ParticleProvider.LineParticle(level, (int) blockPos.getCenter().distanceTo(mob.position()) * 5,
                        blockPos.getCenter(), mob.position().add(0, 1, 0),
                        ModParticles.LONG_ENTROPY.get());
            }
        }
    }

    public static void DetectDig(BlockEvent.BreakEvent event) throws IOException {
        BlockPos blockPos = event.getPos();
        BlockState blockState = event.getState();
        Player player = event.getPlayer();
        Level level = player.level();
        if (Arrays.stream(blockPosList).toList().contains(blockPos) && blockState.is(Blocks.CRYING_OBSIDIAN)) {
            level.destroyBlock(blockPos, false);
            Compute.RateItemStackGive(ModItems.DevilBlood.get().getDefaultInstance(), 0.1, player);
            Compute.RateItemStackGive(ModItems.TabooAttackLeggingsForgeDraw.get().getDefaultInstance(), 0.01, player);
            Compute.RateItemStackGive(ModItems.TabooSwiftHelmetForgeDraw.get().getDefaultInstance(), 0.01, player);
            Compute.RateItemStackGive(ModItems.TabooManaBootsForgeDraw.get().getDefaultInstance(), 0.01, player);
        }
    }

    public static void ResetBlock(Level level) {
        for (BlockPos blockPos : blockPosList) {
            level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
        }
    }

    public static void RangeManaDamage(Mob mob, List<Player> playerList, double damage, double distance) {
        playerList.forEach(player -> {
            if (player.distanceTo(mob) < distance) {
                Compute.Damage.manaDamageToPlayer(mob, player, damage);
            }
        });
        ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) mob.level(), 1, 1, 120, ModParticles.LONG_ENTROPY.get(), 1);
        ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) mob.level(), 1.5, 1, 120, ModParticles.LONG_ENTROPY.get(), 1);
    }

    public static void singleRewardToPlayer(Player player, int difficultyEnhanceRate, int playerNum, boolean isMopUp) {
        Compute.itemStackGive(player, new ItemStack(ModItems.TabooPiece.get(), difficultyEnhanceRate));

        Random random = new Random();
        if (!isMopUp) {
            if (random.nextDouble() <= 0.025 * (playerNum - 1) * difficultyEnhanceRate) {
                Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("你通过组队挑战副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                                append(ModItems.TabooPiece.get().getDefaultInstance().getDisplayName()));
                Compute.itemStackGive(player, new ItemStack(ModItems.TabooPiece.get(), 2));
            }
        }

        if (LoginInEvent.playerDailyInstanceReward(player, 8)) {
            Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("每日首次通关副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                            append(ModItems.TabooPiece.get().getDefaultInstance().getDisplayName()));

            Compute.itemStackGive(player, new ItemStack(ModItems.TabooPiece.get(), 16));
        }
    }
}












