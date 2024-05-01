package com.very.wraq.process.element.equipAndCurios.lifeElement;

import com.very.wraq.process.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LifeElementSword extends SwordItem{
    private final double BaseDamage = 600;
    private final double DefencePenetration0 = 4000;
    private final double CriticalHitRate = 0.30d;
    private final double CHitDamage = 0.9;
    private final double HealSteal = 0.08F;
    private final double SpeedUp = 0.5F;
    private final double AttackSpeedUp = -2f;
    public LifeElementSword(Tier tier, int num1, float num2, Properties properties){
        super(tier,num1,num2,properties);
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.HealthSteal.put(this,this.HealSteal);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Element.LifeElementValue.put(this, 2d);
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
        Style style = CustomStyle.styleOfLife;
        Compute.ForgingHoverName(stack,Component.literal("").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长剑").withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components,Component.literal("化作春泥").withStyle(style));
        components.add(Component.literal(" 失去").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("80%当前")).
                append(Component.literal("，并在10s内为你回复").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Health("200%消耗的等额")));
        Compute.CoolDownTimeDescription(components,25);

        Compute.DescriptionPassive(components,Component.literal("护花").withStyle(style));
        components.add(Component.literal(" 处于").withStyle(ChatFormatting.WHITE).
                append(Component.literal("化作春泥").withStyle(style)).
                append(Component.literal("状态时，根据5s内回复的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Health("")).
                append(Component.literal("，为你提供等同于回复量12.5%的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("")));
        components.add(Component.literal(" 多件生机武器的效果将不会叠加").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" 落红不是无情物，化作春泥更护花\uD83C\uDF37").withStyle(ChatFormatting.ITALIC).withStyle(style));
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

    public static Map<Player,Integer> lifeElementActiveLastTick = new HashMap<>();
    public static Map<Player,Double> lifeElementActiveHealth = new HashMap<>();
    public static Map<Player,Integer> lifeElementActiveCoolDown = new HashMap<>();
    public static Map<Player,List<ShortTimeStoreHealth>> playerShortTimeStoreHealthMap = new HashMap<>();

    public record ShortTimeStoreHealth(int tickCount, double num) {}

    public static void Active(Player player) {
        if (Compute.PlayerUseWithHud(player, lifeElementActiveCoolDown,ModItems.LifeElementSword.get(), lifeElementActiveLastTick,100,0,25)) {
            lifeElementActiveHealth.put(player, player.getHealth() * 0.8);
            Compute.PlayerHealthDecrease(player,player.getHealth() * 0.8,Component.literal(" 被生机元素吞噬了。").withStyle(CustomStyle.styleOfLife));
        }
    }

    public static void Tick(Player player) {
        if (lifeElementActiveLastTick.containsKey(player) && lifeElementActiveLastTick.get(player) >= player.getServer().getTickCount()) {
            int tickCount = lifeElementActiveLastTick.get(player) - player.getServer().getTickCount();
            Compute.EffectLastTimeSend(player,ModItems.LifeElementSword.get().getDefaultInstance(),tickCount,tickCount,true);
            Compute.PlayerHeal(player, lifeElementActiveHealth.get(player) * 0.01);
        }
    }

    public static void StoreToList(Player player, double num) {
        if (lifeElementActiveLastTick.containsKey(player) && lifeElementActiveLastTick.get(player) > player.getServer().getTickCount()) {
            if (!playerShortTimeStoreHealthMap.containsKey(player)) playerShortTimeStoreHealthMap.put(player,new ArrayList<>());
            List<ShortTimeStoreHealth> list = playerShortTimeStoreHealthMap.get(player);
            list.removeIf(shortTimeStoreHealth -> shortTimeStoreHealth.tickCount < player.getServer().getTickCount());
            list.add(new ShortTimeStoreHealth(player.getServer().getTickCount() + 100,num));
        }
    }

    public static double ComputeStoreNum(Player player) {
        if (!playerShortTimeStoreHealthMap.containsKey(player)) return 0;
        List<ShortTimeStoreHealth> list = playerShortTimeStoreHealthMap.get(player);
        double sum = 0;
        list.removeIf(shortTimeStoreHealth -> shortTimeStoreHealth.tickCount < player.getServer().getTickCount());
        for (ShortTimeStoreHealth shortTimeStoreHealth : list) sum += shortTimeStoreHealth.num;
        return sum;
    }

    public static double ExAttackDamage(Player player) {
        if (Utils.SwordTag.containsKey(player.getMainHandItem().getItem())) return ComputeStoreNum(player) * 0.125;
        return 0;
    }
}
