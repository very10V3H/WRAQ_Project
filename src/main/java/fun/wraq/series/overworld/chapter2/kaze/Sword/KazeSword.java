package fun.wraq.series.overworld.chapter2.kaze.Sword;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class KazeSword extends WraqSword implements ActiveItem {

    private final int tier;

    public KazeSword(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.attackDamage.put(this, new double[]{80, 85, 90, 100, 200}[tier]);
        Utils.defencePenetration0.put(this, new double[]{9, 10, 11, 12, 15}[tier]);
        Utils.healthSteal.put(this, 0.05);
        Utils.critRate.put(this, 0.3);
        Utils.critDamage.put(this, new double[]{0.4, 0.45, 0.55, 0.65, 0.75}[tier]);
        Element.WindElementValue.put(this, new double[]{0.2, 0.4, 0.6, 0.8, 1}[tier]);
        Utils.levelRequire.put(this, 80);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfKaze;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionActive(components, Component.literal("1.飓风:").withStyle(CustomStyle.styleOfKaze));
        components.add(Component.literal("若周围无在空中的单位，").withStyle(ChatFormatting.WHITE).
                append(Component.literal("击飞").withStyle(CustomStyle.styleOfKaze)).
                append(Component.literal("周围的所有单位。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("冷却时间: 0.8s").withStyle(ChatFormatting.WHITE).
                append(Component.literal(" 不可被冷却缩减减少").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal("2.狂风绝息:").withStyle(CustomStyle.styleOfKaze));
        components.add(Component.literal("若周围有单位位于空中，则对位于空中的所有单位造成").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.attackDamage(new String[]{"200%", "300%", "400%", "500%", "1000%"}[tier])).
                append(Component.literal("的物理伤害，并将其强制牵引至地面").withStyle(ChatFormatting.WHITE)));
        ComponentUtils.coolDownTimeDescription(components, 3);
        ComponentUtils.manaCostDescription(components, 45);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public void active(Player player) {
        CompoundTag data = player.getPersistentData();
        int rate = new int[]{2, 3, 4, 5, 10}[tier];
        Level level = player.level();
        List<Mob> MobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 15, 15, 15));
        List<Player> PlayerList1 = level.getEntitiesOfClass(Player.class, AABB.ofSize(player.position(), 15, 15, 15));
        List<ServerPlayer> playerList = level.getServer().getPlayerList().getPlayers();
        boolean InSkyFlag = false;
        for (Mob mob : MobList1) {
            if (mob.getPosition(0).distanceTo(player.getPosition(0)) < 8) {
                if (Compute.onSky(mob) && !mob.isInWater()) {
                    InSkyFlag = true;
                    break;
                }
            }
        }
        if (!InSkyFlag) {
            for (Player player1 : PlayerList1) {
                if (player1.getPosition(0).distanceTo(player.getPosition(0)) < 8) {
                    if (Compute.onSky(player1) && !player1.isInWater() && player1 != player) {
                        InSkyFlag = true;
                        break;
                    }
                }
            }
        }

        Vec3 FaceVec = player.pick(1, 0, false).getLocation();
        if (InSkyFlag) {
            ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(
                    ParticleTypes.SWEEP_ATTACK, true, FaceVec.x, FaceVec.y, FaceVec.z,
                    0, 0, 0, 0, 0
            );
            ServerPlayer serverPlayer1 = (ServerPlayer) player;
            serverPlayer1.connection.send(clientboundLevelParticlesPacket);
            ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.PLAYER_ATTACK_KNOCKBACK),
                    SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 0.4f, 0.4f, 0);
            serverPlayer1.connection.send(clientboundSoundPacket);
            double MobDamageCount = 0;
            for (Mob mob : MobList1) {
                if (mob.getPosition(0).distanceTo(player.getPosition(0)) < 8) {
                    if (Compute.onSky(mob) && !mob.isInWater()) {
                        mob.setDeltaMovement(0, -1, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 1, 16, ParticleTypes.ENCHANTED_HIT, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.75, 16, ParticleTypes.ENCHANTED_HIT, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.75, 16, ParticleTypes.ENCHANTED_HIT, 0);
                        MobDamageCount += Damage.causeAttackDamageToMonster_RateAdDamage(player, mob, rate);
                    }
                }
            }
            double PlayerDamageCount = 0;
            for (Player player1 : PlayerList1) {
                if (player1.getPosition(0).distanceTo(player.getPosition(0)) < 8) {
                    if (Compute.onSky(player1) && !player1.isInWater() && player1 != player) {
                        ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket = new ClientboundSetEntityMotionPacket(player1.getId(), new Vec3(0, 1, 0));
                        for (ServerPlayer serverPlayer : playerList) {
                            serverPlayer.connection.send(clientboundSetEntityMotionPacket);
                        }
                        ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 1, 16, ParticleTypes.ENCHANTED_HIT, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.75, 16, ParticleTypes.ENCHANTED_HIT, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.75, 16, ParticleTypes.ENCHANTED_HIT, 0);
                        PlayerDamageCount += Damage.causeAttackDamageToPlayer_RateAdDamage(player, player1, rate);
                    }
                }
            }
            player.getCooldowns().addCooldown(ModItems.KazeSword0.get(), (int) (60 * (1 - PlayerAttributes.coolDownDecrease(player))));
            player.getCooldowns().addCooldown(ModItems.KazeSword1.get(), (int) (60 * (1 - PlayerAttributes.coolDownDecrease(player))));
            player.getCooldowns().addCooldown(ModItems.KazeSword2.get(), (int) (60 * (1 - PlayerAttributes.coolDownDecrease(player))));
            player.getCooldowns().addCooldown(ModItems.KazeSword3.get(), (int) (60 * (1 - PlayerAttributes.coolDownDecrease(player))));
            player.getCooldowns().addCooldown(ModItems.KazeSword4.get(), (int) (60 * (1 - PlayerAttributes.coolDownDecrease(player))));

            if (MobDamageCount > 0) {
                if (!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight")))
                    Compute.sendFormatMSG(player, Component.literal("战斗").withStyle(ChatFormatting.RED),
                            Component.literal("狂风绝息").withStyle(CustomStyle.styleOfKaze).
                                    append(Component.literal("对怪物造成了").withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal(String.format("%.2f", MobDamageCount)).withStyle(CustomStyle.styleOfKaze)).
                                    append(Component.literal("伤害值。").withStyle(ChatFormatting.WHITE)));
            }
            if (PlayerDamageCount > 0) {
                if (!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight")))
                    Compute.sendFormatMSG(player, Component.literal("战斗").withStyle(ChatFormatting.RED),
                            Component.literal("狂风绝息").withStyle(CustomStyle.styleOfKaze).
                                    append(Component.literal("对玩家造成了").withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal(String.format("%.2f", PlayerDamageCount)).withStyle(CustomStyle.styleOfKaze)).
                                    append(Component.literal("伤害值。").withStyle(ChatFormatting.WHITE)));
            }
        } else {
            ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(
                    ParticleTypes.SWEEP_ATTACK,
                    true,
                    FaceVec.x,
                    FaceVec.y,
                    FaceVec.z,
                    0, 0, 0, 0, 0
            );
            ServerPlayer serverPlayer1 = (ServerPlayer) player;
            serverPlayer1.connection.send(clientboundLevelParticlesPacket);
            ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.PLAYER_ATTACK_SWEEP),
                    SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 0.4f, 0.4f, 0);
            serverPlayer1.connection.send(clientboundSoundPacket);
            for (Mob mob : MobList1) {
                if (mob.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                    mob.setDeltaMovement(0, 1, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 1, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                    ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                }
            }
            for (Player player1 : PlayerList1) {
                if (player1.getPosition(0).distanceTo(player.getPosition(0)) < 6) {
                    if (player1 != player) {
                        ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket = new ClientboundSetEntityMotionPacket(player1.getId(), new Vec3(0, 1, 0));
                        for (ServerPlayer serverPlayer : playerList) {
                            serverPlayer.connection.send(clientboundSetEntityMotionPacket);
                        }
                        ParticleProvider.EntityEffectVerticleCircleParticle(player1, 1, 1, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0.5, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                        ParticleProvider.EntityEffectVerticleCircleParticle(player1, 0, 0.75, 16, ParticleTypes.TOTEM_OF_UNDYING, 0);
                    }
                }
            }
            player.getCooldowns().addCooldown(ModItems.KazeSword0.get(), 8);
            player.getCooldowns().addCooldown(ModItems.KazeSword1.get(), 8);
            player.getCooldowns().addCooldown(ModItems.KazeSword2.get(), 8);
            player.getCooldowns().addCooldown(ModItems.KazeSword3.get(), 8);
            player.getCooldowns().addCooldown(ModItems.KazeSword4.get(), 8);
        }
    }

    @Override
    public double manaCost(Player player) {
        return 45;
    }
}
