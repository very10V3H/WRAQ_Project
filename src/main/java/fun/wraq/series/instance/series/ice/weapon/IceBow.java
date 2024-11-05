package fun.wraq.series.instance.series.ice.weapon;

import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.impl.onhit.OnCritHitEffectMainHandWeapon;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.core.MyArrow;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class IceBow extends WraqBow implements OnHitEffectEquip, OnCritHitEffectMainHandWeapon {

    public IceBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 700d);
        Utils.defencePenetration0.put(this, 21d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 1.35);
        Element.IceElementValue.put(this, 1.25);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfIce;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        IceWeaponPassiveHelper.description(components);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfIce();
    }

    @Override
    protected MyArrow summonArrow(ServerPlayer serverPlayer, double rate) {
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true, rate);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        MySound.soundToNearPlayer(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.SNOWFLAKE);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.SNOWFLAKE);
        return arrow;
    }

    @Override
    public void onHit(Player player, Mob mob) {
        IceWeaponPassiveHelper.onHit(player, mob);
    }

    @Override
    public void onCritHit(Player player, Mob mob) {
        IceWeaponPassiveHelper.onCritHit(player, mob, ModItems.IceBow.get(), 5);
    }
}
