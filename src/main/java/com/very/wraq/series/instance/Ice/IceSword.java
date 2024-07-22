package com.very.wraq.series.instance.Ice;

import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.ParticlePackets.SlowDownParticleS2CPacket;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.WraqSword;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class IceSword extends WraqSword implements ActiveItem {
    public IceSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 700d);
        Utils.defencePenetration0.put(this, 2400d);
        Utils.healthSteal.put(this, 0.3);
        Utils.critRate.put(this, 0.3);
        Utils.critDamage.put(this, 0.8);
        Utils.movementSpeedWithoutBattle.put(this, 0.5);
        Element.IceElementValue.put(this, 1.25);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfIce;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionActive(components, Component.literal("迸晶裂玉").withStyle(style));
        components.add(Component.literal(" 冻结").withStyle(style).
                append(Component.literal("周围目标1s").withStyle(ChatFormatting.WHITE)));
        Compute.CoolDownTimeDescription(components, 4);
        Compute.ManaCostDescription(components, 75);
        Compute.DescriptionPassive(components, Component.literal("凝结爆裂").withStyle(style));
        components.add(Component.literal(" 造成暴击后，为你提供基于攻击目标").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("")).
                append(Component.literal("的额外").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("")).
                append(Component.literal("持续2s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 每12点护甲值提供1%暴击伤害").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal(" 最多提供250%暴击伤害（在目标拥有3000护甲值达到最大值）").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfIce();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, 75)) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            Level level = player.level();
            Compute.playerItemCoolDown(player, this, 4);
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 15, 15, 15));
            mobList.forEach(mob -> {
                if (mob.distanceTo(player) <= 6) {
                    mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 99, false, false));
                    player.getServer().getPlayerList().getPlayers().forEach(TmpServerPlayer -> {
                        ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(), 40), TmpServerPlayer);
                    });
                    if (level.getBlockState(new BlockPos(mob.getBlockX(), mob.getBlockY() + 1, mob.getBlockZ())).is(Blocks.AIR)) {
                        level.setBlockAndUpdate(new BlockPos(mob.getBlockX(), mob.getBlockY() + 1, mob.getBlockZ()), Blocks.ICE.defaultBlockState());
                        level.destroyBlock(new BlockPos(mob.getBlockX(), mob.getBlockY() + 1, mob.getBlockZ()), false);
                    }
                }
            });
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1, 6, 80, ParticleTypes.SNOWFLAKE);
        }
    }
}
