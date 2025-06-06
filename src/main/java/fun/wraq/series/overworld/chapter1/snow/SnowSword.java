package fun.wraq.series.overworld.chapter1.snow;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.USE.UtilsSnowSwordS2CPacket;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SnowSword extends WraqSword implements ActiveItem {

    public SnowSword(Properties p_42964_, int tier) {
        super(p_42964_);
        Utils.attackDamage.put(this, new double[]{100, 105, 110, 120, 200}[tier]);
        Utils.defencePenetration0.put(this, new double[]{6, 7, 8, 9, 18}[tier]);
        Utils.healthSteal.put(this, new double[]{0.02, 0.03, 0.04, 0.08, 0.08}[tier]);
        Utils.critRate.put(this, new double[]{0.3, 0.33, 0.36, 0.45, 0.45}[tier]);
        Element.IceElementValue.put(this, new double[]{0.2, 0.4, 0.6, 0.8, 1d}[tier]);
        Utils.levelRequire.put(this, 100);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSnow;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("凿击-Ex").withStyle(ChatFormatting.AQUA));
        ComponentUtils.descriptionNum(components, "攻击将会大幅降低目标生物的移动速度", Component.literal("2s").withStyle(ChatFormatting.AQUA), "");
        components.add(Component.literal("主动:").withStyle(ChatFormatting.AQUA).
                append(Component.literal("冰川攀登！").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal("向前闪现一小段距离"));
        components.add(Component.literal("冷却时间: 10s"));
        ComponentUtils.manaCostDescription(components, 60);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public void active(Player player) {
        ModNetworking.sendToClient(new UtilsSnowSwordS2CPacket(true), (ServerPlayer) player);
        player.getCooldowns().addCooldown(ModItems.SNOW_SWORD_0.get(), (int) (200 * (1.0 - PlayerAttributes.coolDownDecrease(player))));
        player.getCooldowns().addCooldown(ModItems.SNOW_SWORD_1.get(), (int) (200 * (1.0 - PlayerAttributes.coolDownDecrease(player))));
        player.getCooldowns().addCooldown(ModItems.SNOW_SWORD_2.get(), (int) (200 * (1.0 - PlayerAttributes.coolDownDecrease(player))));
        player.getCooldowns().addCooldown(ModItems.SNOW_SWORD_3.get(), (int) (200 * (1.0 - PlayerAttributes.coolDownDecrease(player))));
        player.getCooldowns().addCooldown(ModItems.SNOW_SWORD_4.get(), (int) (200 * (1.0 - PlayerAttributes.coolDownDecrease(player))));
    }

    @Override
    public double manaCost(Player player) {
        return 60;
    }
}
