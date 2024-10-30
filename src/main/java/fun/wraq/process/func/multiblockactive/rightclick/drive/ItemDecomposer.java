package fun.wraq.process.func.multiblockactive.rightclick.drive;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.multiblockactive.rightclick.top.RightClickActivation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ItemDecomposer extends RightClickActivation {

    private final List<List<ItemStack>> supportItems;
    private final List<ItemStack> product;

    public ItemDecomposer(Component title, Vec3 centerPos, List<List<ItemStack>> supportItems, List<ItemStack> product) {
        super(title, centerPos);
        this.supportItems = supportItems;
        this.product = product;
    }

    @Override
    public void handleRightClick(Player player) {
        supportItems.stream()
                .filter(supportItemList -> InventoryOperation.checkPlayerHasItem(player, supportItemList))
                .findFirst()
                .ifPresent(supportItems -> {
                    supportItems.forEach(stack -> {
                        sendFormatMSG(player, Te.s("成功分解了", stack.getDisplayName(),
                                " * ", stack.getCount(), ChatFormatting.AQUA));
                    });
                    InventoryOperation.removeItemWithoutCheck(player, supportItems);
                    product.forEach(stack -> {
                        InventoryOperation.itemStackGive(player, new ItemStack(stack.getItem(), stack.getCount()));
                    });
                });
    }

    @Override
    public List<Component> getDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("分解:"));
        for (int i = 0; i < supportItems.size(); i++) {
            List<ItemStack> items = supportItems.get(i);
            MutableComponent mutableComponent = Component.literal("");
            if (i != 0) {
                mutableComponent.append(Te.s("或 ", CustomStyle.styleOfLife));
            }
            for (int j = 0; j < items.size(); j++) {
                ItemStack stack = items.get(j);
                mutableComponent.append(Te.s(stack.getDisplayName(),
                        " * ", String.valueOf(stack.getCount()), ChatFormatting.AQUA,
                        (j == items.size() - 1 ? "" : " + "), CustomStyle.styleOfVolcano));
            }
            components.add(mutableComponent);
        }
        components.add(Te.s("获得:"));
        product.forEach(stack -> {
            components.add(Te.s(stack.getDisplayName(), " * ", String.valueOf(stack.getCount()), ChatFormatting.AQUA));
        });
        return components;
    }

    private void sendFormatMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("分解", ChatFormatting.RED), content);
    }
}
