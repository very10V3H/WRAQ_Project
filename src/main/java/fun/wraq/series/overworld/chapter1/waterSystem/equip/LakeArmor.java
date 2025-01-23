package fun.wraq.series.overworld.chapter1.waterSystem.equip;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter1.waterSystem.LakeSuitDescription;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LakeArmor extends WraqArmor {

    public LakeArmor(ArmorMaterial armorMaterial, Type type) {
        super(armorMaterial, type, new Properties().rarity(CustomStyle.LakeItalic));
        switch (type) {
            case HELMET -> {
                Utils.coolDownDecrease.put(this, 0.1);
            }
            case CHESTPLATE -> {
                Utils.coolDownDecrease.put(this, 0.16);
            }
            case LEGGINGS -> {
                Utils.coolDownDecrease.put(this, 0.14);
            }
            case BOOTS -> {
                Utils.coolDownDecrease.put(this, 0.08);
                Utils.movementSpeedCommon.put(this, 0.05);
            }
        }
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfLake;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        LakeSuitDescription.LakeArmorCommonDescription(components);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    public static double exCooldown(Player player) {
        return SuitCount.getLakeSuitCountWithoutCrest(player) > 0 ? Math.min(100, player.experienceLevel) * 0.0025 : 0;
    }
}
