package fun.wraq.process.func.multiblockactive.rightclick.drive;

import fun.wraq.commands.changeable.TextCommand;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.multiblockactive.rightclick.top.RightClickActivation;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ItemChanger extends RightClickActivation {

    private final List<ItemStack> materials;
    private final List<ItemStack> products;
    public ItemChanger(Component title, Vec3 centerPos, List<ItemStack> materials, List<ItemStack> products) {
        super(title, centerPos);
        this.materials = materials;
        this.products = products;
    }

    public List<ItemStack> getMaterials() {
        return materials;
    }

    public List<ItemStack> getProducts() {
        return products;
    }

    @Override
    public void handleRightClick(Player player) {
        if (InventoryOperation.checkItemRemoveIfHas(player, materials)) {
            products.forEach(stack -> InventoryOperation.itemStackGive(player, new ItemStack(stack.getItem(), stack.getCount())));
        } else {
            sendFormatMSG(player, Te.s("所需物品不足"));
        }
    }

    @Override
    public void summonArmorStand(Level level) {
        List<Component> components = new ArrayList<>();
        components.add(getTitle());
        components.add(Te.m("使用:"));
        materials.forEach(stack -> {
            components.add(Te.s(stack.getDisplayName(), " * ", String.valueOf(stack.getCount())));
        });
        components.add(Te.m("兑换:"));
        products.forEach(stack -> {
            components.add(Te.s(stack.getDisplayName(), " * ", String.valueOf(stack.getCount())));
        });
        TextCommand.summonArmorStand(components, level, getCenterPos());
    }

    private void sendFormatMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.m("交换", ChatFormatting.AQUA), content);
    }
}
