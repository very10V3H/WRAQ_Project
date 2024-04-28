package com.very.wraq.customized.players.sceptre.shangmengli;

import com.very.wraq.customized.uniform.Attributes;
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

public class ShangMengLiCurios2 extends Item implements ICurioItem {

    public ShangMengLiCurios2(Properties p_41383_) {
        super(p_41383_);
        Utils.ManaDamage.put(this, Attributes.ManaDamage);
        Utils.Defence.put(this,Attributes.Defence);
        Utils.ManaPenetration0.put(this, Attributes.ManaPenetration0);
        Utils.ManaRecover.put(this, Attributes.ManaRecover);
        Utils.MaxMana.put(this, Attributes.MaxMana);
        Utils.CoolDownDecrease.put(this, Attributes.CoolDown);
        Utils.CustomizedList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfMoon;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("物质解构").withStyle(style));
        components.add(Component.literal(" 每过").withStyle(ChatFormatting.WHITE).
                append(Component.literal("5s").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("若周围有目标，则对周围所有目标造成").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamageValue("1000%")).
                append(Component.literal("并叠加一层").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("物质解析").withStyle(style)));
        components.add(Component.literal(" 每拥有一层").withStyle(ChatFormatting.WHITE).
                append(Component.literal("物质解析").withStyle(style)).
                append(Component.literal("，在进行").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("10").withStyle(style)).
                append(Component.literal("次攻击后，对目标造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("等同于物质解析层数的").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("魔法伤害").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("，并叠加一层").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("物质解析").withStyle(style)));
        components.add(Component.literal(" 当你拥有").withStyle(ChatFormatting.WHITE).
                append(Component.literal("5").withStyle(style)).
                append(Component.literal("层时，解构模块将会发射").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("解构射线").withStyle(style)).
                append(Component.literal("，持续5s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" -解构射线每秒造成4次附带攻击特效的伤害").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" 数据分析->完成.").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚物质解构模块，授予对维瑞阿契做出了杰出贡献的 - shangmengli").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Utils.ShangMengLi = (Player) slotContext.entity();
        Utils.ShangMengLiCore2 = true;
        Compute.AddCuriosToList((Player) slotContext.entity(),stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Utils.ShangMengLi = null;
        Utils.ShangMengLiCore2 = false;
        Compute.RemoveCuriosInList((Player) slotContext.entity(),stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static int Count1 = 0;
    public static int Count2 = 0;
    public static int LaserTick = 0;
    public static void Passive(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (TickCount % 100 == 0) {
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(),25,25,25));
            mobList.forEach(mob -> {
                if (mob.distanceTo(player) < 10) {
                    Compute.Damage.ManaDamageToMonster_RateApDamage(player,mob,10,false);
                    ParticleProvider.LineParticle(player.level(),20,player.position(),mob.position(), ModParticles.LONG_SNOW.get());
                }
            });
            if (mobList.size() > 0) {
                Count1 ++;
                Compute.EffectLastTimeSend(player,ModItems.ShangMengLiCurios2.get().getDefaultInstance(),8888,Count1,true);
                LaserTickJudge(player);
            }
        }
    }
    public static double Count2ExDamage(Player player) {
        int TickCount = player.getServer().getTickCount();
        Count2 ++;
        Compute.EffectLastTimeSend(player,ModItems.ShangMengLiCurios21.get().getDefaultInstance(),8888,Count1,true);
        if (Count2 == 10) {
            Count2 = 0;
            if (LaserTick <= TickCount) Count1 ++;
            Compute.EffectLastTimeSend(player,ModItems.ShangMengLiCurios2.get().getDefaultInstance(),8888,Count1,true);
            LaserTickJudge(player);
            return Compute.XpStrengthAPDamage(player, (double) Count1);
        }
        return 0;
    }
    public static void LaserTickJudge(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (Count1 == 5) {
            LaserTick = TickCount + 100;
            Count1 = 0;
            Compute.EffectLastTimeSend(player, ModItems.ShangMengLiCurios2.get().getDefaultInstance(),100);
        }
    }
    public static void LaserShoot(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (LaserTick > TickCount) {
            Compute.Laser(player,ModParticles.SNOW.get(), Compute.PlayerAttributes.PlayerManaDamage(player),5);
        }
    }
    public static boolean IsShangMengLi(Player player) {
        return Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore2;
    }
    public static void Tick(Player player) {
        if (IsShangMengLi(player)) {
            Passive(player);
            LaserShoot(player);
        }
    }
}
