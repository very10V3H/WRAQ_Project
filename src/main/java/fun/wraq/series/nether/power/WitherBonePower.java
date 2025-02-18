package fun.wraq.series.nether.power;

import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.ParticlePackets.EffectParticle.DamageDecreaseParticleS2CPacket;
import fun.wraq.networking.misc.ParticlePackets.SlowDownParticleS2CPacket;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.PowerLogic;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

import static fun.wraq.common.Compute.detectPlayerPickMob;
import static fun.wraq.common.Compute.playerItemCoolDown;

public class WitherBonePower extends WraqPower {

    public WitherBonePower(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public Component getActiveName() {
        return null;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal(" 对").withStyle(ChatFormatting.WHITE).
                append(Component.literal("指针").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("周围所有单位造成").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamageValue("300%")));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.FireElement("1 + 100%")));
        components.add(Component.literal("·[对魔]").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("使周围怪物造成伤害降低20%，持续5s。").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public int getCoolDownSecond() {
        return 10;
    }

    @Override
    public double getManaCost() {
        return 360;
    }

    @Override
    public Component getSuffix() {
        return null;
    }

    @Override
    public void release(Player player) {
        playerItemCoolDown(player, this, 10);
        Level level = player.level();
        Vec3 TargetPos = player.pick(15, 0, false).getLocation();
        if (detectPlayerPickMob(player) != null) TargetPos = detectPlayerPickMob(player).position();
        List<Mob> monsterList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(TargetPos, 20, 20, 20));
        for (Mob mob : monsterList) {
            if (mob.getPosition(0).distanceTo(TargetPos) < 6) {
                Damage.causeRateApDamageWithElement(player, mob, 3, true,
                        Element.fire, ElementValue.getElementValueJudgeByType(player, Element.fire) + 1);
                PowerLogic.PlayerPowerEffectToMob(player, mob);
                mob.getPersistentData().putInt("witherBonePower", 100);
                player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                        ModNetworking.sendToClient(new DamageDecreaseParticleS2CPacket(mob.getId(), 100), serverPlayer));

                Utils.witherBonePowerCCMonster.add(mob);
                player.getServer().getPlayerList().getPlayers().forEach(serverPlayer ->
                        ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(), 100), serverPlayer));

                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 1, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.25, 0.4, 8, ParticleTypes.WITCH, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 0.4, 8, ParticleTypes.WITCH, 0);
            }
        }
        ParticleProvider.VerticleCircleParticle(TargetPos, (ServerLevel) level, 1, 6, 100, ParticleTypes.WITCH);
        ParticleProvider.VerticleCircleParticle(TargetPos, (ServerLevel) level, 1.5, 6, 100, ParticleTypes.WITCH);
        MySound.soundToNearPlayer(player, ModSounds.Nether_Power.get());
    }
}
