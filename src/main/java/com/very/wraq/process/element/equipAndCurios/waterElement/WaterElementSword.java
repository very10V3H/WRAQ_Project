package com.very.wraq.process.element.equipAndCurios.waterElement;

import com.very.wraq.process.element.Element;
import com.very.wraq.process.element.ElementValue;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ItemTier;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaterElementSword extends SwordItem{
    private final double BaseDamage = 600;
    private final double DefencePenetration0 = 4000;
    private final double CriticalHitRate = 0.30d;
    private final double CHitDamage = 0.9;
    private final double HealSteal = 0.08F;
    private final double SpeedUp = 0.5F;
    private final double AttackSpeedUp = -2f;
    public WaterElementSword(Properties properties){
        super(ItemTier.VMaterial,2,0,properties);
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.HealthSteal.put(this,this.HealSteal);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Element.WaterElementValue.put(this, 2d);
        Utils.AttackSpeedUp.put(this,AttackSpeedUp);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.SwordTag.put(this,1d);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        Compute.USE(player);
        return super.use(level, player, useHand);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfWater;
        Compute.ForgingHoverName(stack,Component.literal("").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长剑").withStyle(style)));
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
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        p_43278_.hurtAndBreak(0, p_43280_, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }
    @Override
    public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0d) {
            p_40998_.hurtAndBreak(0, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }
        return true;
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static Map<Integer, Integer> mobDefenceDecreaseTickMap = new HashMap<>();
    public static Map<Player, Integer> playerElementEnhanceTickMap = new HashMap<>();
    public static Map<Player, Integer> playerActiveCoolDownMap = new HashMap<>();

    public static void Active(Player player) {
        if (Compute.PlayerUseWithHud(player, playerActiveCoolDownMap,ModItems.WaterElementSword.get(),0,25)) {
            Vec3 pos = Compute.MyPlayerPickLocation(player, 15);
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(pos, 15, 15, 15));
            mobList.removeIf(mob -> mob.position().distanceTo(pos) > 6);
            mobList.forEach(mob -> {
                Element.ElementEffectAddToEntity(player,mob,Element.Water, ElementValue.PlayerWaterElementValue(player), true, PlayerAttributes.PlayerAttackDamage(player) * 4);
                mobDefenceDecreaseTickMap.put(mob.getId(), player.getServer().getTickCount() + 140);
            });
            ParticleProvider.DisperseParticle(pos, (ServerLevel) player.level(), 1, 1, 120, ModParticles.WaterElementParticle.get(), 1);
            ParticleProvider.DisperseParticle(pos, (ServerLevel) player.level(), 1.5, 1, 120, ModParticles.WaterElementParticle.get(), 1);
        }
    }

    public static double MobDefenceDecrease(Mob mob) {
        if (mobDefenceDecreaseTickMap.containsKey(mob.getId()) && mobDefenceDecreaseTickMap.get(mob.getId()) > mob.getServer().getTickCount()) {
            return 0.5;
        }
        return 1;
    }

    public static void Passive(LivingEntity livingEntity) {
        if (livingEntity instanceof Player player && player.getMainHandItem().is(ModItems.WaterElementSword.get())) {
            playerElementEnhanceTickMap.put(player, player.getServer().getTickCount() + 140);
            Compute.EffectLastTimeSend(player, ModItems.WaterElementSword.get().getDefaultInstance(), 140);
        }
    }

    public static double PlayerWaterElementValueEnhance(Player player) {
        if (playerElementEnhanceTickMap.containsKey(player) && playerElementEnhanceTickMap.get(player) > player.getServer().getTickCount()) {
            return 2;
        }
        return 1;
    }


}
