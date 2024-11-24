package fun.wraq.series.overworld.chapter1.plain;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
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

public class PlainPower extends WraqPower {

    private final int tier;

    public PlainPower(Properties p_41383_, int tier) {
        super(p_41383_);
        this.tier = tier;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public Component getActiveName() {
        return Component.literal("平原之风").withStyle(CustomStyle.styleOfPlain);
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal(" 击退").withStyle(ChatFormatting.WHITE).
                append(Component.literal("指针").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("周围怪物，同时对其造成").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamageValue(String.format("%.0f", effect[this.tier] * 100) + "%")));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.LifeElement("1 + 100%")));
        components.add(Component.literal(" 为").withStyle(ChatFormatting.WHITE).
                append(Component.literal("自身").withStyle(ChatFormatting.GREEN)).
                append(Component.literal("周围所有玩家提供持续5s的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.movementSpeed("20%")));
        return components;
    }

    @Override
    public int getCoolDownSecond() {
        return CoolDownTime[this.tier];
    }

    @Override
    public double getManaCost() {
        return manaCost[this.tier];
    }

    @Override
    public Component getSuffix() {
        return null;
    }

    @Override
    public void release(Player player) {
        List.of(ModItems.PlainPower.get(), ModItems.PlainPower1.get(),
                ModItems.PlainPower2.get(), ModItems.PlainPower3.get())
                .forEach(item -> {
                    playerItemCoolDown(player, this, PlainPower.CoolDownTime[tier] - SuitCount.getLifeManaESuitCount(player));
                });
        Level dimension = player.level();
        double effect = PlainPower.effect[tier];
        Vec3 TargetPos = player.pick(15, 0, false).getLocation();
        if (detectPlayerPickMob(player) != null) TargetPos = detectPlayerPickMob(player).position();
        List<Mob> mobList = dimension.getEntitiesOfClass(Mob.class,
                AABB.ofSize(TargetPos, 20, 20, 20));
        Vec3 finalTargetPos = TargetPos;
        mobList.forEach(mob -> {
            Vec3 PosVec = mob.position().subtract(finalTargetPos);
            if (PosVec.length() <= 6) {
                if (!MonsterCantBeMove(mob))
                    mob.setDeltaMovement(PosVec.normalize().scale(Math.min(2, 6 / PosVec.length())));
                Damage.causeManaDamageToMonster_RateApDamage_ElementAddition(player, mob, effect, true,
                        Element.life, ElementValue.ElementValueJudgeByType(player, Element.life) + 1);
                PowerLogic.PlayerPowerEffectToMob(player, mob);
                ParticleProvider.dustParticle(player, mob.getEyePosition(), 0.8, 20,
                        CustomStyle.styleOfLife.getColor().getValue());
            }
        });

        List<Player> playerList = dimension.getEntitiesOfClass(Player.class,
                AABB.ofSize(player.position(), 20, 20, 20));

        playerList.forEach(player1 -> {
            if (player1.distanceTo(player) <= 6) {
                StableAttributesModifier.addAttributeModifier(player1, StableAttributesModifier.playerMovementSpeedModifier,
                        new StableAttributesModifier("plainPowerMovementSpeed", 0.2, player.getServer().getTickCount() + 100));
                Compute.sendEffectLastTime(player1, ModItems.PlainPower.get(), 100);

                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
            }
        });
        ParticleProvider.dustParticle(player, player.getEyePosition(), 6, 36, CustomStyle.styleOfPlain.getColor().getValue());

        ParticleProvider.DisperseParticle(TargetPos, (ServerLevel) player.level(), 1, 1, 120, ModParticles.LONG_PLAIN.get(), 1);
        ParticleProvider.DisperseParticle(TargetPos, (ServerLevel) player.level(), 1.5, 1, 120, ModParticles.LONG_PLAIN.get(), 1);

        MySound.soundToNearPlayer(player, ModSounds.Wind.get());
    }

    public static int[] manaCost = {
            100, 115, 130, 150
    };

    public static int[] CoolDownTime = {
            13, 12, 11, 10
    };

    public static double[] effect = {
            1, 1.15, 1.3, 1.5
    };
}
