package fun.wraq.process.system.profession.smith;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

public class SmithPlayerData {
    public static final String SMITH_PLAYER_DATA_KEY = "SmithData";
    public static CompoundTag getSmithData(Player player) {
        return Compute.getPlayerSpecificKeyCompoundTagData(player, SMITH_PLAYER_DATA_KEY);
    }

    public static final String TIER_KEY = "Tier";
    public static int getTier(Player player) {
        return getSmithData(player).getInt(TIER_KEY);
    }

    public static void setTier(Player player, int tier) {
        getSmithData(player).putInt(TIER_KEY, tier);
    }

    public static final String DAILY_REWARD_KEY = "DailyReward";
    public static boolean allowDailyReward(Player player) {
        return getTier(player) > 0 && getSmithData(player).getBoolean(DAILY_REWARD_KEY);
    }

    public static void setDailyReward(Player player, boolean allow) {
        getSmithData(player).putBoolean(DAILY_REWARD_KEY, allow);
    }

    public static void onPlayerSubmitEntrustment(Player player) {
        int tier = getTier(player);
        if (tier == 0) return;
        Item equipPiece5 = ModItems.equipPiece5.get();
        Item equipPiece6 = ModItems.equipPiece6.get();
        if (allowDailyReward(player)) {
            setDailyReward(player, false);
            ItemStack stack;
            if (tier <= 4) {
                stack = new ItemStack(equipPiece5, tier);
            } else {
                stack = new ItemStack(equipPiece6, tier - 4);
            }
            InventoryOperation.giveItemStack(player, stack);
            sendMSG(player, Te.s("你的", "工匠等阶", CustomStyle.styleOfGold,
                    "为你额外提供了", stack, " * " + stack.getCount(), ChatFormatting.AQUA));
        }
        if (tier > 0 && RandomUtils.nextDouble(0, 1) < tier * 0.05) {
            InventoryOperation.giveItemStack(player, new ItemStack(equipPiece5));
            sendMSG(player, Te.s("你的", "工匠等阶", CustomStyle.styleOfGold,
                    "为你额外提供了", equipPiece5, " * 1", ChatFormatting.AQUA));
        }
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("工匠", CustomStyle.styleOfGold), content);
    }

    public static Component getTierDescription(int tier) {
        switch (tier) {
            case 0 -> {
                return Te.s("新生", ChatFormatting.GREEN);
            }
            case 1 -> {
                return Te.s("初等工匠-I", ChatFormatting.AQUA);
            }
            case 2 -> {
                return Te.s("初等工匠-II", ChatFormatting.AQUA);
            }
            case 3 -> {
                return Te.s("中级工匠-I", ChatFormatting.YELLOW);
            }
            case 4 -> {
                return Te.s("中级工匠-II", ChatFormatting.YELLOW);
            }
        }
        return Te.s("新生", ChatFormatting.GREEN);
    }

    public static int getMaxTier() {
        return 4;
    }

    public static void onPlayerInteractWithVillager(Player player) {
        sendMSG(player, Te.s("近来可好？", player));
        Compute.sendBlankLine(player, 3);
        player.sendSystemMessage(Te.s(" ".repeat(4),
                Te.c(
                        Te.s("「提升工匠等阶」", CustomStyle.styleOfGold),
                        "/vmd profession smithTier",
                        Te.s("点击以尝试提升工匠等阶")
                )));
        Compute.sendBlankLine(player, 4);
        MySound.soundToNearPlayer(player, SoundEvents.VILLAGER_AMBIENT);
    }

    public static void tryToIncrementTier(Player player) {
        int currentTier = getTier(player);
        if (currentTier == 0) {
            sendMSG(player, Te.s("你还不是一名", "工匠", CustomStyle.styleOfGold));
            return;
        }
        sendMSG(player, Te.s("当前工匠等阶为: ", getTierDescription(currentTier)));
        Compute.sendBlankLine(player, 1);
        player.sendSystemMessage(Te.s("  升级到下一等阶需要:"));
        List<ItemStack> incrementTierNeedMaterials = getIncrementTierNeedMaterial(currentTier);
        for (int i = 0; i < incrementTierNeedMaterials.size(); i++) {
            ItemStack stack = incrementTierNeedMaterials.get(i);
            player.sendSystemMessage(Te.s("  " + (i + 1) + ". ", CustomStyle.styleOfWorld,
                    stack, " * ", stack.getCount(), ChatFormatting.AQUA));
        }
        Compute.sendBlankLine(player, 2);
        player.sendSystemMessage(Te.s(" ".repeat(4), Te.c(
                Te.s("「提升等阶」", CustomStyle.styleOfGold),
                "/vmd profession incrementSmithTier",
                Te.s("点击以提升工匠等阶")
        )));
        Compute.sendBlankLine(player, 1);
    }

    public static void incrementTier(Player player) {
        int tier = getTier(player);
        if (tier >= getMaxTier()) {
            sendMSG(player, Te.s("已达到了最高的等阶!"));
            MySound.soundToPlayer(player, SoundEvents.VILLAGER_CELEBRATE);
            return;
        }
        if (InventoryOperation.checkPlayerHasItem(player, getIncrementTierNeedMaterial(tier))) {
            InventoryOperation.removeItemWithoutCheck(player, getIncrementTierNeedMaterial(tier));
            InventoryOperation.giveItemStackWithMSG(player, SmithHammer.getHammerByTier(tier));
            Compute.incrementSpecificKeyDataIntValue(player, SMITH_PLAYER_DATA_KEY, TIER_KEY, 1);
            sendMSG(player, Te.s("已达到新的等阶:", getTierDescription(tier + 1)));
            MySound.soundToPlayer(player, SoundEvents.PLAYER_LEVELUP);
        } else {
            sendMSG(player, Te.s("似乎没有足够的材料来提升等阶..."));
            MySound.soundToPlayer(player, SoundEvents.VILLAGER_NO);
        }
    }

    public static List<ItemStack> getIncrementTierNeedMaterial(int currentTier) {
        return List.of(
                new ItemStack(SmithItems.SMITH_STONE.get(), 3 + currentTier - 1),
                new ItemStack(ModItems.GOLDEN_BEANS.get(), (30 + (currentTier - 1) * 15) * 2),
                new ItemStack(SmithHammer.getHammerByTier(currentTier - 1))
        );
    }
}
