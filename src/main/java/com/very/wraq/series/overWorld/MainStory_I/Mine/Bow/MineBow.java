package com.very.wraq.series.overWorld.MainStory_I.Mine.Bow;

import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.process.element.Element;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class MineBow extends WraqBow {
    private final int Num;
    public MineBow(Properties p_40524_, int Num) {
        super(p_40524_);
        this.Num = Num;
        Utils.AttackDamage.put(this, MineBowAttributes.BaseDamage[Num]);
        Utils.DefencePenetration0.put(this, MineBowAttributes.DefencePenetration0[Num]);
        Utils.CritRate.put(this, MineBowAttributes.CriticalRate[Num]);
        Utils.CritDamage.put(this, MineBowAttributes.CriticalDamage[Num]);
        Utils.MovementSpeed.put(this, MineBowAttributes.SpeedUp[Num]);
        Element.StoneElementValue.put(this, MineBowAttributes.StoneElementValue[Num]);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.BowTag.put(this,1.0d);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("精钢锻弓" + MineBowAttributes.Number[Num]).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长弓").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GRAY,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GRAY,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("钢屑").withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" -命中目标后，对周围大范围单位造成").append(Compute.AttributeDescription.AttackDamage(MineBowAttributes.Effect[Num])).
                append(Component.literal("的物理伤害。").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GRAY,ChatFormatting.WHITE);
        components.add(Component.literal("MineBow" + MineBowAttributes.Number[Num]).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
    }

    public int getNum() {
        return Num;
    }

    @Override
    public void shoot(ServerPlayer serverPlayer) {
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ModParticles.BLACKFOREST_RECALL.get());
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ModParticles.LONG_SEA.get());
    }
}
