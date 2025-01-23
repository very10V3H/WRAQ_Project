package fun.wraq.series.overworld.chapter1.plain;

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

public class PlainArmor extends WraqArmor {

    public PlainArmor(ArmorMaterial armorMaterial, Type type) {
        super(armorMaterial, type, new Properties().rarity(CustomStyle.PlainItalic));
        switch (type) {
            case HELMET -> {
                Utils.maxHealth.put(this, 200d);
            }
            case CHESTPLATE -> {
                Utils.maxHealth.put(this, 320d);
            }
            case LEGGINGS -> {
                Utils.maxHealth.put(this, 280d);
            }
            case BOOTS -> {
                Utils.movementSpeedCommon.put(this, 0.08);
                Utils.maxHealth.put(this, 80d);
            }
        }
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPlain;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        PlainSuitDescription.ArmorCommonDescription(components);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    public static double exMaxHealth(Player player) {
        return SuitCount.getPlainSuitCountWithoutCrest(player) > 0 ? Math.min(100, player.experienceLevel) * 10 : 0;
    }
}
