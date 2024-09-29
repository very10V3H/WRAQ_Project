package fun.wraq.series.overworld.sakuraSeries.Ship;

import fun.wraq.common.Compute;
import fun.wraq.common.impl.inslot.InEquipmentSlotAttributeEnhance;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.core.MyArrow;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShipBow extends WraqBow implements InEquipmentSlotAttributeEnhance {

    public ShipBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 450d);
        Utils.defencePenetration0.put(this, 22d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 1.2);
        Utils.movementSpeedWithoutBattle.put(this, 0.6);
        Element.WaterElementValue.put(this, 1d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfShip;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("领航").withStyle(style));
        components.add(Component.literal(" 根据周围玩家数量，为你提供").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.DefencePenetration("5 - 20")));
        components.add(Component.literal(" - 提供的护甲数额随周围玩家数量增长。").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterV();
    }

    @Override
    protected MyArrow summonArrow(ServerPlayer serverPlayer, double rate) {
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true, rate);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        MySound.soundToNearPlayer(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.DRIPPING_WATER);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.DRIPPING_WATER);
        return arrow;
    }

    @Override
    public double getAttributeValue(Player player) {
        return 5 * Math.min(4, Compute.getNearEntity(player, Player.class, 6)
                .stream().filter(entity -> entity.distanceTo(player) <= 6).count());
    }

    @Override
    public Map<Item, Double> baseAttributeMap() {
        return Utils.defencePenetration0;
    }
}
