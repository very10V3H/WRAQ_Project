package fun.wraq.series.instance.series.mushroom.gem;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.process.func.StableTierAttributeModifier;
import fun.wraq.series.gems.passive.WraqPassiveGem;
import fun.wraq.series.gems.passive.impl.GemOnKillMob;
import fun.wraq.series.instance.series.mushroom.MushroomItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MushroomParasitismGem extends WraqPassiveGem implements GemOnKillMob, Decomposable {
    public MushroomParasitismGem(Properties properties, List<AttributeMapValue> attributeMapValues, Style hoverStyle, Component oneLineDescription, Component suffix) {
        super(properties, attributeMapValues, hoverStyle, oneLineDescription, suffix);
    }

    @Override
    public List<Component> getAdditionDescription() {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("寄生", hoverStyle));
        components.add(Te.s(" 击杀敌人", ChatFormatting.RED, "后，将掉落一个", "菌", hoverStyle));
        components.add(Te.s(" 拾取", "菌", hoverStyle, "将提供",
                ComponentUtils.AttributeDescription.maxHealth("等级 * 50")));
        components.add(Te.s(" 持续30s", ChatFormatting.AQUA, "，最多可叠加至", "10层", hoverStyle));
        components.add(Te.s(" 在提供最大生命值时，将会回复等量生命值", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        components.add(Te.s(" 当层数达10层时继续拾取，仅提供生命回复", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        return components;
    }

    @Override
    public void onKill(Player player, Mob mob) {
        ItemAndRate.summonItemEntity(MushroomItems.PARASITISM_GEM_MUSHROOM.get().getDefaultInstance(),
                mob.getEyePosition(), mob.level(), 20);
    }

    public static final String PASSIVE_TAG = "MushroomParasitismGemPassive";

    public static void onPickUp(Player player) {
        if (StableTierAttributeModifier.getAttributeModifierTier(player,
                StableTierAttributeModifier.playerMaxHealthExValue, PASSIVE_TAG) >= 10) {
            Compute.playerHeal(player, player.experienceLevel * 50);
        }
        StableTierAttributeModifier.addM(player, StableTierAttributeModifier.playerMaxHealthExValue,
                PASSIVE_TAG, player.experienceLevel * 50,
                Tick.get() + Tick.s(30), 10, "item/brown_mushroom");
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(MushroomItems.UNKNOWN_MUSHROOM.get());
    }
}
