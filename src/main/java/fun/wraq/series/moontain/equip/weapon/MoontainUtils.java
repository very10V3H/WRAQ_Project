package fun.wraq.series.moontain.equip.weapon;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.impl.ExBaseAttributeValueEquip;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.moontain.MoontainEntities;
import fun.wraq.process.func.multiblockactive.rightclick.drive.EnhanceCondition;
import fun.wraq.process.func.multiblockactive.rightclick.drive.EnhanceOperation;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.castle.RandomCuriosAttributesUtil;
import fun.wraq.series.moontain.MoontainItems;
import fun.wraq.series.moontain.equip.curios.MoontainCurios;
import net.mcreator.borninchaosv.init.BornInChaosV1ModMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

public class MoontainUtils {
    public static String MOONTAIN_ATTACK_TAG_KEY = "MOONTAIN_ATTACK_TAG_KEY";
    public static String MOONTAIN_MANA_ATTACK_TAG_KEY = "MOONTAIN_MANA_ATTACK_TAG_KEY";
    public static String MOONTAIN_HEALTH_RECOVER_TAG_KEY = "MOONTAIN_HEALTH_RECOVER_TAG_KEY";
    public static String MOONTAIN_MAX_HEALTH_TAG_KEY = "MOONTAIN_MAX_HEALTH_TAG_KEY";
    public static String MOONTAIN_DEFENCE_TAG_KEY = "MOONTAIN_DEFENCE_TAG_KEY";
    public static String MOONTAIN_MOVEMENT_SPEED_TAG_KEY = "MOONTAIN_MOVEMENT_SPEED_TAG_KEY";

    public static void formatBroad(Level level, Component content) {
        Compute.formatBroad(level, Te.s("望山阁", CustomStyle.styleOfMoontain), content);
    }

    public static Map<Item, Double> getTraditionalAttributeMap(ItemStack stack) {
        return Map.of(
                MoontainItems.SWORD.get(), Utils.attackDamage,
                MoontainItems.BOW.get(), Utils.attackDamage,
                MoontainItems.SCEPTRE.get(), Utils.manaDamage,
                MoontainItems.HELMET.get(), Utils.healthRecover,
                MoontainItems.CHEST.get(), Utils.defence,
                MoontainItems.LEGGINGS.get(), Utils.maxHealth,
                MoontainItems.BOOTS.get(), Utils.movementSpeedCommon
        ).get(stack.getItem());
    }

    public static List<Item> getMoontainWeapons() {
        return List.of(
                MoontainItems.SWORD.get(),
                MoontainItems.BOW.get(),
                MoontainItems.SCEPTRE.get()
        );
    }

    public static List<Item> getMoontainArmors() {
        return List.of(
                MoontainItems.HELMET.get(),
                MoontainItems.CHEST.get(),
                MoontainItems.LEGGINGS.get(),
                MoontainItems.BOOTS.get()
        );
    }

    public static EnhanceCondition weaponEnhanceCondition = (stack -> {
        if (getMoontainWeapons().contains(stack.getItem())) {
            return ExBaseAttributeValueEquip.getForgeTier(stack, MoontainUtils.getTraditionalAttributeMap(stack)) < 20;
        }
        return false;
    });

    public static EnhanceOperation weaponEnhanceOperation = (stack -> {
        int qualityTier = ExBaseAttributeValueEquip.getForgeTier(stack, MoontainUtils.getTraditionalAttributeMap(stack));
        SecureRandom secureRandom = new SecureRandom();
        if (secureRandom.nextDouble() < (1 - qualityTier / 20d)) {
            ExBaseAttributeValueEquip.forgeThisTypeEquip(stack, getTraditionalAttributeMap(stack), 1);
        }
    });

    public static EnhanceCondition armorsEnhanceCondition = (stack -> {
        if (getMoontainArmors().contains(stack.getItem())) {
            return ExBaseAttributeValueEquip.getForgeTier(stack, MoontainUtils.getTraditionalAttributeMap(stack)) < 20;
        }
        return false;
    });

    public static EnhanceOperation armorsEnhanceOperation = (stack -> {
        int qualityTier = ExBaseAttributeValueEquip.getForgeTier(stack, MoontainUtils.getTraditionalAttributeMap(stack));
        SecureRandom secureRandom = new SecureRandom();
        if (secureRandom.nextDouble() < (1 - qualityTier / 20d)) {
            ExBaseAttributeValueEquip.forgeThisTypeEquip(stack, getTraditionalAttributeMap(stack), 1);
        }
    });

    public static List<Item> getMoontainCurios() {
        return List.of(
                MoontainItems.CHEST_CURIOS.get(),
                MoontainItems.BRACELET.get(),
                MoontainItems.HAND.get(),
                MoontainItems.RING.get()
        );
    }

    public static EnhanceCondition curiosEnhanceRateCondition = (stack -> {
        if (getMoontainCurios().contains(stack.getItem())) {
            CompoundTag tag = stack.getOrCreateTagElement(Utils.MOD_ID);
            return RandomCuriosAttributesUtil.attributeValueMap.keySet().stream().anyMatch(key -> {
                if (stack.getOrCreateTagElement(Utils.MOD_ID).contains(key)) {
                    double rate = tag.getDouble(key);
                    double fullRate = RandomCurios.getFullRateByTag(stack);
                    return rate < fullRate;
                }
                return false;
            });
        }
        return false;
    });

    public static EnhanceOperation curiosEnhanceRateOperation = (stack -> {
        MoontainCurios.enhanceAttributesRate(stack, 0.1);
    });

    public static EnhanceCondition curiosEnhanceFullRateCondition = (stack -> {
        return RandomCurios.getFullRateByTag(stack) < 5;
    });

    public static EnhanceOperation curiosEnhanceFullRateOperation = (stack -> {
        MoontainCurios.enhanceAttributesFullRate(stack, 0.1);
    });

    public static final List<Vec3> jumpPos = List.of(
            new Vec3(1992, 159, -890),
            new Vec3(1974, 167, -872),
            new Vec3(1992, 175, -890),
            new Vec3(1974, 199, -872),
            new Vec3(1992, 223, -890)
    );

    public static void tick(Player player) {
        if (player.tickCount % 20 == 0) {
            if (player.hasEffect(BornInChaosV1ModMobEffects.INFERNAL_FLAME.get())) {
                Compute.decreasePlayerHealth(player, player.getMaxHealth() * 0.08,
                        Te.s("被", "望山黯魂", CustomStyle.styleOfMoontain, "吞噬了", ChatFormatting.RED));
            }
        }
        if (player.tickCount % 10 == 0
                && Compute.isEntityInTwoPoint(player, MoontainEntities.commonDownPos, MoontainEntities.commonUpPos)) {
            if (jumpPos.stream().anyMatch(pos -> player.position().distanceTo(pos) < 1.5)) {
                Compute.sendMotionPacketToPlayer(player, new Vec3(0, 1.35, 0));
            }
        }
    }
}
