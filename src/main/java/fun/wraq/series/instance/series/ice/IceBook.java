package fun.wraq.series.instance.series.ice;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
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

public class IceBook extends Item {

    public IceBook(Properties p_41383_) {
        super(p_41383_);
        Utils.manaDamage.put(this, 400d);
        Utils.manaPenetration0.put(this, 6d);
        Utils.maxMana.put(this, 50d);
        Utils.movementSpeedWithoutBattle.put(this, 1d);
        Utils.expUp.put(this, 1d);
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    private final Style style = CustomStyle.styleOfIce;

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.setHoverName(Component.literal("冰封的记忆").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("魔导书").withStyle(CustomStyle.styleOfMana)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionPassive(components, Component.literal("千里冰封").withStyle(style));
        components.add(Component.literal("每过5s，你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("将使目标").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("减速").withStyle(style)).
                append(Component.literal("，并使你对其伤害提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("20%").withStyle(style)).
                append(Component.literal("，持续3s").withStyle(ChatFormatting.WHITE)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(Component.literal("IceMemory").withStyle(style).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, level, components, flag);
    }

    public static void IceBookPassive(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.IceBook.get())) {
            int TickCount = player.getServer().getTickCount();
            if (!Utils.IceBookCoolDownMap.containsKey(player) || Utils.IceBookCoolDownMap.get(player) < TickCount) {
                Utils.IceBookMobEffectPlayerMap.put(mob, player);
                Utils.IceBookMobEffectTickMap.put(mob, TickCount + 60);
                Utils.IceBookCoolDownMap.put(player, TickCount + 100);
                Compute.coolDownTimeSend(player, ModItems.IceBook.get().getDefaultInstance(), 100);
                Compute.addSlowDownEffect(mob, 60, 3);
                Compute.iceParticleCreate(mob);
                Compute.sendMobEffectHudToNearPlayer(mob, ModItems.IceBook.get(), "IceBookDamageEnhance", 60, 0, false);
            }
        }
    }

    public static double IceBookDamageEnhance(Player player, Mob mob) {
        int TickCount = player.getServer().getTickCount();
        if (Utils.IceBookMobEffectTickMap.containsKey(mob) && Utils.IceBookMobEffectTickMap.get(mob) > TickCount
                && Utils.IceBookMobEffectPlayerMap.get(mob).equals(player)) {
            return 0.2;
        }
        return 0;
    }
}
