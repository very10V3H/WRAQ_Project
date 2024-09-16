package com.very.wraq.events.instance;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.util.struct.Boss2Damage;
import com.very.wraq.common.util.struct.Instance;
import com.very.wraq.common.util.struct.PlayerTeam;
import com.very.wraq.events.server.LoginInEvent;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.process.func.damage.Damage;
import com.very.wraq.process.func.item.InventoryOperation;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.render.hud.ColdData;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.level.block.Blocks;
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
import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber
public class IceKnight {
    public static ServerBossEvent BossInfo;

    @SubscribeEvent
    public static void PlainEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {

            int InstanceIndex = 5;
            double MaxHealth = 350000;
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
                                    new ClientboundSetTitleTextPacket(Component.literal("冰霜骑士挑战").withStyle(CustomStyle.styleOfIce));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("在十分钟内击杀冰霜骑士!").withStyle(ChatFormatting.RED));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        } else {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("冰霜骑士挑战").withStyle(CustomStyle.styleOfIce));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("做好准备，离Boss出现还有 " + (10 - instanceTick / 20) + " 秒!"));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        }
                        if (instanceTick < 200) {
                            List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(340, 73, 2331), 100, 100, 100));
                            list.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
                        }
                    }
                }

                // 怪物生成
                if (instanceTick == 200) {

                    instance.setMobList(new ArrayList<>() {{
                        add(new Stray(EntityType.STRAY, level));
                    }});
                    Mob entity = instance.getMobList().get(0);

                    MobSpawn.setMobCustomName(entity, ModItems.MobArmorIceHelmet.get(),
                            Component.literal("冰霜骑士").withStyle(CustomStyle.styleOfIce));

                    entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(MaxHealth * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75));
                    entity.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(100 * difficultyEnhanceRate);
                    entity.setHealth(entity.getMaxHealth());
                    entity.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorIceHelmet.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorIceChest.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorIceLeggings.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorIceBoots.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.MAINHAND, ModItems.SoulSword.get().getDefaultInstance());

                    entity.moveTo(318, 63, 2353);
                    level.addFreshEntity(entity);
                    BossInfo = (ServerBossEvent) (new ServerBossEvent(entity.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
                    playerListGetByName.forEach(player -> {
                        BossInfo.addPlayer((ServerPlayer) player);
                    });
                    Utils.IceKnightDamageList.clear();
                }

                if (instanceTick > 200 && instance.getMobList().get(0) != null && BossInfo != null) {
                    Mob mob = instance.getMobList().get(0);
                    BossInfo.setProgress(mob.getHealth() / mob.getMaxHealth());
                    Element.ElementProvider(mob, Element.ice, 3);
                }
                if (instanceTick > 200 && instance.getMobList().get(0) != null && instanceTick % 50 == 0) {
                    Mob mob = instance.getMobList().get(0);
                    List<Player> players = level.getEntitiesOfClass(Player.class, AABB.ofSize(mob.position(), 50, 50, 50));

                    Skill(mob, instanceTick, players); // 技能
                    AtomicReference<Player> NearestPlayer = new AtomicReference<>();
                    AtomicReference<Double> distance = new AtomicReference<>((double) 50);
                    players.forEach(player -> {
                        if (player.distanceTo(instance.getMobList().get(0)) < distance.get()) {
                            distance.set((double) player.distanceTo(instance.getMobList().get(0)));
                            NearestPlayer.set(player);
                        }
                    });


                    if (NearestPlayer.get() != null) {
                        Damage.AttackDamageToPlayer(mob, NearestPlayer.get(), 200 * difficultyEnhanceRate);
                        NearestPlayer.get().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 3, false, false, false));

                        BlockPos blockPos = new BlockPos((int) NearestPlayer.get().getX(), (int) (NearestPlayer.get().getY() + 0.9), (int) NearestPlayer.get().getZ());
                        if (NearestPlayer.get().level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                            NearestPlayer.get().level().setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());
                            NearestPlayer.get().level().destroyBlock(blockPos, false);
                        }
                    }

                    playerListGetByName.forEach(player -> {
                        if (player != null && player.distanceTo(mob) < 50) {
                            Damage.manaDamageToPlayer(mob, player, 40 * difficultyEnhanceRate);
                            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1, false, false, false));
                            BlockPos blockPos = new BlockPos((int) player.getX(), (int) (player.getY() + 0.9), (int) player.getZ());
                            if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                                player.level().setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());
                                player.level().destroyBlock(blockPos, false);
                            }
                        }
                    });

                    ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) level,
                            1, 1, 120, ModParticles.LONG_SNOW.get(), 1);
                    ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) level,
                            1.5, 1, 120, ModParticles.LONG_SNOW.get(), 1);
                } // AI - 基础攻击

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
                        SingleRewardToPlayer(player, difficultyEnhanceRate, playerNum, false);
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

                        Utils.IceKnightDamageList.sort(Comparator.comparing(Boss2Damage::getDamage).reversed());
                        AtomicInteger index = new AtomicInteger(1);

                        Utils.IceKnightDamageList.forEach(boss2Damage -> {
                            if (boss2Damage.getPlayer() != null) {
                                Player player1 = boss2Damage.getPlayer();
                                double damage = boss2Damage.getDamage();
                                if (playerListGetByName.size() == 1) {
                                    if (damage / (MaxHealth * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75)) >= 0.9
                                            && (instanceTick - 200) <= 600) {
                                        CompoundTag data = player1.getPersistentData();
                                        if (data.getInt(StringUtils.PlayerInstanceProgress) < 6) {
                                            data.putInt(StringUtils.PlayerInstanceProgress, 6);
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
                        data.putInt(StringUtils.InstanceTimes.IceKnight, data.getInt(StringUtils.InstanceTimes.IceKnight) + 1);
                    });
                    instance.reset();
                    Utils.IceKnightDamageList.clear();
                    ClearIceHunter();
                    Compute.EndTp(playerListGetByName, new Vec3(374, 63, 2320));
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
                    Utils.IceKnightDamageList.clear();
                    ClearIceHunter();
                    Compute.EndTp(playerListGetByName, new Vec3(374, 63, 2320));
                    if (BossInfo != null) playerListGetByName.forEach(player -> {
                        BossInfo.removePlayer((ServerPlayer) player);
                    });
                    BossInfo = null;
                }
            }
        }
    }

    public static double IceKnightHealthAttackDamageFix(Mob mob) {
        if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorIceHelmet.get())) {
            if (mob.getHealth() / mob.getMaxHealth() > 2.0 / 3) return -0.5;
            if (mob.getHealth() / mob.getMaxHealth() < 1.0 / 3) return 0.5;
        }
        return 0;
    }

    public static double IceKnightHealthManaDamageFix(Mob mob) {
        if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorIceHelmet.get())) {
            if (mob.getHealth() / mob.getMaxHealth() > 2.0 / 3) return 0.5;
            if (mob.getHealth() / mob.getMaxHealth() < 1.0 / 3) return -0.5;
        }
        return 0;
    }

    public static void Skill(Mob mob, int instanceTick, List<Player> playerList) {
        int[] Tick = {60, 80, 100, 140, 200, 200};
        int Frequency = Tick[(int) (mob.getHealth() / mob.getMaxHealth())];
        Random random = new Random();
        int ChooseSkill = random.nextInt(5);
        Level level = mob.level();
        Style style = CustomStyle.styleOfIce;
        if (instanceTick % Frequency == 0) {
            switch (ChooseSkill) {
                case 0 -> {
                    playerList.forEach(player -> {
                        if (player.distanceTo(mob) < 50) {
                            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 99, false, false, false));
                            BlockPos blockPos = new BlockPos((int) player.getX(), (int) (player.getY() + 0.9), (int) player.getZ());
                            if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                                player.level().setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());
                                player.level().destroyBlock(blockPos, false);
                            }
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("寒意释放").withStyle(style));
                            ServerPlayer serverPlayer = (ServerPlayer) player;
                            serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        }
                    });
                    ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) level,
                            1, 1, 120, ModParticles.LONG_SKY.get(), 2);
                    ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) level,
                            1.5, 1, 120, ModParticles.LONG_SKY.get(), 2);
                } // 寒意释放
                case 1 -> {
                    playerList.forEach(player -> {
                        if (player.distanceTo(mob) < 50) {
                            Vec3 vec3 = player.position().subtract(mob.position());
                            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                                    new ClientboundSetEntityMotionPacket(player.getId(), vec3.normalize().scale(6));
                            ServerPlayer serverPlayer = (ServerPlayer) player;
                            serverPlayer.connection.send(clientboundSetEntityMotionPacket);
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("高压寒风").withStyle(style));
                            serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        }
                    });
                    ParticleProvider.GatherParticle(mob.position(), (ServerLevel) level,
                            1, 1, 120, ModParticles.LONG_SKY.get(), 1);
                    ParticleProvider.GatherParticle(mob.position(), (ServerLevel) level,
                            1.5, 1, 120, ModParticles.LONG_SKY.get(), 1);
                } // 凝聚寒意
                case 2 -> {
                    playerList.forEach(player -> {
                        if (player.distanceTo(mob) < 50) {
                            if (ColdData.PlayerCurrentColdNum(player) >= 50) {
                                Damage.AttackDamageToPlayer(mob, player, player.getMaxHealth() * 1.5);
                            }
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("迸晶裂玉").withStyle(style));
                            ServerPlayer serverPlayer = (ServerPlayer) player;
                            serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        }
                    });
                    ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) level,
                            1, 1, 120, ModParticles.LONG_SKY.get(), 2);
                    ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) level,
                            1.5, 1, 120, ModParticles.LONG_SKY.get(), 2);
                } // 迸晶裂玉
                case 3 -> {
                    playerList.forEach(player -> {
                        if (player.distanceTo(mob) < 50) {
                            Vec3 vec3 = mob.position().subtract(player.position());
                            if (vec3.length() <= 8) {
                                ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                                        new ClientboundSetEntityMotionPacket(player.getId(), vec3.normalize().scale(Math.min(2, 8 / vec3.length())));
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetEntityMotionPacket);
                                ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                        new ClientboundSetTitleTextPacket(Component.literal("凝聚寒意").withStyle(style));
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                            }
                        }
                    });
                    ParticleProvider.GatherParticle(mob.position(), (ServerLevel) level,
                            1, 1, 120, ModParticles.LONG_SKY.get(), 1);
                    ParticleProvider.GatherParticle(mob.position(), (ServerLevel) level,
                            1.5, 1, 120, ModParticles.LONG_SKY.get(), 1);
                }
                case 4 -> {
                    for (int i = 0; i < 4; i++) {
                        if (Utils.IceHunterForIceKnight[i] == null || Utils.IceHunterForIceKnight[i].isDeadOrDying()) {
                            if (Utils.IceHunterForIceKnight[i] != null)
                                Utils.IceHunterForIceKnight[i].remove(Entity.RemovalReason.KILLED);
                            Utils.IceHunterForIceKnight[i] = new Stray(EntityType.STRAY, mob.level());
                            MobSpawn.setMobCustomName(Utils.IceHunterForIceKnight[i], ModItems.MobArmorIceHunterHelmet.get(),
                                    Component.literal("冰原猎手").withStyle(CustomStyle.styleOfIce));
                            Utils.IceHunterForIceKnight[i].setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorIceHunterHelmet.get().getDefaultInstance());
                            Utils.IceHunterForIceKnight[i].setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorIceHunterChest.get().getDefaultInstance());
                            Utils.IceHunterForIceKnight[i].setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorIceHunterLeggings.get().getDefaultInstance());
                            Utils.IceHunterForIceKnight[i].setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorIceHunterBoots.get().getDefaultInstance());
                            Utils.IceHunterForIceKnight[i].setItemSlot(EquipmentSlot.MAINHAND, Items.BOW.getDefaultInstance());
                            Utils.IceHunterForIceKnight[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(1500000 * (1 + (playerList.size() - 1) * 0.75));
                            Utils.IceHunterForIceKnight[i].setHealth((float) (1500000 * (1 + (playerList.size() - 1) * 0.75)));
                            Utils.IceHunterForIceKnight[i].setHealth(Utils.IceHunterForIceKnight[i].getMaxHealth());
                            Utils.IceHunterForIceKnight[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                            Utils.IceHunterForIceKnight[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1000);
                            Utils.IceHunterForIceKnight[i].moveTo(mob.position().add(1 - random.nextInt(2), 1 - random.nextInt(2), 1 - random.nextInt(2)));

                            level.addFreshEntity(Utils.IceHunterForIceKnight[i]);
                        }
                    }
                } // 召唤冰原猎手
            }
        }
    }

    public static void ClearIceHunter() {
        for (Stray stray : Utils.IceHunterForIceKnight) {
            if (stray != null && stray.isAlive()) stray.remove(Entity.RemovalReason.KILLED);
        }
    }

    public static void SingleRewardToPlayer(Player player, int difficultyEnhanceRate, int playerNum, boolean isMopUp) {
        Level level = player.level();
        Random random = new Random();
        ItemStack itemStack = new ItemStack(Items.AIR);
        if (random.nextDouble() < 0.01 * difficultyEnhanceRate) {
            switch (random.nextInt(7)) {
                case 0 -> itemStack = new ItemStack(ModItems.IceHelmetForgeDraw.get());
                case 1 -> itemStack = new ItemStack(ModItems.IceChestForgeDraw.get());
                case 2 -> itemStack = new ItemStack(ModItems.IceLeggingsForgeDraw.get());
                case 3 -> itemStack = new ItemStack(ModItems.IceBootsForgeDraw.get());
                case 4 -> itemStack = new ItemStack(ModItems.IceSwordForgeDraw.get());
                case 5 -> itemStack = new ItemStack(ModItems.IceBowForgeDraw.get());
                case 6 -> itemStack = new ItemStack(ModItems.IceSceptreForgeDraw.get());
            }
        }
        if (!itemStack.is(Items.AIR)) {
            Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                    Component.literal("").
                            append(player.getDisplayName()).
                            append(Component.literal("获得了:").withStyle(ChatFormatting.WHITE)).
                            append(itemStack.getDisplayName()));
            InventoryOperation.itemStackGive(player, itemStack);
        }

        itemStack = new ItemStack(Items.AIR);
        if (random.nextDouble() < 0.01 * difficultyEnhanceRate) {
            itemStack = new ItemStack(ModItems.IceHeart.get());
        }
        if (!itemStack.is(Items.AIR)) {
            Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                    Component.literal("").
                            append(player.getDisplayName()).
                            append(Component.literal("获得了:").withStyle(ChatFormatting.WHITE)).
                            append(itemStack.getDisplayName()));
            InventoryOperation.itemStackGive(player, itemStack);
        }

        if (!isMopUp) {
            if (random.nextDouble() <= 0.025 * (playerNum - 1) * difficultyEnhanceRate) {
                Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("你通过组队挑战副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                                append(ModItems.IceLoot.get().getDefaultInstance().getDisplayName()));
                InventoryOperation.itemStackGive(player, new ItemStack(ModItems.IceLoot.get(), 2));
            }
        }

        if (LoginInEvent.playerDailyInstanceReward(player, 5)) {
            Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("每日首次通关副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                            append(ModItems.IceLoot.get().getDefaultInstance().getDisplayName()));
                InventoryOperation.itemStackGive(player, new ItemStack(ModItems.IceLoot.get(), 24));
        }
        InventoryOperation.itemStackGive(player, new ItemStack(ModItems.IceLoot.get(), difficultyEnhanceRate));
    }
}
