package fun.wraq.series.overworld.sakura.EarthMana;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.EarthPower.EarthPowerS2CPacket;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.PowerLogic;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class EarthPower extends WraqPower {

    public EarthPower(Properties p_41383_) {
        super(p_41383_);
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
    public Component getActiveName() {
        return Component.literal("汲蕴").withStyle(CustomStyle.styleOfBloodMana);
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal(" 依据").withStyle(ChatFormatting.WHITE).
                append(Component.literal("周边环境的物质").withStyle(CustomStyle.styleOfMoon)).
                append(Component.literal("提供不同的效果:").withStyle(ChatFormatting.WHITE)));

        components.add(Component.literal(" 平原:").withStyle(CustomStyle.styleOfPlain).
                append(Component.literal(" 降低怪物造成的伤害20%").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("    治疗附近所有玩家").withStyle(ChatFormatting.WHITE).
                append(Component.literal("能力-智力 * 30").withStyle(CustomStyle.styleOfMana)).
                append(ComponentUtils.AttributeDescription.maxHealth("")));

        components.add(Component.literal(" 森林:").withStyle(CustomStyle.styleOfForest).
                append(Component.literal(" 对周围怪物造成减速效果").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("    提升周围玩家的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.defence("25%")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDefence("25%")));

        components.add(Component.literal(" 澈源:").withStyle(CustomStyle.styleOfLake).
                append(Component.literal(" 削减周围怪物").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDefence("40%")));
        components.add(Component.literal("    使周围玩家获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.releaseSpeed("30")));

        components.add(Component.literal(" 火山:").withStyle(CustomStyle.styleOfVolcano).
                append(Component.literal(" 额外造成一次").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamageValue("400%")));
        components.add(Component.literal("    使周围玩家获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.attackDamage("25%")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamage("25%")));
        components.add(Component.literal(" - 对玩家的增益效果均持续3s").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" - 对怪物的负面效果均持续3s").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));

        ComponentUtils.coolDownTimeDescription(components, 15);
        ComponentUtils.manaCostDescription(components, 225);

        ClientUtils.EarthPowerCompute = true;
        if (ClientUtils.EarthPowerType != -1) {
            components.add(Component.literal(" - 当前所处环境中的主导物质类型为: ").withStyle(CustomStyle.styleOfBloodMana).
                    append(Component.literal(elements[ClientUtils.EarthPowerType]).withStyle(styles[ClientUtils.EarthPowerType])));
        }

        components.add(Component.literal(" - IDEA FROM : AzusaLin").withStyle(ChatFormatting.LIGHT_PURPLE));
        return components;
    }

    @Override
    public int getCoolDownSecond() {
        return 15;
    }

    @Override
    public double getManaCost() {
        return 225;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix1Sakura();
    }

    @Override
    public void release(Player player) {
        Compute.playerItemCoolDown(player, ModItems.EarthPower.get(), 15);
        ModNetworking.sendToClient(new EarthPowerS2CPacket(), (ServerPlayer) player);
    }

    public static WeakHashMap<Mob, Integer> Plain_MobDamageDecrease = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> Forest_PlayerDefenceEnhance = new WeakHashMap<>();
    public static WeakHashMap<Mob, Integer> Lake_MobManaDefenceDecrease = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> Lake_PlayerCoolDownEnhance = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> Volcano_PlayerDamageEnhance = new WeakHashMap<>();

    public static double MobDamageDecrease(Mob mob) {
        int TickCount = Tick.get();
        if (Plain_MobDamageDecrease.containsKey(mob) && Plain_MobDamageDecrease.get(mob) > TickCount) {
            return -0.25;
        }
        return 0;
    }

    public static double PlayerDefenceEnhance(Player player) {
        if (Forest_PlayerDefenceEnhance.containsKey(player) && Forest_PlayerDefenceEnhance.get(player) > Tick.get()) {
            return 0.25;
        }
        return 0;
    }

    public static double MobManaDefenceDecrease(Mob mob) {
        int TickCount = Tick.get();
        if (Lake_MobManaDefenceDecrease.containsKey(mob) && Lake_MobManaDefenceDecrease.get(mob) > TickCount) {
            return -0.4;
        }
        return 0;
    }

    public static double PlayerCoolDownEnhance(Player player) {
        int TickCount = Tick.get();
        if (Lake_PlayerCoolDownEnhance.containsKey(player) && Lake_PlayerCoolDownEnhance.get(player) > TickCount) {
            return 0.3;
        }
        return 0;
    }

    public static double PlayerDamageEnhance(Player player) {
        int TickCount = Tick.get();
        if (Volcano_PlayerDamageEnhance.containsKey(player) && Volcano_PlayerDamageEnhance.get(player) > TickCount) {
            return 0.25;
        }
        return 0;
    }

    public static void Active(Player player, int type) {
        int tick = Tick.get();
        Vec3 targetPos = player.pick(15, 0, false).getLocation();
        if (Compute.detectPlayerPickMob(player) != null) targetPos = Compute.detectPlayerPickMob(player).position();
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,
                AABB.ofSize(targetPos, 20, 20, 20));
        mobList.removeIf(mob1 -> mob1.distanceTo(player) > 6);
        List<Player> playerList = player.level().getEntitiesOfClass(Player.class,
                AABB.ofSize(targetPos, 20, 20, 20));
        playerList.removeIf(player1 -> player1.distanceTo(player1) > 6);
        mobList.forEach(mob -> {
            Damage.causeRateApDamageToMonster(player, mob, 3, true);
            PowerLogic.PlayerPowerEffectToMob(player, mob);
        });
        switch (type) {
            case 0 -> {
                mobList.forEach(mob1 -> {
                    Plain_MobDamageDecrease.put(mob1, tick + 80);
                    Compute.addDamageDecreaseEffectParticle(mob1, 80);
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
                    Compute.sendEffectLastTime(player1, ModItems.EarthPower.get().getDefaultInstance(), 60);
                    Forest_PlayerDefenceEnhance.put(player1, tick + 60);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                });
            }
            case 2 -> {
                mobList.forEach(mob1 -> {
                    Lake_MobManaDefenceDecrease.put(mob1, tick + 60);
                    Compute.addManaDefenceDecreaseEffectParticle(mob1, 60);
                });
                playerList.forEach(player1 -> {
                    Compute.sendEffectLastTime(player1, ModItems.EarthPower.get().getDefaultInstance(), 60);
                    Lake_PlayerCoolDownEnhance.put(player1, tick + 60);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1.25, 0.4, 8, ParticleTypes.DRIPPING_WATER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.DRIPPING_WATER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.DRIPPING_WATER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.DRIPPING_WATER, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.DRIPPING_WATER, 0);
                });
            }
            case 3 -> {
                playerList.forEach(player1 -> {
                    Compute.sendEffectLastTime(player1, ModItems.EarthPower.get().getDefaultInstance(), 60);
                    Volcano_PlayerDamageEnhance.put(player1, tick + 60);
                });
                mobList.forEach(mob1 -> {
                    Damage.causeRateApDamageToMonster(player, mob1, 3, true);
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
    public double manaCost(Player player) {
        return 225;
    }
}
