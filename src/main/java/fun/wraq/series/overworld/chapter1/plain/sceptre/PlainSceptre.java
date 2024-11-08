package fun.wraq.series.overworld.chapter1.plain.sceptre;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class PlainSceptre extends WraqSceptre implements OnHitEffectEquip, InCuriosOrEquipSlotAttributesModify {

    private final int tier;
    public PlainSceptre(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.manaDamage.put(this, new double[]{20, 25, 35, 45, 60}[tier]);
        Utils.manaRecover.put(this, new double[]{8, 9, 10, 11, 12}[tier]);
        Utils.manaPenetration0.put(this, new double[]{1, 1, 2, 2, 3}[tier]);
        Utils.coolDownDecrease.put(this, new double[]{0.1, 0.12, 0.14, 0.16, 0.2}[tier]);
        Element.LifeElementValue.put(this, new double[]{0.2, 0.4, 0.6, 0.8, 1}[tier]);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPlain;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("平原的加护").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal(" 当法球命中单位时，获得持续5s的:"));
        ComponentUtils.emojiDescriptionDefence(components, 15);
        ComponentUtils.emojiDescriptionManaDefence(components, 15);
        components.add(Component.literal(" 并回复").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.maxHealth("1%")));
        if (tier > 3) {
            Compute.DescriptionPassive(components, Component.literal("生机涌动").withStyle(CustomStyle.styleOfHealth));
            components.add(Component.literal(" 白天额外获得").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.manaDamage("45")).
                    append(Component.literal("与")).
                    append(ComponentUtils.AttributeDescription.manaRecover("15")));
        }
        return components;
    }

    @Override
    protected AbstractArrow summonManaArrow(Player player, double rate) {
        Level level = player.level();
        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_PLAIN.get(), player, level,
                PlayerAttributes.manaDamage(player) * rate, PlayerAttributes.manaPenetration(player),
                PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Plain);
        newArrow.setSilent(true);
        newArrow.setNoGravity(true);
        newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 3, 1);
        ProjectileUtil.rotateTowardsMovement(newArrow, 0);
        WraqSceptre.adjustOrb(newArrow, player);
        level.addFreshEntity(newArrow);
        if (tier > 3) {
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.COMPOSTER);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.COMPOSTER);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 2, 0.25, 12, ParticleTypes.COMPOSTER);
        } else {
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.SCRAPE);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.SCRAPE);
        }
        return newArrow;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public List<Attribute> getAttributes(Player player) {
        if (player.getMainHandItem().is(this) && tier == 4
                && !Utils.OverWorldLevelIsNight && player.level().dimension().equals(Level.OVERWORLD)) {
            return List.of(
                    new Attribute(Utils.manaDamage, 45),
                    new Attribute(Utils.manaRecover, 15)
            );
        }
        return List.of();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        StableAttributesModifier.addM(player, StableAttributesModifier.playerDefenceModifier,
                "PlainSceptrePassiveDefence", 15, Tick.get() + 100);
        StableAttributesModifier.addM(player, StableAttributesModifier.playerManaDefenceModifier,
                "PlainSceptrePassiveManaDefence", 15, Tick.get() + 100);
        Compute.sendEffectLastTime(player,this, 100);
        Compute.playerHeal(player, player.getMaxHealth() * 0.01);
    }
}
