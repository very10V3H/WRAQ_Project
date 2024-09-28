package fun.wraq.series.overworld.chapter1.plain.sceptre;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class PlainSceptre extends WraqSceptre {

    private final int tier;
    public PlainSceptre(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.manaDamage.put(this, new double[]{20, 25, 35, 45, 60}[tier]);
        Utils.manaRecover.put(this, new double[]{8, 9, 10, 12, 15}[tier]);
        Utils.manaPenetration0.put(this, new double[]{1, 1, 2, 2, 3}[tier]);
        Utils.coolDownDecrease.put(this, new double[]{0.1, 0.12, 0.14, 0.16, 0.2}[tier]);
        Utils.movementSpeedWithoutBattle.put(this, new double[]{0.15, 0.15, 0.15, 0.2, 0.3}[tier]);
        Utils.manaCost.put(this, (double) 45);
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
        components.add(Component.literal("当法球命中单位时，获得:"));
        ComponentUtils.emojiDescriptionDefence(components, 40);
        ComponentUtils.emojiDescriptionManaDefence(components, 40);
        components.add(Component.literal("并回复").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.maxHealth("1%")));
        if (tier > 3) {
            Compute.DescriptionPassive(components, Component.literal("生机涌动").withStyle(CustomStyle.styleOfHealth));
            components.add(Component.literal("白天额外获得").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.manaDamage("45")).
                    append(Component.literal("与")).
                    append(Compute.AttributeDescription.ManaRecover("15")));
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

    public boolean is4Tier() {
        return tier == 4;
    }
}
