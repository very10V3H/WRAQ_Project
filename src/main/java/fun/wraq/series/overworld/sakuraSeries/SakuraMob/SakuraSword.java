package fun.wraq.series.overworld.sakuraSeries.SakuraMob;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SkillPackets.Charging.ChargedClearS2CPacket;
import fun.wraq.networking.misc.SkillPackets.SkillImageS2CPacket;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SakuraSword extends WraqSword implements ActiveItem {

    public SakuraSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 240d);
        Utils.defencePenetration0.put(this, 18d);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.30d);
        Utils.critDamage.put(this, 0.8);
        Utils.movementSpeedWithoutBattle.put(this, 0.5);
        Element.LifeElementValue.put(this, 1.25);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSakura;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("樱灵之息").withStyle(style));
        components.add(Component.literal(" 手持该妖刀将积攒").withStyle(ChatFormatting.WHITE).
                append(Component.literal("樱灵之息").withStyle(style)));
        components.add(Component.literal(" 移动").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal("将加快").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("樱灵之息").withStyle(style)).
                append(Component.literal("的积攒").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionActive(components, Component.literal("妖化樱灵").withStyle(CustomStyle.styleOfSakura));
        components.add(Component.literal(" 当层数满时，右键获得持续10s的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("妖化英灵之力").withStyle(CustomStyle.styleOfSakura)));
        Compute.DescriptionPassive(components, Component.literal("妖化樱灵之力").withStyle(CustomStyle.styleOfSakura));
        components.add(Component.literal(" 1.").withStyle(CustomStyle.styleOfDemon).
                append(Component.literal("无视怪物伤害。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 2.").withStyle(CustomStyle.styleOfDemon).
                append(Component.literal("普通攻击额外造成的50%伤害值的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
        components.add(Component.literal(" 3.").withStyle(CustomStyle.styleOfDemon).
                append(Component.literal("获得").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.movementSpeed("60%")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfSakura();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, 120)) {
            CompoundTag data = player.getPersistentData();
            int tickCount = player.getServer().getTickCount();
            String name = player.getName().getString();
            if (Utils.SakuraDemonSword.containsKey(name) && Utils.SakuraDemonSword.get(name)) {
                ModNetworking.sendToClient(new ChargedClearS2CPacket(4), (ServerPlayer) player);
                ModNetworking.sendToClient(new SkillImageS2CPacket(1, 10, 10, 0, 3), (ServerPlayer) player);
                Utils.SakuraDemonSword.put(name, false);
                data.putInt(StringUtils.SakuraDemonSword, tickCount + 200);
                player.getCooldowns().addCooldown(ModItems.SakuraDemonSword.get(), (int) (300 - 300 * PlayerAttributes.coolDownDecrease(player)));
            } else {
                Compute.sendFormatMSG(player, Component.literal("妖刀").withStyle(CustomStyle.styleOfDemon),
                        Component.literal("妖刀能量尚未充盈完毕。").withStyle(ChatFormatting.WHITE));
            }
        }
    }

    public static double SakuraDemonSword(Player player, double DamageBeforeDefence) {
        double DamageInfluence = 0;
        int TickCount = player.getServer().getTickCount();
        CompoundTag data = player.getPersistentData();
        if (data.contains(StringUtils.SakuraDemonSword) && data.getInt(StringUtils.SakuraDemonSword) > TickCount) {
            DamageInfluence += DamageBeforeDefence * 0.5f;
        }
        return DamageInfluence;
    }
}
