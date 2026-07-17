package fun.wraq.common.equip;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import fun.wraq.blocks.blocks.forge.ForgeRecipe;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.equip.impl.RepeatableCurios;
import fun.wraq.common.equip.impl.Souvenirs;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.gui.illustrate.Display;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.castle.RandomCuriosAttributesUtil;
import fun.wraq.series.moontain.equip.curios.MoontainCurios;
import fun.wraq.series.newrunes.RuneItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.*;

public abstract class WraqCurios extends Item implements ICurioItem {

    public WraqCurios(Properties properties) {
        super(properties.stacksTo(1));
        if (!(this instanceof Souvenirs)) {
            Utils.curiosList.add(this);
        }
        if (this instanceof ForgeItem forgeItem) {
            ForgeRecipe.recipes.put(this, forgeItem.forgeRecipe());
        }
        if (this instanceof RuneItem) {
            Display.runeList.add(this);
        }
    }

    public WraqCurios(Properties properties, int maxSlotSize) {
        super(properties.stacksTo(maxSlotSize));
        if (!(this instanceof Souvenirs)) {
            Utils.curiosList.add(this);
        }
        if (this instanceof ForgeItem forgeItem) {
            ForgeRecipe.recipes.put(this, forgeItem.forgeRecipe());
        }
    }

    public static boolean hasCurios(Player player, Item curios) {
        return CuriosAttribute.getDistinctCuriosSet(player).contains(curios);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = hoverMainStyle();
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        Component type = RandomCurios.getTypeDescriptionByTag(stack);
        if (type != null) {
            if (RandomCurios.getFullRateByTag(stack) != 0) {
                components.add(Te.s(type, " v = ", hoverMainStyle(),
                        String.format("%.1f", RandomCurios.getFullRateByTag(stack)), hoverMainStyle()));
            } else {
                components.add(Te.s(type));
            }
        } else {
            if (getTypeDescription() != null) {
                components.add(getTypeDescription());
            }
        }
        int levelRequirement = Utils.levelRequire.getOrDefault(stack.getItem(), 0);
        if (levelRequirement != 0) {
            components.add(Component.literal(" 等级需求: ").withStyle(ChatFormatting.AQUA).
                    append(Component.literal("Lv." + levelRequirement).withStyle(Utils.levelStyleList.get(levelRequirement / 25))));
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        if (!additionHoverText(stack).isEmpty()) {
            ComponentUtils.descriptionOfAddition(components);
            components.addAll(additionHoverText(stack));
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        }
        if (this instanceof Souvenirs souvenirs) {
            components.add(ComponentUtils.getSuffixOfSouvenirs());
            components.add(Te.s("由于" + souvenirs.getReason(),
                    "，这件物品成为了一件纪念品", CustomStyle.styleOfGold));
            components.add(Te.s("Souvenirs-" + souvenirs.getDate(), CustomStyle.styleOfSakura));
        } else {
            components.add(suffix());
        }
        super.appendHoverText(stack, level, components, flag);
    }

    public abstract Component getTypeDescription();

    public abstract List<Component> additionHoverText(ItemStack stack);

    public abstract Style hoverMainStyle();

    public abstract Component suffix();

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return !(this instanceof MoontainCurios);
    }

    public static boolean isOn(Class<? extends Item> clazz, Player player) {
        List<ItemStack> curiosList = CuriosAttribute.getDistinctCuriosList(player);
        return curiosList.stream().anyMatch(itemStack -> itemStack.getItem().getClass() == clazz);
    }

    public static ItemStack isOnWithStack(Class<? extends Item> clazz, Player player) {
        List<ItemStack> curiosList = CuriosAttribute.getDistinctCuriosList(player);
        return curiosList.stream()
                .filter(itemStack -> itemStack.getItem().getClass() == clazz)
                .findFirst()
                .orElse(new ItemStack(Items.AIR));
    }

    public static boolean coolDownOver(Map<String, Integer> map, Player player) {
        return !map.containsKey(player.getName().getString())
                || map.get(player.getName().getString()) < Tick.get();
    }

    public static boolean inLastTime(Map<String, Integer> map, Player player) {
        return map.containsKey(player.getName().getString())
                && map.get(player.getName().getString()) > Tick.get();
    }

    public void tick(Player player) {}
    public void clientTick(Player player) {}

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        if (player.level().isClientSide) {
            clientTick(player);
        }
        else {
            tick(player);
        }
        ICurioItem.super.curioTick(slotContext, stack);
    }

    public static void shrinkOtherModSlot(ServerPlayer serverPlayer) {
        CuriosApi.getCuriosInventory(serverPlayer).ifPresent(handler -> {
            Multimap<String, AttributeModifier> modifiers = ArrayListMultimap.create();
            modifiers.put("feet", new AttributeModifier(
                    "vmd_feet_slot_modifier", -1, AttributeModifier.Operation.ADDITION));
            modifiers.put("mask", new AttributeModifier(
                    "vmd_mask_slot_modifier", -1, AttributeModifier.Operation.ADDITION));
            handler.addTransientSlotModifiers(modifiers);
        });
    }

    public static boolean hasCurio(Player player, Item item) {
        return CuriosAttribute.getDistinctCuriosSet(player).contains(item);
    }

    public static boolean hasCurio(Player player, Class<? extends WraqCurios> clazz) {
        return CuriosAttribute.getDistinctCuriosSet(player)
                .stream()
                .anyMatch(item -> item.getClass().equals(clazz));
    }

    public static class CuriosAttribute {

        public static Map<Player, List<ItemStack>> curiosListCache = new HashMap<>();

        /**
         * 获取玩家去重饰品列表
         */
        public static List<ItemStack> getDistinctCuriosList(Player player) {
            if (!curiosListCache.containsKey(player)) {
                List<ItemStack> curiosList = new ArrayList<>();
                CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                    int size = iCuriosItemHandler.getEquippedCurios().getSlots();
                    Set<Item> curiosItemSet = new HashSet<>();
                    for (int i = 0; i < size; i++) {
                        ItemStack stack = iCuriosItemHandler.getEquippedCurios().getStackInSlot(i);
                        if (stack.is(Items.AIR)) continue;
                        if (!curiosItemSet.contains(stack.getItem())) {
                            if (!(stack.getItem() instanceof RepeatableCurios)) {
                                curiosItemSet.add(stack.getItem());
                            }
                            curiosList.add(stack);
                        }
                    }
                });
                curiosListCache.put(player, curiosList);
            }
            return curiosListCache.get(player);
        }

        @OnlyIn(Dist.CLIENT)
        public static Set<Item> getClientCuriosSet(Player player) {
            Set<Item> set = new HashSet<>();
            CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                int size = iCuriosItemHandler.getEquippedCurios().getSlots();
                for (int i = 0; i < size; i++) {
                    ItemStack stack = iCuriosItemHandler.getEquippedCurios().getStackInSlot(i);
                    set.add(stack.getItem());
                }
            });
            return set;
        }

        public static Map<Player, Set<Item>> curiosSetCache = new HashMap<>();

        public static Set<Item> getDistinctCuriosSet(Player player) {
            if (!curiosSetCache.containsKey(player)) {
                Set<Item> set = new HashSet<>(getDistinctCuriosList(player)
                        .stream().map(itemStack -> (Item) itemStack.getItem())
                        .toList());
                curiosSetCache.put(player, set);
            }
            return curiosSetCache.get(player);
        }

        public static double attributeValue(Player player, Map<Item, Double> attributeMap, String attributeName) {
            if (attributeMap.equals(Utils.defencePenetration) || attributeMap.equals(Utils.manaPenetration)) {
                double rate = 1;
                for (ItemStack curioStack : getDistinctCuriosList(player)) {
                    Item curiosItem = curioStack.getItem();
                    if (attributeMap.containsKey(curiosItem)
                            && player.experienceLevel >= Utils.levelRequire.getOrDefault(curiosItem, 0)) {
                        rate *= (1 - attributeMap.get(curiosItem));
                    }
                    if (attributeName != null) {
                        CompoundTag data = curioStack.getOrCreateTagElement(Utils.MOD_ID);
                        if (data.contains(attributeName)) {
                            if (curiosItem instanceof RandomCurios) {
                                rate *= (1 - data.getDouble(attributeName)
                                        * RandomCuriosAttributesUtil.attributeValueMap.getOrDefault(attributeName, 0d));
                            } else {
                                rate *= (1 - data.getInt(attributeName));
                            }
                        }
                    }
                }
                return 1 - rate;
            } else {
                return getDistinctCuriosList(player).stream()
                        .mapToDouble(stack -> {
                            double value = 0;
                            Item curiosItem = stack.getItem();
                            if (attributeMap.containsKey(curiosItem)
                                    && player.experienceLevel >= Utils.levelRequire.getOrDefault(curiosItem, 0)) {
                                value += attributeMap.get(curiosItem);
                            }
                            if (attributeName != null) {
                                CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);
                                if (data.contains(attributeName)) {
                                    if (curiosItem instanceof RandomCurios) {
                                        value += data.getDouble(attributeName)
                                                * RandomCuriosAttributesUtil.attributeValueMap.getOrDefault(attributeName, 0d);
                                    } else {
                                        value += data.getInt(attributeName);
                                    }
                                }
                            }
                            return value;
                        }).sum();
            }
        }
    }
}
