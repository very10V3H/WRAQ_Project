package com.very.wraq.series.end.eventController.SnowRecall;

import com.very.wraq.common.Compute;
import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.USE.UtilsSnowSwordS2CPacket;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.WraqSword;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SnowSword4 extends WraqSword implements ActiveItem {

    public SnowSword4(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 200.0d);
        Utils.defencePenetration0.put(this, 1800d);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.45);
        Utils.critDamage.put(this, 0.6);
        Utils.movementSpeedWithoutBattle.put(this, 0.4);
        Element.IceElementValue.put(this, 1d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSnow;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("被动:").withStyle(ChatFormatting.GREEN).
                append(Component.literal("凿击-Ex").withStyle(ChatFormatting.AQUA)));
        ComponentUtils.descriptionNum(components, "攻击将会大幅降低目标生物的移动速度", Component.literal("2s").withStyle(ChatFormatting.AQUA), "");
        components.add(Component.literal("主动:").withStyle(ChatFormatting.AQUA).
                append(Component.literal("冰川攀登！").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal("向前闪现一小段距离"));
        components.add(Component.literal("冷却时间: 10s"));
        components.add(Component.literal("法力消耗:").withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.UNDERLINE)
                .withStyle(ChatFormatting.BOLD).append(Component.literal(" 20").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getIntensifiedSuffix();
    }

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, 60)) {
            ModNetworking.sendToClient(new UtilsSnowSwordS2CPacket(true), (ServerPlayer) player);
            player.getCooldowns().addCooldown(ModItems.SnowSword3.get(), (int) (200 * (1.0 - PlayerAttributes.coolDownDecrease(player))));
            player.getCooldowns().addCooldown(ModItems.SnowSword4.get(), (int) (200 * (1.0 - PlayerAttributes.coolDownDecrease(player))));
        }
    }
}
