package fun.wraq.process.system.element.equipAndCurios.fireElement;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class FireElementSword extends WraqSword implements ActiveItem {

    public FireElementSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 600d);
        Utils.defencePenetration0.put(this, 40d);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.3);
        Element.fireElementValue.put(this, 2d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfFire;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionActive(components, Component.literal("引燃").withStyle(style));
        components.add(Component.literal(" 释放一道").withStyle(ChatFormatting.WHITE).
                append(Component.literal("激光").withStyle(style)).
                append(Component.literal("，对沿途的目标").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("造成").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.getAutoAdaptDamageDescription("200%")).
                append(Component.literal("，并").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("点燃").withStyle(style)).
                append(Component.literal("目标4s").withStyle(ChatFormatting.WHITE)));
        ComponentUtils.coolDownTimeDescription(components, 7);
        Compute.DescriptionPassive(components, Component.literal("燃烬").withStyle(style));
        components.add(Component.literal(" 对处于").withStyle(ChatFormatting.WHITE).
                append(Component.literal("燃烧状态").withStyle(style)).
                append(Component.literal("的目标造成伤害后，提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("100%归一化炽焰元素强度").withStyle(style)).
                append(Component.literal("，持续2s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 对未处于").withStyle(ChatFormatting.WHITE).
                append(Component.literal("燃烧状态").withStyle(style)).
                append(Component.literal("的目标造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("燃烧").withStyle(style)).
                append(Component.literal("，会为你提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("20%伤害提升").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("，至多叠加至").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("60%").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("，每个目标持续3s").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfElement();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static WeakHashMap<Player, Integer> playerActiveCoolDownMap = new WeakHashMap<>();

    public static WeakHashMap<Player, Integer> playerFireElementValueEnhanceTickMap = new WeakHashMap<>();

    public static void IgniteEffect(Player player, Mob mob) {
        if (mob.getRemainingFireTicks() > 0 && player.getMainHandItem().is(ModItems.FIRE_ELEMENT_SWORD.get())) {
            FireElementSword.playerFireElementValueEnhanceTickMap.put(player, Tick.get() + 40);
        }
    }

    public static double FireElementValueEnhance(Player player) {
        if (playerFireElementValueEnhanceTickMap.containsKey(player) && playerFireElementValueEnhanceTickMap.get(player) > Tick.get())
            return 1;
        return 0;
    }

    @Override
    public void active(Player player) {
        if (Compute.PlayerUseWithHud(player, FireElementSword.playerActiveCoolDownMap, ModItems.FIRE_ELEMENT_SWORD.get(), 0, 7)) {
            Compute.playerItemCoolDown(player, this, 7);
            List<Mob> mobList = Compute.shootOneLaser(player, true, Damage.getAutoAdaptionDamageValue(player, 2), ModParticles.LONG_RED_SPELL.get());
            mobList.forEach(mob -> Compute.igniteMob(player, mob, 80));
        }
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    public record IgniteMob(Integer id, Integer tick) {
    }

    public static WeakHashMap<Player, List<IgniteMob>> playerIgniteMobMap = new WeakHashMap<>();

    public static void Tick(Player player) {
        if (!player.getMainHandItem().is(ModItems.FIRE_ELEMENT_SWORD.get())) return;
        if (!FireElementSword.playerIgniteMobMap.containsKey(player))
            FireElementSword.playerIgniteMobMap.put(player, new ArrayList<>());
        List<IgniteMob> list = FireElementSword.playerIgniteMobMap.get(player);
        list.removeIf(igniteMob -> igniteMob.tick() < Tick.get());
        if (list.size() > 0)
            Compute.sendEffectLastTime(player, ModItems.FIRE_ELEMENT_SWORD.get().getDefaultInstance(), 8888, Math.min(3, list.size()), true);
        else
            Compute.sendEffectLastTime(player, ModItems.FIRE_ELEMENT_SWORD.get().getDefaultInstance(), 0, Math.min(3, list.size()), true);
    }

    public static void PlayerIgniteMobEffect(Player player, Mob mob) {
        if (!player.getMainHandItem().is(ModItems.FIRE_ELEMENT_SWORD.get())) return;
        if (!FireElementSword.playerIgniteMobMap.containsKey(player))
            FireElementSword.playerIgniteMobMap.put(player, new ArrayList<>());
        List<IgniteMob> list = FireElementSword.playerIgniteMobMap.get(player);
        list.removeIf(igniteMob -> igniteMob.tick() < Tick.get());

        if (!mob.wasOnFire) {
            list.add(new IgniteMob(mob.getId(), Tick.get() + 60));
        }
    }

    public static double DamageEnhance(Player player) {
        if (!FireElementSword.playerIgniteMobMap.containsKey(player))
            FireElementSword.playerIgniteMobMap.put(player, new ArrayList<>());
        List<IgniteMob> list = FireElementSword.playerIgniteMobMap.get(player);
        list.removeIf(igniteMob -> igniteMob.tick() < Tick.get());
        return Math.min(3, list.size()) * 0.2;
    }
}
