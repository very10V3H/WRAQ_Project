package com.very.wraq.series.nether.Equip.MagmaSceptre;

import com.very.wraq.process.element.Element;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.mana.NewArrowMagma;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class MagmaSceptre extends WraqSceptre {
    private final int Num;
    public MagmaSceptre(Properties p_42964_, int Num) {
        super(p_42964_.rarity(Utils.MagmaItalic));
        this.Num = Num;
        Utils.ManaDamage.put(this,MagmaSceptreAttributes.ManaDamage[Num]);
        Utils.ManaRecover.put(this,MagmaSceptreAttributes.ManaRecover[Num]);
        Utils.ManaPenetration0.put(this,MagmaSceptreAttributes.ManaDefencePenetration0[Num]);
        Utils.MovementSpeed.put(this,MagmaSceptreAttributes.MovementSpeed[Num]);
        Utils.ManaCost.put(this,MagmaSceptreAttributes.ManaCost[Num]);
        Element.FireElementValue.put(this, MagmaSceptreAttributes.FireElementValue[Num]);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.SceptreTag.put(this,1.0d);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfVolcano;
        Compute.ForgingHoverName(stack,Component.literal("爆裂魔杖" + MagmaSceptreAttributes.Number[Num]).withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("爆裂").withStyle(style));
        components.add(Component.literal("法球在命中目标时会施加:").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术-熔岩能量附着").withStyle(CustomStyle.styleOfMana)));
        Compute.ManaCoreAddition(stack,components);
        components.add(Component.literal("Magma-Sceptre" + MagmaSceptreAttributes.Number[Num]).withStyle(style).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryIII(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void shoot(Player player) {
        CompoundTag data = player.getPersistentData();
        Level level = player.level();
        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player, (int) MagmaSceptreAttributes.ManaCost[Num])) {
            NewArrowMagma newArrow = new NewArrowMagma(player, level, PlayerAttributes.PlayerManaDamage(player),
                    PlayerAttributes.PlayerManaPenetration(player), PlayerAttributes.PlayerExpUp(player), PlayerAttributes.PlayerManaPenetration0(player));
            newArrow.setSilent(true);
            newArrow.setNoGravity(true);

            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
            WraqSceptre.adjustOrb(newArrow, player);
            level.addFreshEntity(newArrow);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ModParticles.LONG_VOLCANO.get());
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ModParticles.LONG_VOLCANO.get());
            Compute.SoundToAll(player, ModSounds.Mana.get());
        }
    }
}
