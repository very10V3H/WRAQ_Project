package fun.wraq.events.mob.instance.instances.moontain;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.moontain.MoontainItems;
import net.mcreator.borninchaosv.entity.SirPumpkinheadWithoutHorseEntity;
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

public class MoontainBoss1Instance extends NoTeamInstance {

    private static MoontainBoss1Instance instance;

    public static String mobName = "望山武士";
    public static Style style = CustomStyle.styleOfMoontain;

    public static MoontainBoss1Instance getInstance() {
        if (instance == null) {
            instance = new MoontainBoss1Instance(new Vec3(1995, 199, -877), 15, 60, new Vec3(1995, 199, -877),
                    Te.s(mobName, style));
        }
        return instance;
    }

    public MoontainBoss1Instance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, 200);
    }

    @Override
    public MobAttributes getMainMobAttributes() {
        double maxHealth = 750 * Math.pow(10, 4) * (1 + 0.75 * (Math.max(1, players.size()) - 1));
        return new MobAttributes(2800, 240, 240, 0.4, 3, 0.4, 70, 0, maxHealth, 0.45);
    }

    @Override
    public void summonModule(Level level) {
        SirPumpkinheadWithoutHorseEntity entity =
                new SirPumpkinheadWithoutHorseEntity(BornInChaosV1ModEntities.SIR_PUMPKINHEAD_WITHOUT_HORSE.get(), level);
        MobSpawn.setMobCustomName(entity, Te.s(mobName, style), 200);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(entity), 200);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(entity, getMainMobAttributes());
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
        return List.of(new ItemAndRate(MoontainItems.FRAGMENT.get(), 1),
                new ItemAndRate(MoontainItems.STONE_FRAGMENT.get(), 6),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GOLD_COIN_BAG.get(), 0.1));
    }

    @Override
    public String getKillCountDataKey() {
        return "MoontainBoss1";
    }
}
