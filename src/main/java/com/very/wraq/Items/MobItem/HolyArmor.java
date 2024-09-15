package com.very.wraq.Items.MobItem;

import com.very.wraq.common.registry.ItemMaterial;
import com.very.wraq.entities.armor.HolyArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.function.Consumer;

public class HolyArmor extends MobArmor implements GeoItem {

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public HolyArmor(ItemMaterial Material, Type Slots, double AttackDamage, double Defence, double ManaDefence, double MobLevel, double BreakDefence, double BreakDefence0, double CritRate, double CritDamage, double HealSteal) {
        super(Material, Slots, AttackDamage, Defence, ManaDefence, MobLevel, BreakDefence, BreakDefence0, CritRate, CritDamage, HealSteal);
    }

    public HolyArmor(double Defence, double ManaDefence, double MobLevel) {
        super(Defence, ManaDefence, MobLevel);
    }

    public HolyArmor(ItemMaterial Material, Type Slots, double Defence, double ManaDefence, double MobLevel) {
        super(Material, Slots, Defence, ManaDefence, MobLevel);
    }

    public HolyArmor(ItemMaterial Material, Type Slots, String MobName) {
        super(Material, Slots, MobName);
    }

    public HolyArmor(String MobName) {
        super(MobName);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private HolyArmorRenderer renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                   EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new HolyArmorRenderer();

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    private PlayState predicate(AnimationState animationState) {
        animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
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
