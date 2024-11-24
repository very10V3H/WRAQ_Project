package fun.wraq.series.overworld.chapter2.codeMana;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.Gather;
import fun.wraq.common.util.struct.Power;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.projectiles.mana.NewArrow;
import fun.wraq.render.hud.Mana;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CodeSceptre extends PickaxeItem {
    public CodeSceptre(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
        Utils.manaDamage.put(this, this.ManaDamage);
        Utils.manaRecover.put(this, this.ManaReply);
        Utils.manaPenetration.put(this, this.ManaBreakDefence);
        Utils.mainHandTag.put(this, 1d);
        Utils.sceptreTag.put(this, 1.0d);
    }

    private double ManaDamage = 50;
    private double ManaReply = 10.0f;
    private double ManaBreakDefence = 0.3F;

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        components.add(Component.literal("能量激化:").withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfVolcano));
        components.add(Component.literal("能使用").withStyle(ChatFormatting.WHITE).
                append(Component.literal("魔符").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("来强化下一次").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.WHITE);
        components.add(Component.literal("Evoker-Sceptre-X").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryII-I.").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_40994_, LivingEntity p_40995_, LivingEntity p_40996_) {
        p_40994_.hurtAndBreak(0, p_40996_, (p_41007_) -> {
            p_41007_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0f) {
            p_40998_.hurtAndBreak(0, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        CompoundTag data = player.getPersistentData();
        if (!level.isClientSide) {
            Power power = Utils.PowerMap.get((ServerPlayer) player);
            int Effect = power.get1Count() + 1;
            int Range = power.get2Count();
            int Damage = power.get3Count();
            int Break = power.get4Count();
            int Kaze = power.get5Count();
            int Snow = power.get6Count();
            int Lightning = power.get7Count();
            int Gather = power.get8Count();
            int ManaCost = 10;
            ManaCost += Effect * 10;
            ManaCost += Range * 15;
            ManaCost += Damage * 10;
            ManaCost += Break * 10;
            ManaCost += Kaze * 15;
            ManaCost += Snow * 15;
            ManaCost += Lightning * 10;
            ManaCost += Gather * 20;
            if (data.getInt("MANA") < ManaCost)
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("法力不足").withStyle(ChatFormatting.WHITE)));
            else {
                data.putInt("MANA", data.getInt("MANA") - ManaCost);
                Mana.updateManaStatus(player);
                NewArrow newArrow = new NewArrow(player, level, PlayerAttributes.manaDamage(player), PlayerAttributes.manaPenetration(player), PlayerAttributes.expUp(player), true, PlayerAttributes.manaPenetration0(player));
                newArrow.setSilent(true);
                newArrow.setNoGravity(true);

                newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.5F, 1.0f);
                WraqSceptre.adjustOrb(newArrow, player);
                level.addFreshEntity(newArrow);
                ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.WITCH);
                ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.WITCH);
            }
        }
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(Items.AIR)
                && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag data1 = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
            if (data1.contains(StringUtils.ManaCore.ManaCore)) {
                if (Utils.ManaCoreMap.isEmpty()) Utils.setManaCoreMap();
                player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Utils.ManaCoreMap.get(data1.getString(StringUtils.ManaCore.ManaCore))));
                data1.remove(StringUtils.ManaCore.ManaCore);
            }
        }
/*        HitResult result = player.pick(2,0,true);
        Compute.ParticleWITCH(result.getLocation().x, result.getLocation().y,result.getLocation().z,level,0.5);*/
        return super.use(level, player, interactionHand);
    }

    public static void hitPlayer(Level level, Player hurter, int Range, ServerPlayer player, double damage, int Kaze, int Effect, int Snow, int Lightning, int Gather) {
        List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(hurter.position(), 10 * Range, 10 * Range, 10 * Range));
        List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(hurter.position(), 10 * Range, 10 * Range, 10 * Range));
        if (Range == 0 && !playerList.contains(hurter)) playerList.add(hurter);
        for (Mob mob : mobList) {
            mob.hurt(mob.damageSources().playerAttack(player), (float) damage);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
            ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);

            if (Kaze > 0) {
                mob.setDeltaMovement(0, Effect, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 1, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);

            }
            if (Snow > 0) {
                mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * Effect, 99, false, false));
                BlockState blockState = Blocks.ICE.defaultBlockState();
                BlockPos blockPos = new BlockPos((int) mob.getX(), (int) (mob.getY() + 0.9), (int) mob.getZ());
                if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                    player.level().setBlockAndUpdate(blockPos, blockState);
                    player.level().destroyBlock(blockPos, false);
                }
            }
            if (Lightning > 0) {
                LightningBolt lightningBolt1 = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightningBolt1.setCause((ServerPlayer) player);
                lightningBolt1.setDamage(0);
                lightningBolt1.setVisualOnly(true);
                lightningBolt1.moveTo(mob.position());
                lightningBolt1.setSilent(true);
                level.addFreshEntity(lightningBolt1);
            }
            if (Gather > 0) {
                fun.wraq.common.util.struct.Gather gather = new Gather(20 * Effect, hurter.position());
                if (Utils.GatherMobMap.containsKey(gather)) {
                    Queue<Mob> mobQueue = Utils.GatherMobMap.get(gather);
                    mobQueue.add(mob);
                } else {
                    Queue<Mob> mobQueue = new LinkedList<>();
                    mobQueue.add(mob);
                    Utils.GatherMobMap.put(gather, mobQueue);
                }
            }
        }
        for (Player player1 : playerList) {
            if (player1 != player) {
                if (player1 != hurter) player1.hurt(player1.damageSources().playerAttack(player), (float) damage);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.4, 8, ParticleTypes.WITCH, 0);

                if (Kaze > 0) {
                    ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket = new ClientboundSetEntityMotionPacket(player1.getId(), new Vec3(0, Effect, 0));
                    List<ServerPlayer> playerList1 = player1.getServer().getPlayerList().getPlayers();
                    for (ServerPlayer player2 : playerList1) {
                        player2.connection.send(clientboundSetEntityMotionPacket);
                    }
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 1, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);

                }
                if (Snow > 0) {
                    player1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * Effect, 99, false, false));
                    BlockState blockState = Blocks.ICE.defaultBlockState();
                    BlockPos blockPos = new BlockPos((int) player1.getX(), (int) (player1.getY() + 0.9), (int) player1.getZ());
                    if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                        player.level().setBlockAndUpdate(blockPos, blockState);
                        player.level().destroyBlock(blockPos, false);
                    }
                }
                if (Lightning > 0) {
                    LightningBolt lightningBolt1 = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lightningBolt1.setCause((ServerPlayer) player);
                    lightningBolt1.setDamage(0);
                    lightningBolt1.setVisualOnly(true);
                    lightningBolt1.moveTo(player1.position());
                    lightningBolt1.setSilent(true);
                    level.addFreshEntity(lightningBolt1);
                }
                if (Gather > 0) {
                    Gather gather = new Gather(20 * Effect, hurter.position());
                    if (Utils.GatherPlayerMap.containsKey(gather)) {
                        Queue<Player> mobQueue = Utils.GatherPlayerMap.get(gather);
                        mobQueue.add(player1);
                    } else {
                        Queue<Player> mobQueue = new LinkedList<>();
                        mobQueue.add(player1);
                        Utils.GatherPlayerMap.put(gather, mobQueue);
                    }
                }
            }
        }
    }

    public static void hitMonster(Level level, Mob monster, int Range, ServerPlayer player, double damage, int Kaze, int Effect, int Snow, int Lightning, int Gather) {
        List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(monster.position(), 10 * Range, 10 * Range, 10 * Range));
        if (Range == 0 && !mobList.contains(monster)) mobList.add(monster);
        List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(monster.position(), 10 * Range, 10 * Range, 10 * Range));
        for (Mob mob : mobList) {
            if (mob != monster) {
                mob.hurt(mob.damageSources().playerAttack(player), (float) damage);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);
            }
            if (Kaze > 0) {
                mob.setDeltaMovement(0, Effect, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 1, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);

            }
            if (Snow > 0) {
                mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * Effect, 99, false, false));
                BlockState blockState = Blocks.ICE.defaultBlockState();
                BlockPos blockPos = new BlockPos((int) mob.getX(), (int) (mob.getY() + 0.9), (int) mob.getZ());

                if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                    player.level().setBlockAndUpdate(blockPos, blockState);
                    player.level().destroyBlock(blockPos, false);
                }
            }
            if (Lightning > 0) {
                LightningBolt lightningBolt1 = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightningBolt1.setCause((ServerPlayer) player);
                lightningBolt1.setDamage(0);
                lightningBolt1.setVisualOnly(true);
                lightningBolt1.moveTo(mob.position());
                lightningBolt1.setSilent(true);
                level.addFreshEntity(lightningBolt1);
            }
            if (Gather > 0) {
                fun.wraq.common.util.struct.Gather gather = new Gather(20 * Effect, monster.position());
                if (Utils.GatherMobMap.containsKey(gather)) {
                    Queue<Mob> mobQueue = Utils.GatherMobMap.get(gather);
                    mobQueue.add(mob);
                } else {
                    Queue<Mob> mobQueue = new LinkedList<>();
                    mobQueue.add(mob);
                    Utils.GatherMobMap.put(gather, mobQueue);
                }
            }
        }
        for (Player player1 : playerList) {
            if (player1 != player) {
                player1.hurt(player1.damageSources().playerAttack(player), (float) damage);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.4, 8, ParticleTypes.WITCH, 0);

                if (Kaze > 0) {
                    ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket = new ClientboundSetEntityMotionPacket(player1.getId(), new Vec3(0, Effect, 0));
                    List<ServerPlayer> playerList1 = player1.getServer().getPlayerList().getPlayers();
                    for (ServerPlayer player2 : playerList1) {
                        player2.connection.send(clientboundSetEntityMotionPacket);
                    }
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 1, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);

                }
                if (Snow > 0) {
                    player1.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * Effect, 99, false, false));
                    BlockState blockState = Blocks.ICE.defaultBlockState();
                    BlockPos blockPos = new BlockPos((int) player1.getX(), (int) (player1.getY() + 0.9), (int) player1.getZ());
                    if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                        player.level().setBlockAndUpdate(blockPos, blockState);
                        player.level().destroyBlock(blockPos, false);
                    }
                }
                if (Lightning > 0) {
                    LightningBolt lightningBolt1 = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lightningBolt1.setCause((ServerPlayer) player);
                    lightningBolt1.setDamage(0);
                    lightningBolt1.setVisualOnly(true);
                    lightningBolt1.moveTo(player1.position());
                    lightningBolt1.setSilent(true);
                    level.addFreshEntity(lightningBolt1);
                }
                if (Gather > 0) {
                    Gather gather = new Gather(20 * Effect, monster.position());
                    if (Utils.GatherPlayerMap.containsKey(gather)) {
                        Queue<Player> mobQueue = Utils.GatherPlayerMap.get(gather);
                        mobQueue.add(player1);
                    } else {
                        Queue<Player> mobQueue = new LinkedList<>();
                        mobQueue.add(player1);
                        Utils.GatherPlayerMap.put(gather, mobQueue);
                    }
                }
            }
        }
    }
}