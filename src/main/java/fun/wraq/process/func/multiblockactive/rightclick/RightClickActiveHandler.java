package fun.wraq.process.func.multiblockactive.rightclick;

import fun.wraq.commands.changeable.TextCommand;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.multiblockactive.rightclick.drive.GemEnhancer;
import fun.wraq.process.func.multiblockactive.rightclick.drive.ItemDecomposer;
import fun.wraq.process.func.multiblockactive.rightclick.drive.ItemEnhancer;
import fun.wraq.process.func.multiblockactive.rightclick.top.RightClickActivation;
import fun.wraq.process.system.ore.OreItems;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.gems.WraqGem;
import fun.wraq.series.instance.series.harbinger.HarbingerItems;
import fun.wraq.series.instance.series.harbinger.weapon.HarbingerMainHand;
import fun.wraq.series.instance.series.harbinger.weapon.HarbingerWeaponMaterial;
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
            new ItemEnhancer(Te.s("强化望山武器", CustomStyle.styleOfMoontain), new Vec3(1926, 152, -908),
                    List.of(new ItemStack(MoontainItems.WEAPON_ENHANCER.get())),
                    MoontainUtils.weaponEnhanceCondition, MoontainUtils.weaponEnhanceOperation,
                    List.of(Te.s("至多可以将", "望山武器", CustomStyle.styleOfMoontain,
                            "提升到", "20阶", CustomStyle.styleOfSakura),
                            Te.s("成功概率随阶数的提升而降低", CustomStyle.styleOfPlain))),

            new ItemEnhancer(Te.s("强化望山防具", CustomStyle.styleOfMoontain), new Vec3(1917, 152, -908),
                    List.of(new ItemStack(MoontainItems.ARMOR_ENHANCER.get())),
                    MoontainUtils.armorsEnhanceCondition, MoontainUtils.armorsEnhanceOperation,
                    List.of(Te.s("至多可以将", "望山防具", CustomStyle.styleOfMoontain,
                            "提升到", "20阶", CustomStyle.styleOfSakura),
                            Te.s("成功概率随阶数的提升而降低", CustomStyle.styleOfPlain))),

            new ItemEnhancer(Te.s("强化望山饰品数值", CustomStyle.styleOfMoontain), new Vec3(1926, 152, -922),
                    List.of(new ItemStack(MoontainItems.CURIOS_RATE_ENHANCER.get())),
                    MoontainUtils.curiosEnhanceRateCondition, MoontainUtils.curiosEnhanceRateOperation,
                    List.of(Te.s("至多可以将", "望山饰品属性", CustomStyle.styleOfMoontain,
                            "提升到", "数值上限", CustomStyle.styleOfRed),
                            Te.s("每次提升", "0.1比例数值", ChatFormatting.AQUA))),

            new ItemEnhancer(Te.s("强化望山饰品数值上限", CustomStyle.styleOfMoontain), new Vec3(1917, 152, -922),
                    List.of(new ItemStack(MoontainItems.CURIOS_FULL_RATE_ENHANCER.get())),
                    MoontainUtils.curiosEnhanceFullRateCondition, MoontainUtils.curiosEnhanceFullRateOperation,
                    List.of(Te.s("至多可以将", "望山饰品属性上限", CustomStyle.styleOfMoontain,
                            "提升到", "5.0", CustomStyle.styleOfRed),
                            Te.s("每次提升", "0.1比例数值上限", ChatFormatting.AQUA))),

            new ItemDecomposer(Te.s("分解望山饰品", CustomStyle.styleOfMoontain), new Vec3(1926, 152, -931),
                    List.of(
                        List.of(new ItemStack(MoontainItems.CHEST_CURIOS.get())),
                        List.of(new ItemStack(MoontainItems.HAND.get())),
                        List.of(new ItemStack(MoontainItems.RING.get())),
                        List.of(new ItemStack(MoontainItems.BRACELET.get()))
                    ),
                    List.of(new ItemStack(MoontainItems.CURIOS_PIECE.get()))),

            new GemEnhancer(List.of(Te.s("升级", "德朗斯蒂克", CustomStyle.styleOfWorld , "宝石")), new Vec3(1816, 72, 298),
                    WraqGem.GEM_ENHANCE_TYPE_MAP.get(1),
                    List.of(
                            new ItemStack(OreItems.WRAQ_ORE_1_ITEM.get(), 256),
                            new ItemStack(ModItems.REFINED_PIECE.get(), 64)
                    ),
                    List.of(
                            Te.s("德朗斯蒂克宝石", CustomStyle.styleOfWorld)
                    )),

            new GemEnhancer(List.of(Te.s("升级", "艾里蒙特", CustomStyle.styleOfEntropy , "宝石")), new Vec3(1824, 72, 298),
                    WraqGem.GEM_ENHANCE_TYPE_MAP.get(2),
                    List.of(
                            new ItemStack(OreItems.WRAQ_ORE_2_ITEM.get(), 256),
                            new ItemStack(ModItems.REFINED_PIECE.get(), 64)
                    ),
                    List.of(
                            Te.s("艾里蒙特宝石", CustomStyle.styleOfEntropy)
                    )),

            new GemEnhancer(List.of(Te.s("升级", "下界", CustomStyle.styleOfEntropy , "宝石")), new Vec3(1832, 72, 298),
                    WraqGem.GEM_ENHANCE_TYPE_MAP.get(3),
                    List.of(
                            new ItemStack(OreItems.WRAQ_ORE_4_ITEM.get(), 256),
                            new ItemStack(ModItems.REFINED_PIECE.get(), 64)
                    ),
                    List.of(
                            Te.s("下界宝石", CustomStyle.styleOfEntropy)
                    )),

            new GemEnhancer(List.of(Te.s("升级", "绯樱岛、副本", CustomStyle.styleOfEntropy , "宝石")), new Vec3(1848, 68, 284),
                    WraqGem.GEM_ENHANCE_TYPE_MAP.get(4),
                    List.of(
                            new ItemStack(OreItems.WRAQ_ORE_3_ITEM.get(), 256),
                            new ItemStack(ModItems.REFINED_PIECE.get(), 64)
                    ),
                    List.of(
                            Te.s("船厂宝石", CustomStyle.styleOfShip),
                            Te.s("绯樱岛宝石", CustomStyle.styleOfShip),
                            Te.s("尘月宫宝石", CustomStyle.styleOfMoon)
                    )),

            new ItemEnhancer(Te.s("强化鹰眼武器", CustomStyle.styleOfHarbinger), new Vec3(1938, 86, 1686),
                    List.of(new ItemStack(HarbingerItems.HARBINGER_INGOT.get(), 20)),
                    HarbingerMainHand.enhanceCondition, HarbingerMainHand.enhanceOperation,
                    List.of(Te.s("至多可以将", "鹰眼武器被动层数", CustomStyle.styleOfHarbinger,
                                    "提升到", "20", CustomStyle.styleOfRed),
                            Te.s("每次提升", "1层", CustomStyle.styleOfHarbinger))),

            new ItemEnhancer(Te.s("强化鹰眼武器部件", CustomStyle.styleOfHarbinger), new Vec3(1867, 81, 1696),
                    List.of(
                            new ItemStack(HarbingerItems.HARBINGER_INGOT.get(), 32),
                            new ItemStack(HarbingerItems.RAW_IRON_NUGGET.get(), 128),
                            new ItemStack(HarbingerItems.RAW_IRON_INGOT.get(), 32)
                    ),
                    HarbingerWeaponMaterial.enhanceCondition, HarbingerWeaponMaterial.enhanceOperation,
                    List.of(Te.s("至多可以将", "鹰眼武器部件", CustomStyle.styleOfHarbinger,
                                    "提升到", "不可思议", CustomStyle.styleOfPower, "等阶"),
                            Te.s("每次提升", "1阶", CustomStyle.styleOfHarbinger)))
    );

    public static void detectNearPlayer(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.dimension().equals(Level.OVERWORLD)) {
            Level level = event.level;
            if (Tick.get() % 80 == 0) {
                activations.forEach(activation -> {
                    Compute.getNearPlayer(level, activation.getCenterPos(), 16).stream()
                            .findAny().ifPresent(player -> {
                                level.getEntitiesOfClass(ArmorStand.class,
                                                AABB.ofSize(activation.getCenterPos(), 8, 8, 8))
                                        .forEach(armorStand -> armorStand.remove(Entity.RemovalReason.KILLED));
                                List<Component> components = new ArrayList<>();
                                components.addAll(activation.getTitle());
                                components.addAll(activation.getDescription());
                                TextCommand.summonArmorStand(components, level, activation.getCenterPos()
                                        .add(0.5, 0.5, 0.5));
                    });
                });
            }
        }
    }

    public static void handleOnPlayerRightClick(Player player) {
        activations.stream().filter(activation -> activation.getCenterPos().distanceTo(player.position()) < 4)
                .findFirst()
                .ifPresent(activation -> {
                    if (player.isShiftKeyDown()) {
                        activation.handleRightClick(player);
                    } else {
                        Compute.sendFormatMSG(player, Te.s("交互", ChatFormatting.AQUA),
                                Te.s("使用shift + 右键来交互"));
                    }
                });
    }
}
