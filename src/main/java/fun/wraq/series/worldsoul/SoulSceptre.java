package fun.wraq.series.worldsoul;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SoulSceptre extends WraqSceptre {

    public SoulSceptre(Properties p_42964_) {
        super(p_42964_);
        Utils.manaDamage.put(this, SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaAttackDamage);
        Utils.manaRecover.put(this, SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaRecover);
        Utils.manaPenetration0.put(this, SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaPenetration0);
        Utils.levelRequire.put(this, 260);
    }

    @Override
    protected EntityType<ManaArrow> getArrowType() {
        return ModEntityType.NEW_ARROW_WORLD.get();
    }

    @Override
    protected String getParticleType() {
        return StringUtils.ParticleTypes.Sky;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfWorld;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionActive(components, Component.literal("本源打击-陨石").withStyle(style));
        components.add(Component.literal(" 指针处将坠下一枚陨石，陨石会对一定范围内的怪物随机造成持续").withStyle(ChatFormatting.WHITE).
                append(Component.literal("4s").withStyle(style)).
                append(Component.literal("的负面效果").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal("  1.中毒：每秒造成").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaDamage("30%")));
        components.add(Component.literal("  2.缓慢：减缓目标").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.movementSpeedWithoutBattle("")));
        components.add(Component.literal("  3.燃烧：每秒造成").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaDamage("35%")));
        components.add(Component.literal("  4.致残：大幅降低目标").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.movementSpeedWithoutBattle("")));
        components.add(Component.literal(" 对一定范围内的玩家随机造成").withStyle(ChatFormatting.WHITE).
                append(Component.literal("增益效果").withStyle(ChatFormatting.GREEN)));
        components.add(Component.literal("  1.治疗：回复已损失生命值10%的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.health("")));
        components.add(Component.literal("  2.抗性：持续4秒的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaDamage("5%")).
                append(ComponentUtils.AttributeDescription.defence("")).
                append(ComponentUtils.AttributeDescription.manaDamage("5%")).
                append(ComponentUtils.AttributeDescription.manaDefence("")));
        components.add(Component.literal("  3.攻击增幅：持续4秒的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.attackDamage("10%")).
                append(ComponentUtils.AttributeDescription.manaDamage("10%")));
        components.add(Component.literal("  4.穿透增幅：持续4秒的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.defencePenetration("20%")).
                append(ComponentUtils.AttributeDescription.manaPenetration("20%")));
        ComponentUtils.coolDownTimeDescription(components, 8);
        ComponentUtils.manaCostDescription(components, 120);
        components.add(Component.literal(" Idea From:Mr_RED").withStyle(ChatFormatting.LIGHT_PURPLE));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix1Wraq();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND) && player.isShiftKeyDown()) {
            try {
                SoulEquipAttribute.Forging(player.getItemInHand(InteractionHand.MAIN_HAND), player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(Items.AIR)
                && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
            if (data.contains(StringUtils.ManaCore.ManaCore)) {
                if (Utils.ManaCoreMap.isEmpty()) Utils.setManaCoreMap();
                player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Utils.ManaCoreMap.get(data.getString(StringUtils.ManaCore.ManaCore))));
                data.remove(StringUtils.ManaCore.ManaCore);
            }
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static double getManaCost(CompoundTag data) {
        if (data.contains(StringUtils.SoulEquipForge))
            return SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaCost - SoulEquipAttribute.ForgingAddition.ManaCost;
        else return SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaCost;
    }
}


