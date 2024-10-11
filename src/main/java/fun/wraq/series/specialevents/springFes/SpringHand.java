package fun.wraq.series.specialevents.springFes;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class SpringHand extends Item implements ICurioItem {

    private final int level;

    public SpringHand(Properties p_41383_, int level) {
        super(p_41383_);
        this.level = level;
        Utils.attackDamage.put(this, Attack[level]);
        Utils.defence.put(this, new double[]{1, 1, 2, 2}[level]);
        Utils.maxHealth.put(this, MaxHealth[level]);
        Utils.defencePenetration.put(this, DefencePenetration[level]);
        Utils.expUp.put(this, ExpUp[level]);
        Utils.curiosList.add(this);
    }

    private final double[] Attack = {
            20, 40, 60, 80
    };
    private final double[] Defence = {
            1, 1, 2, 2
    };
    private final double[] MaxHealth = {
            100, 200, 300, 400
    };
    private final double[] DefencePenetration = {
            0.2, 0.25, 0.3, 0.4
    };
    private final double[] ExpUp = {
            0.3, 0.5, 0.7, 1.0
    };
    private final int[] LevelRequire = {
            20, 40, 60, 80
    };

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GOLD, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        components.add(Component.literal("多件金龙咆哮手套仅会生效最后装备的一件效果").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GOLD, ChatFormatting.WHITE);
        components.add(Component.literal(" 等级需求:" + LevelRequire[this.level]).withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal("SpringFestival~2024").withStyle(ChatFormatting.ITALIC).withStyle(CustomStyle.styleOfSpring));

        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Utils.PlayerSpringHandAttackAttribute.put((Player) slotContext.entity(), Attack[this.level]);
        Utils.PlayerSpringHandDefenceAttribute.put((Player) slotContext.entity(), Defence[this.level]);
        Utils.PlayerSpringHandMaxHealthAttribute.put((Player) slotContext.entity(), MaxHealth[this.level]);
        Utils.PlayerSpringHandDefencePenetraionAttribute.put((Player) slotContext.entity(), DefencePenetration[this.level]);
        Utils.PlayerSpringHandExpUpAttribute.put((Player) slotContext.entity(), ExpUp[this.level]);
        Utils.PlayerSpringHandLevelRequire.put((Player) slotContext.entity(), LevelRequire[this.level]);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Utils.PlayerSpringHandAttackAttribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringHandDefenceAttribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringHandMaxHealthAttribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringHandDefencePenetraionAttribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringHandExpUpAttribute.remove((Player) slotContext.entity());
        Utils.PlayerSpringHandLevelRequire.remove((Player) slotContext.entity());
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
