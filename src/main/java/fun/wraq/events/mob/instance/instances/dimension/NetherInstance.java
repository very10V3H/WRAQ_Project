package fun.wraq.events.mob.instance.instances.dimension;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignited_Revenant_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.system.element.Element;
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

public class NetherInstance extends NoTeamInstance {

    private static NetherInstance instance;

    public static String mobName = "燃魂";

    public static NetherInstance getInstance() {
        if (instance == null) {
            instance = new NetherInstance(new Vec3(529, 64, -540), 50, 60, new Vec3(529, 64, -540),
                    Component.literal("燃魂").withStyle(CustomStyle.styleOfPower));
        }
        return instance;
    }

    public NetherInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, 90);
    }

    @Override
    public MobAttributes getMainMobAttributes() {
        double maxHealth = 125000 * (1 + 0.75 * (Math.max(1, players.size()) - 1));
        return new MobAttributes(400, 55, 55, 0.35, 3, 0.2, 20, 0, maxHealth, 0.3);
    }

    @Override
    public Element.Unit getElementUnit() {
        return new Element.Unit(Element.fire, 3);
    }

    @Override
    public void summonModule(Level level) {
        Ignited_Revenant_Entity entity = new Ignited_Revenant_Entity(ModEntities.IGNITED_REVENANT.get(), level);
        entity.setBaby(true);
        MobSpawn.setMobCustomName(entity, Component.literal("燃魂").withStyle(CustomStyle.styleOfPower), 90);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(entity), 90);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(entity, getMainMobAttributes());
        entity.moveTo(pos);
        level.addFreshEntity(entity);
        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(entity.getDisplayName(),
                BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getNearPlayers(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
        mobList.add(entity);
        MobSpawn.setCanNotAddSlowDownOrImprison(entity);
    }

    @Override
    public void exReward(Player player) {
        Guide.trigV2(player, Guide.StageV2.NETHER_BOSS);
    }

    @Override
    public boolean allowReward(Player player) {
        return true;
    }

    @Override
    public Component allowRewardCondition() {
        return Te.s("");
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(new ItemAndRate(ModItems.NETHER_QUARTZ.get(), 8),
                new ItemAndRate(ModItems.NETHER_IMPRINT.get(), 0.25),
                new ItemAndRate(ModItems.RUBY.get(), 8),
                new ItemAndRate(ModItems.REVENANT_GOLDEN_HELMET.get(), 0.01),
                new ItemAndRate(ModItems.NETHER_HAND.get(), 0.08),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GOLD_COIN_BAG.get(), 0.1));
    }

    @Override
    public String getKillCountDataKey() {
        return "Revenant";
    }
}
