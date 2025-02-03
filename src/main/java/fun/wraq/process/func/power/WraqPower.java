package fun.wraq.process.func.power;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.gui.illustrate.Display;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class WraqPower extends Item implements ActiveItem {
    public WraqPower(Properties properties) {
        super(properties);
        Utils.powerTag.put(this, 1d);
        Display.powerList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·法术").withStyle(CustomStyle.styleOfMana));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        if (getActiveName() != null) {
            Compute.DescriptionActive(components, getActiveName());
        }
        components.addAll(getAdditionalComponents());
        ComponentUtils.coolDownTimeDescription(components, getCoolDownSecond());
        ComponentUtils.manaCostDescription(components, (int) getManaCost());
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        components.add(ComponentUtils.getSuffixOfSouvenirs());
        components.add(Te.s("随着新版技能组的上线，这件物品成为了一件纪念品。", ChatFormatting.GOLD));
        components.add(Te.s("Souvenirs-2025.1.23", CustomStyle.styleOfSakura, ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    public abstract Component getActiveName();
    public abstract List<Component> getAdditionalComponents();
    public abstract int getCoolDownSecond();
    public abstract double getManaCost();
    public abstract Component getSuffix();
    public abstract void release(Player player);

    @Override
    public void active(Player player) {
/*        release(player);
        PowerLogic.playerReleasePower(player);
        PowerLogic.playerLastTimeReleasePower.put(player, this);
        PowerLogic.playerLastTimeReleasePowerManaCost.put(player, getManaCost());
        MySound.soundToNearPlayer(player.level(), getDefaultTargetPos(player), SoundEvents.EVOKER_CAST_SPELL);
        OnPowerReleaseEquip.release(player);
        OnPowerReleaseCurios.release(player);*/
    }

    @Override
    public double manaCost(Player player) {
        return getManaCost();
    }

    protected static void basicCauseManaDamageDescription(List<Component> components, double value) {
        components.add(Te.s(" 对", "准星周围", ChatFormatting.AQUA, "所有怪物造成",
                ComponentUtils.AttributeDescription.manaDamageValue(String.format("%.0f%%", value * 100))));
    }

    protected static void elementAdditionDescription(List<Component> components, Component component) {
        components.add(Component.literal(" - 这个伤害会附带").withStyle(CustomStyle.styleOfStone).append(component));
    }

    public static List<Mob> getDefaultTargetMobList(Player player, double range) {
        Vec3 pos = getDefaultTargetPos(player);
        return player.level().getEntitiesOfClass(Mob.class,
                AABB.ofSize(pos, 20, 20, 20))
                .stream().filter(mob -> mob.position().distanceTo(pos) <= range)
                .toList();
    }

    public static List<Mob> getDefaultTargetMobList(Player player) {
        return getDefaultTargetMobList(player, 6);
    }

    protected static List<Player> getDefaultTargetPlayerList(Player player) {
        return player.level().getEntitiesOfClass(Player.class,
                AABB.ofSize(player.position(), 20, 20, 20));
    }

    public static Vec3 getDefaultTargetPos(Player player) {
        Mob mob = Compute.detectPlayerPickMob(player);
        if (mob != null) {
            return mob.position();
        }
        return player.pick(15, 0, false).getLocation();
    }

    protected void produceDefaultDustParticle(Player player, Style style) {
        ParticleProvider.dustParticle(player, player.getEyePosition(), 6, 36, style.getColor().getValue());
    }

    protected void produceDefaultPlayerEnhanceEffectParticle(Player targetPlayer) {
        ParticleProvider.EntityEffectVerticleCircleParticle(targetPlayer, 1.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(targetPlayer, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(targetPlayer, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(targetPlayer, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(targetPlayer, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
    }

    protected void produceDefaultMobDeBuffParticle(Mob targetMob) {
        ParticleProvider.EntityEffectVerticleCircleParticle(targetMob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(targetMob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(targetMob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(targetMob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(targetMob, 0, 0.4, 8, ParticleTypes.WITCH, 0);
    }
}
