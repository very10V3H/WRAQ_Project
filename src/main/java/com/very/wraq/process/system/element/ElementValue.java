package com.very.wraq.process.system.element;

import com.very.wraq.customized.uniform.element.*;
import com.very.wraq.process.system.element.equipAndCurios.fireElement.FireElementSword;
import com.very.wraq.process.system.element.equipAndCurios.waterElement.WaterElementSword;
import com.very.wraq.process.system.season.MySeason;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class ElementValue {

    public static String LifeElementValue = "LifeElementValue";
    public static String WaterElementValue = "WaterElementValue";
    public static String FireElementValue = "FireElementValue";
    public static String StoneElementValue = "StoneElementValue";
    public static String IceElementValue = "IceElementValue";
    public static String LightningElementValue = "LightningElementValue";
    public static String WindElementValue = "WindElementValue";

    public static double ElementValueJudgeByType(Player player, String type) {
        if (type.equals(Element.life)) return PlayerLifeElementValue(player);
        if (type.equals(Element.water)) return PlayerWaterElementValue(player);
        if (type.equals(Element.fire)) return PlayerFireElementValue(player);
        if (type.equals(Element.stone)) return PlayerStoneElementValue(player);
        if (type.equals(Element.ice)) return PlayerIceElementValue(player);
        if (type.equals(Element.lightning)) return PlayerLightningElementValue(player);
        if (type.equals(Element.wind)) return PlayerWindElementValue(player);
        return 0;
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

    public static double PlayerLifeElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player, Element.LifeElementValue);
        value += Compute.CuriosAttribute.attributeValue(player, Element.LifeElementValue, LifeElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), Element.LifeElementValue);

        // 百分比分割线
        value *= LifeCurios0.playerLifeElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(Element.life));

        return value;
    }

    public static double PlayerWaterElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player, Element.WaterElementValue);
        value += Compute.CuriosAttribute.attributeValue(player, Element.WaterElementValue, WaterElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), Element.WaterElementValue);

        // 百分比分割线
        value *= WaterElementSword.PlayerWaterElementValueEnhance(player);
        value *= WaterCurios0.playerWaterElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(Element.water));

        return value;
    }

    public static double PlayerFireElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player, Element.FireElementValue);
        value += Compute.CuriosAttribute.attributeValue(player, Element.FireElementValue, FireElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), Element.FireElementValue);

        value += FireElementSword.FireElementValueEnhance(player);

        // 百分比分割线
        value *= FireCurios0.playerFireElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(Element.fire));

        return value;
    }

    public static double PlayerStoneElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player, Element.StoneElementValue);
        value += Compute.CuriosAttribute.attributeValue(player, Element.StoneElementValue, StoneElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), Element.StoneElementValue);

        // 百分比分割线
        value *= StoneCurios0.playerStoneElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(Element.stone));

        return value;
    }

    public static double PlayerIceElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player, Element.IceElementValue);
        value += Compute.CuriosAttribute.attributeValue(player, Element.IceElementValue, IceElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), Element.IceElementValue);

        // 百分比分割线
        value *= IceCurios0.playerIceElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(Element.ice));

        return value;
    }

    public static double PlayerLightningElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player, Element.LightningElementValue);
        value += Compute.CuriosAttribute.attributeValue(player, Element.LightningElementValue, LightningElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), Element.LightningElementValue);

        // 百分比分割线
        value *= LightningCurios0.playerLightningElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(Element.lightning));

        return value;
    }

    public static double PlayerWindElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player, Element.WindElementValue);
        value += Compute.CuriosAttribute.attributeValue(player, Element.WindElementValue, WindElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), Element.WindElementValue);

        // 百分比分割线
        value *= WindCurios0.playerWindElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(Element.wind));

        return value;
    }
}
