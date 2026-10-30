package fun.wraq.series.overworld.chapter2.sky;

import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * AI-Generated, 2026-10-30
 * Sky-Line 副手：天空科技。
 * 四个配置槽可放入平原/森林/澈源/火山意志，提供对应被动加成。
 */
@SuppressWarnings({"deprecation"})
public class SkyLineOffhand extends WraqOffHandItem implements GeoItem {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public SkyLineOffhand(Properties properties) {
        super(properties.stacksTo(1), Component.literal("手环"));
        Utils.adaptivePower.put(this, 5d);
        Utils.expUp.put(this, 0.05d);
        Utils.movementSpeedCommon.put(this, 0.05d);
        Utils.manaRecover.put(this, 5d);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, state -> PlayState.STOP));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private SkyLineRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    this.renderer = new SkyLineRenderer();
                }
                return this.renderer;
            }
        });
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSky;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        Style style = getMainStyle();
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("天空科技", style));
        components.add(Te.s(" 主手Shift+右键", ChatFormatting.AQUA, "打开配置界面"));

        int plain = SkyLineData.count(stack, fun.wraq.common.registry.ModItems.PLAIN_RUNE.get());
        int forest = SkyLineData.count(stack, fun.wraq.common.registry.ModItems.FOREST_RUNE.get());
        int lake = SkyLineData.count(stack, fun.wraq.common.registry.ModItems.LAKE_RUNE.get());
        int volcano = SkyLineData.count(stack, fun.wraq.common.registry.ModItems.VOLCANO_RUNE.get());
        if (plain + forest + lake + volcano > 0) {
            ComponentUtils.descriptionPassive(components, Te.s("已装配", style));
            if (plain > 0) {
                components.add(Te.s(" 平原意志 *" + plain + ": ", CustomStyle.styleOfLife,
                        "+" + format(plain * 0.5) + "%生命回复", CustomStyle.styleOfLife));
            }
            if (forest > 0) {
                components.add(Te.s(" 森林意志 *" + forest + ": ", CustomStyle.styleOfForest,
                        "+" + (forest * 5) + "护甲/魔抗", CustomStyle.styleOfForest));
            }
            if (lake > 0) {
                components.add(Te.s(" 澈源意志 *" + lake + ": ", CustomStyle.styleOfLake,
                        "+" + (lake * 5) + "技能急速, +" + (lake * 5) + "法力回复", CustomStyle.styleOfLake));
            }
            if (volcano > 0) {
                components.add(Te.s(" 火山意志 *" + volcano + ": ", CustomStyle.styleOfVolcano,
                        "+" + format(volcano * 2.5) + "%物理/魔法攻击", CustomStyle.styleOfVolcano));
            }
        }
        return components;
    }

    private static String format(double value) {
        return String.format("%.1f", value).replace(".0", "");
    }

    @Override
    public Component getSuffix() {
        return Te.s("Sky-Line", CustomStyle.styleOfSky);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (hand == InteractionHand.MAIN_HAND && player.isShiftKeyDown()) {
            if (player instanceof ServerPlayer serverPlayer) {
                SkyLineMenu.open(serverPlayer);
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level,
                                @Nonnull List<Component> components,
                                @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, level, components, flag);
    }

    /** 副手为 Sky-Line 时，按配置槽返回对应动态属性。 */
    public static double getDynamicBonus(Player player, Map<net.minecraft.world.item.Item, Double> map) {
        if (!(player.getOffhandItem().getItem() instanceof SkyLineOffhand)) {
            return 0;
        }
        ItemStack device = player.getOffhandItem();
        if (map == Utils.percentHealthRecover) {
            return SkyLineData.count(device, fun.wraq.common.registry.ModItems.PLAIN_RUNE.get()) * 0.005d;
        }
        if (map == Utils.defence || map == Utils.manaDefence) {
            return SkyLineData.count(device, fun.wraq.common.registry.ModItems.FOREST_RUNE.get()) * 5d;
        }
        if (map == Utils.coolDownDecrease) {
            return SkyLineData.count(device, fun.wraq.common.registry.ModItems.LAKE_RUNE.get()) * 5d;
        }
        if (map == Utils.manaRecover) {
            return SkyLineData.count(device, fun.wraq.common.registry.ModItems.LAKE_RUNE.get()) * 5d;
        }
        if (map == Utils.percentAttackDamageEnhance || map == Utils.percentManaDamageEnhance) {
            return SkyLineData.count(device, fun.wraq.common.registry.ModItems.VOLCANO_RUNE.get()) * 0.025d;
        }
        return 0;
    }
}
