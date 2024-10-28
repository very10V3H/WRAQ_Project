package fun.wraq.events.mob.instance.instances.moontain;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.moontain.MoontainItems;
import net.mcreator.borninchaosv.entity.SirPumpkinheadWithoutHorseEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoontainBoss1Instance extends NoTeamInstance {

    private static MoontainBoss1Instance instance;

    public static String mobName = "望山武士";
    public static Style style = CustomStyle.styleOfMoontain;

    public static MoontainBoss1Instance getInstance() {
        if (instance == null) {
            instance = new MoontainBoss1Instance(new Vec3(1995, 199, -877), 15, 200, new Vec3(1995, 199, -877),
                    Component.literal(mobName).withStyle(style));
        }
        return instance;
    }

    public MoontainBoss1Instance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
    }

    @Override
    public void summonModule(Level level) {
        SirPumpkinheadWithoutHorseEntity entity =
                new SirPumpkinheadWithoutHorseEntity(BornInChaosV1ModEntities.SIR_PUMPKINHEAD_WITHOUT_HORSE.get(), level);

        MobSpawn.setMobCustomName(entity, Component.literal(mobName).withStyle(style), 200);

        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(entity), 200);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(entity, 2800, 200, 200,
                0.4, 5, 0.3, 70, 25,
                1250 * Math.pow(10, 4), 0.45);

        entity.setHealth(entity.getMaxHealth());

        entity.moveTo(pos);
        level.addFreshEntity(entity);

        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(entity.getDisplayName(),
                BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getPlayerList(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
        mobList.add(entity);
    }

    @Override
    public void rewardModule(Player player) {
        List<ItemAndRate> rewardList = getRewardList();
        rewardList.forEach(itemAndRate -> itemAndRate.dropWithBounding(lastMob, 1, player));

        String name = player.getName().getString();
        if (!MobSpawn.tempKillCount.containsKey(name)) MobSpawn.tempKillCount.put(name, new HashMap<>());
        Map<String, Integer> map = MobSpawn.tempKillCount.get(name);
        map.put(mobName, map.getOrDefault(mobName, 0) + 1);
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player), 200);
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.moontainBoss);
    }

    @Override
    public Component allowRewardCondition() {
        return Component.literal("需要至少").withStyle(ChatFormatting.WHITE).
                append(Component.literal("锻造").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("过").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1件").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("暗黑城堡武器").withStyle(CustomStyle.styleOfCastleCrystal)).
                append(Component.literal("，方能获取奖励。").withStyle(ChatFormatting.WHITE));
    }

    public static List<ItemAndRate> getRewardList() {
        return List.of(new ItemAndRate(MoontainItems.FRAGMENT.get(), 1),
                new ItemAndRate(MoontainItems.STONE_FRAGMENT.get(), 6),
                new ItemAndRate(ModItems.WorldSoul2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1));
    }
}
