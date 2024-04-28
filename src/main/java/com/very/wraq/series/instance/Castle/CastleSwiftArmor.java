package com.very.wraq.series.instance.Castle;

import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.process.Particle.ParticleProvider;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CastleSwiftArmor extends ArmorItem {
    private static final Style style = CustomStyle.styleOfCastle;
    private String type = "";

    public static Map<Integer,Double> attributeIndexValueMap = new HashMap<>(){{
        put(0,5120d);
        put(1,450d);
        put(2,400d);
        put(3,400d);
        put(4,4d);
    }};

    public CastleSwiftArmor(ItemMaterial Material, Type Slots, Properties itemProperties, int type)
    {
        super(Material,Slots,itemProperties);
        Utils.MaxHealth.put(this,5120d);
        Utils.AttackDamage.put(this,450d);
        Utils.Defence.put(this,400d);
        Utils.ManaDefence.put(this,400d);
        Utils.SwiftnessUp.put(this,4d);
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
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal(type).withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("暗影打击").withStyle(style));
        components.add(Component.literal(" 进行").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("后，0.2s后你会进行一次").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("暗影打击").withStyle(style)));
        components.add(Component.literal(" 暗影打击").withStyle(style).
                append(Component.literal("被视为拥有15%基础伤害值的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("普通箭矢攻击").withStyle(CustomStyle.styleOfFlexible)));
        Compute.DescriptionPassive(components,Component.literal("灵魂痛击").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("附带").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("2倍").withStyle(CustomStyle.styleOfSea)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
        components.add(Component.literal(" -多件暗影城堡防具能线性提升伤害值百分比/伤害值").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        ForgeAttributeDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfCastle(components);
        super.appendHoverText(stack,level,components,flag);
    }

    public static Map<Player,Integer> playerDoubleAttackTick = new HashMap<>();

    public static void Tick(Player player) {
        int ArmorCount = Compute.ArmorCount.CastleSwift(player);
        if (ArmorCount == 0) return;
        int TickCount = player.getServer().getTickCount();
        if (playerDoubleAttackTick.containsKey(player) && playerDoubleAttackTick.get(player) == TickCount) {
            ExAttack(player,ArmorCount * 0.15);
        }
    }

    public static void NormalAttack(Player player) {
        int ArmorCount = Compute.ArmorCount.CastleSwift(player);
        if (ArmorCount == 0) return;
        int TickCount = player.getServer().getTickCount();
        playerDoubleAttackTick.put(player,TickCount + 4);
    }

    public static void ExAttack(Player player, double rate) {
        MyArrow myArrow = new MyArrow(EntityType.ARROW,player.level(),player,true,rate);
        myArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3F, 1.0f);
        myArrow.setCritArrow(true);
        player.level().addFreshEntity(myArrow);
        Compute.SoundToAll(player, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.WAX_OFF);
    }

    public static double ExIgnoreDefenceDamage(Player player) {
        int ArmorCount = Compute.ArmorCount.CastleSwift(player);
        if (ArmorCount == 0) return 0;
        return Compute.XpStrengthADDamage(player,2) * ArmorCount;
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
        Compute.DescriptionPassive(components,Component.literal("蕴魔注能：").withStyle(CustomStyle.styleOfCastleCrystal));
        if (!data.contains(attributeType)) {
            components.add(Component.literal(" 暂未注能..").withStyle(CustomStyle.styleOfCastleCrystal));
            return;
        }
        int type = data.getInt(attributeType);
        Component[] components1 = {
                Compute.AttributeDescription.MaxHealth(""),
                Compute.AttributeDescription.AttackDamage(""),
                Compute.AttributeDescription.Defence(""),
                Compute.AttributeDescription.ManaDefence(""),
                Compute.AttributeDescription.Swiftness("")
        };
        components.add(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                append(components1[type]).
                append(Component.literal(" + ").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("" + String.format("%.2f%%", data.getDouble(attributeRate) * 100))));

        components.add(Component.literal(" 目前已注能的次数为:").withStyle(ChatFormatting.WHITE).
                append(Component.literal("" + data.getInt(attributeTimes)).withStyle(CustomStyle.styleOfCastleCrystal))); // forge times

    }

    public static String ExMaxHealth = "ExManHealth";
    public static String ExAttackDamage = "ExAttackDamage";
    public static String ExDefence = "ExDefence";
    public static String ExManaDefence = "ExManaDefence";
    public static String ExSwiftnessUp = "ExSwiftnessUp";

    public static Map<String,Integer> attributeNumMap = new HashMap<>(){{
        put(ExMaxHealth,0);
        put(ExAttackDamage,1);
        put(ExDefence,2);
        put(ExManaDefence,3);
        put(ExSwiftnessUp,4);
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
            if (itemStack.getItem() instanceof CastleSwiftArmor) {
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
