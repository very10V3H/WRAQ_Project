package com.Very.very.ValueAndTools;

import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItemProperties {
    public static void addCustomBowProperties()
    {
        MakeBow(Moditems.Mybow.get());
        MakeBow(Moditems.forestbow.get());
        MakeBow(Moditems.plainbow.get());
        MakeBow(Moditems.volcanobow.get());
        MakeBow(Moditems.skybow.get());
        MakeBow(Moditems.NetherBow.get());
    }
    private static void MakeBow(Item item)
    {
        ItemProperties.register(item, new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)(p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(item, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
            return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
        });
    }
}
