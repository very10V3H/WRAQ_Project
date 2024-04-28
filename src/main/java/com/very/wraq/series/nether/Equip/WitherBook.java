package com.very.wraq.series.nether.Equip;

import com.very.wraq.Items.MobItem.MobArmor;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class WitherBook extends Item {
    private final double ManaDamage = 400;
    private final double ManaPenetration0 = 550;
    private final double MaxMana = 50;
    private final double MovementSpeed = 0.5;
    private final double ExpUp = 1;
    public WitherBook(Properties p_41383_) {
        super(p_41383_);
        Utils.ManaDamage.put(this,ManaDamage);
        Utils.ManaPenetration0.put(this,ManaPenetration0);
        Utils.MaxMana.put(this,MaxMana);
        Utils.MovementSpeed.put(this,MovementSpeed);
        Utils.ExpUp.put(this,ExpUp);
        Utils.OffHandTag.put(this,1d);
        Utils.WeaponList.add(this);
    }
    private final Style style = CustomStyle.styleOfWither;

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.setHoverName(Component.literal("凋零秘典").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("魔导书").withStyle(CustomStyle.styleOfMana)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("凋零秘术").withStyle(style));
        components.add(Component.literal(" 任意").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("命中敌人时，击碎敌人").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDefence("25%")).
                append(Component.literal("并为你提供等额").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaPenetration("")).
                append(Component.literal("持续2s").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryIII(components);
        super.appendHoverText(stack,level,components,flag);
    }

    public static void WitherBookEffect(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.WitherBook.get())) {
            ItemStack itemStack = mob.getItemBySlot(EquipmentSlot.HEAD);
            if (itemStack.getItem() instanceof MobArmor mobArmor) {
                int TickCount = player.getServer().getTickCount();
                Utils.WitherBookMobEffectTick.put(mob,TickCount + 40);
                Compute.AddManaDefenceDescreaseEffectParticle(mob,40);
                if (Utils.WitherBookPlayerEffectNum.containsKey(player)) {
                    if (Utils.WitherBookPlayerEffectTick.containsKey(player)) {
                        if (Utils.WitherBookPlayerEffectTick.get(player) < TickCount) {
                            Utils.WitherBookPlayerEffectNum.put(player,0d);
                        }
                        double ManaDefence = Utils.WitherBookPlayerEffectNum.get(player);
                        if (mobArmor.ManaDefence * 0.25 > ManaDefence) {
                            Utils.WitherBookPlayerEffectNum.put(player,mobArmor.ManaDefence * 0.25);
                        }
                    }
                }
                else Utils.WitherBookPlayerEffectNum.put(player,mobArmor.ManaDefence * 0.25);
                Utils.WitherBookPlayerEffectTick.put(player,TickCount + 40);
                Compute.EffectLastTimeSend(player,ModItems.WitherBook.get().getDefaultInstance(),40);
            }
        }
    }
}
