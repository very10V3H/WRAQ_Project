package com.very.wraq.process.element;

import com.very.wraq.customized.uniform.element.*;
import com.very.wraq.process.element.equipAndCurios.fireElement.FireElementSword;
import com.very.wraq.process.element.equipAndCurios.waterElement.WaterElementSword;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
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
        if (type.equals(Element.Life)) return PlayerLifeElementValue(player);
        if (type.equals(Element.Water)) return PlayerWaterElementValue(player);
        if (type.equals(Element.Fire)) return PlayerFireElementValue(player);
        if (type.equals(Element.Stone)) return PlayerStoneElementValue(player);
        if (type.equals(Element.Ice)) return PlayerIceElementValue(player);
        if (type.equals(Element.Lightning)) return PlayerLightningElementValue(player);
        if (type.equals(Element.Wind)) return PlayerWindElementValue(player);
        return 0;
    }

    public static double QiLingJudge(Player player, Map<Item, Double> map) {
        double value = 0;
        Inventory inventory = player.getInventory();
        for (int i = 3 ; i < 9 ; i ++) {
            ItemStack itemStack = inventory.getItem(i);
            Item item = itemStack.getItem();
            if (map.containsKey(item) && Utils.PassiveEquipTag.containsKey(item))
                value += map.get(item);
        }
        return value;
    }

    public static double mainHandEquipValue(Item item, Map<Item, Double> map) {
        if (Utils.MainHandTag.containsKey(item) && map.containsKey(item)) return map.get(item);
        return 0;
    }

    public static double PlayerLifeElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player,Element.LifeElementValue);
        value += Compute.CuriosAttribute.AttributeValue(player, Element.LifeElementValue, LifeElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), Element.LifeElementValue);

        // 百分比分割线
        value *= LifeCurios0.playerLifeElementValueEnhance(player);

        return value;
    }

    public static double PlayerWaterElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player,Element.WaterElementValue);
        value += Compute.CuriosAttribute.AttributeValue(player, Element.WaterElementValue, WaterElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), Element.WaterElementValue);

        // 百分比分割线
        value *= WaterElementSword.PlayerWaterElementValueEnhance(player);
        value *= WaterCurios0.playerWaterElementValueEnhance(player);

        return value;
    }

    public static double PlayerFireElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player,Element.FireElementValue);
        value += Compute.CuriosAttribute.AttributeValue(player, Element.FireElementValue, FireElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), Element.FireElementValue);

        value += FireElementSword.FireElementValueEnhance(player);

        // 百分比分割线
        value *= FireCurios0.playerFireElementValueEnhance(player);

        return value;
    }

    public static double PlayerStoneElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player,Element.StoneElementValue);
        value += Compute.CuriosAttribute.AttributeValue(player, Element.StoneElementValue, StoneElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), Element.StoneElementValue);

        // 百分比分割线
        value *= StoneCurios0.playerStoneElementValueEnhance(player);

        return value;
    }

    public static double PlayerIceElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player,Element.IceElementValue);
        value += Compute.CuriosAttribute.AttributeValue(player, Element.IceElementValue, IceElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), Element.IceElementValue);

        // 百分比分割线
        value *= IceCurios0.playerIceElementValueEnhance(player);

        return value;
    }

    public static double PlayerLightningElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player,Element.LightningElementValue);
        value += Compute.CuriosAttribute.AttributeValue(player, Element.LightningElementValue, LightningElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), Element.LightningElementValue);

        // 百分比分割线
        value *= LightningCurios0.playerLightningElementValueEnhance(player);

        return value;
    }

    public static double PlayerWindElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player,Element.WindElementValue);
        value += Compute.CuriosAttribute.AttributeValue(player, Element.WindElementValue, WindElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), Element.WindElementValue);

        // 百分比分割线
        value *= WindCurios0.playerWindElementValueEnhance(player);

        return value;
    }
}
