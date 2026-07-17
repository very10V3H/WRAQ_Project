package fun.wraq.process.system.buff;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.HudUtil;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.USE.MobEffectHudS2CPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class BuffSystem {
    public static List<GatherEntity> gatherEntityList = new ArrayList<>();

    public static void removeEffectLastTime(Player player, Item item) {
        HudUtil.removeEffectLastTime(player, item);
    }

    public static void removeEffectLastTime(Player player, String url) {
        HudUtil.removeEffectLastTime(player, url);
    }

    public static void removeEffectLastTimeByItemId(Player player, String itemId) {
        HudUtil.removeEffectLastTimeByItemId(player, itemId);
    }

    public static void sendEffectLastTime(Player player, ItemStack itemStack, int tickCount) {
        HudUtil.sendEffectLastTime(player, itemStack, tickCount);
    }

    public static void sendEffectLastTimeToClientPlayer(Item item, int level, int tick, boolean noTime) {
        HudUtil.sendEffectLastTimeToClientPlayer(item, level, tick, noTime);
    }

    public static void sendEffectLastTime(Player player, Item item, int tickCount) {
        HudUtil.sendEffectLastTime(player, item, tickCount);
    }

    public static void sendEffectLastTime(Player player, ItemStack itemStack, int tickCount, int level, boolean forever) {
        HudUtil.sendEffectLastTime(player, itemStack, tickCount, level, forever);
    }

    public static void sendEffectLastTime(Player player, Item item, int level, boolean forever) {
        HudUtil.sendEffectLastTime(player, item, level, forever);
    }

    public static void sendEffectLastTimeByItemId(Player player, String itemId, int level, boolean forever) {
        HudUtil.sendEffectLastTimeByItemId(player, itemId, level, forever);
    }

    public static void sendEffectLastTime(Player player, Item item, int tickCount, int level, boolean forever) {
        HudUtil.sendEffectLastTime(player, item, tickCount, level, forever);
    }

    public static void sendEffectLastTime(Player player, String url, int level, boolean forever) {
        HudUtil.sendEffectLastTime(player, url, level, forever);
    }

    public static void sendEffectLastTime(Player player, String url, int tickCount, int level, boolean forever) {
        HudUtil.sendEffectLastTime(player, url, tickCount, level, forever);
    }

    public static void sendCoolDownTime(Player player, Item item, int tickCount) {
        HudUtil.sendCoolDownTime(player, item, tickCount);
    }

    public static void sendCoolDownTime(Player player, ItemStack itemStack, int tickCount) {
        HudUtil.sendCoolDownTime(player, itemStack, tickCount);
    }

    public static void sendCoolDownTime(Player player, String url, int tickCount) {
        HudUtil.sendCoolDownTime(player, url, tickCount);
    }

    public static void sendDebuffTime(Player player, String url, int tickCount, int level, boolean forever) {
        HudUtil.sendDebuffTime(player, url, tickCount, level, forever);
    }

    public static void sendDebuffTime(Player player, Item item, int tickCount, int level) {
        HudUtil.sendDebuffTime(player, item, tickCount, level);
    }

    public static void sendDebuffTime(Player player, String url, int tickCount) {
        HudUtil.sendDebuffTime(player, url, tickCount);
    }

    public static void removeDebuffTime(Player player, String url) {
        HudUtil.removeDebuffTime(player, url);
    }

    public static void removeDebuffTime(Player player, Item item) {
        HudUtil.removeDebuffTime(player, item);
    }

    public static void gather(int TickCount) {
        gatherEntityList.removeIf(gatherEntity -> gatherEntity.tick < TickCount);
        gatherEntityList.forEach(gatherEntity -> {
            if (gatherEntity.livingEntity instanceof Mob mob) {
                if (!Compute.MonsterCantBeMove(mob)) {
                    gatherEntity.livingEntity.setDeltaMovement(
                            gatherEntity.pos.subtract(gatherEntity.livingEntity.position()).scale(0.2));
                }
            } else if (gatherEntity.livingEntity instanceof Player player) {
                Compute.sendMotionPacketToPlayer(player,
                        gatherEntity.pos.subtract(gatherEntity.livingEntity.position()).scale(0.2));
            }
        });
    }

    public static void causeGatherEffect(LivingEntity livingEntity, int lastTick, Vec3 gatherPos) {
        gatherEntityList.add(new GatherEntity(livingEntity, Tick.get() + lastTick, gatherPos));
    }

    public static void sendMobEffectHudToNearPlayer(Mob mob, Item icon, String tag, int lastTick, int level, boolean forever) {
        List<? extends Entity> list = Compute.getNearEntity(mob, Player.class, 16);
        list.stream().filter(e -> e instanceof Player).forEach(p -> {
            ServerPlayer serverPlayer = (ServerPlayer) p;
            ModNetworking.sendToClient(new MobEffectHudS2CPacket(mob.getId(), "item/" + icon, tag, lastTick, level, forever), serverPlayer);
        });
    }

    public static void sendMobEffectHudToNearPlayer(Mob mob, String url, String tag, int lastTick, int level, boolean forever) {
        List<? extends Entity> list = Compute.getNearEntity(mob, Player.class, 16);
        list.stream().filter(e -> e instanceof Player).forEach(p -> {
            ServerPlayer serverPlayer = (ServerPlayer) p;
            ModNetworking.sendToClient(new MobEffectHudS2CPacket(mob.getId(), url, tag, lastTick, level, forever), serverPlayer);
        });
    }

    public static void removeMobEffectHudToNearPlayer(Mob mob, Item icon, String tag) {
        removeMobEffectHudToNearPlayer(mob, "item" + icon, tag);
    }

    public static void removeMobEffectHudToNearPlayer(Mob mob, String url, String tag) {
        List<? extends Entity> list = Compute.getNearEntity(mob, Player.class, 16);
        list.stream().filter(e -> e instanceof Player).forEach(p -> {
            ServerPlayer serverPlayer = (ServerPlayer) p;
            ModNetworking.sendToClient(new MobEffectHudS2CPacket(mob.getId(), url, tag, 0, 0, false), serverPlayer);
        });
    }

    public record GatherEntity(LivingEntity livingEntity, int tick, Vec3 pos) {
    }
}
