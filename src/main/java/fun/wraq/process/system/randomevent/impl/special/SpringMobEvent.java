package fun.wraq.process.system.randomevent.impl.special;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.system.randomevent.RandomAdditionalRewardEvent;
import fun.wraq.process.system.randomevent.impl.killmob.KillMobEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpringMobEvent extends KillMobEvent {

    public SpringMobEvent(ResourceKey<Level> dimension, Vec3 pos, List<Component> readyAnnouncement,
                          List<Component> beginAnnouncement, List<Component> finishAnnouncement,
                          List<Component> overTimeAnnouncement, MinecraftServer server,
                          List<ItemAndRate> rewardList, RandomAdditionalRewardEvent randomAdditionalRewardEvent) {
        super(dimension, pos, readyAnnouncement, beginAnnouncement, finishAnnouncement, overTimeAnnouncement,
                server, rewardList, randomAdditionalRewardEvent);
    }

    public static final String mobName = "";
    public static int luckyNumber = 0;

    @Override
    protected void beginAction() {
        super.beginAction();
        luckyNumber = RandomUtils.nextInt(0, 10);
        broad(Te.s("幸运数字", ChatFormatting.LIGHT_PURPLE, "为 ", luckyNumber, ChatFormatting.LIGHT_PURPLE));
    }

    @Override
    protected void summonAndSetMobList() {

    }

    @Override
    protected void tick() {
        super.tick();

    }

    @Override
    protected void finishAction() {

    }

    @Override
    public void reset() {
        super.reset();
        playerCausedDamageMap.clear();
        causedDamageCount = BigDecimal.ZERO;
    }

    public static Map<String, Double> playerCausedDamageMap = new HashMap<>();
    public static BigDecimal causedDamageCount = BigDecimal.ZERO;
    public static void onPlayerCauseDamage(Player player, Mob mob, double damage) {
        if (MobSpawn.getMobOriginName(mob).equals(mobName)) {
            String name = player.getName().getString();
            playerCausedDamageMap.compute(name, (k, v) -> v == null ? damage : v + damage);
            causedDamageCount = causedDamageCount.add(BigDecimal.valueOf(damage));
        }
    }

    public static void announceCurrentCausedDamageCount() {

    }

    public static double onMobWithStandDamage(Mob mob) {
        return MobSpawn.getMobOriginName(mob).equals(mobName) ? 0 : 1;
    }

    public static void onFinishReward() {
        playerCausedDamageMap.forEach((k, v) -> {
            ServerPlayer serverPlayer = Compute.getPlayerByName(k);
            if (serverPlayer == null) return;

            double damageCount = v;
            int numberCount = 0;
            while (damageCount > 1) {
                if (damageCount % 10 == luckyNumber) {
                    ++numberCount;
                }
                damageCount /= 10;
            }

            // 根据number count给予特殊奖励
        });

    }
}
