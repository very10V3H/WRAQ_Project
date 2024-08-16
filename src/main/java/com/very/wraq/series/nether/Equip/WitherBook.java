package com.very.wraq.series.nether.Equip;

import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class WitherBook extends Item {
    private final double ManaDamage = 200;
    private final double ManaPenetration0 = 550;
    private final double MaxMana = 50;
    private final double MovementSpeed = 0.5;
    private final double ExpUp = 1;

    public WitherBook(Properties p_41383_) {
        super(p_41383_);
        Utils.manaDamage.put(this, ManaDamage);
        Utils.manaPenetration0.put(this, ManaPenetration0);
        Utils.maxMana.put(this, MaxMana);
        Utils.movementSpeedWithoutBattle.put(this, MovementSpeed);
        Utils.expUp.put(this, ExpUp);
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    private final Style style = CustomStyle.styleOfWither;

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.setHoverName(Component.literal("凋零秘典").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("魔导书").withStyle(CustomStyle.styleOfMana)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfAddition(components);
        Compute.DescriptionPassive(components, Component.literal("凋零秘术").withStyle(style));
        components.add(Component.literal(" 任意").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("命中敌人时，击碎敌人").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDefence("25%")).
                append(Component.literal("并为你提供等额").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaPenetration("")).
                append(Component.literal("持续2s").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryIII(components);
        super.appendHoverText(stack, level, components, flag);
    }

    public static void witherBookEffect(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.WitherBook.get())) {
            double manaDefence = MobSpawn.MobBaseAttributes.getMobBaseAttribute(mob, MobSpawn.MobBaseAttributes.manaDefence);
            int tickCount = player.getServer().getTickCount();
            Utils.WitherBookMobEffectTick.put(mob, tickCount + 40);
            Compute.AddManaDefenceDescreaseEffectParticle(mob, 40);
            if (Utils.WitherBookPlayerEffectNum.containsKey(player)) {
                if (Utils.WitherBookPlayerEffectTick.containsKey(player)) {
                    if (Utils.WitherBookPlayerEffectTick.get(player) < tickCount) {
                        Utils.WitherBookPlayerEffectNum.put(player, 0d);
                    }
                    double ManaDefence = Utils.WitherBookPlayerEffectNum.get(player);
                    if (manaDefence * 0.25 > ManaDefence) {
                        Utils.WitherBookPlayerEffectNum.put(player, manaDefence * 0.25);
                    }
                }
            } else Utils.WitherBookPlayerEffectNum.put(player, manaDefence * 0.25);
            Utils.WitherBookPlayerEffectTick.put(player, tickCount + 40);
            Compute.sendEffectLastTime(player, ModItems.WitherBook.get().getDefaultInstance(), 40);

        }
    }
}
