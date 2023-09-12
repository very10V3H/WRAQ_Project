package com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.SkyForgingDraw;

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

public class SkyCForgingDrawing extends Item {
    private final Item Material1 = Moditems.SkyRune.get();
    private final Item Material2 = Moditems.GoldCoin.get();
    private final Item Material3 = Moditems.MineSoul.get();
    private final int Num1 = 10;
    private final int Num2 = 64;
    private final int Num3 = 128;
    public SkyCForgingDrawing(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Component.literal("·锻造图").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal("-右键尝试锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
        components.add(Component.literal("·制作:天空之心*1").withStyle(Utils.styleOfSky));
        components.add(Component.literal("·材料需求:").withStyle(ChatFormatting.AQUA));
        components.add(Compute.MaterialRequirement(1,Material1.getDefaultInstance().getDisplayName(),Num1));
        components.add(Compute.MaterialRequirement(2,Material2.getDefaultInstance().getDisplayName(),Num2));
        components.add(Compute.MaterialRequirement(3,Material3.getDefaultInstance().getDisplayName(),Num3));
        components.add(Component.literal(" "));
        components.add(Component.literal("ForgingDrawing-Sky").withStyle(Utils.styleOfSky).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND))
        {
            Inventory inventory = player.getInventory();
            boolean ContainMaterial = Compute.ItemStackCheck(inventory, Material1, Num1)
                    && Compute.ItemStackCheck(inventory, Material2, Num2)
                    && Compute.ItemStackCheck(inventory, Material3, Num3);
            if(ContainMaterial)
            {
                Compute.ItemStackRemove(inventory,Material1,Num1);
                Compute.ItemStackRemove(inventory,Material2,Num2);
                Compute.ItemStackRemove(inventory,Material3,Num3);
                Compute.FormatMSGSend(player,Component.literal("锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                        Component.literal("锻造成功！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD));
                ItemStack itemStack = Moditems.SkyArmorChest.get().getDefaultInstance();
                itemStack.getOrCreateTagElement(Utils.MOD_ID).putInt("Number", ConfigTest.SkyArmorChest.get());
                Compute.FormatBroad(level,Component.literal("锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                        Component.literal(player.getName().getString()+"成功锻造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()).
                                append(Component.literal("这是维瑞阿契第"+ConfigTest.SkyArmorChest.get()+"件").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                append(Component.literal("天空之心").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSky)));
                ConfigTest.SkyArmorChest.set(ConfigTest.SkyArmorChest.get()+1);
                player.setItemInHand(InteractionHand.MAIN_HAND,itemStack);
            }
            else
            {
                Compute.FormatMSGSend(player,Component.literal("锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                        Component.literal("背包里似乎没有足够的物品用于锻造。"));
                if(Compute.ItemStackCount(inventory,Material1) < Num1)
                {
                    Compute.FormatMSGSend(player,Component.literal("锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal("缺少:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Material1.getDefaultInstance().getDisplayName()).
                                    append(Component.literal("*"+(Num1-Compute.ItemStackCount(inventory,Material1)))));
                }
                if(Compute.ItemStackCount(inventory,Material2) < Num2)
                {
                    Compute.FormatMSGSend(player,Component.literal("锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal("缺少:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Material2.getDefaultInstance().getDisplayName()).
                                    append(Component.literal("*"+(Num2-Compute.ItemStackCount(inventory,Moditems.GoldCoin.get())))));
                }
                if(Compute.ItemStackCount(inventory,Material3) < Num3)
                {
                    Compute.FormatMSGSend(player,Component.literal("锻造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal("缺少:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Material3.getDefaultInstance().getDisplayName()).
                                    append(Component.literal("*"+(Num3-Compute.ItemStackCount(inventory,Material3)))));
                }
            }
        }
        return super.use(level, player, interactionHand);
    }
}
