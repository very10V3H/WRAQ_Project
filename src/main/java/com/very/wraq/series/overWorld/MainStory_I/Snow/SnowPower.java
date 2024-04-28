 package com.very.wraq.series.overWorld.MainStory_I.Snow;

import com.very.wraq.process.element.Element;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.ToolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

 public class SnowPower extends Item {

     private final int Level;
     public SnowPower(Properties p_41383_, int level) {
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
         components.add(Component.literal("·法术").withStyle(CustomStyle.styleOfMana));
         Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfMana,ChatFormatting.WHITE);
         Compute.DescriptionActive(components,Component.literal("冰封陵墓").withStyle(CustomStyle.styleOfSnow));
         components.add(Component.literal(" -禁锢周围所有敌人2s，并造成").withStyle(ChatFormatting.WHITE).
                 append(Compute.AttributeDescription.ManaDamageValue((Effect[Level] * 200) +"%")));
         components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                 append(Element.Description.IceElement("1 + 100%")));
         components.add(Component.literal(" -为技能范围内的所有玩家提供").withStyle(ChatFormatting.WHITE).
                 append(Component.literal("能力 - 智力 * 30").withStyle(CustomStyle.styleOfMana)).
                 append(Component.literal("护盾值").withStyle(ChatFormatting.GRAY)).
                 append(Component.literal("，持续4s。").withStyle(ChatFormatting.WHITE)));
         Compute.CoolDownTimeDescription(components,CoolDownTime[Level]);
         Compute.ManaCostDescription(components,ManaCost[Level]);

         Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfMana,ChatFormatting.WHITE);
         components.add(Component.literal("Powers-Snow").withStyle(CustomStyle.styleOfSnow));
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

     public static int[] ManaCost = {
             60,50,50,40
     };

     public static int[] CoolDownTime = {
             8,7,6,5
     };

     public static int[] Effect = {
             1,2,3,4
     };
 }
