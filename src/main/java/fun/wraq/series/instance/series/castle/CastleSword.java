package fun.wraq.series.instance.series.castle;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class CastleSword extends WraqSword implements ForgeItem, ActiveItem {

    public CastleSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 1500d);
        Utils.defencePenetration0.put(this, 36d);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.30d);
        Utils.critDamage.put(this, 0.8);
        Utils.movementSpeedWithoutBattle.put(this, 0.5);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfCastle;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = CustomStyle.styleOfCastle;
        Compute.DescriptionPassive(components, Component.literal("暗影之刃").withStyle(style));
        components.add(Component.literal(" 你的普通攻击将使敌人被拖入暗影之中").withStyle(ChatFormatting.ITALIC).withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通攻击").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("将附带造成伤害100%的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamageValue("")));
        Compute.DescriptionActive(components, Component.literal("噬魔注能").withStyle(style));
        components.add(Component.literal(" 扣除自身").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("15%当前")).
                append(Component.literal("，获得持续6s的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%伤害提升").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("以及").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.DefencePenetration("150")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaPenetration("15")));
        ComponentUtils.coolDownTimeDescription(components, 15);
        components.add(Component.literal(" 多件暗黑武器的主动将会刷新持续时间，但效果将不会叠加，且共享冷却时间").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfCastle();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static void NormalAttack(Player player, Mob mob, double damage) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.CastleSword.get())) {
            Damage.causeManaDamageToMonster_ApDamage_Direct(player, mob, damage, true);
        }
    }

    public static WeakHashMap<Player, Integer> CastleWeaponActiveLastTick = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> CastleWeaponActiveCoolDown = new WeakHashMap<>();

    public static double DamageEnhance(Player player) {
        if (CastleWeaponActiveLastTick.containsKey(player) && CastleWeaponActiveLastTick.get(player) > player.getServer().getTickCount())
            return 0.8;
        return 0;
    }

    public static double ExPenetration0(Player player) {
        if (CastleWeaponActiveLastTick.containsKey(player) && CastleWeaponActiveLastTick.get(player) > player.getServer().getTickCount())
            return 15;
        return 0;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.CastleSwordPiece.get(), 12));
            add(new ItemStack(ModItems.CastlePiece.get(), 192));
            add(new ItemStack(ModItems.BlazeRune.get(), 8));
            add(new ItemStack(ModItems.completeGem.get(), 26));
            add(new ItemStack(ModItems.ReputationMedal.get(), 104));
            add(new ItemStack(ModItems.RefiningGold.get(), 6));
            add(new ItemStack(ModItems.WorldSoul3.get(), 6));
        }};
    }

    @Override
    public void active(Player player) {
        if (Compute.PlayerUseWithHud(player, CastleWeaponActiveCoolDown, ModItems.CastleSword.get(), CastleWeaponActiveLastTick, 120, 0, 20)) {
            Compute.PlayerHealthDecrease(player, player.getHealth() * 0.15, Component.literal(" 被暗黑魔能吞噬了。").withStyle(CustomStyle.styleOfCastle));
        }
    }
}
