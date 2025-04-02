package fun.wraq.series.events.qingMing;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.List;

public class QingTuan extends WraqItem {

    public static final String PREFIX_1 = "qing_ming_prefix_1";
    public static final String PREFIX_2 = "qing_ming_prefix_2";

    public QingTuan(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Style style = CustomStyle.styleOfHealth;
        QingMingSuffix(components);
        components.add(Component.literal(" "));
        components.add(Te.s(" 捣青草为汁，和粉作团，色如碧玉。", style));
        components.add(Te.s(" 右键食用，", "获得持续5min的:", ChatFormatting.AQUA));
        components.add(Te.s(" 1.", style, "饱和", CustomStyle.MUSHROOM_STYLE));
        components.add(Te.s(" 2.", style, ComponentUtils.AttributeDescription.movementSpeed("25%")));
        components.add(Te.s(" 3.", style, ComponentUtils.AttributeDescription.healthRecover("1%")));
        components.add(Te.s(" 4.", style, "清明活动期间，获得", "20%额外产出", ChatFormatting.GOLD));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            Compute.playerItemUseWithRecord(player);
            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, Tick.min(5)));
            StableAttributesModifier.addM(player, StableAttributesModifier.playerMovementSpeedModifier,
                    "QingTuanEffectMovementSpeedIncrease", 0.25, Tick.get() + Tick.min(5));
            StableAttributesModifier.addM(player, StableAttributesModifier.playerPercentHealthRecoverModifier,
                    "QingTuanEffectHealthRecoverIncrease", 0.01, Tick.get() + Tick.min(5));
            if (isInActivityDate()) {
                StableAttributesModifier.addM(player, StableAttributesModifier.playerExHarvestModifier,
                        "QingTuanEffectExHarvestIncrease", 0.2, Tick.get() + Tick.min(5));
            }
            Compute.sendEffectLastTime(player, this, Tick.min(5));
            player.getCooldowns().addCooldown(this, Tick.s(10));
            MySound.soundToPlayer(player, SoundEvents.GENERIC_EAT);
        }
        return super.use(level, player, interactionHand);
    }

    public static void QingMingSuffix(List<Component> components) {
        components.add(Component.literal(" 活动").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" · ").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("清明").withStyle(CustomStyle.styleOfLife)));
    }

    public static Component getQingMingSuffix() {
        return Te.s(" 活动", ChatFormatting.LIGHT_PURPLE,
                " · ", ChatFormatting.AQUA, "清明", CustomStyle.styleOfLife);
    }

    // 活动在4.8日00:00自动关闭（无需重启）
    public static boolean isInActivityDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) == 2025 && calendar.get(Calendar.MONTH) == Calendar.APRIL
                && calendar.get(Calendar.DAY_OF_MONTH) <= 7;
    }
}
