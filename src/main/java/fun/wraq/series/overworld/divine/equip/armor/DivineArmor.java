package fun.wraq.series.overworld.divine.equip.armor;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.series.overworld.divine.DivineUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class DivineArmor extends WraqArmor implements DivineArmorCommon {

    private final double maxRate;
    public DivineArmor(ArmorMaterial armorMaterial, Type type, Properties properties, double maxRate) {
        super(armorMaterial, type, properties);
        this.maxRate = maxRate;
    }

    @Override
    public Style getMainStyle() {
        return DivineArmorCommon.style;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return DivineArmorCommon.getDescription(stack, maxRate);
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfDivine();
    }

    @Override
    public int getType(Player player) {
        switch (this.getType()) {
            case CHESTPLATE -> {
                return 4;
            }
            case HELMET -> {
                return 1;
            }
            case LEGGINGS -> {
                return 2;
            }
            case BOOTS -> {
                return 3;
            }
        }
        return 1;
    }

    @Override
    public double getEnhanceRate(Player player) {
        return DivineArmorCommon.getCommonEnhanceRate(player, maxRate);
    }

    @Override
    public void onKill(Player player, Mob mob) {
        DivineUtils.addHolyLightCount(player, 1);
    }
}
