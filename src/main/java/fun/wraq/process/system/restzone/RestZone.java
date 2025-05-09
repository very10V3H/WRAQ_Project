package fun.wraq.process.system.restzone;

import com.mojang.datafixers.util.Pair;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.security.Security;
import fun.wraq.process.system.bonuschest.BonusChestContent;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

public class RestZone {
    public static final String REST_ZONE_MINUTES = "RestZoneMinutes";

    public static List<BlockPos> restBlockPosList = List.of(
            new BlockPos(1808, 73, 337),
            new BlockPos(1785, 75, 329),
            new BlockPos(1115, 81, 36),
            new BlockPos(1909, 150, -922)
    );

    public static Map<Player, Boolean> inZoneMap = new WeakHashMap<>();
    public static Map<Player, Integer> countMap = new WeakHashMap<>();

    public static void tick(ServerPlayer serverPlayer) {
        if (serverPlayer.tickCount % 40 == 39) {
            if (restBlockPosList.stream()
                    .anyMatch(blockPos -> serverPlayer.position().distanceTo(blockPos.getCenter()) < 16)) {
                if (!inZoneMap.containsKey(serverPlayer)) {
                    inZoneMap.put(serverPlayer, true);
                    Compute.sendFormatMSG(serverPlayer, Te.s("休息", CustomStyle.styleOfLife),
                            Te.s("你已进入休息区域，在此区域休息可以获得", "经验", ChatFormatting.LIGHT_PURPLE,
                                    "/", "VB", CustomStyle.styleOfGold, "/", "随机物品", ChatFormatting.GREEN));
                }
            } else {
                if (inZoneMap.containsKey(serverPlayer)) {
                    inZoneMap.remove(serverPlayer);
                    if (countMap.getOrDefault(serverPlayer, 0) > 0) {
                        Compute.sendFormatMSG(serverPlayer, Te.s("休息", CustomStyle.styleOfLife),
                                Te.s("你已离开休息区域，本次共休息了",
                                        countMap.getOrDefault(serverPlayer, 0) + "分钟", ChatFormatting.GREEN));
                    }
                    countMap.remove(serverPlayer);
                }
            }
        }

        if (serverPlayer.tickCount % 1200 == 39) {
            if (restBlockPosList.stream()
                    .anyMatch(blockPos -> serverPlayer.position().distanceTo(blockPos.getCenter()) < 16)) {
                CompoundTag data = serverPlayer.getPersistentData();
                data.putInt(REST_ZONE_MINUTES, data.getInt(REST_ZONE_MINUTES) + 1);
                countMap.compute(serverPlayer, (k, v) -> v == null ? 1 : v + 1);

                Random random = new Random();
                // 奖励 给予经验 + vb奖励 + 随机物品奖励
                Compute.givePercentExpToPlayer(serverPlayer, 0.02, 0, serverPlayer.experienceLevel);
                Compute.VBIncomeAndMSGSend(serverPlayer, 12);
                if (random.nextDouble() < 0.2) {
                    List<Pair<ItemStack, Integer>> bonusContent = BonusChestContent.getBonusContent(1);
                    Pair<ItemStack, Integer> pair = bonusContent.get(random.nextInt(bonusContent.size()));
                    ItemStack itemStack = pair.getFirst();
                    if (InventoryCheck.containOwnerTag(itemStack)) {
                        InventoryCheck.addOwnerTagToItemStack(serverPlayer, itemStack);
                    }
                    Security.recordItemStream(serverPlayer.getName().getString(), itemStack,
                            Security.RecordType.REST);
                    InventoryOperation.giveItemStackWithMSG(serverPlayer, itemStack);
                }
            }
        }
    }
}
