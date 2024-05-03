package com.very.wraq.customized.players.sceptre.liulixian_;

import com.very.wraq.customized.Customize;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.ModEntityType;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LiulixianSceptre extends WraqSceptre {
    private final double BaseDamage = Customize.ManaDamage;
    private final double ManaCost = 15;
    private final double BreakDefence = 0.50d;
    private final double DefencePenetration0 = Customize.ManaPenetration0;
    private final double ManaRecover = 30;
    private final double SpeedUp = 0.5F;
    private final double AttackSpeedUp = -2f;
    public LiulixianSceptre(){
        super(new Properties().rarity(Utils.SakuraItalic));
        Utils.ManaDamage.put(this,this.BaseDamage);
        Utils.ManaCost.put(this,this.ManaCost);
        Utils.ManaPenetration0.put(this,this.DefencePenetration0);
        Utils.ManaRecover.put(this,this.ManaRecover);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.AttackSpeedUp.put(this,AttackSpeedUp);
        Utils.MainHandTag.put(this,1d);
        Utils.CustomizedList.add(this);
        Utils.SceptreTag.put(this,1d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfSakura;
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("雾切").withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("染霜琉璃颂歌").withStyle(style));
        components.add(Component.literal(" 手持").withStyle(ChatFormatting.WHITE).
                append(Component.literal("『雾切丶璃』").withStyle(ChatFormatting.BOLD).withStyle(style)).
                append(Component.literal("时被视为持有法杖，并提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("10%")));
        components.add(Component.literal(" 每拥有").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MovementSpeed("4%")).
                append(Component.literal("提供").withStyle(style)).
                append(Compute.AttributeDescription.MaxMana("1")));
        Compute.DescriptionActive(components,Component.literal("间月琉璃祝词").withStyle(style));
        components.add(Component.literal(" 以倾奇之姿汇聚寒霜，放出持续").withStyle(ChatFormatting.WHITE).
                append(Component.literal("5s").withStyle(CustomStyle.styleOfIce)).
                append(Component.literal("行进的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("霜见雪关扉").withStyle(CustomStyle.styleOfIce)).
                append(Component.literal("，并额外释放两股规模较小的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("霜见雪关扉").withStyle(CustomStyle.styleOfIce)).
                append(Component.literal("，各自能造成原本20%的伤害。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 霜见雪关扉").withStyle(CustomStyle.styleOfIce));
        components.add(Component.literal(" -以刀锋般尖锐的霜风持续切割触及的敌人，造成").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamageValue("200%")).
                append(Component.literal("，每秒最多造成1次伤害").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" -持续时间结束时绽放，对周围所有敌人造成").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamageValue("800%")));
        components.add(Component.literal(" -霜见雪关扉被视为普通法球攻击").withStyle(ChatFormatting.LIGHT_PURPLE));
        Compute.CoolDownTimeDescription(components,8);
        components.add(Component.literal(" 可切换为").withStyle(ChatFormatting.WHITE).
                append(Component.literal("『雾切丶琉』").withStyle(ChatFormatting.BOLD).withStyle(style)));
        components.add(Component.literal(" 「吹雪濡鹭时，积思若霜，胸厎思重焉哀怜。」").withStyle(style));
        Compute.ManaCoreAddition(stack,components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一把『雾切丶琉』，授予对维瑞阿契做出了杰出贡献的 - liulixian_").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void shoot(Player player) {
        double ManaCost = 15;
        CompoundTag data = player.getPersistentData();
        Level level = player.level();
        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player, (int) ManaCost)) {
            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_WORLD.get(),
                    player, level, PlayerAttributes.PlayerManaDamage(player),
                    PlayerAttributes.PlayerManaPenetration(player),
                    PlayerAttributes.PlayerManaPenetration0(player), StringUtils.ParticleTypes.World,true);
            newArrow.setSilent(true);
            newArrow.setNoGravity(true);

            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 2.5f, 1.0f);
            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
            WraqSceptre.adjustOrb(newArrow, player);
            level.addFreshEntity(newArrow);
            Compute.FaceIceParticleCreate(player,player.level());
            Compute.SoundToAll(player, ModSounds.Mana.get());
        }
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
