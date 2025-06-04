package fun.wraq.common.registry;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItemProperties {
    public static void addCustomBowProperties() {
        MakeBow(fun.wraq.common.registry.ModItems.FOREST_BOW_0.get());
        MakeBow(fun.wraq.common.registry.ModItems.PLAIN_BOW_0.get());
        MakeBow(fun.wraq.common.registry.ModItems.VOLCANO_BOW_0.get());
        MakeBow(fun.wraq.common.registry.ModItems.SKY_BOW.get());
        MakeBow(fun.wraq.common.registry.ModItems.NETHER_BOW.get());
        MakeBow(fun.wraq.common.registry.ModItems.SKY_BOSS_BOW.get());
        MakeBow(fun.wraq.common.registry.ModItems.WITHER_BOW_0.get());
        MakeBow(fun.wraq.common.registry.ModItems.WITHER_BOW_1.get());
        MakeBow(fun.wraq.common.registry.ModItems.WITHER_BOW_2.get());
        MakeBow(ModItems.WITHER_BOW_3.get());
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
