package fun.wraq.process.system.teamInstance.instances.blackCastle;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.teamInstance.NewTeamInstance;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewCastleInstance extends NewTeamInstance {

    public boolean firstBonusMobIsClear;
    public static NewTeamInstance instance;

    public static NewTeamInstance getInstance() {
        if (instance == null)
            instance = new NewCastleInstance(false, new Vec3(2417, 152, -1372),
                    Te.s("暗黑城堡", CustomStyle.styleOfCastle),
                    Te.s("暗黑城堡", CustomStyle.styleOfCastle), 5, 180);
        return instance;
    }

    public NewCastleInstance(boolean inChallenging, Vec3 prepareCenterPos,
                             MutableComponent mutableComponent, MutableComponent regionDescription,
                             double detectRange, int levelRequire) {
        super(inChallenging, prepareCenterPos, mutableComponent, regionDescription, detectRange, levelRequire,
                1, 8, 300, Level.OVERWORLD, new Vec2(90, -10));
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
                double maxHealth = 150 * Math.pow(10, 4) * (1 + 0.75 * (players.size() - 1));
                MobSpawn.setMobCustomName(witherSkeleton, Component.literal(mobNameOf1StageMana).withStyle(CustomStyle.styleOfCastle), 180);
                MobSpawn.MobBaseAttributes.setMobBaseAttributes(witherSkeleton, 180, 3000, 160,
                        80, 0.5, 3, 0.3, 55, 0,
                        maxHealth, 0.3);
                witherSkeleton.setItemSlot(EquipmentSlot.HEAD, ModItems.MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_MANA_HELMET.get().getDefaultInstance());
                witherSkeleton.setItemSlot(EquipmentSlot.CHEST, ModItems.MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_CHEST.get().getDefaultInstance());
                witherSkeleton.setItemSlot(EquipmentSlot.LEGS, ModItems.MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_LEGGINGS.get().getDefaultInstance());
                witherSkeleton.setItemSlot(EquipmentSlot.FEET, ModItems.MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_BOOTS.get().getDefaultInstance());
                witherSkeleton.setItemSlot(EquipmentSlot.MAINHAND, ModItems.CASTLE_SCEPTRE.get().getDefaultInstance());
                witherSkeleton.setItemSlot(EquipmentSlot.OFFHAND, ModItems.WITHER_BOOK.get().getDefaultInstance());
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
            MobSpawn.setMobCustomName(witherSkeleton, Component.literal(mobNameOf1StageAttack).withStyle(CustomStyle.styleOfCastle), 180);
            double maxHealth = 250 * Math.pow(10, 4) * (1 + 0.75 * (players.size() - 1));
            MobSpawn.MobBaseAttributes.setMobBaseAttributes(witherSkeleton, 180, 3000, 80,
                    160, 0.5, 3, 0.3, 55, 0,
                    maxHealth, 0.3);
            witherSkeleton.setItemSlot(EquipmentSlot.HEAD, ModItems.MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_ATTACK_HELMET.get().getDefaultInstance());
            witherSkeleton.setItemSlot(EquipmentSlot.CHEST, ModItems.MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_CHEST.get().getDefaultInstance());
            witherSkeleton.setItemSlot(EquipmentSlot.LEGS, ModItems.MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_LEGGINGS.get().getDefaultInstance());
            witherSkeleton.setItemSlot(EquipmentSlot.FEET, ModItems.MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_BOOTS.get().getDefaultInstance());
            witherSkeleton.setItemSlot(EquipmentSlot.MAINHAND, ModItems.CASTLE_SWORD.get().getDefaultInstance());
            witherSkeleton.setItemSlot(EquipmentSlot.OFFHAND, ModItems.MINE_SHIELD.get().getDefaultInstance());
            witherSkeleton.moveTo(pos.add(offset.get(random.nextInt(offset.size()))));
            mobList.add(new ConditionSummonMob(0, witherSkeleton, pos, 5));
        });
    }

    @Override
    public void handleTick(Level level) {
        int tickCount = Tick.get();
        mobList.forEach(conditionSummonMob -> {
            Mob mob = conditionSummonMob.mob();
            if (mob.isAlive() && hasSummonedMobs.contains(mob)) {
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
                MobSpawn.setMobCustomName(zombie, Component.literal("暗黑城堡禁军 - 护卫").withStyle(CustomStyle.styleOfCastle), 180);
                double maxHealth = 400 * Math.pow(10, 4) * (1 + 0.75 * (players.size() - 1));
                MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, 180, 3000, 170,
                        170, 0.5, 3, 0.3, 55, 0,
                        maxHealth, 0.35);
                zombie.setBaby(true);
                zombie.setItemSlot(EquipmentSlot.HEAD, ModItems.MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_MANA_HELMET.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.CHEST, ModItems.MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_CHEST.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.LEGS, ModItems.MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_LEGGINGS.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.FEET, ModItems.MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_BOOTS.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.MAINHAND, ModItems.CASTLE_SWORD.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.OFFHAND, ModItems.MINE_SHIELD.get().getDefaultInstance());
                zombie.moveTo(new Vec3(2372 + random.nextInt(6) - 3, 160, -1372 + random.nextInt(6) - 3));
                mobList.add(new ConditionSummonMob(0, zombie, new Vec3(2372, 159, -1372), 10));
            }

            for (int i = 0; i < 2; i++) {
                Zombie zombie = new Zombie(EntityType.ZOMBIE, level);
                MobSpawn.setMobCustomName(zombie, Component.literal("暗黑城堡禁军 - 统领").withStyle(CustomStyle.styleOfCastle), 180);
                double maxHealth = 500 * Math.pow(10, 4) * (1 + 0.75 * (players.size() - 1));
                MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, 180, 3500, 170,
                        170, 0.5, 3, 0.3, 55, 0,
                        maxHealth, 0.35);
                zombie.setItemSlot(EquipmentSlot.HEAD, ModItems.MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_ATTACK_HELMET.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.CHEST, ModItems.MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_CHEST.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.LEGS, ModItems.MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_LEGGINGS.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.FEET, ModItems.MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_BOOTS.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.MAINHAND, ModItems.CASTLE_SWORD.get().getDefaultInstance());
                zombie.setItemSlot(EquipmentSlot.OFFHAND, ModItems.MINE_SHIELD.get().getDefaultInstance());
                zombie.moveTo(new Vec3(2372 + random.nextInt(6) - 3, 160, -1372 + random.nextInt(6) - 3));
                mobList.add(new ConditionSummonMob(0, zombie, new Vec3(2372, 159, -1372), 10));
            }
        }
    }

    @Override
    public void reward(Player player) {
        if (InventoryOperation.checkItemRemoveIfHas(player, List.of(new ItemStack(ModItems.NOTE_PAPER.get(), 2)))) {
            getRewardList().forEach(itemAndRate -> {
                itemAndRate.sendWithMSG(player, 2);
            });
            Compute.givePercentExpToPlayer(player, 0.8, PlayerAttributes.expUp(player), 180);
        } else {
            Compute.sendFormatMSG(player, Component.literal("副本").withStyle(ChatFormatting.RED),
                    Component.literal("你的背包中没有 ").withStyle(ChatFormatting.WHITE).
                            append(ModItems.NOTE_PAPER.get().getDefaultInstance().getDisplayName()).
                            append(Te.s(" * 2", ChatFormatting.AQUA)).
                            append(Component.literal(" 因此你无法获得奖励").withStyle(ChatFormatting.WHITE)));
        }
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.blackCastle);
    }

    @Override
    public Component allowRewardCondition() {
        return Component.literal("需要至少").withStyle(ChatFormatting.WHITE).
                append(Component.literal("锻造").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("过").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1件").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("尘月宫主手/副手/饰品").withStyle(CustomStyle.styleOfMoon)).
                append(Component.literal("，方能获取奖励。").withStyle(ChatFormatting.WHITE));
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(new ItemAndRate(ModItems.CASTLE_LOOT.get(), 1),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GOLD_COIN_BAG.get(), 0.1),
                new ItemAndRate(ModItems.CASTLE_NECKLACE.get(), 0.08),
                new ItemAndRate(NewRuneItems.CASTLE_NEW_RUNE.get(), 0.015));
    }

    @Override
    public double judgeDamage(Player player, Mob mob, double originDamage) {
        return originDamage;
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

    public static void summonLightning(Mob mob) {
        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, mob.level());
        lightningBolt.moveTo(mob.position());
        lightningBolt.setVisualOnly(true);
        mob.level().addFreshEntity(lightningBolt);
    }

    @Override
    public String getKillCountDataKey() {
        return "CastleInstance";
    }
}

