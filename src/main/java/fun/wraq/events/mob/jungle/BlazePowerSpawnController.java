package fun.wraq.events.mob.jungle;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.loot.C2LootItems;
import fun.wraq.process.func.damage.Dot;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import net.mcreator.borninchaosv.entity.SearedSpiritEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlazePowerSpawnController extends JungleMobSpawnController {

    public static final String MOB_NAME = "熔岩之能";
    public static final Style STYLE = CustomStyle.styleOfPower;
    public static final int XP_LEVEL = 60;

    public static BlazePowerSpawnController instance;

    public static BlazePowerSpawnController getInstance() {
        if (instance == null) {
            instance = new BlazePowerSpawnController(Te.s(MOB_NAME, STYLE), new Vec3(1363, 78, 100),
                    new Vec3(1384, 100, 118), new Vec3(1334, 68, 64), XP_LEVEL, 48, Tick.min(3));
        }
        return instance;
    }

    public BlazePowerSpawnController(Component name, Vec3 descriptionPos,
                                     Vec3 mobUpperBoundary, Vec3 mobLowerBoundary,
                                     int mobXpLevel, double detectionRadius, int refreshInterval) {
        super(name, descriptionPos, mobUpperBoundary, mobLowerBoundary, mobXpLevel, detectionRadius, refreshInterval);
    }

    @Override
    public void tryToReward(Player player) {
        getRewardItemList().forEach(itemAndRate -> {
            itemAndRate.sendWithMSG(player, 1);
        });
        buffExpiredTickMap.put(Name.get(player), Tick.get() + Tick.min(8));
        Compute.sendEffectLastTime(player, "item/blaze_powder", Tick.min(8), 0,false);
        sendMSG(player, Te.s("获得了持续", "8min", ChatFormatting.AQUA, "的", "余烬之冠", STYLE,
                "。在持续时间内，你的普通攻击将会对目标造成基于你最大生命值的持续伤害，并施加减速，持续3s。"));
    }

    public static Map<String, Integer> buffExpiredTickMap = new HashMap<>();

    public static void onPlayerHitMob(Player player, Mob mob) {
        if (buffExpiredTickMap.getOrDefault(Name.get(player), 0) > Tick.get()) {
            Dot.addDotOnMob(mob,
                    new Dot(0, player.getHealth() * 0.25, 2, Tick.get() + Tick.s(3),
                            Name.get(player), false, "RedBuff"));
            Compute.sendMobEffectHudToNearPlayer(mob, "item/blaze_powder",
                    "RedBuff",Tick.s(3), 0, false);
            Compute.addSlowDownEffect(mob, Tick.s(3), 1);
        }
    }

    @Override
    public void spawnMob(Level level) {
        SearedSpiritEntity mob = new SearedSpiritEntity(BornInChaosV1ModEntities.SEARED_SPIRIT.get(), level);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), XP_LEVEL);
        MobSpawn.setMobCustomName(mob, Te.s(name, STYLE), XP_LEVEL);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, 200, 35, 35,
                0.3, 2, 0.1, 3, 10,
                4500, 0.3);
        mob.moveTo(new Vec3(1363, 78, 100));
        level.addFreshEntity(mob);
        mobs.add(mob);
    }

    @Override
    public List<ItemAndRate> getRewardItemList() {
        return List.of(
                new ItemAndRate(ModItems.VOLCANO_SOUL.get(), 30),
                new ItemAndRate(ModItems.SILVER_COIN.get(), 8),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 1),
                new ItemAndRate(ModItems.VOLCANO_CREST_0.get(), 0.4),
                new ItemAndRate(ModItems.VOLCANO_CREST_1.get(), 0.1),
                new ItemAndRate(ModItems.VOLCANO_CREST_2.get(), 0.02),
                new ItemAndRate(ModItems.VOLCANO_CREST_3.get(), 0.004),
                new ItemAndRate(ModItems.FIRE_ELEMENT_PIECE_0.get(), 4),
                new ItemAndRate(NewRuneItems.VOLCANO_NEW_RUNE.get(), 0.02),
                new ItemAndRate(C2LootItems.SEARED_SPIRIT_STICK.get(), 0.02)
        );
    }

    @Override
    public double modifyMobWithstandDamage(Mob mob, Player player) {
        double rate = mob.getHealth() / mob.getMaxHealth();
        mob.setHealth((float) (mob.getMaxHealth() * (rate - 0.01)));
        return 0;
    }
}
