package com.very.wraq.series.nether.Equip;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.MySound;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.core.MyArrow;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.hud.Mana;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NetherBow extends WraqBow {

    public NetherBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 140d);
        Utils.defencePenetration0.put(this, 900d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 0.6);
        Utils.movementSpeedWithoutBattle.put(this, 0.3);
        Element.FireElementValue.put(this, 1d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfNether;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("夸塔兹激化箭矢").withStyle(CustomStyle.styleOfQuartz));
        components.add(Component.literal("使用").withStyle(ChatFormatting.WHITE).
                append(Component.literal("下界能量石英").withStyle(CustomStyle.styleOfNether)).
                append(Component.literal("制作而成的长弓。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("射出箭矢时，将会消耗").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("40")).
                append(Component.literal("使箭矢不会下坠，并造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("50%额外伤害。").withStyle(ChatFormatting.YELLOW)));
        components.add(Component.literal("所有箭矢会根据飞行距离，至多提供").withStyle(ChatFormatting.WHITE).
                append(Component.literal("100%额外伤害（在100格达到最大值）").withStyle(ChatFormatting.YELLOW)));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterIII();
    }

    @Override
    protected MyArrow summonArrow(ServerPlayer serverPlayer, double rate) {
        if (Mana.getPlayerCurrentManaNum(serverPlayer) > 40) {
            Mana.addOrCostPlayerMana(serverPlayer, -40);
            MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true, rate);
            arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5F, 1.0f);
            arrow.setCritArrow(true);
            arrow.setNoGravity(true);
            WraqBow.adjustArrow(arrow, serverPlayer);
            serverPlayer.level().addFreshEntity(arrow);
            MySound.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
            ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.WITCH);
            return arrow;
        } else {
            MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true);
            arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 3F, 1.0f);
            arrow.setCritArrow(true);
            WraqBow.adjustArrow(arrow, serverPlayer);
            serverPlayer.level().addFreshEntity(arrow);
            MySound.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
            ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.WAX_OFF);
            return arrow;
        }
    }
}
