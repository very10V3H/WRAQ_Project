package fun.wraq.series.nether.Power;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.power.PowerLogic;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PiglinPower extends Item implements ActiveItem {

    public PiglinPower(Properties p_41383_) {
        super(p_41383_);
        Utils.powerTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·法术").withStyle(CustomStyle.styleOfMana));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        components.add(Component.literal("·[对魔]").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("基于").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("指针").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("周围怪物数量，对每个怪物造成:").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamageValue("200%*怪物数量")));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.FireElement("1 + 100%")));
        components.add(Component.literal("·[对人]").withStyle(ChatFormatting.AQUA).
                append(Component.literal("基于").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("自身").withStyle(ChatFormatting.GREEN)).
                append(Component.literal("周围玩家数量，提升每个玩家:").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.movementSpeed("10%*玩家数量")).
                append(Component.literal("以及").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamage("3%")).
                append(Component.literal("额外攻击力").withStyle(ChatFormatting.YELLOW)).
                append(Component.literal("，并移除玩家的负面效果。").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal(" - 这个效果持续5s，且仅能同时存在一个").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        ComponentUtils.coolDownTimeDescription(components, 10);
        ComponentUtils.manaCostDescription(components, 360);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, 360, true)) {
            PowerLogic.PiglinPower(player, this);
            PowerLogic.PlayerReleasePowerType(player, 1);
        }
    }
}