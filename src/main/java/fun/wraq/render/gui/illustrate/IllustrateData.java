package fun.wraq.render.gui.illustrate;

import fun.wraq.blocks.blocks.forge.ForgeRecipe;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * AI-Generated, 2025-10-18
 * <p>
 * 图鉴数据整理工具：负责将图鉴分类中的物品按“系列（同一物品的不同等阶 / 同一套装的多个部位）”聚合，
 * 计算系列名称、生成展示用 ItemStack，并提供锻造配方查询（供“来源”窗口展示）。
 */
@OnlyIn(Dist.CLIENT)
public final class IllustrateData {

    public static final int COLUMNS = 2;
    public static final int ROWS = 5;
    public static final int PER_PAGE = COLUMNS * ROWS;

    private static final java.util.regex.Pattern TRAILING_TIER_PATTERN =
            java.util.regex.Pattern.compile("[\\u2070\\u00b9\\u00b2\\u00b3\\u2074-\\u2079]+\\s*$");
    private static final java.util.regex.Pattern DECORATION_PATTERN =
            java.util.regex.Pattern.compile("^[\\s\\-\\u2014\\u00b7:：()（）\\[\\]【】「」『』<>《》的之]+"
                    + "|[\\s\\-\\u2014\\u00b7:：()（）\\[\\]【】「」『』<>《》的之]+$");
    /** 装备部位后缀：用于把同一套装的 头盔/胸甲/护腿/靴子 归为一个系列。 */
    private static final java.util.regex.Pattern ARMOR_SLOT_PATTERN = java.util.regex.Pattern.compile(
            "(helmet|helm|head|chestplate|chest|body|leggings|legs|pants|boots|shoes|feet)$");
    /** 部位名不规则、无法从名称推断套装名时的固定名称，key 为去掉部位后的注册名前缀。 */
    private static final Map<String, String> ARMOR_SET_NAME_OVERRIDES = Map.of(
            "netherarmor", "下界套装",
            "nether_mana_", "混沌下界套装",
            "ilarmor", "激化唤雷套装"
    );
    /** 套装图标优先使用胸甲。 */
    private static final java.util.regex.Pattern CHEST_SLOT_PATTERN =
            java.util.regex.Pattern.compile("(chestplate|chest|body)$");
    /** 这些通用类别词不能作为“名称后缀家族”的系列名，避免把整类物品合并。 */
    private static final List<String> SERIES_SUFFIX_BLACKLIST = List.of(
            "药水", "补给包", "奖励箱", "之心", "宝石", "护符", "项链",
            "头盔", "胸甲", "护腿", "靴子", "剑", "弓", "杖", "锤",
            "币", "羊毛", "锭", "焦炭", "套装", "碎片", "券", "饰品", "材料");

    /** 图鉴中的一条目：可以是单个物品，也可以是同一系列的多个等阶 / 同一套装的多个部位。 */
    public record Entry(List<Item> members, Component displayName, ItemStack icon,
                        boolean series, List<ItemStack> seriesItems, boolean hasSource) {
    }

    /** 聚合键：key 用于分组，armorSet 标记“套装”以便生成“XX套装”的名称。 */
    private record GroupKey(String key, boolean armorSet) {
    }

    private IllustrateData() {
    }

    // ------------------------------------------------------------------
    // 系列聚合
    // ------------------------------------------------------------------

    /**
     * 将分类物品聚合为图鉴条目。
     * <p>聚合优先级：
     * <ol>
     *     <li>注册名去掉结尾数字后相同：同一物品的不同等阶（如 plainsword0~3）；</li>
     *     <li>注册名去掉部位后缀后相同：同一套装的 头盔/胸甲/护腿/靴子；</li>
     *     <li>名称共享同一后缀：同一材料/物品系列（如“水竹行军刀/苍天行军刀”→ 行军刀、“生机元素水晶/碧水元素水晶…”→ 元素水晶）；</li>
     *     <li>显示名去掉等阶/评级标记后相同：同一物品的不同阶/评级（如“见习魔法师的笔记¹”、银龙刺客之击 𝘼~𝙀）。</li>
     * </ol>
     */
    public static List<Entry> buildEntries(List<Item> items) {
        ensureRecipes();
        Map<Item, GroupKey> groupKeys = computeGroupKeys(items);
        Map<String, List<Item>> groups = new LinkedHashMap<>();
        Map<String, Boolean> armorSetFlags = new LinkedHashMap<>();
        for (Item item : items) {
            GroupKey groupKey = groupKeys.get(item);
            List<Item> group = groups.computeIfAbsent(groupKey.key(), k -> new ArrayList<>());
            if (!group.contains(item)) {
                group.add(item);
            }
            armorSetFlags.putIfAbsent(groupKey.key(), groupKey.armorSet());
        }
        List<Entry> entries = new ArrayList<>(groups.size());
        for (Map.Entry<String, List<Item>> groupEntry : groups.entrySet()) {
            List<Item> members = groupEntry.getValue();
            boolean series = members.size() > 1;
            boolean armorSet = series && Boolean.TRUE.equals(armorSetFlags.get(groupEntry.getKey()));

            // 防具套装默认使用胸甲作为图标与名称样式来源
            Item styleBase = members.get(0);
            int iconIndex = 0;
            if (armorSet) {
                for (int index = 0; index < members.size(); index++) {
                    if (isChestPiece(pathOf(members.get(index)))) {
                        iconIndex = index;
                        styleBase = members.get(index);
                        break;
                    }
                }
            }

            Component displayName;
            if (armorSet) {
                displayName = styledName(armorSetName(members, groupEntry.getKey()), styleBase);
            } else if (groupEntry.getKey().startsWith("family:")) {
                displayName = styledName(
                        groupEntry.getKey().substring("family:".length()), styleBase);
            } else if (series) {
                displayName = styledName(seriesName(members), styleBase);
            } else {
                displayName = displayNameOf(styleBase);
            }

            List<ItemStack> seriesItems = new ArrayList<>(members.size());
            for (Item item : members) {
                seriesItems.add(prepareStack(item));
            }
            entries.add(new Entry(List.copyOf(members), displayName, seriesItems.get(iconIndex),
                    series, seriesItems, ItemSourceInfo.hasSource(members)));
        }
        return entries;
    }

    /** 物品的展示名称：保留物品自身名称的样式，并补上稀有度带来的颜色/字体。 */
    public static Component displayNameOf(Item item) {
        ItemStack stack = item.getDefaultInstance();
        MutableComponent component = Component.empty().append(stack.getHoverName());
        component.withStyle(stack.getRarity().getStyleModifier());
        if (stack.hasCustomHoverName()) {
            component.withStyle(ChatFormatting.ITALIC);
        }
        return component;
    }

    /** 系列名称：使用代表物品的名称样式（稀有度颜色、字体等），避免图鉴里全部显示成白色。 */
    public static Component styledName(String text, Item base) {
        ItemStack stack = base.getDefaultInstance();
        MutableComponent component = Component.literal(text);
        component.withStyle(stack.getRarity().getStyleModifier());
        component.withStyle(stack.getHoverName().getStyle());
        if (stack.hasCustomHoverName()) {
            component.withStyle(ChatFormatting.ITALIC);
        }
        return component;
    }

    private static Map<Item, GroupKey> computeGroupKeys(List<Item> items) {
        Map<Item, GroupKey> groupKeys = new LinkedHashMap<>();

        // 1) 名称后缀家族（优先，可把同族的多个等阶系列合并，如七系元素水晶）
        Map<Item, String> familyKeys = computeFamilyKeys(items);
        List<Item> remaining = new ArrayList<>();
        for (Item item : items) {
            String familyKey = familyKeys.get(item);
            if (familyKey != null) {
                groupKeys.put(item, new GroupKey(familyKey, false));
            } else {
                remaining.add(item);
            }
        }

        // 2) 等阶：注册名去掉结尾数字后相同
        Map<String, Integer> tierCounts = new LinkedHashMap<>();
        Map<Item, String> tierStems = new LinkedHashMap<>();
        for (Item item : remaining) {
            String stem = pathOf(item).replaceAll("\\d+$", "");
            tierStems.put(item, stem);
            tierCounts.merge(stem, 1, Integer::sum);
        }
        List<Item> afterTier = new ArrayList<>();
        for (Item item : remaining) {
            String stem = tierStems.get(item);
            if (tierCounts.getOrDefault(stem, 0) > 1) {
                groupKeys.put(item, new GroupKey("tier:" + stem, false));
            } else {
                afterTier.add(item);
            }
        }

        // 3) 套装：注册名去掉部位后缀后相同
        Map<String, Integer> armorCounts = new LinkedHashMap<>();
        Map<Item, String> armorStems = new LinkedHashMap<>();
        for (Item item : afterTier) {
            String stem = armorStemOf(pathOf(item));
            if (stem != null) {
                armorStems.put(item, stem);
                armorCounts.merge(stem, 1, Integer::sum);
            }
        }
        List<Item> afterArmor = new ArrayList<>();
        for (Item item : afterTier) {
            String stem = armorStems.get(item);
            if (stem != null && armorCounts.getOrDefault(stem, 0) > 1) {
                groupKeys.put(item, new GroupKey("armor:" + stem, true));
            } else {
                afterArmor.add(item);
            }
        }

        // 4) 显示名去掉等阶/评级标记后相同
        Map<String, Integer> nameCounts = new LinkedHashMap<>();
        Map<Item, String> nameKeys = new LinkedHashMap<>();
        for (Item item : afterArmor) {
            String normalized = normalizeSeriesName(item.getDefaultInstance().getHoverName().getString());
            if (normalized.length() >= 2) {
                nameKeys.put(item, normalized);
                nameCounts.merge(normalized, 1, Integer::sum);
            }
        }
        for (Item item : afterArmor) {
            String normalized = nameKeys.get(item);
            if (normalized != null && nameCounts.getOrDefault(normalized, 0) > 1) {
                groupKeys.put(item, new GroupKey("name:" + normalized, false));
            } else {
                groupKeys.put(item, new GroupKey("single:" + pathOf(item), false));
            }
        }
        return groupKeys;
    }

    /**
     * 名称后缀家族：按注册名最后一段（去掉结尾数字）分组，在同一组内寻找
     * “各成员名称共有的后缀”，且每个成员名称去掉该后缀后的部分长度为 2~3（即地域/属性等短前缀）。
     * <p>例如：life_crystal0/water_crystal0… -> 元素水晶。
     */
    private static Map<Item, String> computeFamilyKeys(List<Item> items) {
        Map<String, List<Item>> tokenGroups = new LinkedHashMap<>();
        for (Item item : items) {
            tokenGroups.computeIfAbsent(registryTokenOf(item), key -> new ArrayList<>()).add(item);
        }
        Map<Item, String> familyKeys = new LinkedHashMap<>();
        for (List<Item> tokenGroup : tokenGroups.values()) {
            List<Item> group = new ArrayList<>(tokenGroup);
            Map<Item, String> normalizedNames = new LinkedHashMap<>();
            for (Item item : group) {
                normalizedNames.put(item,
                        normalizeSeriesName(item.getDefaultInstance().getHoverName().getString()));
            }
            // 同一 token 组内可能有多个家族（如 *_gem_o 同时包含“意志玛瑙/意志晶钻/化炽焰石”），逐个提取
            while (group.size() >= 3) {
                String bestSuffix = findFamilySuffix(group, normalizedNames);
                if (bestSuffix == null) {
                    break;
                }
                List<Item> covered = new ArrayList<>();
                for (Item item : group) {
                    if (normalizedNames.get(item).endsWith(bestSuffix)) {
                        familyKeys.put(item, "family:" + bestSuffix);
                        covered.add(item);
                    }
                }
                group.removeAll(covered);
            }
        }
        return familyKeys;
    }

    private static String findFamilySuffix(List<Item> group, Map<Item, String> normalizedNames) {
        String bestSuffix = null;
        int bestCount = 0;
        for (Item candidate : group) {
            String name = normalizedNames.get(candidate);
            for (int length = name.length(); length >= 3; length--) {
                String suffix = name.substring(name.length() - length);
                if (!isValidSeriesSuffix(suffix)) {
                    continue;
                }
                int count = 0;
                boolean valid = true;
                Set<String> prefixes = new LinkedHashSet<>();
                for (Item other : group) {
                    String otherName = normalizedNames.get(other);
                    if (!otherName.endsWith(suffix)) {
                        continue;
                    }
                    String prefix = trimDecorations(
                            otherName.substring(0, otherName.length() - length));
                    if (prefix.length() < 2 || prefix.length() > 3) {
                        valid = false;
                        break;
                    }
                    prefixes.add(prefix);
                    count++;
                }
                if (valid && prefixes.size() >= 2 && count >= 3
                        && (count > bestCount
                        || (count == bestCount && suffix.length() > (bestSuffix == null ? 0 : bestSuffix.length())))) {
                    bestSuffix = suffix;
                    bestCount = count;
                }
            }
        }
        return bestSuffix;
    }

    private static String registryTokenOf(Item item) {
        String stripped = pathOf(item).replaceAll("\\d+$", "");
        int underscore = stripped.lastIndexOf('_');
        return underscore >= 0 ? stripped.substring(underscore + 1) : stripped;
    }

    /** 后缀不能是通用物品类别词，避免把药水、补给包等整类合并。 */
    private static boolean isValidSeriesSuffix(String suffix) {
        if (suffix.length() < 3) {
            return false;
        }
        if (suffix.contains(" - ")) {
            return false;
        }
        if (DECORATION_PATTERN.matcher(suffix.substring(0, 1)).find()
                || DECORATION_PATTERN.matcher(suffix.substring(suffix.length() - 1)).find()) {
            return false;
        }
        for (String blocked : SERIES_SUFFIX_BLACKLIST) {
            if (suffix.contains(blocked)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 去除名称结尾的等阶/评级标记（上标数字、数学粗斜体字母 𝘼~𝙀、罗马数字、撇号）。
     */
    private static String normalizeSeriesName(String name) {
        String result = name.trim();
        result = TRAILING_TIER_PATTERN.matcher(result).replaceAll("");
        result = result.replaceAll("[\\x{1D63C}-\\x{1D640}]+$", "");
        result = result.replaceAll("(?i)\\s*[\\-\\u2014]?\\s*(?:I{1,3}|IV|VI{0,2}|IX|X)$", "");
        result = result.replaceAll("[\\u2032\\u2033']+$", "");
        return result.trim();
    }

    private static String pathOf(Item item) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        return id == null ? item.getDescriptionId() : id.getPath();
    }

    /**
     * 套装前缀：注册名去掉阶数/单字母强化后缀（如 life_armor_helmet_e 的 _e）后，
     * 若以部位词结尾则返回其前缀（life_armor_）。
     */
    private static String armorStemOf(String path) {
        String base = path.replaceAll("\\d+$", "");
        java.util.regex.Matcher slotMatcher = ARMOR_SLOT_PATTERN.matcher(base);
        if (!slotMatcher.find() || slotMatcher.start() <= 0) {
            String stripped = base.replaceAll("[_\\-][a-z]$", "");
            if (stripped.equals(base)) {
                return null;
            }
            base = stripped;
            slotMatcher = ARMOR_SLOT_PATTERN.matcher(base);
            if (!slotMatcher.find() || slotMatcher.start() <= 0) {
                return null;
            }
        }
        return base.substring(0, slotMatcher.start());
    }

    private static boolean isChestPiece(String path) {
        String base = path.replaceAll("\\d+$", "");
        if (!CHEST_SLOT_PATTERN.matcher(base).find()) {
            String stripped = base.replaceAll("[_\\-][a-z]$", "");
            if (stripped.equals(base)) {
                return false;
            }
            base = stripped;
        }
        return CHEST_SLOT_PATTERN.matcher(base).find();
    }

    /** 物品自身名称（去除结尾的等阶上标）。 */
    public static String plainName(Item item) {
        return TRAILING_TIER_PATTERN.matcher(
                item.getDefaultInstance().getHoverName().getString()).replaceAll("").trim();
    }

    /**
     * 系列名称：取所有等阶名称的最长公共前缀或最长公共后缀，
     * 例如“平原短匕 / 平原短匕¹ / 平原短匕²” -> “平原短匕”，
     * “粗糙锻造碎片 / 精良锻造碎片” -> “锻造碎片”。
     */
    private static String seriesName(List<Item> members) {
        List<String> names = new ArrayList<>(members.size());
        for (Item item : members) {
            names.add(plainName(item));
        }
        // 优先使用出现次数占多数的名称（如 生机法杖 ×4 + 生机权杖-X ×1 -> 生机法杖）
        Map<String, Integer> nameCounts = new LinkedHashMap<>();
        for (String name : names) {
            nameCounts.merge(name, 1, Integer::sum);
        }
        String majority = null;
        int majorityCount = 0;
        for (Map.Entry<String, Integer> entry : nameCounts.entrySet()) {
            if (entry.getValue() > majorityCount) {
                majority = entry.getKey();
                majorityCount = entry.getValue();
            }
        }
        if (majority != null && majorityCount * 2 > names.size() && majority.length() >= 2) {
            return majority;
        }
        String prefix = commonPrefix(names);
        String suffix = commonSuffix(names);
        String best = prefix.length() >= suffix.length() ? prefix : suffix;
        best = trimDecorations(best);
        if (best.length() >= 2) {
            return best;
        }
        return names.get(0);
    }

    /**
     * 套装名称：优先使用固定名称；否则取各部位名称的公共前缀（如“平原头盔…” -> “平原套装”）；
     * 公共前缀过短时取超过半数部位共享的前缀（如“矿洞护腿/矿洞胸甲/矿洞靴子/矿工裤/铁铁铁铁铁头” -> “矿洞套装”）。
     */
    private static String armorSetName(List<Item> members, String key) {
        String stem = key.startsWith("armor:") ? key.substring("armor:".length()) : key;
        String override = ARMOR_SET_NAME_OVERRIDES.get(stem);
        if (override != null) {
            return override;
        }
        List<String> names = new ArrayList<>(members.size());
        for (Item item : members) {
            names.add(plainName(item).replaceAll("^<[^>]*>", "").trim());
        }
        String prefix = trimDecorations(commonPrefix(names));
        if (prefix.length() < 2) {
            prefix = trimDecorations(majorityPrefix(names));
        }
        if (prefix.length() >= 2) {
            return prefix + "套装";
        }
        String first = names.get(0);
        return first.endsWith("套装") ? first : first + "套装";
    }

    private static String trimDecorations(String value) {
        return DECORATION_PATTERN.matcher(value).replaceAll("").trim();
    }

    private static String majorityPrefix(List<String> names) {
        if (names.isEmpty()) {
            return "";
        }
        int half = (names.size() + 1) / 2;
        Set<String> candidates = new LinkedHashSet<>();
        for (String name : names) {
            for (int length = name.length(); length >= 2; length--) {
                candidates.add(name.substring(0, length));
            }
        }
        String best = "";
        for (String candidate : candidates) {
            if (candidate.length() <= best.length()) {
                continue;
            }
            int count = 0;
            for (String name : names) {
                if (name.startsWith(candidate)) {
                    count++;
                }
            }
            if (count >= half) {
                best = candidate;
            }
        }
        return best;
    }

    private static String commonPrefix(List<String> names) {
        String prefix = names.get(0);
        for (String name : names) {
            int length = Math.min(prefix.length(), name.length());
            int i = 0;
            while (i < length && prefix.charAt(i) == name.charAt(i)) {
                i++;
            }
            prefix = prefix.substring(0, i);
            if (prefix.isEmpty()) {
                break;
            }
        }
        return prefix;
    }

    private static String commonSuffix(List<String> names) {
        String suffix = names.get(0);
        for (String name : names) {
            int length = Math.min(suffix.length(), name.length());
            int i = 0;
            while (i < length
                    && suffix.charAt(suffix.length() - 1 - i) == name.charAt(name.length() - 1 - i)) {
                i++;
            }
            suffix = suffix.substring(suffix.length() - i);
            if (suffix.isEmpty()) {
                break;
            }
        }
        return suffix;
    }

    // ------------------------------------------------------------------
    // 展示用 ItemStack
    // ------------------------------------------------------------------

    /**
     * 生成图鉴中展示用的 ItemStack：保留物品原有特性（品质滚动、强化信息等），
     * 并打上图鉴展示标记，使 tooltip 可以显示“获取途径”等图鉴专属内容。
     */
    public static ItemStack prepareStack(Item item) {
        ItemStack itemStack = item.getDefaultInstance();
        itemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        data.putBoolean(Illustrate.DISPLAY_FLAG, true);
        if (!Screen.hasControlDown()) {
            boolean equip = Utils.mainHandTag.containsKey(item)
                    || Utils.armorTag.containsKey(item)
                    || Utils.offHandTag.containsKey(item)
                    || item instanceof WraqCurios;
            if (equip) {
                if (!Utils.offHandTag.containsKey(item) && !(item instanceof WraqCurios)) {
                    ForgeEquipUtils.setForgeQualityOnEquip(itemStack,
                            ClientUtils.clientPlayerTick / 20 % 13);
                }
                Compute.forgingHoverName(itemStack);
            }
        }
        return itemStack;
    }

    // ------------------------------------------------------------------
    // 制作配方
    // ------------------------------------------------------------------

    private static boolean recipesReady = false;

    private static void ensureRecipes() {
        if (recipesReady) {
            return;
        }
        recipesReady = true;
        if (ForgeRecipe.recipes.isEmpty()) {
            ForgeRecipe.forgeDrawRecipeInit();
        }
    }

    /**
     * 获取物品的锻造配方材料；没有锻造配方时返回空列表。
     * 供图鉴「来源」窗口展示完整制作材料。
     */
    public static List<ItemStack> getForgeRecipe(Item item) {
        ensureRecipes();
        List<ItemStack> materials = ForgeRecipe.recipes.get(item);
        if (materials == null && item instanceof ForgeItem forgeItem) {
            List<ItemStack> recipe = forgeItem.forgeRecipe();
            if (recipe != null && !recipe.isEmpty()) {
                materials = recipe;
            }
        }
        return materials == null ? List.of() : materials;
    }
}
