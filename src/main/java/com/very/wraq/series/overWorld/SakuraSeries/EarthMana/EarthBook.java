package com.very.wraq.series.overWorld.SakuraSeries.EarthMana;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class EarthBook extends Item {
    private final double ManaDamage = 800;
    private final double ManaPenetration0 = 600;
    private final double MaxMana = 50;
    private final double MovementSpeed = 1;
    private final double ExpUp = 1.5;
    private final double HealthSteal = 0.04;
    public EarthBook(Properties p_41383_) {
        super(p_41383_);
        Utils.ManaDamage.put(this,ManaDamage);
        Utils.ManaPenetration0.put(this,ManaPenetration0);
        Utils.MaxMana.put(this,MaxMana);
        Utils.MovementSpeed.put(this,MovementSpeed);
        Utils.ExpUp.put(this,ExpUp);
        Utils.ManaHealthSteal.put(this,HealthSteal);
        Utils.OffHandTag.put(this,1d);
        Utils.WeaponList.add(this);
    }
    private final Style style = CustomStyle.styleOfMana;

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.setHoverName(Component.literal("地蕴传世秘典").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("魔导书").withStyle(CustomStyle.styleOfMana)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("大地之力").withStyle(style));
        components.add(Component.literal(" 当你持续5s").withStyle(ChatFormatting.WHITE).
                append(Component.literal("移动幅度").withStyle(ChatFormatting.GREEN)).
                append(Component.literal("较小时，充满魔力的土地会吸收你的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxHealth("50%")).
                append(Component.literal("并将之以5%效率转化为").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("")).
                append(Component.literal("，加成持续20s。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" -大地之力仅在你的生命值大于75%时会触发").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryV(components);
        super.appendHoverText(stack,level,components,flag);
    }

    public static void EarthBookPlayerEffect(Player player) {
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.EarthBook.get())) {
            int TickCount = player.getServer().getTickCount();
            if (!Utils.EarthBookPlayerPosMap.containsKey(player)) Utils.EarthBookPlayerPosMap.put(player,new ArrayDeque<>());
            Queue<Vec3> vec3s = Utils.EarthBookPlayerPosMap.get(player);
            vec3s.add(player.position());
            if (vec3s.size() > 5) {
                if (vec3s.peek().distanceTo(player.position()) < 2) {
                    if (player.getHealth() / player.getMaxHealth() > 0.75) {
                        Utils.EarthBookPlayerEffectMap.put(player,TickCount + 400);
                        player.setHealth(player.getHealth() - player.getMaxHealth() * 0.5f);
                        Compute.EffectLastTimeSend(player,ModItems.EarthBook.get().getDefaultInstance(),400);
                    }
                }
                vec3s.poll();
            }
        }
    }
}
