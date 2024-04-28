package com.very.wraq.process.element.equipAndCurios.waterElement;

import com.very.wraq.process.element.Element;
import com.very.wraq.process.element.ElementValue;
import com.very.wraq.process.Particle.ParticleProvider;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.render.Particles.ModParticles;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.ModEntityType;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ItemTier;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

public class WaterElementSceptre extends SwordItem {

    public static Map<Player,Integer> coolDownMap = new HashMap<>();
    public WaterElementSceptre(Properties p_42964_) {
        super(ItemTier.VMaterial,2,0, p_42964_);
        Utils.ManaDamage.put(this, 3548d);
        Utils.ManaRecover.put(this, 30d);
        Utils.ManaPenetration0.put(this, 4000d);
        Utils.MovementSpeed.put(this, 0.4);
        Utils.ManaCost.put(this, 15d);
        Utils.CoolDownDecrease.put(this,0.2);
        Element.WaterElementValue.put(this, 2d);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.SceptreTag.put(this,1.0d);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfWater;
        Compute.ForgingHoverName(stack,Component.literal("").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE)));
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
    public boolean hurtEnemy(ItemStack p_40994_, LivingEntity p_40995_, LivingEntity p_40996_) {
        p_40994_.hurtAndBreak(0, p_40996_, (p_41007_) -> {
            p_41007_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.ManaAttack(player,14);
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(Items.AIR)
                && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
            if (data.contains(StringUtils.ManaCore.ManaCore)) {
                if (Utils.ManaCoreMap.isEmpty()) Utils.setManaCoreMap();
                player.setItemInHand(InteractionHand.OFF_HAND,new ItemStack(Utils.ManaCoreMap.get(data.getString(StringUtils.ManaCore.ManaCore))));
                data.remove(StringUtils.ManaCore.ManaCore);
            }
        }
        return super.use(level, player, interactionHand);
    }

    public static void Active(Player player) {
        if (Compute.PlayerUseWithHud(player, WaterElementSword.playerActiveCoolDownMap, ModItems.WaterElementSceptre.get(),0,25)) {
            Vec3 pos = Compute.MyPlayerPickLocation(player, 15);
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(pos, 15, 15, 15));
            mobList.removeIf(mob -> mob.position().distanceTo(pos) > 6);
            mobList.forEach(mob -> {
                Element.ElementEffectAddToEntity(player,mob,Element.Water, ElementValue.PlayerWaterElementValue(player), false, Compute.PlayerAttributes.PlayerManaDamage(player));
                WaterElementSword.mobDefenceDecreaseTickMap.put(mob.getId(), player.getServer().getTickCount() + 140);
            });
        }
    }

    public static void Passive(LivingEntity livingEntity) {
        if (livingEntity instanceof Player player && player.getMainHandItem().is(ModItems.WaterElementSceptre.get())) {
            WaterElementSword.playerElementEnhanceTickMap.put(player, player.getServer().getTickCount() + 140);
            Compute.EffectLastTimeSend(player, ModItems.WaterElementSceptre.get().getDefaultInstance(), 140);
        }
    }

    public static void Shoot(Player player) {
        Level level = player.level();
        CompoundTag data = player.getPersistentData();
        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player,15)) {
            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SEA.get(), player,level,
                    Compute.PlayerAttributes.PlayerManaDamage(player),
                    Compute.PlayerAttributes.PlayerManaPenetration(player),
                    Compute.PlayerAttributes.PlayerManaPenetration0(player),StringUtils.ParticleTypes.WaterElement1TickParticle);
            newArrow.setSilent(true);
            newArrow.setNoGravity(true);
            newArrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0,3,1);
            ProjectileUtil.rotateTowardsMovement(newArrow,0);
            level.addFreshEntity(newArrow);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player,1,0.75,20, ModParticles.WaterElementParticle.get());
            ParticleProvider.FaceCircleCreate((ServerPlayer) player,1.5,0.5,16, ModParticles.WaterElementParticle.get());
        }
    }
}
