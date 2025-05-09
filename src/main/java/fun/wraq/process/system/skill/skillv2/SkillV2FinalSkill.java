package fun.wraq.process.system.skill.skillv2;

import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class SkillV2FinalSkill extends SkillV2 {

    protected SkillV2FinalSkill(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected boolean canRelease(Player player) {
        return !DelayOperationWithAnimation.playerCurrentOperationMap.containsKey(Name.get(player));
    }

    @Override
    protected List<Component> getReleaseConditionDescription() {
        return List.of();
    }

    // 终极技能的技能等级不应超过基础技能等级的最小值
    @Override
    protected boolean canUpgrade(Player player) {
        List<SkillV2> skillV2List = getSkillV2ListByProfession(professionType);
        int minLevel = Math.min(getMaxLevel(player,skillV2List.stream().filter(skillV2 -> skillV2.skillType == 1).toList()),
                Math.min(getMaxLevel(player,skillV2List.stream().filter(skillV2 -> skillV2.skillType == 2).toList()),
                        getMaxLevel(player,skillV2List.stream().filter(skillV2 -> skillV2.skillType == 3).toList())));
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        return skillLevel < minLevel && skillLevel < maxSkillLevel && InventoryOperation.checkPlayerHasItem(player,
                getUpgradeNeedMaterial(skillLevel));
    }

    private List<ItemStack> getUpgradeNeedMaterial(int skillLevel) {
        switch (skillLevel) {
            case 3 -> {
                return List.of(new ItemStack(ModItems.PlainCompleteGem.get(), 3));
            }
            case 4 -> {
                return List.of(new ItemStack(ModItems.GOLD_COIN.get(), 2));
            }
            case 5 -> {
                return List.of(new ItemStack(ModItems.FOILED_NETHER_IMPRINT.get(), 3));
            }
            case 6 -> {
                return List.of(new ItemStack(ModItems.PurpleIronBud2.get(), 3));
            }
            case 7 -> {
                return List.of(new ItemStack(ModItems.IceCompleteGem.get(), 3));
            }
            case 8 -> {
                return List.of(
                        new ItemStack(ModItems.GOLDEN_SHEET.get(), 3),
                        new ItemStack(ModItems.DevilBlood.get(), 3)
                );
            }
            case 9 -> {
                List<ItemStack> items = new ArrayList<>();
                items.add(new ItemStack(ModItems.MoonCompleteGem.get(), 3));
                switch (professionType) {
                    case 0 -> items.add(new ItemStack(ModItems.CastleSwordPiece.get(), 3));
                    case 1 -> items.add(new ItemStack(ModItems.CastleBowPiece.get(), 3));
                    case 2 -> items.add(new ItemStack(ModItems.CastleSceptrePiece.get(), 3));
                }
                return items;
            }
            default -> {
                return List.of(new ItemStack(ModItems.GOLD_COIN.get(), 3));
            }
        }
    }

    @Override
    protected List<Component> getUpgradeConditionDescription(int skillLevel) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("1.", CustomStyle.styleOfWorld, "每类", "基础技能", ChatFormatting.AQUA,
                "的最大等级需要达到", (skillLevel + 1) + "级", ChatFormatting.AQUA));
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

    private int getMaxLevel(Player player, List<SkillV2> list) {
        int maxLevel = 0;
        for (SkillV2 skillV2 : list) {
            int skillLevel = getPlayerSkillLevelBySkillV2(player, skillV2);
            if (skillLevel > maxLevel) {
                maxLevel = skillLevel;
            }
        }
        return maxLevel;
    }
}
