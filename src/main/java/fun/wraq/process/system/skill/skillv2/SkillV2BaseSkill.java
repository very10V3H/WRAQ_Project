package fun.wraq.process.system.skill.skillv2;

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

public abstract class SkillV2BaseSkill extends SkillV2 {

    protected SkillV2BaseSkill(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected boolean canRelease(Player player) {
        return (!DelayOperationWithAnimation.playerCurrentOperationMap.containsKey(player)
                || DelayOperationWithAnimation.isNormalAttacking(player));
    }

    @Override
    protected List<Component> getReleaseConditionDescription() {
        return List.of();
    }

    // 基础技能等级不应超过最大被动技能等级
    @Override
    protected boolean canUpgrade(Player player) {
        List<SkillV2> passiveSkillList = getSkillV2ListByProfession(professionType)
                .stream().filter(skillV2 -> skillV2.skillType == 0)
                .toList();
        int maxPassiveSkillLevel = 0;
        for (SkillV2 skillV2 : passiveSkillList) {
            int skillLevel = getPlayerSkillLevelBySkillV2(player, skillV2);
            if (skillLevel > maxPassiveSkillLevel) {
                maxPassiveSkillLevel = getPlayerSkillLevelBySkillV2(player, skillV2);
            }
        }
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        return skillLevel < maxPassiveSkillLevel && skillLevel < maxSkillLevel
                && InventoryOperation.checkPlayerHasItem(player, getUpgradeNeedMaterial(skillLevel));
    }

    private List<ItemStack> getUpgradeNeedMaterial(int skillLevel) {
        switch (skillLevel) {
            case 5 -> {
                return List.of(new ItemStack(ModItems.GOLD_COIN.get(), 3));
            }
            case 6 -> {
                return List.of(new ItemStack(ModItems.GOLD_COIN.get(), 5));
            }
            case 7 -> {
                return List.of(new ItemStack(ModItems.GOLD_COIN.get(), 32),
                        new ItemStack(ModItems.GOLDEN_BEANS.get(), 3));
            }
            case 8 -> {
                return List.of(new ItemStack(ModItems.GOLD_COIN.get(), 64),
                        new ItemStack(ModItems.GOLDEN_BEANS.get(), 6));
            }
            case 9 -> {
                return List.of(new ItemStack(ModItems.GOLD_COIN.get(), 128),
                        new ItemStack(ModItems.GOLDEN_BEANS.get(), 9));
            }
            default -> {
                if (skillLevel < 3) {
                    return List.of(new ItemStack(ModItems.GOLD_COIN.get(), 1));
                } else {
                    return List.of(new ItemStack(ModItems.GOLD_COIN.get(), 2));
                }
            }
        }
    }

    @Override
    protected List<Component> getUpgradeConditionDescription(int skillLevel) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("1.", CustomStyle.styleOfWorld, "任意", "被动技能", ChatFormatting.GREEN,
                "的等级达到", (skillLevel + 1) + "级", ChatFormatting.AQUA));
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
                getUpgradeNeedMaterial(getPlayerSkillLevelBySkillV2(player, this)));
    }
}
