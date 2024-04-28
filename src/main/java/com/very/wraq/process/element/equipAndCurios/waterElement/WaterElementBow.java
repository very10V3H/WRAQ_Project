package com.very.wraq.process.element.equipAndCurios.waterElement;

import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.process.element.Element;
import com.very.wraq.process.element.ElementValue;
import com.very.wraq.process.Particle.ParticleProvider;
import com.very.wraq.render.Particles.ModParticles;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class WaterElementBow extends BowItem {
    private final double BaseDamage = 600;
    private final double DefencePenetration0 = 4000;
    private final double CriticalHitRate = 0.25;
    private final double CHitDamage = 1.45;
    private final double SpeedUp = 0.6F;
    public WaterElementBow(Properties p_40524_) {
        super(p_40524_);
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Element.WaterElementValue.put(this, 2d);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.BowTag.put(this,1.0d);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfWater;
        Compute.ForgingHoverName(stack,Component.literal("<维瑞级>").withStyle(ChatFormatting.GOLD).append(Component.literal("破空之隼").withStyle(CustomStyle.styleOfSky).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长弓").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components,Component.literal("水皆缥碧").withStyle(style));
        components.add(Component.literal(" 对").withStyle(ChatFormatting.WHITE).
                append(Component.literal("指针位置").withStyle(CustomStyle.styleOfMoon)).
                append(Component.literal("一定范围内的目标施加").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("100%碧水元素").withStyle(style)).
                append(Component.literal("，并削减其").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Defence("50%")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDefence("50%")).
                append(Component.literal("，持续7s").withStyle(ChatFormatting.WHITE)));
        Compute.CoolDownTimeDescription(components,25);
        Compute.DescriptionPassive(components,Component.literal("千丈见底").withStyle(style));
        components.add(Component.literal(" 对目标完成一次").withStyle(ChatFormatting.WHITE).
                append(Component.literal("碧水元素").withStyle(style)).
                append(Component.literal("主动参与的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("元素反应").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("后，使").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("归一化碧水元素强度").withStyle(style)).
                append(Component.literal("提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("100%").withStyle(style)).
                append(Component.literal("，持续7s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 从流飘荡，任意东西").withStyle(ChatFormatting.ITALIC).withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfElement(components);
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public void releaseUsing(ItemStack p_40667_, Level p_40668_, LivingEntity p_40669_, int p_40670_) {

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.BowAttack(player,Utils.BowNumMap.get(StringUtils.BowNameString.WaterElementBow));
        return interactionHand.equals(InteractionHand.MAIN_HAND)
                ? InteractionResultHolder.success(player.getMainHandItem()) : InteractionResultHolder.success(player.getOffhandItem());    }

    public static void Active(Player player) {
        if (Compute.PlayerUseWithHud(player, WaterElementSword.playerActiveCoolDownMap, ModItems.WaterElementSword.get(),0,25)) {
            Vec3 pos = Compute.MyPlayerPickLocation(player, 15);
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(pos, 15, 15, 15));
            mobList.removeIf(mob -> mob.position().distanceTo(pos) > 6);
            mobList.forEach(mob -> {
                Element.ElementEffectAddToEntity(player,mob,Element.Water, ElementValue.PlayerWaterElementValue(player), true, Compute.PlayerAttributes.PlayerAttackDamage(player) * 4);
                WaterElementSword.mobDefenceDecreaseTickMap.put(mob.getId(), player.getServer().getTickCount() + 140);
            });
        }
    }

    public static void Passive(LivingEntity livingEntity) {
        if (livingEntity instanceof Player player && player.getMainHandItem().is(ModItems.WaterElementSword.get())) {
            WaterElementSword.playerElementEnhanceTickMap.put(player, player.getServer().getTickCount() + 140);
            Compute.EffectLastTimeSend(player, ModItems.WaterElementSword.get().getDefaultInstance(), 140);
        }
    }

    public static void Shoot(Player player) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5f, 1.0f);
        arrow.setCritArrow(true);
        serverPlayer.level().addFreshEntity(arrow);
        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ModParticles.WaterElementParticle.get());
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ModParticles.WaterElementParticle.get());
    }
}
