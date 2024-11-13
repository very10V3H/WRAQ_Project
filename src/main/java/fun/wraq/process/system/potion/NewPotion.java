package fun.wraq.process.system.potion;

import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
        put(PotionName.AttackUp, ComponentUtils.AttributeDescription.attackDamage("25 + 25%"));
        put(PotionName.DefencePenetrationUp, ComponentUtils.AttributeDescription.defencePenetration("20%"));
        put(PotionName.ManaPenetrationUp, ComponentUtils.AttributeDescription.manaPenetration("20%"));
        put(PotionName.CooldownUp, ComponentUtils.AttributeDescription.releaseSpeed("20"));
        put(PotionName.CritDamageUp, ComponentUtils.AttributeDescription.critDamage("40%"));

        put(PotionName.CritRateUp, ComponentUtils.AttributeDescription.critRate("20%"));
        put(PotionName.DefenceUp, ComponentUtils.AttributeDescription.defence("80 + 25%"));
        put(PotionName.HealthStealUp, ComponentUtils.AttributeDescription.healthSteal("12%"));
        put(PotionName.ManaDamageUp, ComponentUtils.AttributeDescription.manaDamage("25 + 25%"));
        put(PotionName.ManaDefenceUp, ComponentUtils.AttributeDescription.manaDefence("75 + 25%"));

        put(PotionName.MovementSpeedUp, ComponentUtils.AttributeDescription.movementSpeed("30%"));
        put(PotionName.HealthRecoverUp, ComponentUtils.AttributeDescription.healthRecover("2.5%"));
        put(PotionName.ManaRecoverUp, ComponentUtils.AttributeDescription.manaRecover("10"));

        put(PotionName.damageEnhance, Component.literal("35%伤害提升").withStyle(CustomStyle.styleOfPower));
        put(PotionName.attackDamageEnhance, Component.literal("35%物理伤害提升").withStyle(CustomStyle.styleOfPower));
        put(PotionName.manaDamageEnhance, Component.literal("35%魔法伤害提升").withStyle(CustomStyle.styleOfMana));
        put(PotionName.giant, Component.literal("15%最大生命值").withStyle(CustomStyle.styleOfLife));
        put(PotionName.stone, Component.literal("15%伤害减免").withStyle(CustomStyle.styleOfMine));
        put(PotionName.exHarvest, Component.literal("15%额外产出").withStyle(CustomStyle.styleOfGold));

    }};

    public static Map<String, Component> effect1DescriptionMap = new HashMap<>() {{
        put(PotionName.AttackUp, ComponentUtils.AttributeDescription.attackDamage("40 + 40%"));
        put(PotionName.DefencePenetrationUp, ComponentUtils.AttributeDescription.defencePenetration("45%"));
        put(PotionName.ManaPenetrationUp, ComponentUtils.AttributeDescription.manaPenetration("45%"));
        put(PotionName.CooldownUp, ComponentUtils.AttributeDescription.releaseSpeed("40"));
        put(PotionName.CritDamageUp, ComponentUtils.AttributeDescription.critDamage("80%"));

        put(PotionName.CritRateUp, ComponentUtils.AttributeDescription.critRate("40%"));
        put(PotionName.DefenceUp, ComponentUtils.AttributeDescription.defence("160 + 40%"));
        put(PotionName.HealthStealUp, ComponentUtils.AttributeDescription.healthSteal("25%"));
        put(PotionName.ManaDamageUp, ComponentUtils.AttributeDescription.manaDamage("40 + 40%"));
        put(PotionName.ManaDefenceUp, ComponentUtils.AttributeDescription.manaDefence("125 + 40%"));

        put(PotionName.MovementSpeedUp, ComponentUtils.AttributeDescription.movementSpeed("60%"));
        put(PotionName.HealthRecoverUp, ComponentUtils.AttributeDescription.healthRecover("5%"));
        put(PotionName.ManaRecoverUp, ComponentUtils.AttributeDescription.manaRecover("25"));

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
    public void active(Player player) {
        int tick = 4800;
        if (type == 1) tick = 2400;
        if (type == 2) tick = 9600;
        player.addEffect(new MobEffectInstance(ForgeRegistries.MOB_EFFECTS.
                getValue(new ResourceLocation(Utils.MOD_ID, potionName)), tick, type == 1 ? 1 : 0));
        InventoryOperation.removeItem(player.getInventory(), this, 1);
        player.getCooldowns().addCooldown(this, 20);
    }
}
