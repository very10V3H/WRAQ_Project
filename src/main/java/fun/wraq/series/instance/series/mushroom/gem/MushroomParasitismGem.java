package fun.wraq.series.instance.series.mushroom.gem;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.process.func.StableTierAttributeModifier;
import fun.wraq.series.gems.passive.WraqPassiveGem;
import fun.wraq.series.gems.passive.impl.GemOnKillMob;
import fun.wraq.series.instance.series.mushroom.MushroomItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class MushroomParasitismGem extends WraqPassiveGem implements GemOnKillMob, Decomposable {

    private final boolean isEnhanced;
    public MushroomParasitismGem(Properties properties, List<AttributeMapValue> attributeMapValues, Style hoverStyle,
                                 Component oneLineDescription, Component suffix, boolean isEnhanced) {
        super(properties, attributeMapValues, hoverStyle, oneLineDescription, suffix);
        this.isEnhanced = isEnhanced;
    }

    @Override
    public List<Component> getAdditionDescription() {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("寄生", hoverStyle));
        components.add(Te.s(" 击杀敌人", ChatFormatting.RED, "后，将掉落一个", "菌", hoverStyle));
        components.add(Te.s(" 拾取", "菌", hoverStyle, "将提供",
                ComponentUtils.AttributeDescription.maxHealth("等级 * " + (isEnhanced ? "2.5%" : "2%"))));
        components.add(Te.s(" 持续30s", ChatFormatting.AQUA, "，最多可叠加至", "10层", hoverStyle));
        components.add(Te.s(" 在提供最大生命值时，将会回复等量生命值", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        components.add(Te.s(" 当层数达10层时继续拾取，仅提供生命回复", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        return components;
    }

    public static Map<String, Queue<ItemEntity>> itemEntityMap = new HashMap<>();

    public static void handlePlayerTick(Player player) {
        if (itemEntityMap.containsKey(Name.get(player))) {
            Queue<ItemEntity> queue = itemEntityMap.get(Name.get(player));
            queue.removeIf(itemEntity -> {
                if (itemEntity.tickCount > 100) {
                    itemEntity.remove(Entity.RemovalReason.KILLED);
                    return true;
                }
                return itemEntity.isRemoved();
            });
        }
    }

    @Override
    public void onKill(Player player, Mob mob) {
        if (!itemEntityMap.containsKey(Name.get(player))) {
            itemEntityMap.put(Name.get(player), new ArrayDeque<>());
        }
        Queue<ItemEntity> queue = itemEntityMap.get(Name.get(player));
        if (queue.size() > 15) {
            queue.poll().remove(Entity.RemovalReason.KILLED);
        }
        Item item;
        if (isEnhanced) {
            item = MushroomItems.PARASITISM_GEM_ENHANCED_MUSHROOM.get();
        } else {
            item = MushroomItems.PARASITISM_GEM_MUSHROOM.get();
        }
        ItemEntity itemEntity = ItemAndRate
                .summonItemEntity(item.getDefaultInstance(), mob.getEyePosition(), mob.level(), 20);
        queue.add(itemEntity);
    }

    public static void clearItemEntity() {
        itemEntityMap.forEach((name, queue) -> {
            queue.forEach(itemEntity -> {
                if (itemEntity != null && !itemEntity.isRemoved()) {
                    itemEntity.remove(Entity.RemovalReason.KILLED);
                }
            });
        });
    }

    public static final String PASSIVE_TAG = "MushroomParasitismGemPassive";

    public static void onPickUp(Player player) {
        if (StableTierAttributeModifier.getAttributeModifierTier(player,
                StableTierAttributeModifier.playerPercentMaxHealthExValue, PASSIVE_TAG) >= 10) {
            Compute.playerHeal(player, player.getMaxHealth() * 0.02);
        }
        StableTierAttributeModifier.addM(player, StableTierAttributeModifier.playerPercentMaxHealthExValue,
                PASSIVE_TAG, 0.02,
                Tick.get() + Tick.s(30), 10, "item/brown_mushroom");
    }

    public static void onEnhancedPickUp(Player player) {
        if (StableTierAttributeModifier.getAttributeModifierTier(player,
                StableTierAttributeModifier.playerPercentMaxHealthExValue, PASSIVE_TAG) >= 10) {
            Compute.playerHeal(player, player.getMaxHealth() * 0.025);
        }
        StableTierAttributeModifier.addM(player, StableTierAttributeModifier.playerPercentMaxHealthExValue,
                PASSIVE_TAG, 0.025,
                Tick.get() + Tick.s(30), 10, "item/brown_mushroom");
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(MushroomItems.MUSHROOM_GEM_PIECE.get(), 6);
    }
}
