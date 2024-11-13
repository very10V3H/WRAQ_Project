package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.MySound;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeHandler;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;

import java.util.Map;
import java.util.WeakHashMap;

public class FixBackpackCommand implements Command<CommandSourceStack> {
    public static FixBackpackCommand instance = new FixBackpackCommand();

    public static Map<Player, Boolean> attentionInfoMap = new WeakHashMap<>();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();

        /*            for (int i = 0 ; i < player.getInventory().getMaxStackSize() ; i ++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (stack.getItem() instanceof BackpackItem) {
                    BackpackWrapper backpackWrapper = new BackpackWrapper(stack);
                    backpackWrapper.getInventoryHandler().setStackInSlot(0, Items.IRON_INGOT.getDefaultInstance());
                    backpackWrapper.refreshInventoryForInputOutput();

                    ItemStack newBackPack = new ItemStack(ModItems.NETHERITE_BACKPACK.get());
                    BackpackWrapper newBackpackWrapper = new BackpackWrapper(newBackPack);
                    backpackWrapper.getUpgradeHandler().copyTo(newBackpackWrapper.getUpgradeHandler());
                    for (int j = 0 ; j < backpackWrapper.getInventoryHandler().getSlots() ; j ++) {
                        newBackpackWrapper.getInventoryHandler().setSlotStack(j, new ItemStack(Items.IRON_INGOT, 128));
                    }
                    player.getInventory().setItem(i, newBackPack);
                }
            }*/
        if (!attentionInfoMap.containsKey(player)) {
            MySound.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
            Compute.setPlayerTitleAndSubTitle((ServerPlayer) player,
                    Te.s("请看聊天窗提示信息!", ChatFormatting.RED),
                    Te.s("请看聊天窗提示信息!", ChatFormatting.RED));
            player.sendSystemMessage(Te.s("使用前须知:"));
            player.sendSystemMessage(Te.s("1.使用后，", "禁止", ChatFormatting.RED, "马上点击右上整理"));
            player.sendSystemMessage(Te.s("2.收到新背包，", "必须", ChatFormatting.RED, "先装上收到的几个升级"));
            player.sendSystemMessage(Te.s("3.收到新背包后，装上升级后点击整理，理论上可以修复新旧版堆叠"));
            player.sendSystemMessage(Te.s("3.详细阅读以上须知后，再次按下指令以修复背包物品堆叠"));
            return 0;
        }
        if (player.getMainHandItem().is(ModItems.NETHERITE_BACKPACK.get())) {
            ItemStack oldBackpack = player.getMainHandItem();
            BackpackWrapper wrapper = new BackpackWrapper(oldBackpack);
            InventoryHandler inventoryHandler = wrapper.getInventoryHandler();
            UpgradeHandler upgradeHandler = wrapper.getUpgradeHandler();
            InventoryHelper.iterate(upgradeHandler, ((integer, itemStack) -> {
                player.addItem(itemStack);
            }));
            ItemStack newBackPack = new ItemStack(ModItems.NETHERITE_BACKPACK.get());
            BackpackWrapper newBackpackWrapper = new BackpackWrapper(newBackPack);
            InventoryHandler newInventoryHandler = newBackpackWrapper.getInventoryHandler();
            for (int i = 0 ; i < inventoryHandler.getSlots() ; i ++) {
                ItemStack stack = inventoryHandler.getStackInSlot(i);
                stack.hideTooltipPart(ItemStack.TooltipPart.ADDITIONAL);
                newInventoryHandler.setSlotStack(i, stack);
            }
            player.setItemInHand(InteractionHand.MAIN_HAND, newBackPack);
        } else {
            player.sendSystemMessage(Te.s("仅接受下界合金背包"));
        }
        return 0;
    }
}
