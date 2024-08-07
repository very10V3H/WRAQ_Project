package com.very.wraq.series.overworld.sakuraSeries.EarthMana;

import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.process.func.power.PowerLogic;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ClientUtils;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EarthPower extends Item implements ActiveItem {

    public EarthPower(Properties p_41383_) {
        super(p_41383_);
        Utils.powerTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    private final String[] elements = {
            "平原", "森林", "澈源", "火山"
    };

    private final Style[] styles = {
            CustomStyle.styleOfPlain,
            CustomStyle.styleOfForest,
            CustomStyle.styleOfLake,
            CustomStyle.styleOfVolcano
    };

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·法术").withStyle(CustomStyle.styleOfMana));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        Compute.DescriptionActive(components, Component.literal("汲蕴").withStyle(CustomStyle.styleOfBloodMana));
        components.add(Component.literal(" 依据").withStyle(ChatFormatting.WHITE).
                append(Component.literal("周边环境的物质").withStyle(CustomStyle.styleOfMoon)).
                append(Component.literal("提供不同的效果:").withStyle(ChatFormatting.WHITE)));

        components.add(Component.literal(" 平原:").withStyle(CustomStyle.styleOfPlain).
                append(Component.literal(" 降低怪物造成的伤害20%").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("    治疗附近所有玩家").withStyle(ChatFormatting.WHITE).
                append(Component.literal("能力-智力 * 30").withStyle(CustomStyle.styleOfMana)).
                append(Compute.AttributeDescription.MaxHealth("")));

        components.add(Component.literal(" 森林:").withStyle(CustomStyle.styleOfForest).
                append(Component.literal(" 对周围怪物造成减速效果").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("    提升周围玩家的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("25%")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDefence("25%")));

        components.add(Component.literal(" 澈源:").withStyle(CustomStyle.styleOfLake).
                append(Component.literal(" 削减周围怪物").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDefence("40%")));
        components.add(Component.literal("    使周围玩家获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CoolDown("30")));

        components.add(Component.literal(" 火山:").withStyle(CustomStyle.styleOfVolcano).
                append(Component.literal(" 额外造成一次").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamageValue("400%")));
        components.add(Component.literal("    使周围玩家获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("25%")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("25%")));
        components.add(Component.literal(" - 对玩家的增益效果均持续3s").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" - 对怪物的负面效果均持续3s").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));

        Compute.CoolDownTimeDescription(components, 15);
        Compute.ManaCostDescription(components, 225);

        ClientUtils.EarthPowerCompute = true;
        if (ClientUtils.EarthPowerType != -1) {
            components.add(Component.literal(" - 当前所处环境中的主导物质类型为: ").withStyle(CustomStyle.styleOfBloodMana).
                    append(Component.literal(elements[ClientUtils.EarthPowerType]).withStyle(styles[ClientUtils.EarthPowerType])));
        }

        components.add(Component.literal(" - IDEA FROM : AzusaLin").withStyle(ChatFormatting.LIGHT_PURPLE));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryV(components);
        components.add(ComponentUtils.getDemonAndElementStorySuffix1Sakura());
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.use(player);
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static Map<Mob, Integer> Plain_MobDamageDecrease = new HashMap<>();
    public static Map<Player, Integer> Forest_PlayerDefenceEnhance = new HashMap<>();
    public static Map<Mob, Integer> Lake_MobManaDefenceDecrease = new HashMap<>();
    public static Map<Player, Integer> Lake_PlayerCoolDownEnhance = new HashMap<>();
    public static Map<Player, Integer> Volcano_PlayerDamageEnhance = new HashMap<>();

    public static double MobDamageDecrease(Mob mob) {
        int TickCount = mob.getServer().getTickCount();
        if (Plain_MobDamageDecrease.containsKey(mob) && Plain_MobDamageDecrease.get(mob) > TickCount) {
            return -0.25;
        }
        return 0;
    }

    public static double PlayerDefenceEnhance(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (Forest_PlayerDefenceEnhance.containsKey(player) && Forest_PlayerDefenceEnhance.get(player) > TickCount) {
            return 0.25;
        }
        return 0;
    }

    public static double MobManaDefenceDecrease(Mob mob) {
        int TickCount = mob.getServer().getTickCount();
        if (Lake_MobManaDefenceDecrease.containsKey(mob) && Lake_MobManaDefenceDecrease.get(mob) > TickCount) {
            return -0.4;
        }
        return 0;
    }

    public static double PlayerCoolDownEnhance(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (Lake_PlayerCoolDownEnhance.containsKey(player) && Lake_PlayerCoolDownEnhance.get(player) > TickCount) {
            return 0.3;
        }
        return 0;
    }

    public static double PlayerDamageEnhance(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (Volcano_PlayerDamageEnhance.containsKey(player) && Volcano_PlayerDamageEnhance.get(player) > TickCount) {
            return 0.25;
        }
        return 0;
    }

    public static void Active(Player player, int type) {
        int TickCount = player.getServer().getTickCount();
        Vec3 TargetPos = player.pick(15, 0, false).getLocation();
        if (Compute.detectPlayerPickMob(player) != null) TargetPos = Compute.detectPlayerPickMob(player).position();
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,
                AABB.ofSize(TargetPos, 20, 20, 20));
        mobList.removeIf(mob1 -> mob1.distanceTo(player) > 6);
        List<Player> playerList = player.level().getEntitiesOfClass(Player.class,
                AABB.ofSize(TargetPos, 20, 20, 20));
        playerList.removeIf(player1 -> player1.distanceTo(player1) > 6);
        mobList.forEach(mob -> {
            Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, 3, true);
            PowerLogic.PlayerPowerEffectToMob(player, mob);
        });
        switch (type) {
            case 0 -> {
                mobList.forEach(mob1 -> {
                    Plain_MobDamageDecrease.put(mob1, TickCount + 80);
                    Compute.AddDamageDescreaseEffectParticle(mob1, 80);
                });
                playerList.forEach(player1 -> {
                    Compute.playerHeal(player1, player.getPersistentData().getInt(StringUtils.Ability.Intelligent) * 30);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                });
            }
            case 1 -> {
                playerList.forEach(player1 -> {
                    Compute.effectLastTimeSend(player1, ModItems.EarthPower.get().getDefaultInstance(), 60);
                    Forest_PlayerDefenceEnhance.put(player1, TickCount + 60);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                });
            }
            case 2 -> {
                mobList.forEach(mob1 -> {
                    Lake_MobManaDefenceDecrease.put(mob1, TickCount + 60);
                    Compute.AddManaDefenceDescreaseEffectParticle(mob1, 60);
                });
                playerList.forEach(player1 -> {
                    Compute.effectLastTimeSend(player1, ModItems.EarthPower.get().getDefaultInstance(), 60);
                    Lake_PlayerCoolDownEnhance.put(player1, TickCount + 60);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1.25, 0.4, 8, ParticleTypes.DRIPPING_WATER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.DRIPPING_WATER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.DRIPPING_WATER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.DRIPPING_WATER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.DRIPPING_WATER, 0);
                });
            }
            case 3 -> {
                playerList.forEach(player1 -> {
                    Compute.effectLastTimeSend(player1, ModItems.EarthPower.get().getDefaultInstance(), 60);
                    Volcano_PlayerDamageEnhance.put(player1, TickCount + 60);
                });
                mobList.forEach(mob1 -> {
                    Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob1, 3, true);
                });
            }
        }
    }

    public static int CalculateElement(Player player, List<Block> blockList) {
        int count = 0;
        int radius = 30;
        for (int i = 0; i < radius * 2; i++) {
            for (int j = 0; j < radius * 2; j++) {
                for (int k = 0; k < radius; k++) {
                    BlockPos blockPos = new BlockPos(player.getBlockX() + i - radius,
                            player.getBlockY() + j - radius,
                            player.getBlockZ() + k - radius / 2);
                    if (blockList.contains(player.level().getBlockState(blockPos).getBlock())) count++;
                }
            }
        }
        return count;
    }

    public static int CalculatePlainElement(Player player) {
        List<Block> blockList = new ArrayList<>() {{
            add(Blocks.GRASS_BLOCK);
            add(Blocks.GRASS);
        }};
        return CalculateElement(player, blockList);
    }

    public static int CalculateForestElement(Player player) {
        int count = 0;
        int radius = 30;
        for (int i = 0; i < radius * 2; i++) {
            for (int j = 0; j < radius * 2; j++) {
                for (int k = 0; k < radius; k++) {
                    BlockPos blockPos = new BlockPos(player.getBlockX() + i - radius,
                            player.getBlockY() + j - radius,
                            player.getBlockZ() + k - radius / 2);
                    Block block = player.level().getBlockState(blockPos).getBlock();
                    if (block.toString().contains("leaves") || block.toString().contains("log")) count++;
                }
            }
        }
        return count * 20;
    }

    public static int CalculateLakeElement(Player player) {
        List<Block> blockList = new ArrayList<>() {{
            add(Blocks.WATER);
            add(Blocks.ICE);
            add(Blocks.BLUE_ICE);
            add(Blocks.FROSTED_ICE);
            add(Blocks.PACKED_ICE);
        }};
        return CalculateElement(player, blockList);
    }

    public static int CalculateVolcanoElement(Player player) {
        List<Block> blockList = new ArrayList<>() {{
            add(Blocks.OBSIDIAN);
            add(Blocks.CRYING_OBSIDIAN);
            add(Blocks.LAVA);
            add(Blocks.NETHERRACK);
        }};
        return CalculateElement(player, blockList) * 20;
    }

    public static int ElementType(Player player) {
        if (CalculatePlainElement(player) > Math.max(Math.max(CalculateForestElement(player), CalculateLakeElement(player)), CalculateVolcanoElement(player)))
            return 0;
        if (CalculateForestElement(player) > Math.max(Math.max(CalculatePlainElement(player), CalculateLakeElement(player)), CalculateVolcanoElement(player)))
            return 1;
        if (CalculateLakeElement(player) > Math.max(Math.max(CalculatePlainElement(player), CalculateForestElement(player)), CalculateVolcanoElement(player)))
            return 2;
        return 3;
    }

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, 225, true)) {
            PowerLogic.EarthPower(player, false);
            PowerLogic.PlayerReleasePowerType(player, 9);
        }
    }
}
