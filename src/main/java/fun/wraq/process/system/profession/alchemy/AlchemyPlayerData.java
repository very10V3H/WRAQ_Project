package fun.wraq.process.system.profession.alchemy;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.Map;

public class AlchemyPlayerData {
    public static final String ALCHEMY_DATA_KEY = "AlchemyData";
    public static CompoundTag getAlchemyData(Player player) {
        return Compute.getPlayerSpecificKeyCompoundTagData(player, ALCHEMY_DATA_KEY);
    }

    public static final String TIER_KEY = "Tier";
    public static final String CURRENT_CHOOSE_ATTRIBUTE_NAME_KEY = "CurrentChooseAttributeName";

    public static int getTier(Player player) {
        return getAlchemyData(player).getInt(TIER_KEY);
    }

    public static double getEnhanceRateByTier(int tier) {
        return tier * 0.03;
    }

    public static String getChooseAttributeName(Player player) {
        return getAlchemyData(player).getString(CURRENT_CHOOSE_ATTRIBUTE_NAME_KEY);
    }

    public static void setChooseAttributeName(Player player, String attributeName) {
        getAlchemyData(player).putString(CURRENT_CHOOSE_ATTRIBUTE_NAME_KEY, attributeName);
    }

    public static void tryToChooseAttributeName(Player player) {
        if (getTier(player) == 0) {
            sendMSG(player, Te.s("你首先需要成为一名", "炼金术士", CustomStyle.styleOfGold));
            return;
        }
        sendMSG(player, Te.s("点击以下任一属性，以获取属性增益"));

        player.sendSystemMessage(
                Te.s(getCommandText(PlayerAttributes.AttributeNames.ATTACK_DAMAGE),
                        getCommandText(PlayerAttributes.AttributeNames.DEFENCE),
                        getCommandText(PlayerAttributes.AttributeNames.DEFENCE_PENETRATION0)));
        player.sendSystemMessage(
                Te.s(getCommandText(PlayerAttributes.AttributeNames.MANA_DAMAGE),
                        getCommandText(PlayerAttributes.AttributeNames.MANA_DEFENCE),
                        getCommandText(PlayerAttributes.AttributeNames.MANA_PENETRATION0)));
        player.sendSystemMessage(
                Te.s(getCommandText(PlayerAttributes.AttributeNames.CRIT_RATE),
                        getCommandText(PlayerAttributes.AttributeNames.CRIT_DAMAGE),
                        getCommandText(PlayerAttributes.AttributeNames.RELEASE_SPEED),
                        getCommandText(PlayerAttributes.AttributeNames.MOVEMENT_SPEED_COMMON)));
        player.sendSystemMessage(
                Te.s(getCommandText(PlayerAttributes.AttributeNames.MANA_RECOVER),
                        getCommandText(PlayerAttributes.AttributeNames.MAX_MANA),
                        getCommandText(PlayerAttributes.AttributeNames.MAX_HEALTH),
                        getCommandText(PlayerAttributes.AttributeNames.HEALTH_RECOVER)));
        player.sendSystemMessage(
                Te.s(getCommandText(PlayerAttributes.AttributeNames.EXP_UP)));
    }

    public static Component getCommandText(String attributeName) {
        return Te.c(PlayerAttributes.AttributeNames.getDescription(attributeName),
                "/vmd alchemy " + attributeName,
                Te.s("点击以使用", PlayerAttributes.AttributeNames.getDescription(attributeName), "增益"));
    }

    public static void onClickCommandText(String attributeName, Player player) {
        setChooseAttributeName(player, attributeName);
        sendMSG(player, Te.s("你的", PlayerAttributes.AttributeNames.getDescription(attributeName),
                "已", "增幅", String.format("%.0f%%", getEnhanceRateByTier(getTier(player)))));
    }

    public static double getEnhanceRate(Player player, Map<Item, Double> attributeMap) {
        int tier = getTier(player);
        if (tier > 0 && getAlchemyData(player).contains(CURRENT_CHOOSE_ATTRIBUTE_NAME_KEY)) {
            String attributeName = getChooseAttributeName(player);
            if (PlayerAttributes.AttributeNames.getAttributeMapByName(attributeName).equals(attributeMap)) {
                return getEnhanceRateByTier(tier);
            }
        }
        return 0;
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("炼金", CustomStyle.styleOfGold), content);
    }
}
