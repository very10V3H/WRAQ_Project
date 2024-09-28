package fun.wraq.customized.uniform.bow;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.MySound;
import fun.wraq.core.MyArrow;
import fun.wraq.customized.WraqUniformCurios;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class BowCurios1 extends WraqBowUniformCurios {

    public BowCurios1(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        Compute.DescriptionPassive(components, Component.literal("精湛弓术").withStyle(style));
        components.add(Component.literal(" 普通箭矢攻击").withStyle(style).
                append(Component.literal("将额外释放一枚具有").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("100%基础伤害").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("箭矢").withStyle(style)));
        components.add(Component.literal(" 不仅是敏捷，力量、智慧对在恶劣环境中的猎手同样重要。").withStyle(style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("优胜劣汰").withStyle(hoverMainStyle());
    }

    public static WeakHashMap<Player, Boolean> onPlayerMap = new WeakHashMap<>();

    public static boolean isOn(Player player) {
        return WraqUniformCurios.isOn(BowCurios1.class, player);
    }

    public static WeakHashMap<Player, Integer> playerShootTick = new WeakHashMap<>();

    public static void playerShoot(Player player) {
        if (!isOn(player)) return;
        playerShootTick.put(player, player.getServer().getTickCount() + 4);
    }

    public static void tick(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            playerShootTick.forEach((player, integer) -> {
                if (player != null && integer == player.getServer().getTickCount()) {
                    arrowShoot(player);
                }
            });
        }
    }

    public static void arrowShoot(Player player) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5f, 1.0f);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        MySound.soundToNearPlayer(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.COMPOSTER);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.COMPOSTER);
    }

}
