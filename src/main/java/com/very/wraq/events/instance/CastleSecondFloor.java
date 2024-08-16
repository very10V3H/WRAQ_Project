package com.very.wraq.events.instance;

import com.very.wraq.entities.entities.Civil.Civil;
import com.very.wraq.events.core.LoginInEvent;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.Castle.CastleCurios;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Struct.Boss2Damage;
import com.very.wraq.common.Utils.Struct.Instance;
import com.very.wraq.common.Utils.Struct.PlayerTeam;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerBossEvent;
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
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber
public class CastleSecondFloor {

    public static Map<Player, Boolean> playerPickedItemMap = new HashMap<>();
    public static boolean passiveFlag = false;
    public static int shieldCount = 0;
    public static int shieldLastTick = 0;
    public static Map<Player, Double> playerDamageCount = new HashMap<>();
    public static double MaxHealth = 2 * Math.pow(10, 8);
    public static double AttackDamage = 10000;
    public static ServerBossEvent BossInfo1;
    public static ServerBossEvent BossInfo2;

    public static void PlayerPickItem(Player player, ItemStack itemStack) {
        if (itemStack.is(ModItems.CastleTabooPiece.get())) {
            playerPickedItemMap.put(player, true);
            Inventory inventory = player.getInventory();
            for (int i = 0; i < inventory.getMaxStackSize(); i++) {
                if (inventory.getItem(i).is(ModItems.CastleTabooPiece.get())) inventory.getItem(i).setCount(0);
            }
            Compute.sendEffectLastTime(player, ModItems.CastleTabooPiece.get().getDefaultInstance(), 8888, 0, true);
            ClientboundSoundPacket clientboundSoundPacket =
                    new ClientboundSoundPacket(Holder.direct(SoundEvents.ANVIL_PLACE), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1, 1, 0);
            ((ServerPlayer) player).connection.send(clientboundSoundPacket);
        }
    }

    public static void PlayerPickItemExDamage(Player player, Mob mob) {
        if (!mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorCastleKnightHelmet.get()) || mob instanceof Civil)
            return;
        if (playerPickedItemMap.containsKey(player) && playerPickedItemMap.get(player)) {
            playerPickedItemMap.put(player, false);
            if (shieldCount > 0) shieldCount--;
            ModNetworking.sendToClient(new SoundsS2CPacket(11), (ServerPlayer) player);
            Compute.sendEffectLastTime(player, ModItems.CastleTabooPiece.get().getDefaultInstance(), 0);
            Compute.sendFormatMSG(player, Component.literal("暗黑城堡").withStyle(CustomStyle.styleOfCastle),
                    Component.literal("暗黑骑士还有" + shieldCount + "层顽盾!").withStyle(ChatFormatting.WHITE));
            ClientboundSoundPacket clientboundSoundPacket =
                    new ClientboundSoundPacket(Holder.direct(SoundEvents.ANVIL_DESTROY), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1, 1, 0);
            ((ServerPlayer) player).connection.send(clientboundSoundPacket);
        }
    }

    public static double MobDamageImmune(Player player, Mob mob) {
        if (!mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorCastleKnightHelmet.get()) || mob instanceof Civil)
            return 1;
        if (shieldCount > 0) {
            Compute.sendFormatMSG(player, Component.literal("暗黑城堡").withStyle(CustomStyle.styleOfCastle),
                    Component.literal("暗黑骑士还有" + shieldCount + "层顽盾!").withStyle(ChatFormatting.WHITE));

            return 0;
        }
        return 1;
    }

    public static void Skill1(Mob mob, List<Player> playerList) {
        playerList.forEach(player -> {
            if (player.distanceTo(mob) < 50) {
                List<Player> players = player.level().getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 15, 15, 15));
                players.removeIf(player1 -> player1.distanceTo(player) > 6);
                players.forEach(player1 -> {
                    Compute.Damage.DamageIgnoreDefenceToPlayer(mob, player1, 10000);
                });

                ClientboundLevelParticlesPacket clientboundLevelParticlesPacket =
                        new ClientboundLevelParticlesPacket(ParticleTypes.EXPLOSION_EMITTER, true,
                                player.getX(), player.getY(), player.getZ(),
                                0, 0, 0, 0, 0);
                playerList.forEach(player1 -> ((ServerPlayer) player1).connection.send(clientboundLevelParticlesPacket));
                Compute.SoundToAll(player, SoundEvents.DRAGON_FIREBALL_EXPLODE);

            }
        });
    } // 鼓励分散

    public static void Skill2(Mob mob, List<Player> playerList) {
        playerList.forEach(player -> {
            if (player.distanceTo(mob) < 50) {
                List<Player> players = player.level().getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 15, 15, 15));
                players.removeIf(player1 -> player1.distanceTo(player) > 6);
                players.forEach(player1 -> {
                    Compute.Damage.DamageIgnoreDefenceToPlayer(mob, player1, (double) 25000 / players.size());
                });

                ParticleProvider.LineParticle(mob.level(), (int) (5 * mob.distanceTo(player)), mob.getEyePosition(), player.getEyePosition(), ParticleTypes.WITCH);

            }
        });
    } // 鼓励聚集

    @SubscribeEvent
    public static void CastleEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {

            int InstanceIndex = 10;
            if (Utils.instanceList.get(InstanceIndex).isChallenge()) {
                Instance instance = Utils.instanceList.get(InstanceIndex);
                MutableComponent difficulty = Component.literal("");
                switch (instance.getDifficulty()) {
                    case 0 -> difficulty = Component.literal("一般").withStyle(ChatFormatting.GREEN);
                    case 1 -> difficulty = Component.literal("困难").withStyle(ChatFormatting.LIGHT_PURPLE);
                    case 2 -> difficulty = Component.literal("炼狱").withStyle(ChatFormatting.RED);
                }

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
                int startTick = 200;

                // 挑战前提示
                if (instanceTick <= startTick) {
                    if (instanceTick % 20 == 0) {
                        if (instanceTick == startTick) {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("暗黑城堡 - 2层").withStyle(CustomStyle.styleOfCastle));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("在十分钟内完成暗黑骑士挑战!").withStyle(ChatFormatting.RED));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        } else {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("暗黑城堡 - 2层").withStyle(CustomStyle.styleOfCastle));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("做好准备，离副本开始还有 " + (startTick / 20 - instanceTick / 20) + " 秒!"));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        }

                        if (instanceTick < startTick) {
                            List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(767, 90, 1028), 30, 20, 30));
                            list.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
                        }
                    }

                }

                if (instanceTick == startTick) {
                    instance.setMobList(new ArrayList<>() {{
                        add(new WitherSkeleton(EntityType.WITHER_SKELETON, level));
                        add(new WitherSkeleton(EntityType.WITHER_SKELETON, level));
                    }});
                    Summon(instance.getMobList().get(0), instance, 0);
                    BossInfo1 = (ServerBossEvent) (new ServerBossEvent(instance.getMobList().get(0).getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
                    playerListGetByName.forEach(player -> {
                        BossInfo1.addPlayer((ServerPlayer) player);
                    });

                    Summon(instance.getMobList().get(1), instance, 1);
                    BossInfo2 = (ServerBossEvent) (new ServerBossEvent(instance.getMobList().get(1).getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
                    playerListGetByName.forEach(player -> {
                        BossInfo2.addPlayer((ServerPlayer) player);
                    });
                    passiveFlag = true;
                    playerPickedItemMap.clear();
                    BreakBlock(level);
                } // 怪物生成

                if (instanceTick > 200 && instance.getMobList().get(0) != null && BossInfo1 != null) {
                    Mob mob = instance.getMobList().get(0);
                    BossInfo1.setProgress(mob.getHealth() / mob.getMaxHealth());
                }

                if (instanceTick > 200 && instance.getMobList().get(1) != null && BossInfo2 != null) {
                    Mob mob = instance.getMobList().get(1);
                    BossInfo2.setProgress(mob.getHealth() / mob.getMaxHealth());
                }

                if (instanceTick > startTick && instance.getMobList().get(0) != null) {
                    if (instance.getMobList().get(0).isAlive()) instance.getMobList().get(0).removeAllEffects();
                    if (instance.getMobList().get(1).isAlive()) instance.getMobList().get(1).removeAllEffects();
                    if (instance.getMobList().get(0).isAlive() && (instanceTick - startTick) % 200 == 30) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("暗黑爆裂").withStyle(CustomStyle.styleOfCastle));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("请迅速远离其他玩家！").withStyle(ChatFormatting.WHITE));
                        playerListGetByName.forEach(player -> {
                            ((ServerPlayer) player).connection.send(clientboundSetSubtitleTextPacket);
                            ((ServerPlayer) player).connection.send(clientboundSetTitleTextPacket);
                        });
                    }
                    if (instance.getMobList().get(0).isAlive() && (instanceTick - startTick) % 200 == 90) {
                        Skill1(instance.getMobList().get(0), playerListGetByName);
                    }

                    if (instance.getMobList().get(1).isAlive() && (instanceTick - startTick) % 200 == 130) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("暗黑聚能").withStyle(CustomStyle.styleOfCastle));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("请与其他玩家站在一起！").withStyle(ChatFormatting.WHITE));
                        playerListGetByName.forEach(player -> {
                            ((ServerPlayer) player).connection.send(clientboundSetSubtitleTextPacket);
                            ((ServerPlayer) player).connection.send(clientboundSetTitleTextPacket);
                        });
                    }
                    if (instance.getMobList().get(1).isAlive() && (instanceTick - startTick) % 200 == 190) {
                        Skill2(instance.getMobList().get(1), playerListGetByName);
                    }

                    if (((instance.getMobList().get(0).isAlive() && !instance.getMobList().get(1).isAlive())
                            || (!instance.getMobList().get(0).isAlive() && instance.getMobList().get(1).isAlive()))
                            && passiveFlag) {
                        passiveFlag = false;
                        shieldCount = 6;
                        shieldLastTick = instanceTick + 2400;
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("暗黑能量方块已生成！").withStyle(CustomStyle.styleOfCastle));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("获取蕴魔石，击碎骑士顽盾！").withStyle(ChatFormatting.WHITE));
                        playerListGetByName.forEach(player -> {
                            ((ServerPlayer) player).connection.send(clientboundSetSubtitleTextPacket);
                            ((ServerPlayer) player).connection.send(clientboundSetTitleTextPacket);
                        });
                        SummonBlock(level);
                    }

                    if (shieldLastTick == instanceTick) shieldCount = 0;

                    if (!passiveFlag && shieldCount == 0) {
                        shieldCount = -1;
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("骑士顽盾已破碎！").withStyle(CustomStyle.styleOfCastle));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("迅速击杀暗黑骑士！").withStyle(ChatFormatting.WHITE));
                        playerListGetByName.forEach(player -> {
                            ((ServerPlayer) player).connection.send(clientboundSetSubtitleTextPacket);
                            ((ServerPlayer) player).connection.send(clientboundSetTitleTextPacket);
                        });
                    }

                    if (!passiveFlag && shieldCount == -1 && instanceTick % 100 == 0) {
                        if (instance.getMobList().get(0).isAlive())
                            instance.getMobList().get(0).heal((float) (MaxHealth * 0.02));
                        if (instance.getMobList().get(1).isAlive())
                            instance.getMobList().get(1).heal((float) (MaxHealth * 0.02));
                    }


                } // Skill

                if (instanceTick >= startTick && instance.getMobList().get(0) != null && Utils.instanceKillCount[InstanceIndex] >= 2) {
                    Utils.instanceKillCount[InstanceIndex] = 0;
                    Reward(playerListGetByName, instance, playerNum, difficultyEnhanceRate, level, playerTeam, instanceTick, startTick, difficulty);
                    passiveFlag = false;
                    shieldCount = 0;
                    shieldLastTick = 0;
                    playerDamageCount.clear();
                    BreakBlock(level);

                    if (BossInfo1 != null) playerListGetByName.forEach(player -> {
                        BossInfo1.removePlayer((ServerPlayer) player);
                    });
                    BossInfo1 = null;
                    if (BossInfo2 != null) playerListGetByName.forEach(player -> {
                        BossInfo2.removePlayer((ServerPlayer) player);
                    });
                    BossInfo2 = null;
                } // 奖励

                // 失败
                if (instanceTick == 12200 || playerListGetByName.size() == 0 || instance.getDeadTimes() >= playerListGetByName.size()) {
                    Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("队伍：" + playerTeam.getTeamName()).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("挑战副本:").withStyle(ChatFormatting.WHITE)).
                                    append(instance.getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                    append(difficulty).
                                    append(Component.literal(" 失败。").withStyle(ChatFormatting.WHITE)));
                    instance.reset();
                    Compute.EndTp(playerListGetByName, new Vec3(898, 70, 1033));
                    Utils.instanceKillCount[InstanceIndex] = 0;
                    passiveFlag = false;
                    shieldCount = 0;
                    shieldLastTick = 0;
                    playerDamageCount.clear();
                    BreakBlock(level);

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

    public static void Reward(List<Player> playerListGetByName, Instance instance, int playerNum,
                              int difficultyEnhanceRate, Level level, PlayerTeam playerTeam,
                              int instanceTick, int startTick, Component difficulty) {

        playerListGetByName.forEach(player -> {
            SingleRewardToPlayer(player, difficultyEnhanceRate, playerNum, false);
        });

        Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                Component.literal("队伍:").withStyle(ChatFormatting.WHITE).
                        append(Component.literal(playerTeam.getTeamName() + " 用时:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(String.format("%.2f", (instanceTick - startTick) * 0.05) + "s ").withStyle(CustomStyle.styleOfBloodMana)).
                        append(Component.literal("成功挑战了 ").withStyle(ChatFormatting.WHITE)).
                        append(instance.getInstanceName()).
                        append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                        append(difficulty));

        playerListGetByName.forEach(player -> {
            CompoundTag data = player.getPersistentData();
            data.putInt(StringUtils.InstanceTimes.BlackCastle2, data.getInt(StringUtils.InstanceTimes.BlackCastle2) + 1);
        });

        instance.reset();
        Compute.EndTp(playerListGetByName, new Vec3(898, 70, 1033));
    }

    public static void Summon(Mob mob, Instance instance, int index) {
        Level level = mob.level();
        Compute.SetMobCustomName(mob, ModItems.MobArmorCastleKnightHelmet.get(),
                Component.literal("暗黑骑士").withStyle(CustomStyle.styleOfCastle));

        mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(MaxHealth);
        mob.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(AttackDamage);

        mob.setHealth(mob.getMaxHealth());
        mob.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorCastleKnightHelmet.get().getDefaultInstance());
        mob.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorBlackCastleOneFloorChest.get().getDefaultInstance());
        mob.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorBlackCastleOneFloorLeggings.get().getDefaultInstance());
        mob.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorBlackCastleOneFloorBoots.get().getDefaultInstance());
        mob.setItemSlot(EquipmentSlot.MAINHAND, ModItems.CastleSword.get().getDefaultInstance());
        mob.setItemSlot(EquipmentSlot.OFFHAND, ModItems.NetherShield.get().getDefaultInstance());

        mob.moveTo(index == 1 ? new Vec3(769.5, 84, 1028.5) : new Vec3(764, 84, 1028.5));
        level.addFreshEntity(mob);

    }

    public static List<BlockPos> blockPosList = new ArrayList<>() {{
        add(new BlockPos(778, 84, 1028));
        add(new BlockPos(775, 84, 1036));
        add(new BlockPos(766, 84, 1039));
        add(new BlockPos(758, 84, 1036));

        add(new BlockPos(755, 84, 1028));
        add(new BlockPos(758, 84, 1020));
        add(new BlockPos(766, 84, 1017));
        add(new BlockPos(775, 84, 1020));
    }};

    public static void SummonBlock(Level level) {
        blockPosList.forEach(blockPos -> {
            level.setBlockAndUpdate(blockPos, Blocks.NETHER_BRICKS.defaultBlockState());
        });
    }

    public static void BreakBlock(Level level) {
        blockPosList.forEach(blockPos -> {
            level.destroyBlock(blockPos, false);
        });
    }


    public static void DetectDig(BlockEvent.BreakEvent event) {
        BlockPos blockPos = event.getPos();
        if (blockPosList.contains(blockPos)) {
            Player player = event.getPlayer();
            Level level = player.level();
            Random random = new Random();
            ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, level);
            itemEntity.setItem(ModItems.CastleTabooPiece.get().getDefaultInstance());
            itemEntity.setCustomName(ModItems.CastleTabooPiece.get().getDefaultInstance().getDisplayName());
            itemEntity.setCustomNameVisible(true);
            itemEntity.moveTo(blockPos, 0, 0);
            itemEntity.setDeltaMovement(random.nextDouble(0, 0.05), 0.15, random.nextDouble(0.05));
            itemEntity.setPickUpDelay(10);
            level.addFreshEntity(itemEntity);
        }
    }

    public static void SingleRewardToPlayer(Player player, int difficultyEnhanceRate, int playerNum, boolean isMopUp) {
        Level level = player.level();
        Random random = new Random();

        if (!isMopUp) {
            Compute.sendFormatMSG(player, Component.literal("副本").withStyle(ChatFormatting.RED),
                    Component.literal("  征讨伤害排名如下：").withStyle(ChatFormatting.WHITE));

            List<Boss2Damage> list = new ArrayList<>();
            playerDamageCount.forEach((player1, aDouble) -> {
                list.add(new Boss2Damage(player1, aDouble));
            });
            list.sort(Comparator.comparing(Boss2Damage::getDamage).reversed());
            AtomicInteger index = new AtomicInteger(1);

            list.forEach(boss2Damage -> {
                if (boss2Damage.getPlayer() != null) {
                    Player player1 = boss2Damage.getPlayer();
                    double damage = boss2Damage.getDamage();
                    Compute.sendFormatMSG(player, Component.literal(index + ".").withStyle(ChatFormatting.RED),
                            Component.literal(" ").withStyle(ChatFormatting.WHITE).
                                    append(player1.getDisplayName()).
                                    append(Component.literal("  DMG:" + damage + "[" + String.format("%.2f", damage * 100 / (MaxHealth * 2)) + "%]").withStyle(ChatFormatting.WHITE)));
                    index.incrementAndGet();
                }
            });
        }

        if (isMopUp || (playerDamageCount.containsKey(player) && playerDamageCount.get(player) / (MaxHealth * 2) > 0.1)) {
            Compute.itemStackGive(player, new ItemStack(ModItems.CastleKnightStone.get(), difficultyEnhanceRate));
            if (random.nextDouble() <= 0.1 && difficultyEnhanceRate == 4) {
                ItemStack itemStack = ModItems.CastleBrooch.get().getDefaultInstance();
                CastleCurios.randomAttributeProvide(itemStack, 6, 1);
                CastleCurios.RandomPassiveProvide(itemStack);
                Compute.formatBroad(level, Component.literal("黑金").withStyle(CustomStyle.styleOfCastle),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 获得了: ").withStyle(ChatFormatting.WHITE)).
                                append(itemStack.getDisplayName()));
                Compute.itemStackGive(player, itemStack);

            }

            if (!isMopUp) {
                if (random.nextDouble() <= 0.025 * (playerNum - 1) * difficultyEnhanceRate) {
                    Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal("你通过组队挑战副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                                    append(ModItems.CastleKnightStone.get().getDefaultInstance().getDisplayName()));
                    Compute.itemStackGive(player, new ItemStack(ModItems.CastleKnightStone.get(), 2));
                }
            }
        }

        if (LoginInEvent.playerDailyInstanceReward(player, 10)) {
            Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("每日首次通关副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                            append(ModItems.CastleKnightStone.get().getDefaultInstance().getDisplayName()));
            Compute.itemStackGive(player, new ItemStack(ModItems.CastleKnightStone.get(), 16));
        }
    }
}












