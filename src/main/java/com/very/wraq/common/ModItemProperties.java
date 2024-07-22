package com.very.wraq.common;

import com.very.wraq.common.registry.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItemProperties {
    public static void addCustomBowProperties() {
        MakeBow(ModItems.ForestBow0.get());
        MakeBow(ModItems.PlainBow0.get());
        MakeBow(ModItems.VolcanoBow0.get());
        MakeBow(ModItems.SkyBow.get());
        MakeBow(ModItems.NetherBow.get());
        MakeBow(ModItems.SkyBossBow.get());
        MakeBow(ModItems.WitherBow0.get());
        MakeBow(ModItems.WitherBow1.get());
        MakeBow(ModItems.WitherBow2.get());
        MakeBow(ModItems.WitherBow3.get());
    }

    private static void MakeBow(Item item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0f;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0f : (float) (p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / 20.0f;
            }
        });
        ItemProperties.register(item, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
            return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0f : 0.0f;
        });
    }
}
