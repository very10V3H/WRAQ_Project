package com.very.wraq.series.nether.Equip;

import com.very.wraq.common.registry.MySound;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.WraqSword;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.registry.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;

import java.util.ArrayList;
import java.util.List;

public class ManaSword extends WraqSword implements ActiveItem {

    public ManaSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 130d);
        Utils.critRate.put(this, 0.3);
        Utils.critDamage.put(this, 0.5);
        Element.FireElementValue.put(this, 0.8);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMana;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("魔力渴望").withStyle(CustomStyle.styleOfMana));
        components.add(Component.literal("玛莫提乌斯之噬对魔力十分渴望。").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("·[对魔]").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("攻击唤魔者/唤魔大师时，").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("无视其100%的护甲。").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal("·[对人]").withStyle(ChatFormatting.AQUA).
                append(Component.literal("攻击玩家时，").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("偷取玩家10%最大法力值。").withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionActive(components, Component.literal("魔力灌注").withStyle(CustomStyle.styleOfMana));
        components.add(Component.literal("将自身魔力全部灌注入玛莫提乌斯之噬，获得短暂的巨幅攻击力加成。").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("消耗全部").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("")).
                append(Component.literal("并获得等同于消耗量的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("")));
        Compute.CoolDownTimeDescription(components, 5);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterIII();
    }

    @Override
    public void active(Player player) {
        CompoundTag data = player.getPersistentData();
        Compute.PlayerPowerParticle(player);
        player.getCooldowns().addCooldown(ModItems.ManaSword.get(), (int) (100 - 100 * PlayerAttributes.coolDownDecrease(player)));
        data.putInt("ManaSwordActive", data.getInt("MANA"));
        data.putInt("MANA", 0);
        Compute.ManaStatusUpdate(player);
        MySound.SoundToAll(player, ModSounds.Mana_Sword.get());
    }
}
