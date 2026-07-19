/** AI-Generated, 2026-07-19 */
package fun.wraq.process.system.endlessinstance.instance;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.system.endlessinstance.DailyEndlessInstance;
import fun.wraq.process.system.xp.MyExpSystem;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.IPlantable;

import java.time.LocalDate;
import java.util.*;

public class DailyExpInstance extends DailyEndlessInstance {

    private static DailyExpInstance instance;

    public static DailyExpInstance getInstance() {
        if (instance == null) {
            instance = new DailyExpInstance(
                    Te.s("尸潮", CustomStyle.styleOfForest),
                    new Vec3(3966.5, 87, 3152.5),
                    Tick.s(45),
                    10,
                    1
            );
        }
        return instance;
    }

    private static final Component INSTANCE_TYPE = Component.literal("尸潮").withStyle(CustomStyle.styleOfForest);

    // 经验
    private int expGivenKills = 0;
    private static final int MAX_EXP_KILLS = 100;
    private static final int EXP_PER_KILL = 5;

    // 奖励里程碑
    private final Set<Integer> rewardedMilestones = new HashSet<>();
    private static final List<Integer> MILESTONES = List.of(100, 150, 200, 300);

    // 每日进入次数
    private static final int MAX_DAILY_ENTRIES = 3;
    private static final String NBT_DATE_KEY = "DailyExp_Date";
    private static final String NBT_COUNT_KEY = "DailyExp_Count";

    // 上一 tick 击杀数（用于检测新击杀）
    private int lastTickKillCount = 0;

    // 提示消息冷却（玩家名 → 上次发送 tick）
    private final Map<String, Long> lastHintTick = new HashMap<>();

    private DailyExpInstance(Component name, Vec3 pos, int lastTick, int maxMobNum, int refreshDelayTick) {
        super(name, pos, lastTick, maxMobNum, refreshDelayTick);
    }

    @Override
    public void start(Player player) {
        super.start(player);
        expGivenKills = 0;
        rewardedMilestones.clear();
        lastTickKillCount = 0;
        // 记录一次进入次数
        incrementDailyEntry(player);
    }

    @Override
    public void commonTick(Level level) {
        super.commonTick(level);

        if (!isChallenging()) return;

        int currentKillCount = getKillCount();
        ServerPlayer player = Compute.getPlayerByName(getChallengingPlayerName());
        if (player == null) return;

        // 为新击杀发放经验（仅前 100 只）
        int newKills = currentKillCount - lastTickKillCount;
        if (newKills > 0) {
            for (int i = 0; i < newKills && expGivenKills < MAX_EXP_KILLS; i++) {
                MyExpSystem.giveExpToPlayer(player, 5, 0, 25);
                player.giveExperiencePoints(EXP_PER_KILL);
                expGivenKills++;
            }
            if (expGivenKills >= MAX_EXP_KILLS && expGivenKills - newKills < MAX_EXP_KILLS) {
                sendFormatMSG(player, Te.s("已达到经验上限（", MAX_EXP_KILLS, "只），后续击杀不再获得经验。", ChatFormatting.GRAY));
            }
        }
        lastTickKillCount = currentKillCount;

        // 检查里程碑奖励
        for (int milestone : MILESTONES) {
            if (currentKillCount >= milestone && !rewardedMilestones.contains(milestone)) {
                rewardedMilestones.add(milestone);
                giveMilestoneReward(player, milestone);
            }
        }
    }

    private void giveMilestoneReward(ServerPlayer player, int milestone) {
        sendFormatMSG(player, Te.s("击杀数达到 ", String.valueOf(milestone), "！", ChatFormatting.GOLD));
        // TODO: 填入实际奖励
    }

    @Override
    protected List<Mob> summonMob(Level level) {
        List<Mob> mobs = new ArrayList<>();
        Player player = Compute.getPlayerByName(getChallengingPlayerName());
        if (player == null) return mobs;

        Vec3 spawnPos = findSpawnPos(level, player);
        if (spawnPos == null) return mobs;

        Zombie zombie = new Zombie(EntityType.ZOMBIE, level);
        MobSpawn.setMobCustomName(zombie, Te.s("僵僵僵僵僵尸"), 25);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombie, 25,
                50, 0, 0, 0, 0,
                0, 0, 0, 200, 0.24);
        zombie.moveTo(spawnPos);
        level.addFreshEntity(zombie);
        mobs.add(zombie);
        return mobs;
    }

    /**
     * 在中心点 20 格内找一个可刷怪的地面位置。
     * 若玩家在中心点 25 格内，还需距玩家至少 5 格。
     */
    private Vec3 findSpawnPos(Level level, Player player) {
        Random random = new Random();
        Vec3 center = getPos();
        boolean playerNearby = player.distanceToSqr(center) <= 25;

        for (int attempt = 0; attempt < 20; attempt++) {
            double angle = random.nextDouble() * 2 * Math.PI;
            double dist = 5 + random.nextDouble() * 15;
            double x = center.x + Math.cos(angle) * dist;
            double z = center.z + Math.sin(angle) * dist;

            // 玩家在范围内时，距玩家至少 5 格
            if (playerNearby && player.distanceToSqr(x, player.getY(), z) < 25) continue;

            int groundY = findGroundY(level, x, z, (int) player.getY());
            if (groundY != Integer.MIN_VALUE) {
                return new Vec3(x, groundY + 0.5, z);
            }
        }
        return null;
    }

    /**
     * 扫描找到地面（实心方块上方为空气/植物的位置）。
     */
    private int findGroundY(Level level, double x, double z, int playerY) {
        int startY = playerY + 15;
        int endY = Math.max(playerY - 10, level.getMinBuildHeight());
        for (int y = startY; y > endY; y--) {
            BlockPos pos = new BlockPos((int) x, y, (int) z);
            Block block = level.getBlockState(pos).getBlock();
            Block above = level.getBlockState(pos.above()).getBlock();
            boolean blockIsSolid = !(block instanceof IPlantable
                    || block instanceof SnowLayerBlock
                    || block.equals(Blocks.AIR)
                    || block instanceof LeavesBlock
                    || block instanceof RotatedPillarBlock);
            boolean aboveIsFree = above instanceof IPlantable
                    || above instanceof SnowLayerBlock
                    || above.equals(Blocks.AIR);
            if (blockIsSolid && aboveIsFree) {
                return y + 1;
            }
        }
        return Integer.MIN_VALUE;
    }

    @Override
    protected void reward(Player player) {
        int kills = getKillCount();
        // TODO: 追加结算奖励
    }

    @Override
    protected boolean onRightClickTrig(Player player) {
        // 每日次数耗尽 → 静默忽略，不刷提示
        if (!checkDailyLimit(player)) {
            sendFormatMSG(player, Te.s("今天的挑战次数已耗尽，明天再来挑战吧!"));
            return false;
        }
        if (!player.isShiftKeyDown()) {
            // 潜行提示每 10s 只发一次，避免法杖/武器右键刷屏
            long lastSent = lastHintTick.getOrDefault(player.getName().getString(), 0L);
            if (Tick.get() - lastSent >= Tick.s(10)) {
                lastHintTick.put(player.getName().getString(), (long) Tick.get());
                sendFormatMSG(player, Te.s("潜行右键开始尸潮挑战"));
            }
            return false;
        }
        return true;
    }

    @Override
    protected List<Component> getTrigConditionDescription() {
        return List.of(
                Te.s("潜行右键开始挑战"),
                Te.s("每日经验副本"),
                Te.s("45 秒内尽可能多地击杀僵尸"),
                Te.s("每日 ", MAX_DAILY_ENTRIES, " 次", ChatFormatting.GREEN)
        );
    }

    @Override
    protected void sendFormatMSG(Player player, Component component) {
        Compute.sendFormatMSG(player, INSTANCE_TYPE, component);
    }

    // ==================== 每日进入次数管理 ====================

    private boolean checkDailyLimit(Player player) {
        CompoundTag data = player.getPersistentData();
        String today = LocalDate.now().toString();
        String lastDate = data.getString(NBT_DATE_KEY);
        if (!today.equals(lastDate)) {
            data.putString(NBT_DATE_KEY, today);
            data.putInt(NBT_COUNT_KEY, 0);
            return true;
        }
        return data.getInt(NBT_COUNT_KEY) < MAX_DAILY_ENTRIES;
    }

    private void incrementDailyEntry(Player player) {
        CompoundTag data = player.getPersistentData();
        data.putInt(NBT_COUNT_KEY, data.getInt(NBT_COUNT_KEY) + 1);
        int remaining = MAX_DAILY_ENTRIES - data.getInt(NBT_COUNT_KEY);
        sendFormatMSG(player, Te.s("今日剩余次数：", remaining, ChatFormatting.AQUA));
    }
}
