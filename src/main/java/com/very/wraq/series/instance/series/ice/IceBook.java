package com.very.wraq.series.instance.series.ice;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class IceBook extends Item {
    private final double ManaDamage = 400;
    private final double ManaPenetration0 = 600;
    private final double MaxMana = 50;
    private final double MovementSpeed = 1;
    private final double ExpUp = 1;

    public IceBook(Properties p_41383_) {
        super(p_41383_);
        Utils.manaDamage.put(this, ManaDamage);
        Utils.manaPenetration0.put(this, ManaPenetration0);
        Utils.maxMana.put(this, MaxMana);
        Utils.movementSpeedWithoutBattle.put(this, MovementSpeed);
        Utils.expUp.put(this, ExpUp);
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    private final Style style = CustomStyle.styleOfIce;

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.setHoverName(Component.literal("冰封的记忆").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("魔导书").withStyle(CustomStyle.styleOfMana)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionPassive(components, Component.literal("千里冰封").withStyle(style));
        components.add(Component.literal("每过5s，你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("将使目标").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("减速").withStyle(style)).
                append(Component.literal("，并使你对其伤害提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("20%").withStyle(style)).
                append(Component.literal("，持续3s").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(Component.literal("IceMemory").withStyle(style).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, level, components, flag);
    }

    public static void IceBookPassive(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.IceBook.get())) {
            int TickCount = player.getServer().getTickCount();
            if (!Utils.IceBookCoolDownMap.containsKey(player) || Utils.IceBookCoolDownMap.get(player) < TickCount) {
                Utils.IceBookMobEffectPlayerMap.put(mob, player);
                Utils.IceBookMobEffectTickMap.put(mob, TickCount + 60);
                Utils.IceBookCoolDownMap.put(player, TickCount + 100);
                Compute.coolDownTimeSend(player, ModItems.IceBook.get().getDefaultInstance(), 100);
                Compute.addSlowDownEffect(mob, 60, 3);
                Compute.iceParticleCreate(mob);
            }
        }
    }

    public static double IceBookDamageEnhance(Player player, Mob mob) {
        int TickCount = player.getServer().getTickCount();
        if (Utils.IceBookMobEffectTickMap.containsKey(mob) && Utils.IceBookMobEffectTickMap.get(mob) > TickCount
                && Utils.IceBookMobEffectPlayerMap.get(mob).equals(player)) {
            return 0.2;
        }
        return 0;
    }
}
