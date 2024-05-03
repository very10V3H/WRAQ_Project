package com.very.wraq.series.overWorld.MainStory_II.Evoker;

import com.very.wraq.process.element.Element;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.mana.NewArrow;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
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

public class EvokerSword extends WraqSceptre {
    public EvokerSword(Properties p_42964_) {
        super(p_42964_);
        Utils.ManaDamage.put(this,this.ManaDamage);
        Utils.ManaRecover.put(this,this.ManaReply);
        Utils.ManaPenetration0.put(this,this.ManaPenetration0);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.ManaCost.put(this,(double) ManaCost);
        Element.LightningElementValue.put(this, 0.8);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.SceptreTag.put(this,1.0d);
    }
    private final double ManaDamage = 400.0d;
    private final double ManaReply = 10.0d;
    private final double ManaPenetration0 = 800;
    private final double SpeedUp = 0.4F;
    public static final int ManaCost = 20;
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("魔源之杖").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.LIGHT_PURPLE,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.LIGHT_PURPLE,ChatFormatting.WHITE);
        Compute.ManaCoreAddition(stack,components);
        components.add(Component.literal("Evoker-Sceptre").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryII(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void shoot(Player player) {
        CompoundTag data = player.getPersistentData();
        Level level = player.level();
        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player,EvokerSword.ManaCost)) {
            NewArrow newArrow = new NewArrow(player, level, PlayerAttributes.PlayerManaDamage(player), PlayerAttributes.PlayerManaPenetration(player), PlayerAttributes.PlayerExpUp(player), false, PlayerAttributes.PlayerManaPenetration0(player));
            newArrow.setSilent(true);
            newArrow.setNoGravity(true);

            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
            WraqSceptre.adjustOrb(newArrow, player);
            level.addFreshEntity(newArrow);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.WITCH);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.WITCH);
            Compute.SoundToAll(player, ModSounds.Mana.get());
        }
    }
}
