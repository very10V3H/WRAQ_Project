package com.very.wraq.process.element.equipAndCurios.lifeElement;

import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.process.element.Element;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.very.wraq.process.element.equipAndCurios.lifeElement.LifeElementSword.*;

public class LifeElementBow extends BowItem {
    private final double BaseDamage = 600;
    private final double DefencePenetration0 = 4000;
    private final double CriticalHitRate = 0.25;
    private final double CHitDamage = 1.45;
    private final double SpeedUp = 0.6F;
    public LifeElementBow(Properties p_40524_) {
        super(p_40524_);
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Element.LifeElementValue.put(this, 2d);

        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.BowTag.put(this,1.0d);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfLife;
        Compute.ForgingHoverName(stack,Component.literal("<维瑞级>").withStyle(ChatFormatting.GOLD).append(Component.literal("破空之隼").withStyle(CustomStyle.styleOfSky).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长弓").withStyle(ChatFormatting.WHITE)));
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
        components.add(Component.literal(" 根据5s内回复的").withStyle(ChatFormatting.WHITE).
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
    public void releaseUsing(ItemStack p_40667_, Level p_40668_, LivingEntity p_40669_, int p_40670_) {

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.BowAttack(player,Utils.BowNumMap.get(StringUtils.BowNameString.LifeElementBow));
        return interactionHand.equals(InteractionHand.MAIN_HAND)
                ? InteractionResultHolder.success(player.getMainHandItem()) : InteractionResultHolder.success(player.getOffhandItem());    }

    public static void Active(Player player) {
        if (Compute.PlayerUseWithHud(player, lifeElementActiveCoolDown,ModItems.LifeElementBow.get(), lifeElementActiveLastTick,100,0,25)) {
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
            List<LifeElementSword.ShortTimeStoreHealth> list = playerShortTimeStoreHealthMap.get(player);
            list.removeIf(shortTimeStoreHealth -> shortTimeStoreHealth.tickCount() < player.getServer().getTickCount());
            list.add(new LifeElementSword.ShortTimeStoreHealth(player.getServer().getTickCount() + 100,num));
        }
    }

    public static double ComputeStoreNum(Player player) {
        if (!playerShortTimeStoreHealthMap.containsKey(player)) return 0;
        List<LifeElementSword.ShortTimeStoreHealth> list = playerShortTimeStoreHealthMap.get(player);
        double sum = 0;
        list.removeIf(shortTimeStoreHealth -> shortTimeStoreHealth.tickCount() < player.getServer().getTickCount());
        for (LifeElementSword.ShortTimeStoreHealth shortTimeStoreHealth : list) sum += shortTimeStoreHealth.num();
        return sum;
    }

    public static double ExAttackDamage(Player player) {
        if (Utils.BowTag.containsKey(player.getMainHandItem().getItem())) return ComputeStoreNum(player) * 0.125;
        return 0;
    }

    public static void Shoot(Player player) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true);        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5f, 1.0f);
        arrow.setCritArrow(true);
        serverPlayer.level().addFreshEntity(arrow);
        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ModParticles.LifeElementParticle.get());
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ModParticles.LifeElementParticle.get());
    }
}
