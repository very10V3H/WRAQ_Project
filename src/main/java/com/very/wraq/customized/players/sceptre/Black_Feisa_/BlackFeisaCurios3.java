package com.very.wraq.customized.players.sceptre.Black_Feisa_;

import com.very.wraq.process.Power.PowerLogic;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.allay.Allay;
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

public class BlackFeisaCurios3 extends Item implements ICurioItem {

    public static Player Player;
    public static boolean IsOn;

    public BlackFeisaCurios3(Properties p_41383_) {
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
        Style style = CustomStyle.styleOfMoon;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("Manba out").withStyle(style));
        components.add(Component.literal(" 拥有10点").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术专精 - 术法全析").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("时，每").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("10000")).
                append(Component.literal("为你提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%魔法伤害提升与法球攻击增幅").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" 并且你的法术会进行").withStyle(ChatFormatting.WHITE).
                append(Component.literal("双重施法").withStyle(CustomStyle.styleOfMana)));
        Compute.DescriptionPassive(components,Component.literal("what can i say").withStyle(style));
        components.add(Component.literal(" 当你造成伤害时，为你提供一层层数，同时").withStyle(ChatFormatting.WHITE).
                append(Component.literal("牢大").withStyle(style)).
                append(Component.literal("会出现在你的旁边守护你并为你提供至多").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("30%总")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("50%魔法伤害提升").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" 孩子们 我回来了").withStyle(style));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚牢大，授予对维瑞阿契做出了杰出贡献的 - Black_FeiSa_").withStyle(ChatFormatting.AQUA));
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

    public static Allay allay;
    public static void SummonAllay(Player player) {
        if (!IsPlayer(player)) return;
        if (allay == null || !allay.isAlive()) {
            allay = new Allay(EntityType.ALLAY, player.level());
            allay.moveTo(player.position().add(1,2,1));
            allay.getAttribute(Attributes.MAX_HEALTH).setBaseValue(player.getMaxHealth());
            allay.setHealth(allay.getMaxHealth());
            allay.setCustomName(Component.literal("牢大").withStyle(ChatFormatting.YELLOW));
            allay.setCustomNameVisible(true);
            allay.getBrain().setMemory(MemoryModuleType.LIKED_PLAYER, player.getUUID());
            player.level().addFreshEntity(allay);
        }
    }

    public static void RemoveAllay(Player player) {
        if (!IsPlayer(player)) return;
        if (allay != null) {
            allay.remove(Entity.RemovalReason.KILLED);
            allay = null;
        }
    }

    public static void MoveAllayToPlayer(Player player) {
        if (!IsPlayer(player)) return;
        if (allay != null && allay.distanceTo(player) > 32) {
            allay.moveTo(player.position().add(1,2,1));
        }
    }

    public static double Passive1ManaDamageEnhance(Player player) {
        if (!IsPlayer(player)) return 0;
        return Compute.ManaSkillLevelGet(player.getPersistentData(),11) == 10 ? Compute.PlayerAttributes.PlayerManaDamage(player) / 10000 * 0.25 : 0;
    }

    public record ReleaseTimeAndIndex(int tick, int index) {}

    public static List<ReleaseTimeAndIndex> releaseList = new ArrayList<>();

    public static void ReleaseRecord(Player player, int index) {
        if (!IsPlayer(player)) return;
        releaseList.add(new ReleaseTimeAndIndex(player.getServer().getTickCount() + 4,index));
    }

    public static void Tick(Player player) {
        if (!IsPlayer(player)) return;
        int tickCount = player.getServer().getTickCount();
        releaseList.forEach(releaseTimeAndIndex -> {
            if (releaseTimeAndIndex.tick == tickCount) {
                PowerLogic.ReleaseByType(player,releaseTimeAndIndex.index);
            }
        });
        releaseList.removeIf(releaseTimeAndIndex -> releaseTimeAndIndex.tick <= tickCount);
        MoveAllayToPlayer(player);
    }

    public static int count = 0;
    public static int countLastTick = 0;
    public static void CountAdd(Player player) {
        if (!IsPlayer(player)) return;
        if (countLastTick < player.getServer().getTickCount()) count = 0;
        count = Math.min(100,count + 1);
        countLastTick = player.getServer().getTickCount() + 200;
        Compute.EffectLastTimeSend(player, ModItems.BlackFeisaCurios4.get().getDefaultInstance(),200,count);
        SummonAllay(player);
    }

    public static double ManaDamageUp(Player player) {
        if (!IsPlayer(player) || countLastTick < player.getServer().getTickCount()) return 1;
        return 1 + count * 0.003;
    }

    public static double Passive2ManaDamageEnhance(Player player) {
        if (!IsPlayer(player) || countLastTick < player.getServer().getTickCount()) return 1;
        return 1 + count * 0.005;
    }

}
