package fun.wraq.series.overworld.chapter2.sky;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * AI-Generated, 2026-10-25
 * <p>
 * 重力旁路装置：天空城联合研院制式的便携装置。
 * 携带于背包中并处于启用状态时，旁路低重力区域对玩家重力的修改，
 * 使 {@link Compute#inLowGravityEnvironment(Player)} 在该玩家身上始终返回 false。
 * 手持右键可切换启用 / 关闭。
 */
public class GravityBypassDevice extends WraqItem {

    /** 装置启用状态，存储在物品自身 NBT 中，缺省为启用。 */
    public static final String ENABLED_TAG = "GravityBypassEnabled";

    public GravityBypassDevice(Properties properties) {
        super(properties);
    }

    /* ===== 状态读写 ===== */

    public static boolean isEnabled(ItemStack stack) {
        CompoundTag data = stack.getTagElement(Utils.MOD_ID);
        return data == null || !data.contains(ENABLED_TAG) || data.getBoolean(ENABLED_TAG);
    }

    public static void setEnabled(ItemStack stack, boolean enabled) {
        stack.getOrCreateTagElement(Utils.MOD_ID).putBoolean(ENABLED_TAG, enabled);
    }

    private static boolean isEnabledDevice(ItemStack stack) {
        return stack.getItem() instanceof GravityBypassDevice && isEnabled(stack);
    }

    /**
     * 玩家背包中是否存在处于启用状态的重力旁路装置（含副手）。
     */
    public static boolean isActive(Player player) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (isEnabledDevice(inventory.getItem(i))) {
                return true;
            }
        }
        return isEnabledDevice(player.getOffhandItem());
    }

    /* ===== 右键开关 ===== */

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            boolean enabled = !isEnabled(stack);
            setEnabled(stack, enabled);
            // 主动同步手持物品 NBT，使客户端提示与附魔光效即时刷新
            if (player instanceof ServerPlayer serverPlayer) {
                player.getInventory().setChanged();
                serverPlayer.inventoryMenu.broadcastChanges();
            }
            Compute.sendInfoToScreen(player, Te.s("重力旁路装置", CustomStyle.styleOfSky,
                    " · ", ChatFormatting.DARK_GRAY,
                    enabled ? "已启用" : "已关闭", enabled ? ChatFormatting.GREEN : ChatFormatting.GRAY));
            MySound.soundToPlayer(player, enabled
                    ? SoundEvents.STONE_BUTTON_CLICK_ON : SoundEvents.STONE_BUTTON_CLICK_OFF);
        }
        return InteractionResultHolder.success(stack);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isEnabled(stack);
    }

    @Override
    public List<Component> getWayDescription() {
        return List.of(
                Component.literal("手持右键切换启用 / 关闭").withStyle(ChatFormatting.WHITE),
                Component.literal("携带于背包中即可生效").withStyle(ChatFormatting.WHITE));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components,
                                TooltipFlag flag) {
        Style style = CustomStyle.styleOfSky;
        ComponentUtils.descriptionPassive(components, Te.s("重力旁路", style));
        components.add(Te.s(" 处于", "低重力区域", CustomStyle.styleOfFlexible, "（如天空城、尘月之梦）时，",
                "不再受到", "区域重力修改", style));
        components.add(Te.s(" 仅对区域性的重力修改生效，不影响", "其他来源", style, "的重力变化"));
        components.add(Te.s(" 携带于背包中即可生效", ChatFormatting.GRAY));
        components.add(Te.s(" 右键切换开关 · 当前状态: ",
                isEnabled(stack) ? "已启用" : "已关闭",
                isEnabled(stack) ? ChatFormatting.GREEN : ChatFormatting.GRAY));
        components.add(Te.s(""));
        components.add(ComponentUtils.getUniformSuffix());
        super.appendHoverText(stack, level, components, flag);
    }
}
