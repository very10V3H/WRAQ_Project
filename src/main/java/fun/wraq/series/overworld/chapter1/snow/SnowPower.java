package fun.wraq.series.overworld.chapter1.snow;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.struct.Shield;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.PowerLogic;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

import static fun.wraq.common.Compute.*;

public class SnowPower extends WraqPower {

    private final int tier;

    public SnowPower(Properties p_41383_, int tier) {
        super(p_41383_);
        this.tier = tier;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public Component getActiveName() {
        return Component.literal("冰封陵墓").withStyle(CustomStyle.styleOfSnow);
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal(" 禁锢").withStyle(ChatFormatting.WHITE).
                append(Component.literal("指针").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("周围所有敌人1s，并造成").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamageValue(String.format("%.0f", effect[tier] * 100) + "%")));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.IceElement("1 + 100%")));
        components.add(Component.literal(" 为").withStyle(ChatFormatting.WHITE).
                append(Component.literal("自身").withStyle(ChatFormatting.GREEN)).
                append(Component.literal("周围所有玩家提供")).
                append(Component.literal("能力 - 智力 * 20").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("护盾值").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("，持续2.5s").withStyle(ChatFormatting.WHITE)));
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
        List.of(ModItems.SnowPower.get(), ModItems.SnowPower1.get(),
                ModItems.SnowPower2.get(), ModItems.SnowPower3.get()).
                forEach(item -> {
                    playerItemCoolDown(player, item, SnowPower.CoolDownTime[tier]);
                });

        Level dimension = player.level();
        double effect = SnowPower.effect[tier];
        Vec3 targetPos = player.pick(15, 0, false).getLocation();
        CompoundTag data = player.getPersistentData();
        if (detectPlayerPickMob(player) != null) targetPos = detectPlayerPickMob(player).position();

        List<Mob> mobList = dimension.getEntitiesOfClass(Mob.class,
                AABB.ofSize(targetPos, 20, 20, 20));
        Vec3 finalTargetPos = targetPos;
        mobList.forEach(mob -> {
            Vec3 PosVec = mob.position().subtract(finalTargetPos);
            if (PosVec.length() <= 6) {
                Compute.IgniteMob(player, mob, 0);
                BlockPos blockPos = mob.blockPosition().above();
                if (dimension.getBlockState(blockPos).is(Blocks.AIR)) {
                    dimension.setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());
                    dimension.destroyBlock(blockPos, false);
                }
                mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 100, false, false, false));
                Damage.causeRateApDamageWithElement(player, mob, effect, true,
                        Element.ice, ElementValue.ElementValueJudgeByType(player, Element.ice) + 1);
                PowerLogic.PlayerPowerEffectToMob(player, mob);
                Compute.sendMobEffectHudToNearPlayer(mob, ModItems.SnowPower.get(), "SnowPowerImprison", 20, 0, false);
            }
        });

        List<Player> players = dimension.getEntitiesOfClass(Player.class,
                AABB.ofSize(player.position(), 20, 20, 20));
        players.forEach(player1 -> {
            if (player1.distanceTo(player) < 6) {
                Shield.providePlayerShield(player1, 50, data.getInt(StringUtils.Ability.Intelligent) * 20);
                Compute.createIceParticle(player1);
                sendEffectLastTime(player, ModItems.SnowPower.get().getDefaultInstance(), 50);
            }
        });
        ParticleProvider.dustParticle(player, player.getEyePosition(), 6, 36, CustomStyle.styleOfSnow.getColor().getValue());

        ParticleProvider.createSpaceRangeParticle((ServerLevel) player.level(), targetPos, 6, 100, ParticleTypes.SNOWFLAKE);
        MySound.soundToNearPlayer(player, ModSounds.Mana.get());
    }

    public static int[] ManaCost = {
            100, 125, 150, 175
    };

    public static int[] CoolDownTime = {
            12, 11, 10, 8
    };

    public static double[] effect = {
            1, 1.25, 1.5, 1.75
    };
}
