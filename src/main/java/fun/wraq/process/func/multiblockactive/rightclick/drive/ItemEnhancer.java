package fun.wraq.process.func.multiblockactive.rightclick.drive;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.multiblockactive.rightclick.top.RightClickActivation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ItemEnhancer extends RightClickActivation {

    public final List<ItemStack> needMaterial;
    public final EnhanceCondition enhanceCondition;
    public final EnhanceOperation enhanceOperation;
    public final List<Component> description;

    public ItemEnhancer(Component title, Vec3 centerPos, List<ItemStack> needMaterial,
                        EnhanceCondition enhanceCondition, EnhanceOperation enhanceOperation,
                        List<Component> description) {
        super(title, centerPos);
        this.needMaterial = needMaterial;
        this.enhanceCondition = enhanceCondition;
        this.enhanceOperation = enhanceOperation;
        this.description = description;
    }

    @Override
    public void handleRightClick(Player player) {
        ItemStack mainHandStack = player.getMainHandItem();
        if (InventoryOperation.checkPlayerHasItem(player, needMaterial)) {
            if (enhanceCondition.check(mainHandStack)) {
                enhanceOperation.enhance(mainHandStack);
                sendFormatMSG(player,Te.s("成功锻造了 ", mainHandStack.getDisplayName()));
                MySound.soundToPlayer(player, SoundEvents.ANVIL_USE);
                InventoryOperation.removeItemWithoutCheck(player, needMaterial);
            } else {
                sendFormatMSG(player, Te.s("锻造的物品不满足要求"));
            }
        } else {
            sendFormatMSG(player, Te.s("所需物品不足"));
        }
    }

    @Override
    public List<Component> getDescription() {
        List<Component> components = new ArrayList<>(description);
        components.add(Te.s("所需材料:", CustomStyle.styleOfSky));
        needMaterial.forEach(stack -> {
            components.add(Te.s(stack.getDisplayName(), " * ", String.valueOf(stack.getCount())));
        });
        return components;
    }

    protected void sendFormatMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.m("锻造", ChatFormatting.GOLD), content);
    }
}
