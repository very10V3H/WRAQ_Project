package fun.wraq.process.func.multiblockactive.rightclick;

import fun.wraq.commands.changeable.TextCommand;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.func.multiblockactive.rightclick.drive.ItemEnhancer;
import fun.wraq.process.func.multiblockactive.rightclick.top.RightClickActivation;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.moontain.MoontainItems;
import fun.wraq.series.moontain.equip.weapon.MoontainUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class RightClickActiveHandler {

    public static final List<RightClickActivation> activations = List.of(
            new ItemEnhancer(Te.s("强化望山武器", CustomStyle.styleOfMoontain), new Vec3(1099, 81, 40),
                    List.of(new ItemStack(MoontainItems.WEAPON_ENHANCER.get())),
                    MoontainUtils.weaponEnhanceCondition, MoontainUtils.weaponEnhanceOperation,
                    List.of(Te.s("至多可以将", "望山武器", CustomStyle.styleOfMoontain,
                            "提升到", "「仅存于梦」", CustomStyle.styleOfSakura),
                            Te.s("成功概率随品质的提升而降低", CustomStyle.styleOfPlain))),

            new ItemEnhancer(Te.s("强化望山防具", CustomStyle.styleOfMoontain), new Vec3(1099, 91, 40),
                    List.of(new ItemStack(MoontainItems.ARMOR_ENHANCER.get())),
                    MoontainUtils.armorsEnhanceCondition, MoontainUtils.armorsEnhanceOperation,
                    List.of(Te.s("至多可以将", "望山防具", CustomStyle.styleOfMoontain,
                            "提升到", "「仅存于梦」", CustomStyle.styleOfSakura),
                            Te.s("成功概率随品质的提升而降低", CustomStyle.styleOfPlain))),

            new ItemEnhancer(Te.s("强化望山饰品数值", CustomStyle.styleOfMoontain), new Vec3(1099, 101, 40),
                    List.of(new ItemStack(MoontainItems.CURIOS_RATE_ENHANCER.get())),
                    MoontainUtils.curiosEnhanceRateCondition, MoontainUtils.curiosEnhanceRateOperation,
                    List.of(Te.s("至多可以将", "望山饰品属性", CustomStyle.styleOfMoontain,
                            "提升到", "数值上限", CustomStyle.styleOfRed),
                            Te.s("每次提升", "0.1比例数值", ChatFormatting.AQUA))),

            new ItemEnhancer(Te.s("强化望山饰品数值上限", CustomStyle.styleOfMoontain), new Vec3(1099, 111, 40),
                    List.of(new ItemStack(MoontainItems.CURIOS_FULL_RATE_ENHANCER.get())),
                    MoontainUtils.curiosEnhanceFullRateCondition, MoontainUtils.curiosEnhanceFullRateOperation,
                    List.of(Te.s("至多可以将", "望山饰品属性上限", CustomStyle.styleOfMoontain,
                            "提升到", "5.0", CustomStyle.styleOfRed),
                            Te.s("每次提升", "0.1比例数值上限", ChatFormatting.AQUA)))
    );

    public static void detectNearPlayer(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.dimension().equals(Level.OVERWORLD)) {
            Level level = event.level;
            if (Tick.get() % 80 == 0) {
                activations.forEach(activation -> {
                    level.getEntitiesOfClass(Player.class, AABB.ofSize(activation.getCenterPos(), 32, 32, 32))
                            .stream().findAny().ifPresent(player -> {
                                level.getEntitiesOfClass(ArmorStand.class,
                                                AABB.ofSize(activation.getCenterPos(), 8, 8, 8))
                                        .forEach(armorStand -> armorStand.remove(Entity.RemovalReason.KILLED));
                                List<Component> components = new ArrayList<>();
                                components.add(activation.getTitle());
                                components.addAll(activation.getDescription());
                                TextCommand.summonArmorStand(components, level, activation.getCenterPos());
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
