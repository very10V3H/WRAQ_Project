package com.very.wraq.customized.players.bow.Qi_Fu;

import com.very.wraq.customized.Customize;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.Ice.IceSceptreAttributes;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class QiFuBow extends WraqBow {
    public static int ManaDamageEnhanceTick = 0;
    public static int ParticleTick = -1;
    public static int ParticleLastTick = 0;
    private final int Num;

    public QiFuBow( Properties p_42964_, int Num) {
        super(p_42964_);
        this.Num = Num;
        Utils.AttackDamage.put(this,Customize.AttackDamage);
        Utils.DefencePenetration0.put(this,Customize.DefencePenetration0);
        Utils.CritRate.put(this,Customize.BowCritRate);
        Utils.CritDamage.put(this,Customize.BowCritDamage);
        Utils.MovementSpeed.put(this,Customize.BowMovementSpeed);
        Utils.MainHandTag.put(this,1d);
        Utils.CustomizedList.add(this);
        Utils.BowTag.put(this,1.0d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfMoon1;
        Compute.ForgingHoverName(stack,Component.literal("爆裂魔杖" + IceSceptreAttributes.Number[Num]).withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长弓").withStyle(CustomStyle.styleOfFlexible)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components,Component.literal("潮起潮落月为因").withStyle(style));
        components.add(Component.literal(" 展开法阵，祈愿皓月，召唤潮汐，消耗自身").withStyle(ChatFormatting.WHITE).
                append(Component.literal("[潮汐之力]").withStyle(style)).
                append(Component.literal("层数的50%，释放月华").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("  - 每消耗五层额外发射一支箭矢").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        Compute.DescriptionPassive(components,Component.literal("不灭月华").withStyle(style));
        components.add(Component.literal(" - 其一 吸收月光:").withStyle(style).
                append(Component.literal("对自身进行强化，获得一个月光护盾").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" - 其二 释放月华:").withStyle(style).
                append(Component.literal("释放").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("月华").withStyle(style)).
                append(Component.literal("对周围所有敌人造成").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamageValue("500%")));
        components.add(Component.literal(" 星稀河影转，霜重月华孤").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一把星辉月皎，授予对维瑞阿契做出了杰出贡献的 - 147895").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }


    public int getNum() {
        return this.Num;
    }

    public static void Active(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (TickCount > ManaDamageEnhanceTick) {
            ParticleTick = 20;
            ManaDamageEnhanceTick = TickCount + 200;
            QiFuCurios.ExManaBallTickCount = QiFuCurios.Count / 10;
            QiFuCurios.Count /= 2;
            Compute.CoolDownTimeSend(player, ModItems.QiFuBow.get().getDefaultInstance(),200);
        }
    }

    public static void RangeDamage(Player player, double rate) {
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(),25,25,25));
        List<ServerPlayer> playerList = player.getServer().getPlayerList().getPlayers();
        mobList.removeIf(mob -> mob.distanceTo(player) > 10);
        mobList.forEach(mob -> {
            ServerLevel serverLevel = (ServerLevel) player.level();
            ParticleProvider.VerticleCircleParticle(mob.position(), serverLevel, 1, 2, 40, ParticleTypes.FIREWORK);
            ParticleProvider.VerticleCircleParticle(mob.position(), serverLevel, 1.5, 2, 40, ParticleTypes.FIREWORK);

            List<Mob> mobList1 = serverLevel.getEntitiesOfClass(Mob.class,
                    AABB.ofSize(mob.position(), 10, 10, 10));
            mobList1.forEach(mob1 -> {
                if (mob1.position().subtract(mob.position()).length() <= 2) {
                    Compute.Damage.AttackDamageToMonster_RateAdDamage(player,mob1,rate);
                    Compute.AddSlowDownEffect(mob, 40, 3);
                    ClientboundLevelParticlesPacket clientboundLevelParticlesPacket =
                            new ClientboundLevelParticlesPacket(ParticleTypes.EXPLOSION_EMITTER, true,
                                    mob1.getX(), mob1.getY(), mob1.getZ(),
                                    0, 0, 0, 0, 0);
                    playerList.forEach(serverPlayer -> serverPlayer.connection.send(clientboundLevelParticlesPacket));
                }
            });
        });

        ParticleProvider.DisperseParticle(player.position(), (ServerLevel) player.level(), 1, 1, 120, ParticleTypes.FIREWORK, 2);
        ParticleProvider.DisperseParticle(player.position(), (ServerLevel) player.level(), 1.5, 1, 120, ParticleTypes.FIREWORK, 2);
    }

    public static void FromMobRangeDamage(Player player, Mob mob, double rate, double range) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.QiFuBow.get())) {
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(),25,25,25));
            mobList.forEach(mob1 -> {
                if (mob1.distanceTo(mob) < range) {
                    Compute.Damage.AttackDamageToMonster_RateAdDamage(player,mob1,rate);
                }
            });
            ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) player.level(), 1, 1, 120, ParticleTypes.FIREWORK, 1);
            Compute.PlayerShieldProvider(player,20, PlayerAttributes.PlayerAttackDamage(player) * 0.1);
        }
    }

    public static void Tick(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.QiFuBow.get())) {
            if (ParticleTick > 0) {
                ParticleTick --;
                ParticleCreate(player,ParticleTick);
            }
            if (ParticleTick == 0) {
                RangeDamage(player, (double) QiFuCurios.Count / 2);
                QiFuCurios.CountSub(player,QiFuCurios.Count / 2);
                ParticleLastTick = 20;
                ParticleTick --;
            }
            if (ParticleLastTick > 0) {
                ParticleLastTick --;
                ParticleCreate(player,0);
            }
        }
    }

    public static void ParticleCreate(Player player, double rate) {
        ParticleProvider.VerticleCircleParticle((ServerPlayer) player,0,1.3,80, ModParticles.WHITE_SPELL.get());
        Level level = player.level();
        double r = 1.5 * (20 - rate) / 20;
        Vec3 Dot1 = new Vec3(0,0,r).add(player.position());
        Vec3 Dot2 = new Vec3(Math.sqrt(3) * r / 2, 0, - 0.5 * r).add(player.position());
        Vec3 Dot3 = new Vec3(- Math.sqrt(3) * r / 2, 0 , - 0.5 * r).add(player.position());
        ParticleProvider.LineParticle(level,20,Dot1,Dot2,ModParticles.WHITE_SPELL.get());
        ParticleProvider.LineParticle(level,20,Dot2,Dot3,ModParticles.WHITE_SPELL.get());
        ParticleProvider.LineParticle(level,20,Dot3,Dot1,ModParticles.WHITE_SPELL.get());

        Vec3 Dot4 = new Vec3(0,0 ,- r).add(player.position());
        Vec3 Dot5 = new Vec3(- Math.sqrt(3) * r / 2, 0, 0.5 * r).add(player.position());
        Vec3 Dot6 = new Vec3(Math.sqrt(3) * r / 2, 0, 0.5 * r).add(player.position());
        ParticleProvider.LineParticle(level,20,Dot4,Dot5,ModParticles.WHITE_SPELL.get());
        ParticleProvider.LineParticle(level,20,Dot5,Dot6,ModParticles.WHITE_SPELL.get());
        ParticleProvider.LineParticle(level,20,Dot6,Dot4,ModParticles.WHITE_SPELL.get());
    }

    @Override
    public void shoot(ServerPlayer serverPlayer) {
        QiFuCurios.Shoot(serverPlayer,true,false);
        QiFuCurios1.Passive(serverPlayer);
    }
}
