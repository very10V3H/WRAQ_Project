package fun.wraq.series.overworld.chapter1.mine;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.PowerLogic;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import static fun.wraq.common.Compute.detectPlayerPickMob;
import static fun.wraq.common.Compute.playerItemCoolDown;

public class MinePower extends WraqPower {

    private final int tier;
    public MinePower(Properties p_41383_, int tier) {
        super(p_41383_);
        this.tier = tier;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public Component getActiveName() {
        return Te.s("覆岩盖石", CustomStyle.styleOfStone);
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        WraqPower.basicCauseManaDamageDescription(components, effect[tier]);
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.StoneElement("1 + 100%")));
        components.add(Component.literal(" 为").withStyle(ChatFormatting.WHITE).
                append(Component.literal("自身").withStyle(ChatFormatting.GREEN)).
                append(Component.literal("周围所有玩家提供")).
                append(Te.s("2层", "「岩」", CustomStyle.styleOfStone)));
        components.add(Te.s(" 「岩」", CustomStyle.styleOfStone,
                "持续3s，每层会为玩家抵挡一次", "伤害", CustomStyle.styleOfRed));
        return components;
    }

    @Override
    public int getCoolDownSecond() {
        return CoolDownTime[tier];
    }

    @Override
    public double getManaCost() {
        return ManaCost[tier];
    }

    @Override
    public Component getSuffix() {
        return null;
    }

    @Override
    public void release(Player player) {
        List.of(ModItems.MINE_POWER.get(), ModItems.MINE_POWER_1.get(),
                        ModItems.MINE_POWER_2.get(), ModItems.MINE_POWER_3.get()).
                forEach(item -> {
                    playerItemCoolDown(player, item, MinePower.CoolDownTime[tier]);
                });

        Level dimension = player.level();
        double effect = MinePower.effect[tier];
        Vec3 targetPos = player.pick(15, 0, false).getLocation();
        if (detectPlayerPickMob(player) != null) targetPos = detectPlayerPickMob(player).position();

        List<Mob> mobList = dimension.getEntitiesOfClass(Mob.class,
                AABB.ofSize(targetPos, 20, 20, 20));
        Vec3 finalTargetPos = targetPos;
        mobList.forEach(mob -> {
            Vec3 PosVec = mob.position().subtract(finalTargetPos);
            if (PosVec.length() <= 6) {
                ParticleProvider.createBreakBlockParticle(mob, Blocks.STONE);
                Damage.causeRateApDamageWithElement(player, mob, effect, true,
                        Element.stone, ElementValue.ElementValueJudgeByType(player, Element.stone) + 1);
                PowerLogic.PlayerPowerEffectToMob(player, mob);
            }
        });

        List<Player> players = dimension.getEntitiesOfClass(Player.class,
                AABB.ofSize(player.position(), 20, 20, 20));
        players.forEach(eachPlayer -> {
            if (eachPlayer.distanceTo(player) < 6) {
                ParticleProvider.createBreakBlockParticle(eachPlayer, Blocks.STONE);
                addStoneEffect(eachPlayer);
            }
        });
        ParticleProvider.dustParticle(player, player.getEyePosition(), 6, 36,
                CustomStyle.styleOfStone.getColor().getValue());
    }

    public static int[] ManaCost = {
            100, 115, 130, 150
    };

    public static int[] CoolDownTime = {
            16, 14, 12, 10
    };

    public static double[] effect = {
            1.2, 1.35, 1.5, 1.7
    };

    public static Map<Player, Integer> stoneEffectCountMap = new WeakHashMap<>();
    public static Map<Player, Integer> stoneEffectExpiredTickMap = new WeakHashMap<>();

    public static void addStoneEffect(Player player) {
        stoneEffectCountMap.put(player, 2);
        stoneEffectExpiredTickMap.put(player, Tick.get() + Tick.s(3));
        Compute.sendEffectLastTime(player, ModItems.MINE_POWER.get(), Tick.s(3));
    }

    public static double onPlayerWithstand(Player player) {
        if (stoneEffectCountMap.getOrDefault(player, 0) > 0
                && stoneEffectExpiredTickMap.getOrDefault(player, 0) > Tick.get()) {
            stoneEffectCountMap.compute(player, (k, v) -> v == null ? v = 0 : v - 1);
            if (stoneEffectCountMap.getOrDefault(player, 0) <= 0) {
                Compute.removeEffectLastTime(player, ModItems.MINE_POWER.get());
                stoneEffectCountMap.remove(player);
            }
            ParticleProvider.createBreakBlockParticle(player, Blocks.STONE);
            return 0;
        }
        return 1;
    }
}
