package com.very.wraq.series.end.eventController.NetherRecall1;

import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.WraqSword;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ManaSword1 extends WraqSword implements ActiveItem {

    public ManaSword1(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 90d);
        Utils.defencePenetration0.put(this, 1800d);
        Utils.healthSteal.put(this, 0.1);
        Utils.critRate.put(this, 0.35);
        Utils.critDamage.put(this, 0.5);
        Utils.movementSpeedWithoutBattle.put(this, 0.6);
        Element.FireElementValue.put(this, 1d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMana;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("魔力渴望").withStyle(CustomStyle.styleOfMana));
        components.add(Component.literal("玛莫提乌斯之噬对魔力十分渴望。").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("·[对魔]").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("攻击唤魔者/唤魔大师时，").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("无视其100%的护甲。").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal("·[对人]").withStyle(ChatFormatting.AQUA).
                append(Component.literal("攻击玩家时，").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("偷取玩家10%最大法力值。").withStyle(ChatFormatting.LIGHT_PURPLE)));
        components.add(Component.literal("主动:").withStyle(ChatFormatting.AQUA).append(Component.literal("魔力灌注").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#ba00df")))));
        components.add(Component.literal("将自身魔力全部灌注入玛莫提乌斯之噬，获得短暂的巨幅攻击力加成。").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("消耗全部法力值，并获得与消耗法力值等同的逐渐衰减的攻击力加成。").withStyle(ChatFormatting.RED));
        components.add(Component.literal("消耗全部").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("")).
                append(Component.literal("并获得等同于消耗量的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("")));
        Compute.CoolDownTimeDescription(components, 5);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getIntensifiedSuffix();
    }

    @Override
    public void active(Player player) {
        CompoundTag data = player.getPersistentData();
        player.getCooldowns().addCooldown(this, (int) (100 - 100 * PlayerAttributes.coolDownDecrease(player)));
        data.putInt("ManaSwordActive", data.getInt("MANA"));
        data.putInt("MANA", 0);
        Compute.ManaStatusUpdate(player);
        Compute.PlayerPowerParticle(player);
    }
}
