package com.very.wraq.series.overworld.chapter1.ManaBook;

import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ManaNote extends Item {
    private final double ManaDamage;
    private final double ManaPenetration;
    private final double ManaPenetration0;
    private final double MaxMana;
    private final double MovementSpeed;
    private final double ExpUp;
    private final int num;

    public ManaNote(Properties p_41383_, int num, double ManaDamage, double ManaPenetration, double ManaPenetration0,
                    double maxMana, double ManaRecover, double MovementSpeed, double ExpUp) {
        super(p_41383_);
        Utils.manaDamage.put(this, ManaDamage);
        Utils.manaPenetration0.put(this, ManaPenetration0);
        Utils.maxMana.put(this, maxMana);
        Utils.movementSpeedWithoutBattle.put(this, MovementSpeed);
        Utils.expUp.put(this, ExpUp);
        Utils.manaRecover.put(this, new double[]{4, 6, 8, 10, 12, 14, 16, 18, 20}[num]);
        this.ManaDamage = ManaDamage;
        this.ManaPenetration = ManaPenetration;
        this.ManaPenetration0 = ManaPenetration0;
        this.MaxMana = maxMana;
        this.MovementSpeed = MovementSpeed;
        this.ExpUp = ExpUp;
        this.num = num;
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    public static final String[] Name = {
            "", "¹", "²", "³", "⁴", "⁵", "ⁱᶜᵉ"
    };

    public static final Style[] styles = {
            CustomStyle.styleOfHealth,
            CustomStyle.styleOfHealth,
            CustomStyle.styleOfSea,
            CustomStyle.styleOfVolcano,
            CustomStyle.styleOfSnow,
            CustomStyle.styleOfIce

    };

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (num > 4) {
            stack.setHoverName(Component.literal("唤魔典籍" + Name[num - 5]).withStyle(CustomStyle.styleOfMana).withStyle(ChatFormatting.BOLD));
        } else
            stack.setHoverName(Component.literal("见习魔法师的笔记" + Name[num]).withStyle(styles[num]).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("魔导书").withStyle(CustomStyle.styleOfMana)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        if (num > 4) {
            components.add(Component.literal("Evoker-Book" + Name[num - 5]).withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.ITALIC));
            ComponentUtils.suffixOfChapterII(components);
        } else {
            components.add(Component.literal("ManaNote" + Name[num]).withStyle(styles[num]).withStyle(ChatFormatting.ITALIC));
            ComponentUtils.suffixOfChapterI(components);
        }
        super.appendHoverText(stack, level, components, flag);
    }

}
