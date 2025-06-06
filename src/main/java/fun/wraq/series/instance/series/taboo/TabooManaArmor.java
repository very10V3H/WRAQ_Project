package fun.wraq.series.instance.series.taboo;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.impl.oncostmana.OnCostManaEquip;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.ore.OreItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabooManaArmor extends WraqArmor implements OnCostManaEquip, InCuriosOrEquipSlotAttributesModify, ForgeItem {

    public TabooManaArmor(ModArmorMaterials Material, Type type, Properties itemProperties) {
        super(Material, type, itemProperties);
        Utils.maxHealth.put(this, 10000d);
        Utils.movementSpeedCommon.put(this, 0.15);
        Utils.levelRequire.put(this, 150);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfBloodMana;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("禁忌秘法-涌动").withStyle(style));
        components.add(Component.literal(" 根据你最近5s内消耗的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaValue("4%")).
                append(Component.literal("来提供等量").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaPenetration("")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix2Sakura();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.CONSTRAINT_TABOO.get(), 1),
                new ItemStack(ModItems.PURPLE_IRON_BOOTS.get(), 1),
                new ItemStack(OreItems.WRAQ_ORE_3_ITEM.get(), 32)
        );
    }

    public record NearCostMana(int tick, double value) {}

    public static Map<Player, List<NearCostMana>> nearCostListMap = new HashMap<>();

    @Override
    public void onCostMana(Player player, double costManaValue) {
        if (!nearCostListMap.containsKey(player)) {
            nearCostListMap.put(player, new ArrayList<>());
        }
        List<NearCostMana> list = nearCostListMap.get(player);
        list.add(new NearCostMana(Tick.get() + 100, costManaValue));
        if (Math.abs(getStoredTotalValue(player) * 0.04) >= 1) {
            Compute.sendEffectLastTime(player, this, 100,
                    (int) (-getStoredTotalValue(player) * 0.04), false);
        }
    }

    @Override
    public List<Attribute> getAttributes(Player player, ItemStack stack) {
        return List.of(new Attribute(Utils.manaPenetration0, -getStoredTotalValue(player) * 0.04));
    }

    private double getStoredTotalValue(Player player) {
        if (!nearCostListMap.containsKey(player)) return 0;
        List<NearCostMana> list = nearCostListMap.get(player);
        list.removeIf(nearCostMana -> nearCostMana == null || nearCostMana.tick < Tick.get());
        return list.stream().mapToDouble(cost -> cost.value).sum();
    }
}
