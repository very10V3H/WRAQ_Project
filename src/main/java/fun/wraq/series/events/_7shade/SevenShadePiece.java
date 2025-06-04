package fun.wraq.series.events._7shade;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.networking.ModNetworking;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.gems.WraqGem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SevenShadePiece extends WraqItem {

    public static final List<Item> pieces = new ArrayList<>();
    public SevenShadePiece(Properties properties, List<WraqGem.AttributeMapValue> attributes) {
        super(properties, false, true);
        pieces.add(this);
        attributes.forEach(attribute -> {
            attribute.attributeMap().put(this, attribute.value());
        });
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s("来自遥远七海的七影碎片", CustomStyle.styleOfWorld, ChatFormatting.ITALIC));
        components.add(Te.s(" 若能跨越七海，抵达旅途的终点，", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        components.add(Te.s(" 便能在无梦之眠中静静沉睡.", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        components.add(Te.s(" - 右键使用将永久调整自身属性"));
        components.add(Te.s(" - 调整的属性："));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag data = getData(player);
            if (data.contains(this.toString()) && data.getBoolean(this.toString())) {
                sendMSG(player, Te.s("你已经使用过这个七影碎片了."));
            } else {
                data.putBoolean(this.toString(), true);
                sendMSG(player, Te.s(this, "已被激活，在", ModItems.ID_CARD.get(), "上方可查看已激活的七影碎片."));
                Compute.playerItemUseWithRecord(player);
                sendDataToClient(player);
            }
        }
        return super.use(level, player, interactionHand);
    }

    public static final String DATA_KEY = "SevenShadePiece";
    public static CompoundTag getData(Player player) {
        return Compute.getPlayerSpecificKeyCompoundTagData(player, DATA_KEY);
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("七影", CustomStyle.styleOfWorld), content);
    }

    public static double getEnhanceValue(Player player, Map<Item, Double> map) {
        double value = 0;
        CompoundTag data = getData(player);
        for (Item piece : pieces) {
            if (data.contains(piece.toString()) && data.getBoolean(piece.toString())
                    && map.containsKey(piece)) {
                value += map.get(piece);
            }
        }
        return value;
    }

    public static List<Item> clientActivePieces = new ArrayList<>();
    public static List<Item> getActivePieces(Player player) {
        List<Item> list = new ArrayList<>();
        CompoundTag data = getData(player);
        for (Item piece : pieces) {
            if (data.contains(piece.toString()) && data.getBoolean(piece.toString())) {
                list.add(piece);
            }
        }
        return list;
    }

    public static void clearActivePieces(Player player) {
        CompoundTag data = getData(player);
        for (Item piece : pieces) {
            data.remove(piece.toString());
        }
        sendDataToClient(player);
    }

    public static void sendDataToClient(Player player) {
        ModNetworking.sendToClient(new SevenShadePieceS2CPacket(getActivePieces(player)), player);
    }
}
