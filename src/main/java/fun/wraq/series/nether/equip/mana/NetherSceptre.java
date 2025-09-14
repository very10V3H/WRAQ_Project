package fun.wraq.series.nether.equip.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.BowAttribute;
import fun.wraq.common.equip.SwordAttribute;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.equip.impl.Laser;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.display.EnhancedForgedItem;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.hud.Mana;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class NetherSceptre extends WraqSceptre implements Laser, ForgeItem, EnhancedForgedItem,
        SwordAttribute, BowAttribute {

    private final int tier;
    public NetherSceptre(Properties properties, int tier) {
        super(properties.rarity(CustomStyle.NetherItalic));
        if (tier < 2) {
            if (tier > 0) {
                Utils.critRate.put(this, 0.25);
            }
            Utils.manaDamage.put(this, 480d);
            Utils.manaRecover.put(this, 20d);
            Utils.coolDownDecrease.put(this, 0.35);
            Utils.manaPenetration0.put(this, 24d);
            Element.fireElementValue.put(this, 1d);
            Utils.levelRequire.put(this, 80);
        } else {
            Utils.manaDamage.put(this, 6000d);
            Utils.critRate.put(this, 0.4);
            Utils.critDamage.put(this, 0.1);
            Utils.manaRecover.put(this, 40d);
            Utils.manaPenetration0.put(this, 60d);
            Utils.coolDownDecrease.put(this, 0.3);
            Element.fireElementValue.put(this, 1.5);
            Utils.levelRequire.put(this, 225);
        }
        this.tier = tier;
    }

    @Override
    protected ManaArrow summonManaArrow(Player player, double rate) {
        return null;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfNether;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        if (tier < 2) {
            ComponentUtils.descriptionPassive(components, Te.m("解构射线", CustomStyle.styleOfNether));
            components.add(Te.s("你的", "普通法球攻击", CustomStyle.styleOfMana,
                    "被替换为", "解构射线", ChatFormatting.RED));
            components.add(Te.s("解构射线", ChatFormatting.RED, "具有以下特征:"));
            components.add(Te.s(" 1.", ChatFormatting.RED, "最远可达24格"));
            components.add(Te.s(" 2.", ChatFormatting.RED, "对沿途的所有敌人每0.5s造成一次伤害"));
            components.add(Te.s(" 3.", ChatFormatting.RED, "伤害被视为",
                    "普通法球攻击", CustomStyle.styleOfMana));
            components.add(Te.s(" 解构射线", ChatFormatting.RED, "每秒消耗",
                    "90法力", CustomStyle.styleOfMana));
        } else {
            ComponentUtils.descriptionPassive(components, Te.m("解构射线-EX", CustomStyle.styleOfNether));
            components.add(Te.s("你的", "普通法球攻击", CustomStyle.styleOfMana,
                    "被替换为", "解构射线", ChatFormatting.RED));
            components.add(Te.s("解构射线", ChatFormatting.RED, "具有以下特征:"));
            components.add(Te.s(" 1.", ChatFormatting.RED, "最远可达40格"));
            components.add(Te.s(" 2.", ChatFormatting.RED, "对沿途的所有敌人每0.5s造成一次伤害"));
            components.add(Te.s(" 3.", ChatFormatting.RED, "伤害被视为",
                    "普通法球攻击", CustomStyle.styleOfMana));
        }
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfNether();
    }

    public static Map<Player, Integer> passiveLastTickMap = new WeakHashMap<>();

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && (tier >= 2 || Mana.getPlayerCurrentManaNum(player) > 45)) {
            passiveLastTickMap.put(player, Tick.get() + 8);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void tick(Player player) {
        if (passiveLastTickMap.getOrDefault(player, 0) > Tick.get()
                && (tier >= 2 || Mana.getPlayerCurrentManaNum(player) > 45)) {
            if (tier < 2 && Tick.get() % 10 == 0) {
                Mana.addOrCostPlayerMana(player, -45);
            }
            Compute.TargetLocationLaser(player, Compute.getPickLocationIgnoreBlock(player, tier >= 2 ? 40 : 24),
                    ModParticles.YSR1.get(), 1, 10);
        }
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        if (tier == 0) {
            return List.of(
                    new ItemStack(ModItems.NETHER_RUNE.get(), 2),
                    new ItemStack(ModItems.QUARTZ_RUNE.get(), 1),
                    new ItemStack(ModItems.GOLD_COIN.get(), 192),
                    new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4)
            );
        } else if (tier == 1) {
            return List.of(
                    new ItemStack(ModItems.NETHER_SCEPTRE.get()),
                    new ItemStack(ModItems.COMPLETE_GEM.get(), 8),
                    new ItemStack(ModItems.REPUTATION_MEDAL.get(), 8)
            );
        }
        return List.of(
                new ItemStack(ModItems.COLLEGE_SENIOR_EQUIP_TICKET.get())
        );
    }

    @Override
    public int getEnhanceTier() {
        return tier;
    }
}
