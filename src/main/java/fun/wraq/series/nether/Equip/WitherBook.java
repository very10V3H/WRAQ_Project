package fun.wraq.series.nether.Equip;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.render.toolTip.CustomStyle;
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

    public WitherBook(Properties p_41383_) {
        super(p_41383_);
        Utils.manaDamage.put(this, 200d);
        Utils.manaPenetration0.put(this, 6d);
        Utils.maxMana.put(this, 50d);
        Utils.movementSpeedWithoutBattle.put(this, 0.5);
        Utils.expUp.put(this, 1d);
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    private final Style style = CustomStyle.styleOfWither;

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.setHoverName(Component.literal("凋零秘典").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("魔导书").withStyle(CustomStyle.styleOfMana)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        Compute.DescriptionPassive(components, Component.literal("凋零秘术").withStyle(style));
        components.add(Component.literal(" 任意").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("命中敌人时，击碎敌人").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDefence("25%")).
                append(Component.literal("并为你提供等额").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaPenetration("")).
                append(Component.literal("持续2s").withStyle(ChatFormatting.WHITE)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.suffixOfChapterIII(components);
        super.appendHoverText(stack, level, components, flag);
    }

    public static void witherBookEffect(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.WitherBook.get())) {
            double manaDefence = MobSpawn.MobBaseAttributes.getMobBaseAttribute(mob, MobSpawn.MobBaseAttributes.manaDefence);
            int tickCount = player.getServer().getTickCount();
            Utils.WitherBookMobEffectTick.put(mob, tickCount + 40);
            Compute.addManaDefenceDecreaseEffectParticle(mob, 40);
            Compute.sendMobEffectHudToNearPlayer(mob, ModItems.WitherBook.get(), "WitherBookManaDefenceDecrease", 40, 0, false);
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