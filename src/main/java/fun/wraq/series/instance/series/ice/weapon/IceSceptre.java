package fun.wraq.series.instance.series.ice.weapon;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.impl.onhit.OnHitEffectMainHandWeapon;
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

public class IceSceptre extends WraqSceptre implements OnHitEffectMainHandWeapon {

    public IceSceptre(Properties p_42964_) {
        super(p_42964_);
        Utils.manaDamage.put(this, 1400d);
        Utils.manaRecover.put(this, 30d);
        Utils.coolDownDecrease.put(this, 0.45);
        Utils.manaPenetration0.put(this, 21d);
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
        MySound.soundToNearPlayer(player, ModSounds.Mana.get());
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
        Compute.DescriptionPassive(components, Component.literal("凝结爆裂").withStyle(CustomStyle.styleOfIce));
        components.add(Te.m(" 法球对处于").
                append(Te.m("减速", CustomStyle.styleOfIce)).
                append(Te.m("状态的目标造成")).
                append(Te.m("伤害", ChatFormatting.BLUE)).
                append(Te.m("后，施加一层")).
                append(Te.m("寒冰", CustomStyle.styleOfIce)));
        components.add(Te.m(" 当目标的").
                append(Te.m("寒冰", CustomStyle.styleOfIce)).
                append(Te.m("达到8层后，下次暴击会引爆所有层数")));
        components.add(Te.m(" 对目标造成").
                append(ComponentUtils.getAutoAdaptDamageDescription("200%")));
        components.add(Te.m(" 并击碎目标").
                append(ComponentUtils.AttributeDescription.manaDefence("25%")).
                append(Te.m("，持续5s")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfIce();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        IceWeaponPassiveHelper.onHit(player, mob);
        IceWeaponPassiveHelper.onCritHit(player, mob, ModItems.IceSceptre.get(), 8);
    }
}
