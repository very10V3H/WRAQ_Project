package fun.wraq.series.nether.power;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.PowerLogic;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
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

import static fun.wraq.common.Compute.detectPlayerPickMob;
import static fun.wraq.common.Compute.playerItemCoolDown;

public class PiglinPower extends WraqPower {

    public PiglinPower(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public Component getActiveName() {
        return null;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("·[对魔]").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("基于").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("指针").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("周围怪物数量，对每个怪物造成:").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamageValue("200%*怪物数量")));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.FireElement("1 + 100%")));
        components.add(Component.literal("·[对人]").withStyle(ChatFormatting.AQUA).
                append(Component.literal("基于").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("自身").withStyle(ChatFormatting.GREEN)).
                append(Component.literal("周围玩家数量，提升每个玩家:").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.movementSpeed("4%*玩家数量")).
                append(Component.literal("以及").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamage("3%")).
                append(Component.literal("额外攻击力").withStyle(ChatFormatting.YELLOW)));
        components.add(Component.literal(" - 这个效果持续5s，且仅能同时存在一个").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        return components;
    }

    @Override
    public int getCoolDownSecond() {
        return 10;
    }

    @Override
    public double getManaCost() {
        return 360;
    }

    @Override
    public Component getSuffix() {
        return null;
    }

    @Override
    public void release(Player player) {
        Compute.PlayerPowerParticle(player);
        playerItemCoolDown(player, this, 10);
        Level level = player.level();
        Vec3 TargetPos = player.pick(15, 0, false).getLocation();
        if (detectPlayerPickMob(player) != null) TargetPos = detectPlayerPickMob(player).position();
        List<Mob> monsterList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(TargetPos, 20, 20, 20));
        for (Mob mob : monsterList) {
            if (mob.getPosition(0).distanceTo(TargetPos) < 6) {
                Damage.causeRateApDamageWithElement(player, mob, monsterList.size() * 2, true,
                        Element.fire, ElementValue.getElementValueJudgeByType(player, Element.fire) + 1);
                PowerLogic.PlayerPowerEffectToMob(player, mob);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);
            }
        }

        List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 20, 20, 20));
        double manaDamageUpValue = 0;
        if (StableAttributesModifier.getAttributeModifierList(player, StableAttributesModifier.playerAttackDamageModifier).
                stream().anyMatch(attributesModifier -> attributesModifier.tag().equals("piglinPowerAttackDamageUp"))
                && Compute.hasCurios(player, NewRuneItems.castleNewRune.get())) {
            manaDamageUpValue = (PlayerAttributes.manaDamage(player) - StableAttributesModifier.getAttributeModifierList(player, StableAttributesModifier.playerAttackDamageModifier).
                    stream().filter(attributesModifier -> attributesModifier.tag().equals("piglinPowerAttackDamageUp")).findFirst().get().value() * 0.4) * 0.03;
        } else manaDamageUpValue = 0.03 * PlayerAttributes.manaDamage(player);

        for (Player player1 : playerList) {
            if (player1.distanceTo(player) < 6) {
                Compute.sendEffectLastTime(player1, ModItems.PIGLIN_POWER.get(), 100);
                StableAttributesModifier.addAttributeModifier(player1, StableAttributesModifier.playerMovementSpeedModifier,
                        new StableAttributesModifier("piglinPowerMovementSpeedUp", 0.04 * playerList.size(), Tick.get() + 100));
                StableAttributesModifier.addAttributeModifier(player1, StableAttributesModifier.playerAttackDamageModifier,
                        new StableAttributesModifier("piglinPowerAttackDamageUp", manaDamageUpValue, Tick.get() + 100));
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.4, 8, ParticleTypes.COMPOSTER, 0);
            }
        }
        ParticleProvider.dustParticle(player, player.getEyePosition(), 6, 36, CustomStyle.styleOfPower.getColor().getValue());
        ParticleProvider.VerticleCircleParticle(TargetPos, (ServerLevel) level, 1, 6, 100, ParticleTypes.WITCH);
        ParticleProvider.VerticleCircleParticle(TargetPos, (ServerLevel) level, 1.5, 6, 100, ParticleTypes.WITCH);
        MySound.soundToNearPlayer(player, ModSounds.Nether_Power.get());
    }
}
