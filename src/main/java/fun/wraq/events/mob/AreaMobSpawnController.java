/** AI-Generated, 2026-07-12 */
package fun.wraq.events.mob;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.element.Element;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.IPlantable;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 区域玩家附近随机刷怪控制器。
 * <p>
 * 与 {@link MobSpawnController}（固定坐标刷怪）不同，此抽象类实现"玩家在区域内时，
 * 在其附近随机位置生成怪物"的机制。适用于大范围野外区域（森林、沙漠等）的动态刷怪。
 * </p>
 *
 * <h3>核心特性</h3>
 * <ul>
 *   <li>区域边界检测 —— 复用 {@link MobSpawnController.Boundary}</li>
 *   <li>战斗中不刷怪 —— 通过 {@link Damage#playerIsInBattle(Player)} 检测</li>
 *   <li>防卡方块 —— 检查生成位置是否为有效方块（植物/雪层/空气）</li>
 *   <li>掉落表复用 —— 使用相同的 {@link MobSpawn#dropList} 机制</li>
 *   <li>频率/数量可控 —— 子类可覆盖配置方法调整刷怪参数</li>
 * </ul>
 *
 * <h3>使用方式</h3>
 * <pre>{@code
 * public class ForestWolfSpawner extends AreaMobSpawnController {
 *     public ForestWolfSpawner(Level level) {
 *         super(Te.s("森林野狼"), level, 10,
 *             List.of(new Boundary(new Vec3(500,200,500), new Vec3(400,50,400))));
 *     }
 *     public Mob mobItemAndAttributeSet() { ... }
 *     public MobAttributes getMobAttributes() { ... }
 *     public String getKillCountDataKey() { return "ForestWolf"; }
 * }}</pre>
 */
public abstract class AreaMobSpawnController {

    // ==================== 静态注册 ====================

    private static final List<AreaMobSpawnController> overworldControllers = new ArrayList<>();
    private static final List<AreaMobSpawnController> netherControllers = new ArrayList<>();
    private static final List<AreaMobSpawnController> endControllers = new ArrayList<>();

    /**
     * 获取所有已注册的区域刷怪控制器（跨维度）。
     */
    public static List<AreaMobSpawnController> getAllControllers() {
        List<AreaMobSpawnController> all = new ArrayList<>();
        all.addAll(overworldControllers);
        all.addAll(netherControllers);
        all.addAll(endControllers);
        return all;
    }

    /**
     * 根据维度获取对应的控制器列表。
     */
    public static List<AreaMobSpawnController> getListForDimension(ResourceKey<Level> dimension) {
        if (dimension.equals(Level.OVERWORLD)) return overworldControllers;
        if (dimension.equals(Level.NETHER)) return netherControllers;
        if (dimension.equals(Level.END)) return endControllers;
        return List.of();
    }

    /**
     * 将控制器注册到指定维度对应的静态列表中（幂等，可重复调用）。
     * <p>用于替代构造函数中的自动注册，使得单例子类在 {@code onServerStop} 清空列表后
     * 也能通过 {@code getInstance()} 重新注册。</p>
     */
    public static void registerController(AreaMobSpawnController controller, Level level) {
        if (level != null && !level.isClientSide) {
            List<AreaMobSpawnController> list = getListForDimension(level.dimension());
            if (!list.contains(controller)) {
                list.add(controller);
            }
        }
    }

    /**
     * 由 {@link MobSpawn#tick} 调用的静态入口，驱动所有区域刷怪控制器的 tick。
     */
    public static void tickAll(Level level, ResourceKey<Level> dimension) {
        List<AreaMobSpawnController> list = getListForDimension(dimension);
        list.forEach(controller -> {
            if (controller.level != level) {
                controller.level = level;
            }
            controller.handleTick();
        });
    }

    /**
     * 服务器停止时清理所有怪物。
     */
    public static void onServerStop() {
        getAllControllers().forEach(controller -> {
            controller.mobList.forEach(mob -> mob.discard());
            controller.mobList.clear();
        });
        overworldControllers.clear();
        netherControllers.clear();
        endControllers.clear();
    }

    // ==================== 实例字段 ====================

    public final Component mobName;
    public final int averageLevel;
    public Level level;
    public final List<Mob> mobList = new ArrayList<>();
    public final List<MobSpawnController.Boundary> multiBoundaryList;
    private int tickCounter;
    /** 上次有玩家在区域内的 server tick，用于无玩家时的自动清理。 */
    private long lastPlayerInBoundaryTick = 0;

    // ==================== 构造函数 ====================

    /**
     * @param mobName      怪物显示名称
     * @param level        所在世界
     * @param averageLevel 平均等级
     * @param boundaries   区域边界列表（玩家在这些边界内时才会刷怪）
     */
    public AreaMobSpawnController(Component mobName, Level level, int averageLevel,
                                  List<MobSpawnController.Boundary> boundaries) {
        this.mobName = mobName;
        this.level = level;
        this.averageLevel = averageLevel;
        this.multiBoundaryList = boundaries;
        this.tickCounter = 0;
    }

    // ==================== 抽象方法（子类必须实现） ====================

    /**
     * 创建怪物实体并设置装备/外观。
     * 每次刷怪时调用，应返回一个新的 Mob 实例。
     */
    public abstract Mob mobItemAndAttributeSet();

    /**
     * 返回怪物基础属性。
     */
    public abstract MobAttributes getMobAttributes();

    /**
     * 返回击杀计数数据键，用于持久化玩家的怪物击杀统计。
     * 必须全局唯一。
     */
    public abstract String getKillCountDataKey();

    // ==================== 可覆盖的配置方法 ====================

    /**
     * 每级等级提供的属性与掉落倍率加成。
     * 默认每高一级 +2.5%。公式: rate = 1 + (xpLevel - averageLevel) * 0.025。
     * <p>
     * 例如：averageLevel=10，生成等级=12 → rate = 1 + 2*0.025 = 1.05（+5%属性与掉落）
     */
    public double getLevelScalingPerLevel() {
        return 0.025;
    }

    /**
     * 根据怪物等级计算属性/掉落倍率。
     * 公式: 1 + (xpLevel - averageLevel) * getLevelScalingPerLevel()，最低 0.5。
     */
    public double getLevelScalingRate(int xpLevel) {
        double rate = 1 + (xpLevel - averageLevel) * getLevelScalingPerLevel();
        return Math.max(0.5, rate);
    }

    /**
     * 刷怪尝试频率（tick）。
     * 默认每 30 秒尝试一次。
     */
    public int getSpawnFrequencyTicks() {
        return Tick.s(10);
    }

    /**
     * 玩家周围最大刷怪距离。
     */
    public double getSpawnRadius() {
        return 24;
    }

    /**
     * 玩家周围最小刷怪距离（避免刷在玩家脸上）。
     */
    public double getMinSpawnDistance() {
        return 8;
    }

    /**
     * 每个玩家周围允许存在的同类怪物最大数量。
     */
    public int getMaxNearbyMobs() {
        return 5;
    }

    /**
     * 每个周期每个玩家的刷怪尝试次数。
     */
    public int getSpawnTryCount() {
        return 3;
    }

    /**
     * 全局同类怪物数量上限。
     */
    public int getMaxTotalMobs() {
        return 50;
    }

    /**
     * 全局怪物数量检查半径。
     */
    public int getGlobalCheckRadius() {
        return 96;
    }

    /**
     * 区域内无玩家时的怪物存活时间（tick）。
     * 超过此时间后区域内所有怪物将被自动清理。默认 40 秒。
     * <p>返回 0 或负数表示不启用此机制。</p>
     */
    public int getDespawnDelayTicks() {
        return Tick.s(40);
    }

    /**
     * 禁止刷怪的区域列表。
     * 子类可覆写此方法返回 {@link MobSpawnController.Boundary} 列表，
     * 若玩家在这些区域内将不会生成怪物。
     * <p>默认返回空列表（不排除任何区域）。</p>
     */
    public List<MobSpawnController.Boundary> getExcludedBoundaries() {
        return List.of();
    }

    // ==================== 可覆盖的方法（掉落/元素/tick/坐骑） ====================

    /**
     * 返回默认掉落表。
     */
    public List<ItemAndRate> getDropList() {
        return List.of();
    }

    /**
     * 返回按等级的掉落表。默认回退到 {@link #getDropList()}。
     */
    public List<ItemAndRate> getDropList(int xpLevel) {
        return getDropList();
    }

    /**
     * 返回元素属性。默认无元素。
     */
    public Element.Unit getElement() {
        return null;
    }

    /**
     * 每只存活的怪物每 tick 的回调。
     */
    public void eachMobTick(Mob mob) {
    }

    /**
     * 返回怪物骑乘的坐骑实体。默认无坐骑。
     */
    public LivingEntity getMounts() {
        return null;
    }

    // ==================== 核心 Tick 逻辑 ====================

    /**
     * 每个 tick 调用一次。内部处理频率控制、怪物清理、区域检测和刷怪。
     */
    public void handleTick() {
        // 1. 清理已死亡的怪物
        mobList.removeIf(mob -> !mob.isAlive());

        // 1.5 区域内无玩家时自动清理怪物
        if (getDespawnDelayTicks() > 0 && !multiBoundaryList.isEmpty()) {
            if (isAnyPlayerInBoundary()) {
                lastPlayerInBoundaryTick = Tick.get();
            } else {
                if (mobList.size() > 0 && Tick.get() - lastPlayerInBoundaryTick > getDespawnDelayTicks()) {
                    mobList.forEach(mob -> {
                        if (mob.isAlive()) mob.discard();
                    });
                    mobList.clear();
                    tickCounter = 0;
                    return;
                }
            }
        }

        // 2. 频率控制
        if (RandomUtils.nextDouble(0, 1) > 1.0 / getSpawnFrequencyTicks()) {
            return;
        }

        // 3. 处理存活怪物的每 tick 逻辑和边界检测
        judgeOverBoundary();

        // 4. 全局数量检查
        int globalCount = 0;
        for (AreaMobSpawnController controller : getListForDimension(level.dimension())) {
            if (controller.mobName.getString().equals(this.mobName.getString())) {
                globalCount += controller.mobList.size();
            }
        }
        if (globalCount >= getMaxTotalMobs()) {
            return;
        }

        // 5. 遍历在线玩家，为每个符合条件的玩家尝试刷怪
        List<ServerPlayer> players = level.getServer().getPlayerList().getPlayers();
        for (ServerPlayer player : players) {
            // 跳过创造/观察者模式
            if (player.isCreative() || player.isSpectator()) {
                continue;
            }
            // 跳过不在区域内的玩家
            if (!isPlayerInBoundary(player)) {
                continue;
            }
            // 跳过在禁止刷怪区域内的玩家
            if (isPlayerInExcludedZone(player)) {
                continue;
            }
            // 跳过战斗中的玩家
            if (Damage.playerIsInBattle(player)) {
                continue;
            }
            // 检查该玩家周围同类怪物数量
            int nearbyCount = countNearbyMobsOfType(player);
            if (nearbyCount >= getMaxNearbyMobs()) {
                continue;
            }
            // 尝试刷怪
            for (int i = 0; i < getSpawnTryCount(); i++) {
                Vec3 spawnPos = findValidSpawnPos(player);
                if (spawnPos != null) {
                    spawnMob(spawnPos);
                }
            }
        }
    }

    // ==================== 边界检测 ====================

    /**
     * 检测怪物是否越界，越界则传送回区域内随机位置。
     * 逻辑与 {@link MobSpawnController#judgeOverBoundary()} 一致。
     */
    private void judgeOverBoundary() {
        mobList.forEach(mob -> {
            if (mob != null && mob.isAlive()) {
                // 每 tick 回调
                if (getElement() != null) {
                    Element.provideElement(mob, getElement().type(), getElement().value());
                }
                eachMobTick(mob);

                // 越界检测
                if (!multiBoundaryList.isEmpty()) {
                    boolean mobIsInBoundary = false;
                    for (MobSpawnController.Boundary boundary : multiBoundaryList) {
                        if (mob.getX() > boundary.downPos().x && mob.getY() > boundary.downPos().y
                                && mob.getZ() > boundary.downPos().z && mob.getX() < boundary.upPos().x
                                && mob.getY() < boundary.upPos().y && mob.getZ() < boundary.upPos().z) {
                            mobIsInBoundary = true;
                            break;
                        }
                    }
                    if (!mobIsInBoundary) {
                        // 传送到区域内随机玩家的位置
                        Vec3 safePos = findRandomPositionInBoundary();
                        if (safePos != null) {
                            mob.moveTo(safePos);
                        }
                    }
                }
            }
        });
    }

    /**
     * 判断是否有任何在线玩家在定义的区域内。
     */
    private boolean isAnyPlayerInBoundary() {
        if (multiBoundaryList.isEmpty()) {
            return true; // 无边界限制，视为一直有玩家
        }
        if (level == null || level.getServer() == null) return false;
        for (ServerPlayer player : level.getServer().getPlayerList().getPlayers()) {
            if (isPlayerInBoundary(player)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断玩家是否在任意禁止刷怪区域内。
     * 若玩家处于排除区域，则不为此玩家刷怪。
     */
    private boolean isPlayerInExcludedZone(Player player) {
        List<MobSpawnController.Boundary> excluded = getExcludedBoundaries();
        if (excluded.isEmpty()) return false;
        for (MobSpawnController.Boundary boundary : excluded) {
            if (player.getX() > boundary.downPos().x && player.getY() > boundary.downPos().y
                    && player.getZ() > boundary.downPos().z && player.getX() < boundary.upPos().x
                    && player.getY() < boundary.upPos().y && player.getZ() < boundary.upPos().z) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断玩家是否在定义的区域内。
     */ private boolean isPlayerInBoundary(Player player) {
        if (multiBoundaryList.isEmpty()) {
            return true; // 无边界限制
        }
        for (MobSpawnController.Boundary boundary : multiBoundaryList) {
            if (player.getX() > boundary.downPos().x && player.getY() > boundary.downPos().y
                    && player.getZ() > boundary.downPos().z && player.getX() < boundary.upPos().x
                    && player.getY() < boundary.upPos().y && player.getZ() < boundary.upPos().z) {
                return true;
            }
        }
        return false;
    }

    /**
     * 在边界内随机找一个位置（用于越界怪物传送）。
     */
    private Vec3 findRandomPositionInBoundary() {
        if (multiBoundaryList.isEmpty()) return null;
        MobSpawnController.Boundary boundary = multiBoundaryList.get(
                new Random().nextInt(multiBoundaryList.size()));
        double x = boundary.downPos().x + Math.random() * (boundary.upPos().x - boundary.downPos().x);
        double y = boundary.downPos().y + Math.random() * (boundary.upPos().y - boundary.downPos().y);
        double z = boundary.downPos().z + Math.random() * (boundary.upPos().z - boundary.downPos().z);
        return new Vec3(x, y, z);
    }

    // ==================== 刷怪位置查找（防卡方块） ====================

    /**
     * 在玩家周围找一个有效的刷怪位置。
     * <p>
     * 在玩家周围 [{@link #getMinSpawnDistance()}, {@link #getSpawnRadius()}] 的环形区域内
     * 随机选择 X/Z，然后向下扫描找地面，确保脚部和头部方块为有效方块。
     * </p>
     *
     * @return 有效刷怪位置，未找到则返回 null
     */
    private Vec3 findValidSpawnPos(Player player) {
        Random random = new Random();
        double maxDist = getSpawnRadius();
        double minDist = getMinSpawnDistance();

        for (int attempt = 0; attempt < 10; attempt++) {
            // 随机角度和距离
            double angle = random.nextDouble() * 2 * Math.PI;
            double dist = minDist + random.nextDouble() * (maxDist - minDist);
            double x = player.getX() + Math.cos(angle) * dist;
            double z = player.getZ() + Math.sin(angle) * dist;

            // 从玩家 Y 坐标附近向下扫描找地面
            int groundY = findGroundY(player, x, z);
            if (groundY == Integer.MIN_VALUE) {
                continue;
            }

            // 检查脚部和头部是否为有效方块
            BlockPos feetPos = new BlockPos((int) x, groundY, (int) z);
            BlockPos headPos = feetPos.above();
            Block feetBlock = level.getBlockState(feetPos).getBlock();
            Block headBlock = level.getBlockState(headPos).getBlock();

            if (isValidSpawnBlock(feetBlock) && isValidSpawnBlock(headBlock)) {
                // 检查上方 3 格是否有遮挡（防止刷新在树冠下/树叶中）
                boolean hasClearance = true;
                for (int dy = 2; dy <= 4; dy++) {
                    Block checkBlock = level.getBlockState(feetPos.above(dy)).getBlock();
                    if (!(checkBlock instanceof IPlantable
                            || checkBlock instanceof SnowLayerBlock
                            || checkBlock.equals(Blocks.AIR)
                            || checkBlock instanceof LeavesBlock
                            || checkBlock instanceof RotatedPillarBlock)) {
                        hasClearance = false;
                        break;
                    }
                }
                if (hasClearance) {
                    return new Vec3(x + 0.5, groundY, z + 0.5);
                }
            }
        }
        return null;
    }

    /**
     * 在指定 X/Z 坐标找到地面 Y 值（实心方块上方是空气/植物的位置）。
     */
    private int findGroundY(Player player, double x, double z) {
        int startY = (int) player.getY() + 5;
        int endY = Math.max((int) player.getY() - 10, level.getMinBuildHeight());
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

    /**
     * 判断方块是否可作为怪物生成位置（非实心、非窒息方块）。
     */
    private boolean isValidSpawnBlock(Block block) {
        return block instanceof IPlantable
                || block instanceof SnowLayerBlock
                || block.equals(Blocks.AIR);
    }

    // ==================== 怪物生成 ====================

    /**
     * 在给定位置生成怪物。设置属性、掉落表、名称和坐骑。
     * 属性与掉落倍率基于等级浮动：每高一级 +{@link #getLevelScalingPerLevel()}*100%。
     */
    private void spawnMob(Vec3 pos) {
        Random r = new Random();
        Mob mob = this.mobItemAndAttributeSet();

        // 等级：averageLevel 上下浮动 5 级，最低 1 级
        int xpLevel = Math.max(1, averageLevel + 5 - r.nextInt(11));

        // 基于等级计算属性倍率与掉落倍率：每高1级 +2.5%（可覆盖）
        double levelRate = getLevelScalingRate(xpLevel);

        // 设置自定义名称（Lv.X 怪物名）
        Component customMobName = getMobName(mob);
        MobSpawn.setMobCustomName(mob, customMobName != null ? customMobName : mobName, xpLevel);

        // 设置属性（应用等级倍率）
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, getMobAttributes(), levelRate);

        // 设置掉落表 —— 复用 MobSpawn.dropList 机制
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(mob), getDropList());
        if (!getDropList(xpLevel).isEmpty()) {
            MobSpawn.dropList.put(mob.getName().getString(), getDropList(xpLevel));
        }

        // 注册等级掉落倍率到 mobXpDropRateMap，使 MobSpawn.drop() 能按等级调整掉落数量
        String originName = MobSpawn.getMobOriginName(mob);
        MobSpawn.MobBaseAttributes.mobXpDropRateMap
                .computeIfAbsent(originName, k -> new java.util.HashMap<>())
                .put(xpLevel, levelRate);

        // 移动到目标位置
        mob.moveTo(pos);

        // 加入世界
        this.mobList.add(mob);
        this.level.addFreshEntity(mob);

        // 处理坐骑
        LivingEntity mounts = getMounts();
        if (mounts != null) {
            mounts.moveTo(mob.position());
            level.addFreshEntity(mounts);
            mob.startRiding(mounts);
            MobSpawn.mountsMap.put(mounts, mob);
        }
    }

    // ==================== 辅助方法 ====================

    /**
     * 统计玩家周围同类怪物数量。
     */
    private int countNearbyMobsOfType(Player player) {
        int count = 0;
        double checkRadius = getSpawnRadius();
        String targetName = this.mobName.getString();
        for (AreaMobSpawnController controller : getListForDimension(level.dimension())) {
            if (controller.mobName.getString().equals(targetName)) {
                for (Mob mob : controller.mobList) {
                    if (mob.isAlive() && mob.position().distanceTo(player.position()) < checkRadius) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    protected Component getMobName(Mob mob) {
        return null;
    }
}
