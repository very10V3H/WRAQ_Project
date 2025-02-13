package fun.wraq.series.overworld.chapter1.volcano;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.PowerLogic;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

import static fun.wraq.common.Compute.detectPlayerPickMob;
import static fun.wraq.common.Compute.playerItemCoolDown;

public class VolcanoPower extends WraqPower {

    private final int tier;

    public VolcanoPower(Properties p_41383_, int tier) {
        super(p_41383_);
        this.tier = tier;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public Component getActiveName() {
        return Component.literal("爆裂之焰").withStyle(CustomStyle.styleOfVolcano);
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal(" 使").withStyle(ChatFormatting.WHITE).
                append(Component.literal("指针").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("周围所有敌人爆裂，在每个敌人位置处产生小范围").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamageValue(String.format("%.0f", effect[tier] * 100) + "%")));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.FireElement("1 + 100%")));
        return components;
    }

    @Override
    public int getCoolDownSecond() {
        return CoolDownTime[tier];
    }

    @Override
    public double getManaCost() {
        return ManaCost[tier];
    }

    @Override
    public Component getSuffix() {
        return null;
    }

    @Override
    public void release(Player player) {
        List.of(ModItems.VolcanoPower.get(), ModItems.VolcanoPower1.get(),
                ModItems.VolcanoPower2.get(), ModItems.VolcanoPower3.get()).
                forEach(item -> {
                    playerItemCoolDown(player, item, VolcanoPower.CoolDownTime[tier] - SuitCount.getObsiManaESuitCount(player) * 0.75);
                });
        Level dimension = player.level();
        double effect = VolcanoPower.effect[tier];
        Vec3 targetPos = player.pick(15, 0, false).getLocation();
        if (detectPlayerPickMob(player) != null) targetPos = detectPlayerPickMob(player).position();
        List<Mob> mobList = dimension.getEntitiesOfClass(Mob.class,
                AABB.ofSize(targetPos, 20, 20, 20));
        List<ServerPlayer> playerList = dimension.getServer().getPlayerList().getPlayers();
        Vec3 finalTargetPos = targetPos;
        mobList.forEach(mob -> {
            Vec3 PosVec = mob.position().subtract(finalTargetPos);
            if (PosVec.length() <= 6) {
                Compute.IgniteMob(player, mob, 40);
                List<Mob> mobList1 = dimension.getEntitiesOfClass(Mob.class,
                        AABB.ofSize(mob.position(), 10, 10, 10));
                mobList1.forEach(mob1 -> {
                    if (mob1.position().subtract(mob.position()).length() <= 2) {
                        Damage.causeRateApDamageWithElement(player, mob1, effect, true,
                                Element.fire, ElementValue.ElementValueJudgeByType(player, Element.fire) + 1);
                        PowerLogic.PlayerPowerEffectToMob(player, mob);
                        ClientboundLevelParticlesPacket clientboundLevelParticlesPacket =
                                new ClientboundLevelParticlesPacket(ParticleTypes.EXPLOSION_EMITTER, true,
                                        mob1.getX(), mob1.getY(), mob1.getZ(),
                                        0, 0, 0, 0, 0);
                        playerList.forEach(serverPlayer -> serverPlayer.connection.send(clientboundLevelParticlesPacket));
                    }
                });
            }
        });
        MySound.soundToNearPlayer(player, SoundEvents.DRAGON_FIREBALL_EXPLODE);
        ParticleProvider.createSpaceRangeParticle((ServerLevel) dimension, targetPos, 6, 10,
                ParticleTypes.EXPLOSION);
    }

    public static int[] ManaCost = {
            100, 115, 130, 150
    };

    public static int[] CoolDownTime = {
            10, 9, 8, 7
    };

    public static double[] effect = {
            1, 1.15, 1.3, 1.5
    };
}
