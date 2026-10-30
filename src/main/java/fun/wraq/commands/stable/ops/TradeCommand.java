package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.render.gui.villagerTrade.TradeList;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * AI-Generated, 2026-10-30
 * /vmd trade：运行时修改 NPC 的 TradeList.tradeContent 与 tradeRecipeMap。
 * <p>
 * 所有修改仅在当前服务器进程内生效，不写入代码或配置文件。
 */
public final class TradeCommand {

    private TradeCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(Utils.MOD_ID)
                .then(Commands.literal("trade")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.literal("npcs")
                                .executes(TradeCommand::listNpcs))
                        .then(Commands.literal("content")
                                .then(Commands.literal("list")
                                        .then(npcArg().executes(TradeCommand::listContent)))
                                .then(Commands.literal("add")
                                        .then(npcArg()
                                                .then(itemArg()
                                                        .then(countArg()
                                                                .executes(TradeCommand::contentAdd)))))
                                .then(Commands.literal("set")
                                        .then(npcArg()
                                                .then(indexArg()
                                                        .then(itemArg()
                                                                .then(countArg()
                                                                        .executes(TradeCommand::contentSet))))))
                                .then(Commands.literal("remove")
                                        .then(npcArg()
                                                .then(indexArg()
                                                        .executes(TradeCommand::contentRemove)))))
                        .then(Commands.literal("recipe")
                                .then(Commands.literal("list")
                                        .then(npcArg()
                                                .then(productArg()
                                                        .executes(TradeCommand::recipeList))))
                                .then(Commands.literal("add")
                                        .then(npcArg()
                                                .then(productArg()
                                                        .then(itemArg()
                                                                .then(countArg()
                                                                        .executes(TradeCommand::recipeAdd))))))
                                .then(Commands.literal("set")
                                        .then(npcArg()
                                                .then(productArg()
                                                        .then(ingredientArg()
                                                                .then(itemArg()
                                                                        .then(countArg()
                                                                                .executes(TradeCommand::recipeSet)))))))
                                .then(Commands.literal("remove")
                                        .then(npcArg()
                                                .then(productArg()
                                                        .then(ingredientArg()
                                                                .executes(TradeCommand::recipeRemove))))))));
    }

    private static RequiredArgumentBuilder<CommandSourceStack, String> npcArg() {
        return Commands.argument("npc", StringArgumentType.string())
                .suggests(TradeCommand::suggestNpcs);
    }

    private static RequiredArgumentBuilder<CommandSourceStack, Integer> indexArg() {
        return Commands.argument("index", IntegerArgumentType.integer(0));
    }

    private static RequiredArgumentBuilder<CommandSourceStack, Integer> productArg() {
        return Commands.argument("product", IntegerArgumentType.integer(0));
    }

    private static RequiredArgumentBuilder<CommandSourceStack, Integer> ingredientArg() {
        return Commands.argument("ingredient", IntegerArgumentType.integer(0));
    }

    private static RequiredArgumentBuilder<CommandSourceStack, Integer> countArg() {
        return Commands.argument("count", IntegerArgumentType.integer(1, 64));
    }

    private static RequiredArgumentBuilder<CommandSourceStack, String> itemArg() {
        return Commands.argument("item", StringArgumentType.string())
                .suggests(TradeCommand::suggestItems);
    }

    private static int listNpcs(CommandContext<CommandSourceStack> context) {
        TradeList.setTradeContent();
        List<String> npcs = TradeList.tradeContent.keySet().stream().sorted(Comparator.naturalOrder()).toList();
        context.getSource().sendSuccess(() -> Te.s("NPC 交易组共 ", npcs.size(), " 个："), false);
        for (String npc : npcs) {
            context.getSource().sendSuccess(() -> Te.s(" - ", npc, " (", TradeList.tradeContent.get(npc).size(), " 件)"), false);
        }
        return npcs.size();
    }

    private static int listContent(CommandContext<CommandSourceStack> context) {
        String npc = StringArgumentType.getString(context, "npc");
        List<ItemStack> content = getContent(npc);
        if (content == null) {
            return fail(context, "未找到 NPC 交易组: " + npc);
        }
        for (int i = 0; i < content.size(); i++) {
            ItemStack stack = content.get(i);
            int index = i;
            context.getSource().sendSuccess(() -> Te.s(index, ": ",
                    stack.getDisplayName(), " ×", stack.getCount()), false);
        }
        return content.size();
    }

    private static int contentAdd(CommandContext<CommandSourceStack> context) {
        String npc = StringArgumentType.getString(context, "npc");
        List<ItemStack> content = getContent(npc);
        if (content == null) {
            return fail(context, "未找到 NPC 交易组: " + npc);
        }
        ItemStack stack = parseItem(context);
        if (stack == null) {
            return 0;
        }
        List<ItemStack> mutable = new ArrayList<>(content);
        mutable.add(stack);
        TradeList.tradeContent.put(npc, mutable);
        TradeList.tradeRecipeMap.putIfAbsent(stack, new ArrayList<>());
        sync(context, npc);
        context.getSource().sendSuccess(() -> Te.s("已添加商品: ", stack.getDisplayName(), " ×", stack.getCount()), true);
        return 1;
    }

    private static int contentSet(CommandContext<CommandSourceStack> context) {
        String npc = StringArgumentType.getString(context, "npc");
        int index = IntegerArgumentType.getInteger(context, "index");
        List<ItemStack> content = getContent(npc);
        if (content == null || index < 0 || index >= content.size()) {
            return fail(context, "商品索引无效。");
        }
        ItemStack stack = parseItem(context);
        if (stack == null) {
            return 0;
        }
        ItemStack old = content.get(index);
        List<ItemStack> mutable = new ArrayList<>(content);
        mutable.set(index, stack);
        TradeList.tradeContent.put(npc, mutable);
        TradeList.tradeRecipeMap.putIfAbsent(stack, new ArrayList<>());
        TradeList.removeRecipeIfUnused(old);
        sync(context, npc);
        context.getSource().sendSuccess(() -> Te.s("已替换商品 ", index, ": ", stack.getDisplayName(), " ×", stack.getCount()), true);
        return 1;
    }

    private static int contentRemove(CommandContext<CommandSourceStack> context) {
        String npc = StringArgumentType.getString(context, "npc");
        int index = IntegerArgumentType.getInteger(context, "index");
        List<ItemStack> content = getContent(npc);
        if (content == null || index < 0 || index >= content.size()) {
            return fail(context, "商品索引无效。");
        }
        List<ItemStack> mutable = new ArrayList<>(content);
        ItemStack removed = mutable.remove(index);
        TradeList.tradeContent.put(npc, mutable);
        TradeList.removeRecipeIfUnused(removed);
        sync(context, npc);
        context.getSource().sendSuccess(() -> Te.s("已移除商品: ", removed.getDisplayName()), true);
        return 1;
    }

    private static int recipeList(CommandContext<CommandSourceStack> context) {
        String npc = StringArgumentType.getString(context, "npc");
        int index = IntegerArgumentType.getInteger(context, "product");
        List<ItemStack> content = getContent(npc);
        if (content == null || index < 0 || index >= content.size()) {
            return fail(context, "商品索引无效。");
        }
        ItemStack product = content.get(index);
        List<ItemStack> recipe = TradeList.getRecipeCopy(product);
        context.getSource().sendSuccess(() -> Te.s("商品 ", product.getDisplayName(), " 的配方:"), false);
        if (recipe.isEmpty()) {
            context.getSource().sendSuccess(() -> Te.s(" - 无材料"), false);
        }
        for (int i = 0; i < recipe.size(); i++) {
            ItemStack stack = recipe.get(i);
            int ingredientIndex = i;
            context.getSource().sendSuccess(() -> Te.s(" ", ingredientIndex, ": ",
                    stack.getDisplayName(), " ×", stack.getCount()), false);
        }
        return recipe.size();
    }

    private static int recipeAdd(CommandContext<CommandSourceStack> context) {
        String npc = StringArgumentType.getString(context, "npc");
        int index = IntegerArgumentType.getInteger(context, "product");
        List<ItemStack> content = getContent(npc);
        if (content == null || index < 0 || index >= content.size()) {
            return fail(context, "商品索引无效。");
        }
        ItemStack stack = parseItem(context);
        if (stack == null) {
            return 0;
        }
        ItemStack product = content.get(index);
        List<ItemStack> recipe = TradeList.getMutableRecipe(product);
        recipe.add(stack);
        sync(context, npc);
        context.getSource().sendSuccess(() -> Te.s("已为 ", product.getDisplayName(), " 添加材料: ",
                stack.getDisplayName(), " ×", stack.getCount()), true);
        return 1;
    }

    private static int recipeSet(CommandContext<CommandSourceStack> context) {
        String npc = StringArgumentType.getString(context, "npc");
        int productIndex = IntegerArgumentType.getInteger(context, "product");
        List<ItemStack> content = getContent(npc);
        if (content == null || productIndex < 0 || productIndex >= content.size()) {
            return fail(context, "商品索引无效。");
        }
        int ingredientIndex = IntegerArgumentType.getInteger(context, "ingredient");
        ItemStack product = content.get(productIndex);
        List<ItemStack> recipe = TradeList.getMutableRecipe(product);
        if (ingredientIndex < 0 || ingredientIndex >= recipe.size()) {
            return fail(context, "材料索引无效。");
        }
        ItemStack stack = parseItem(context);
        if (stack == null) {
            return 0;
        }
        recipe.set(ingredientIndex, stack);
        sync(context, npc);
        context.getSource().sendSuccess(() -> Te.s("已替换材料 ", ingredientIndex, ": ",
                stack.getDisplayName(), " ×", stack.getCount()), true);
        return 1;
    }

    private static int recipeRemove(CommandContext<CommandSourceStack> context) {
        String npc = StringArgumentType.getString(context, "npc");
        int productIndex = IntegerArgumentType.getInteger(context, "product");
        List<ItemStack> content = getContent(npc);
        if (content == null || productIndex < 0 || productIndex >= content.size()) {
            return fail(context, "商品索引无效。");
        }
        int ingredientIndex = IntegerArgumentType.getInteger(context, "ingredient");
        ItemStack product = content.get(productIndex);
        List<ItemStack> recipe = TradeList.getMutableRecipe(product);
        if (ingredientIndex < 0 || ingredientIndex >= recipe.size()) {
            return fail(context, "材料索引无效。");
        }
        ItemStack removed = recipe.remove(ingredientIndex);
        sync(context, npc);
        context.getSource().sendSuccess(() -> Te.s("已移除材料: ", removed.getDisplayName()), true);
        return 1;
    }

    private static List<ItemStack> getContent(String npc) {
        TradeList.setTradeContent();
        return TradeList.tradeContent.get(npc);
    }

    private static ItemStack parseItem(CommandContext<CommandSourceStack> context) {
        String input = StringArgumentType.getString(context, "item");
        ResourceLocation id = ResourceLocation.tryParse(input);
        if (id == null || ForgeRegistries.ITEMS.getValue(id) == null) {
            context.getSource().sendFailure(Te.s("未找到物品: " + input));
            return null;
        }
        Item item = ForgeRegistries.ITEMS.getValue(id);
        int count = IntegerArgumentType.getInteger(context, "count");
        return new ItemStack(item, count);
    }

    private static void sync(CommandContext<CommandSourceStack> context, String npc) {
        TradeList.syncNpc(context.getSource().getServer(), npc);
    }

    private static int fail(CommandContext<CommandSourceStack> context, String message) {
        context.getSource().sendFailure(Te.s(message));
        return 0;
    }

    private static CompletableFuture<Suggestions> suggestNpcs(
            CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        TradeList.setTradeContent();
        return SharedSuggestionProvider.suggest(TradeList.tradeContent.keySet(), builder);
    }

    private static CompletableFuture<Suggestions> suggestItems(
            CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        List<String> ids = ForgeRegistries.ITEMS.getKeys().stream()
                .map(ResourceLocation::toString)
                .toList();
        return SharedSuggestionProvider.suggest(ids, builder);
    }
}
