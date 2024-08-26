package com.very.wraq.series.instance.series.castle;

import com.very.wraq.common.Compute;
import com.very.wraq.common.ModEntityType;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.ForgeItem;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class CastleSceptre extends WraqSceptre implements ForgeItem, ActiveItem {

    public CastleSceptre(Properties p_42964_) {
        super(p_42964_);
        Utils.manaDamage.put(this, 3000d);
        Utils.manaRecover.put(this, 30d);
        Utils.manaPenetration0.put(this, 3600d);
        Utils.movementSpeedWithoutBattle.put(this, 0.4);
        Utils.manaCost.put(this, 45d);
        Utils.coolDownDecrease.put(this, 0.2);
    }


    public void summonManaArrow(Player player, double rate) {
        Level level = player.level();
        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SNOW.get(), player, level,
                PlayerAttributes.manaDamage(player), PlayerAttributes.manaPenetration(player),
                PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Sea);
        newArrow.setSilent(true);
        newArrow.setNoGravity(true);

        newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 3, 1);
        ProjectileUtil.rotateTowardsMovement(newArrow, 0);
        WraqSceptre.adjustOrb(newArrow, player);
        level.addFreshEntity(newArrow);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.FIREWORK);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.FIREWORK);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfCastle;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("暗影秘法").withStyle(style));
        components.add(Component.literal(" 你的法球攻击将使敌人被拖入暗影之中").withStyle(ChatFormatting.ITALIC).withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术伤害").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("将附带造成伤害100%的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamageValue("")));
        Compute.DescriptionActive(components, Component.literal("噬魔注能").withStyle(style));
        components.add(Component.literal(" 扣除自身").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("15%当前")).
                append(Component.literal("，获得持续6s的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%伤害提升").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("以及").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.DefencePenetration("1500")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaPenetration("1500")));
        Compute.CoolDownTimeDescription(components, 15);
        components.add(Component.literal(" 多件暗黑武器的主动将会刷新持续时间，但效果将不会叠加，且共享冷却时间").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfCastle();
    }

    public static void ExDamage(Player player, Mob mob, double damage) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.CastleSceptre.get())) {
            Compute.Damage.AttackDamageToMonster_AdDamage_Direct(player, mob, damage, true);
        }
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.CastleSceptrePiece.get(), 12));
            add(new ItemStack(ModItems.CastlePiece.get(), 192));
            add(new ItemStack(ModItems.TreeRune.get(), 8));
            add(new ItemStack(ModItems.completeGem.get(), 26));
            add(new ItemStack(ModItems.ReputationMedal.get(), 104));
            add(new ItemStack(ModItems.RefiningGold.get(), 6));
            add(new ItemStack(ModItems.WorldSoul3.get(), 6));
        }};
    }

    @Override
    public void active(Player player) {
        Compute.playerItemCoolDown(player, this, 20);
        Compute.sendEffectLastTime(player, this, 120);
        CastleSword.CastleWeaponActiveLastTick.put(player, player.getServer().getTickCount() + 120);
        Compute.PlayerHealthDecrease(player, player.getHealth() * 0.15, Component.literal(" 被暗黑魔能吞噬了。").withStyle(CustomStyle.styleOfCastle));
    }
}
