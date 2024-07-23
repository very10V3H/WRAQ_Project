package com.very.wraq.series.worldsoul;

import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.WraqSword;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SoulSword extends WraqSword implements ActiveItem {
    public SoulSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, SoulEquipAttribute.BaseAttribute.SoulSword.AttackDamage);
        Utils.critRate.put(this, SoulEquipAttribute.BaseAttribute.SoulSword.CritRate);
        Utils.critDamage.put(this, SoulEquipAttribute.BaseAttribute.SoulSword.CritDamage);
        Utils.defencePenetration0.put(this, SoulEquipAttribute.BaseAttribute.SoulSword.DefencePenetration0);
        Utils.movementSpeedWithoutBattle.put(this, SoulEquipAttribute.BaseAttribute.SoulSword.MovementSpeed);
        Utils.healthSteal.put(this, SoulEquipAttribute.BaseAttribute.SoulSword.HealthSteal);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfWorld;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        int killCount = stack.getOrCreateTagElement(Utils.MOD_ID).getInt(StringUtils.SoulSwordKillCount);
        if (killCount > 25600) killCount = 25600;
        Compute.DescriptionPassive(components, Component.literal("本源具象").withStyle(style));
        components.add(Component.literal(" 击杀怪物以回收").withStyle(ChatFormatting.WHITE).
                append(Component.literal("世界本源").withStyle(style)).
                append(Component.literal("增加").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("")));
        components.add(Component.literal(" 当前增加的攻击力为: ").withStyle(ChatFormatting.WHITE).
                append(Component.literal("" + killCount / 100).withStyle(style)));
        Compute.DescriptionActive(components, Component.literal("本源打击-咒刃").withStyle(style));
        components.add(Component.literal(" 下次攻击造成").withStyle(ChatFormatting.WHITE).
                append(Component.literal("50%额外物理伤害").withStyle(ChatFormatting.WHITE)));
        Compute.CoolDownTimeDescription(components, 3);
        Compute.ManaCostDescription(components, 60);
        components.add(Component.literal(" Idea From:Mr_RED").withStyle(ChatFormatting.LIGHT_PURPLE));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix1Wraq();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND) && player.isShiftKeyDown()) {
            try {
                SoulEquipAttribute.Forging(player.getItemInHand(InteractionHand.MAIN_HAND), player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Compute.use(player);
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, 60)) {
            Compute.effectLastTimeSend(player, ModItems.SoulSword.get().getDefaultInstance(), 8888, 0, true);
            Utils.SoulSwordMap.put(player, true);
        }
    }
}
