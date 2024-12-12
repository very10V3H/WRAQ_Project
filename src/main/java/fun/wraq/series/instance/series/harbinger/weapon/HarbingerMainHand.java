package fun.wraq.series.instance.series.harbinger.weapon;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.display.BeforeRemoveMaterialOnForge;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.multiblockactive.rightclick.drive.EnhanceCondition;
import fun.wraq.process.func.multiblockactive.rightclick.drive.EnhanceOperation;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.harbinger.HarbingerItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public interface HarbingerMainHand extends BeforeRemoveMaterialOnForge {

    Map<Player, Integer> countMap = new WeakHashMap<>();
    Map<Player, Integer> countExpiredTickMap = new WeakHashMap<>();

    static boolean isHandHeld(Player player) {
        return player.getMainHandItem().getItem() instanceof HarbingerMainHand;
    }

    String MAX_COUNT_KEY = "MAX_COUNT_KEY";
    static int getMaxCount(ItemStack stack) {
        if (stack.getItem() instanceof HarbingerMainHand) {
            return Math.max(20, stack.getOrCreateTagElement(Utils.MOD_ID).getInt(MAX_COUNT_KEY));
        }
        return 0;
    }

    static void setMaxCount(ItemStack stack, int count) {
        stack.getOrCreateTagElement(Utils.MOD_ID).putInt(MAX_COUNT_KEY, count);
    }

    EnhanceOperation enhanceOperation = (stack -> {
        setMaxCount(stack, getMaxCount(stack) + 1);
    });

    EnhanceCondition enhanceCondition = (stack -> {
        return stack.getItem() instanceof HarbingerMainHand && getMaxCount(stack) < 50;
    });

    String ATTACK_DAMAGE = "HARBINGER_ATTACK_DAMAGE";
    String MANA_DAMAGE = "HARBINGER_MANA_DAMAGE";
    String PERCENT_ATTACK_DAMAGE = "HARBINGER_PERCENT_ATTACK_DAMAGE";
    String PERCENT_MANA_DAMAGE = "HARBINGER_PERCENT_MANA_DAMAGE";
    String CRIT_DAMAGE = "HARBINGER_CRIT_DAMAGE";
    String SWIFTNESS = "HARBINGER_SWIFTNESS";
    String MANA_RECOVER = "HARBINGER_MANA_RECOVER";

    static void tick(Player player) {
        if (player.tickCount % 20 == 0) {
            if (isHandHeld(player) && Compute.playerIsInBattle(player)) {
                ItemStack stack = player.getMainHandItem();
                countMap.compute(player, (k, v) -> v == null ? 1 : Math.min(getMaxCount(stack), v + 1));
                countExpiredTickMap.put(player, Tick.get() + Tick.s(30));
                Compute.sendEffectLastTime(player, HarbingerItems.HARBINGER_HEART.get(), Tick.s(30), countMap.get(player), true);
            }
            if (countMap.containsKey(player) && Tick.get() > countExpiredTickMap.getOrDefault(player, 0)) {
                countMap.remove(player);
                countExpiredTickMap.remove(player);
                Compute.removeEffectLastTime(player, HarbingerItems.HARBINGER_HEART.get());
            }
        }
    }

    static void active(Player player) {
        if (Compute.playerIsInBattle(player)) {
            ItemStack stack = player.getMainHandItem();
            List<Item> list = List.of(
                    HarbingerItems.HARBINGER_SWORD.get(),
                    HarbingerItems.HARBINGER_BOW.get(),
                    HarbingerItems.HARBINGER_SCEPTRE.get()
            );
            if (player.isShiftKeyDown()) {
                countMap.compute(player, (k, v) -> v == null ? 5 : Math.min(getMaxCount(stack), v * 2));
                countExpiredTickMap.put(player, Tick.get() + Tick.s(30));
                list.forEach(item -> {
                    player.getCooldowns().addCooldown(item, Tick.s(30));
                });
                Compute.sendEffectLastTime(player, HarbingerItems.HARBINGER_HEART.get(), Tick.s(30), countMap.get(player), true);
            } else {
                countMap.compute(player, (k, v) -> v == null ? 5 : Math.min(getMaxCount(stack), v + 5));
                countExpiredTickMap.put(player, Tick.get() + Tick.s(10));
                list.forEach(item -> {
                    player.getCooldowns().addCooldown(item, Tick.s(10));
                });
                Compute.sendEffectLastTime(player, HarbingerItems.HARBINGER_HEART.get(), Tick.s(30), countMap.get(player), true);
            }
        }
    }

    static double getDamageRate(Player player) {
        if (countExpiredTickMap.getOrDefault(player, 0) > Tick.get()) {
            return countMap.getOrDefault(player, 0) * 0.01;
        }
        return 0;
    }

    static double getAttackDamageRate(Player player) {
        Item item = player.getMainHandItem().getItem();
        if (isHandHeld(player) && (item instanceof WraqSword || item instanceof WraqBow)) {
            return getDamageRate(player);
        }
        return 0;
    }

    static double getManaDamageRate(Player player) {
        Item item = player.getMainHandItem().getItem();
        if (isHandHeld(player) && item instanceof WraqSceptre) {
            return getDamageRate(player);
        }
        return 0;
    }

    static List<Component> getCommonAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("高温高压", CustomStyle.styleOfHarbinger));
        components.add(Te.s(" 在", "战斗状态", CustomStyle.styleOfPower, "中，每秒获得1层",
                "「匠」", CustomStyle.styleOfHarbinger));
        components.add(Te.s(" 最多", String.valueOf(getMaxCount(stack)), CustomStyle.styleOfHarbinger,
                "层，每层提供", Utils.attackDamage.containsKey(stack.getItem()) ?
                        ComponentUtils.AttributeDescription.attackDamage("1%总")
                        : ComponentUtils.AttributeDescription.manaDamage("1%总")));
        components.add(Te.s("「匠」", CustomStyle.styleOfHarbinger, "持续30s"));
        ComponentUtils.descriptionActive(components, Te.s("锤炼", CustomStyle.styleOfHarbinger));
        components.add(Te.s(" 获得5层", "「匠」", CustomStyle.styleOfHarbinger));
        ComponentUtils.coolDownTimeDescription(components, 10);
        ComponentUtils.descriptionActive(components, Te.s("淬火", CustomStyle.styleOfHarbinger));
        components.add(Te.s(" 按下shift释放，翻倍", "「匠」", CustomStyle.styleOfHarbinger, "的层数"));
        ComponentUtils.coolDownTimeDescription(components, 30);
        components.add(Te.s(" 该主动效果的冷却时间不会被技能急速削减", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        components.add(Te.s(" 且仅在战斗状态下能够使用", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        ComponentUtils.descriptionPassive(components, Te.s("铸造", CustomStyle.styleOfHarbinger));
        components.add(Te.s(" 可以使用",
                HarbingerItems.HARBINGER_INGOT.get().getDefaultInstance().getDisplayName(), "来提升最大层数"));
        components.add(Te.s(" 至多可提升至", "50", CustomStyle.styleOfHarbinger, "层"));
        components.add(Te.s(" 铸造材料:", CustomStyle.styleOfHarbinger));
        components.addAll(getMaterial(stack));
        return components;
    }

    String MATERIAL_LIST_KEY = "MATERIAL_LIST_KEY";
    String ROD = "ROD";
    String CORE = "CORE";
    String BLADE = "BLADE";
    String STRING = "STRING";
    String MIRROR = "MIRROR";

    Map<Item, String> itemToStringMap = new LinkedHashMap<>();
    Map<String, Item> stringToItemMap = new LinkedHashMap<>();

    static Map<Item, String> getItemToStringMap() {
        if (itemToStringMap.isEmpty()) {
            itemToStringMap.put(HarbingerItems.HARBINGER_ROD.get(), ROD);
            itemToStringMap.put(HarbingerItems.HARBINGER_WEAPON_CORE.get(), CORE);
            itemToStringMap.put(HarbingerItems.HARBINGER_SWORD_BLADE.get(), BLADE);
            itemToStringMap.put(HarbingerItems.HARBINGER_STRING.get(), STRING);
            itemToStringMap.put(HarbingerItems.HARBINGER_MIRROR.get(), MIRROR);
        }
        return itemToStringMap;
    }

    static Map<String, Item> getStringToItemMap() {
        if (stringToItemMap.isEmpty()) {
            stringToItemMap.put(ROD, HarbingerItems.HARBINGER_ROD.get());
            stringToItemMap.put(CORE, HarbingerItems.HARBINGER_WEAPON_CORE.get());
            stringToItemMap.put(BLADE, HarbingerItems.HARBINGER_SWORD_BLADE.get());
            stringToItemMap.put(STRING, HarbingerItems.HARBINGER_STRING.get());
            stringToItemMap.put(MIRROR, HarbingerItems.HARBINGER_MIRROR.get());
        }
        return stringToItemMap;
    }

    static List<Component> getMaterial(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID).getCompound(MATERIAL_LIST_KEY);
        getStringToItemMap().forEach((k, v) -> {
            if (data.contains(k)) {
                ForgeEquipUtils.TierValueAndDescription tierValueAndDescription
                        = ForgeEquipUtils.tierValueAndDescriptionMap.get(data.getInt(k));
                components.add(Te.s(v.getDefaultInstance().getDisplayName(), " - ",
                        tierValueAndDescription.description(), tierValueAndDescription.style()));
            }
        });
        return components;
    }

    static void addMaterial(ItemStack weapon, ItemStack material) {
        CompoundTag data = weapon.getOrCreateTagElement(Utils.MOD_ID);
        if (!data.contains(MATERIAL_LIST_KEY)) {
            data.put(MATERIAL_LIST_KEY, new CompoundTag());
        }
        CompoundTag materials = data.getCompound(MATERIAL_LIST_KEY);
        getItemToStringMap().forEach((k, v) -> {
            if (material.is(k)) {
                materials.putInt(v, HarbingerWeaponMaterial.getQualityTier(material));
            }
        });
    }
}
