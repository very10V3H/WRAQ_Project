package com.very.wraq.series.end.curios;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Struct.Drops;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class EndCrystal extends Item {

    public EndCrystal(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal(" 凝聚终界能量的水晶，具有扭曲时间与空间的强大能量").withStyle(CustomStyle.styleOfEnd));
/*        components.add(Component.literal(" - 于终界寂域中心使用，召唤").withStyle(ChatFormatting.WHITE).
                append(Component.literal(" 终界征讨者遗骸").withStyle(CustomStyle.styleOfEnd)));*/
        Compute.SuffixOfMainStoryIV(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
/*        if (!level.isClientSide) {
            ServerLevel end = level.getServer().getLevel(Level.END);
            ServerPlayer serverPlayer = (ServerPlayer) player;
            if (serverPlayer.level().equals(end) && serverPlayer.position().distanceTo(new Vec3(24, 87, -205)) < 6) {
                Compute.playerItemUseWithRecord(player);
                Vec3 centerPos = new Vec3(24.5, 88, -204.5);
                Summon(level, centerPos);
                List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(centerPos, 50, 50, 50));
                playerList.forEach(player1 -> {
                    ServerPlayer serverPlayer1 = (ServerPlayer) player1;
                    ModNetworking.sendToClient(new SoundsS2CPacket(8), serverPlayer1);
                    Compute.formatMSGSend(player, Component.literal("终界").withStyle(CustomStyle.styleOfEnd),
                            Component.literal("").withStyle(ChatFormatting.WHITE).
                                    append(serverPlayer.getDisplayName()).
                                    append(Component.literal(" 召唤了 ").withStyle(ChatFormatting.WHITE)).
                                    append(stray != null ? stray.getDisplayName() : Component.literal("终界征讨者遗骸").withStyle(CustomStyle.styleOfEnd)));
                });
                ParticleProvider.SpaceRangeParticle(end, centerPos, 6, 100, ParticleTypes.EXPLOSION_EMITTER);

            } else {
                Compute.formatMSGSend(player, Component.literal("终界").withStyle(CustomStyle.styleOfEnd),
                        Component.literal("此处似乎不能进行召唤").withStyle(ChatFormatting.WHITE));
            }
        }*/
        return super.use(level, player, interactionHand);
    }

    public static Stray stray;

    public static void Summon(Level level, Vec3 pos) {
        stray = new Stray(EntityType.STRAY, level);

        Compute.SetMobCustomName(stray, ModItems.MobArmorEndStrayHelmet.get(),
                Component.literal("终界征讨者遗骸").withStyle(CustomStyle.styleOfEnd));

        stray.setItemInHand(InteractionHand.MAIN_HAND, ModItems.PurpleIronSceptre.get().getDefaultInstance());
        stray.setItemInHand(InteractionHand.OFF_HAND, ModItems.MoonShield.get().getDefaultInstance());
        stray.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1 * Math.pow(10, 7));
        stray.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8000);
        stray.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5);
        stray.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorEndStrayHelmet.get().getDefaultInstance());
        stray.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorPurpleIronKnightChest.get().getDefaultInstance());
        stray.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorPurpleIronKnightLeggings.get().getDefaultInstance());
        stray.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorPurpleIronKnightBoots.get().getDefaultInstance());

        stray.setHealth(stray.getMaxHealth());
        stray.moveTo(pos);
        level.addFreshEntity(stray);

        SummonLighting(level, new Vec3(18.5, 88, -198.5));
        SummonLighting(level, new Vec3(30.5, 88, -198.5));
        SummonLighting(level, new Vec3(30.5, 88, -210.5));
        SummonLighting(level, new Vec3(18.5, 88, -210.5));
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static void Drop(Player player, Mob monster) throws IOException {
        Random r = new Random();
        List<Player> playerList = monster.level().getEntitiesOfClass(Player.class, AABB.ofSize(monster.position(), 50, 50, 50));
        ItemStack itemStack = null;

        if (r.nextDouble() < 0.12) {
            if (r.nextDouble() < 0.5) itemStack = ModItems.EndCuriosForgeDraw.get().getDefaultInstance();
            else itemStack = ModItems.EndCurios1ForgeDraw.get().getDefaultInstance();
        }

        playerList.forEach(player1 -> {
            Compute.formatMSGSend(player1, Component.literal("终界").withStyle(CustomStyle.styleOfEnd),
                    Component.literal("").withStyle(ChatFormatting.WHITE).
                            append(player.getDisplayName()).
                            append(Component.literal(" 击杀了 ").withStyle(ChatFormatting.WHITE)).
                            append(monster.getDisplayName()));
        });
        if (itemStack != null) {
            Compute.formatBroad(player.level(), Component.literal("终界").withStyle(CustomStyle.styleOfEnd),
                    Component.literal("").withStyle(ChatFormatting.WHITE).
                            append(player.getDisplayName()).
                            append(Component.literal(" 通过击杀 ").withStyle(ChatFormatting.WHITE)).
                            append(monster.getDisplayName()).
                            append(Component.literal(" 获得了 ").withStyle(ChatFormatting.WHITE)).
                            append(itemStack.getDisplayName()));
            Compute.itemStackGive(player, itemStack);
        }
        Compute.itemStackGive(player, new ItemStack(ModItems.ShulkerSoul.get(), 16));
        Compute.itemStackGive(player, new ItemStack(ModItems.EnderMiteSoul.get(), 16));
        Drops.KillCount(player.getPersistentData(), StringUtils.MobName.EndStray);
    }

    public static void SummonLighting(Level level, Vec3 vec3) {
        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
        lightningBolt.setVisualOnly(true);
        lightningBolt.moveTo(vec3);
        level.addFreshEntity(lightningBolt);
    }

}
