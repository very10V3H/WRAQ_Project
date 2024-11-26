package fun.wraq.process.system.randomevent;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public abstract class RandomEvent {

    // 统计玩家参与的事件数

    // 第一类事件 - 击杀怪物事件
    // 第二类事件 - 寻找隐藏方块/物品/动物事件
    // 第三类事件 - 玩家到达指定地点 - 空投事件
    // 第四类事件 - 保护指定目标事件
    // 第五类事件 - 挖掘指定大型方块堆事件
    // 第六类事件 - 使用雪球击中空中目标事件

    protected final ResourceKey<Level> dimension;
    protected final Vec3 pos; // 事件发生地点
    protected final List<Component> beginAnnouncement; // 事件开始的通知
    protected boolean isCarryingOut = false;
    protected final MinecraftServer server;
    protected final Level level;
    protected int beginTick;

    public RandomEvent(ResourceKey<Level> dimension, Vec3 pos, List<Component> beginAnnouncement, MinecraftServer server) {
        this.dimension = dimension;
        this.pos = pos;
        this.beginAnnouncement = beginAnnouncement;
        this.server = server;
        this.level = server.getLevel(dimension);
    }

    protected abstract void beginAction();// 事件开始的行动
    protected abstract void tick(); // 事件进行中的操作
    protected abstract boolean endCondition(); // 事件结束的条件
    protected abstract void endAction(); // 事件结束的行动

    public void begin() {
        isCarryingOut = true;
        beginTick = Tick.get();
        server.getPlayerList().getPlayers().forEach(serverPlayer -> {
            beginAnnouncement.forEach(serverPlayer::sendSystemMessage);
        });
        beginAction();
    }

    public void handleTick() {
        if (isCarryingOut) {
            tick();
        }
        if (endCondition()) {
            endAction();
        }
    }

    public void end() {
        isCarryingOut = false;
        endAction();
    }

    public void broad(Component content) {
        server.getPlayerList().getPlayers().forEach(serverPlayer -> {
            Compute.sendFormatMSG(serverPlayer, Te.s("随机事件", CustomStyle.styleOfFlexible),
                    content);
        });
    }

}
