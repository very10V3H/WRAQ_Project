package fun.wraq.series.overworld.chapter2.blackForest;

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

public class HuskSword extends WraqSword implements ActiveItem {

    private final int tier;

    public HuskSword(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.attackDamage.put(this, new double[]{80, 85, 90, 100, 200}[tier]);
        Utils.defencePenetration0.put(this, new double[]{9, 10, 11, 12, 15}[tier]);
        Utils.healthSteal.put(this, 0.05);
        Utils.critRate.put(this, 0.3);
        Utils.critDamage.put(this, new double[]{0.4, 0.45, 0.55, 0.65, 0.75}[tier]);
        Utils.movementSpeedWithoutBattle.put(this, 0.1);
        Element.StoneElementValue.put(this, new double[]{0.2, 0.4, 0.6, 0.8, 1}[tier]);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfHusk;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionActive(components, Component.literal("土葬").withStyle(CustomStyle.styleOfHusk));
        components.add(Component.literal("使下一次的普通攻击附带:").withStyle(ChatFormatting.WHITE));
        int rate = 2;
        if (tier == 4) rate = 4;
        else if (tier == 3) rate = 3;
        components.add(Component.literal("基于目标当前生命值造成至多" + rate + "倍的").withStyle(CustomStyle.styleOfHusk).
                append(Component.literal("等级强度").withStyle(CustomStyle.styleOfLucky)).
                append(Component.literal("额外物理伤害").withStyle(CustomStyle.styleOfHusk)));
        components.add(Component.literal("倍率随目标当前生命值线性增长").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("若目标死亡，则获得自身").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.maxHealth(rate >= 3 ? "40%" : "25%")).
                append(Component.literal("的护盾,持续10s。").withStyle(ChatFormatting.WHITE)));
        ComponentUtils.coolDownTimeDescription(components, 3);
        ComponentUtils.manaCostDescription(components, 30);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterII();
    }

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, 90)) {
            CompoundTag data = player.getPersistentData();
            player.getCooldowns().addCooldown(ModItems.huskSword0.get(), (int) (60 - 60 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.huskSword1.get(), (int) (60 - 60 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.huskSword2.get(), (int) (60 - 60 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.huskSword3.get(), (int) (60 - 60 * PlayerAttributes.coolDownDecrease(player)));
            Compute.playerItemCoolDown(player, ModItems.BlackForestSword4.get(), 3);
            data.putBoolean("BlackForestSword4", false);
            data.putBoolean("BlackForestSword3", false);
            data.putBoolean("BlackForestSword0", false);
            if (tier == 4) Utils.BlackForestSwordActiveMap.put(player, 3);
            else if (tier == 3) Utils.BlackForestSwordActiveMap.put(player, 2);
            else Utils.BlackForestSwordActiveMap.put(player, 1);
            Compute.sendEffectLastTime(player, ModItems.huskSword0.get().getDefaultInstance(), 8888, 0, true);
            MySound.soundToNearPlayer(player, ModSounds.Attack.get());
        }
    }
}
