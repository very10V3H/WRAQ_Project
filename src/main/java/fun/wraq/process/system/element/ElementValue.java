package fun.wraq.process.system.element;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.uniform.element.*;
import fun.wraq.events.mob.instance.item.RevenantGoldenHelmet;
import fun.wraq.process.system.element.equipAndCurios.fireElement.FireElementSword;
import fun.wraq.process.system.element.equipAndCurios.waterElement.WaterElementSword;
import fun.wraq.process.system.season.MySeason;
import fun.wraq.series.overworld.divine.equip.weapon.DivineWeaponCommon;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ElementValue {

    public static String LifeElementValue = "LifeElementValue";
    public static String WaterElementValue = "WaterElementValue";
    public static String FireElementValue = "FireElementValue";
    public static String StoneElementValue = "StoneElementValue";
    public static String IceElementValue = "IceElementValue";
    public static String LightningElementValue = "LightningElementValue";
    public static String WindElementValue = "WindElementValue";

    public static double getElementValueJudgeByType(Player player, @Nullable String type) {
        if (type == null) return 0;
        if (type.equals(Element.life)) return getPlayerLifeElementValue(player);
        if (type.equals(Element.water)) return getPlayerWaterElementValue(player);
        if (type.equals(Element.fire)) return getPlayerFireElementValue(player);
        if (type.equals(Element.stone)) return getPlayerStoneElementValue(player);
        if (type.equals(Element.ice)) return getPlayerIceElementValue(player);
        if (type.equals(Element.lightning)) return getPlayerLightningElementValue(player);
        if (type.equals(Element.wind)) return getPlayerWindElementValue(player);
        return 0;
    }

    public static double getCommonRate(Player player, Map<Item, Double> elementValueMap, String attributeName, String type) {
        double value = 0;
        value += PlayerAttributes.computeAllEquipSlotBaseAttributeValue(player, elementValueMap, false);
        value += QiLingJudge(player, elementValueMap);
        value += Compute.CuriosAttribute.attributeValue(player, elementValueMap, attributeName);
        value += mainHandEquipValue(player.getMainHandItem().getItem(), elementValueMap);
        value += InCuriosOrEquipSlotAttributesModify.getAttributes(player, elementValueMap);
        value += DivineWeaponCommon.getEnhanceElementValue(player, type);
        value *= (1 + PlayerAttributes.getElementStrength(player));
        return value;
    }

    public static double QiLingJudge(Player player, Map<Item, Double> map) {
        double value = 0;
        Inventory inventory = player.getInventory();
        for (int i = 3; i < 9; i++) {
            ItemStack itemStack = inventory.getItem(i);
            Item item = itemStack.getItem();
            if (map.containsKey(item) && Utils.passiveEquipTag.containsKey(item))
                value += map.get(item);
        }
        return value;
    }

    public static double mainHandEquipValue(Item item, Map<Item, Double> map) {
        if (Utils.mainHandTag.containsKey(item) && map.containsKey(item)) return map.get(item);
        return 0;
    }

    public static double getPlayerLifeElementValue(Player player) {
        double value = 0;
        value += getCommonRate(player, Element.LifeElementValue, LifeElementValue, Element.life);
        // 百分比分割线
        value *= LifeCurios0.playerLifeElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(Element.life));
        return value;
    }

    public static double getPlayerWaterElementValue(Player player) {
        double value = 0;
        value += getCommonRate(player, Element.WaterElementValue, WaterElementValue, Element.water);
        // 百分比分割线
        value *= WaterElementSword.PlayerWaterElementValueEnhance(player);
        value *= WaterCurios0.playerWaterElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(Element.water));
        return value;
    }

    public static double getPlayerFireElementValue(Player player) {
        double value = 0;
        value += getCommonRate(player, Element.FireElementValue, FireElementValue, Element.fire);
        value += FireElementSword.FireElementValueEnhance(player);
        // 百分比分割线
        double enhanceRate = 0;
        enhanceRate += RevenantGoldenHelmet.getFireElementValueEnhanceRate(player);
        enhanceRate += FireCurios0.getFireElementValueEnhance(player);
        value *= (1 + enhanceRate);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(Element.fire));
        return value;
    }

    public static double getPlayerStoneElementValue(Player player) {
        double value = 0;
        value += getCommonRate(player, Element.StoneElementValue, StoneElementValue, Element.stone);
        // 百分比分割线
        value *= StoneCurios0.playerStoneElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(Element.stone));

        return value;
    }

    public static double getPlayerIceElementValue(Player player) {
        double value = 0;
        value += getCommonRate(player, Element.IceElementValue, IceElementValue, Element.ice);
        // 百分比分割线
        value *= IceCurios0.playerIceElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(Element.ice));

        return value;
    }

    public static double getPlayerLightningElementValue(Player player) {
        double value = 0;
        value += getCommonRate(player, Element.LightningElementValue, LightningElementValue, Element.lightning);
        // 百分比分割线
        value *= LightningCurios0.playerLightningElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(Element.lightning));

        return value;
    }

    public static double getPlayerWindElementValue(Player player) {
        double value = 0;
        value += getCommonRate(player, Element.WindElementValue, WindElementValue, Element.wind);
        // 百分比分割线
        value *= WindCurios0.playerWindElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(Element.wind));
        return value;
    }
}
