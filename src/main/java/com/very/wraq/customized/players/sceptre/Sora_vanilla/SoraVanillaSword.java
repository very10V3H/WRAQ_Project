
package com.very.wraq.customized.players.sceptre.Sora_vanilla;

import com.very.wraq.customized.Customize;
import com.very.wraq.entities.entities.SoraSword.SoraSwordAir;
import com.very.wraq.entities.entities.SoraSword.SoraSwordRender;
import com.very.wraq.process.Particle.ParticleProvider;
import com.very.wraq.render.Particles.ModParticles;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.ModEntityType;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SoraVanillaSword extends SwordItem implements GeoItem {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public SoraVanillaSword(Tier tier, Properties properties) {
        super(tier,2,0,properties);
        Utils.ManaDamage.put(this, Customize.ManaDamage);
        Utils.ManaCost.put(this, 15d);
        Utils.ManaPenetration0.put(this, Customize.ManaPenetration0);
        Utils.ManaRecover.put(this, Customize.ManaRecover);
        Utils.MovementSpeed.put(this, Customize.MovementSpeed);
        Utils.MainHandTag.put(this,1d);
        Utils.CustomizedList.add(this);
        Utils.SceptreTag.put(this,1d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        ChatFormatting style = ChatFormatting.RED;
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("开膛手杰克").withStyle(style));
        components.add(Component.literal(" 普通法球攻击").withStyle(CustomStyle.styleOfMana).
                append(Component.literal("将变为").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("罗德里格斯新阴流").withStyle(style)).
                append(Component.literal("，造成").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamageValue("200%")).
                append(Component.literal("，视为").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("法术伤害").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" 低于").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("30%")).
                append(Component.literal("时，开启").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("开膛手形态").withStyle(style)).
                append(Component.literal("，获得:").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 1. ").withStyle(ChatFormatting.RED).
                append(Compute.AttributeDescription.ManaDamage("总30%")));
        components.add(Component.literal(" 2. ").withStyle(ChatFormatting.RED).
                append(Compute.AttributeDescription.HealthRecover("-100")));
        components.add(Component.literal(" 3. ").withStyle(ChatFormatting.RED).
                append(Compute.AttributeDescription.ManaHealSteal("-100%")));
        components.add(Component.literal(" 4. ").withStyle(ChatFormatting.RED).
                append(Compute.AttributeDescription.HealAmplification("-100%")));
        components.add(Component.literal(" 5. ").withStyle(ChatFormatting.RED).
                append(Component.literal("使").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("法术伤害").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("附带").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamageValue("100%")));
        components.add(Component.literal(" 6. ").withStyle(ChatFormatting.RED).
                append(Component.literal("将").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("0.5%造成伤害").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("转换为").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("护盾值").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal(" 7. ").withStyle(ChatFormatting.RED).
                append(Component.literal("罗德里格斯新阴流").withStyle(style)).
                append(Component.literal("造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("双倍伤害").withStyle(style)));
        components.add(Component.literal(" 8. ").withStyle(style).
                append(Component.literal("允许释放:").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("子弹居合").withStyle(style)));
        Compute.DescriptionActive(components,Component.literal("子弹居合").withStyle(style));
        components.add(Component.literal(" 在短暂蓄力后，向前位移，进行小范围").withStyle(ChatFormatting.WHITE).
                append(Component.literal("居合攻击").withStyle(ChatFormatting.RED)).
                append(Component.literal("，造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("20倍").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("魔法伤害").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("，并将").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("护盾值").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("转化为").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("100%魔法伤害").withStyle(CustomStyle.styleOfMana)));
        Compute.ManaCoreAddition(stack,components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 【序列号977-AZQEE】").withStyle(ChatFormatting.RED));
        components.add(Component.literal(" 崇高的敬意化作一把高频村雨-Jetstream Sam，授予对维瑞阿契做出了杰出贡献的 - SoraVanilla").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.ManaAttack(player,12);
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(Items.AIR)
                && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
            if (data.contains(StringUtils.ManaCore.ManaCore)) {
                if (Utils.ManaCoreMap.isEmpty()) Utils.setManaCoreMap();
                player.setItemInHand(InteractionHand.OFF_HAND,new ItemStack(Utils.ManaCoreMap.get(data.getString(StringUtils.ManaCore.ManaCore))));
                data.remove(StringUtils.ManaCore.ManaCore);
            }
        }
        return super.use(level, player, interactionHand);
    }

    private PlayState predicate(AnimationState animationState) {
        animationState.getController().setAnimation(RawAnimation.begin().then("animation.model.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            private SoraSwordRender renderer;
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(this.renderer == null) {
                    renderer = new SoraSwordRender();
                }
                return this.renderer;
            }

        });
    }

    public static int activeTick = -1;

    public static boolean IsPlayer(Player player) {
        return player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.SoraSword.get());
    }

    public static boolean HealthUnderThreshold(Player player) {
        return player.getHealth() / player.getMaxHealth() <= 0.3;
    }

    public static double ManaDamageUp(Player player) {
        if (!IsPlayer(player)) return 1;
        return HealthUnderThreshold(player) ? 1.3 : 1;
    }

    public static double HealthRecover(Player player) {
        if (!IsPlayer(player)) return 0;
        return HealthUnderThreshold(player) ? -100 : 0;
    }

    public static double ManaHealthSteal(Player player) {
        if (!IsPlayer(player)) return 1;
        return HealthUnderThreshold(player) ? 0 : 1;
    }

    public static double HealEffect(Player player) {
        if (!IsPlayer(player)) return 0;
        return HealthUnderThreshold(player) ? -1 : 0;
    }

    public static void ExAttackDamage(Player player, Mob mob, double damage) {
        if (!IsPlayer(player) || !HealthUnderThreshold(player)) return;
        Compute.Damage.AttackDamageToMonster_AdDamage(player,mob,damage);
    }

    public static void ShieldProvider(Player player, double damage) {
        if (!IsPlayer(player) || !HealthUnderThreshold(player)) return;
        Compute.PlayerShieldProvider(player,50,damage * 0.005);
    }

    public static Map<Integer, List<Integer>> causeDamageList = new HashMap<>();

    public static void SwordAirHitEntity(List<Entity> list, SoraSwordAir swordAir) {
        if (swordAir.player == null) return;
        list.forEach(entity1 -> {
            if (entity1 instanceof Mob mob && !swordAir.player.level().isClientSide) {
                if (!causeDamageList.containsKey(swordAir.getId())) causeDamageList.put(swordAir.getId(), new ArrayList<>());
                List<Integer> causedList = causeDamageList.get(swordAir.getId());
                if (!causedList.contains(mob.getId())) {
                    causedList.add(mob.getId());
                    Player player = swordAir.player;
                    Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, HealthUnderThreshold(player) ? 4 : 2, true);

                }
            }
        });
    }

    public static int activeCoolDownTime = 0;

    public static void Active(Player player) {
        if (!IsPlayer(player) || !HealthUnderThreshold(player)) return;
        if (player.getServer().getTickCount() > activeCoolDownTime) {
            activeTick = 40;
            int coolDownTime = (int) (800 * (1 - Compute.PlayerAttributes.PlayerCoolDownDecrease(player)));
            activeCoolDownTime = player.getServer().getTickCount() + coolDownTime;
            Compute.CoolDownTimeSend(player,ModItems.SoraSwordItem.get().getDefaultInstance(),coolDownTime);
        }
    }

    public static void Tick(Player player) {
        if (!IsPlayer(player)) return;
        if (HealthUnderThreshold(player)) {
            ParticleProvider.SpaceRangeParticle((ServerLevel) player.level(), player.position(), 1, 30, ModParticles.SoraSwordParticle.get());
        }
        if (activeTick == 40) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,20,3,false,false));
        }
        if (activeTick == 20) {
            Vec3 vec3 = player.pick(1,0,false).getLocation();
            Vec3 vec = vec3.subtract(player.getEyePosition()).normalize();
            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                    new ClientboundSetEntityMotionPacket(player.getId(), vec.scale(3));
            ((ServerPlayer) player).connection.send(clientboundSetEntityMotionPacket);
        }
        if (activeTick == 0) {
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 15, 15, 15));
            mobList.removeIf(mob -> mob.distanceTo(player) > 6);
            double shieldValue = Compute.PlayerShieldCompute(player);
            double damage = Compute.XpStrengthAPDamage(player,20) + shieldValue;
            Compute.FormatMSGSend(player, Component.literal("高频村雨").withStyle(ChatFormatting.RED), Component.literal("子弹居合").withStyle(ChatFormatting.RED).
                    append(Component.literal("造成了:").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal(String.format(" %.0f",damage * mobList.size())).withStyle(ChatFormatting.RED)).
                    append(Component.literal(" 伤害值。").withStyle(ChatFormatting.WHITE)));
            Compute.PlayerShieldClear(player);
            mobList.forEach(mob -> {
                Compute.Damage.ManaDamageToMonster_ApDamage(player,mob,damage,true);
            });
        }
        if (activeTick >= 0) -- activeTick;
    }

    public static void SwordAirShoot(Player player) {
        SoraSwordAir swordAir = HealthUnderThreshold(player) ? new SoraSwordAir(ModEntityType.SORA_RED_SWORD_AIR.get(),player,player.level())
                : new SoraSwordAir(ModEntityType.SORA_SWORD_AIR.get(),player,player.level());
        swordAir.shootFromRotation(player,player.getXRot(), player.getYRot(),1,1,0);
        swordAir.setNoGravity(true);
        swordAir.setSilent(true);
        player.level().addFreshEntity(swordAir);
    }
}

