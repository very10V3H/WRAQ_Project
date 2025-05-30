package fun.wraq.series.events.dragonboat;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MeatZongZi extends WraqItem {

    public MeatZongZi(Properties properties) {
        super(properties.food(new FoodProperties.Builder()
                .alwaysEat()
                .nutrition(16)
                .meat()
                .build()));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s("香喷喷肉粽"));
        components.add(Te.s(" - 食用后获得持续300s的", "普通伤害提升", CustomStyle.styleOfMoon, "与饱和"));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (!level.isClientSide) {
            StableAttributesModifier.addM(livingEntity,
                    StableAttributesModifier.playerCommonDamageEnhance,
                    "MeatZongZi", 0.25, Tick.get() + Tick.s(300), this);
            livingEntity.addEffect(new MobEffectInstance(MobEffects.SATURATION, Tick.s(300)));
        }
        return super.finishUsingItem(itemStack, level, livingEntity);
    }
}
