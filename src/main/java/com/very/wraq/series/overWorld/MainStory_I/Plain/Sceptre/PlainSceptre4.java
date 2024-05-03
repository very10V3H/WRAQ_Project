package com.very.wraq.series.overWorld.MainStory_I.Plain.Sceptre;

import com.very.wraq.process.element.Element;
import com.very.wraq.process.particle.ParticleProvider;
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
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PlainSceptre4 extends WraqSceptre {
    public PlainSceptre4(Properties p_42964_) {
        super(p_42964_);
        Utils.ManaDamage.put(this,this.ManaDamage);
        Utils.ManaRecover.put(this,this.ManaReply);
        Utils.ManaPenetration0.put(this,this.ManaPenetration0);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.ManaCost.put(this,(double) ManaCost);
        Element.LifeElementValue.put(this, 1d);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.SceptreTag.put(this,1.0d);
    }
    private double ManaDamage = 120.0d;
    private final double ManaPenetration0 = 450;
    private double ManaReply = 5.0d;
    private double SpeedUp = 0.4F;
    public static final int ManaCost = 15;
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("生机权杖-X").withStyle(CustomStyle.styleOfHealth).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfHealth,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfHealth,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("生机涌动").withStyle(CustomStyle.styleOfHealth));
        components.add(Component.literal("白天额外获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("45")).
                append(Component.literal("与")).
                append(Compute.AttributeDescription.ManaRecover("15")));
        PlainSceptre0.PlainSceptreDescription(components);
        Compute.ManaCoreAddition(stack,components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        components.add(Component.literal("Plain-Sceptre-IV").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);

        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void shoot(Player player) {
        CompoundTag data = player.getPersistentData();
        Level level = player.level();
        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player,PlainSceptre4.ManaCost)) {
            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_PLAIN.get(),
                    player, level, PlayerAttributes.PlayerManaDamage(player),
                    PlayerAttributes.PlayerManaPenetration(player),
                    PlayerAttributes.PlayerManaPenetration0(player),StringUtils.ParticleTypes.Plain);
            newArrow.setSilent(true);
            newArrow.setNoGravity(true);

            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
            WraqSceptre.adjustOrb(newArrow, player);
            level.addFreshEntity(newArrow);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.COMPOSTER);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.COMPOSTER);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 2, 0.25, 12, ParticleTypes.COMPOSTER);
            Compute.SoundToAll(player, ModSounds.Mana.get());
        }
    }
}
