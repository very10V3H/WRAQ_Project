package com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LightningSoul extends Item {
    public LightningSoul(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.MaterialLevelDescription.Rare(components);
        components.add(Component.literal(" "));
        components.add(Component.literal("建成唤雷塔的材料碎块。").withStyle(Utils.styleOfLightingIsland));
        components.add(Component.literal("可在锻造台与一个精炼铁锭合成:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).
                append(Component.literal("唤雷之芽").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLightingIsland)));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-Island").withStyle(Utils.styleOfLightingIsland).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
