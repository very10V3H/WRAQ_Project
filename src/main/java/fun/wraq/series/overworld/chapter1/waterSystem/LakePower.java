package fun.wraq.series.overworld.chapter1.waterSystem;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.EffectOnMob;
import fun.wraq.process.func.SpecialEffectOnPlayer;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.PowerLogic;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import static fun.wraq.common.Compute.*;

public class LakePower extends WraqPower {

    private final int tier;

    public static WeakHashMap<Player, Integer> playerDefendTickMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> playerDefendRateMap = new WeakHashMap<>();

    public LakePower(Properties p_41383_, int tier) {
        super(p_41383_);
        this.tier = tier;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public Component getActiveName() {
        return Component.literal("迟滞之水").withStyle(CustomStyle.styleOfWater);
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal(" 对").withStyle(ChatFormatting.WHITE).
                append(Component.literal("指针").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("周围敌人造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%减速").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal(" 同时对其造成").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaDamageValue(String.format("%.0f", effect[tier] * 100) + "%")));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.WaterElement("1 + 100%")));
        components.add(Component.literal(" 为").withStyle(ChatFormatting.WHITE).
                append(Component.literal("自身").withStyle(ChatFormatting.GREEN)).
                append(Component.literal("周围玩家提供持续4s的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal((tier + 1) * 5 + "%").withStyle(CustomStyle.styleOfWater)).
                append(Component.literal("伤害削减。").withStyle(ChatFormatting.GREEN)));
        components.add(Te.s(" 并", "移除", CustomStyle.styleOfWater, "重伤", CustomStyle.styleOfMoontain, "/",
                "禁锢", CustomStyle.styleOfMine, "/", "眩晕", CustomStyle.styleOfIce, "效果"));
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
        List.of(ModItems.LakePower.get(), ModItems.LakePower1.get(),
                        ModItems.LakePower2.get(), ModItems.LakePower3.get())
                .forEach(item -> {
                    playerItemCoolDown(player, item, LakePower.CoolDownTime[tier] - SuitCount.getObsiManaESuitCount(player) * 0.75);
                });
        int tick = Tick.get();
        Level dimension = player.level();
        double effect = LakePower.effect[tier];
        Vec3 targetPos = player.pick(15, 0, false).getLocation();
        if (detectPlayerPickMob(player) != null) targetPos = detectPlayerPickMob(player).position();
        List<Mob> mobList = dimension.getEntitiesOfClass(Mob.class,
                AABB.ofSize(targetPos, 20, 20, 20));

        List<Player> playerList = dimension.getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 20, 20, 20));
        playerList.removeIf(player1 -> player1.distanceTo(player) > 6);
        playerList.forEach(player1 -> {
            LakePower.playerDefendRateMap.put(player1, tier + 1);
            LakePower.playerDefendTickMap.put(player1, tick + 80);
            Compute.sendEffectLastTime(player1, ModItems.LakePower.get().getDefaultInstance(), 80);
            SpecialEffectOnPlayer.cleanse(player1);
        });
        ParticleProvider.dustParticle(player, player.getEyePosition(), 6, 36, CustomStyle.styleOfLake.getColor().getValue());

        Vec3 finalTargetPos = targetPos;
        mobList.forEach(mob -> {
            Vec3 PosVec = mob.position().subtract(finalTargetPos);
            if (PosVec.length() <= 6) {
                Compute.IgniteMob(player, mob, 0);
                EffectOnMob.addSlowDownEffect(mob, 40, 0.25);
                addManaDefenceDecreaseEffectParticle(mob, 40);
                PowerLogic.PlayerPowerEffectToMob(player, mob);
                Damage.causeManaDamageToMonster_RateApDamage_ElementAddition(player, mob, effect, true,
                        Element.water, ElementValue.ElementValueJudgeByType(player, Element.water) + 1);
                ParticleProvider.dustParticle(player, mob.getEyePosition(), 0.8, 20,
                        ChatFormatting.BLUE.getColor().intValue());
            }
        });

        MySound.soundToNearPlayer(player, SoundEvents.WATER_AMBIENT);
        ParticleProvider.createSpaceRangeParticle((ServerLevel) dimension, targetPos, 6, 150,
                ParticleTypes.BUBBLE_POP);
    }

    public static int[] ManaCost = {
            100, 115, 130, 150
    };

    public static int[] CoolDownTime = {
            8, 8, 8, 8
    };

    public static double[] effect = {
            1, 1.15, 1.3, 1.5
    };

    public static double PlayerDefend(Player player) {
        if (playerDefendTickMap.containsKey(player) && playerDefendTickMap.get(player) > player.getServer().getTickCount()) {
            return 0.05 * playerDefendRateMap.get(player);
        }
        return 0;
    }
}
