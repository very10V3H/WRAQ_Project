package fun.wraq.process.system.teamInstance.instances.harbinger;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Harbinger_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Prowler_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.The_Watcher_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.teamInstance.NewTeamInstance;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.harbinger.HarbingerItems;
import fun.wraq.series.instance.series.harbinger.weapon.HarbingerWeaponMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HarbingerInstance extends NewTeamInstance {

    public static int stage = -1;
    public static HarbingerInstance instance;
    public static HarbingerInstance getInstance() {
        if (instance == null) {
            instance = new HarbingerInstance(false, new Vec3(1913, -25, 1798),
                    Te.s("鹰眼工厂", CustomStyle.styleOfHarbinger),
                    Te.s("鹰眼工厂", CustomStyle.styleOfHarbinger), 6, 225, 1, 8, 500,
                    Level.OVERWORLD, new Vec2(-90, 0));
        }
        return instance;
    }

    public HarbingerInstance(boolean inChallenging, Vec3 prepareCenterPos, MutableComponent description,
                             MutableComponent regionDescription, double prepareDetectRange, int levelRequire,
                             int minPlayerNum, int maxPlayerNum, int maxChallengeTime,
                             ResourceKey<Level> dimension, Vec2 rot) {
        super(inChallenging, prepareCenterPos, description, regionDescription, prepareDetectRange, levelRequire,
                minPlayerNum, maxPlayerNum, maxChallengeTime, dimension, rot, 5);
    }

    BlockPos wallUpPos = new BlockPos(1935, -17, 1824);
    BlockPos wallDownPos = new BlockPos(1933, -25, 1824);

    private void setWallBlock(Level level, Block block) {
        for (int i = wallDownPos.getX() ; i <= wallUpPos.getX() ; i ++) {
            for (int j = wallDownPos.getY() ; j <= wallUpPos.getY() ; j ++) {
                for (int k = wallDownPos.getZ() ; k <= wallUpPos.getZ() ; k ++) {
                    BlockPos blockPos = new BlockPos(i, j, k);
                    if (block.equals(Blocks.AIR)) {
                        level.destroyBlock(blockPos, false);
                    } else {
                        level.setBlockAndUpdate(blockPos, block.defaultBlockState());
                    }
                }
            }
        }
    }

    @Override
    public void initMobList(Level level) {
        stage = 1;
        stage1summonPos.forEach(pos -> {
            for (int i = 0 ; i < 4 ; i ++) {
                mobList.add(new ConditionSummonMob(0, setAttributesThenSpawnOfWatcher(level,
                        pos.add(spawnOffsetVec3.get(i))), pos.add(spawnOffsetVec3.get(i)), 24));
            }
        });
        setWallBlock(level, Blocks.BLACK_STAINED_GLASS);
    }

    public List<Mob> blazes = new ArrayList<>();

    @Override
    public void handleTick(Level level) {
        for (ConditionSummonMob conditionSummonMob : mobList) {
            Mob mob = conditionSummonMob.mob();
            if (MobSpawn.getMobOriginName(conditionSummonMob.mob()).equals(THE_HARBINGER_NAME)) {
                boss = mob;
            }
            if (mob.isAlive()) {
                Element.provideElement(mob, Element.fire, 5);
            }
        }
        if (stage == 1) {
            if (allMobIsClear()) {
                stage = 2;
                stage2SummonPos.forEach(pos -> {
                    mobList.add(new ConditionSummonMob(0, setAttributesThenSpawnOfProwler(level, pos), pos, 24));
                });
                setWallBlock(level, Blocks.AIR);
                players.forEach(player -> {
                    Compute.setPlayerTitleAndSubTitle((ServerPlayer) player,
                            Te.s("车间大门已开放!", CustomStyle.styleOfHarbinger),
                            Te.s("前往激光充能车间", CustomStyle.styleOfHarbinger));
                });
            }
        }
        if (stage == 2) {
            if (allMobIsClear()) {
                stage = 3;
                stage3SummonPos.forEach(pos -> {
                    mobList.add(new ConditionSummonMob(0, setAttributesThenSpawnOfProwler(level, pos), pos, 24));
                    for (int i = 0 ; i < 4 ; i ++) {
                        mobList.add(new ConditionSummonMob(0, setAttributesThenSpawnOfWatcher(level,
                                pos.add(spawnOffsetVec3.get(i))), pos.add(spawnOffsetVec3.get(i)), 24));
                    }
                });
                stage3NormalSummonPos.forEach(pos -> {
                    for (int i = 0 ; i < 4 ; i ++) {
                        mobList.add(new ConditionSummonMob(0, setAttributesThenSpawnOfWatcher(level,
                                pos.add(spawnOffsetVec3.get(i))), pos.add(spawnOffsetVec3.get(i)), 24));
                    }
                });
                players.forEach(player -> {
                    Compute.setPlayerTitleAndSubTitle((ServerPlayer) player,
                            Te.s("更多器械已出现!", CustomStyle.styleOfHarbinger),
                            Te.s("前往车间内部，清理暴走器械!", CustomStyle.styleOfHarbinger));
                });
            }
        }
        if (stage == 3) {
            if (allMobIsClear()) {
                stage = 4;
                stage4SummonPos.forEach(pos -> {
                    mobList.add(new ConditionSummonMob(0, setAttributesThenSpawnOfHarbinger(level, pos), pos, 48));
                });
                players.forEach(player -> {
                    Compute.setPlayerTitleAndSubTitle((ServerPlayer) player,
                            Te.s("鹰眼机械先驱出现!", CustomStyle.styleOfHarbinger),
                            Te.s("前往车间中部深处，击杀机械先驱!", CustomStyle.styleOfHarbinger));
                });
            }
        }
        if (stage == 4) {
            if (blazes.isEmpty() && boss != null && boss.getHealth() / boss.getMaxHealth() < 0.5) {
                stage4BlazePos.forEach(pos -> {
                    Mob blaze = setAttributesThenSpawnOfBlade(level, pos);
                    blazes.add(blaze);
                    level.addFreshEntity(blaze);
                });
                players.forEach(player -> {
                    Compute.setPlayerTitleAndSubTitle((ServerPlayer) player,
                            Te.s("实验体已释放!", CustomStyle.styleOfHarbinger),
                            Te.s("先行解决实验体，否则先驱将不会受伤!", CustomStyle.styleOfHarbinger));
                });
            }
        }
    }

    Mob boss;

    List<Vec3> spawnOffsetVec3 = List.of(
            new Vec3(1, 0, 0),
            new Vec3(0, 0, 1),
            new Vec3(-1, 0, 0),
            new Vec3(0, 0, -1)
    );

    List<Vec3> stage1summonPos = List.of(
            new Vec3(1934, -25, 1798),
            new Vec3(1934, -25, 1818),
            new Vec3(1913, -24, 1819),
            new Vec3(1925, -14, 1807),
            new Vec3(1943, -14, 1809)
    );

    List<Vec3> stage2SummonPos = List.of(
            new Vec3(1934, -25, 1829)
    );

    List<Vec3> stage3SummonPos = List.of(
            new Vec3(1919, -19, 1849),
            new Vec3(1919, -19, 1863),
            new Vec3(1949, -19, 1863),
            new Vec3(1949, -19, 1849)
    );

    List<Vec3> stage3NormalSummonPos = List.of(
            new Vec3(1934, -27, 1849),
            new Vec3(1934, -27, 1865),
            new Vec3(1954, -27, 1875),
            new Vec3(1913, -27, 1875),
            new Vec3(1913, -27, 1837),
            new Vec3(1954, -27, 1837)
    );

    List<Vec3> stage4SummonPos = List.of(
            new Vec3(1934, -26, 1883)
    );

    List<Vec3> stage4BlazePos = List.of(
            new Vec3(1945, -28, 1849),
            new Vec3(1945, -28, 1863),
            new Vec3(1923, -28, 1863),
            new Vec3(1923, -28, 1849)
    );

    public static final String THE_WATCHER_NAME = "鹰眼器械I型";
    public Mob setAttributesThenSpawnOfWatcher(Level level, Vec3 pos) {
        The_Watcher_Entity entity = new The_Watcher_Entity(ModEntities.THE_WATCHER.get(), level);
        MobSpawn.setMobCustomName(entity, Te.s(THE_WATCHER_NAME, CustomStyle.styleOfHarbinger), 260);
        double maxHealth = 2500 * Math.pow(10, 4) * (1 + 0.75 * (players.size() - 1));
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(entity, 260, 5000, 360,
                360, 0.4, 3, 0.6, 300, 25,
                maxHealth, 0.5);
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(entity), List.of(
                new ItemAndRate(HarbingerItems.RAW_IRON_NUGGET.get(), 0.2)
        ));
        entity.moveTo(pos);
        return entity;
    }

    public static final String THE_PROWLER_NAME = "鹰眼器械II型";
    public Mob setAttributesThenSpawnOfProwler(Level level, Vec3 pos) {
        The_Prowler_Entity entity = new The_Prowler_Entity(ModEntities.THE_PROWLER.get(), level);
        MobSpawn.setMobCustomName(entity, Te.s(THE_PROWLER_NAME, CustomStyle.styleOfHarbinger), 260);
        double maxHealth = 7500 * Math.pow(10, 4) * (1 + 0.75 * (players.size() - 1));
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(entity, 260, 7500, 480,
                480, 0.4, 3, 0.6, 350, 25,
                maxHealth, 0.35);
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(entity), List.of(
                new ItemAndRate(HarbingerItems.RAW_IRON_INGOT.get(), 0.2)
        ));
        entity.moveTo(pos);
        return entity;
    }

    public static final String THE_HARBINGER_NAME = "鹰眼机械先驱";
    public Mob setAttributesThenSpawnOfHarbinger(Level level, Vec3 pos) {
        The_Harbinger_Entity entity = new The_Harbinger_Entity(ModEntities.THE_HARBINGER.get(), level);
        MobSpawn.setMobCustomName(entity, Te.s(THE_HARBINGER_NAME, CustomStyle.styleOfHarbinger), 260);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(entity, getMainMobAttributes());
        entity.moveTo(pos);
        MobSpawn.setCanNotAddSlowDownOrImprison(entity);
        return entity;
    }

    @Override
    public MobAttributes getMainMobAttributes() {
        double maxHealth = 7500 * Math.pow(10, 4) * (1 + 0.75 * (players.size() - 1));
        return new MobAttributes(9000, 600, 600, 0.4, 3, 0.6, 400, 25, maxHealth, 0.35);
    }

    public static final String THE_BLAZE_NAME = "鹰眼工厂实验体";
    public Mob setAttributesThenSpawnOfBlade(Level level, Vec3 pos) {
        Blaze entity = new Blaze(EntityType.BLAZE, level);
        MobSpawn.setMobCustomName(entity, Te.s(THE_BLAZE_NAME, CustomStyle.styleOfHarbinger), 260);
        double maxHealth = 7500 * Math.pow(10, 4) * (1 + 0.75 * (players.size() - 1));
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(entity, 260, 7500, 480,
                480, 0.4, 3, 0.6, 350, 25,
                maxHealth, 0.35);
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(entity), List.of(
                new ItemAndRate(HarbingerItems.RAW_IRON_INGOT.get(), 0.5)
        ));
        entity.moveTo(pos);
        return entity;
    }

    @Override
    public void reward(Player player) {
        Random random = new Random();
        getRewardList().forEach(itemAndRate -> {
            itemAndRate.sendWithMSG(player, 1, (stack -> {
                if (stack.getItem() instanceof HarbingerWeaponMaterial) {
                    int tier = 0;
                    if (random.nextDouble() < 0.2) {
                        tier = random.nextInt(6, 10);
                    } else {
                        tier = random.nextInt(0, 6);
                    }
                    HarbingerWeaponMaterial.setQualityTier(stack, tier);
                    Compute.formatBroad(Te.s("鹰眼工厂", CustomStyle.styleOfHarbinger),
                            Te.s(player.getDisplayName(), "获得了", stack));
                }
            }));
        });
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.moontainBoss);
    }

    @Override
    public Component allowRewardCondition() {
        return NoTeamInstanceModule.AllowRewardCondition.BLACK_CASTLE_WEAPON;
    }

    @Override
    public List<ItemAndRate> getRewardList() {
        return List.of(
                new ItemAndRate(HarbingerItems.HARBINGER_INGOT.get(), 1),
                new ItemAndRate(HarbingerItems.HARBINGER_CURIO.get(), 0.02),
                new ItemAndRate(HarbingerItems.HARBINGER_HEART.get(), 0.1),
                new ItemAndRate(HarbingerItems.HARBINGER_ROD.get(), 0.01),
                new ItemAndRate(HarbingerItems.HARBINGER_WEAPON_CORE.get(), 0.01),
                new ItemAndRate(HarbingerItems.HARBINGER_SWORD_BLADE.get(), 0.01),
                new ItemAndRate(HarbingerItems.HARBINGER_STRING.get(), 0.01),
                new ItemAndRate(HarbingerItems.HARBINGER_MIRROR.get(), 0.01),
                new ItemAndRate(HarbingerItems.SAKURA_INDUSTRY_INGOT.get(), 0.01)
        );
    }

    @Override
    public double judgeDamage(Player player, Mob mob, double originDamage) {
        if (MobSpawn.getMobOriginName(mob).equals(THE_HARBINGER_NAME)) {
            originDamage *= (1 - 0.91);
            if (!blazes.isEmpty() && blazes.stream().anyMatch(LivingEntity::isAlive)) return 0;
            return Math.min(originDamage, mob.getMaxHealth() * 0.01);
        }
        return originDamage;
    }

    @Override
    public void clear() {
        stage = -1;
        blazes.forEach(mob -> {
            mob.remove(Entity.RemovalReason.KILLED);
        });
        blazes.clear();
        super.clear();
    }

    @Override
    public String getKillCountDataKey() {
        return "HarbingerInstance";
    }
}
