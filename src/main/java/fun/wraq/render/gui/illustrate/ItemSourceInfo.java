package fun.wraq.render.gui.illustrate;

import fun.wraq.blocks.blocks.inject.InjectRecipe;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.struct.InjectingRecipe;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * AI-Generated, 2025-10-18
 * <p>
 * 「来源」数据整理：在图鉴中检索每个物品在代码中的制作来源并组织为可展示的文本行：
 * <ol>
 *     <li>锻造配方：锻造地点 + 完整材料需求（带材料图标，可悬停查看获取途径）；</li>
 *     <li>灌注配方：基底物品与灌注材料；</li>
 *     <li>获取途径：由 {@code material_sources.json}（代码检索生成的固定资产）提供的掉落/商店等来源；</li>
 *     <li>以上均未检索到时给出明确提示。</li>
 * </ol>
 */
@OnlyIn(Dist.CLIENT)
public final class ItemSourceInfo {

    /** 一条来源信息：icon 非空时以「图标 + 文本」形式展示材料，否则为纯文本行。 */
    public record Line(Component text, ItemStack icon) {
    }

    private ItemSourceInfo() {
    }

    /** 是否在代码中检索到任何来源（锻造 / 灌注 / 获取途径）；没有来源的条目不显示「来源」按钮。 */
    public static boolean hasSource(List<Item> members) {
        for (Item item : members) {
            if (!IllustrateData.getForgeRecipe(item).isEmpty()) {
                return true;
            }
            if (findInjectRecipe(item) != null) {
                return true;
            }
            if (!MaterialSourceData.get(item).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /** 为图鉴条目（可能包含同一系列的多个等阶）生成来源信息。 */
    public static List<Line> build(List<Item> members) {        // 先按「来源内容完全相同」合并（同来源的等阶只展示一遍）
        Map<String, List<Item>> blockGroups = new LinkedHashMap<>();
        Map<String, List<Line>> blocks = new LinkedHashMap<>();
        for (Item member : members) {
            List<Line> block = buildForItem(member);
            String key = blockKey(block);
            blockGroups.computeIfAbsent(key, k -> new ArrayList<>()).add(member);
            blocks.putIfAbsent(key, block);
        }

        // 含锻造/灌注配方的条目必须保留“产物 ↔ 基底/材料”的对应关系，按物品逐个分节展示；
        // 仅包含获取途径（掉落/商店等）的条目才按同名合并，避免探路者等出现多个同名标题。
        boolean hasRecipe = false;
        for (List<Line> block : blocks.values()) {
            if (containsRecipeLine(block)) {
                hasRecipe = true;
                break;
            }
        }
        List<Line> result = new ArrayList<>();
        if (hasRecipe) {
            if (blockGroups.size() == 1) {
                result.addAll(blocks.values().iterator().next());
                return result;
            }
            for (Map.Entry<String, List<Item>> entry : blockGroups.entrySet()) {
                List<Item> same = entry.getValue();
                Item first = same.get(0);
                MutableComponent header = headerOf(
                        first.getDefaultInstance().getHoverName().getString(), first);
                if (same.size() > 1) {
                    header.append(Component.literal(" 等" + same.size() + "件").withStyle(ChatFormatting.GOLD));
                }
                result.add(new Line(header, null));
                result.addAll(blocks.get(entry.getKey()));
            }
            return result;
        }

        Map<String, List<Line>> merged = new LinkedHashMap<>();
        Map<String, Integer> mergedCounts = new LinkedHashMap<>();
        Map<String, Item> mergedBases = new LinkedHashMap<>();
        for (Map.Entry<String, List<Item>> entry : blockGroups.entrySet()) {
            String groupName = groupNameOf(entry.getValue().get(0));
            mergedBases.putIfAbsent(groupName, entry.getValue().get(0));
            List<Line> target = merged.computeIfAbsent(groupName, k -> new ArrayList<>());
            for (Line line : blocks.get(entry.getKey())) {
                boolean duplicate = false;
                for (Line existing : target) {
                    if (existing.text().getString().equals(line.text().getString())) {
                        duplicate = true;
                        break;
                    }
                }
                if (!duplicate) {
                    target.add(line);
                }
            }
            mergedCounts.merge(groupName, entry.getValue().size(), Integer::sum);
        }
        if (merged.size() == 1) {
            result.addAll(merged.values().iterator().next());
            return result;
        }
        for (Map.Entry<String, List<Line>> entry : merged.entrySet()) {
            int count = mergedCounts.getOrDefault(entry.getKey(), 1);
            MutableComponent header = headerOf(entry.getKey(), mergedBases.get(entry.getKey()));
            if (count > 1) {
                header.append(Component.literal(" 等" + count + "件").withStyle(ChatFormatting.GOLD));
            }
            result.add(new Line(header, null));
            result.addAll(entry.getValue());
        }
        return result;
    }

    /** 分节标题：保留物品名称自身的颜色/字体。 */
    private static MutableComponent headerOf(String text, Item base) {
        MutableComponent header = Component.literal("【").withStyle(ChatFormatting.GOLD);
        Component styled = base == null ? Component.literal(text) : IllustrateData.styledName(text, base);
        header.append(styled);
        header.append(Component.literal("】").withStyle(ChatFormatting.GOLD));
        return header;
    }

    private static boolean containsRecipeLine(List<Line> block) {
        for (Line line : block) {
            if (line.icon() != null) {
                return true;
            }
            String text = line.text().getString();
            if (text.startsWith("锻造") || text.startsWith("灌注")
                    || text.startsWith("材料需求") || text.startsWith("可在锻造台重铸")) {
                return true;
            }
        }
        return false;
    }

    private static String blockKey(List<Line> block) {
        StringBuilder builder = new StringBuilder();
        for (Line line : block) {
            builder.append(line.text().getString()).append('\u0001');
        }
        return builder.toString();
    }

    /** 分组标题用名称：去掉上标等阶与 𝘼~𝙀 评级字母。 */
    private static String groupNameOf(Item item) {
        String name = IllustrateData.plainName(item);
        name = name.replaceAll("[\\x{1D63C}-\\x{1D640}]+$", "");
        return name.trim();
    }

    private static List<Line> buildForItem(Item item) {
        List<Line> lines = new ArrayList<>();

        // 1. 锻造配方
        List<ItemStack> recipe = IllustrateData.getForgeRecipe(item);
        boolean reforged = recipe.size() == 1
                && recipe.get(0).is(ModItems.COLLEGE_SENIOR_EQUIP_TICKET.get());
        if (reforged) {
            lines.add(new Line(text("可在锻造台重铸（无需材料）"), null));
        } else if (!recipe.isEmpty()) {
            List<Component> places = ForgeEquipUtils.itemForgePlaceMap.get(item);
            if (places != null && !places.isEmpty()) {
                lines.add(placeLine(places));
            } else {
                lines.add(new Line(text("在锻造台锻造"), null));
            }
            lines.add(new Line(text("材料需求："), null));
            for (ItemStack material : recipe) {
                lines.add(materialLine("  ", material));
            }
        }

        // 2. 灌注配方
        InjectingRecipe injecting = findInjectRecipe(item);
        if (injecting != null) {
            Item sourceItem = findInjectSourceItem(injecting);
            if (sourceItem != null) {
                lines.add(new Line(text("灌注：以「" + sourceItem.getDefaultInstance().getHoverName().getString()
                        + "」×" + injecting.getSourceItemCount() + " 为基底"), null));
            }
            ItemStack material = new ItemStack(injecting.getMaterial());
            lines.add(materialLine("灌注材料：", material, injecting.getMaterialCount()));
        }

        // 3. 获取途径（material_sources.json）
        for (String source : MaterialSourceData.get(item)) {
            if (source.equals("在锻造台锻造获得") && !recipe.isEmpty()) {
                continue;
            }
            lines.add(new Line(text("· " + source), null));
        }

        if (lines.isEmpty()) {
            lines.add(new Line(Component.literal("代码中暂未检索到明确的获取/制作来源")
                    .withStyle(ChatFormatting.DARK_GRAY), null));
        }
        return lines;
    }

    private static Line placeLine(List<Component> places) {
        MutableComponent line = Component.literal("锻造地点：").withStyle(ChatFormatting.DARK_GRAY);
        for (int index = 0; index < places.size(); index++) {
            line.append(Component.literal(places.get(index).getString())
                    .withStyle(ChatFormatting.DARK_GRAY));
            if (index != places.size() - 1) {
                line.append(Component.literal("、").withStyle(ChatFormatting.DARK_GRAY));
            }
        }
        return new Line(line, null);
    }

    private static Line materialLine(String prefix, ItemStack material) {
        return materialLine(prefix, material, material.getCount());
    }

    private static Line materialLine(String prefix, ItemStack material, int count) {
        MutableComponent line = Component.literal(prefix).withStyle(ChatFormatting.DARK_GRAY);
        line.append(Component.literal(material.getHoverName().getString())
                .withStyle(ChatFormatting.DARK_GRAY));
        line.append(Component.literal(" ×" + count).withStyle(ChatFormatting.DARK_AQUA));
        return new Line(line, IllustrateData.prepareStack(material.getItem()));
    }

    private static Component text(String value) {
        return Component.literal(value).withStyle(ChatFormatting.DARK_GRAY);
    }

    private static Map<Item, InjectingRecipe> injectProductMap;
    private static final Map<InjectingRecipe, Item> INJECT_SOURCE_MAP = new java.util.IdentityHashMap<>();

    private static void ensureInjectRecipes() {
        if (injectProductMap != null) {
            return;
        }
        if (InjectRecipe.injectingRecipeMap.isEmpty()) {
            InjectRecipe.setInjectingRecipeMap();
        }
        injectProductMap = new HashMap<>();
        for (Map.Entry<Item, InjectingRecipe> entry : InjectRecipe.injectingRecipeMap.entrySet()) {
            injectProductMap.put(entry.getValue().getProduct(), entry.getValue());
            INJECT_SOURCE_MAP.put(entry.getValue(), entry.getKey());
        }
    }

    private static InjectingRecipe findInjectRecipe(Item item) {
        ensureInjectRecipes();
        return injectProductMap.get(item);
    }

    private static Item findInjectSourceItem(InjectingRecipe recipe) {
        ensureInjectRecipes();
        return INJECT_SOURCE_MAP.get(recipe);
    }
}
