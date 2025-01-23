package fun.wraq.series.overworld.chapter1.forest;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ForestArmor extends WraqArmor {

    public ForestArmor(ArmorMaterial armorMaterial, Type type) {
        super(armorMaterial, type, new Properties().rarity(CustomStyle.ForestItalic));
        switch (type) {
            case HELMET -> {
                Utils.defence.put(this, 2.5d);
            }
            case CHESTPLATE -> {
                Utils.defence.put(this, 4.5d);
            }
            case LEGGINGS -> {
                Utils.defence.put(this, 3.5d);
            }
            case BOOTS -> {
                Utils.defence.put(this, 2d);
                Utils.movementSpeedCommon.put(this, 0.05);
            }
        }
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfForest;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ForestSuitDescription.ArmorCommonDescription(components);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    public static double exDefence(Player player) {
        return SuitCount.getForestSuitCountWithoutCrest(player) > 0 ? Math.min(100, player.experienceLevel) * 0.3 : 0;
    }
}
