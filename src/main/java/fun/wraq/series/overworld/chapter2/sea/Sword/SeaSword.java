package fun.wraq.series.overworld.chapter2.sea.Sword;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SeaSword extends WraqSword implements ActiveItem {

    private final int tier;

    public SeaSword(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.attackDamage.put(this, new double[]{80, 85, 90, 100, 200}[tier]);
        Utils.defencePenetration0.put(this, new double[]{9, 10, 11, 12, 15}[tier]);
        Utils.healthSteal.put(this, 0.05);
        Utils.critRate.put(this, 0.3);
        Utils.critDamage.put(this, new double[]{0.4, 0.45, 0.55, 0.65, 0.75}[tier]);
        Element.WaterElementValue.put(this, new double[]{0.2, 0.4, 0.6, 0.8, 1}[tier]);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSea;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionActive(components, Component.literal("海葬").withStyle(CustomStyle.styleOfSea));
        components.add(Component.literal("使下一次的普通攻击附带:").withStyle(ChatFormatting.WHITE));
        int rate = 2;
        if (tier == 4) rate = 4;
        else if (tier == 3) rate = 3;
        components.add(Component.literal("基于目标已损失生命值造成至多" + (rate * 100) + "%").withStyle(CustomStyle.styleOfSea).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
        components.add(Component.literal("倍率随目标已损失生命值线性增长").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("若目标死亡，则获得自身").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.maxHealth(tier >= 3 ? "40%" : "25%")).
                append(Component.literal("的生命回复。").withStyle(ChatFormatting.WHITE)));
        ComponentUtils.coolDownTimeDescription(components, 3);
        ComponentUtils.manaCostDescription(components, 20);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, 20)) {
            CompoundTag data = player.getPersistentData();
            player.getCooldowns().addCooldown(ModItems.SeaSword0.get(), (int) (60 - 60 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.SeaSword1.get(), (int) (60 - 60 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.SeaSword2.get(), (int) (60 - 60 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.SeaSword3.get(), (int) (60 - 60 * PlayerAttributes.coolDownDecrease(player)));
            Compute.playerItemCoolDown(player, ModItems.SeaSword4.get(), 3);
            data.putBoolean("SeaSword4", false);
            data.putBoolean("SeaSword3", false);
            data.putBoolean("SeaSword0", false);
            if (tier == 4) Utils.SeaSwordActiveMap.put(player, 3);
            else if (tier == 3) Utils.SeaSwordActiveMap.put(player, 2);
            else Utils.SeaSwordActiveMap.put(player, 1);
            Compute.sendEffectLastTime(player, ModItems.SeaSword0.get().getDefaultInstance(), 8888, 0, true);
            MySound.soundToNearPlayer(player, ModSounds.Attack.get());
        }
    }
}
