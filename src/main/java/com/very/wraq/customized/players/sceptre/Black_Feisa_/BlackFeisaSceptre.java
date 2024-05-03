package com.very.wraq.customized.players.sceptre.Black_Feisa_;

import com.very.wraq.coreAttackModule.ManaAttackModule;
import com.very.wraq.customized.Customize;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.Ice.IceSceptreAttributes;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.ModEntityType;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import com.very.wraq.valueAndTools.registry.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class BlackFeisaSceptre extends WraqSceptre {
    private final int Num;
    public BlackFeisaSceptre(Properties p_42964_, int Num) {
        super(p_42964_.rarity(Utils.End));
        this.Num = Num;
        Utils.ManaDamage.put(this, Customize.ManaDamage);
        Utils.ManaRecover.put(this, 30d);
        Utils.ManaPenetration0.put(this, Customize.ManaPenetration0);
        Utils.MovementSpeed.put(this, 0.4);
        Utils.ManaCost.put(this, 15d);
        Utils.MainHandTag.put(this,1d);
        Utils.SceptreTag.put(this,1.0d);
        Utils.CustomizedList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfEnd;
        Compute.ForgingHoverName(stack,Component.literal("爆裂魔杖" + IceSceptreAttributes.Number[Num]).withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components,Component.literal("约女娲").withStyle(style));
        components.add(Component.literal(" 展开法阵，每0.5s对周围所有目标，造成").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamageValue("100%")).
                append(Component.literal(" 并说出约邀请雄壮之躯").withStyle(style)));
        components.add(Component.literal(" 法阵造成的伤害被视为").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)));
        Compute.DescriptionPassive(components,Component.literal("你无敌了孩子").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("会释放两枚法球").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" -第一枚法球造成").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamageValue("200%")).
                append(Component.literal("并回复").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("15%")));
        components.add(Component.literal(" -第二枚法球造成").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamageValue("200%")).
                append(Component.literal("并为你提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("15%")).
                append(Component.literal("，持续5s，最多叠加至5层。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 无恐惧之人").withStyle(style));
        Compute.ManaCoreAddition(stack,components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一把蟒蛇缠绕的窒息感，授予对维瑞阿契做出了杰出贡献的 - Black_FeiSa_").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void shoot(Player player) {
        double ManaCost = 15;
        CompoundTag data = player.getPersistentData();
        Level level = player.level();
        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player, (int) ManaCost)) {
            BlackFeisa.Player = player;
            BlackFeisa.BlackFeisaManaCount = true;
            BlackFeisa.DoubleManaAttackDelay = 3;
            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW.get(), player, level,
                    PlayerAttributes.PlayerManaDamage(player),
                    PlayerAttributes.PlayerManaPenetration(player),
                    PlayerAttributes.PlayerManaPenetration0(player),StringUtils.ParticleTypes.EndParticle);
            newArrow.setSilent(true);
            newArrow.setNoGravity(true);

            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
            WraqSceptre.adjustOrb(newArrow, player);
            level.addFreshEntity(newArrow);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.WITCH);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.WITCH);
            Compute.SoundToAll(player, ModSounds.Mana.get());
        }
    }

    public static boolean IsPlayer(Player player) {
        return player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.BlackFeisaSceptre.get());
    }

    public static boolean ActiveStatus = false;

    public static int Count = 0;

    public static void ActiveStatusChange() {
        ActiveStatus = !ActiveStatus;
    }

    public static void Active(Player player) {
        if (!ActiveStatus) return;
        if (!IsPlayer(player)) return;
        Particle(player);
        if (player.tickCount % 10 != 0) return;
        ParticleProvider.VerticleCircleParticle((ServerPlayer) player,0,6,100, ModParticles.RED_SPELL.get());
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(),15,15,15));
        mobList.removeIf(mob -> mob.distanceTo(player) > 6);
        if (mobList.size() > 0) {
            mobList.forEach(mob -> {
                ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SNOW.get(), player, player.level(),
                        PlayerAttributes.PlayerManaDamage(player),
                        PlayerAttributes.PlayerManaPenetration(player),
                        PlayerAttributes.PlayerManaPenetration0(player), StringUtils.ParticleTypes.Snow);
                ManaAttackModule.BasicAttack(player, mob, PlayerAttributes.PlayerManaDamage(player),
                        PlayerAttributes.PlayerManaPenetration(player), PlayerAttributes.PlayerManaPenetration0(player),
                        player.level(), newArrow);
            });
        }
    }

    public static void Tick(Player player) {
        if (!IsPlayer(player)) return;
        Active(player);
    }

    public static void Particle(Player player) {
        Level level = player.level();
        ParticleProvider.VerticleCircleParticle((ServerPlayer) player,0,1.3,80, ModParticles.WHITE_SPELL.get());
        double r = 1.5;
        Vec3 Dot1 = new Vec3(0,0,r).add(player.position());
        Vec3 Dot2 = new Vec3(Math.sqrt(3) * r / 2, 0, - 0.5 * r).add(player.position());
        Vec3  Dot3 = new Vec3(- Math.sqrt(3) * r / 2, 0 , - 0.5 * r).add(player.position());
        ParticleProvider.LineParticle(level,20,Dot1,Dot2, ModParticles.WHITE_SPELL.get());
        ParticleProvider.LineParticle(level,20,Dot2,Dot3,ModParticles.WHITE_SPELL.get());
        ParticleProvider.LineParticle(level,20,Dot3,Dot1,ModParticles.WHITE_SPELL.get());

        Vec3  Dot4 = new Vec3(0,0 ,- r).add(player.position());
        Vec3  Dot5 = new Vec3(- Math.sqrt(3) * r / 2, 0, 0.5 * r).add(player.position());
        Vec3  Dot6 = new Vec3(Math.sqrt(3) * r / 2, 0, 0.5 * r).add(player.position());
        ParticleProvider.LineParticle(level,20,Dot4,Dot5,ModParticles.WHITE_SPELL.get());
        ParticleProvider.LineParticle(level,20,Dot5,Dot6,ModParticles.WHITE_SPELL.get());
        ParticleProvider.LineParticle(level,20,Dot6,Dot4,ModParticles.WHITE_SPELL.get());
    }


}
