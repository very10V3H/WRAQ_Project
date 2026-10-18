package fun.wraq.commands.stable.ops;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import fun.wraq.common.fast.Te;
import fun.wraq.process.func.item.ItemAudit;
import fun.wraq.process.system.backpack.audit.BackpackLockManager;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * AI-Generated, 2026-10-18
 * {@code /vmd backpackUuids <player>}：列出某玩家**实际拥有**的精妙背包及其内容 UUID。
 *
 * <p>与精妙背包自带的 {@code /sbp list [playerName]} 不同：那条命令查的是「访问日志」，
 * 也就是「最后一次被谁打开过」，并不是「谁拥有」。本命令直接从玩家的
 * 物品栏 + 饰品栏 + VMD 自定义背包出发，递归展开嵌套背包（Inception 升级），
 * 因此能拿到真正持有的背包、它们的位置、以及是否有重复 UUID。
 *
 * <p>输出的 UUID 可点击，直接执行 {@code /vmd auditBackpack <uuid>} 打开审计界面。
 */
public class BackpackUuidCommand implements Command<CommandSourceStack> {

    public static final BackpackUuidCommand instance = new BackpackUuidCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        Collection<GameProfile> profiles = GameProfileArgument.getGameProfiles(context, "player");
        GameProfile targetProfile = profiles.iterator().next();
        net.minecraft.server.MinecraftServer server = source.getServer();
        ServerPlayer onlineTarget = server.getPlayerList().getPlayer(targetProfile.getId());
        boolean offline = onlineTarget == null;
        String targetName = offline ? targetProfile.getName() : onlineTarget.getName().getString();

        // 在线：直接扫实时物品栏/饰品栏；离线：读 <world>/playerdata/<UUID>.dat 深层扫描
        List<ItemAudit.FoundItem> foundItems = offline
                ? ItemAudit.collectAllOffline(server, targetProfile.getId())
                : ItemAudit.collectAll(onlineTarget);
        Map<UUID, List<String>> uuidToPlaces = new LinkedHashMap<>();
        Map<UUID, ItemStack> uuidToStack = new LinkedHashMap<>();
        int unbound = 0;
        for (ItemAudit.FoundItem foundItem : foundItems) {
            if (!(foundItem.stack().getItem() instanceof BackpackItem)) {
                continue;
            }
            UUID contentsUuid = new BackpackWrapper(foundItem.stack()).getContentsUuid().orElse(null);
            if (contentsUuid == null) {
                unbound++;
                continue;
            }
            uuidToPlaces.computeIfAbsent(contentsUuid, key -> new ArrayList<>()).add(foundItem.where());
            uuidToStack.putIfAbsent(contentsUuid, foundItem.stack());
        }

        ServerPlayer viewer = source.getPlayer();
        final int unboundCount = unbound;
        final boolean offlineMode = offline;
        source.sendSuccess(() -> Te.s("玩家 ", ChatFormatting.WHITE, targetName,
                offlineMode ? "（离线，读取存档）" : "",
                " 持有 ", ChatFormatting.AQUA, String.valueOf(uuidToPlaces.size()),
                " 个已绑定 UUID 的精妙背包", ChatFormatting.WHITE,
                unboundCount > 0 ? "，另有 " + unboundCount + " 个未绑定（空背包）" : ""), false);

        LogUtils.getLogger().info("[VMD-背包审计] {} 查询 {} 的背包 UUID：{}", 
                viewer == null ? "控制台" : viewer.getName().getString(), targetName, uuidToPlaces.keySet());

        if (uuidToPlaces.isEmpty()) {
            source.sendSuccess(() -> Te.s("没有找到任何精妙背包。"), false);
            return 1;
        }

        for (Map.Entry<UUID, List<String>> entry : uuidToPlaces.entrySet()) {
            UUID contentsUuid = entry.getKey();
            List<String> places = entry.getValue();
            ItemStack stack = uuidToStack.get(contentsUuid);
            BackpackLockManager.Lock lock = BackpackLockManager.get(contentsUuid);

            Component uuidComponent = Te.c(
                    Component.literal(contentsUuid.toString()).withStyle(ChatFormatting.AQUA),
                    "/vmd auditBackpack " + contentsUuid,
                    Te.s("位置: ", ChatFormatting.WHITE, places.get(0),
                            places.size() > 1 ? "\n重复出现了 " + places.size() + " 处（疑似复制）" : "",
                            "\n状态: ", ChatFormatting.WHITE,
                            lock == null ? "空闲" : lock.holderName() + " 正在打开",
                            "\n点击打开审计界面"));
            Component line = Te.s(" - ", ChatFormatting.DARK_GRAY,
                    stack.getHoverName(), ChatFormatting.WHITE, " ", uuidComponent);
            source.sendSuccess(() -> line, false);
        }
        return 1;
    }
}
