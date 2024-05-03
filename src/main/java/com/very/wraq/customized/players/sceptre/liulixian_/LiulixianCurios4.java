package com.very.wraq.customized.players.sceptre.liulixian_;

import com.very.wraq.Items.MobItem.MobArmor;
import com.very.wraq.entities.entities.Civil.Civil;
import com.very.wraq.process.damage.SputteringDamage;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.*;

public class LiulixianCurios4 extends Item implements ICurioItem {

    public static Player Player;
    public static boolean IsOn;

    public LiulixianCurios4(Properties p_41383_) {
        super(p_41383_);
        Utils.Defence.put(this,800d);
        Utils.ManaDefence.put(this,800d);
        Utils.DefencePenetration.put(this,0.2);
        Utils.ManaPenetration.put(this,0.2);
        Utils.CoolDownDecrease.put(this,0.25);
        Utils.CustomizedList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = CustomStyle.styleOfSakura;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components, Component.literal("夺心魄").withStyle(style));
        components.add(Component.literal(" 使周围9格半径球内的敌人增加").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("50%")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDefence("50%")).
                append(Component.literal("以及").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("50%造成伤害").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" 使装备者:").withStyle(style));
        components.add(Component.literal(" 壹:").withStyle(style).
                append(Component.literal("减少").withStyle(ChatFormatting.RED)).
                append(Compute.AttributeDescription.CritRate("50%")));
        components.add(Component.literal(" 贰:").withStyle(style).
                append(Component.literal("减少").withStyle(ChatFormatting.RED)).
                append(Compute.AttributeDescription.Defence("50%")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDefence("50%")));
        components.add(Component.literal(" 叁:").withStyle(style).
                append(Component.literal("无法被击退").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 肆:").withStyle(style).
                append(Component.literal("投射物命中目标时，有10%概率触发").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("多重连射效果").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("，在0.5s内射出5枚对应投射物").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 伍:").withStyle(style).
                append(Component.literal("你将获得被你最后一次击杀生物的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("54%")).
                append(Component.literal("，手持法杖时，转化为对应的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("216%")).
                append(Component.literal("，并窃取其").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Defence("27%")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDefence("27%")).
                append(Component.literal("，持续27s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 陆:").withStyle(style).
                append(Component.literal("对").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("等级").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("低于装备者的敌人造成的伤害提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("差值 * 2.5%").withStyle(ChatFormatting.LIGHT_PURPLE)));
        components.add(Component.literal(" 柒:").withStyle(style).
                append(Component.literal("闪避").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("后恢复").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxHealth("10%")).
                append(Component.literal("并发射一枚").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("法球").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" 捌:").withStyle(style).
                append(Component.literal("所有来源为你的投射物会").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("自动锁定").withStyle(CustomStyle.styleOfMoon)).
                append(Component.literal("自身半径30内的目标，优先锁定指针指向的目标").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 玖:").withStyle(style).
                append(Component.literal("造成的伤害会").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("溅射").withStyle(style)).
                append(Component.literal("9格内的敌人，至多3次").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("，每次溅射削减50%伤害").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("，同一目标无法被溅射2次。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 道法三千六百门 人人各执一苗根").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚『极道』，授予对维瑞阿契做出了杰出贡献的 - liulixian_").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Player = (Player) slotContext.entity();
        IsOn = true;
        Compute.AddCuriosToList(player,stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Player = null;
        IsOn = false;
        Compute.RemoveCuriosInList(player,stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static boolean isOn(Player player) {
        return Player != null && Player.equals(player) && IsOn;
    }

    public static Map<Integer, Integer> mobEffectMap = new HashMap<Integer, Integer>();

    public static void tick(Player player) {
        if (!isOn(player)) return;
        int tick = player.getServer().getTickCount();
        if (tick % 20 == 0) {
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 30, 30, 30));
            mobList.removeIf(mob -> mob.distanceTo(player) > 9);
            mobList.forEach(mob -> mobEffectMap.put(mob.getId(), tick + 40));
        }
        if (shootTick > 0) {
            if (shootTick % 2 == 0) {
                Item weapon = player.getMainHandItem().getItem();
                if (weapon instanceof WraqBow wraqBow) wraqBow.shoot((ServerPlayer) player);
                if (weapon instanceof WraqSceptre wraqSceptre) wraqSceptre.shoot(player);
            }
            -- shootTick;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < LiulixianCurios4.sputteringDamageList.size(); i++) {
                    LiulixianCurios4.sputteringDamageList.get(i).sputter();
                }
                LiulixianCurios4.sputteringDamageList.removeIf(sputteringDamage -> tick - sputteringDamage.getOriginTick() > 20);
            }
        }).start();
    }

    public static double mobAttributeEnhance(Mob mob) {
        int tick = mob.getServer().getTickCount();
        if (mobEffectMap.containsKey(mob.getId()) && mobEffectMap.get(mob.getId()) > tick) return 1.5;
        return 1;
    }

    public static double playerAttributeDown(Player player) {
        if (!isOn(player)) return 1;
        return 0.5;
    }

    public static int shootTick = 0;
    public static void onProjectileHitEntity(Player player) {
        if (!isOn(player)) return;
        Random random = new Random();
        if (random.nextDouble() < 0.1 && shootTick == 0) shootTick = 10;
    }

    public static double lastKillMobAttackDamage = 0;
    public static double lastKillMobDefence = 0;
    public static double lastKillMobManaDefence = 0;
    public static double killMobEffectLastTick = 0;

    public static void onKillMob(Player player, Mob mob) {
        if (!isOn(player)) return;
        Item helmet = mob.getItemBySlot(EquipmentSlot.HEAD).getItem();
        if (helmet instanceof MobArmor mobArmor) {
            lastKillMobAttackDamage = mobArmor.AttackDamage;
            lastKillMobDefence = mobArmor.Defence;
            lastKillMobManaDefence = mobArmor.ManaDefence;
            killMobEffectLastTick = player.getServer().getTickCount() + 540;
            Compute.EffectLastTimeSend(player, ModItems.LiulixianCurios4.get(), 540);
        }
    }

    public static void dodge(Player player) {
        if (!isOn(player)) return;
        Item weapon = player.getMainHandItem().getItem();
        if (weapon instanceof WraqBow wraqBow) wraqBow.shoot((ServerPlayer) player);
        if (weapon instanceof WraqSceptre wraqSceptre) wraqSceptre.shoot(player);
        Compute.PlayerHeal(player, player.getMaxHealth() * 0.1);
    }

    public static double attackDamageUp(Player player) {
        if (!isOn(player) || !Utils.SwordTag.containsKey(player.getMainHandItem().getItem())
                || !Utils.BowTag.containsKey(player.getMainHandItem().getItem())
                || killMobEffectLastTick < player.getServer().getTickCount()) return 0;
        return lastKillMobAttackDamage * 0.54;
    }

    public static double manaDamageUp(Player player) {
        if (!isOn(player) || !Utils.SceptreTag.containsKey(player.getMainHandItem().getItem())
                || killMobEffectLastTick < player.getServer().getTickCount()) return 0;
        return lastKillMobDefence * 2.16;
    }

    public static double defenceUp(Player player) {
        if (!isOn(player) || killMobEffectLastTick < player.getServer().getTickCount()) return 0;
        return lastKillMobDefence * 0.27;
    }

    public static double manaDefenceUp(Player player) {
        if (!isOn(player) || killMobEffectLastTick < player.getServer().getTickCount()) return 0;
        return lastKillMobManaDefence * 0.27;
    }

    public static double xpMinusDamageEnhance(Player player, Mob mob) {
        if (!isOn(player)) return 0;
        Item helmet = mob.getItemBySlot(EquipmentSlot.HEAD).getItem();
        if (helmet instanceof MobArmor mobArmor) {
            double minus = player.experienceLevel - mobArmor.MobLevel;
            if (minus > 0) return minus * 0.025;
        }
        return 0;
    }

    public static final List<SputteringDamage> sputteringDamageList = new ArrayList<>();

    public static void onMainAttackHit(Player player, Mob mob, double damage, int type) {
        if (!isOn(player)) return;
        sputteringDamageList.add(new SputteringDamage(mob, player, player.getServer().getTickCount(), damage, type));
    }

    public static void adjustProjectile(AbstractArrow arrow, Player player) {
        if (!isOn(player)) return;
        Mob mob = Compute.detectPlayerPickMob(player, 30, 1);
        if (mob == null) {
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(),80,80,80));
            mobList.removeIf(mob1 -> mob1.distanceTo(player) > 30 || mob1 instanceof Civil);

            double Distance = 80;
            for (Mob mob1 : mobList) {
                if (mob1.distanceTo(player) < Distance) {
                    mob = mob1;
                    Distance = mob1.distanceTo(player);
                }
            }
        }

        if (mob != null) {
            arrow.setDeltaMovement(mob.position().add(0,1,0).subtract(player.position().add(0,1.5,0)).normalize().scale(4.5));
            arrow.moveTo(player.pick(0.5,0,false).getLocation());
            arrow.setCritArrow(true);
            arrow.setNoGravity(true);
            ProjectileUtil.rotateTowardsMovement(arrow,1);

            ParticleProvider.LineParticle(player.level(), (int) mob.distanceTo(player),
                    player.pick(0.5,0,false).getLocation(),mob.position().add(0,1,0), ParticleTypes.SNOWFLAKE);
        }
    }
}
