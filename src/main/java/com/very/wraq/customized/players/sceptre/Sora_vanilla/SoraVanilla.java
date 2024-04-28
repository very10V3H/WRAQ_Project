package com.very.wraq.customized.players.sceptre.Sora_vanilla;

import com.very.wraq.process.Particle.ParticleProvider;
import com.very.wraq.render.Particles.ModParticles;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class SoraVanilla extends Item implements ICurioItem {

    public static Player Player;
    public static boolean IsOn;

    public SoraVanilla(Properties p_41383_) {
        super(p_41383_);
        Utils.ManaDamage.put(this, 1728d);
        Utils.Defence.put(this, 300d);
        Utils.ManaPenetration0.put(this, 150d);
        Utils.ManaRecover.put(this, 30d);
        Utils.MaxMana.put(this, 100d);
        Utils.CustomizedList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = CustomStyle.styleOfMoon;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components, Component.literal("装逼我让你飞起来").withStyle(style));
        components.add(Component.literal(" 法术与技能将不会消耗").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("")).
                append(Component.literal("，每").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("100")).
                append(Component.literal("将为你提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("2500")));
        Compute.DescriptionPassive(components, Component.literal("黑手").withStyle(style));
        components.add(Component.literal(" 每过2s，若周围有敌人，则对周围所有敌人造成").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("400%")).
                append(Component.literal("，并提供1层充能").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 当充能满时，发射激光，受到激光影响的目标每0.5s受到").withStyle(ChatFormatting.WHITE).
                append(Component.literal("0.25倍").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("魔法伤害").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" “盖牌认输永远不在选择之列。”——凯德6号").withStyle(style));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚黑桃A，授予对维瑞阿契做出了杰出贡献的 - Sora_vanilla").withStyle(ChatFormatting.AQUA));
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

    public static int Count = 0;

    public static int LasetTick = 0;

    public static double ManaDamageUp(Player player) {
        if (!IsPlayer(player)) return 0;
        return (Compute.PlayerAttributes.PlayerMaxMana(player) + 100) * 2500 / 100;
    }

    public static void Passive(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (TickCount % 40 == 0) {
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 15, 15, 15));
            mobList.removeIf(mob -> mob.distanceTo(player) > 6);
            mobList.forEach(mob -> {
                Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, 4, false);
                ParticleProvider.LineParticle(player.level(), 20, player.position(), mob.position(), ModParticles.LONG_SNOW.get());
            });
            if (mobList.size() > 0) {
                Count ++;
                if (Count == 5) {
                    Count = 0;
                    LasetTick = TickCount + 60;
                }
                Compute.EffectLastTimeSend(player,ModItems.BlackFeisaCurios3.get().getDefaultInstance(),8888,Count,true);
            }
        }
    }

    public static void Tick(Player player) {
        if (!IsPlayer(player)) return;
        Passive(player);
        Laser(player);
    }

    public static void Laser(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (LasetTick > TickCount) {
            Compute.Laser(player,ModParticles.SNOW.get(), Compute.XpStrengthAPDamage(player,0.25),10);
        }
    }

}
