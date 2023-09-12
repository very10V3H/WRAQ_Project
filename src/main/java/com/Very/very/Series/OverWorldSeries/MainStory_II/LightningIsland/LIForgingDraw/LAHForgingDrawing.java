package com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.LIForgingDraw;

import com.Very.very.Files.ConfigTest;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LAHForgingDrawing extends Item {

    public LAHForgingDrawing(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Component.literal("·锻造图").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal("-右键尝试锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
        components.add(Component.literal("·制作:唤雷之顶*1").withStyle(Utils.styleOfLightingIsland));
        components.add(Component.literal("·材料需求:").withStyle(ChatFormatting.AQUA));
        components.add(Compute.MaterialRequirement(1,Component.literal("唤雷之芽").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLightingIsland),10));
        components.add(Compute.MaterialRequirement(2,Component.literal("金币").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD),64));
        components.add(Compute.MaterialRequirement(3,Component.literal("矿工的劳动产出").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),128));
        components.add(Component.literal(" "));
        components.add(Component.literal("ForgingDrawing-Island").withStyle(Utils.styleOfLightingIsland).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND))
        {
            Inventory inventory = player.getInventory();
            boolean ContainMaterial = Compute.ItemStackCheck(inventory, Moditems.LightningRune.get(),10)
                    && Compute.ItemStackCheck(inventory, Moditems.GoldCoin.get(),64)
                    && Compute.ItemStackCheck(inventory, Moditems.MineSoul.get(),128);
            if(ContainMaterial)
            {
                Compute.ItemStackRemove(inventory,Moditems.LightningRune.get(),10);
                Compute.ItemStackRemove(inventory,Moditems.GoldCoin.get(),64);
                Compute.ItemStackRemove(inventory,Moditems.MineSoul.get(),128);
                Compute.FormatMSGSend(player,Component.literal("锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                        Component.literal("锻造成功！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD));
                ItemStack itemStack = Moditems.IslandArmorHelmet.get().getDefaultInstance();
                itemStack.getOrCreateTagElement(Utils.MOD_ID).putInt("Number", ConfigTest.ForgingLightningArmorHelmet.get());
                Compute.FormatBroad(level,Component.literal("锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                        Component.literal(player.getName().getString()+"成功锻造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()).
                                append(Component.literal("这是维瑞阿契第"+ConfigTest.ForgingLightningArmorHelmet.get()+"件").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                append(Component.literal("唤雷之顶").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLightingIsland)));
                ConfigTest.ForgingLightningArmorHelmet.set(ConfigTest.ForgingLightningArmorHelmet.get()+1);
                player.setItemInHand(InteractionHand.MAIN_HAND,itemStack);
            }
            else
            {
                Compute.FormatMSGSend(player,Component.literal("锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                        Component.literal("背包里似乎没有足够的物品用于锻造。"));
                if(Compute.ItemStackCount(inventory,Moditems.LightningRune.get()) < 10)
                {
                    Compute.FormatMSGSend(player,Component.literal("锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal("缺少:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("唤雷之芽").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLightingIsland)).
                                    append(Component.literal("*"+(10-Compute.ItemStackCount(inventory,Moditems.LightningRune.get())))));
                }
                if(Compute.ItemStackCount(inventory,Moditems.GoldCoin.get()) < 64)
                {
                    Compute.FormatMSGSend(player,Component.literal("锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal("缺少:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("金币").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)).
                                    append(Component.literal("*"+(64-Compute.ItemStackCount(inventory,Moditems.GoldCoin.get())))));
                }
                if(Compute.ItemStackCount(inventory,Moditems.MineSoul.get()) < 128)
                {
                    Compute.FormatMSGSend(player,Component.literal("锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal("缺少:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("矿工的劳动产出").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).
                                    append(Component.literal("*"+(128-Compute.ItemStackCount(inventory,Moditems.MineSoul.get())))));
                }
            }
        }
        return super.use(level, player, interactionHand);
    }
}
