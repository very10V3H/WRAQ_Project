package fun.wraq.series.specialevents.qingMing;

import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.specialevents.qingMing.QingTuan;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class QingMingGem extends Item {

    private final double AttackDamage = 135;
    private final double ManaDamage = 270;
    private final double MovementSpeed = 0.3;

    public QingMingGem(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsAttackDamage.put(StringUtils.GemName.QingMingGem, AttackDamage);
        Utils.gemsManaDamage.put(StringUtils.GemName.QingMingGem, ManaDamage);
        Utils.gemsSpeedUp.put(StringUtils.GemName.QingMingGem, MovementSpeed);
        Utils.attackDamage.put(this, AttackDamage);
        Utils.manaDamage.put(this, ManaDamage);
        Utils.movementSpeedWithoutBattle.put(this, MovementSpeed);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfForest;
        components.add(Component.literal("雨纷纷，欲断魂").withStyle(style));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
/*        Compute.EmojiDescriptionDefencePenetration(components,DefencePenetration);
        Compute.EmojiDescriptionManaPenetration(components,ManaPenetration);*/
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        QingTuan.QingMingSuffix(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
