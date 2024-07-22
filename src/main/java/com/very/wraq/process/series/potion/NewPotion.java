package com.very.wraq.process.series.potion;

import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewPotion extends Item implements ActiveItem {

    private final String potionName;
    private final int type; // 0 - 原始药水 1 - 浓缩药水 2 - 延时药水

    public static class PotionName {
        public static String AttackUp = "attackup";
        public static String DefencePenetrationUp = "breakdefenceup";
        public static String ManaPenetrationUp = "breakmanadefenceup";
        public static String CooldownUp = "cooldownup";
        public static String CritDamageUp = "critdamageup";

        public static String CritRateUp = "critrateup";
        public static String DefenceUp = "defenceup";
        public static String HealthStealUp = "healstealup";
        public static String ManaDamageUp = "manadamageup";
        public static String ManaDefenceUp = "manadefenceup";

        public static String ManaRecoverUp = "manareplyup";
        public static String MovementSpeedUp = "speedup";
        public static String HealthRecoverUp = "healreply";

        public static String damageEnhance = "damage_enhance";
        public static String attackDamageEnhance = "attack_damage_enhance";
        public static String manaDamageEnhance = "mana_damage_enhance";
        public static String giant = "giant";
        public static String stone = "stone";
        public static String exHarvest = "ex_harvest";
    }

    public static Map<String, Component> effect0DescriptionMap = new HashMap<>() {{
        put(PotionName.AttackUp, Compute.AttributeDescription.AttackDamage("25 + 25%"));
        put(PotionName.DefencePenetrationUp, Compute.AttributeDescription.DefencePenetration("20%"));
        put(PotionName.ManaPenetrationUp, Compute.AttributeDescription.ManaPenetration("20%"));
        put(PotionName.CooldownUp, Compute.AttributeDescription.CoolDown("35"));
        put(PotionName.CritDamageUp, Compute.AttributeDescription.CritDamage("40%"));

        put(PotionName.CritRateUp, Compute.AttributeDescription.CritRate("20%"));
        put(PotionName.DefenceUp, Compute.AttributeDescription.Defence("80 + 25%"));
        put(PotionName.HealthStealUp, Compute.AttributeDescription.HealthSteal("12%"));
        put(PotionName.ManaDamageUp, Compute.AttributeDescription.ManaDamage("25 + 25%"));
        put(PotionName.ManaDefenceUp, Compute.AttributeDescription.ManaDefence("75 + 25%"));

        put(PotionName.MovementSpeedUp, ComponentUtils.AttributeDescription.movementSpeed("30%"));
        put(PotionName.HealthRecoverUp, Compute.AttributeDescription.HealthRecover("2.5%"));
        put(PotionName.ManaRecoverUp, Compute.AttributeDescription.ManaRecover("10"));

        put(PotionName.damageEnhance, Component.literal("35%伤害提升").withStyle(CustomStyle.styleOfPower));
        put(PotionName.attackDamageEnhance, Component.literal("35%物理伤害提升").withStyle(CustomStyle.styleOfPower));
        put(PotionName.manaDamageEnhance, Component.literal("35%魔法伤害提升").withStyle(CustomStyle.styleOfMana));
        put(PotionName.giant, Component.literal("15%最大生命值").withStyle(CustomStyle.styleOfLife));
        put(PotionName.stone, Component.literal("15%伤害减免").withStyle(CustomStyle.styleOfMine));
        put(PotionName.exHarvest, Component.literal("15%额外产出").withStyle(CustomStyle.styleOfGold));

    }};

    public static Map<String, Component> effect1DescriptionMap = new HashMap<>() {{
        put(PotionName.AttackUp, Compute.AttributeDescription.AttackDamage("40 + 40%"));
        put(PotionName.DefencePenetrationUp, Compute.AttributeDescription.DefencePenetration("45%"));
        put(PotionName.ManaPenetrationUp, Compute.AttributeDescription.ManaPenetration("45%"));
        put(PotionName.CooldownUp, Compute.AttributeDescription.CoolDown("80"));
        put(PotionName.CritDamageUp, Compute.AttributeDescription.CritDamage("80%"));

        put(PotionName.CritRateUp, Compute.AttributeDescription.CritRate("40%"));
        put(PotionName.DefenceUp, Compute.AttributeDescription.Defence("160 + 40%"));
        put(PotionName.HealthStealUp, Compute.AttributeDescription.HealthSteal("25%"));
        put(PotionName.ManaDamageUp, Compute.AttributeDescription.ManaDamage("40 + 40%"));
        put(PotionName.ManaDefenceUp, Compute.AttributeDescription.ManaDefence("125 + 40%"));

        put(PotionName.MovementSpeedUp, ComponentUtils.AttributeDescription.movementSpeed("60%"));
        put(PotionName.HealthRecoverUp, Compute.AttributeDescription.HealthRecover("5%"));
        put(PotionName.ManaRecoverUp, Compute.AttributeDescription.ManaRecover("25"));

        put(PotionName.damageEnhance, Component.literal("50%伤害提升").withStyle(CustomStyle.styleOfPower));
        put(PotionName.attackDamageEnhance, Component.literal("50%物理伤害提升").withStyle(CustomStyle.styleOfPower));
        put(PotionName.manaDamageEnhance, Component.literal("50%魔法伤害提升").withStyle(CustomStyle.styleOfMana));
        put(PotionName.giant, Component.literal("25%最大生命值").withStyle(CustomStyle.styleOfLife));
        put(PotionName.stone, Component.literal("25%伤害减免").withStyle(CustomStyle.styleOfMine));
        put(PotionName.exHarvest, Component.literal("25%额外产出").withStyle(CustomStyle.styleOfGold));
    }};

    public NewPotion(Properties p_41383_, String potionName, int type) {
        super(p_41383_);
        this.potionName = potionName;
        this.type = type;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        String time = "4min";
        if (type == 1) time = "2min";
        if (type == 2) time = "8min";

        if (effect1DescriptionMap.containsKey(this.potionName) && effect0DescriptionMap.containsKey(this.potionName)) {
            if (this.type == 1) {
                components.add(Component.literal(" 提供: ").withStyle(ChatFormatting.WHITE).
                        append(effect1DescriptionMap.get(this.potionName)).
                        append(Component.literal(" 持续" + time).withStyle(ChatFormatting.WHITE)));
            } else {
                components.add(Component.literal(" 提供: ").withStyle(ChatFormatting.WHITE).
                        append(effect0DescriptionMap.get(this.potionName)).
                        append(Component.literal(" 持续" + time).withStyle(ChatFormatting.WHITE)));
            }
        }
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide() && interactionHand == InteractionHand.MAIN_HAND) {
            active(player);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void active(Player player) {
        int tick = 4800;
        if (type == 1) tick = 2400;
        if (type == 2) tick = 9600;
        player.addEffect(new MobEffectInstance(ForgeRegistries.MOB_EFFECTS.
                getValue(new ResourceLocation(Utils.MOD_ID, potionName)), tick, type == 1 ? 1 : 0));
        Compute.playerItemUseWithRecord(player);
        Item item = player.getMainHandItem().getItem();
        player.getCooldowns().addCooldown(item, 20);
    }
}
