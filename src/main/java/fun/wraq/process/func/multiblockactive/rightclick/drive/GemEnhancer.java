package fun.wraq.process.func.multiblockactive.rightclick.drive;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.multiblockactive.rightclick.top.RightClickActivation;
import fun.wraq.series.gems.WraqGem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GemEnhancer extends RightClickActivation {

    private final Map<Item, Item> enhanceMap;
    private final List<ItemStack> materials;
    private final List<Component> receiveTypes;
    public GemEnhancer(List<Component> title, Vec3 centerPos, Map<Item, Item> enhanceMap, List<ItemStack> materials,
                       List<Component> receiveTypes) {
        super(title, centerPos);
        this.enhanceMap = enhanceMap;
        this.materials = materials;
        this.receiveTypes = receiveTypes;
    }

    @Override
    public void handleRightClick(Player player) {
        ItemStack stack = player.getMainHandItem();
        Item item = stack.getItem();
        if (enhanceMap.containsKey(item) && InventoryOperation.checkPlayerHasItem(player, materials)) {
            InventoryOperation.removeItemWithoutCheck(player, materials);
            InventoryOperation.removeItemWithoutCheck(player, new ItemStack(item));
            InventoryOperation.giveItemStackWithMSG(player, enhanceMap.get(item));
        } else {
            if (!enhanceMap.containsKey(item)) {
                if (!(item instanceof WraqGem)) {
                    sendFormatMSG(player, Te.s("这是宝石升级台= ="));
                } else {
                    sendFormatMSG(player, Te.s("这里不能升级这个类别的宝石呢"));
                }
            } else {
                sendFormatMSG(player, Te.s("似乎没有足够的材料来升级宝石"));
            }
        }
    }

    @Override
    public List<Component> getDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("可以升级以下种类的宝石"));
        MutableComponent mutableComponent = Te.s("");
        for (int i = 0 ; i < receiveTypes.size() ; i++) {
            Component component = receiveTypes.get(i);
            if (i != receiveTypes.size() - 1) {
                mutableComponent.append(component).append(Te.s("、"));
            } else {
                mutableComponent.append(component);
            }
        }
        components.add(mutableComponent);
        components.add(Te.m("使用:"));
        materials.forEach(stack -> {
            components.add(Te.s(stack.getDisplayName(), " * ", String.valueOf(stack.getCount())));
        });
        return components;
    }

    private void sendFormatMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("宝石升级", ChatFormatting.LIGHT_PURPLE), content);
    }
}
