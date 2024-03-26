package com.Very.very.Series.InstanceSeries.Castle;


import com.Very.very.Process.Particle.ParticleProvider;
import com.Very.very.Projectile.Mana.NewArrow;
import com.Very.very.ValueAndTools.BasicAttributeDescription;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemMaterial;
import com.Very.very.ValueAndTools.registry.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CastleManaArmor extends ArmorItem {
    private static final Style style = Utils.styleOfCastle;
    private String type = "";

    public static Map<Integer,Double> attributeIndexValueMap = new HashMap<>(){{
        put(0,5120d);
        put(1,2048d);
        put(2,400d);
        put(3,400d);
        put(4,0.5);
    }};

    public CastleManaArmor(ItemMaterial Material, Type Slots, Properties itemProperties, int type)
    {
        super(Material,Slots,itemProperties);
        Utils.MaxHealth.put(this,5120d);
        Utils.ManaDamage.put(this,2048d);
        Utils.Defence.put(this,400d);
        Utils.ManaDefence.put(this,400d);
        Utils.CoolDownDecrease.put(this,0.5);
        Utils.ArmorTag.put(this,1d);
        Utils.ArmorList.add(this);
        switch (type) {
            case 0 -> this.type = "头盔";
            case 1 -> this.type = "胸甲";
            case 2 -> this.type = "护腿";
            case 3 -> this.type = "靴子";
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal(type).withStyle(ChatFormatting.RESET).withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("暗影打击").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 1.当你拥有").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术专精-力凝魔核:").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana)));
        components.add(Component.literal(" 进行").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana)).
                append(Component.literal("后，0.2s后你会进行一次").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("暗影打击").withStyle(ChatFormatting.RESET).withStyle(style)));
        components.add(Component.literal(" 暗影打击").withStyle(ChatFormatting.RESET).withStyle(style).
                append(Component.literal("被视为拥有15%基础伤害值的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("普通法球攻击").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana)));
        components.add(Component.literal(" 2.当你拥有").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术专精-术法全析:").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana)));
        components.add(Component.literal("在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("施法").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana)).
                append(Component.literal("后，0.2s后你会进行一次").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("暗影打击").withStyle(ChatFormatting.RESET).withStyle(style)));
        components.add(Component.literal(" -暗影打击会在指针范围内造成").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("650%")));
        Compute.DescriptionPassive(components,Component.literal("灵魂痛击").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana)).
                append(Component.literal("附带").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("2倍").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("真实伤害").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea)));
        components.add(Component.literal(" -多件暗影城堡防具能线性提升伤害值百分比/伤害值").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        ForgeAttributeDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfCastle(components);
        super.appendHoverText(stack,level,components,flag);


    }

    public static Map<Player,Integer> playerDoubleAttackTick = new HashMap<>();

    public static void Tick(Player player) {
        int ArmorCount = Compute.ArmorCount.CastleMana(player);
        if (ArmorCount == 0) return;
        int TickCount = player.getServer().getTickCount();
        if (playerDoubleAttackTick.containsKey(player) && playerDoubleAttackTick.get(player) == TickCount) {
            if (Compute.ManaSkillLevelGet(player.getPersistentData(), 10) == 10) ExAttack(player,ArmorCount * 0.15);
            else ExPower(player,ArmorCount * 6.5);
        }
    }

    public static void NormalAttack(Player player) {
        int ArmorCount = Compute.ArmorCount.CastleMana(player);
        if (ArmorCount == 0) return;
        int TickCount = player.getServer().getTickCount();
        if (playerDoubleAttackTick.containsKey(player) && playerDoubleAttackTick.get(player) > TickCount) {
            if (Compute.ManaSkillLevelGet(player.getPersistentData(), 10) == 10) ExAttack(player,ArmorCount * 0.15);
            else ExPower(player,ArmorCount * 6.5);
            playerDoubleAttackTick.put(player,0);
        }
        else playerDoubleAttackTick.put(player,TickCount + 4);
    }

    public static void ExAttack(Player player, double rate) {
        NewArrow newArrow = new NewArrow(player, player.level(), Compute.PlayerAttributes.PlayerManaDamage(player) * rate,
                Compute.PlayerAttributes.PlayerManaPenetration(player), Compute.PlayerExpUp(player),
                false, Compute.PlayerAttributes.PlayerManaPenetration0(player));
        newArrow.setSilent(true);
        newArrow.setNoGravity(true);

        newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
        ProjectileUtil.rotateTowardsMovement(newArrow, 0);
        player.level().addFreshEntity(newArrow);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.WITCH);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.WITCH);
        Compute.SoundToAll(player, ModSounds.Mana.get());

    }

    public static void ExPower(Player player, double rate) {
        Level level = player.level();
        Vec3 TargetPos = player.pick(15,0,false).getLocation();
        if (Compute.powerDetectPlayerPickMob(player) != null) TargetPos = Compute.powerDetectPlayerPickMob(player).position();
        List<Mob> monsterList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(TargetPos, 20, 20, 20));
        for (Mob mob : monsterList) {
            if (mob.getPosition(0).distanceTo(TargetPos) < 6) {
                Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, rate,true);

                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);
            }
        }
        ParticleProvider.VerticleCircleParticle(TargetPos,(ServerLevel) level,1,6,100,ParticleTypes.WITCH);
        ParticleProvider.VerticleCircleParticle(TargetPos,(ServerLevel) level,1.5,6,100,ParticleTypes.WITCH);
    }

    public static double ExIgnoreDefenceDamage(Player player) {
        int ArmorCount = Compute.ArmorCount.CastleMana(player);
        if (ArmorCount == 0) return 0;
        return Compute.XpStrengthAPDamage(player,0.5) * ArmorCount;
    }

    public static String attributeType = "attributeType"; // 锁定类型
    public static String attributeRate = "attributeRate"; // 锁定倍率
    public static String attributeTimes = "attributeTimes";

    public static void ForgeArmor(ItemStack itemStack, boolean lockType, boolean lockRate) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        Random random = new Random();
        data.putInt(attributeTimes,data.getInt(attributeTimes) + 1);
        if (!lockType) data.putInt(attributeType,random.nextInt(5));
        if (!lockRate) {

            if (data.getInt(attributeTimes) == 5) {
                data.putDouble(attributeRate,random.nextDouble(0.25,0.75));
                if (random.nextDouble() < 0.2) {
                    data.putDouble(attributeRate,random.nextDouble(0.65,1));
                } // 高品质
            } else if (data.getInt(attributeTimes) == 10) {
                data.putDouble(attributeRate,random.nextDouble(0.25,0.75));
                if (random.nextDouble() < 0.4) {
                    data.putDouble(attributeRate,random.nextDouble(0.65,1));
                }
            } else {
                data.putDouble(attributeRate,random.nextDouble(0.25,0.75));
                if (random.nextDouble() < 0.05) {
                    data.putDouble(attributeRate,random.nextDouble(0.65,1));
                }
            }

        }
        if (data.getInt(attributeTimes) == 10) data.putInt(attributeTimes,0);
    }

    public static void ForgeAttributeDescription(List<Component> components, ItemStack itemStack) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionPassive(components,Component.literal("蕴魔注能：").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfCastleCrystal));
        if (!data.contains(attributeType)) {
            components.add(Component.literal(" 暂未注能..").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfCastleCrystal));
            return;
        }
        int type = data.getInt(attributeType);
        Component[] components1 = {
                Compute.AttributeDescription.MaxHealth(""),
                Compute.AttributeDescription.ManaDamage(""),
                Compute.AttributeDescription.Defence(""),
                Compute.AttributeDescription.ManaDefence(""),
                Compute.AttributeDescription.CoolDown("")
        };
        components.add(Component.literal(" ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(components1[type]).
                append(Component.literal(" + ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("" + String.format("%.2f%%", data.getDouble(attributeRate) * 100))));

        components.add(Component.literal(" 目前已注能的次数为:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("" + data.getInt(attributeTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfCastleCrystal))); // forge times

    }

    public static String ExMaxHealth = "ExManHealth";
    public static String ExManaDamage = "ExManaDamage";
    public static String ExDefence = "ExDefence";
    public static String ExManaDefence = "ExManaDefence";
    public static String ExCoolDownDecrease = "ExCoolDownDecrease";

    public static Map<String,Integer> attributeNumMap = new HashMap<>(){{
        put(ExMaxHealth,0);
        put(ExManaDamage,1);
        put(ExDefence,2);
        put(ExManaDefence,3);
        put(ExCoolDownDecrease,4);
    }};

    public static double ExAttributeValue(Player player, String attributeName) {
        double value = 0;
        ItemStack[] itemStacks = {
                player.getItemBySlot(EquipmentSlot.HEAD),
                player.getItemBySlot(EquipmentSlot.CHEST),
                player.getItemBySlot(EquipmentSlot.LEGS),
                player.getItemBySlot(EquipmentSlot.FEET)
        };
        for (ItemStack itemStack : itemStacks) {
            if (itemStack.getItem() instanceof CastleManaArmor) {
                CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
                if (!data.contains(attributeType)) return 0;

                int type = data.getInt(attributeType);
                if (attributeNumMap.get(attributeName) != type) return 0;

                return attributeIndexValueMap.get(type) * data.getDouble(attributeRate);
            }
        }
        return 0;
    }
}
