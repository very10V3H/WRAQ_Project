package fun.wraq.series.instance.series.warden;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.events.mob.instance.instances.element.WardenInstance;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WardenMatrix extends WraqItem {

    public static Vec3 ancientCityPos = new Vec3(2267.5, -46.5, -703.5);
    public static Vec2 spawnPosRot = new Vec2(-90, 0);

    public WardenMatrix(Properties properties) {
        super(properties, true, true);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag p_41424_) {
        if (level != null) {
            components.add(Component.literal("右键").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("碾碎").withStyle(ChatFormatting.GRAY)).
                    append(Component.literal("此物，前往").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("远古之城").withStyle(CustomStyle.styleOfWarden)));
            components.add(Te.s(" 为应对", "黑暗", CustomStyle.styleOfWarden,
                    "你最好带几瓶", "夜视药水", CustomStyle.styleOfFlexible));
        }
        super.appendHoverText(stack, level, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            if (player.experienceLevel >= 220) {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                Compute.playerItemUseWithRecord(player);
                serverPlayer.teleportTo(serverPlayer.getServer().getLevel(Level.OVERWORLD),
                        ancientCityPos.x, ancientCityPos.y, ancientCityPos.z, spawnPosRot.x, spawnPosRot.y);
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 50));
            } else {
                WardenInstance.sendFormatMSG(player, Te.s("需要",
                        "220级", CustomStyle.styleOfWarden, "才能前往", "远古城市", CustomStyle.styleOfWarden));
            }
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
