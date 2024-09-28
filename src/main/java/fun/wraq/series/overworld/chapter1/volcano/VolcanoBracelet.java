package fun.wraq.series.overworld.chapter1.volcano;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter1.volcano.VolcanoSuitDescription;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class VolcanoBracelet extends Item {
    private final double ExpUp = 0.3F;
    private final double CriticalRate = 0.05F;
    private final double MaxHealth = 50.0d;
    private final double ExDamage = 10;

    public VolcanoBracelet() {
        super(new Properties().rarity(CustomStyle.VolcanoItalic));
        Utils.critRate.put(this, CriticalRate);
        Utils.maxHealth.put(this, MaxHealth);
        Utils.expUp.put(this, ExpUp);
        Utils.attackDamage.put(this, ExDamage);
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        ChatFormatting chatFormatting = ChatFormatting.YELLOW;
        stack.setHoverName(Component.literal("火山手环").withStyle(chatFormatting).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("手环").withStyle(ChatFormatting.GRAY)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, chatFormatting, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, chatFormatting, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        if (Screen.hasShiftDown()) VolcanoSuitDescription.VolcanoSuitDescription(components);
        else {
            Compute.SuitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, chatFormatting, ChatFormatting.WHITE);
        ComponentUtils.suffixOfChapterI(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
