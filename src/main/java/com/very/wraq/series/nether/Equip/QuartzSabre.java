package com.very.wraq.series.nether.Equip;

import com.very.wraq.process.element.Element;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Struct.ServerWaltzMonster;
import com.very.wraq.valueAndTools.Utils.Struct.ServerWaltzPlayer;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class QuartzSabre extends SwordItem {
    private double BaseDamage = 70.0d;
    private double BreakDefence = 0.15;
    private double CriticalHitRate = 0.50d;
    private double CHitDamage = 0.35;
    private double HealSteal = 0.08F;
    private double SpeedUp = 0.4F;
    private final double AttackSpeedUp = -2f;
    public QuartzSabre(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Properties().rarity(Utils.QuartzItalic));
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration.put(this,this.BreakDefence);
        Utils.HealthSteal.put(this,this.HealSteal);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.AttackSpeedUp.put(this,AttackSpeedUp);
        Element.FireElementValue.put(this, 0.8);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.SwordTag.put(this,1d);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        if(!level.isClientSide && useHand.equals(InteractionHand.MAIN_HAND))
        {
            CompoundTag data = player.getPersistentData();
            if(data.contains("MANA") && data.getInt("MANA") < 80) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("法力不足").withStyle(ChatFormatting.WHITE)));
            else
            {
                data.putInt("MANA",data.getInt("MANA")-80);
                Compute.ManaStatusUpdate(player);
                Player player1 = level.getNearestPlayer(TargetingConditions.DEFAULT,player,10,10,10);
                if(player1 != null)
                {
                    player1.getPersistentData().putInt("QuartzSabre",1200);
                    player1.getPersistentData().putInt("QuartzSabrePos",-1);
                    ServerWaltzPlayer serverWaltzPlayer = new ServerWaltzPlayer(player,player1);
                    Iterator<ServerWaltzPlayer> iterator = Utils.QuartzSabreCCPlayer.iterator();
                    while(iterator.hasNext())
                    {
                        ServerWaltzPlayer serverWaltzPlayer1 = iterator.next();
                        if(serverWaltzPlayer1.getAttacker().equals(player)) Utils.QuartzSabreCCPlayer.remove(serverWaltzPlayer1);
                    }
                    Utils.QuartzSabreCCPlayer.add(serverWaltzPlayer);
                }
                else
                {
                    Mob monster = level.getNearestEntity(Mob.class,TargetingConditions.DEFAULT,player,10,10,10, AABB.ofSize(player.position(),10,10,10));
                    if(monster != null)
                    {
                        monster.getPersistentData().putInt("QuartzSabre",1200);
                        monster.getPersistentData().putInt("QuartzSabrePos",-1);
                        ServerWaltzMonster serverWaltzMonster = new ServerWaltzMonster(player,monster);
                        Iterator<ServerWaltzMonster> iterator = Utils.QuartzSabreCCMonster.iterator();
                        while(iterator.hasNext())
                        {
                            ServerWaltzMonster serverWaltzMonster1 = iterator.next();
                            if(serverWaltzMonster1.getAttacker().equals(player)) Utils.QuartzSabreCCMonster.remove(serverWaltzMonster1);
                        }
                        Utils.QuartzSabreCCMonster.add(serverWaltzMonster);
                    }
                }
            }
        }
        return super.use(level, player, useHand);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("夸塔兹佩剑").withStyle(CustomStyle.styleOfQuartz).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长剑").withStyle(ChatFormatting.AQUA)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfQuartz,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfQuartz,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        components.add(Component.literal("主动：").withStyle(ChatFormatting.AQUA).
                append(Component.literal("利刃华尔兹").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#ea7552")))));
        components.add(Component.literal("标记一个距你最近的单位，该单位将会持续露出").withStyle(ChatFormatting.WHITE).
                append(Component.literal("破绽").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#b26814")))));
        components.add(Component.literal("击打").withStyle(ChatFormatting.WHITE).
                append(Component.literal("破绽").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#b26814")))).
                append(Component.literal("将会造成(5%攻击力)%目标最大生命值").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("额外真实伤害").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#b26814")))));
        components.add(Component.literal("并回复等量生命值，提供持续2s的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MovementSpeed("50%")));
        Compute.CoolDownTimeDescription(components,120);
        Compute.ManaCostDescription(components,80);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfQuartz,ChatFormatting.WHITE);
        components.add(Component.literal("Quartz-Sabre-0").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryIII(components);
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
}
