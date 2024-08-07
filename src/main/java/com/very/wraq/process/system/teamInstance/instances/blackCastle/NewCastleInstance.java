package com.very.wraq.process.system.teamInstance.instances.blackCastle;

import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.events.instance.Castle;
import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.instance.NoTeamInstance;
import com.very.wraq.process.system.teamInstance.NewTeamInstance;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.newrunes.NewRuneItems;
import com.very.wraq.common.Compute;
import com.very.wraq.common.ModEntityType;
import com.very.wraq.common.Utils.ItemAndRate;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewCastleInstance extends NewTeamInstance {

    public boolean firstBonusMobIsClear;
    public static NewTeamInstance instance;

    public static NewTeamInstance getInstance() {
        if (instance == null)
            instance = new NewCastleInstance(false, new Vec3(2417, 152, -1372), Component.literal("暗黑城堡").withStyle(CustomStyle.styleOfCastle), 5, 180);
        return instance;
    }

    public NewCastleInstance(boolean inChallenging, Vec3 prepareCenterPos, MutableComponent mutableComponent, double detectRange, int levelRequire) {
        super(inChallenging, prepareCenterPos, mutableComponent, detectRange, levelRequire, 1, 8, 300);
    }

    public static String mobNameOf1StageMana = "暗黑城堡遗魂 - 术士";
    public static String mobNameOf1StageAttack = "暗黑城堡遗魂 - 骑士";

    @Override
    public void initMobList(Level level) {
        firstBonusMobIsClear = false;
        List<Vec3> manaSummonPos = new ArrayList<>() {{
            add(new Vec3(2353, 160, -1404));
            add(new Vec3(2335, 160, -1406));
            add(new Vec3(2318, 160, -1388));
            add(new Vec3(2285, 160, -1386));
        }};
        List<Vec3> offset = new ArrayList<>() {{
            add(new Vec3(0, 0, 1));
            add(new Vec3(1, 0, 0));
            add(new Vec3(0, 0, -1));
            add(new Vec3(-1, 0, 0));
        }};

        manaSummonPos.forEach(pos -> {
            for (int i = 0; i < 4; i++) {
                WitherSkeleton witherSkeleton = new WitherSkeleton(EntityType.WITHER_SKELETON, level);
                Compute.SetMobCustomName(witherSkeleton, Component.literal(mobNameOf1StageMana).withStyle(CustomStyle.styleOfCastle), 180);
                MobSpawn.MobBaseAttributes.setMobBaseAttributes(witherSkeleton, 180, 3000, 7000, 1000, 0.5, 5, 0.3, 1500, 20, 800 * Math.pow(10, 4), 0.3);
                witherSkeleton.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorBlackCastleOneFloorManaHelmet.get().getDefaultInstance());
                witherSkeleton.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorBlackCastleOneFloorChest.get().getDefaultInstance());
                witherSkeleton.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorBlackCastleOneFloorLeggings.get().getDefaultInstance());
                witherSkeleton.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorBlackCastleOneFloorBoots.get().getDefaultInstance());
                witherSkeleton.setItemSlot(EquipmentSlot.MAINHAND, ModItems.CastleSceptre.get().getDefaultInstance());
                witherSkeleton.setItemSlot(EquipmentSlot.OFFHAND, ModItems.WitherBook.get().getDefaultInstance());
                witherSkeleton.moveTo(pos.add(offset.get(i)));
                mobList.add(new ConditionSummonMob(0, witherSkeleton, pos, 5));
            }
        });

        List<Vec3> attackSummonPos = new ArrayList<>() {{
            add(new Vec3(2309, 160, -1348));
            add(new Vec3(2344, 160, -1345));
            add(new Vec3(2286, 160, -1368));
            add(new Vec3(2318, 160, -1366));
        }};
        Random random = new Random();
        attackSummonPos.forEach(pos -> {
            WitherSkeleton witherSkeleton = new WitherSkeleton(EntityType.WITHER_SKELETON, level);
            Compute.SetMobCustomName(witherSkeleton, Component.literal(mobNameOf1StageAttack).withStyle(CustomStyle.styleOfCastle), 180);
            MobSpawn.MobBaseAttributes.setMobBaseAttributes(witherSkeleton, 180, 3000, 1000, 7000, 0.5, 5, 0.3, 1500, 20, 2000 * Math.pow(10, 4), 0.3);
            witherSkeleton.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorBlackCastleOneFloorAttackHelmet.get().getDefaultInstance());
            witherSkeleton.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorBlackCastleOneFloorChest.get().getDefaultInstance());
            witherSkeleton.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorBlackCastleOneFloorLeggings.get().getDefaultInstance());
            witherSkeleton.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorBlackCastleOneFloorBoots.get().getDefaultInstance());
            witherSkeleton.setItemSlot(EquipmentSlot.MAINHAND, ModItems.CastleSword.get().getDefaultInstance());
            witherSkeleton.setItemSlot(EquipmentSlot.OFFHAND, ModItems.MineShield.get().getDefaultInstance());
            witherSkeleton.moveTo(pos.add(offset.get(random.nextInt(offset.size()))));
            mobList.add(new ConditionSummonMob(0, witherSkeleton, pos, 5));
        });
    }

    @Override
    public void handleTick(Level level) {
        hasSummonedMobs.forEach(mob -> {
            if (mob.isDeadOrDying()) hasKilledMobs.add(mob);
        });

        int tickCount = level.getServer().getTickCount();
        if (tickCount % 200 == 8) {
            players.forEach(player -> {
                Compute.sendFormatMSG(player, Component.literal("团队副本").withStyle(ChatFormatting.RED),
                        Component.literal("还剩").withStyle(ChatFormatting.WHITE).
                                append(Component.literal(String.valueOf(mobList.size() - hasKilledMobs.size())).withStyle(ChatFormatting.RED)).
                                append(Component.literal("只怪物未清理。").withStyle(ChatFormatting.WHITE)));
            });
        }
        mobList.forEach(conditionSummonMob -> {
            Mob mob = conditionSummonMob.mob();
            if (!hasSummonedMobs.contains(mob)) {
                if (conditionSummonMob.condition() == 0) {
                    boolean hasPlayerNearby = false;
                    for (Player player : players) {
                        if (player.position().distanceTo(conditionSummonMob.summonPos()) < conditionSummonMob.detectRange()) {
                            hasPlayerNearby = true;
                        }
                    }
                    if (hasPlayerNearby) {
                        hasSummonedMobs.add(mob);
                        if (mob instanceof Zombie) Castle.lightningSummon(mob);
                        level.addFreshEntity(mob);
                    }
                }
            }
            if (mob.isAlive()) {
                if (MobSpawn.getMobOriginName(mob).equals(mobNameOf1StageMana)) {
                    if (tickCount % 30 == 0) shootManaArrow(mob);
                }
            }
        });
        Random random = new Random();
        if (allMobIsClear() && !firstBonusMobIsClear) {
            firstBonusMobIsClear = true;

            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                    new ClientboundSetTitleTextPacket(Component.literal("敌方增援即将赶到").withStyle(CustomStyle.styleOfCastle));
            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                    new ClientboundSetSubtitleTextPacket(Component.literal("前往城堡大门迎战！").withStyle(CustomStyle.styleOfCastle));
            players.forEach(player -> {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
            });

            for (int i = 0; i < 4; i++) {
                Zombie zombie = new Zombie(EntityType.ZOMBIE, level);
                Compute.SetMobCustomName(zombie, Component.literal("暗黑城堡禁军 - 护卫").withStyle(CustomStyle.styleOfCastle), 180);

                MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, 180, 3000, 3000, 3000, 0.5, 5, 0.3, 1500, 20, 1000 * Math.pow(10, 4), 0.35);
                zombie.setBaby(true);
                zombie.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorBlackCastleOneFloorManaHelmet.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorBlackCastleOneFloorChest.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorBlackCastleOneFloorLeggings.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorBlackCastleOneFloorBoots.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.MAINHAND, ModItems.CastleSword.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.OFFHAND, ModItems.MineShield.get().getDefaultInstance());
                zombie.moveTo(new Vec3(2372 + random.nextInt(6) - 3, 160, -1372 + random.nextInt(6) - 3));
                mobList.add(new ConditionSummonMob(0, zombie, new Vec3(2372, 159, -1372), 10));
            }

            for (int i = 0; i < 2; i++) {
                Zombie zombie = new Zombie(EntityType.ZOMBIE, level);
                Compute.SetMobCustomName(zombie, Component.literal("暗黑城堡禁军 - 统领").withStyle(CustomStyle.styleOfCastle), 180);
                MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, 180, 3500, 5000, 5000, 0.5, 5, 0.3, 1500, 20, 2000 * Math.pow(10, 4), 0.35);
                zombie.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorBlackCastleOneFloorAttackHelmet.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorBlackCastleOneFloorChest.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorBlackCastleOneFloorLeggings.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorBlackCastleOneFloorBoots.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.MAINHAND, ModItems.CastleSword.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.OFFHAND, ModItems.MineShield.get().getDefaultInstance());
                zombie.moveTo(new Vec3(2372 + random.nextInt(6) - 3, 160, -1372 + random.nextInt(6) - 3));
                mobList.add(new ConditionSummonMob(0, zombie, new Vec3(2372, 159, -1372), 10));
            }
        }
        if (mobList.size() - hasKilledMobs.size() == 0 && !allMobIsClear()) {
            players.forEach(player -> {
                Compute.sendFormatMSG(player, Component.literal("团队副本").withStyle(ChatFormatting.RED),
                        Component.literal("挑战异常，已终止").withStyle(ChatFormatting.WHITE));
            });
            clear();
        }
    }

    @Override
    public void reward() {
        players.forEach(player -> {
            if (NoTeamInstance.playerHasItem(player)) {
                Compute.itemStackRemove(player.getInventory(), ModItems.notePaper.get(), 1);

                getRewardList().forEach(itemAndRate -> {
                    try {
                        itemAndRate.send(player, (1 + Compute.playerExHarvest(player)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player), 180);

            } else {
                Compute.sendFormatMSG(player, Component.literal("副本").withStyle(ChatFormatting.RED),
                        Component.literal("你的背包中没有 ").withStyle(ChatFormatting.WHITE).
                                append(ModItems.notePaper.get().getDefaultInstance().getDisplayName()).
                                append(Component.literal(" 因此你无法获得奖励").withStyle(ChatFormatting.WHITE)));
            }
        });
    }

    public static List<ItemAndRate> getRewardList() {
        return List.of(new ItemAndRate(ModItems.CastleLoot.get(), 1),
                new ItemAndRate(ModItems.WorldSoul2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1),
                new ItemAndRate(ModItems.CastleNecklace.get(), 0.08),
                new ItemAndRate(NewRuneItems.castleNewRune.get(), 0.015));
    }

    @Override
    public void clear() {
        super.clear();
        firstBonusMobIsClear = false;
    }

    public static void shootManaArrow(Mob mob) {
        ManaArrow manaArrow = new ManaArrow(ModEntityType.NEW_ARROW.get(), mob, mob.level(), 1);
        manaArrow.shootFromRotation(mob, mob.getXRot(), mob.getYRot(), 1, 1.5f, 1);
        manaArrow.setSilent(true);
        manaArrow.setNoGravity(true);
        mob.level().addFreshEntity(manaArrow);
    }
}

