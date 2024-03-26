package com.Very.very.Customize.Players.YuanShiRen;

import com.Very.very.Customize.Customize;
import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.AnimationPackets.NULLAnimationS2CPacket;
import com.Very.very.NetWorking.Packets.AnimationPackets.YSRAnimationS2CPacket;
import com.Very.very.Process.Particle.ParticleProvider;
import com.Very.very.Projectile.Mana.ManaArrow;
import com.Very.very.CoreAttackModule.ManaAttackModule;
import com.Very.very.Render.Particles.ModParticles;
import com.Very.very.ValueAndTools.BasicAttributeDescription;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.ModEntityType;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class YuanShiRen extends PickaxeItem {
    private final int Num;
    public YuanShiRen(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_, int Num) {
        super(p_42961_, p_42962_, p_42963_, p_42964_.rarity(Utils.YSRItalic));
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
        Style style = Utils.styleOfYSR;
        Compute.ForgingHoverName(stack,Component.literal("").withStyle(ChatFormatting.RESET).withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("Sentient喷溅").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 普通法球攻击").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana).
                append(Component.literal("改为射出1根").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("能量触手").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("以及").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("4根").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("能量卷须").withStyle(ChatFormatting.RESET).withStyle(style)));
        components.add(Component.literal(" 触手与卷须").withStyle(ChatFormatting.RESET).withStyle(style).
                append(Component.literal("会自动寻找并连接攻击范围内的目标，每0.25s对正在连接的目标造成1次伤害").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("  - 每条触手造成").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamageValue("250%")));
        components.add(Component.literal("  - 每条卷须造成").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamageValue("50%")));
        Compute.DescriptionActive(components,Component.literal("Sentient涌现").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 将所有").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("能量卷须").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("升级为").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("能量触手").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("持续5s").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        Compute.CoolDownTimeDescription(components,10);
        Compute.ManaCoreAddition(stack,components);
        components.add(Component.literal(" 一把融合了机械的自适应性和生物的再生能力的手枪，对低等生物进行毁灭性打击。").withStyle(ChatFormatting.RESET).withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一把视使之触，授予对维瑞阿契做出了杰出贡献的 - 疯狂原-是人").withStyle(ChatFormatting.AQUA));
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
            ActiveTick = 8;
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

    public static List<Mob> mobList = new ArrayList<>();

    public static int EnhanceTick = 0;

    public static int EnhanceCoolDown = 0;

    public static int ActiveTick = 0;

    public static boolean IsAnimation = false;

    public static boolean IsOn(Player player) {
        return player.getMainHandItem().is(ModItems.YuanShiRenSceptre.get());
    }

    public static void FindMob(Player player) {
        Level level = player.level();
        Vec3 TargetPos = player.pick(60,0,false).getLocation();
        if (Compute.powerDetectPlayerPickMob(player,60,12) != null) TargetPos = Compute.powerDetectPlayerPickMob(player,60,12).position();

        Vec3 finalTargetPos = TargetPos;
        mobList.removeIf(mob -> mob.position().distanceTo(finalTargetPos) > 6 || mob.isDeadOrDying());
        List<Mob> mobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(TargetPos,15,15,15));
        Vec3 finalTargetPos1 = TargetPos;
        mobList1.removeIf(mob -> mob.position().distanceTo(finalTargetPos1) > 6);
        mobList1.forEach(mob -> {
            if (!mobList.contains(mob) && mobList.size() < 5) mobList.add(mob);
        });
    }

    public static void Active(Player player) {
        if (!IsOn(player)) return;
        int TickCount = player.getServer().getTickCount();
        if (TickCount > EnhanceCoolDown) {
            EnhanceTick = TickCount + 100;
            Compute.CoolDownTimeSend(player,ModItems.YuanShiRenSceptre.get().getDefaultInstance(),200);
            EnhanceCoolDown = TickCount + 200;
        }
    }

    public static boolean IsEnhance(Player player) {
        int TickCount = player.getServer().getTickCount();
        return EnhanceTick > TickCount;
    }

    public static void Tick(Player player) {
        if (!IsOn(player)) return;
        if (ActiveTick > 0 && !IsAnimation) {
            Animation(player,false);
            IsAnimation = true;
        }
        if (ActiveTick == 0 && IsAnimation) {
            Animation(player,true);
            IsAnimation = false;
        }
        if (ActiveTick > 0) {
            ActiveTick --;
            FindMob(player);
            if (mobList.size() == 0) return;
            Vec3 TargetPos = player.pick(30,0,false).getLocation();
            Mob neareatMob = null;
            double distance = 50;
            for (Mob mob : mobList) {
                if (mob.position().distanceTo(TargetPos) < distance) {
                    neareatMob = mob;
                    distance = mob.position().distanceTo(TargetPos);
                }
            }
            if (neareatMob == null) return;
            int nearestMobCount = 0;
            for (int i = 0 ; i < 5 ; i ++) {
                Mob mob;
                if (i >= mobList.size()) mob = neareatMob;
                else mob = mobList.get(i);
                if (mob == neareatMob) {
                    ParticleCreate(player,mob, nearestMobCount == 0 || IsEnhance(player),nearestMobCount);
                    if (nearestMobCount == 0 && player.tickCount % 5 == 0) Damage(player,mob,2.5);
                    nearestMobCount ++;
                }
                else {
                    ParticleCreate(player,mob, IsEnhance(player),0);
                    if (player.tickCount % 5 == 0) Damage(player,mob,IsEnhance(player) ? 2.5 : 0.5);
                }
            }
        }

    }

    public static void ParticleCreate(Player player, Mob mob, boolean IsMain, int num) {
        Vec3 TargetPos = player.pick(1,0,false).getLocation();

        if (num != 0) {
            switch (num) {
                case 1 -> TargetPos = mob.position().add(1,2,0);
                case 2 -> TargetPos = mob.position().add(0,0,1);
                case 3 -> TargetPos = mob.position().add(-1,0,0);
                case 4 -> TargetPos = mob.position().add(0,2,-1);
            }
        }

        Vec3 MobPos = mob.position().add(0,1,0);
        Vec3 FacePos = player.pick(0.5,0,false).getLocation();
        TargetPos = FacePos.add(TargetPos.subtract(FacePos).scale(30));
        Vec3 vec3 = TargetPos.subtract(FacePos).normalize();
        double min = 50;
        double distance = TargetPos.distanceTo(FacePos);
        Vec3 nearPos = TargetPos;
        for (int i = 0 ; i < 30 ; i ++) {
            Vec3 vec31 = FacePos.add(vec3.normalize().scale(i * distance / 50.0));
            if (vec31.distanceTo(MobPos) <= min) {
                min = vec31.distanceTo(MobPos);
                nearPos = vec31;
            }
        }

        Vec3 Delta = MobPos.subtract(nearPos);

        Vec3 handVec3 = player.pick(0.5,0,false).getLocation().
                add(player.getHandHoldingItemAngle(ModItems.YuanShiRenSceptre.get())).add(0,- 0.3,0);
        if (IsMain) ParticleProvider.CurveParticle(player.level(),10,handVec3,MobPos,Delta, ModParticles.YSR1.get());
        else ParticleProvider.CurveParticle(player.level(),10,handVec3,MobPos,Delta, ModParticles.YSR.get());
    }

    public static void Animation(Player player, boolean isNull) {
        List<ServerPlayer> list = player.getServer().getPlayerList().getPlayers();
        list.forEach(serverPlayer -> {
            if (!isNull) ModNetworking.sendToClient(new YSRAnimationS2CPacket(player.getId()),serverPlayer);
            else ModNetworking.sendToClient(new NULLAnimationS2CPacket(player.getId()),serverPlayer);
        });
    }

    public static void Damage(Player player, Mob mob, double rate) {
        Level level = player.level();
        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW.get(), player, level,
                Compute.PlayerAttributes.PlayerManaDamage(player),
                Compute.PlayerAttributes.PlayerManaPenetration(player),
                Compute.PlayerAttributes.PlayerManaPenetration0(player),5);
        ManaAttackModule.BasicAttack(player,mob,Compute.PlayerAttributes.PlayerManaDamage(player) * rate,
                Compute.PlayerAttributes.PlayerManaPenetration(player),Compute.PlayerAttributes.PlayerManaPenetration0(player),
                player.level(),newArrow);
    }

}
