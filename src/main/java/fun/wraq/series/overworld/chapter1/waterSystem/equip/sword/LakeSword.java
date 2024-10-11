package fun.wraq.series.overworld.chapter1.waterSystem.equip.sword;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.USE.UtilsLakeSwordS2CPacket;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LakeSword extends WraqSword implements ActiveItem, OnHitEffectEquip {

    private final int tier;
    public LakeSword(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.attackDamage.put(this, new double[]{50, 60, 70, 80}[tier]);
        Utils.defencePenetration0.put(this, new double[]{2, 2.5, 3, 4}[tier]);
        Utils.critRate.put(this, 0.3);
        Utils.critDamage.put(this, new double[]{0.25, 0.3, 0.35, 0.45}[tier]);
        Utils.movementSpeedWithoutBattle.put(this, new double[]{0.3, 0.4, 0.5, 0.75}[tier]);
        Element.WaterElementValue.put(this, new double[]{0.2, 0.4, 0.6, 0.8}[tier]);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfLake;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("潜泳").withStyle(ChatFormatting.BLUE));
        ComponentUtils.descriptionNum(components, "攻击后获得持续1秒的", ComponentUtils.AttributeDescription.movementSpeed("50%"), "");
        if (tier == 3) {
            Compute.DescriptionActive(components, Component.literal("出水").withStyle(ChatFormatting.BLUE));
            components.add(Component.literal("右键向前冲刺"));
            ComponentUtils.coolDownTimeDescription(components, 8);
            ComponentUtils.manaCostDescription(components, 60);
        }
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        StableAttributesModifier.addAttributeModifier(player, StableAttributesModifier.playerMovementSpeedModifier,
                new StableAttributesModifier("lakeSwordPassiveExMovementSpeed", 0.5, player.getServer().getTickCount() + 20));
    }

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, 60)) {
            ModNetworking.sendToClient(new UtilsLakeSwordS2CPacket(true), (ServerPlayer) player);
            player.getCooldowns().addCooldown(ModItems.LakeSword3.get(), (int) (160 * (1.0 - PlayerAttributes.coolDownDecrease(player))));
        }
    }
}
