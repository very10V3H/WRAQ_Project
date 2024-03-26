package com.Very.very.Series.EndSeries;

import com.Very.very.Process.Power.PowerLogic;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EndPower extends Item {

    private final int Level;
    public EndPower(Properties p_41383_, int level) {
        super(p_41383_);
        this.Level = level;
        Utils.PowerTag.put(this,1d);
        Utils.WeaponList.add(this);
    }

    public int getLevel() {
        return Level;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = Utils.styleOfEnd;
        components.add(Component.literal("·法术").withStyle(Utils.styleOfMana));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMana,ChatFormatting.WHITE);
        Compute.DescriptionPassive(components,Component.literal("哀思").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 追忆").withStyle(ChatFormatting.RESET).withStyle(style).
                append(Component.literal("的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaCost("")).
                append(Component.literal("等于同于上次释放法术的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaCost("150%")));
        Compute.DescriptionActive(components,Component.literal("追忆").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 重复释放").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("上次").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("释放的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("法术").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana)));
        Compute.CoolDownTimeDescription(components,CoolDownTime[Level]);
        components.add(Component.literal(" - IDEA FROM : AzusaLin").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMana,ChatFormatting.WHITE);
        components.add(Component.literal("Powers-End").withStyle(ChatFormatting.RESET).withStyle(style));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.USE(player);
        return super.use(level, player, interactionHand);
    }
    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static int[] CoolDownTime = {
            13,12,11,10
    };

    public static void Release(Player player) {
        if (Compute.PlayerManaCost(player, (int) (PowerLogic.playerLastTimeReleasePowerManaCost.getOrDefault(player,45) * 1.5),true)) {
            Compute.PlayerItemCoolDown(player, ModItems.EndPower.get(), CoolDownTime[0]);
            Compute.PlayerItemCoolDown(player, ModItems.EndPower1.get(), CoolDownTime[1]);
            Compute.PlayerItemCoolDown(player, ModItems.EndPower2.get(), CoolDownTime[2]);
            Compute.PlayerItemCoolDown(player, ModItems.EndPower3.get(), CoolDownTime[3]);
            PowerLogic.ReleaseLastTime(player);
        }
    }

}
