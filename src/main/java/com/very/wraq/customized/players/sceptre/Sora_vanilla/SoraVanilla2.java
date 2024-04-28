package com.very.wraq.customized.players.sceptre.Sora_vanilla;

import com.very.wraq.process.Power.PowerLogic;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SoraVanilla2 extends Item implements ICurioItem {

    public static Player Player;
    public static boolean IsOn;

    public SoraVanilla2(Properties p_41383_) {
        super(p_41383_);
        Utils.ManaDamage.put(this, 1728d);
        Utils.Defence.put(this, 300d);
        Utils.ManaPenetration0.put(this, 150d);
        Utils.ManaRecover.put(this, 30d);
        Utils.MaxMana.put(this, 100d);
        Utils.CoolDownDecrease.put(this, 0.3);
        Utils.CustomizedList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = CustomStyle.styleOfPower;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);

        Compute.DescriptionPassive(components,Component.literal("初始之火的继承者").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术技能").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("会进行双重施法，每释放一个技能扣除").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Health("10%当前")).
                append(Component.literal("，并为你提供持续3s的等量").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("护盾值").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal(" -生命值扣除不会使你的生命值低于20%最大生命值").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));

        Compute.DescriptionPassive(components,Component.literal("血缘之末—洛斯里克的圣王").withStyle(style));
        components.add(Component.literal(" 当你的拥有低于某一阈值的生命值时，为你提供法术强度:").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" 1.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("90%")).
                append(Component.literal(":").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("30%")));
        components.add(Component.literal(" 2.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("70%")).
                append(Component.literal(":").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("50%")));
        components.add(Component.literal(" 2.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("70%")).
                append(Component.literal(":").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("50%")));
        components.add(Component.literal(" 3.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("50%")).
                append(Component.literal(":").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("70%")));
        components.add(Component.literal(" 4.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("25%")).
                append(Component.literal(":").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("90%")));
        components.add(Component.literal(" 在低于").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("25%")).
                append(Component.literal("时，提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("80%伤害提升").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" 每一代薪王都会继承上一代的诅咒，继续燃烧自己的灵魂來传承火，" +
                "一个强大的灵魂的死亡才能够换来这个世界的苟延残喘，我们到底该何去何从呢？我们是要熄灭这火焰，" +
                "然后进入无人知晓的黑暗时代还是继续牺牲灵魂，来使这世界苟延残喘下去。").withStyle(style));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚黑暗灵魂，授予对维瑞阿契做出了杰出贡献的 - Sora_vanilla").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Player = (Player) slotContext.entity();
        IsOn = true;
        Compute.AddCuriosToList(player, stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Player = null;
        IsOn = false;
        Compute.RemoveCuriosInList(player, stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static boolean IsPlayer(Player player) {
        return Player != null && Player.equals(player) && IsOn;
    }

    public record ReleaseTimeAndIndex(int tick, int index) {}

    public static List<ReleaseTimeAndIndex> releaseList = new ArrayList<>();

    public static void ReleaseRecord(Player player, int index) {
        if (!IsPlayer(player)) return;
        releaseList.add(new ReleaseTimeAndIndex(player.getServer().getTickCount() + 4,index));
        Compute.PlayerShieldProvider(player,60,player.getHealth() * 0.1);
        Compute.PlayerHealthDecrease(player,player.getHealth() * 0.1,0.2);
    }

    public static void Tick(Player player) {
        if (!IsPlayer(player)) return;
        int tickCount = player.getServer().getTickCount();
        releaseList.forEach(releaseTimeAndIndex -> {
            if (releaseTimeAndIndex.tick == tickCount) {
                PowerLogic.ReleaseByType(player,releaseTimeAndIndex.index);
            }
        });
        releaseList.removeIf(releaseTimeAndIndex -> releaseTimeAndIndex.tick == tickCount);

        if (player.getHealth() / player.getMaxHealth() <= 0.25) {
            Compute.EffectLastTimeSend(player, ModItems.SoraCurios2.get().getDefaultInstance(),8888,0,true);
        } // hud display
    }

    public static double ExManaDamage(Player player) {
        if (!IsPlayer(player)) return 1;
        double rate = player.getHealth() / player.getMaxHealth();
        if (rate <= 0.25) {
            return 1.9;
        } else if (rate <= 0.5) {
            return 1.7;
        } else if (rate <= 0.7) {
            return 1.5;
        } else if (rate <= 0.9) {
            return 1.3;
        }
        return 1;
    }

    public static double ManaDamageEnhance(Player player) {
        if (!IsPlayer(player)) return 0;
        if (player.getHealth() / player.getMaxHealth() <= 0.25) return 0.8;
        return 0;
    }

}
