package fun.wraq.process.system.profession.pet.allay;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AllayPetPlayerData {
    public static final String ALLAY_PET_PLAYER_DATA_KEY = "AllayPetData";
    public static CompoundTag getAllayPetData(Player player) {
        return Compute.getPlayerSpecificKeyCompoundTagData(player, ALLAY_PET_PLAYER_DATA_KEY);
    }

    public static final String CUSTOM_NAME_KEY = "AllayCustomName";

    public static final String TIER_KEY = "AllayTier";
    public static final String XP_LEVEL_KEY = "AllayXpLevel";
    public static final String XP_VALUE_KEY = "AllayXpValue";

    public static final String ATTACK_LEVEL_KEY = "AllayAttackLevel";
    public static final Component ATTACK_SKILL_NAME = Te.s("欢悦气息", CustomStyle.styleOfWorld);
    public static final String HEALING_LEVEL_KEY = "AllayHealingLevel";
    public static final Component HEALING_SKILL_NAME = Te.s("陪伴馨声", CustomStyle.styleOfLife);
    public static final String GEM_PIECE_LEVEL_KEY = "AllayGemPieceLevel";
    public static final Component GEM_PIECE_SKILL_NAME = Te.s("紫晶亲和", CustomStyle.styleOfPurpleIron);

    public static void setCustomAllayName(Player player, String name) {
        if (name.length() < 2 || name.length() > 8) {
            sendMSG(player, Te.s("名字长度不能小于2或大于8"));
            return;
        }
        getAllayPetData(player).putString(CUSTOM_NAME_KEY, name);
        sendMSG(player, Te.s("更名成功!"));
    }

    public static String getAllayName(Player player) {
        if (!getAllayPetData(player).contains(CUSTOM_NAME_KEY)) {
            return "悦灵";
        }
        return getAllayPetData(player).getString(CUSTOM_NAME_KEY);
    }

    public static int getAllayXpLevel(Player player) {
        return getAllayPetData(player).getInt(XP_LEVEL_KEY);
    }

    public static double getAllayXpValue(Player player) {
        return getAllayPetData(player).getDouble(XP_VALUE_KEY);
    }

    public static int getSkillLevel(Player player, String skillKey) {
        return getAllayPetData(player).getInt(skillKey);
    }

    public static void incrementSkillLevel(Player player, String skillKey, int num) {
        getAllayPetData(player).putInt(skillKey, getSkillLevel(player, skillKey) + num);
    }

    public static int getAllayTier(Player player) {
        return getAllayPetData(player).getInt(TIER_KEY);
    }

    public static Component getAllayTierDescription(int tier) {

    }

    public static int getMaxTier() {
        return 4;
    }

    public static void tryToIncrementAllayTier(Player player) {
        int currentTier = getAllayTier(player);
        sendMSG(player, Te.s(getAllayName(player), "当前等阶为: ", getAllayTierDescription(currentTier)));
        player.sendSystemMessage(Te.s("  升级到下一等阶需要:"));
        List<ItemStack> incrementTierNeedMaterials = getIncrementTierNeedMaterial(currentTier);
        for (int i = 0; i < incrementTierNeedMaterials.size(); i++) {
            ItemStack stack = incrementTierNeedMaterials.get(i);
            player.sendSystemMessage(Te.s("  " + (i + 1) + ". ", CustomStyle.styleOfWorld,
                    stack, " * ", stack.getCount(), ChatFormatting.AQUA));
        }
    }

    public static void incrementAllayTier(Player player) {
        int tier = getAllayTier(player);
        if (tier >= getMaxTier()) {
            sendMSG(player, Te.s(getAllayName(player), "已达到了最高的等阶!"));
            return;
        }
        if (InventoryOperation.checkPlayerHasItem(player, getIncrementTierNeedMaterial(tier))) {
            InventoryOperation.removeItemWithoutCheck(player, getIncrementTierNeedMaterial(tier));
            Compute.incrementSpecificKeyDataIntValue(player, ALLAY_PET_PLAYER_DATA_KEY, TIER_KEY, 1);
            sendMSG(player, Te.s(getAllayName(player), "已达到新的等阶:", getAllayTierDescription(tier + 1)));
        }
    }

    public static List<ItemStack> getIncrementTierNeedMaterial(int currentTier) {
        return List.of(
                new ItemStack(ModItems.BOND.get(), 3 + currentTier),
                new ItemStack(ModItems.GOLDEN_BEANS.get(), 30 + currentTier * 15)
        );
    }

    public static int getAllayXpLevelUpperLimit(Player player) {
        return 25 * (1 + getAllayTier(player));
    }

    public static int getAllaySkillLevelUpperLimit(Player player) {
        return 4 * (1 + getAllayTier(player));
    }

    public static boolean giveAllayPercentExp(Player player, double num, int expLevel) {
        int currentXpLevel = getAllayXpLevel(player);
        if (currentXpLevel >= player.experienceLevel || currentXpLevel >= getAllayXpLevelUpperLimit(player)) {
            return false;
        }
        if (expLevel >= Compute.expGetUpperLimit) {
            num *= (double) expLevel / Compute.expGetUpperLimit;
            expLevel = Compute.expGetUpperLimit;
        }
        if (expLevel - currentXpLevel > 8) expLevel = player.experienceLevel;
        double getXpValue = Compute.getCurrentXpLevelUpNeedXpPoint(expLevel) * num;
        getAllayPetData(player).putDouble(XP_VALUE_KEY, getAllayXpValue(player) + getXpValue);
        sendMSG(player, Te.s(getAllayName(player), CustomStyle.styleOfWorld, " 获得了 ",
                Compute.getSimplifiedNumberDescription(getXpValue) + "经验值", ChatFormatting.LIGHT_PURPLE));
        if (getAllayXpValue(player) > Compute.getCurrentXpLevelUpNeedXpPoint(currentXpLevel)) {
            // 提升等级
            getAllayPetData(player).putDouble(XP_VALUE_KEY,
                    getAllayXpValue(player) - Compute.getCurrentXpLevelUpNeedXpPoint(currentXpLevel));
            getAllayPetData(player).putInt(XP_LEVEL_KEY, currentXpLevel + 1);
            sendMSG(player, Te.s(getAllayName(player), CustomStyle.styleOfWorld));
            String name = player.getName().getString();
            if (AllayPet.playerAllayPetMap.containsKey(name)) {
                AllayPet allayPet = AllayPet.playerAllayPetMap.get(name);
                MobSpawn.setMobCustomName(allayPet.allay, Te.s(getAllayName(player), CustomStyle.styleOfWorld),
                        currentXpLevel + 1);
            }
        }
        return true;
    }

    public static void queryAllayInfo(Player player) {
        sendMSG(player, Te.s(" ", Utils.getLevelDescription(getAllayXpLevel(player)),
                getAllayName(player), CustomStyle.styleOfWorld, "的基本信息如下"));
        player.sendSystemMessage(Te.s(" ".repeat(4), "经验值 ", ChatFormatting.LIGHT_PURPLE,
                Compute.getSimplifiedNumberDescription(getAllayXpValue(player)), ChatFormatting.LIGHT_PURPLE, " / ",
                        Compute.getSimplifiedNumberDescription(Compute.getCurrentXpLevelUpNeedXpPoint(getAllayXpLevel(player))), CustomStyle.styleOfLucky));
        player.sendSystemMessage(Te.s(" ".repeat(4),
                ATTACK_SKILL_NAME, " Lv." + getSkillLevel(player, ATTACK_LEVEL_KEY), CustomStyle.styleOfWorld));
        player.sendSystemMessage(Te.s(" ".repeat(4),
                HEALING_SKILL_NAME, " Lv." + getSkillLevel(player, HEALING_LEVEL_KEY), CustomStyle.styleOfWorld));
        player.sendSystemMessage(Te.s(" ".repeat(4),
                GEM_PIECE_SKILL_NAME, " Lv." + getSkillLevel(player, GEM_PIECE_LEVEL_KEY), CustomStyle.styleOfWorld));
    }

    private static void sendMSG(Player player, Component content) {
        AllayPet.sendMSG(player, content);
    }
}
