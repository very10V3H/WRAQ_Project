package fun.wraq.series.nether.equip.attack.sword;

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
        Utils.attackDamage.put(this, new double[]{80, 100, 120, 140}[tier]);
        Utils.defencePenetration0.put(this, new double[]{9, 10, 11, 12}[tier]);
        Utils.healthSteal.put(this, new double[]{0.04, 0.06, 0.08, 0.1}[tier]);
        Utils.critRate.put(this, new double[]{0.2, 0.22, 0.24, 0.3}[tier]);
        Utils.critDamage.put(this, new double[]{0.35, 0.4, 0.45, 0.55}[tier]);
        Element.FireElementValue.put(this, new double[]{0.2, 0.4, 0.6, 0.8}[tier]);
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
                append(ComponentUtils.AttributeDescription.health("30%")));
        components.add(Component.literal("获得").withStyle(ChatFormatting.GREEN).
                append(ComponentUtils.AttributeDescription.defencePenetration(String.valueOf(new int[]{4, 6, 8, 12}[tier]))));
        components.add(Component.literal("持续时间: 12s"));
        ComponentUtils.coolDownTimeDescription(components, 12);
        ComponentUtils.manaCostDescription(components, 60);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfNether();
    }

    @Override
    public void active(Player player) {
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
                new StableAttributesModifier("witherSwordActiveDefencePenetration0", new int[]{4, 6, 8, 12}[tier], tickCount + 240));
        MySound.soundToNearPlayer(player, ModSounds.Nether_Power.get());
    }

    @Override
    public double manaCost(Player player) {
        return 60;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.WITHER_RUNE.get(), 4));
            add(new ItemStack(ModItems.Ruby.get(), 128));
            add(new ItemStack(ModItems.NetherQuartz.get(), 32));
            add(new ItemStack(Items.COAL, 192));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 64));
        }};
    }
}
