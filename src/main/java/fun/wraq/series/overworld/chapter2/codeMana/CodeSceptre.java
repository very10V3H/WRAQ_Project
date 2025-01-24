package fun.wraq.series.overworld.chapter2.codeMana;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CodeSceptre extends PickaxeItem {
    public CodeSceptre(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
        Utils.manaDamage.put(this, this.ManaDamage);
        Utils.manaRecover.put(this, this.ManaReply);
        Utils.manaPenetration.put(this, this.ManaBreakDefence);
        Utils.mainHandTag.put(this, 1d);
        Utils.sceptreTag.put(this, 1.0d);
    }

    private double ManaDamage = 50;
    private double ManaReply = 10.0f;
    private double ManaBreakDefence = 0.3F;

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        components.add(Component.literal("能量激化:").withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfVolcano));
        components.add(Component.literal("能使用").withStyle(ChatFormatting.WHITE).
                append(Component.literal("魔符").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("来强化下一次").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.WHITE);
        components.add(Component.literal("Evoker-Sceptre-X").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryII-I.").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, level, components, flag);
    }
}