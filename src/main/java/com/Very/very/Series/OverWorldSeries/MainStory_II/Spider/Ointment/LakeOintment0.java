package com.Very.very.Series.OverWorldSeries.MainStory_II.Spider.Ointment;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LakeOintment0 extends Item {
    public LakeOintment0(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·涂料").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal(" "));
        LakeOintment0.LakeOintmentCommonDescription(components,0);
        components.add(Component.literal("一般").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN).
                append(Component.literal("的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("湖泊能量涂料。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)));        components.add(Component.literal(" "));
        components.add(Component.literal("Items-Ointment").withStyle(Utils.styleOfSpider).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    public static void LakeOintmentCommonDescription (List<Component> components, int Level) {
        double Max = 7.5;
        if (Level == 0) {
            components.add(Component.literal(" - ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).
                    append(Component.literal("微光加成:").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider)).
                    append(Compute.AttributeDescription.CoolDown(Max * 0.25 + "%~" + Max * 0.75 + "%")));
        }
        else if (Level == 1) {
            components.add(Component.literal(" - ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).
                    append(Component.literal("微光加成:").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider)).
                    append(Compute.AttributeDescription.CoolDown(Max * 0.5 + "%~" + Max + "%")));
        }
        else if (Level == 2) {
            components.add(Component.literal(" - ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).
                    append(Component.literal("微光加成:").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider)).
                    append(Compute.AttributeDescription.CoolDown(Max * 0.75 + "%~" + Max + "%")));
        }
    }
}
