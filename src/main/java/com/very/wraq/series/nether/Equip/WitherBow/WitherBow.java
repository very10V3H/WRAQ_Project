package com.very.wraq.series.nether.Equip.WitherBow;

import com.very.wraq.blocks.blocks.ForgeRecipe;
import com.very.wraq.projectiles.OnHitEffectMainHandWeapon;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.StableAttributesModifier;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class WitherBow extends WraqBow implements OnHitEffectMainHandWeapon {
    public final int tier;

    public WitherBow(Properties p_40524_, int tier) {
        super(p_40524_);
        this.tier = tier;
        Utils.attackDamage.put(this, WitherBowAttributes.BaseDamage[tier]);
        Utils.defencePenetration0.put(this, WitherBowAttributes.DefencePenetration0[tier]);
        Utils.critRate.put(this, WitherBowAttributes.CriticalRate[tier]);
        Utils.critDamage.put(this, WitherBowAttributes.CriticalDamage[tier]);
        Utils.movementSpeedWithoutBattle.put(this, WitherBowAttributes.SpeedUp[tier]);

        ForgeRecipe.forgeDrawRecipe.put(this, new ArrayList<>() {{
            add(new ItemStack(ModItems.NetherRune.get(), 4));
            add(new ItemStack(ModItems.Ruby.get(), 128));
            add(new ItemStack(ModItems.NetherQuartz.get(), 32));
            add(new ItemStack(Items.COAL, 192));
            add(new ItemStack(ModItems.goldCoin.get(), 64));
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
                append(Compute.AttributeDescription.MaxHealth("7%")));
        components.add(Component.literal("获得").withStyle(ChatFormatting.GREEN).
                append(Compute.AttributeDescription.DefencePenetration(String.valueOf(WitherBowAttributes.Effect[tier]))));
        components.add(Component.literal("持续时间: 5s"));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterIII();
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
                        WitherBowAttributes.Effect[tier], player.getServer().getTickCount() + 100));

        Compute.effectLastTimeSend(player, ModItems.WitherBow0.get().getDefaultInstance(), 100);
    }
}
