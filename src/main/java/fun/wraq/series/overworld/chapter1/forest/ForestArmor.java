package fun.wraq.series.overworld.chapter1.forest;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.impl.forge.IgnoreForgeQuality;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ForestArmor extends WraqArmor implements IgnoreForgeQuality {

    public ForestArmor(ArmorMaterial armorMaterial, Type type) {
        super(armorMaterial, type, new Properties().rarity(CustomStyle.ForestItalic));
        switch (type) {
            case HELMET -> {
                Utils.defence.put(this, 2.5d);
            }
            case CHESTPLATE -> {
                Utils.manaDefence.put(this, 20d);
                Utils.defence.put(this, 20d);
            }
            case LEGGINGS -> {
                Utils.maxHealth.put(this, 200d);
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
        if (type.equals(Type.CHESTPLATE)) {
            Compute.DescriptionPassive(components,
                    Component.literal("森之壁垒").withStyle(CustomStyle.styleOfForest));
            components.add(Component.literal(" 生命值低于50%时，获得").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.defence("20")));
        }
        if (type.equals(Type.LEGGINGS)) {
            Compute.DescriptionPassive(components,
                    Component.literal("林间复苏").withStyle(CustomStyle.styleOfForest));
            components.add(Component.literal(" 每秒回复").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.lossHealth("0.5%")));
        }
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public void tick(Player player) {
        if (type.equals(Type.LEGGINGS) && player.tickCount % 20 == 0
                && player.getHealth() < player.getMaxHealth()) {
            Compute.playerHeal(player, (player.getMaxHealth() - player.getHealth()) * 0.005);
        }
        super.tick(player);
    }

    public static double getLowHealthDefence(Player player) {
        if (!(player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ForestArmor)) {
            return 0;
        }
        return player.getHealth() / player.getMaxHealth() < 0.5 ? 20 : 0;
    }
}
