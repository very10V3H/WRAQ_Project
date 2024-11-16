package fun.wraq.events.mob.instance.instances.dimension;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignited_Revenant_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void tickModule() {
        if (mobList.isEmpty()) return;
    }

    @Override
    public void summonModule(Level level) {
        Ignited_Revenant_Entity entity = new Ignited_Revenant_Entity(ModEntities.IGNITED_REVENANT.get(), level);

        entity.setBaby(true);
        MobSpawn.setMobCustomName(entity, Component.literal("燃魂").withStyle(CustomStyle.styleOfPower), 90);

        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(entity), 90);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(entity, 400, 55, 55, 0.35,
                3, 0.2, 20, 0, 250000, 0.3);

        entity.moveTo(pos);
        level.addFreshEntity(entity);

        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(entity.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getPlayerList(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
        mobList.add(entity);
    }

    @Override
    public void rewardModule(Player player) {
        List<ItemAndRate> rewardList = getRewardList();
        rewardList.forEach(itemAndRate -> {
            itemAndRate.sendWithMSG(player, 1);
        });

        String name = player.getName().getString();
        if (!MobSpawn.tempKillCount.containsKey(name)) MobSpawn.tempKillCount.put(name, new HashMap<>());
        Map<String, Integer> map = MobSpawn.tempKillCount.get(name);
        map.put(mobName, map.getOrDefault(mobName, 0) + 1);
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.nether);
    }

    @Override
    public Component allowRewardCondition() {
        return Component.literal("需要将任意").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普莱尼戒指").withStyle(CustomStyle.styleOfPlain)).
                append(Component.literal("提升至").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("3阶").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("，方能获取奖励").withStyle(ChatFormatting.WHITE));
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(new ItemAndRate(ModItems.NetherQuartz.get(), 8),
                new ItemAndRate(ModItems.NETHER_IMPRINT.get(), 0.25),
                new ItemAndRate(ModItems.Ruby.get(), 8),
                new ItemAndRate(ModItems.netherHand.get(), 0.08),
                new ItemAndRate(ModItems.WorldSoul2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1));
    }

}
