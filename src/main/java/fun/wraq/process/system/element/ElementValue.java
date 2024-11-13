package fun.wraq.process.system.element;

import fun.wraq.common.Compute;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.uniform.element.*;
import fun.wraq.process.system.element.equipAndCurios.fireElement.FireElementSword;
import fun.wraq.process.system.element.equipAndCurios.waterElement.WaterElementSword;
import fun.wraq.process.system.season.MySeason;
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
        if (type.equals(fun.wraq.process.system.element.Element.life)) return getPlayerLifeElementValue(player);
        if (type.equals(fun.wraq.process.system.element.Element.water)) return getPlayerWaterElementValue(player);
        if (type.equals(fun.wraq.process.system.element.Element.fire)) return getPlayerFireElementValue(player);
        if (type.equals(fun.wraq.process.system.element.Element.stone)) return getPlayerStoneElementValue(player);
        if (type.equals(fun.wraq.process.system.element.Element.ice)) return getPlayerIceElementValue(player);
        if (type.equals(fun.wraq.process.system.element.Element.lightning)) return getPlayerLightningElementValue(player);
        if (type.equals(fun.wraq.process.system.element.Element.wind)) return getPlayerWindElementValue(player);
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

    public static double getPlayerLifeElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player, fun.wraq.process.system.element.Element.LifeElementValue);
        value += Compute.CuriosAttribute.attributeValue(player, fun.wraq.process.system.element.Element.LifeElementValue, LifeElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), fun.wraq.process.system.element.Element.LifeElementValue);

        // 百分比分割线
        value *= LifeCurios0.playerLifeElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(fun.wraq.process.system.element.Element.life));

        return value;
    }

    public static double getPlayerWaterElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player, fun.wraq.process.system.element.Element.WaterElementValue);
        value += Compute.CuriosAttribute.attributeValue(player, fun.wraq.process.system.element.Element.WaterElementValue, WaterElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), fun.wraq.process.system.element.Element.WaterElementValue);

        // 百分比分割线
        value *= WaterElementSword.PlayerWaterElementValueEnhance(player);
        value *= WaterCurios0.playerWaterElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(fun.wraq.process.system.element.Element.water));

        return value;
    }

    public static double getPlayerFireElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player, fun.wraq.process.system.element.Element.FireElementValue);
        value += Compute.CuriosAttribute.attributeValue(player, fun.wraq.process.system.element.Element.FireElementValue, FireElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), fun.wraq.process.system.element.Element.FireElementValue);

        value += FireElementSword.FireElementValueEnhance(player);

        // 百分比分割线
        value *= FireCurios0.playerFireElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(fun.wraq.process.system.element.Element.fire));

        return value;
    }

    public static double getPlayerStoneElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player, fun.wraq.process.system.element.Element.StoneElementValue);
        value += Compute.CuriosAttribute.attributeValue(player, fun.wraq.process.system.element.Element.StoneElementValue, StoneElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), fun.wraq.process.system.element.Element.StoneElementValue);

        // 百分比分割线
        value *= StoneCurios0.playerStoneElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(fun.wraq.process.system.element.Element.stone));

        return value;
    }

    public static double getPlayerIceElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player, fun.wraq.process.system.element.Element.IceElementValue);
        value += Compute.CuriosAttribute.attributeValue(player, fun.wraq.process.system.element.Element.IceElementValue, IceElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), fun.wraq.process.system.element.Element.IceElementValue);

        // 百分比分割线
        value *= IceCurios0.playerIceElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(fun.wraq.process.system.element.Element.ice));

        return value;
    }

    public static double getPlayerLightningElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player, fun.wraq.process.system.element.Element.LightningElementValue);
        value += Compute.CuriosAttribute.attributeValue(player, fun.wraq.process.system.element.Element.LightningElementValue, LightningElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), fun.wraq.process.system.element.Element.LightningElementValue);

        // 百分比分割线
        value *= LightningCurios0.playerLightningElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(fun.wraq.process.system.element.Element.lightning));

        return value;
    }

    public static double getPlayerWindElementValue(Player player) {
        double value = 0;

        value += QiLingJudge(player, fun.wraq.process.system.element.Element.WindElementValue);
        value += Compute.CuriosAttribute.attributeValue(player, fun.wraq.process.system.element.Element.WindElementValue, WindElementValue);

        value += mainHandEquipValue(player.getMainHandItem().getItem(), fun.wraq.process.system.element.Element.WindElementValue);

        // 百分比分割线
        value *= WindCurios0.playerWindElementValueEnhance(player);
        value *= (1 + MySeason.getCurrentSeasonElementEffect(Element.wind));

        return value;
    }
}
