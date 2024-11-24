package fun.wraq.series.nether.equip.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.equip.impl.Laser;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.render.hud.Mana;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class NetherSceptre extends WraqSceptre implements Laser, ForgeItem {

    public NetherSceptre(Properties p_42964_) {
        super(p_42964_.rarity(CustomStyle.NetherItalic));
        Utils.critRate.put(this, 0.25);
        Utils.manaDamage.put(this, 480d);
        Utils.manaRecover.put(this, 20d);
        Utils.coolDownDecrease.put(this, 0.35);
        Utils.manaPenetration0.put(this, 24d);
        Element.FireElementValue.put(this, 1d);
    }

    @Override
    protected AbstractArrow summonManaArrow(Player player, double rate) {
        return null;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfNether;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.m("解构射线", CustomStyle.styleOfNether));
        components.add(Te.s("你的", "普通法球攻击", CustomStyle.styleOfMana, "被替换为", "解构射线", ChatFormatting.RED));
        components.add(Te.s("解构射线", ChatFormatting.RED, "具有以下特征:"));
        components.add(Te.s("1.", ChatFormatting.RED, "最远可达30格"));
        components.add(Te.s("2.", ChatFormatting.RED, "对沿途的所有敌人每0.5s造成一次伤害"));
        components.add(Te.s("3.", ChatFormatting.RED, "伤害被视为", "普通法球攻击", CustomStyle.styleOfMana));
        components.add(Te.s(" 解构射线", ChatFormatting.RED, "每秒消耗", "90法力", CustomStyle.styleOfMana));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfNether();
    }

    public static Map<Player, Integer> passiveLastTickMap = new WeakHashMap<>();

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && Mana.getPlayerCurrentManaNum(player) > 45) {
            passiveLastTickMap.put(player, Tick.get() + 8);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void tick(Player player) {
        if (passiveLastTickMap.getOrDefault(player, 0) > Tick.get()
                && Mana.getPlayerCurrentManaNum(player) > 45) {
            if (Tick.get() % 10 == 0) {
                Mana.addOrCostPlayerMana(player, -45);
            }
            Compute.TargetLocationLaser(player, player.pick(30, 0, false).getLocation(),
                    ModParticles.YSR1.get(), 1, 10);
        }
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.NetherRune.get(), 2),
                new ItemStack(ModItems.QuartzRune.get(), 1),
                new ItemStack(ModItems.GOLD_COIN.get(), 192),
                new ItemStack(ModItems.completeGem.get(), 8),
                new ItemStack(ModItems.ReputationMedal.get(), 8),
                new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4)
        );
    }
}
