package fun.wraq.series.gems;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.gui.illustrate.Display;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.*;

public class WraqGem extends Item {

    public static final String NEW_MAX_SLOT_DATA_KEY = "newMaxSlot";
    public static final String NEW_SLOT_DATA_KEY = "newSlot";

    protected final Style hoverStyle;
    protected final Component oneLineDescription;
    protected final Component suffix;
    protected List<AttributeMapValue> attributeMapValues;
    protected final Properties properties;

    public record AttributeMapValue(Map<Item, Double> attributeMap, double value) {}

    public WraqGem(Properties properties, List<AttributeMapValue> attributeMapValues,
                   Style hoverStyle, Component oneLineDescription, Component suffix) {
        super(properties);
        this.properties = properties;
        Utils.gemsTag.put(this, 1);
        for (AttributeMapValue attributeMapValue : attributeMapValues) {
            attributeMapValue.attributeMap.put(this, attributeMapValue.value);
        }
        Display.gemList.add(this);
        this.hoverStyle = hoverStyle;
        this.oneLineDescription = oneLineDescription;
        this.suffix = suffix;
        this.attributeMapValues = attributeMapValues;
    }

    // 困缚禁法
    public static class WraqGemD extends WraqGem {
        public WraqGemD(WraqGem wraqGem) {
            super(wraqGem.getProperties(), wraqGem.getAttributeMapValues(), wraqGem.getHoverStyle(), wraqGem.getOneLineDescription(), wraqGem.getSuffix());
            for (AttributeMapValue attributeMapValue : wraqGem.getAttributeMapValues()) {
                attributeMapValue.attributeMap().put(this, attributeMapValue.value() * 2);
            }

            // 将属性写回属性表 以修正物品镶嵌宝石后ALT无法获取全部宝石属性的问题
            List<AttributeMapValue> newValueList = new ArrayList<>();
            wraqGem.getAttributeMapValues().forEach(attributeMapValue -> {
                newValueList.add(new AttributeMapValue(attributeMapValue.attributeMap, attributeMapValue.value * 2));
            });
            setAttributeMapValues(newValueList);
        }
    }

    public static Map<Integer, Map<Item, Item>> GEM_ENHANCE_TYPE_MAP = new HashMap<>();

    // 矿石
    public static class WraqGemO extends WraqGem {
        public WraqGemO(WraqGem wraqGem, int type) {
            super(wraqGem.getProperties(), wraqGem.getAttributeMapValues(), wraqGem.getHoverStyle(), wraqGem.getOneLineDescription(), wraqGem.getSuffix());

            GEM_ENHANCE_TYPE_MAP.computeIfAbsent(type, k -> new HashMap<>());
            GEM_ENHANCE_TYPE_MAP.get(type).put(wraqGem, this);

            Map<Integer, List<AttributeMapValue>> attributeMapValues = new HashMap<>() {{
                put(1, List.of(new AttributeMapValue(Utils.percentMaxHealthEnhance, 0.08),
                        new AttributeMapValue(Utils.percentHealthRecover, 0.002)));
                put(2, List.of(new AttributeMapValue(Utils.critDamage, 0.03),
                        new AttributeMapValue(Utils.coolDownDecrease, 0.05)));
                put(3, List.of(new AttributeMapValue(Utils.defencePenetration, 0.02),
                        new AttributeMapValue(Utils.manaPenetration, 0.02)));
                put(4, List.of(new AttributeMapValue(Utils.percentAttackDamageEnhance, 0.03),
                        new AttributeMapValue(Utils.percentManaDamageEnhance, 0.03)));
            }};

            // 获取原宝石属性
            Map<Map<Item, Double>, Double> map = new HashMap<>() {{
                wraqGem.getAttributeMapValues().forEach(attributeMapValue -> {
                    put(attributeMapValue.attributeMap(), attributeMapValue.value());
                });
            }};

            // 加上指定属性，写入Utils属性map
            attributeMapValues.get(type).forEach(attributeMapValue -> {
                attributeMapValue.attributeMap.put(this, attributeMapValue.value + map.getOrDefault(attributeMapValue.attributeMap, 0d));
            });

            // 将属性写回属性表 以修正物品镶嵌宝石后ALT无法获取全部宝石属性的问题
            List<AttributeMapValue> newValueList = new ArrayList<>();
            newValueList.addAll(attributeMapValues.get(type));
            newValueList.addAll(wraqGem.getAttributeMapValues());
            setAttributeMapValues(newValueList);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = hoverStyle;
        components.add(oneLineDescription);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(suffix);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    public Style getHoverStyle() {
        return hoverStyle;
    }

    public List<AttributeMapValue> getAttributeMapValues() {
        return attributeMapValues;
    }

    public void setAttributeMapValues(List<AttributeMapValue> attributeMapValues) {
        this.attributeMapValues = attributeMapValues;
    }

    public Component getOneLineDescription() {
        return oneLineDescription;
    }

    public Component getSuffix() {
        return suffix;
    }

    public Properties getProperties() {
        return properties;
    }

    public static List<WraqGem> getEquipContainGemList(ItemStack equip) throws CommandSyntaxException {
        List<WraqGem> gemList = new ArrayList<>();
        if (equip.getTagElement(Utils.MOD_ID) != null) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            for (int i = 1 ; i <= 3 ; i ++) {
                if (data.contains("newGems" + i)) {
                    Item gemItem = Compute.getItemFromString(data.getString("newGems" + i)).getItem();
                    if (gemItem instanceof WraqGem wraqGem) gemList.add(wraqGem);
                }
            }
        }
        return gemList;
    }

    public static Set<WraqGem> getPlayerDistinctEquipGemsSet(Player player) {
        Set<WraqGem> gems = new HashSet<>();
        InventoryOperation.getAllEquipSlotItems(player).forEach(stack -> {
            try {
                gems.addAll(getEquipContainGemList(stack));
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
        });
        return gems;
    }

    public static List<WraqGem> getPlayerAllEquipGems(Player player) {
        List<WraqGem> gems = new ArrayList<>();
        InventoryOperation.getAllEquipSlotItems(player).forEach(stack -> {
            try {
                gems.addAll(getEquipContainGemList(stack));
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
        });
        return gems;
    }
}
