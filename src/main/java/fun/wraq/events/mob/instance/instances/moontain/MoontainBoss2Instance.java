package fun.wraq.events.mob.instance.instances.moontain;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.moontain.MoontainItems;
import net.mcreator.borninchaosv.entity.SirPumpkinheadEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MoontainBoss2Instance extends NoTeamInstance {

    private static MoontainBoss2Instance instance;

    public static String mobName = "望山骑士";
    public static Style style = CustomStyle.styleOfMoontain;

    public static MoontainBoss2Instance getInstance() {
        if (instance == null) {
            instance = new MoontainBoss2Instance(new Vec3(1978, 215, -881), 15, 60, new Vec3(1978, 215, -881),
                    Component.literal(mobName).withStyle(style));
        }
        return instance;
    }

    public MoontainBoss2Instance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, 220);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
    }

    @Override
    public void summonModule(Level level) {
        SirPumpkinheadEntity entity =
                new SirPumpkinheadEntity(BornInChaosV1ModEntities.SIR_PUMPKINHEAD.get(), level);
        MobSpawn.setMobCustomName(entity, Component.literal(mobName).withStyle(style), 220);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(entity), 215);
        double maxHealth = 1500 * Math.pow(10, 4) * (1 + 0.75 * (players.size() - 1));
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(entity, 3200, 300, 300,
                0.4, 5, 0.5, 125, 0,
                maxHealth, 0.45);
        entity.setHealth(entity.getMaxHealth());
        entity.moveTo(pos);
        level.addFreshEntity(entity);
        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(entity.getDisplayName(),
                BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getNearPlayers(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
        mobList.add(entity);
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.moontainBoss);
    }

    @Override
    public Component allowRewardCondition() {
        return NoTeamInstanceModule.AllowRewardCondition.BLACK_CASTLE_WEAPON;
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(new ItemAndRate(MoontainItems.NUGGET.get(), 1),
                new ItemAndRate(MoontainItems.STONE_FRAGMENT.get(), 7),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1));
    }

    @Override
    public String getKillCountDataKey() {
        return "MoontainBoss2";
    }
}
