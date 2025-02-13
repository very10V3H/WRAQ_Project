package fun.wraq.series.overworld.chapter1.forest;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.PowerLogic;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

import static fun.wraq.common.Compute.*;

public class ForestPower extends WraqPower {

    private final int tier;

    public ForestPower(Properties p_41383_, int tier) {
        super(p_41383_);
        this.tier = tier;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public Component getActiveName() {
        return Component.literal("牵引藤蔓").withStyle(CustomStyle.styleOfHealth);
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal(" 牵引").withStyle(ChatFormatting.WHITE).
                append(Component.literal("指针").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("内一定范围内的所有怪物").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 同时对其造成").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaDamageValue(String.format("%.0f", effect[tier] * 100) + "%")));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.LifeElement("1 + 100%")));
        components.add(Te.s(" 为", "自身", ChatFormatting.GREEN, "周围所欲玩家提供持续5s的",
                ComponentUtils.AttributeDescription.healthRecover("能力 - 智力 * 4 ")));
        return components;
    }

    @Override
    public int getCoolDownSecond() {
        return CoolDownTime[tier];
    }

    @Override
    public double getManaCost() {
        return ForestPower.ManaCost[tier];
    }

    @Override
    public Component getSuffix() {
        return null;
    }

    @Override
    public void release(Player player) {
        List.of(ModItems.ForestPower.get(), ModItems.ForestPower1.get(),
                ModItems.ForestPower2.get(), ModItems.ForestPower3.get())
                .forEach(item -> {
                    playerItemCoolDown(player, item, ForestPower.CoolDownTime[tier] - SuitCount.getLifeManaESuitCount(player));
        });
        Level dimension = player.level();
        double effect = ForestPower.effect[tier];
        Vec3 DesPos = player.pick(15, 0, true).getLocation();
        if (detectPlayerPickMob(player) != null) DesPos = detectPlayerPickMob(player).position();

        List<Mob> mobList = dimension.getEntitiesOfClass(Mob.class,
                AABB.ofSize(DesPos, 20, 20, 20));
        Vec3 finalDesPos = DesPos;
        mobList.forEach(mob -> {
            if (mob.position().subtract(finalDesPos).length() <= 8) {
                if (!MonsterCantBeMove(mob))
                    Utils.ForestPowerEffectMobList.add(new ForestPowerEffectMob(finalDesPos, 10, mob));
                Damage.causeRateApDamageWithElement(player, mob, effect, true,
                        Element.life, ElementValue.ElementValueJudgeByType(player, Element.life) + 1);
                PowerLogic.PlayerPowerEffectToMob(player, mob);
                Compute.leafParticleCreate(mob, mob.level());
            }
        });

        List<Player> playerList = dimension.getEntitiesOfClass(Player.class,
                AABB.ofSize(player.position(), 20, 20, 20));
        playerList.forEach(player1 -> {
            if (player1.distanceTo(player) <= 6) {
                StableAttributesModifier.addM(player1, StableAttributesModifier.playerHealthRecoverModifier,
                        "Forest power health recover",
                        player.getPersistentData().getInt(StringUtils.Ability.Intelligent) * 4,
                        Tick.get() + 100, "/item/forest_power");

                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
            }
        });
        ParticleProvider.dustParticle(player, player.getEyePosition(), 6, 36, CustomStyle.styleOfForest.getColor().getValue());

        ParticleProvider.GatherParticle(DesPos, (ServerLevel) player.level(), 1, 6, 120, ModParticles.LONG_FOREST.get(), 0.25);
        ParticleProvider.GatherParticle(DesPos, (ServerLevel) player.level(), 1.5, 6, 120, ModParticles.LONG_FOREST.get(), 0.25);
    }

    public static int[] ManaCost = {
            100, 115, 130, 150
    };

    public static int[] CoolDownTime = {
            16, 14, 12, 10
    };

    public static double[] effect = {
            1, 1.15, 1.3, 1.5
    };
}
