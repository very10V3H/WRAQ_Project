package fun.wraq.series.nether.equip.attack.sword;

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
import fun.wraq.render.hud.Mana;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

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
                append(ComponentUtils.AttributeDescription.manaValue("")).
                append(Component.literal("并获得等同于消耗量的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.attackDamage("")));
        ComponentUtils.coolDownTimeDescription(components, 5);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfNether();
    }

    @Override
    public void active(Player player) {
        CompoundTag data = player.getPersistentData();
        Compute.PlayerPowerParticle(player);
        player.getCooldowns().addCooldown(ModItems.ManaSword.get(), (int) (100 - 100 * PlayerAttributes.coolDownDecrease(player)));
        data.putInt("ManaSwordActive", data.getInt("MANA"));
        data.putInt("MANA", 0);
        Mana.updateManaStatus(player);
        MySound.soundToNearPlayer(player, ModSounds.Mana_Sword.get());
    }
}
