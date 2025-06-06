package fun.wraq.series.end;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.power.PowerLogic;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EndPower extends Item implements ActiveItem {

    private final int Level;

    public EndPower(Properties p_41383_, int level) {
        super(p_41383_);
        this.Level = level;
        Utils.powerTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    public int getLevel() {
        return Level;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = CustomStyle.styleOfEnd;
        components.add(Component.literal("·法术").withStyle(CustomStyle.styleOfMana));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        Compute.DescriptionPassive(components, Component.literal("哀思").withStyle(style));
        components.add(Component.literal(" 追忆").withStyle(style).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaCost("")).
                append(Component.literal("等于同于上次释放法术的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaCost("150%")));
        Compute.DescriptionActive(components, Component.literal("追忆").withStyle(style));
        components.add(Component.literal(" 重复释放").withStyle(ChatFormatting.WHITE).
                append(Component.literal("上次").withStyle(style)).
                append(Component.literal("释放的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("法术").withStyle(CustomStyle.styleOfMana)));
        ComponentUtils.coolDownTimeDescription(components, CoolDownTime[Level]);
        components.add(Component.literal(" - IDEA FROM : AzusaLin").withStyle(ChatFormatting.LIGHT_PURPLE));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        components.add(ComponentUtils.getSuffixOfSouvenirs());
        components.add(Te.s("随着新版技能组的上线，这件物品成为了一件纪念品。", ChatFormatting.GOLD));
        components.add(Te.s("Souvenirs-2025.1.23", CustomStyle.styleOfSakura, ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static int[] CoolDownTime = {
            13, 12, 11, 10
    };

    public static void Release(Player player) {
        if (Compute.playerManaCost(player, (PowerLogic.playerLastTimeReleasePowerManaCost.getOrDefault(player, 45d) * 1.5), true)) {
            Compute.playerItemCoolDown(player, ModItems.END_POWER.get(), CoolDownTime[0]);
            Compute.playerItemCoolDown(player, ModItems.END_POWER_1.get(), CoolDownTime[1]);
            Compute.playerItemCoolDown(player, ModItems.END_POWER_2.get(), CoolDownTime[2]);
            Compute.playerItemCoolDown(player, ModItems.END_POWER_3.get(), CoolDownTime[3]);
            PowerLogic.ReleaseLastTime(player);
        }
    }

    @Override
    public void active(Player player) {

    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}
