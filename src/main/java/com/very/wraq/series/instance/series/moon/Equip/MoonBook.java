package com.very.wraq.series.instance.series.moon.Equip;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.registry.ModItems;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoonBook extends Item {
    private final double ManaDamage = 577;
    private final double ManaPenetration0 = 777;
    private final double MaxMana = 77;
    private final double MovementSpeed = 1.27;
    private final double CoolDown = 0.27;
    private final double ExpUp = 1.57;

    public static Map<Player, Mob> PlayerMoonBookMap = new HashMap<>();
    public static Map<Player, Integer> PlayerMoonBookCountMap = new HashMap<>();

    public MoonBook() {
        super(new Properties().rarity(CustomStyle.MoonItalic).stacksTo(1));
        Utils.manaDamage.put(this, ManaDamage);
        Utils.manaPenetration0.put(this, ManaPenetration0);
        Utils.maxMana.put(this, MaxMana);
        Utils.movementSpeedWithoutBattle.put(this, MovementSpeed);
        Utils.coolDownDecrease.put(this, CoolDown);
        Utils.expUp.put(this, ExpUp);
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    private final Style style = CustomStyle.styleOfGold;

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.setHoverName(Component.literal("天玉明镜").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("魔导书").withStyle(CustomStyle.styleOfMana)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfAddition(components);
        Compute.DescriptionPassive(components, Component.literal("折镜").withStyle(style));
        components.add(Component.literal("法球攻击").withStyle(CustomStyle.styleOfMana).
                append(Component.literal("将会标记一个敌人").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("当你对该敌人造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("7").withStyle(style)).
                append(Component.literal("次伤害后，").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 你将会引爆标记，并对该敌人造成").withStyle(ChatFormatting.WHITE).
                append(Component.literal("14倍等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
        components.add(Component.literal(" - 引爆标记后，你将获得持续3s的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("12%总")));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.SuffixOfMoon(components);
        super.appendHoverText(stack, level, components, flag);
    }

    public static Map<Player, Integer> playerDamageEnhanceTickMap = new HashMap<>();

    public static double MoonBook(Player player, Mob mob) {
        if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.MoonBook.get())) {
            int TickCount = player.getServer().getTickCount();
            if (PlayerMoonBookMap.containsKey(player) && !PlayerMoonBookMap.get(player).equals(mob)) {
                Mob oldMob = PlayerMoonBookMap.get(player);
                oldMob.removeEffect(MobEffects.GLOWING);
                PlayerMoonBookCountMap.put(player, 0);
                Compute.removeMobEffectHudToNearPlayer(oldMob, ModItems.MoonSoul.get(), "MoonBookCount");
            }
            mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 88888, 1, false, false));
            PlayerMoonBookMap.put(player, mob);
            int count = PlayerMoonBookCountMap.getOrDefault(player, 0);
            PlayerMoonBookCountMap.put(player, ++count);
            if (count == 7) {
                count = 0;
                PlayerMoonBookCountMap.put(player, count);
                playerDamageEnhanceTickMap.put(player, TickCount + 60);
                Compute.sendEffectLastTime(player, ModItems.MoonSoul.get().getDefaultInstance(), 60);
                Compute.removeMobEffectHudToNearPlayer(mob, ModItems.MoonSoul.get(), "MoonBookCount");
                return Compute.XpStrengthAPDamage(player, 14);
            } else {
                Compute.sendMobEffectHudToNearPlayer(mob, ModItems.MoonSoul.get(), "MoonBookCount", 8888, count, true);
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
