package fun.wraq.process.func.multiblockactive.rightclick;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.multiblockactive.rightclick.drive.ItemChanger;
import fun.wraq.process.func.multiblockactive.rightclick.top.RightClickActivation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;

import java.util.List;

public class RightClickActiveHandler {

    public static final List<RightClickActivation> activations = List.of(
            new ItemChanger(Te.m("测试"), new Vec3(1099, 81, 40),
                    List.of(new ItemStack(ModItems.goldCoin.get())),
                    List.of(new ItemStack(ModItems.silverCoin.get(), 12)))
    );

    public static void detectNearPlayer(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.dimension().equals(Level.OVERWORLD)) {
            Level level = event.level;
            if (Tick.get() % 80 == 0) {
                activations.forEach(activation -> {
                    level.getEntitiesOfClass(Player.class, AABB.ofSize(activation.getCenterPos(), 32, 32, 32))
                            .stream().findAny().ifPresent(player -> {
                                activation.summonArmorStand(level);
                            });
                });
            }
        }
    }

    public static void handleOnPlayerRightClick(Player player) {
        if (player.isShiftKeyDown()) {
            activations.stream().filter(activation -> activation.getCenterPos().distanceTo(player.position()) < 4)
                    .findFirst()
                    .ifPresent(activation -> activation.handleRightClick(player));
        }
    }
}
