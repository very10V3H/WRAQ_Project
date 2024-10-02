package fun.wraq.series.specialevents.labourDay;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LabourDayGem extends Item {

    public LabourDayGem(Properties p_41383_) {
        super(p_41383_);
        Utils.attackDamage.put(this, 155d);
        Utils.manaDamage.put(this, 310d);
        Utils.movementSpeedWithoutBattle.put(this, 0.4);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        ChatFormatting style = ChatFormatting.GOLD;
        components.add(Component.literal("这是最后的斗争！").withStyle(style));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        OldCoin.LabourDaySuffix(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
