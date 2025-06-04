package fun.wraq.events.mob.instance.instances.sakura;

import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.entities.entities.Boss2.Boss2;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SakuraBossInstance extends NoTeamInstance {

    private static SakuraBossInstance instance;

    public static String mobName = "突见忍";

    public static SakuraBossInstance getInstance() {
        if (instance == null) {
            instance = new SakuraBossInstance(new Vec3(2257, 140, 1694), 30, 60, new Vec3(2257, 140, 1694),
                    Component.literal("突见忍").withStyle(CustomStyle.styleOfSakura));
        }
        return instance;
    }

    public SakuraBossInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, 150);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
    }

    @Override
    public void summonModule(Level level) {
        Boss2 sakuraBoss = new Boss2(ModEntityType.Boss2.get(), level);

        sakuraBoss.setBaby(true);
        MobSpawn.setMobCustomName(sakuraBoss, Component.literal("突见忍").withStyle(CustomStyle.styleOfSakura), 150);

        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(sakuraBoss), 150);
        double maxHealth = 100 * Math.pow(10, 4) * (1 + 0.75 * (players.size() - 1));
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(sakuraBoss, 1500, 110, 110,
                0.4, 3, 0.25, 50, 0,
                maxHealth, 0.35);

        sakuraBoss.setHealth(sakuraBoss.getMaxHealth());

        sakuraBoss.moveTo(pos);
        level.addFreshEntity(sakuraBoss);

        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(sakuraBoss.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getNearPlayers(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
        mobList.add(sakuraBoss);
    }

    @Override
    public void exReward(Player player) {
        Guide.trigV2(player, Guide.StageV2.SAKURA_BOSS);
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.sakuraBoss);
    }

    @Override
    public Component allowRewardCondition() {
        return NoTeamInstanceModule.AllowRewardCondition.ICE_KNIGHT_WEAPON;
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(new ItemAndRate(ModItems.Boss2Piece.get(), 1),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GOLD_COIN_BAG.get(), 0.1));
    }

    @Override
    public String getKillCountDataKey() {
        return "SakuraBoss";
    }
}
