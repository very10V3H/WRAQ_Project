package com.very.wraq.series.overworld.sakuraSeries.Boss2;

import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.events.mob.chapter2.SlimeSpawnController;
import com.very.wraq.events.mob.chapter3_nether.MagmaSpawnController;
import com.very.wraq.projectiles.ForgeItem;
import com.very.wraq.projectiles.OnKillEffectOffHandItem;
import com.very.wraq.projectiles.WraqOffHandItem;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.StableAttributesModifier;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.series.overworld.chapter1.Mine.MineShield;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.*;

public class GoldenAttackOffhand extends WraqOffHandItem implements OnKillEffectOffHandItem, ForgeItem {

    private final int type;
    public GoldenAttackOffhand(Properties properties, Component component, int type) {
        super(properties, component);
        this.type = type;
        if (type == 0) {
            Utils.defence.put(this, 500d);
            Utils.maxHealth.put(this, 500d);
            Utils.attackDamage.put(this, 50d);
            Utils.critDamage.put(this, 0.15);
        } else {
            Utils.critRate.put(this, 0.12);
            Utils.defencePenetration0.put(this, 600d);
            Utils.attackDamage.put(this, 50d);
            Utils.critDamage.put(this, 0.15);
        }

        Utils.expUp.put(this, 1.25);
        if (type == 0) Utils.shieldTag.put(this, 1d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfGold;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        if (type == 0) MineShield.shieldAdditionDescription(components);
        Compute.DescriptionPassive(components, Component.literal("华贵金属").withStyle(style));
        components.add(Component.literal(" 副手").withStyle(ChatFormatting.GOLD).
                append(Component.literal("持有该物品时，").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("主手").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("手持").withStyle(ChatFormatting.WHITE)).
                append(ModItems.goldCoin.get().getDefaultInstance().getDisplayName()).
                append(Component.literal("右键消耗，获得:").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 1.").withStyle(style).
                append(ComponentUtils.AttributeDescription.CritRate("25%")));
        components.add(Component.literal(" 2.").withStyle(style).
                append(ComponentUtils.AttributeDescription.CritDamage("100%")));
        components.add(Component.literal(" 3.").withStyle(style).
                append(ComponentUtils.AttributeDescription.DefencePenetration("30%")));
        components.add(Component.literal(" 持续5min，在持续期间").withStyle(ChatFormatting.WHITE).
                append(Component.literal("击杀").withStyle(ChatFormatting.RED)).
                append(Component.literal("任意怪物，将有").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("4.5%").withStyle(style)).
                append(Component.literal("的概率为你提供1枚").withStyle(ChatFormatting.WHITE)).
                append(ModItems.goldCoin.get().getDefaultInstance().getDisplayName()));
        components.add(Component.literal(" 当副手不再是此物品时，华贵金属的全部效果会即刻失效").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterV();
    }


    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static Map<String, Integer> passiveLastTimeMap = new HashMap<>();
    public static String critRateTag = "GoldenBookPassiveCritRate";
    public static String critDamageTag = "GoldenBookPassiveCritDamage";
    public static String defencePenetrationTag = "GoldenBookPassiveDefencePenetration";

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.OFF_HAND)) {
            ItemStack targetGoldCoin = player.getMainHandItem();
            int tick = player.getServer().getTickCount();
            if (targetGoldCoin.is(ModItems.goldCoin.get())) {
                targetGoldCoin.shrink(1);
                passiveLastTimeMap.put(player.getName().getString(), tick + 6000);
                StableAttributesModifier.addAttributeModifier(player, StableAttributesModifier.playerCritRateModifier,
                        new StableAttributesModifier(critRateTag, 0.25, tick + 6000));
                StableAttributesModifier.addAttributeModifier(player, StableAttributesModifier.playerCritDamageModifier,
                        new StableAttributesModifier(critDamageTag, 1, tick + 6000));
                StableAttributesModifier.addAttributeModifier(player, StableAttributesModifier.playerDefencePenetrationModifier,
                        new StableAttributesModifier(defencePenetrationTag, 0.3, tick + 6000));
                Compute.effectLastTimeSend(player, ModItems.goldCoin.get(), 6000);
            }
        }
        return super.use(level, player, interactionHand);
    }


    @Override
    public void onKill(Player player, Mob mob) {
        int tick = player.getServer().getTickCount();
        String name = player.getName().getString();
        if (passiveLastTimeMap.getOrDefault(name, 0) > tick) {
            Random random = new Random();
            boolean isSlime = MobSpawn.getMobOriginName(mob).equals(SlimeSpawnController.mobName) || MobSpawn.getMobOriginName(mob).equals(MagmaSpawnController.mobName);
            if (random.nextDouble() < (isSlime ? 0.015 : 0.045)) {
                ItemStack goldCoin = ModItems.goldCoin.get().getDefaultInstance();
                Compute.sendFormatMSG(player, Component.literal("华贵金属").withStyle(CustomStyle.styleOfGold),
                        Component.literal("额外获得了1枚").withStyle(ChatFormatting.AQUA).
                                append(goldCoin.getDisplayName()));
                Compute.itemStackGive(player, goldCoin);
                Compute.soundToPlayer(player, SoundEvents.ARMOR_EQUIP_GOLD);
            }
        }
    }

    public static void tick(Player player) {
        int tick = player.getServer().getTickCount();
        String name = player.getName().getString();
        if (passiveLastTimeMap.getOrDefault(name, 0) > tick && !(player.getOffhandItem().getItem() instanceof GoldenAttackOffhand)) {
            passiveLastTimeMap.put(name, 0);
            StableAttributesModifier.removeAttributeModifierByTag(player, StableAttributesModifier.playerCritRateModifier, critRateTag);
            StableAttributesModifier.removeAttributeModifierByTag(player, StableAttributesModifier.playerCritDamageModifier, critDamageTag);
            StableAttributesModifier.removeAttributeModifierByTag(player, StableAttributesModifier.playerDefencePenetrationModifier, defencePenetrationTag);
            Compute.removeEffectLastTime(player, ModItems.goldCoin.get());
        }
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.Boss2Piece.get(), 256));
            add(new ItemStack(ModItems.goldCoin.get(), 128));
            add(new ItemStack(ModItems.completeGem.get(), 4));
            add(new ItemStack(ModItems.ReputationMedal.get(), 16));
            add(new ItemStack(ModItems.RefiningGold.get(), 1));
            add(new ItemStack(ModItems.WorldSoul3.get(), 1));
        }};
    }
}
