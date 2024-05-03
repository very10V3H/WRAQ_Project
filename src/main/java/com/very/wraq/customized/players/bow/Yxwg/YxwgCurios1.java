package com.very.wraq.customized.players.bow.Yxwg;

import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class YxwgCurios1 extends Item implements ICurioItem {

    public static Player Player;
    public static boolean IsOn;

    public YxwgCurios1(Properties p_41383_) {
        super(p_41383_);
        Utils.AttackDamage.put(this, Attributes.AttackDamage);
        Utils.DefencePenetration0.put(this,Attributes.DefencePenetration0);
        Utils.CritDamage.put(this,Attributes.CritDamage);
        Utils.Defence.put(this,Attributes.Defence);
        Utils.CritRate.put(this,Attributes.CritRate);
        Utils.CustomizedList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfHealth;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("连诛").withStyle(style));
        components.add(Component.literal(" 箭矢命中目标时，有33%概率触发多重连射效果。在0.4s内射出").withStyle(ChatFormatting.WHITE).
                append(Component.literal("4支箭矢").withStyle(CustomStyle.styleOfFlexible)));
        Compute.DescriptionPassive(components,Component.literal("追猎").withStyle(style));
        components.add(Component.literal(" 箭矢命中目标时，若未触发多重连射，获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("25%总")));
        components.add(Component.literal(" 力量就像是与生俱来的一样，一点一点的击碎敌人").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚神射手之誓，授予对维瑞阿契做出了杰出贡献的 - yxwg").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Player = (Player) slotContext.entity();
        IsOn = true;
        Compute.AddCuriosToList(player,stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Player = null;
        IsOn = false;
        Compute.RemoveCuriosInList(player,stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static boolean IsPlayer(Player player) {
        return Player != null && Player.equals(player) && IsOn;
    }

    public static int ExArrowTick = 0;

    public static int AttackDamageUpTick = 0;

    public static void Tick(Player player) {
        if (!IsPlayer(player)) return;
        if (ExArrowTick > 0) {
            if (ExArrowTick % 2 == 0) Shoot(player);
            ExArrowTick --;
        }
    }

    public static void Passive(Player player) {
        if (!IsPlayer(player)) return;
        Random random = new Random();
        if (random.nextDouble() < 0.33) Passive1(player);
        else Passive2(player);
    }

    public static void Passive1(Player player) {
        if (!IsPlayer(player)) return;
        ExArrowTick = 8;
    }

    public static void Shoot(Player player) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        double rate = 1;
        if (YxwgBow.ActiveFlag) {
            if (YxwgBow.ActiveEffect(serverPlayer)) rate = 4;
        }
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                serverPlayer, false,false,rate);

        YxwgBow.BattleTick(serverPlayer);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, YxwgBow.IsManaArrow(serverPlayer) ? 1.5f : 4.5f, 1.0f);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.COMPOSTER);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.COMPOSTER);
    }

    public static void Passive2(Player player) {
        if (!IsPlayer(player)) return;
        int TickCount = player.getServer().getTickCount();
        AttackDamageUpTick = TickCount + 40;
        Compute.EffectLastTimeSend(player, ModItems.YxwgCurios1.get().getDefaultInstance(),40);
    }

    public static double AttackDamageUp(Player player) {
        if (!IsPlayer(player)) return 1;
        int TickCount = player.getServer().getTickCount();
        if (AttackDamageUpTick > TickCount) return 1.25;
        return 1;
    }

}
