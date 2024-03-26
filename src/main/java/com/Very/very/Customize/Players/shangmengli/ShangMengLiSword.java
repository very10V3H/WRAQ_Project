package com.Very.very.Customize.Players.shangmengli;

import com.Very.very.CoreAttackModule.ManaAttackModule;
import com.Very.very.Customize.Customize;
import com.Very.very.NetWorking.Customized.ShangMengLiSwordTickS2CPacket;
import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.AnimationPackets.ShangMengLiAttackAnimationS2CPacket;
import com.Very.very.NetWorking.Packets.USE.UtilsLakeSwordS2CPacket;
import com.Very.very.Process.Particle.ParticleProvider;
import com.Very.very.Projectile.Mana.ManaArrow;
import com.Very.very.Projectile.Mana.ShangMengLiSwordAir;
import com.Very.very.ValueAndTools.BasicAttributeDescription;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.ModEntityType;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ShangMengLiSword extends SwordItem{
    private final double BaseDamage = Customize.ManaDamage;
    private final double ManaCost = 15;
    private final double DefencePenetration0 = Customize.ManaPenetration0;
    private final double ManaRecover = 30;
    private final double SpeedUp = 0.5F;
    private final double AttackSpeedUp = -2f;
    public ShangMengLiSword(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Properties().rarity(Utils.EvokerItalic));
        Utils.ManaDamage.put(this,this.BaseDamage);
        Utils.ManaCost.put(this,this.ManaCost);
        Utils.ManaPenetration0.put(this,this.DefencePenetration0);
        Utils.ManaRecover.put(this,this.ManaRecover);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.AttackSpeedUp.put(this,AttackSpeedUp);
        Utils.MainHandTag.put(this,1d);
        Utils.CustomizedList.add(this);
        Utils.SceptreTag.put(this,1d);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {

        if (!level.isClientSide) {
            Active(player);
        }

        return super.use(level, player, useHand);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = Utils.styleOfMana;
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("魔能巨剑").withStyle(ChatFormatting.RESET).withStyle(style)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("魔能斩击").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 普通攻击将会释放一道").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("魔能剑气").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("，并为你提供一层充能").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("当充能达3层时，可以施放:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("乱舞").withStyle(ChatFormatting.RESET).withStyle(style)));
        components.add(Component.literal(" 魔能剑气").withStyle(ChatFormatting.RESET).withStyle(style).
                append(Component.literal("在命中目标时会回复").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("20")).
                append(Component.literal("同时为你提供").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("50%")).
                append(Component.literal("与").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("25%")));
        components.add(Component.literal(" 魔能剑气").withStyle(ChatFormatting.RESET).withStyle(style).
                append(Component.literal("在对目标造成伤害时，视为").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("普通法球攻击").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("，同时将基于").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CoolDown("")).
                append(Component.literal("，来为基础伤害提供至多").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("200%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).
                append(Component.literal("伤害增幅").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionActive(components,Component.literal("乱舞").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 在1.5s内进行").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("四次").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("普通攻击").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        Compute.ManaCoreAddition(stack,components);
        components.add(Component.literal(" 掌握核心科技").withStyle(ChatFormatting.RESET).withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一把魔能巨剑，授予对维瑞阿契做出了杰出贡献的 - shangmengli").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        p_43278_.hurtAndBreak(0, p_43280_, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
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
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static int ShangMengLiActiveTickCount = -1;

    public static int ShangMengLiSwordAirDamageUpTick = 0;

    public static int ShangMengLiAnimationClientTick = 0;

    public static int ShangMengLiAttackCount = 0;

    public static int ActiveAttributeEnhanceTick = 0;

    public static void SwordAir(Player player, boolean isPower) {
        ShangMengLiSwordAir swordAir = new ShangMengLiSwordAir(ModEntityType.SHANGMENGLI_SWORD_AIR.get(),player,player.level(),isPower);
        swordAir.shootFromRotation((Entity) player,player.getXRot(), player.getYRot(),1,1,0);
        swordAir.setNoGravity(true);
        swordAir.setSilent(true);
        player.level().addFreshEntity(swordAir);
    }

    public static List<Integer> tickList = new ArrayList<>(){{
        add(26);
        add(16);
        add(6);
        add(0);
    }};

    public static void Tick(Player player) {
        if (!isOn(player)) return;
        if (ShangMengLiActiveTickCount >= 0) {
            if (tickList.contains(ShangMengLiActiveTickCount)) {
                RangeAttack(player);
                Compute.SoundToAll(player, SoundEvents.PLAYER_ATTACK_SWEEP);
            }
            ShangMengLiActiveTickCount --;
        }
    }

    public static boolean isOn(Player player) {
        return player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ShangMengLiSword.get());
    }

    public static void Active(Player player) {
        int TickCount = player.getServer().getTickCount();
        Compute.PlayerItemCoolDown(player,ModItems.ShangMengLiSword.get(), 8);
        ActiveAttributeEnhanceTick = TickCount + 100;
        Compute.EffectLastTimeSend(player,ModItems.ShangMengLiSword.get().getDefaultInstance(),100);
        ModNetworking.sendToClient(new UtilsLakeSwordS2CPacket(true), (ServerPlayer) player);
    }

    public static boolean ForthNormalAttack(Player player) {
        if (ShangMengLiAttackCount < 3) return false;
        if (ShangMengLiActiveTickCount >= 0) return false;
        ServerPlayer serverPlayer = (ServerPlayer) player;
        List<ServerPlayer> playerList = serverPlayer.getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer1 -> {
            ModNetworking.sendToClient(new ShangMengLiAttackAnimationS2CPacket(serverPlayer.getId(),0),serverPlayer1);
        });
        ShangMengLiSword.ShangMengLiActiveTickCount = 31;
        ModNetworking.sendToClient(new ShangMengLiSwordTickS2CPacket(31),serverPlayer); // 对shangmengli客户端发包 以限制其在动画执行期间进行普通攻击
        ShangMengLiAttackCount = 0;
        Compute.EffectLastTimeSend(player,ModItems.ShangMengLiSword.get().getDefaultInstance(),8888,ShangMengLiAttackCount,true);
        return true;
    }

    public static void OnHitEntity(List<Entity> list, ShangMengLiSwordAir shangMengLiSwordAir) {
        if (shangMengLiSwordAir.player == null) return;
        list.forEach(entity1 -> {
            if (entity1 instanceof Mob mob && !shangMengLiSwordAir.player.level().isClientSide) {
                boolean isPower = shangMengLiSwordAir.isPower();
                Player player = shangMengLiSwordAir.player;
                if (!isPower) {
                    int TickCount = player.getServer().getTickCount();
                    ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW.get(), player, player.level(),
                            Compute.PlayerAttributes.PlayerManaDamage(player),
                            Compute.PlayerAttributes.PlayerManaPenetration(player),
                            Compute.PlayerAttributes.PlayerManaPenetration0(player), 0);
                    ManaAttackModule.BasicAttack(player, entity1, Compute.PlayerAttributes.PlayerManaDamage(player),
                            Compute.PlayerAttributes.PlayerManaPenetration(player), Compute.PlayerAttributes.PlayerManaPenetration0(player),
                            player.level(), newArrow);
                    ShangMengLiSword.ShangMengLiSwordAirDamageUpTick = TickCount + 100;
                    Compute.PlayerManaAddOrCost(player, 20);
                }
                else {
                    Compute.Damage.ManaDamageToMonster_RateApDamage(player,(Mob) entity1,1 + 3 * Compute.PlayerAttributes.PlayerCoolDownDecrease(player),isPower);
                    Compute.PlayerManaAddOrCost(player, 20);
                }
            }
        });
    }

    public static double MaxManaEnhance(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (isOn(player) && ShangMengLiSwordAirDamageUpTick > TickCount) return 1.25;
        return 1;
    }

    public static double ManaDamageEnhance(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (isOn(player) && ShangMengLiSwordAirDamageUpTick > TickCount) return 0.5;
        return 0;
    }

    public static void RangeAttack(Player player) {
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(),15,15,15));
        mobList.removeIf(mob -> mob.distanceTo(player) > 8);

        mobList.forEach(mob -> {
            Compute.Damage.ManaDamageToMonster_RateApDamage(player,mob,10,true);
            Compute.Damage.LastXpStrengthDamageToMob(player,mob,0.25,40,10,false);
        });

        Compute.PlayerManaAddOrCost(player,20);
        ParticleProvider.VerticleCircleParticle(player.position(),(ServerLevel) player.level(),1,8,100, ParticleTypes.WITCH);
        ParticleProvider.VerticleCircleParticle(player.position(),(ServerLevel) player.level(),1.5,8,100,ParticleTypes.WITCH);

    }
}
