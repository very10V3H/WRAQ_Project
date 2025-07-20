package fun.wraq.commands.changeable;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.skill.SkillUtil;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.comsumable.ComsumableItems;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CompensateCommand implements Command<CommandSourceStack> {
    public static CompensateCommand instance = new CompensateCommand();

    public static int rewardNum = 49;
    public static String singleReward = "singleReward" + rewardNum;

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) return 0;
        CompoundTag data = player.getPersistentData();
        if (!data.contains(singleReward)) {
            data.putBoolean(singleReward, true);
            if (player.experienceLevel >= 75) {
                List<ItemStack> list = new ArrayList<>();
                ItemStack supplyBox = new ItemStack(ModItems.SUPPLY_BOX_TIER_3.get(), 1);
                list.add(supplyBox);
                switch (SkillUtil.getMaxType(player)) {
                    case 0 -> {
                        list.add(new ItemStack(ComsumableItems.WHETSTONE_ATTACK_0.get()));
                        list.add(new ItemStack(ComsumableItems.WHETSTONE_PENETRATION_0.get()));
                        list.add(new ItemStack(ComsumableItems.WHETSTONE_PENETRATION0_0.get()));
                        sendMSG(player, Te.s("基于精通点数为你提供了",
                                "战士类型", CustomStyle.styleOfPower, "的补偿品."));
                    }
                    case 1 -> {
                        list.add(new ItemStack(ComsumableItems.QUIVER_ATTACK_0.get()));
                        list.add(new ItemStack(ComsumableItems.QUIVER_PENETRATION_0.get()));
                        list.add(new ItemStack(ComsumableItems.QUIVER_PENETRATION0_0.get()));
                        sendMSG(player, Te.s("基于精通点数为你提供了",
                                "弓箭", CustomStyle.styleOfFlexible, "的补偿品."));
                    }
                    case 2 -> {
                        list.add(new ItemStack(ComsumableItems.MIXTURE_ATTACK_0.get()));
                        list.add(new ItemStack(ComsumableItems.MIXTURE_PENETRATION_0.get()));
                        list.add(new ItemStack(ComsumableItems.MIXTURE_PENETRATION0_0.get()));
                        sendMSG(player, Te.s("基于精通点数为你提供了",
                                "法师", CustomStyle.styleOfMana, "的补偿品."));
                    }
                }
/*                list.add(new ItemStack(ComsumableItems.HEAT_INJECTION_2.get()));
                list.add(new ItemStack(ComsumableItems.HEAT_DEVICE_1.get()));*/
                list.forEach(stack -> {
                    InventoryCheck.addOwnerTagToItemStack(player, stack);
                    InventoryOperation.giveItemStackWithMSG(player, stack);
                });
                /*PlanPlayer.tryToDelayOverDate(player, 3);*/
                sendMSG(player, Te.s("你成功领取了补偿!", ChatFormatting.AQUA));
            }
            return 0;
        }
        sendMSG(player, Te.s("似乎已经领取过/没有资格领取补偿呢", ChatFormatting.AQUA));
        return 0;
    }

    public void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("补偿", CustomStyle.styleOfSakura), content);
    }
}
