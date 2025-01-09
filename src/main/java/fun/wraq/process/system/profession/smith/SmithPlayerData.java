package fun.wraq.process.system.profession.smith;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.profession.pet.allay.AllayPetPlayerData;
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

    public static void onPlayerSubmitEntrustment(Player player) {
        int tier = getTier(player);
        Item equipPiece5 = ModItems.equipPiece5.get();
        Item equipPiece6 = ModItems.equipPiece6.get();
        if (allowDailyReward(player)) {
            ItemStack stack;
            if (tier <= 4) {
                stack = new ItemStack(equipPiece5, tier);
            } else {
                stack = new ItemStack(equipPiece6, tier);
            }
            InventoryOperation.itemStackGive(player, stack);
            sendMSG(player, Te.s("你的", "工匠等阶", CustomStyle.styleOfGold,
                    "为你额外提供了", stack, " * " + stack.getCount(), ChatFormatting.AQUA));
        }
        if (tier > 0 && RandomUtils.nextDouble(0, 1) < tier * 0.05) {
            InventoryOperation.itemStackGive(player, new ItemStack(equipPiece5));
        }
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("工匠", CustomStyle.styleOfGold), content);
    }

    public static Component getTierDescription(int tier) {
        return AllayPetPlayerData.getAllayTierDescription(tier - 1);
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
        sendMSG(player, Te.s("当前等阶为: ", getTierDescription(currentTier)));
        player.sendSystemMessage(Te.s("  升级到下一等阶需要:"));
        List<ItemStack> incrementTierNeedMaterials = getIncrementTierNeedMaterial(currentTier);
        for (int i = 0; i < incrementTierNeedMaterials.size(); i++) {
            ItemStack stack = incrementTierNeedMaterials.get(i);
            player.sendSystemMessage(Te.s("  " + (i + 1) + ". ", CustomStyle.styleOfWorld,
                    stack, " * ", stack.getCount(), ChatFormatting.AQUA));
        }
        sendMSG(player, Te.s(
                Te.c(
                        Te.s("提升等阶"),
                        "/vmd profession incrementSmithTier",
                        Te.s("点击以提升工匠等阶")
                )));
    }

    public static void incrementTier(Player player) {
        int tier = getTier(player);
        if (tier >= getMaxTier()) {
            sendMSG(player, Te.s("已达到了最高的等阶!"));
            return;
        }
        if (InventoryOperation.checkPlayerHasItem(player, getIncrementTierNeedMaterial(tier))) {
            InventoryOperation.removeItemWithoutCheck(player, getIncrementTierNeedMaterial(tier));
            InventoryOperation.itemStackGiveWithMSG(player, SmithHammer.getHammerByTier(tier + 1));
            Compute.incrementSpecificKeyDataIntValue(player, SMITH_PLAYER_DATA_KEY, TIER_KEY, 1);
            sendMSG(player, Te.s("已达到新的等阶:", getTierDescription(tier + 1)));
        } else {
            sendMSG(player, Te.s("似乎没有足够的材料来提升等阶..."));
        }
    }

    public static List<ItemStack> getIncrementTierNeedMaterial(int currentTier) {
        return List.of(
                new ItemStack(ModItems.BOND.get(), 3 + currentTier),
                new ItemStack(ModItems.GOLDEN_BEANS.get(), (30 + currentTier * 15) * 2),
                new ItemStack(SmithHammer.getHammerByTier(currentTier))
        );
    }
}
