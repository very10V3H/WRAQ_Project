package fun.wraq.common.equip;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.entities.entities.Civil.Civil;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.common.impl.onshoot.OnShootManaArrowCurios;
import fun.wraq.common.impl.onshoot.OnShootManaArrowPassiveEquip;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.projectiles.mana.NewArrow;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter2.evoker.EvokerSceptre;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public abstract class WraqSceptre extends WraqMainHandEquip {

    public WraqSceptre(Properties properties) {
        super(properties);
        Utils.sceptreTag.put(this, 1d);
    }

    @Override
    public Component getType() {
        return Component.literal("法杖").withStyle(CustomStyle.styleOfMana);
    }

    public void shootManaArrow(Player player, double rate, boolean mainShoot) {
        if (Compute.getManaSkillLevel(player.getPersistentData(), 10) > 0
                || Compute.playerManaCost(player,
                Utils.manaCost.getOrDefault(player.getMainHandItem().getItem(), 15d))) {
            AbstractArrow arrow = summonManaArrow(player, rate);
            if (arrow == null) return;
            if (arrow instanceof NewArrow newArrow) {
                newArrow.mainShoot = mainShoot;
            }
            if (arrow instanceof ManaArrow manaArrow) {
                manaArrow.mainShoot = mainShoot;
            }
            if (mainShoot) {
                OnShootManaArrowCurios.shoot(player);
                OnShootManaArrowPassiveEquip.shoot(player);
            }
            MySound.soundToNearPlayer(player, SoundEvents.EVOKER_CAST_SPELL);
        }
    }

    protected AbstractArrow summonManaArrow(Player player, double rate) {
        Level level = player.level();
        if (Compute.playerManaCost(player, EvokerSceptre.ManaCost)) {
            NewArrow newArrow = new NewArrow(player, level, PlayerAttributes.manaDamage(player) * rate,
                    PlayerAttributes.manaPenetration(player), PlayerAttributes.expUp(player),
                    false, PlayerAttributes.manaPenetration0(player));
            newArrow.setSilent(true);
            newArrow.setNoGravity(true);
            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
            WraqSceptre.adjustOrb(newArrow, player);
            level.addFreshEntity(newArrow);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.WITCH);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.WITCH);
            MySound.soundToNearPlayer(player, ModSounds.Mana.get());
            return newArrow;
        }
        return null;
    }

    public static void adjustOrb(AbstractArrow arrow, Player player) {
        if (false) {
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 80, 80, 80));
            mobList.removeIf(mob -> mob.distanceTo(player) > 30 || mob instanceof Civil);

            Mob NearestMob = null;
            double Distance = 80;
            for (Mob mob : mobList) {
                if (mob.distanceTo(player) < Distance) {
                    NearestMob = mob;
                    Distance = mob.distanceTo(player);
                }
            }

            arrow.setDeltaMovement(NearestMob.position().add(0, 1, 0).subtract(player.position().add(0, 1.5, 0)).normalize().scale(4.5));
            arrow.moveTo(player.pick(0.5, 0, false).getLocation());
            arrow.setCritArrow(true);
            arrow.setNoGravity(true);
            ProjectileUtil.rotateTowardsMovement(arrow, 1);

            ParticleProvider.LineParticle(player.level(), (int) NearestMob.distanceTo(player),
                    player.pick(0.5, 0, false).getLocation(), NearestMob.position().add(0, 1, 0), ParticleTypes.SNOWFLAKE);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(Items.AIR)
                && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
            if (data.contains(StringUtils.ManaCore.ManaCore)) {
                if (Utils.ManaCoreMap.isEmpty()) Utils.setManaCoreMap();
                player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Utils.ManaCoreMap.get(data.getString(StringUtils.ManaCore.ManaCore))));
                data.remove(StringUtils.ManaCore.ManaCore);
            }
        }
        return interactionHand.equals(InteractionHand.MAIN_HAND)
                ? InteractionResultHolder.success(player.getMainHandItem()) : InteractionResultHolder.success(player.getOffhandItem());
    }

    public static void getManaCoreAddition(ItemStack stack, List<Component> components) {
        CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);
        if (data.contains(StringUtils.ManaCore.ManaCore)) {
            Compute.ManaCoreDescription(components);
            String ManaCore = data.getString(StringUtils.ManaCore.ManaCore);
            String SeaCore = StringUtils.ManaCore.SeaCore;
            String BlackForestCore = StringUtils.ManaCore.BlackForestCore;
            String KazeCore = StringUtils.ManaCore.KazeCore;
            String SakuraCore = StringUtils.ManaCore.SakuraCore;
            if (ManaCore.equals(SeaCore)) {
                Compute.DescriptionPassive(components, Component.literal("灵魂救赎").withStyle(CustomStyle.styleOfSea));
                components.add(Component.literal("使法球附带:").withStyle(ChatFormatting.WHITE));
                components.add(Component.literal("基于目标已损失生命值造成至多0.5倍的").withStyle(CustomStyle.styleOfSea).
                        append(Component.literal("等级强度").withStyle(CustomStyle.styleOfLucky)).
                        append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
                components.add(Component.literal("倍率随目标已损失生命值线性增长").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
            } else if (ManaCore.equals(BlackForestCore)) {
                Compute.DescriptionPassive(components, Component.literal("灵魂收割").withStyle(CustomStyle.styleOfHusk));
                components.add(Component.literal("使法球附带:").withStyle(ChatFormatting.WHITE));
                components.add(Component.literal("基于目标当前生命值造成至多0.5倍的").withStyle(CustomStyle.styleOfHusk).
                        append(Component.literal("等级强度").withStyle(CustomStyle.styleOfLucky)).
                        append(Component.literal("额外魔法伤害").withStyle(CustomStyle.styleOfHusk)));
                components.add(Component.literal("倍率随目标当前生命值线性增长").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));

            } else if (ManaCore.equals(KazeCore)) {
                Compute.DescriptionPassive(components, Component.literal("狂风晶核").withStyle(CustomStyle.styleOfSea));
                components.add(Component.literal("-获得").withStyle(ChatFormatting.WHITE).
                        append(ComponentUtils.AttributeDescription.movementSpeedWithoutBattle("30%")));
                components.add(Component.literal("-基于你的").withStyle(ChatFormatting.WHITE).
                        append(ComponentUtils.AttributeDescription.movementSpeedWithoutBattle("")).
                        append(Component.literal("提供").withStyle(ChatFormatting.WHITE)).
                        append(ComponentUtils.AttributeDescription.manaPenetration("")));
                components.add(Component.literal("-每").withStyle(ChatFormatting.WHITE).
                        append(ComponentUtils.AttributeDescription.exMovementSpeed("1%")).
                        append(Component.literal("提供").withStyle(ChatFormatting.WHITE)).
                        append(ComponentUtils.AttributeDescription.manaPenetration("1")));
            } else if (ManaCore.equals(SakuraCore)) {
                Compute.DescriptionPassive(components, Component.literal("樱妖晶核").withStyle(CustomStyle.styleOfDemon));
                components.add(Component.literal("第一枚法球造成").withStyle(ChatFormatting.WHITE).
                        append(ComponentUtils.AttributeDescription.manaDamage("100%")).
                        append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
                components.add(Component.literal("第二枚法球回复").withStyle(ChatFormatting.WHITE).
                        append(ComponentUtils.AttributeDescription.manaDamage("1.25%")).
                        append(ComponentUtils.AttributeDescription.health("")));
            }
        } else {
            components.add(Component.literal(" 「尚未加载魔核」").withStyle(CustomStyle.styleOfMana));
        }
    }
}
