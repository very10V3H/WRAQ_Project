package fun.wraq.series.instance.series.moon.Equip;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter1.Mine.MineShield;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.WeakHashMap;

public class MoonShield extends Item {

    public static WeakHashMap<Player, Mob> PlayerMoonShieldMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> PlayerMoonShieldCountMap = new WeakHashMap<>();

    public MoonShield() {
        super(new Properties().rarity(CustomStyle.MoonItalic).stacksTo(1));
        Utils.defence.put(this, 7d);
        Utils.maxHealth.put(this, 777d);
        Utils.attackDamage.put(this, 77d);
        Utils.critDamage.put(this, 0.27);
        Utils.expUp.put(this, 1.57);
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
        Utils.shieldTag.put(this, 1d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = CustomStyle.styleOfMoon;
        stack.setHoverName(Component.literal("玉轮明盾").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("手盾").withStyle(ChatFormatting.GRAY)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        MineShield.shieldAdditionDescription(components);
        Compute.DescriptionPassive(components, Component.literal("月闪").withStyle(style));
        components.add(Component.literal("近战攻击").withStyle(CustomStyle.styleOfPower).
                append(Component.literal("将会标记一个敌人").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("当你对该敌人造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("7").withStyle(style)).
                append(Component.literal("次伤害后，").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 你将会引爆标记，并对该敌人造成").withStyle(ChatFormatting.WHITE).
                append(Component.literal("14倍等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
        components.add(Component.literal(" - 引爆标记后，你将获得持续3s的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("12%总")));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.suffixOfMoon(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static WeakHashMap<Player, Integer> playerDamageEnhanceTickMap = new WeakHashMap<>();

    public static double MoonShield(Player player, Mob mob) {
        if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.MoonShield.get())) {
            int TickCount = player.getServer().getTickCount();
            if (PlayerMoonShieldMap.containsKey(player) && !PlayerMoonShieldMap.get(player).equals(mob)) {
                Mob oldMob = PlayerMoonShieldMap.get(player);
                oldMob.removeEffect(MobEffects.GLOWING);
                Compute.removeMobEffectHudToNearPlayer(oldMob, ModItems.MoonSoul.get(), "MoonShieldCount");
                PlayerMoonShieldCountMap.put(player, 0);
            }
            mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 88888, 1, false, false));
            PlayerMoonShieldMap.put(player, mob);
            int count = PlayerMoonShieldCountMap.getOrDefault(player, 0);
            PlayerMoonShieldCountMap.put(player, ++count);
            if (count == 7) {
                PlayerMoonShieldCountMap.put(player, 0);
                playerDamageEnhanceTickMap.put(player, TickCount + 60);
                Compute.sendEffectLastTime(player, ModItems.MoonSoul.get(), 60);
                Compute.removeMobEffectHudToNearPlayer(mob, ModItems.MoonSoul.get(), "MoonShieldCount");
                return Compute.getXpStrengthADDamage(player, 14);
            } else {
                Compute.sendMobEffectHudToNearPlayer(mob, ModItems.MoonSoul.get(), "MoonShieldCount", 8888, count, true);
            }
        }
        return 0;
    }

    public static double damageEnhance(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (playerDamageEnhanceTickMap.containsKey(player) && playerDamageEnhanceTickMap.get(player) > TickCount) {
            return 0.12;
        }
        return 0;
    }
}