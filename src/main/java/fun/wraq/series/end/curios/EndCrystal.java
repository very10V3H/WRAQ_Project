package fun.wraq.series.end.curios;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.events.mob.instance.instances.dimension.CitadelGuardianInstance;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class EndCrystal extends WraqItem {

    public EndCrystal(Properties properties) {
        super(properties, true, true);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal(" 凝聚终界能量的水晶，具有扭曲时间与空间的强大能量").withStyle(CustomStyle.styleOfEnd));
        components.add(Component.literal(" - 于终界寂域中心使用，前往").withStyle(ChatFormatting.WHITE).
                append(Component.literal(" 影珀遗迹").withStyle(CustomStyle.styleOfEnd)));
        super.appendHoverText(stack, level, components, flag);
    }

    public static Map<Player, Integer> teleportTick = new WeakHashMap<>();

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            if (serverPlayer.position().distanceTo(new Vec3(24, 87, -205)) < 6 && level.dimension().equals(Level.END)) {
                Compute.playerItemUseWithRecord(player);
                Compute.playerItemCoolDown(player, this, 5);
                teleportTick.put(player, Tick.get() + 30);
                ParticleProvider.createBallDisperseParticle(ParticleTypes.END_ROD, (ServerLevel) level,
                        player.getEyePosition(), 0.5, 20);
                MySound.soundToNearPlayer(player, SoundEvents.END_PORTAL_FRAME_FILL);
            } else {
                Compute.sendFormatMSG(player, Te.s("终界", CustomStyle.styleOfEnd), Te.s("这里的",
                        "终界能量", CustomStyle.styleOfEnd, "不足以将你折跃至", "影珀遗迹", CustomStyle.styleOfEnd));
            }
            player.getCooldowns().addCooldown(this, 40);
        }
        return super.use(level, player, interactionHand);
    }

    public static void tick(Player player) {
        if (teleportTick.containsKey(player) && teleportTick.get(player) < Tick.get()) {
            teleportTick.remove(player);
            if (player.level().dimension().equals(Level.END)) {
                Vec3 pos = CitadelGuardianInstance.getInstance().pos;
                player.teleportTo((ServerLevel) player.level(), pos.x, pos.y, pos.z, Set.of(), 0, 0);
            }
        }
    }
}
