package fun.wraq.process.system.estate;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EstateKey extends WraqItem {

    private final int type;
    public EstateKey(Properties properties, int type) {
        super(properties);
        this.type = type;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        if (type == 0) {
            components.add(Te.s("右键", ChatFormatting.AQUA, "可以", "立刻回家!", ChatFormatting.AQUA));
            if (EstateUtil.clientEstateSerialNum != -1) {
                EstateInfo estateInfo = EstateInfo.values()[EstateUtil.clientEstateSerialNum];
                components.add(Te.s("右键将回到 ", estateInfo.estateName.getString(), ChatFormatting.AQUA));
            }
        } else {
            components.add(Te.s("右键", ChatFormatting.AQUA, "可以", "立刻前往资产!", ChatFormatting.AQUA));
            if (EstateUtil.clientRealEstateSerialNum != -1) {
                EstateInfo estateInfo = EstateInfo.values()[EstateUtil.clientRealEstateSerialNum];
                components.add(Te.s("右键将回到 ", estateInfo.estateName.getString(), CustomStyle.styleOfGold));
            }
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            if (type == 0) {
                player.getCooldowns().addCooldown(this, Tick.s(10));
                if (EstatePlayerData.hasEstate(player)) {
                    int serial = EstatePlayerData.getEstateSerial(player);
                    EstateInfo estateInfo = EstateInfo.values()[serial];
                    ServerPlayer serverPlayer = (ServerPlayer) player;
                    serverPlayer.teleportTo(Tick.server.overworld(),
                            estateInfo.doorBlockPos1.getX() + 0.5,
                            estateInfo.doorBlockPos1.getY(),
                            estateInfo.doorBlockPos1.getZ() + 0.5, 0, 0);
                    EstateUtil.sendMSG(player, Te.s("回到了温馨的家."));
                } else {
                    EstateUtil.sendMSG(player, Te.s("你暂时还没有房产，因此这件物品不会生效."));
                }
            } else {
                player.getCooldowns().addCooldown(this, Tick.s(10));
                if (EstatePlayerData.hasRealEstate(player)) {
                    int serial = EstatePlayerData.getRealEstateSerial(player);
                    EstateInfo estateInfo = EstateInfo.values()[serial];
                    ServerPlayer serverPlayer = (ServerPlayer) player;
                    serverPlayer.teleportTo(Tick.server.overworld(),
                            estateInfo.doorBlockPos1.getX() + 0.5,
                            estateInfo.doorBlockPos1.getY(),
                            estateInfo.doorBlockPos1.getZ() + 0.5, 0, 0);
                    EstateUtil.sendMSG(player, Te.s("回到了资产所在地."));
                } else {
                    EstateUtil.sendMSG(player, Te.s("你暂时还没有资产，因此这件物品不会生效."));
                }
            }
        }
        return super.use(level, player, interactionHand);
    }
}
