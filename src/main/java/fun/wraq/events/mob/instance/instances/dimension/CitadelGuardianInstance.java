package fun.wraq.events.mob.instance.instances.dimension;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ender_Guardian_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.end.citadel.CitadelItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CitadelGuardianInstance extends NoTeamInstance {

    private static CitadelGuardianInstance instance;
    public static String mobName = "影珀守卫";
    public static Style style = CustomStyle.styleOfEnd;
    public static Level dimension;
    public static CitadelGuardianInstance getInstance() {
        if (instance == null) {
            instance = new CitadelGuardianInstance(new Vec3(1075, 39, -704), 30, 100, new Vec3(1075, 39, -704),
                    Component.literal(mobName).withStyle(style));
        }
        return instance;
    }

    public CitadelGuardianInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, 250);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
        Mob boss = mobList.get(0);
        summonMobList.stream()
                .filter(mob -> mob instanceof Monster)
                .map(mob -> (Monster) mob)
                .forEach(mob -> {
                    if (dimension != null && !getNearPlayers(dimension).isEmpty()) {
                        mob.setTarget(getNearPlayers(dimension).get(0));
                    }
                });
        if (Tick.get() % 100 == 0) {
            skill1(boss);
        }
        if (blockInfoList.isEmpty() && boss.getHealth() / boss.getMaxHealth() < 0.5) {
            clearBlock(boss.level());
        }
    }

    @Override
    public void summonModule(Level level) {
        dimension = level;
        Ender_Guardian_Entity entity =
                new Ender_Guardian_Entity(ModEntities.ENDER_GUARDIAN.get(), level);
        MobSpawn.setMobCustomName(entity, Component.literal(mobName).withStyle(style), this.level);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(entity), this.level);
        double maxHealth = 2500 * Math.pow(10, 4) * (1 + 0.75 * (players.size() - 1));
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(entity, 5000, 480, 480,
                0.4, 5, 0.6, 225, 25,
                maxHealth, 0.35);
        entity.setHealth(entity.getMaxHealth());

        entity.moveTo(pos);
        level.addFreshEntity(entity);
        mobList.add(entity);
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.enderGuardian);
    }

    @Override
    public Component allowRewardCondition() {
        return Te.s("需要至少", "获得过", ChatFormatting.GREEN, "1件", ChatFormatting.AQUA,
                "望山装备", CustomStyle.styleOfMoontain, "，方能获取奖励");
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(
                new ItemAndRate(CitadelItems.CITADEL_CURIO_0.get(), 0.01),
                new ItemAndRate(CitadelItems.CITADEL_EQUIP_ENHANCER.get(), 0.05),
                new ItemAndRate(CitadelItems.CITADEL_PIECE.get(), 0.2),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1)
        );
    }

    @Override
    public void reset(int tick, boolean removeMob) {
        if (dimension != null) {
            resetBlock(dimension);
            getNearPlayers(dimension).forEach(player -> {
                player.teleportTo((ServerLevel) dimension, pos.x, pos.y, pos.z, Set.of(), 0, 0);
            });
            summonMobList.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
            summonMobList.clear();
        }
        super.reset(tick, removeMob);
    }

    // 技能1 - 每5秒召唤4只末影螨 + 1只潜影贝 被末影螨攻击会获得持续2s的致盲 被潜影贝攻击则会获得持续3s的沉默
    public static List<Mob> summonMobList = new ArrayList<>();
    public static final String ENDER_MITE_NAME = "影珀屑";
    public static final String SHULKER_NAME = "影珀簇";

    public void skill1(Mob boss) {
        Random random = new Random();
        if (summonMobList.size() < 15) {
            for (int i = 0; i < 4; i++) {
                summonEnderMite(boss.level(), boss.position().add(random.nextInt(3), 0.25, random.nextInt(3)));
            }
            summonShulker(boss.level(), boss.position().add(random.nextInt(3), 0.25, random.nextInt(3)));
        }
    }

    public static void summonEnderMite(Level level, Vec3 pos) {
        Endermite entity = new Endermite(EntityType.ENDERMITE, level);
        MobSpawn.setMobCustomName(entity, Component.literal(ENDER_MITE_NAME).withStyle(CustomStyle.styleOfEnd), 250);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(entity), 250);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(entity, 4000, 360, 240, 0.4,
                5, 0.4, 120, 25, 500 * Math.pow(10, 4), 0.4);
        entity.moveTo(pos);
        level.addFreshEntity(entity);
        summonMobList.add(entity);
    }

    public static void summonShulker(Level level, Vec3 pos) {
        Shulker entity = new Shulker(EntityType.SHULKER, level);
        MobSpawn.setMobCustomName(entity, Component.literal(SHULKER_NAME).withStyle(CustomStyle.styleOfEnd), 250);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(entity), 250);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(entity, 4000, 240, 360, 0.4,
                5, 0.4, 120, 25, 1000 * Math.pow(10, 4), 0.4);
        entity.moveTo(pos);
        level.addFreshEntity(entity);
        summonMobList.add(entity);
    }

    private static void withstandEnderMite(Player player, Mob mob) {
        if (MobSpawn.getMobOriginName(mob).equals(ENDER_MITE_NAME)) {
            SpecialEffectOnPlayer.addBlindEffect(player, 20);
        }
    }

    private static void withstandShulker(Player player, Mob mob) {
        if (MobSpawn.getMobOriginName(mob).equals(SHULKER_NAME)) {
            SpecialEffectOnPlayer.addSilentEffect(player, 20);
        }
    }

    // 技能2 - 当守卫的生命值低于50%时 守卫将脚下的方块暂时搬移至其他位置 并给所有玩家施加5s的重伤/减速
    public record BlockInfo(BlockState blockState, BlockPos blockPos) {
    }

    public static List<BlockInfo> blockInfoList = new ArrayList<>();

    public static void clearBlock(Level level) {
        blockInfoList.clear();
        BlockPos startPos = new BlockPos(1054, 37, -725);
        BlockPos endPos = new BlockPos(1096, 38, -683);

        for (int i = startPos.getX(); i <= endPos.getX(); i++) {
            for (int j = startPos.getY(); j <= endPos.getY(); j++) {
                for (int k = startPos.getZ(); k <= endPos.getZ(); k++) {
                    BlockPos blockPos = new BlockPos(i, j, k);
                    BlockState blockState = level.getBlockState(blockPos);
                    blockInfoList.add(new BlockInfo(blockState, blockPos));
                    level.destroyBlock(blockPos, false);
                }
            }
        }
    }

    public static void resetBlock(Level level) {
        blockInfoList.forEach(blockInfo -> {
            level.setBlockAndUpdate(blockInfo.blockPos, blockInfo.blockState);
        });
        blockInfoList.clear();
    }

    // 技能3 - 灾变神力 - 每当守卫的生命值减少10%，其就会回复其最大生命值9%的血量
    public static double healthReductionNum = 0;

    public static void mobWithstandDamage(Mob mob, double damage) {
        if (MobSpawn.getMobOriginName(mob).equals(mobName)) {
            healthReductionNum += damage;
        }
        if (healthReductionNum >= mob.getMaxHealth() * 0.1) {
            Compute.mobHeal(mob, healthReductionNum * 0.9);
            healthReductionNum = 0;
        }
    }

    // 技能4 - 恃强凌弱 被守卫攻击时，若生命值低于40%，则获得持续1s的眩晕 / 重伤
    // 技能5 - 影切割 守卫生命值少于25%时，将直接斩杀附近生命值低于25%的玩家
    private static void playerWithstandDamageFromBoss(Player player, Mob mob) {
        if (MobSpawn.getMobOriginName(mob).equals(mobName)) {
            if (player.getHealth() / player.getMaxHealth() < 0.4) {
                SpecialEffectOnPlayer.addVertigoEffect(player, Tick.s(1));
            }
            if (mob.getHealth() / mob.getMaxHealth() < 0.25 && player.getHealth() / mob.getMaxHealth() < 0.25) {
                Damage.causeDirectDamageToPlayer(mob, player, player.getMaxHealth() * 4);
            }
        }
    }

    public static void playerWithstandDamage(Player player, Mob mob) {
        playerWithstandDamageFromBoss(player, mob);
        withstandEnderMite(player, mob);
        withstandShulker(player, mob);
    }

    @Override
    public String getKillCountDataKey() {
        return "CitadelGuardian";
    }
}
