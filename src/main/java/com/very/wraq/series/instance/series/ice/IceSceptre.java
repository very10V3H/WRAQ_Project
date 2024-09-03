package com.very.wraq.series.instance.series.ice;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModEntityType;
import com.very.wraq.common.registry.MySound;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.common.registry.ModSounds;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.OnHitEffectMainHandWeapon;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.render.toolTip.CustomStyle;
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

public class IceSceptre extends WraqSceptre implements OnHitEffectMainHandWeapon {

    public IceSceptre(Properties p_42964_) {
        super(p_42964_.rarity(CustomStyle.IceItalic));
        Utils.manaDamage.put(this, 1400d);
        Utils.manaRecover.put(this, 30d);
        Utils.coolDownDecrease.put(this, 0.45);
        Utils.manaPenetration0.put(this, 2100d);
        Utils.movementSpeedWithoutBattle.put(this, 0.4);
        Utils.manaCost.put(this, 45d);
        Element.IceElementValue.put(this, 1.25);
    }

    @Override
    protected AbstractArrow summonManaArrow(Player player, double rate) {
        Level level = player.level();
        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_WORLD.get(), player,
                level, PlayerAttributes.manaDamage(player) * rate,
                PlayerAttributes.manaPenetration(player),
                PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Sky);
        newArrow.setSilent(true);
        newArrow.setNoGravity(true);

        newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
        ProjectileUtil.rotateTowardsMovement(newArrow, 0);
        WraqSceptre.adjustOrb(newArrow, player);
        level.addFreshEntity(newArrow);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.SNOWFLAKE);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.SNOWFLAKE);
        MySound.SoundToAll(player, ModSounds.Mana.get());
        return newArrow;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfIce;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = CustomStyle.styleOfIce;
        ComponentUtils.descriptionPassive(components, Component.literal("迸晶裂玉").withStyle(style));
        components.add(Component.literal(" 攻击").withStyle(CustomStyle.styleOfPower).
                append(Component.literal("将对目标造成持续1s的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("减速").withStyle(CustomStyle.styleOfIce)));
        Compute.DescriptionPassive(components, Component.literal("凝结爆裂").withStyle(style));
        components.add(Component.literal(" 你的普通法球攻击命中目标后，为你提供基于攻击目标").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDefence("")).
                append(Component.literal("的额外").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("")).
                append(Component.literal("持续2s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 每1点魔法抗性提供2法术攻击").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal(" 最多提供6000法术攻击（在目标拥有3000魔法抗性时达到最大值）").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfIce();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        Compute.addSlowDownEffect(mob, 20, 1);
        Compute.iceParticleCreate(mob);
    }
}
