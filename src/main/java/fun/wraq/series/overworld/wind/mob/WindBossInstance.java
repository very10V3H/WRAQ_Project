package fun.wraq.series.overworld.wind.mob;

import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.hostile.illagers.StormCaster;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.divine.DivineUtils;
import fun.wraq.series.overworld.wind.WindItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class WindBossInstance extends NoTeamInstance {

    private static WindBossInstance instance;

    public static String mobName = "唤风者";
    public Mob boss;
    public static final Style STYLE = CustomStyle.styleOfWind;
    public static final double MAX_HEALTH = 8000 * Math.pow(10, 4);
    public static final int XP_LEVEL = 225;

    public static WindBossInstance getInstance() {
        if (instance == null) {
            instance = new WindBossInstance(new Vec3(1756, 157, -1525), 60, 60,
                    new Vec3(1756, 157, -1525), Te.s(mobName, STYLE));
        }
        return instance;
    }

    public WindBossInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, XP_LEVEL);
    }

    @Override
    public MobAttributes getMainMobAttributes() {
        return new MobAttributes(3500, 350, 350, 0.4, 3, 0.5, 125, 25, MAX_HEALTH, 0.45);
    }

    @Override
    public void summonModule(Level level) {
        StormCaster mob = new StormCaster(ModEntityType.STORM_CASTER.get(), level);
        MobSpawn.setMobCustomName(mob, Te.s(mobName, STYLE), XP_LEVEL);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), XP_LEVEL);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, getMainMobAttributes());
        MobSpawn.setStainArmorOnMob(mob, STYLE);
        mob.moveTo(pos);
        level.addFreshEntity(mob);
        mobList.add(mob);
        boss = mob;
        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(mob.getDisplayName(),
                BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getNearPlayers(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
        MobSpawn.setCanNotAddSlowDownOrImprison(mob);
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.sakuraBoss);
    }

    @Override
    public Component allowRewardCondition() {
        return NoTeamInstanceModule.AllowRewardCondition.ICE_KNIGHT_WEAPON;
    }

    @Override
    public Item getSummonAndRewardNeedItem() {
        return ModItems.REASON.get();
    }

    @Override
    public int getRewardNeedItemCount() {
        return 5;
    }

    @Override
    public List<ItemAndRate> getRewardList() {
        return List.of(
                new ItemAndRate(WindItems.WIND_CRYSTAL_1.get(), 0.8),
                new ItemAndRate(WindItems.WIND_SOUL.get(), 16),
                new ItemAndRate(ModItems.GOLD_COIN.get(), 1),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 3),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.5),
                new ItemAndRate(ModItems.GOLD_COIN_BAG.get(), 0.1)
        );
    }

    public static void sendFormatMSG(Player player, Component content) {
        DivineUtils.sendMSG(player, content);
    }

    @Override
    public String getKillCountDataKey() {
        return "WindBoss";
    }
}
