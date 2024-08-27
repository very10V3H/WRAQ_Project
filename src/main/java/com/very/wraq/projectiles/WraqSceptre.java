package com.very.wraq.projectiles;

import com.very.wraq.blocks.blocks.ForgeRecipe;
import com.very.wraq.common.BasicAttributeDescription;
import com.very.wraq.common.Compute;
import com.very.wraq.common.MySound;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.registry.ItemTier;
import com.very.wraq.common.registry.ModSounds;
import com.very.wraq.entities.entities.Civil.Civil;
import com.very.wraq.events.mob.loot.RandomLootEquip;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.mana.NewArrow;
import com.very.wraq.series.instance.mixture.WraqMixture;
import com.very.wraq.series.overworld.chapter2.evoker.EvokerSceptre;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class WraqSceptre extends SwordItem {

    public WraqSceptre(Properties p_43272_) {
        super(ItemTier.VMaterial, 2, 0, p_43272_);
        Utils.mainHandTag.put(this, 1d);
        Utils.sceptreTag.put(this, 1d);
        Utils.weaponList.add(this);
        if (this instanceof ForgeItem forgeItem) {
            ForgeRecipe.forgeDrawRecipe.put(this, forgeItem.forgeRecipe());
        }
    }

    public abstract Style getMainStyle();

    public abstract List<Component> getAdditionalComponents(ItemStack itemStack);

    public abstract Component getSuffix();

    public Component oneLineDescription() {
        return null;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        Style style = getMainStyle();
        Compute.forgingHoverName(stack);
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        if (this instanceof RandomLootEquip randomLootEquip) {
            if (randomLootEquip.levelRequire() != 0) {
                int levelRequirement = randomLootEquip.levelRequire();
                if (levelRequirement != 0) {
                    components.add(Component.literal(" 等级需求: ").withStyle(ChatFormatting.AQUA).
                            append(Component.literal("Lv." + levelRequirement).withStyle(Utils.levelStyleList.get(levelRequirement / 25))));
                }
            }
        }
        if (!getAdditionalComponents(stack).isEmpty()) {
            Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            Compute.DescriptionOfAddition(components);
            components.addAll(getAdditionalComponents(stack));
        }
        Compute.ManaCoreAddition(stack, components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        if (oneLineDescription() != null) {
            components.add(oneLineDescription());
        }
        components.add(getSuffix());
        super.appendHoverText(stack, level, components, tooltipFlag);
    }

    public void shootManaArrow(Player player, double rate) {
        if (Compute.ManaSkillLevelGet(player.getPersistentData(), 10) > 0
                || Compute.playerManaCost(player,
                Utils.manaCost.getOrDefault(player.getMainHandItem().getItem(), 15d))) {
            summonManaArrow(player, rate);
            if (Math.abs(rate - 1) < 0.0001) {
                WraqMixture.onShoot(player);
            }
            MySound.SoundToAll(player, SoundEvents.EVOKER_CAST_SPELL);
        }
    }

    public void summonManaArrow(Player player, double rate) {
        Level level = player.level();
        CompoundTag data = player.getPersistentData();
        if (Compute.ManaSkillLevelGet(data, 10) > 0 || Compute.playerManaCost(player, EvokerSceptre.ManaCost)) {
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
            MySound.SoundToAll(player, ModSounds.Mana.get());
        }
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
    public boolean hurtEnemy(ItemStack p_40994_, LivingEntity p_40995_, LivingEntity p_40996_) {
        p_40994_.hurtAndBreak(0, p_40996_, (p_41007_) -> {
            p_41007_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0d) {
            p_40998_.hurtAndBreak(0, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        /*Compute.ManaAttack(player);*/
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(Items.AIR)
                && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
            if (data.contains(StringUtils.ManaCore.ManaCore)) {
                if (Utils.ManaCoreMap.isEmpty()) Utils.setManaCoreMap();
                player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Utils.ManaCoreMap.get(data.getString(StringUtils.ManaCore.ManaCore))));
                data.remove(StringUtils.ManaCore.ManaCore);
            }
        }
        return super.use(level, player, interactionHand);
    }
}
