package com.very.wraq.customized.players.sceptre.FengXiaoju;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.mana.NewArrow;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import com.very.wraq.valueAndTools.registry.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class FengXiaoJuCurios1 extends Item implements ICurioItem {

    public FengXiaoJuCurios1(Properties p_41383_) {
        super(p_41383_);
        Utils.ManaDamage.put(this, Attributes.ManaDamage);
        Utils.Defence.put(this,Attributes.Defence);
        Utils.ManaPenetration0.put(this, Attributes.ManaPenetration0);
        Utils.ManaRecover.put(this, Attributes.ManaRecover);
        Utils.MaxMana.put(this, Attributes.MaxMana);
        Utils.CoolDownDecrease.put(this, Attributes.CoolDown);
        Utils.CustomizedList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfSky;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("不休独舞").withStyle(style));
        components.add(Component.literal(" 当你的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("")).
                append(Component.literal("增加或减少时，获得等同于数值的1.5倍的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("")).
                append(Component.literal("，至多叠加至").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxHealth("150%")));
        components.add(Component.literal(" 存储的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("")).
                append(Component.literal("将持续9s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 根据获得的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("")).
                append(Component.literal("与生命值的比例，为你提供:").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("  50% - ").withStyle(ChatFormatting.GREEN).
                append(Compute.AttributeDescription.CritDamage("50%")));
        components.add(Component.literal("  100% - ").withStyle(ChatFormatting.GREEN).
                append(Compute.AttributeDescription.MaxMana("25%")));
        components.add(Component.literal("  150% - ").withStyle(ChatFormatting.GREEN).
                append(Component.literal("20%总伤害提升").withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionPassive(components,Component.literal("以我之名").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("将会额外释放一枚法球").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" - 水的共鸣使装备拥有者获得+1攀爬高度").withStyle(style));
        components.add(Component.literal(" - 以凡人之力，比肩真正神明").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚水之神的红酒杯，授予对维瑞阿契做出了杰出贡献的 - Fengxiaoju").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    public static Player onPlayer;
    public static boolean On;

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        onPlayer = (Player) slotContext.entity();
        On = true;
        ((Player) slotContext.entity()).getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).setBaseValue(0.5D);
        Compute.AddCuriosToList(onPlayer,stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        onPlayer = (Player) slotContext.entity();
        On = false;
        ((Player) slotContext.entity()).getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).setBaseValue(0.0D);
        Compute.RemoveCuriosInList(onPlayer,stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static boolean IsOn(Player player) {
        return onPlayer != null && onPlayer.equals(player) && On;
    }

    public static double storeValue = 0;
    public static int lastStoreTick = 0;
    public static int ExManaAttackTick = 0;
    public static boolean AttackCount = false;

    public static void Tick(Player player) {
        if (!IsOn(player)) return;
        int TickCount = player.getServer().getTickCount();
        if (lastStoreTick + 180 < TickCount && storeValue > 0) {
            storeValue = 0;
            Compute.EffectLastTimeSend(player, ModItems.FengxiaojuCurios_1.get().getDefaultInstance(),
                    8888, 0,true);
        }
        if (ExManaAttackTick == TickCount) Shoot(player);
    }

    public static void Store(Player player, double value) {
        if (!IsOn(player) || value <= 0) return;
        double MaxHealth = player.getMaxHealth();
        storeValue = Math.min(MaxHealth * 1.5, storeValue + value);
        Compute.EffectLastTimeSend(player, ModItems.FengxiaojuCurios_1.get().getDefaultInstance(),
                8888, (int) (storeValue * 150 / player.getMaxHealth()),true);
        lastStoreTick = player.getServer().getTickCount();
    }

    public static double ExManaDamage(Player player) {
        if (!IsOn(player)) return 0;
        return storeValue * 1.5;
    }

    public static void ExAttackCount(Player player) {
        if (!IsOn(player)) return;
        int TickCount = player.getServer().getTickCount();
        ExManaAttackTick = TickCount + 3;
    }

    public static void Shoot(Player player) {
        Level level = player.level();
        NewArrow newArrow = new NewArrow(player, level, PlayerAttributes.PlayerManaDamage(player) * 2, PlayerAttributes.PlayerManaPenetration(player), PlayerAttributes.PlayerExpUp(player), false, PlayerAttributes.PlayerManaPenetration0(player));
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

    public static double ExCritDamage(Player player) {
        if (!IsOn(player)) return 0;
        if (storeValue >= player.getMaxHealth() * 0.5) return 0.5;
        return 0;
    }

    public static double MaxMana(Player player) {
        if (!IsOn(player)) return 1;
        if (storeValue >= player.getMaxHealth()) return 1.25;
        return 1;
    }

    public static double DamageEnhance(Player player) {
        if (!IsOn(player)) return 0;
        if (storeValue >= player.getMaxHealth() * 1.5) return 0.2;
        return 0;
    }

/*    public static void ExAttackEffect(Player player, Mob mob) {
        if (!IsOn(player)) return;
        if (AttackCount) Compute.Damage.LastXpStrengthDamageToMob(player,mob,0.25,40,10,false);
        else AttackCount = true;
    }*/

}
