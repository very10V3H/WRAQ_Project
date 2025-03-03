package fun.wraq.series.overworld.sakura.bunker.mob;

import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Ignited_Berserker_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.teamInstance.instances.harbinger.HarbingerInstance;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.sakura.bunker.BunkerItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class BunkerInstance extends NoTeamInstance {

    private static BunkerInstance instance;

    public static final String mobName = "熔蚀";
    public static final Style STYLE = CustomStyle.BUNKER_STYLE;
    public static final int XP_LEVEL = 275;

    public static BunkerInstance getInstance() {
        if (instance == null) {
            instance = new BunkerInstance(new Vec3(3912, -8, 2001), 50, 60,
                    new Vec3(3912, -8, 2001),
                    Component.literal(mobName).withStyle(STYLE));
        }
        return instance;
    }

    public BunkerInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, XP_LEVEL);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;

        mobList.forEach(mob -> {
            Element.provideElement(mob, Element.fire, 6);
        });
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
    public void summonModule(Level level) {
        Ignited_Berserker_Entity entity = new Ignited_Berserker_Entity(ModEntities.IGNITED_BERSERKER.get(), level);
        MobSpawn.setMobCustomName(entity, Te.s(mobName, STYLE), XP_LEVEL);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(entity), XP_LEVEL);
        double maxHealth = 3 * Math.pow(10, 8) * (1 + 0.75 * (players.size() - 1));
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(entity, 10000, 700, 700,
                0.35, 3, 0.6, 500, 25,
                maxHealth, 0.6);
        entity.moveTo(pos);
        level.addFreshEntity(entity);
        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(entity.getDisplayName(),
                BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getNearPlayers(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
        mobList.add(entity);
    }

    @Override
    public boolean allowReward(Player player) {
        return MobSpawn.getPlayerKillCount(player, HarbingerInstance.getInstance().description.getString()) >= 20;
    }

    @Override
    public Component allowRewardCondition() {
        return Te.s("需要通关", "20次", STYLE, "鹰眼工厂", CustomStyle.styleOfHarbinger, "，方能获取奖励。");
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(
                new ItemAndRate(BunkerItems.BUNKER_BOSS_SOUL.get(), 1),
                new ItemAndRate(BunkerItems.BUNKER_CURIO_0.get(), 0.01),
                new ItemAndRate(BunkerItems.BUNKER_ATTACK_CREST_0.get(), 0.05),
                new ItemAndRate(BunkerItems.BUNKER_ATTACK_CREST_1.get(), 0.0125),
                new ItemAndRate(BunkerItems.BUNKER_ATTACK_CREST_2.get(), 0.0025),
                new ItemAndRate(BunkerItems.BUNKER_ATTACK_CREST_3.get(), 0.0001),
                new ItemAndRate(BunkerItems.BUNKER_MANA_CREST_0.get(), 0.05),
                new ItemAndRate(BunkerItems.BUNKER_MANA_CREST_1.get(), 0.0125),
                new ItemAndRate(BunkerItems.BUNKER_MANA_CREST_2.get(), 0.0025),
                new ItemAndRate(BunkerItems.BUNKER_MANA_CREST_3.get(), 0.0001),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1));
    }

    @Override
    public String getKillCountDataKey() {
        return "BunkerInstance";
    }
}
