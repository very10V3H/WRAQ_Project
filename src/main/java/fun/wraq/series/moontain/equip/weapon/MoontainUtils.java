package fun.wraq.series.moontain.equip.weapon;

import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.multiblockactive.rightclick.drive.EnhanceCondition;
import fun.wraq.process.func.multiblockactive.rightclick.drive.EnhanceOperation;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.series.instance.series.castle.RandomCuriosAttributesUtil;
import fun.wraq.series.moontain.MoontainItems;
import fun.wraq.series.moontain.equip.curios.MoontainCurios;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;

import java.util.List;

public class MoontainUtils {
    public static String MOONTAIN_ATTACK_TAG_KEY = "MOONTAIN_ATTACK_TAG_KEY";
    public static String MOONTAIN_MANA_ATTACK_TAG_KEY = "MOONTAIN_MANA_ATTACK_TAG_KEY";

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
            return ForgeEquipUtils.getForgeQualityOnEquip(stack) < 11;
        }
        return false;
    });

    public static EnhanceOperation weaponEnhanceOperation = (stack -> {
        ForgeEquipUtils.setForgeQualityOnEquip(stack, ForgeEquipUtils.getForgeQualityOnEquip(stack) + 1);
    });

    public static EnhanceCondition armorsEnhanceCondition = (stack -> {
        if (getMoontainArmors().contains(stack.getItem())) {
            return ForgeEquipUtils.getForgeQualityOnEquip(stack) < 11;
        }
        return false;
    });

    public static EnhanceOperation armorsEnhanceOperation = (stack -> {
        ForgeEquipUtils.setForgeQualityOnEquip(stack, ForgeEquipUtils.getForgeQualityOnEquip(stack) + 1);
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
}
