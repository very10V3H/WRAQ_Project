package fun.wraq.series.overworld.sakura.Scarecrow;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.entities.armor.WheatArmorRenderer;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class WheatArmorChest extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public WheatArmorChest(ModArmorMaterials Material, Type Slots) {
        super(Material, Slots, new Properties().rarity(Rarity.UNCOMMON));
        Utils.maxHealth.put(this, 2000d);
        Utils.armorTag.put(this, 1d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = CustomStyle.styleOfWheat;
        Compute.forgingHoverName(stack);
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("胸甲").withStyle(style)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionPassive(components, Component.literal("藤甲"));
        components.add(Component.literal("1.减免受到的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.attackDamageValue("30%")));
        components.add(Component.literal("2.提高受到的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaDamageValue("30%")));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(Component.literal("Scarecrow_ChestPlate").withStyle(style).withStyle(ChatFormatting.ITALIC));
        ComponentUtils.suffixOfSakura(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private WheatArmorRenderer renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                   EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new WheatArmorRenderer();

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    private PlayState predicate(AnimationState animationState) {
        animationState.getController().setAnimation(RawAnimation.begin().then("animation.model.new", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
