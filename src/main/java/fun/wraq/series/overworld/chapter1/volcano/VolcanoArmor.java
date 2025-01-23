package fun.wraq.series.overworld.chapter1.volcano;

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

public class VolcanoArmor extends WraqArmor {

    public VolcanoArmor(ArmorMaterial armorMaterial, Type type) {
        super(armorMaterial, type, new Properties().rarity(CustomStyle.VolcanoItalic));
        switch (type) {
            case HELMET -> {
                Utils.attackDamage.put(this, 10d);
                Utils.manaDamage.put(this, 20d);
            }
            case CHESTPLATE -> {
                Utils.attackDamage.put(this, 16d);
                Utils.manaDamage.put(this, 32d);
            }
            case LEGGINGS -> {
                Utils.attackDamage.put(this, 14d);
                Utils.manaDamage.put(this, 28d);
            }
            case BOOTS -> {
                Utils.movementSpeedCommon.put(this, 0.05);
                Utils.attackDamage.put(this, 8d);
                Utils.manaDamage.put(this, 16d);
            }
        }
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfVolcano;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        VolcanoSuitDescription.VolcanoArmorCommonDescription(components);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    public static double exAttackDamage(Player player) {
        return SuitCount.getVolcanoCountWithoutCrest(player) > 0 ? Math.min(100, player.experienceLevel) : 0;
    }

    public static double exManaDamage(Player player) {
        return SuitCount.getVolcanoCountWithoutCrest(player) > 0 ? Math.min(100, player.experienceLevel) * 2 : 0;
    }
}
