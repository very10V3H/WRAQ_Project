package com.Very.very.Series.OverWorldSeries.MainStory_I.ManaBook;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
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
    private final int Num;
    public ManaNote(Properties p_41383_, int Num, double ManaDamage, double ManaPenetration, double ManaPenetration0,
                    double MaxMana, double ManaRecover, double MovementSpeed, double ExpUp) {
        super(p_41383_);
        Utils.ManaDamage.put(this,ManaDamage);
        Utils.ManaPenetration0.put(this,ManaPenetration0);
        Utils.MaxMana.put(this,MaxMana);
        Utils.MovementSpeed.put(this,MovementSpeed);
        Utils.ExpUp.put(this,ExpUp);
        this.ManaDamage = ManaDamage;
        this.ManaPenetration = ManaPenetration;
        this.ManaPenetration0 = ManaPenetration0;
        this.MaxMana = MaxMana;
        this.MovementSpeed = MovementSpeed;
        this.ExpUp = ExpUp;
        this.Num = Num;
        Utils.OffHandTag.put(this,1d);
        Utils.WeaponList.add(this);
    }
    public static final String[] Name = {
        "","¹","²","³","⁴","⁵","ⁱᶜᵉ"
    };

    public static final Style[] styles = {
            Utils.styleOfHealth,
            Utils.styleOfHealth,
            Utils.styleOfSea,
            Utils.styleOfVolcano,
            Utils.styleOfSnow,
            Utils.styleOfIce

    };

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        if (Num > 4) {
            stack.setHoverName(Component.literal("唤魔典籍" + Name[Num - 5]).withStyle(Utils.styleOfMana).withStyle(ChatFormatting.BOLD));
        }
        else stack.setHoverName(Component.literal("见习魔法师的笔记" + Name[Num]).withStyle(styles[Num]).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("魔导书").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMana,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMana,ChatFormatting.WHITE);
        if (Num > 4) {
            components.add(Component.literal("Evoker-Book" + Name[Num - 5]).withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.ITALIC));
            Compute.SuffixOfMainStoryII(components);
        }
        else {
            components.add(Component.literal("ManaNote" + Name[Num]).withStyle(styles[Num]).withStyle(ChatFormatting.ITALIC));
            Compute.SuffixOfMainStoryI(components);
        }
        super.appendHoverText(stack,level,components,flag);
    }

}
