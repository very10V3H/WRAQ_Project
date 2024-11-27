package fun.wraq.process.system.randomevent.impl.killmob;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.randomevent.RandomEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public abstract class KillMobEvent extends RandomEvent {

    // 一般而言，对于随机击杀怪物事件，在此事件范围内的玩家都可以获得同等奖励
    // 对于击杀怪物的玩家，则可以获得额外奖励

    // 前期的事件主要掉落水晶碎片、金币、声望、启示之书等物品

    // 雨林村遇袭事件 - 掠夺者（前期）√
    // 纽维庙洞穴蜘蛛大量刷怪事件 - 洞穴蜘蛛（前期）√
    // 旭升岛海盗事件 - 掠夺者（前期）√
    // 海岸村遇袭事件 - 掠夺者（前期）√
    // 史莱姆王事件 - 大史莱姆（前期）√

    protected List<Mob> mobList = new ArrayList<>();

    public KillMobEvent(ResourceKey<Level> dimension, Vec3 pos, List<Component> beginAnnouncement,
                        List<Component> finishAnnouncement, List<Component> overTimeAnnouncement, MinecraftServer server) {
        super(dimension, pos, beginAnnouncement, finishAnnouncement, overTimeAnnouncement, server);
    }

    protected abstract void summonAndSetMobList();

    @Override
    protected void beginAction() {
        Level level = server.getLevel(dimension);
        if (level == null) {
            broad(Te.s("随机事件异常，请联系铁头"));
            return;
        }
        summonAndSetMobList();
    }

    @Override
    protected void tick() {
        if (isCarryingOut && Tick.get() % 200 == 0) {
            List<Mob> leftAliveMobList = mobList.stream().filter(LivingEntity::isAlive).toList();
            players.forEach(player -> {
                sendFormatMSG(player, Te.s("还有", leftAliveMobList.size() + "只", ChatFormatting.RED, "怪物存活，位于:"));
                for (int i = 0; i < Math.min(5, leftAliveMobList.size()); i++) {
                    Mob mob = leftAliveMobList.get(i);
                    Vec3 pos = mob.position();
                    sendFormatMSG(player, Te.s("(" + (int) pos.x + "," + (int) pos.y + "," + (int) pos.z + ")"));
                }
                if (leftAliveMobList.size() > 5) {
                    sendFormatMSG(player, Te.s("..."));
                }
            });
        }
    }

    @Override
    protected boolean finishCondition() {
        return mobList.stream().allMatch(mob -> {
            return !mob.isAlive();
        });
    }

    @Override
    protected void finishAction() {
        List<ItemStack> rewardList = getRewardList();
        players.forEach(player -> {
            rewardList.forEach(stack -> {
                InventoryOperation.itemStackGiveWithMSG(player, new ItemStack(stack.getItem(), stack.getCount()));
            });
            additionReward(player);
        });
    }

    @Override
    public void reset() {
        mobList.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
        mobList.clear();
    }

    protected abstract List<ItemStack> getRewardList();
    protected abstract void additionReward(Player player);

    public void onKillMob(Player player, Mob mob) {
        if (mobList.contains(mob)) {
            players.add(player);
        }
    }

    @Override
    protected String getDataKey() {
        return "KILL_MOB_EVENT";
    }
}
