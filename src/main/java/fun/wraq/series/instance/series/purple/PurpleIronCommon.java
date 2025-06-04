package fun.wraq.series.instance.series.purple;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public interface PurpleIronCommon {
    int getPassiveTier();

    static void setDescription(List<Component> components, int tier) {
        ComponentUtils.descriptionPassive(components, Te.m("紫晶之耀", CustomStyle.styleOfPurpleIron));
        components.add(Te.s(" 攻击一名敌方单位时，在其所在位置生成一枚", "紫水晶", CustomStyle.styleOfPurpleIron));
        components.add(Te.s(" 紫水晶", CustomStyle.styleOfPurpleIron, "将周期性辐散", "紫晶之耀", CustomStyle.styleOfPurpleIron));
        components.add(Te.s(" 紫晶之耀", CustomStyle.styleOfPurpleIron, "会对周围敌人造成",
                String.format("%.0f%%", effect[tier] * 100) + "自适应伤害", CustomStyle.styleOfSea));
        components.add(Te.s(" 为周围玩家提供:"));
        components.add(Te.s(" ", ComponentUtils.AttributeDescription.defence("25"), "与", ComponentUtils.AttributeDescription.manaDefence("25")));
        components.add(Te.s(" 只有当能够生成紫水晶方块时，才会计算冷却时间与效果", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        ComponentUtils.coolDownTimeDescription(components, 30);
        components.add(Te.s(" 冷却时间不受冷却缩减影响", ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }

    double[] effect = new double[]{0.2, 0.35, 0.5, 0.65};

    Map<Player, Integer> nextAllowTrigTick = new WeakHashMap<>();

    record BlockInfo(BlockPos blockPos, Player creator, Level level, int lifeEndTick, int tier) {
    }

    List<BlockInfo> blockInfoList = new ArrayList<>();
    Set<BlockPos> blockPosSet = new HashSet<>();

    static void onHit(Player player, Mob mob, Item item) {
        if (nextAllowTrigTick.getOrDefault(player, 0) < Tick.get()) {
            BlockPos blockPos = mob.blockPosition().above();
            Level level = mob.level();
            if (level.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {
                // 允许生成
                nextAllowTrigTick.put(player, Tick.get() + 600);
                player.getCooldowns().addCooldown(item, 600);

                // 生成
                while (level.getBlockState(blockPos.below()).is(Blocks.AIR)) {
                    blockPos = blockPos.below();
                }
                blockInfoList.add(new BlockInfo(blockPos, player, level,
                        Tick.get() + 600, ((PurpleIronCommon) item).getPassiveTier()));
                blockPosSet.add(blockPos);
                level.setBlockAndUpdate(blockPos, Blocks.AMETHYST_CLUSTER.defaultBlockState());

                MySound.soundToNearPlayer(level, blockPos.getCenter(), SoundEvents.AMETHYST_CLUSTER_PLACE);
            }
        }
    }

    static void handleServerTick() {
        List<BlockInfo> removeList = new ArrayList<>();
        blockInfoList.forEach(info -> {
            // 应当摧毁
            if (info.lifeEndTick < Tick.get()) {
                info.level.destroyBlock(info.blockPos, false);
                removeList.add(info);
            } else {
                // 对怪物造成伤害
                Compute.getNearEntity(info.level, info.blockPos.getCenter(), Mob.class, 6)
                        .stream().map(entity -> (Mob) entity)
                        .forEach(mob -> {
                            Damage.causeAutoAdaptionRateDamageToMob(info.creator, mob, effect[info.tier], false);
                        });

                // 对玩家带来增益
                Compute.getNearEntity(info.level, info.blockPos.getCenter(), Player.class, 6)
                        .stream().map(entity -> (Player) entity)
                        .forEach(player -> {
                            StableAttributesModifier.addM(player, StableAttributesModifier.playerManaDefenceModifier,
                                    "PurpleIronPassiveEquip cluster passive", 25, Tick.get() + 60, ModItems.PURPLE_IRON_BUD_3.get());
                            StableAttributesModifier.addM(player, StableAttributesModifier.playerDefenceModifier,
                                    "PurpleIronPassiveEquip cluster passive", 25, Tick.get() + 60, ModItems.PURPLE_IRON_BUD_3.get());
                        });

                MySound.soundToNearPlayer(info.level, info.blockPos.getCenter(), SoundEvents.AMETHYST_CLUSTER_HIT);
                ParticleProvider.createBallDisperseParticle(ParticleTypes.END_ROD,
                        (ServerLevel) info.level, info.blockPos.getCenter(), 0.5, 20);
            }
        });
        removeList.forEach(info -> {
            blockPosSet.remove(info.blockPos);
        });
        blockInfoList.removeAll(removeList);
    }

    static void handlePlayerTick(Player player) {
        int radius = 5;
        Level level = player.level();
        for (int i = player.getBlockX() - radius; i <= player.getBlockX() + radius; i ++) {
            for (int j = player.getBlockY() - radius; j <= player.getBlockY() + radius; j ++) {
                for (int k = player.getBlockZ() - radius; k <= player.getBlockZ() + radius; k ++) {
                    BlockPos blockPos = new BlockPos(i, j, k);
                    if (!blockPosSet.contains(blockPos)) {
                        BlockState blockState = level.getBlockState(blockPos);
                        if (blockState.is(Blocks.AMETHYST_CLUSTER)) {
                            level.destroyBlock(blockPos, false);
                        }
                    }
                }
            }
        }
    }

    static void destroyOnServerStop() {
        blockInfoList.forEach(info -> {
            info.level.destroyBlock(info.blockPos, false);
        });
        blockInfoList.clear();
    }
}
