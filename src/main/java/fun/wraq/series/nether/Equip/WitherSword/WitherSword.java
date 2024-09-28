package fun.wraq.series.nether.Equip.WitherSword;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.nether.Equip.WitherSword.WitherSwordAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class WitherSword extends WraqSword implements ActiveItem, ForgeItem {

    private final int tier;

    public WitherSword(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.attackDamage.put(this, WitherSwordAttributes.BaseDamage[tier]);
        Utils.defencePenetration0.put(this, WitherSwordAttributes.DefencePenetration0[tier]);
        Utils.healthSteal.put(this, WitherSwordAttributes.HealthSteal[tier]);
        Utils.critRate.put(this, WitherSwordAttributes.CriticalRate[tier]);
        Utils.critDamage.put(this, WitherSwordAttributes.CriticalDamage[tier]);
        Utils.movementSpeedWithoutBattle.put(this, WitherSwordAttributes.SpeedUp[tier]);
        Utils.attackSpeedUp.put(this, WitherSwordAttributes.AttackSpeedUp[tier]);
        Element.FireElementValue.put(this, WitherSwordAttributes.FireElementValue[tier]);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfWither;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionActive(components, Component.literal("碳化").withStyle(style));
        components.add(Component.literal("失去").withStyle(ChatFormatting.RED).
                append(ComponentUtils.AttributeDescription.maxHealth("30%")));
        components.add(Component.literal("获得").withStyle(ChatFormatting.GREEN).
                append(Compute.AttributeDescription.DefencePenetration(String.valueOf(WitherSwordAttributes.ActiveEffect[tier]))));
        components.add(Component.literal("持续时间: 12s"));
        ComponentUtils.coolDownTimeDescription(components, 12);
        ComponentUtils.manaCostDescription(components, 60);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterIII();
    }

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, 180)) {
            int tickCount = player.getServer().getTickCount();
            player.getCooldowns().addCooldown(ModItems.WitherSword0.get(), (int) (240 - 240 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.WitherSword1.get(), (int) (240 - 240 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.WitherSword2.get(), (int) (240 - 240 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.WitherSword3.get(), (int) (240 - 240 * PlayerAttributes.coolDownDecrease(player)));
            Compute.sendEffectLastTime(player, ModItems.WitherSword0.get().getDefaultInstance(), 240);
            if (player.getHealth() <= player.getMaxHealth() * 0.3f) {
                player.kill();
                Compute.formatBroad(player.level(), Component.literal("死亡").withStyle(ChatFormatting.RED),
                        Component.literal(player.getName().getString() + "被自己的魔法干掉了。"));
            }
            player.setHealth(player.getHealth() - player.getMaxHealth() * 0.3f);
            StableAttributesModifier.addAttributeModifier(player, StableAttributesModifier.playerDefencePenetration0Modifier,
                    new StableAttributesModifier("witherSwordActiveDefencePenetration0", WitherSwordAttributes.ActiveEffect[tier], tickCount + 240));
            MySound.SoundToAll(player, ModSounds.Nether_Power.get());
        }
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.WitherRune.get(), 4));
            add(new ItemStack(ModItems.Ruby.get(), 128));
            add(new ItemStack(ModItems.NetherQuartz.get(), 32));
            add(new ItemStack(Items.COAL, 192));
            add(new ItemStack(ModItems.goldCoin.get(), 64));
        }};
    }
}
