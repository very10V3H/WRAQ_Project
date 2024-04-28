package com.very.wraq.customized.players.sceptre.shangmengli;

import com.very.wraq.customized.Customize;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.series.instance.Ice.IceSceptreAttributes;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ShangMengLiSceptre2 extends SwordItem {
    private final int Num;
    public ShangMengLiSceptre2(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_, int Num) {
        super(p_42961_, p_42962_, p_42963_, p_42964_.rarity(Utils.SeaItalic));
        this.Num = Num;
        Utils.ManaDamage.put(this, Customize.ManaDamage);
        Utils.ManaRecover.put(this, 30d);
        Utils.ManaPenetration0.put(this, 280d);
        Utils.MovementSpeed.put(this, 0.4);
        Utils.ManaCost.put(this, 15d);
        Utils.MainHandTag.put(this,1d);
        Utils.SceptreTag.put(this,1.0d);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfSea;
        Compute.ForgingHoverName(stack,Component.literal("爆裂魔杖" + IceSceptreAttributes.Number[Num]).withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("潮起").withStyle(style));
        components.add(Component.literal(" 你的单次普通法球攻击会").withStyle(ChatFormatting.WHITE).
                append(Component.literal("释放两枚法球。").withStyle(style)));
        components.add(Component.literal(" 第一枚法球造成").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("200%")).
                append(Component.literal("真实伤害").withStyle(style)));
        components.add(Component.literal(" 第二枚法球造成").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("200%")).
                append(Component.literal("法术伤害，并提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("15%")).
                append(Component.literal("，持续5s，至多叠加至5层。").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionActive(components,Component.literal("怒涛").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("法术攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("将会提供充能").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 当充能满时，会根据").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术专精自适应").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("对周围所有单位造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("或").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("法术攻击").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" 大海的子民归乡的思念汇聚成了武器").withStyle(ChatFormatting.ITALIC).withStyle(style));
        Compute.CoolDownTimeDescription(components,12);
        Compute.ManaCoreAddition(stack,components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一把潮愿，授予对维瑞阿契做出了杰出贡献的 - shangmengli").withStyle(ChatFormatting.AQUA));
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
        Compute.ManaAttack(player,8);
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

    public int getNum() {
        return this.Num;
    }

    public static Vec3 startPos,desPos;

    public static void ParticleCreateInClient(Level level, Vec3 startPos
            , Vec3 desPos, ParticleOptions particleOptions, int totalParticleDotNum
            , int eachParticleDotNum, double eachRange) {

        Vec3 vec3 = desPos.subtract(startPos);
        Random random = new Random();
        for (int i = 0 ; i < totalParticleDotNum ; i ++) {
            Vec3 createDot = startPos.add(vec3.scale((double) i /totalParticleDotNum));
            for (int j = 0 ; j < eachParticleDotNum ; j ++) {
                double r = random.nextDouble(eachRange * 2) - eachRange;
                Vec3 particleDot = createDot.add(r,0,r);
                level.addParticle(particleOptions,particleDot.x,particleDot.y,particleDot.z,0,0,0);
            }
        }

    }

    public static void LineDamageProvide(Level level, Player player) {

    }

}
