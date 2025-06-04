package fun.wraq.Items.money;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import fun.wraq.process.system.element.piece.ElementPieceData;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UDisk extends WraqItem {

    public UDisk(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 维瑞阿契银行发行的U盾。用于远程安全支付。"));
        components.add(Component.literal(" 将U盾放置在背包内，会将背包内的所有金币、银币、铜币直接存入银行账户。"));
        components.add(Component.literal(" 如果不想存入，将U盾放进你的下界合金背包就可以了，不是吗？"));
        components.add(Component.literal(" 维瑞阿契银行与本源研究所达成合作，如果你背包内有一组").withStyle(ChatFormatting.WHITE).
                append(ModItems.WORLD_SOUL_1.get().getDefaultInstance().getDisplayName()).
                append(Component.literal("那么它们将会被自动转化为").withStyle(ChatFormatting.WHITE)).
                append(ModItems.WORLD_SOUL_2.get().getDefaultInstance().getDisplayName()));
        components.add(Te.s(" 与此同时，维瑞阿契银行科技部门还研发了", "元素量子化技术", CustomStyle.styleOfWorld));
        components.add(Te.s(" 在背包内的", "微型元素碎片", "将被转化为", "量子元素", CustomStyle.styleOfWorld));
        if (getElementCollectionStatus(stack)) {
            components.add(Te.s(" 量子元素收集: ", CustomStyle.styleOfWorld, "已开启", ChatFormatting.GREEN));
        } else {
            components.add(Te.s(" 量子元素收集: ", CustomStyle.styleOfWorld, "已关闭", ChatFormatting.RED));
        }
        components.add(Te.s(" ┣右键打开:", ChatFormatting.AQUA, "量子元素终端", CustomStyle.styleOfWorld));
        components.add(Te.s(" ┗潜行右键:", ChatFormatting.GREEN,
                "开启/关闭量子元素收集", CustomStyle.styleOfWorld));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            if (player.isShiftKeyDown()) {
                ItemStack stack = player.getMainHandItem();
                boolean status = !getElementCollectionStatus(stack);
                setElementCollectionStatus(stack, status);
                if (status) {
                    Compute.sendFormatMSG(player, Te.s("量子元素", CustomStyle.styleOfWorld),
                            Te.s("量子元素收集", CustomStyle.styleOfWorld, " 已开启", ChatFormatting.GREEN));
                } else {
                    Compute.sendFormatMSG(player, Te.s("量子元素", CustomStyle.styleOfWorld),
                            Te.s("量子元素收集", CustomStyle.styleOfWorld, " 已关闭", ChatFormatting.RED));
                }
                MySound.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
            } else {
                ModNetworking.sendToClient(new ScreenSetS2CPacket(7), player);
                ElementPieceData.sendDataToClient(player);
            }
        }
        return super.use(level, player, interactionHand);
    }

    private static final String STATUS = "ElementCollectionStatus";
    public static void setElementCollectionStatus(ItemStack stack, boolean open) {
        stack.getOrCreateTagElement(Utils.MOD_ID).putBoolean(STATUS, open);
    }

    public static boolean getElementCollectionStatus(ItemStack stack) {
        return stack.getOrCreateTagElement(Utils.MOD_ID).getBoolean(STATUS);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return getElementCollectionStatus(stack);
    }
}
