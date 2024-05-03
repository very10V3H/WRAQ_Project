package com.very.wraq.customized.players.sceptre.shangmengli;

import com.very.wraq.customized.Customize;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.Ice.IceSceptreAttributes;
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
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ShangMengLiSceptre extends WraqSceptre {
    private final int Num;
    public ShangMengLiSceptre(Properties p_42964_, int Num) {
        super(p_42964_.rarity(Utils.SeaItalic));
        this.Num = Num;
        Utils.ManaDamage.put(this, Customize.ManaDamage);
        Utils.ManaRecover.put(this, 30d);
        Utils.ManaPenetration0.put(this, Customize.ManaPenetration0);
        Utils.MovementSpeed.put(this, 0.4);
        Utils.ManaCost.put(this, 15d);
        Utils.MainHandTag.put(this,1d);
        Utils.CustomizedList.add(this);
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
    public void shoot(Player player) {
        double ManaCost = 15;
        CompoundTag data = player.getPersistentData();
        Level level = player.level();
        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player, (int) ManaCost)) {
            Utils.ShangMengLi = player;
            Utils.ShangMengLiManaCount = true;
            Utils.ShangMengLiDoubleManaAttackDelay = 3;
            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SEA.get(), player, level,
                    PlayerAttributes.PlayerManaDamage(player),
                    PlayerAttributes.PlayerManaPenetration(player),
                    PlayerAttributes.PlayerManaPenetration0(player), StringUtils.ParticleTypes.Sea);
            newArrow.setSilent(true);
            newArrow.setNoGravity(true);

            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
            WraqSceptre.adjustOrb(newArrow, player);
            level.addFreshEntity(newArrow);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.DRIPPING_WATER);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.DRIPPING_WATER);
            Compute.SoundToAll(player, ModSounds.Mana.get());
        }
    }
}
