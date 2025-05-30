package fun.wraq.render.gui.trade;

import com.mojang.logging.LogUtils;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class SingleItemChangeC2SPacket {

    private final ItemStack needStack;
    private final int needCount;
    private final ItemStack goods;

    public SingleItemChangeC2SPacket(ItemStack needStack, int needCount, ItemStack goods) {
        this.needStack = needStack;
        this.needCount = needCount;
        this.goods = goods;
    }

    public SingleItemChangeC2SPacket(FriendlyByteBuf buf) {
        this.needStack = buf.readItem();
        this.needCount = buf.readInt();
        this.goods = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(needStack);
        buf.writeInt(needCount);
        buf.writeItem(goods);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            List<SingleItemChangeRecipe> recipeList = SingleItemChangeRecipe.getRecipeList();
            SingleItemChangeRecipe recipe = null;
            boolean containRecipe = false;
            ItemStack material = new ItemStack(needStack.getItem(), needCount);
            for (SingleItemChangeRecipe singleItemChangeRecipe : recipeList) {
                if (stackEquals(singleItemChangeRecipe.needStack, material)
                        && stackEquals(singleItemChangeRecipe.goods, goods)) {
                    containRecipe = true;
                    if (SingleItemChangePurchaseLimit.check(serverPlayer, singleItemChangeRecipe)) {
                        recipe = singleItemChangeRecipe;
                    }
                    else {
                        // 超出限购
                        Compute.sendFormatMSG(serverPlayer, Te.s("交易", CustomStyle.styleOfGold),
                                Te.s("超出了限购次数!"));
                    }
                }
            }
            if (recipe != null) {
                if (InventoryOperation.checkItemRemoveIfHas(serverPlayer, List.of(material))) {
                    InventoryOperation.giveItemStack(serverPlayer, new ItemStack(goods.getItem(), goods.getCount()));
                    Compute.sendFormatMSG(serverPlayer, Te.s("交易", CustomStyle.styleOfGold),
                            Te.s("完成了一笔交易!"));
                    SingleItemChangePurchaseLimit.addTimes(serverPlayer, recipe);
                    LogUtils.getLogger().info("村民 {} 使用 {} 购买了 {} ", Name.get(serverPlayer),
                            material.getItem() + " * " + material.getCount(), goods.getItem());
                } else {
                    Compute.sendFormatMSG(serverPlayer, Te.s("交易", CustomStyle.styleOfGold),
                            Te.s("所需的物品不足。"));
                }
            } else {
                if (!containRecipe) {
                    // 配方不存在
                    Compute.sendFormatMSG(serverPlayer, Te.s("交易", CustomStyle.styleOfGold),
                            Te.s("配方不存在，请联系铁头!"));
                }
            }
        });
        return true;
    }

    public boolean stackEquals(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem().equals(stack2.getItem()) && stack1.getCount() == stack2.getCount();
    }
}
