package fun.wraq.process.system.randomevent;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class RandomEvent {

    // 统计玩家参与的事件数

    // 第一类事件 - 击杀怪物事件 基本完善
    // 第二类事件 - 寻找隐藏方块/物品/动物事件（不太可行）
    // 第三类事件 - 玩家到达指定地点 - 空投事件 尽快到达 基本完善
    // 第四类事件 - 保护指定目标事件
    // 第五类事件 - 挖掘指定大型方块堆事件 基本完善
    // 第六类事件 - 使用雪球击中空中目标事件

    // 根据等级分档给奖励

    protected final ResourceKey<Level> dimension;
    protected final Vec3 pos;
    protected final List<Component> beginAnnouncement; // 事件开始的通知
    protected final List<Component> finishAnnouncement; // 事件结束的通知
    protected final List<Component> overTimeAnnouncement; // 事件超时，强制结束的通知
    protected boolean isCarryingOut = false;
    protected final MinecraftServer server;
    protected int beginTick;
    protected Set<Player> players = new HashSet<>(); // 参与者们
    public boolean hasWorldSoul5Reward = false;

    public RandomEvent(ResourceKey<Level> dimension, Vec3 pos, List<Component> beginAnnouncement,
                       List<Component> finishAnnouncement, List<Component> overTimeAnnouncement, MinecraftServer server) {
        this.dimension = dimension;
        this.pos = pos;
        this.beginAnnouncement = beginAnnouncement;
        this.finishAnnouncement = finishAnnouncement;
        this.overTimeAnnouncement = overTimeAnnouncement;
        this.server = server;
    }

    protected abstract void beginAction();// 事件开始的行动
    protected abstract void tick(); // 事件进行中的操作
    protected abstract boolean finishCondition(); // 事件结束的条件
    protected abstract void finishAction(); // 事件结束的行动
    public abstract void reset(); // 事件重置为初始状态
    protected abstract String getDataKey();

    public void begin() {
        isCarryingOut = true;
        beginTick = Tick.get();
        beginAnnouncement.forEach(this::broad);
        if (hasWorldSoul5Reward) {
            Component component = ModItems.worldSoul5.get().getDefaultInstance().getDisplayName();
            broad(Te.s("这是一个带有", component, "奖励的", "随机事件!", CustomStyle.styleOfLife,
                    "参与即可获得", component));
        }
        beginAction();
    }

    public void handleTick() {
        if (isCarryingOut) {
            tick();
            if (beginTick + 6000 < Tick.get()) {
                overTimeAnnouncement.forEach(this::broad);
                end();
            } else if (finishCondition()) {
                finishAnnouncement.forEach(this::broad);
                players.forEach(player -> {
                    RandomEventData.incrementTimes(player, getDataKey());
                });
                if (hasWorldSoul5Reward) {
                    players.forEach(player -> {
                        try {
                            rewardWorldSoul5(player);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                finishAction();
                end();
            }
        }
    }

    public void end() {
        isCarryingOut = false;
        players.clear();
        reset();
    }

    public void broad(Component content) {
        server.getPlayerList().getPlayers().forEach(serverPlayer -> {
            Compute.sendFormatMSG(serverPlayer, Te.s("随机事件", CustomStyle.styleOfFlexible),
                    content);
        });
    }

    protected void sendFormatMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("随机事件", CustomStyle.styleOfFlexible), content);
    }

    public void setWorldSoul5Reward(boolean has) {
        hasWorldSoul5Reward = has;
    }

    private void rewardWorldSoul5(Player player) throws SQLException {
        int times = RandomEventData.getWorldSoul5DailyGetTimes(player);
        Component component = ModItems.worldSoul5.get().getDefaultInstance().getDisplayName();
        if (RandomEventData.getWorldSoul5DailyGetTimes(player) < 8) {
            Tower.givePlayerStar(player, 3, "随机事件");
            RandomEventData.incrementWorldSoul5DailyGetTimes(player);
            ++times;
            if (times > 0) {
                sendFormatMSG(player, Te.s("今天还能从", "随机事件", CustomStyle.styleOfFlexible,
                        "中，获取", 8 - times + "次", CustomStyle.styleOfWorld, component, "奖励!"));
            } else {
                sendFormatMSG(player, Te.s("今天从", "随机事件", CustomStyle.styleOfFlexible, "获取",
                        component, "的次数已经用完了，记得明天还要来参与哦!"));
            }
        } else {
            sendFormatMSG(player, Te.s("今天从", "随机事件", CustomStyle.styleOfFlexible, "获取",
                    component, "的次数已经用完了，记得明天还要来参与哦!"));
        }
    }

    public Level level() {
        return server.getLevel(dimension);
    }
}
