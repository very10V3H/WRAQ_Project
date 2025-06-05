package fun.wraq.process.system.skill.skillv2;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.extraordinary.ExtraordinaryItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class SkillV2PassiveSkill extends SkillV2 {

    protected SkillV2PassiveSkill(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    // 被动技能不能主动释放
    @Override
    protected boolean canRelease(Player player) {
        return false;
    }

    @Override
    protected void releaseOperation(Player player) {

    }

    @Override
    protected List<Component> getReleaseConditionDescription() {
        return List.of();
    }

    // 被动技能等级不应超过玩家等级/20 + 1
    @Override
    protected boolean canUpgrade(Player player) {
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        return skillLevel < player.experienceLevel / 20 + 1 && skillLevel < maxSkillLevel
                && InventoryOperation.checkPlayerHasItem(player, getUpgradeNeedMaterial(skillLevel));
    }

    private List<ItemStack> getUpgradeNeedMaterial(int skillLevel) {
        switch (skillLevel) {
            case 3 -> {
                return List.of(new ItemStack(ModItems.PLAIN_COMPLETE_GEM.get()));
            }
            case 4 -> {
                return List.of(new ItemStack(ModItems.GOLD_COIN.get(), 2));
            }
            case 5 -> {
                return List.of(new ItemStack(ModItems.FOILED_NETHER_IMPRINT.get()));
            }
            case 6 -> {
                return List.of(new ItemStack(ModItems.PURPLE_IRON_BUD_2.get()));
            }
            case 7 -> {
                return List.of(new ItemStack(ModItems.ICE_COMPLETE_GEM.get()));
            }
            case 8 -> {
                return List.of(
                        new ItemStack(ModItems.GOLDEN_SHEET.get()),
                        new ItemStack(ModItems.DEVIL_BLOOD.get())
                );
            }
            case 9 -> {
                List<ItemStack> items = new ArrayList<>();
                items.add(new ItemStack(ModItems.MOON_COMPLETE_GEM.get()));
                switch (professionType) {
                    case 0 -> items.add(new ItemStack(ModItems.CASTLE_SWORD_PIECE.get()));
                    case 1 -> items.add(new ItemStack(ModItems.CASTLE_BOW_PIECE.get()));
                    case 2 -> items.add(new ItemStack(ModItems.CASTLE_SCEPTRE_PIECE.get()));
                }
                return items;
            }
            case 10 -> {
                return List.of(new ItemStack(ExtraordinaryItems.DAZZLING_DIAMOND.get(), 1));
            }
            case 11 -> {
                return List.of(new ItemStack(ExtraordinaryItems.DAZZLING_DIAMOND.get(), 2));
            }
            default -> {
                return List.of(new ItemStack(ModItems.GOLD_COIN.get()));
            }
        }
    }

    @Override
    protected List<Component> getUpgradeConditionDescription(int skillLevel) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("1.", CustomStyle.styleOfWorld, "达到",
                Utils.getLevelDescription(skillLevel * 20)));
        List<ItemStack> upgradeNeedMaterial = getUpgradeNeedMaterial(skillLevel);
        for (int i = 0; i < upgradeNeedMaterial.size(); i++) {
            ItemStack itemStack = upgradeNeedMaterial.get(i);
            components.add(Te.s((i + 2) + ".", CustomStyle.styleOfWorld,
                    itemStack, " * " + itemStack.getCount(), ChatFormatting.AQUA));
        }
        return components;
    }

    @Override
    protected void upgradeOperation(Player player) {
        InventoryOperation.removeItemWithoutCheck(player,
                getUpgradeNeedMaterial(getPlayerSkillLevelBySkillV2(player, this) - 1));
    }
}
