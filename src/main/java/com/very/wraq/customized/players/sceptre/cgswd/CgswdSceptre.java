package com.very.wraq.customized.players.sceptre.cgswd;

import com.very.wraq.customized.Customize;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class CgswdSceptre extends SwordItem {
    public CgswdSceptre(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
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
        Style style = CustomStyle.styleOfYSR;
        Compute.ForgingHoverName(stack,Component.literal("爆裂魔杖").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(CustomStyle.styleOfMana)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("灵化").withStyle(style));
        components.add(Component.literal(" 普通法球攻击").withStyle(CustomStyle.styleOfMana).
                append(Component.literal("将转化为").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("激光").withStyle(style)).
                append(Component.literal("，若指针处无目标，激光将会自动寻找周围的敌人").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 激光").withStyle(style).
                append(Component.literal("拥有150%基础伤害").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionActive(components,Component.literal("双衍王镜").withStyle(style));
        components.add(Component.literal(" 受士魂大帝影响，激光将附带其中一种效果").withStyle(style));
        components.add(Component.literal(" 1.喜悦:").withStyle(ChatFormatting.AQUA).
                append(Component.literal("激光将吸附敌人，并回复自身").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxHealth("2.5%")));
        components.add(Component.literal(" 2.愤怒:").withStyle(ChatFormatting.RED).
                append(Component.literal("激光将点燃敌人，使敌人每0.5s受到").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1.5倍").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("魔法伤害").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" 3.恐惧:").withStyle(CustomStyle.styleOfLightingIsland).
                append(Component.literal("激光将召唤雷电，在敌人附近造成").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamageValue("500%")).
                append(Component.literal("，并禁锢敌人").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 4.嫉妒:").withStyle(CustomStyle.styleOfHealth).
                append(Component.literal("激光将无视敌人").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDefence("100%")));
        components.add(Component.literal(" 5.悲伤").withStyle(CustomStyle.styleOfSea).
                append(Component.literal("激光将减速敌人，并使自身获得").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("50%")));
        Compute.ManaCoreAddition(stack,components);
        components.add(Component.literal("疯狂的国王、神秘的漂泊者、变色的非现实世界————超凡而致命").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作托里德，授予对维瑞阿契做出了杰出贡献的 - cgswd").withStyle(ChatFormatting.AQUA));
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
        if (!level.isClientSide) {
            CgswdSceptre.player = player;
            CgswdSceptre.attackTick = 8;
        }
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

    public static Player player;
    public static int attackTick = 0;
    public static int mode = 0;
    public static int coolDownTick = 0;

    public static boolean IsPlayer(Player player) {
        return CgswdSceptre.player != null && CgswdSceptre.player.equals(player)
                && player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.CgswdSceptre.get());
    }

    public static void SwitchMode(Player player) {
        if (mode < 4) mode++;
        else mode = 0;
        Compute.EffectLastTimeSend(player, ModItems.CgswdSceptre.get().getDefaultInstance(), 8888, mode + 1, true);
    }

    public static void Tick(Player player) {
        if (!IsPlayer(player)) return;
        if (attackTick > 0) {
            attackTick --;
            Laser(player);
        }
        if (player.tickCount % 20 == 0 && attackTick > 0) Gather(player);
    }

    public static void Laser(Player player) {
        Mob mob = null;
        if (Compute.detectPlayerPickMob(player,30,6) != null) mob = Compute.detectPlayerPickMob(player,30,6);
        if (mob == null) {
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(),60,60,60));
            mobList.removeIf(mob1 -> mob1.distanceTo(player) > 30);
            Mob nearestMob = null;
            double distance = 30;
            for (Mob mob1 : mobList) {
                if (mob1.distanceTo(player) < distance) {
                    nearestMob = mob1;
                    distance = mob1.distanceTo(player);
                }
            }
            mob = nearestMob;
        }
        if (mob == null) return;
        if (mob.isBaby() || mob instanceof Vex) Compute.TargetLocationLaser(player,mob.position().add(0,0.5,0), ModParticles.YSR.get(), PlayerAttributes.PlayerManaDamage(player) * 1.5,10);
        else Compute.TargetLocationLaser(player,mob.position().add(0,1.5,0), ModParticles.YSR.get(), PlayerAttributes.PlayerManaDamage(player) * 1.5,10);
    }

    public static void Gather(Player player) {
        if (mode != 0 || !IsPlayer(player)) return;
        Vec3 gatherPos = player.pick(25,0,false).getLocation();
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,AABB.ofSize(gatherPos,15,15,15));
        mobList.removeIf(mob -> mob.position().distanceTo(gatherPos) > 6);
        mobList.forEach(mob -> {
            Compute.MonsterGatherProvider(mob,10,gatherPos);
        });
        Compute.PlayerHeal(player,player.getMaxHealth() * 0.025);
    }

    public static void Fire(Player player, Mob mob) {
        if (mode != 1 || !IsPlayer(player)) return;
        Compute.IgniteMob(player,mob,40);
        Compute.Damage.LastXpStrengthDamageToMob(player,mob,1.5,40,10,false);
    }

    public static void Lightning(Player player, Mob mob) {
        if (mode != 2 || !IsPlayer(player)) return;
        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, player.level());
        lightningBolt.setVisualOnly(true);
        lightningBolt.moveTo(mob.position());
        player.level().addFreshEntity(lightningBolt);
        List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class,AABB.ofSize(mob.position(),15,15,15));
        mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 6);
        mobList.forEach(mob1 -> {
            Compute.Damage.ManaDamageToMonster_RateApDamage(player,mob1,5,false);
            Compute.AddSlowDownEffect(mob1,20,99);
        });
    }

    public static boolean ManaPenetrationFlag(Player player) {
        return mode == 3 && IsPlayer(player);
    }

    public static void SlowDown(Player player, Mob mob) {
        if (mode != 4 || !IsPlayer(player)) return;
        Compute.AddSlowDownEffect(mob,60,3);
    }

    public static double ExManaDamage(Player player) {
        return (mode == 4 && IsPlayer(player)) ? 0.5 : 0;
    }

}
