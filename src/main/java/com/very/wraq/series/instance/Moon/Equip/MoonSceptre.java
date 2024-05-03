package com.very.wraq.series.instance.Moon.Equip;

import com.very.wraq.Items.MobItem.MobArmor;
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
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoonSceptre extends WraqSceptre {

    public static Map<Player,Integer> coolDownMap = new HashMap<>();
    public MoonSceptre(Properties p_42964_) {
        super(p_42964_);
        Utils.ManaDamage.put(this, 2560d);
        Utils.ManaRecover.put(this, 30d);
        Utils.ManaPenetration0.put(this, 3200d);
        Utils.MovementSpeed.put(this, 0.4);
        Utils.ManaCost.put(this, 15d);
        Utils.CoolDownDecrease.put(this,0.2);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.SceptreTag.put(this,1.0d);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfMoon1;
        Compute.ForgingHoverName(stack,Component.literal("").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("行星之引").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("会将目标周围半径6内的其他敌人").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("小幅牵引").withStyle(style)).
                append(Component.literal("至目标位置").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionPassive(components,Component.literal("噬星之月 ").withStyle(style));
        components.add(Component.literal(" 你的下一次").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("将").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("吸收").withStyle(style)).
                append(Component.literal("目标周围半径6内所有单位的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("")).
                append(Component.literal("，提供在10s内持续衰减的4倍").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ExManaDamage("")));
        components.add(Component.literal(" 并为你提供持续20s的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("100%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("护盾").withStyle(ChatFormatting.GRAY)));
        Compute.CoolDownTimeDescription(components,27);
        Compute.ManaCoreAddition(stack,components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMoon(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void shoot(Player player) {
        CompoundTag data = player.getPersistentData();
        Level level = player.level();
        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player,15)) {
            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SNOW.get(), player,level,
                    PlayerAttributes.PlayerManaDamage(player),PlayerAttributes.PlayerManaPenetration(player),
                    PlayerAttributes.PlayerManaPenetration0(player), StringUtils.ParticleTypes.Sea);
            newArrow.setSilent(true);
            newArrow.setNoGravity(true);

            newArrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0,3,1);
            ProjectileUtil.rotateTowardsMovement(newArrow,0);
            WraqSceptre.adjustOrb(newArrow, player);
            level.addFreshEntity(newArrow);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player,1,0.75,20, ParticleTypes.FIREWORK);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player,1.5,0.5,16, ParticleTypes.FIREWORK);
        }
    }

    public static Map<Player,Double> manaDamageNumMap = new HashMap<>();
    public static Map<Player,Integer> manaDamageTickMap = new HashMap<>();

    public static void MoonSceptreActive(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.MoonSceptre.get())) {
            int TickCount = player.getServer().getTickCount();
            if (!coolDownMap.containsKey(player) || coolDownMap.get(player) < TickCount) {
                coolDownMap.put(player, (int) (TickCount + 540 * (1 - PlayerAttributes.PlayerCoolDownDecrease(player))));
                Compute.CoolDownTimeSend(player,ModItems.MoonSceptre.get().getDefaultInstance(), (int) (540 * (1 - PlayerAttributes.PlayerCoolDownDecrease(player))));
                Compute.PlayerShieldProvider(player,400,PlayerAttributes.PlayerManaDamage(player));
                Compute.EffectLastTimeSend(player,ModItems.MoonSceptre.get().getDefaultInstance(),200);
                List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class,AABB.ofSize(mob.position(),15,15,15));
                mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 6);
                double attackDamage = 0;
                for (Mob mob1 : mobList) {
                    ItemStack helmet = mob1.getItemBySlot(EquipmentSlot.HEAD);
                    if (helmet.getItem() instanceof MobArmor mobArmor) {
                        attackDamage += mobArmor.AttackDamage;
                    }
                }
                List<Player> playerList = mob.level().getEntitiesOfClass(Player.class,AABB.ofSize(mob.position(),15,15,15));
                playerList.removeIf(player1 -> player1.distanceTo(mob) > 6);
                for (Player player1 : playerList) {
                    attackDamage += PlayerAttributes.PlayerAttackDamage(player1);
                }
                manaDamageNumMap.put(player,attackDamage);
                manaDamageTickMap.put(player,player.getServer().getTickCount() + 200);

            }
        }
    }

    public static void Passive(Player player, Mob mob) {
        if (!player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.MoonSceptre.get())) return;
        List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(),15,15,15));
        mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 6 || mob1.equals(mob));
        mobList.forEach(mob1 -> {
            Compute.MonsterGatherProvider(mob1,2,mob.position());
        });
    }

    public static double ExManaDamage(Player player) {
        int tickCount = player.getServer().getTickCount();
        if (manaDamageTickMap.containsKey(player) && manaDamageTickMap.get(player) > tickCount) {
            return manaDamageNumMap.get(player) * 4 * (manaDamageTickMap.get(player) - tickCount) / 200;
        }
        return 0;
    }
}
