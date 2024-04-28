package com.very.wraq.series.overWorld.MainStory_I.WaterSystem;

import com.very.wraq.process.element.Element;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LakePower extends Item {

    private final int Level;

    public static Map<Player,Integer> playerDefendTickMap = new HashMap<>();
    public static Map<Player,Integer> playerDefendRateMap = new HashMap<>();

    public LakePower(Properties p_41383_, int level) {
        super(p_41383_);
        this.Level = level;
        Utils.PowerTag.put(this,1d);
        Utils.WeaponList.add(this);
    }

    public int getLevel() {
        return Level;
    }
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·法术").withStyle(CustomStyle.styleOfMana));
        Compute.DescriptionDash(components,ChatFormatting.WHITE, CustomStyle.styleOfMana,ChatFormatting.WHITE);
        Compute.DescriptionActive(components,Component.literal("迟滞之水").withStyle(CustomStyle.styleOfLake));
        components.add(Component.literal(" -减速周围敌人，并削减其").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDefence((Effect[Level] * 10) +"%")).
                append(Component.literal("持续2s。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("-同时对其造成").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamageValue((Effect[Level] * 100) +"%")));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.WaterElement("1 + 100%")));
        components.add(Component.literal(" -为周围玩家提供持续4s的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDefence((Effect[Level] * 10) +"%")).
                append(Component.literal("伤害削减。").withStyle(ChatFormatting.GREEN)));
        Compute.CoolDownTimeDescription(components,CoolDownTime[Level]);
        Compute.ManaCostDescription(components,ManaCost[Level]);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfMana,ChatFormatting.WHITE);
        components.add(Component.literal("Powers-Lake").withStyle(CustomStyle.styleOfLake));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.USE(player);
        return super.use(level, player, interactionHand);
    }
    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static int[] ManaCost = {
            50,50,50,40
    };

    public static int[] CoolDownTime = {
            8,8,8,8
    };

    public static int[] Effect = {
            1,2,3,4
    };

    public static double PlayerDefend(Player player) {
        if (playerDefendTickMap.containsKey(player) && playerDefendTickMap.get(player) > player.getServer().getTickCount()) {
            return 0.1 * playerDefendRateMap.get(player);
        }
        return 0;
    }


}
