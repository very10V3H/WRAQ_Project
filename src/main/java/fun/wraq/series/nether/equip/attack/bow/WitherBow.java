package fun.wraq.series.nether.equip.attack.bow;

import fun.wraq.blocks.blocks.forge.ForgeRecipe;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class WitherBow extends WraqBow implements OnHitEffectEquip {
    public final int tier;

    public WitherBow(Properties p_40524_, int tier) {
        super(p_40524_);
        this.tier = tier;
        Utils.attackDamage.put(this, new double[]{80, 100, 120, 140}[tier]);
        Utils.defencePenetration0.put(this, new double[]{9, 10, 11, 12}[tier]);
        Utils.critRate.put(this, new double[]{0.2, 0.25, 0.25, 0.25}[tier]);
        Utils.levelRequire.put(this, 80);

        ForgeRecipe.recipes.put(this, new ArrayList<>() {{
            add(new ItemStack(ModItems.NETHER_RUNE.get(), 4));
            add(new ItemStack(ModItems.RUBY.get(), 128));
            add(new ItemStack(ModItems.NETHER_QUARTZ.get(), 32));
            add(new ItemStack(Items.COAL, 192));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 64));
        }});
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfNether;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("凋零增幅").withStyle(style));
        components.add(Component.literal("箭矢命中后:"));
        components.add(Component.literal("损失").withStyle(ChatFormatting.RED).
                append(ComponentUtils.AttributeDescription.health("7%")));
        components.add(Component.literal("获得").withStyle(ChatFormatting.GREEN).
                append(ComponentUtils.AttributeDescription.defencePenetration(String.valueOf(new int[]{4, 6, 8, 12}[tier]))));
        components.add(Component.literal("持续时间: 5s"));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfNether();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        if (player.getHealth() <= player.getMaxHealth() * 0.05f) {
            player.kill();
            Compute.formatBroad(player.level(), Component.literal("死亡").withStyle(ChatFormatting.RED),
                    Component.literal(player.getName().getString() + "被自己的魔法干掉了。"));
        }
        player.setHealth(player.getHealth() - player.getMaxHealth() * 0.05f);

        StableAttributesModifier.addAttributeModifier(player, StableAttributesModifier.playerDefencePenetration0Modifier,
                new StableAttributesModifier("witherBowActiveDefencePenetration0",
                        new int[]{4, 6, 8, 12}[tier], Tick.get() + 100));

        Compute.sendEffectLastTime(player, ModItems.WITHER_BOW_0.get().getDefaultInstance(), 100);
    }
}
