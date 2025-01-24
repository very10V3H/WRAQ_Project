package fun.wraq.events.mob.instance.instances.element;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Amethyst_Crab_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.events.mob.instance.instances.dimension.NetherInstance;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.missions.series.dailyMission.DailyMission;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurpleIronInstance extends NoTeamInstance {

    private static PurpleIronInstance instance;

    public static String mobName = "紫水晶巨蟹";

    public static PurpleIronInstance getInstance() {
        if (instance == null) {
            instance = new PurpleIronInstance(new Vec3(1171, -36, -172), 50, 60, new Vec3(1171, -36, -172),
                    Component.literal("紫水晶巨蟹").withStyle(CustomStyle.styleOfPurpleIron));
        }
        return instance;
    }

    public PurpleIronInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, 120);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
        Mob mob = this.mobList.get(0);
        if (mob == null || !mob.isAlive()) return;
        Element.ElementProvider(mob, Element.stone, 3);
    }

    @Override
    public void summonModule(Level level) {
        Amethyst_Crab_Entity amethystCrabEntity = new Amethyst_Crab_Entity(ModEntities.AMETHYST_CRAB.get(), level);

        MobSpawn.setMobCustomName(amethystCrabEntity, Component.literal("紫水晶巨蟹").withStyle(CustomStyle.styleOfPurpleIron), 120);

        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(amethystCrabEntity), 120);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(amethystCrabEntity, 1000, 120, 120,
                0.35, 3, 0.2, 25, 0, 30 * Math.pow(10, 4), 0.3);

        amethystCrabEntity.setHealth(amethystCrabEntity.getMaxHealth());

        amethystCrabEntity.moveTo(pos);
        level.addFreshEntity(amethystCrabEntity);
        mobList.add(amethystCrabEntity);

        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(amethystCrabEntity.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getNearPlayers(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
    }

    @Override
    public void rewardModule(Player player) {
        List<ItemAndRate> rewardList = getRewardList();
        List<Item> purpleIronQUWeapons = List.of(ModItems.PurpleIronSword.get(),
                ModItems.PurpleIronBow.get(), ModItems.PurpleIronSceptre.get());
        rewardList.forEach(itemAndRate -> {
            Item item = itemAndRate.getItemStack().getItem();
            if (itemAndRate.sendWithMSG(player, 1)) {
                if (purpleIronQUWeapons.contains(item)) {
                    NoTeamInstanceModule.putPlayerAllowReward(player,
                            NoTeamInstanceModule.AllowRewardKey.iceKnight, true);
                }
            }
        });
        DailyMission.addCount(player, DailyMission.purpleIronInstanceCountMap);

        String name = player.getName().getString();
        if (!MobSpawn.tempKillCount.containsKey(name)) MobSpawn.tempKillCount.put(name, new HashMap<>());
        Map<String, Integer> map = MobSpawn.tempKillCount.get(name);
        map.put(mobName, map.getOrDefault(mobName, 0) + 1);
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player), 120);
        Guide.trigV2(player, Guide.StageV2.PURPLE_IRON_BOSS);
    }

    @Override
    public boolean allowReward(Player player) {
        if (MobSpawn.totalKillCount.getOrDefault(player.getName().getString(), new HashMap<>())
                .getOrDefault(NetherInstance.mobName, 0) >= 50) {
            NoTeamInstanceModule.putPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.purpleIron, true);
        }
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.purpleIron);
    }

    @Override
    public Component allowRewardCondition() {
        return Component.literal("需要击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal("50次").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("燃魂").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("，方能获取奖励").withStyle(ChatFormatting.WHITE));
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(new ItemAndRate(ModItems.PurpleIronBud1.get(), 1),
                new ItemAndRate(new ItemStack(ModItems.GEM_PIECE.get(), 8), 0.1),
                new ItemAndRate(ModItems.PurpleIronBud2.get(), 0.1),
                new ItemAndRate(ModItems.PurpleIronSword.get(), 0.01),
                new ItemAndRate(ModItems.PurpleIronBow.get(), 0.01),
                new ItemAndRate(ModItems.PurpleIronSceptre.get(), 0.01),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1));
    }
}
