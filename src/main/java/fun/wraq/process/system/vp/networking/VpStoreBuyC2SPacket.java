package fun.wraq.process.system.vp.networking;

import fun.wraq.commands.stable.players.CustomPrefixCommand;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.process.func.plan.SimpleTierPaper;
import fun.wraq.process.func.security.Security;
import fun.wraq.process.system.vp.VpDataHandler;
import fun.wraq.process.system.vp.VpStore;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.text.ParseException;
import java.util.Calendar;
import java.util.function.Supplier;

public class VpStoreBuyC2SPacket {

    private final ItemStack goods;

    public VpStoreBuyC2SPacket(ItemStack goods) {
        this.goods = goods;
    }

    public VpStoreBuyC2SPacket(FriendlyByteBuf buf) {
        this.goods = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItem(this.goods);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            String name = serverPlayer.getName().getString();
            double price = VpStore.getPriceMap().get(goods.getItem());
            int count = VpStore.getCountMap().getOrDefault(goods.getItem(), 1);
            boolean buySuccessfully = false;
            int worldSoul5CostNum = 0;
            if (VpStore.getWorldSoul5Price().containsKey(goods.getItem())) {
                int needCount = VpStore.getWorldSoul5Price().get(goods.getItem());
                Inventory inventory = serverPlayer.getInventory();
                if (InventoryOperation.checkPlayerHasItem(inventory, ModItems.WORLD_SOUL_5.get(), needCount)) {

                    Security.recordItemStream(name, Security.SYSTEM,
                            new ItemStack(ModItems.WORLD_SOUL_5.get(), needCount), Security.RecordType.WORLD_SOUL_5_VP_PAY);
                    InventoryOperation.removeItem(inventory, ModItems.WORLD_SOUL_5.get(), needCount);

                    ItemStack itemStack = new ItemStack(goods.getItem(), count);

                    Security.recordItemStream(name, itemStack, Security.RecordType.WORLD_SOUL_5_VP_PAY);
                    InventoryOperation.giveItemStack(serverPlayer, itemStack);

                    buySuccessfully = true;
                    worldSoul5CostNum = needCount;
                }
            }

            if (!buySuccessfully && VpDataHandler.playerVpData.getOrDefault(name.toLowerCase(), 0d) >= price) {
                if (goods.getItem() instanceof SimpleTierPaper simpleTierPaper) {
                    int tier = simpleTierPaper.getTier();
                    try {
                        if (PlanPlayer.getPlayerTier(serverPlayer) > tier) {
                            Compute.sendInfoToScreen(serverPlayer, Te.s("当前已有更高阶的计划生效."));
                            return;
                        } else {
                            VpDataHandler.playerVpData.put(name.toLowerCase(),
                                    VpDataHandler.playerVpData.getOrDefault(name.toLowerCase(), 0d) - price);

                            if (PlanPlayer.getPlayerTier(serverPlayer) == tier) {
                                String oldOverDate = PlanPlayer.getOverDate(serverPlayer);
                                Calendar oldOverDateCalendar = Compute.StringToCalendar(oldOverDate);
                                oldOverDateCalendar.add(Calendar.DATE, SimpleTierPaper.lastDay);
                                PlanPlayer.setOverDate(serverPlayer, Compute.CalendarToString(oldOverDateCalendar));
                                Compute.sendInfoToScreen(serverPlayer, Te.s("续约成功!"));
                            } else {
                                Calendar calendar = Calendar.getInstance();
                                calendar.add(Calendar.DATE, SimpleTierPaper.lastDay);
                                String overDate = Compute.CalendarToString(calendar);
                                calendar = Calendar.getInstance();
                                calendar.add(Calendar.DATE, -1);
                                String lastRewardDate = Compute.CalendarToString(calendar);
                                PlanPlayer.setPlayerTier(serverPlayer, tier);
                                PlanPlayer.setOverDate(serverPlayer, overDate);
                                PlanPlayer.setLastRewardTime(serverPlayer, lastRewardDate);
                            }

                            CompoundTag data = serverPlayer.getPersistentData();
                            if (tier == 1) {
                                data.putInt(CustomPrefixCommand.customPrefixTimes,
                                        data.getInt(CustomPrefixCommand.customPrefixTimes) + 2);
                            }
                            if (tier == 2) {
                                data.putInt(CustomPrefixCommand.customPrefixTimes,
                                        data.getInt(CustomPrefixCommand.customPrefixTimes) + 6);
                            }

                            Security.recordVPStream(name, Security.SYSTEM, price, Security.RecordType.VP_PAY);
                            Security.recordItemStream(name, goods, Security.RecordType.VP_PAY);

                            Compute.clearPlayerScreen(serverPlayer);
                            Compute.setPlayerTitleAndSubTitle(serverPlayer, Te.m(goods.getDisplayName()),
                                    Te.s("已成功激活!", CustomStyle.styleOfWorld));
                            PlanPlayer.setLastRewardTime(serverPlayer, "");
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    VpDataHandler.playerVpData.put(name.toLowerCase(), VpDataHandler.playerVpData.getOrDefault(name.toLowerCase(), 0d) - price);
                    ItemStack itemStack = new ItemStack(goods.getItem(), count);

                    Security.recordVPStream(name, Security.SYSTEM, price, Security.RecordType.VP_PAY);
                    Security.recordItemStream(name, goods, Security.RecordType.VP_PAY);

                    InventoryOperation.giveItemStack(serverPlayer, itemStack);
                }
                VpDataHandler.sendPlayerVpValue(serverPlayer);
                buySuccessfully = true;
            }

            if (buySuccessfully) {
                Component component = null;
                if (worldSoul5CostNum > 0) {
                    component = Component.literal("本次购买花费了 ").withStyle(ChatFormatting.WHITE).
                            append(ModItems.WORLD_SOUL_5.get().getDefaultInstance().getDisplayName()).
                            append(Component.literal(" * " + worldSoul5CostNum).withStyle(ChatFormatting.AQUA));
                } else {
                    component = Component.literal("本次购买花费了 ").withStyle(ChatFormatting.WHITE).
                            append(Component.literal(String.format("%.0f", price) + "vp").withStyle(ChatFormatting.AQUA)).
                            append(Component.literal(" 账户剩余 ").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal(VpDataHandler.getPlayerVp(name) + "vp").withStyle(ChatFormatting.AQUA));
                }
                Compute.sendInfoToScreen(serverPlayer, Te.s("购买成功!", component));
            } else {
                Compute.sendInfoToScreen(serverPlayer, Te.s("所需资源不足."));
            }
        });
        return true;
    }
}
