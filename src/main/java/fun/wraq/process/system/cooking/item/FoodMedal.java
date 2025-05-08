package fun.wraq.process.system.cooking.item;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.cooking.CookingPlayerData;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FoodMedal extends WraqCurios implements InCuriosOrEquipSlotAttributesModify {
    private final int tier;
    public FoodMedal(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.percentHealthRecover.put(this, new double[]{0.005, 0.008, 0.01, 0.015, 0.02}[tier]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getFuncTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("吃货之力", hoverMainStyle()));
        components.add(Te.s("基于", "烹饪等级", hoverMainStyle(),
                "提供", ComponentUtils.AttributeDescription.maxHealth("")));
        int cookingLevel = CookingPlayerData.getLevelByExp(CookingPlayerData.clientCookingExp);
        components.add(Te.s(CookingPlayerData.getPrefixByLevel(cookingLevel), hoverMainStyle(),
                "->", ComponentUtils.AttributeDescription.maxHealth(String.format("%d%%", cookingLevel * 4))));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.MUSHROOM_STYLE;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfCooking();
    }

    @Override
    public List<Attribute> getAttributes(Player player, ItemStack stack) {
        return List.of(
                new Attribute(Utils.percentMaxHealthEnhance, CookingPlayerData.getPlayerCookingLevel(player) * 0.04)
        );
    }
}
