package com.Very.very.Events.MainEvents;

import com.Very.very.Events.WaltzAndModule.AttackEventModule;
import com.Very.very.Series.NetherSeries.Equip.ManaSword;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class AttackEvent {
/*    @SubscribeEvent
    public static void LeftClick0(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) {
            Compute.AttackJudge(player);
        }
    }

    @SubscribeEvent
    public static void LeftClick1(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) {
            Compute.AttackJudge(player);
        }
    }*/

    @SubscribeEvent
    public static void Attack(AttackEntityEvent event) {
        event.setCanceled(true);
        Player player = event.getEntity();
        Entity entity = event.getTarget();
        if (entity.getClass() == Villager.class ||
                entity.getClass() == WanderingTrader.class ||
                entity instanceof Animal
        ) event.setCanceled(true);                    //保护动物人人有责。
        CompoundTag data = player.getPersistentData();
        data.putBoolean("ARROW",false);
        if (player.getName().getString().equals("very_H") && player.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(MinecartItem.byId(765))
                || player.getName().getString().equals("Dev") && player.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(MinecartItem.byId(765)) )
            entity.remove(Entity.RemovalReason.KILLED);
        if (event.getEntity().getPersistentData().getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline)) {
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("请先登录！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        }
/*        if (entity.level().isClientSide) {
            Compute.AttackJudge(player);
        }*/
        Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        if (entity instanceof Mob monster && !entity.level().isClientSide && monster.hurtTime == 0) {
            float Defence = Compute.MonsterDefence(monster);
            AttackEventModule.SnowRune2(data,monster,player,Defence);
            float BaseDamage = Compute.PlayerAttackDamage(player);
            float BreakDefence = Compute.PlayerBreakDefence(player);
            float CriticalHitRate = Compute.PlayerCriticalHitRate(player);
            float CHitDamage = Compute.PlayerCriticalHitDamage(player);
            if (Utils.SnowRune2MobController.contains(monster)) Defence *= 0.5f;
            float BreakDefence0 = Compute.PlayerBreakDefence0(player);
            int LightningArmorCount = Compute.LightningArmorCount(player);
            if (monster instanceof Evoker && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ManaSword)
                BreakDefence = 1.0F;
            Random r = new Random();
            double RanNum = r.nextFloat(1.00F);
            AttackEventModule.MineSwordSlowDownForce(item, monster);
            if (Defence == 0) Defence = (float) (monster.getAttribute(Attributes.ARMOR).getValue());

            float damage;
            float ExDamage = 0;
            float ExDamageIgnoreDefence = 0;
            float DamageEnhance = 0;

            data.putBoolean("IsAttack",true);
            // 变量定义与部分效果附加

            AttackEventModule.LakeSwordSpeedImproveModule(item, player, data);
            AttackEventModule.AnimalThingsGetModule(entity, player);

            ExDamage += AttackEventModule.ForestRune3(data,monster,BaseDamage); // 森林符石-生长汲取
            ExDamage += AttackEventModule.BlackForest(data,monster,BaseDamage); // 灵魂收割者主动
            ExDamage += AttackEventModule.SwordSkill12(data,player,BaseDamage); // 刀光剑影（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以自身为中心范围内造成100%伤害）
            ExDamageIgnoreDefence += AttackEventModule.WaltzCompute(player,monster,data); //华尔兹
            ExDamageIgnoreDefence += AttackEventModule.LightningArmor(player,LightningArmorCount,data); // 唤雷套被动
            ExDamageIgnoreDefence += AttackEventModule.SeaSword(data,monster,BaseDamage); //灵魂救赎者主动
            ExDamageIgnoreDefence += AttackEventModule.VolcanoRune2(data,monster,BaseDamage); //火山符石-熔岩强击
            ExDamageIgnoreDefence += AttackEventModule.SwordSkill0(data,BaseDamage); //剑术热诚（获得1%额外真实伤害）
            ExDamageIgnoreDefence += AttackEventModule.SwordSkill13(data,player,BaseDamage); // 战争热诚（攻击将会提供1层充能，暴击提供2层充能，每层充能将会提升1%的额外真实伤害，并获得等量治疗效果 持续6秒）
            ExDamageIgnoreDefence += AttackEventModule.SwordSkill14(data,player,BaseDamage,monster); // 恃强凌弱（对生命值百分比低于你的目标造成至多20%额外真实伤害 在百分比差值达66%时达到最大值 当受到生命值百分比高于你的目标的伤害使伤害额外提升同样的数值）

            DamageEnhance += AttackEventModule.SwordSkill3(data,player,monster); // 破绽观察（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在10次攻击后达到最大值）
            DamageEnhance += Compute.SwordSkillLevelGet(data,4) * 0.03; // 双刃剑（额外造成3%的伤害，额外受到1.5%的伤害）
            DamageEnhance += Compute.LevelSuppress(player,monster); // 等级压制

            if (RanNum < CriticalHitRate) {
                if (BreakDefence0 >= Defence) {
                    damage = BaseDamage * (1.0F + CHitDamage) + ExDamage;
                    AttackEventModule.BlackForestExDamageNumGet1(data,monster,CHitDamage);
                }
                else {
                    damage = (BaseDamage * (1.0F + CHitDamage) + ExDamage) * Compute.DefenceDamageDecreaseRate(Defence,BreakDefence,BreakDefence0);
                    AttackEventModule.BlackForestExDamageNumGet2(data,monster,CHitDamage,Defence,BreakDefence0,BreakDefence);
                } // 基本伤害计算
                if (Utils.OverWorldLevelIsNight && player.level().equals(player.getServer().getLevel(Level.OVERWORLD)))
                    damage *= 0.8f; // 主世界怪物强化
                damage *= (1 + DamageEnhance);
                ExDamageIgnoreDefence *= (1 + DamageEnhance);
                data.putBoolean(StringUtils.DamageTypes.Crit, true);
                data.putFloat(StringUtils.DamageTypes.DamageAmount,damage);
                data.putFloat(StringUtils.DamageTypes.DamageIgnoreDefenceAmount,ExDamageIgnoreDefence);
                monster.hurt(monster.damageSources().playerAttack(player),damage + ExDamageIgnoreDefence);

            } else {
                if (BreakDefence0 >= Defence) {
                    damage = BaseDamage + ExDamage;
                    AttackEventModule.BlackForestSwordGet3(data,monster);
                }
                else {
                    damage = (BaseDamage + ExDamage) * Compute.DefenceDamageDecreaseRate(Defence,BreakDefence,BreakDefence0);
                    AttackEventModule.BlackForestSwordGet4(data,monster,Defence,BreakDefence0,BreakDefence);
                }
                if (Utils.OverWorldLevelIsNight && player.level().equals(player.getServer().getLevel(Level.OVERWORLD)))
                    damage *= 0.8f;
                damage *= (1 + DamageEnhance);
                ExDamageIgnoreDefence *= (1 + DamageEnhance);
                data.putBoolean(StringUtils.DamageTypes.Crit, false);
                data.putFloat(StringUtils.DamageTypes.DamageAmount,damage);
                data.putFloat(StringUtils.DamageTypes.DamageIgnoreDefenceAmount,ExDamageIgnoreDefence);
                monster.hurt(monster.damageSources().playerAttack(player),damage + ExDamageIgnoreDefence);
            }
        }
        if (entity instanceof Player hurter && !event.getEntity().level().isClientSide && hurter.hurtTime == 0) {
            float Defence = Compute.PlayerDefence(hurter);
            float BaseDamage = Compute.PlayerAttackDamage(player);
            float BreakDefence = Compute.PlayerBreakDefence(player);
            float CriticalHitRate = Compute.PlayerCriticalHitRate(player);
            float CHitDamage = Compute.PlayerCriticalHitDamage(player);
            float BreakDefence0 = Compute.PlayerBreakDefence0(player);
            data.putBoolean(StringUtils.DamageTypes.IsAttack,true);
            int LightningArmorCount = Compute.LightningArmorCount(player);
            Random r = new Random();
            double RanNum = r.nextFloat(1.00F);
            AttackEventModule.MineSwordSlowDownForcePlayer(item, hurter);
            if (Defence == 0) Defence = (float) hurter.getAttribute(Attributes.ARMOR).getValue();
            float damage = 0;
            float ExDamage = 0;
            float ExDamageIgnoreDefence = 0;
            float DamageEnhance = 0;

            ExDamage += AttackEventModule.ToPlayerBlackForest(data,hurter);
            ExDamage += AttackEventModule.ToPlayerForestRune3(data,hurter);
            ExDamage += AttackEventModule.SwordSkill12(data,player,BaseDamage); // 刀光剑影（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以自身为中心范围内造成100%伤害）
            ExDamageIgnoreDefence += AttackEventModule.ToPlayerWaltz(player,hurter,data);
            ExDamageIgnoreDefence += AttackEventModule.LightningArmor(player,LightningArmorCount,data);
            ExDamageIgnoreDefence += AttackEventModule.ToPlayerSeaSword(data,hurter);
            ExDamageIgnoreDefence += AttackEventModule.ToPlayerVolcanoRune2(data,hurter);
            ExDamageIgnoreDefence += AttackEventModule.SwordSkill0(data,BaseDamage); //剑术热诚（获得1%额外真实伤害）
            ExDamageIgnoreDefence += AttackEventModule.SwordSkill13(data,player,BaseDamage); // 战争热诚（攻击将会提供1层充能，暴击提供2层充能，每层充能将会提升1%的额外真实伤害，并获得等量治疗效果 持续6秒）
            ExDamageIgnoreDefence += AttackEventModule.SwordSkill14(data,player,BaseDamage,hurter); // 恃强凌弱（对生命值百分比低于你的目标造成至多20%额外真实伤害 在百分比差值达66%时达到最大值 当受到生命值百分比高于你的目标的伤害使伤害额外提升同样的数值）

            DamageEnhance += AttackEventModule.SwordSkill3(data,player,hurter); // 破绽观察（对一名目标的持续攻击，可以使你对该目标的伤害至多提升至2%，在10次攻击后达到最大值）
            DamageEnhance += Compute.SwordSkillLevelGet(data,4) * 0.03; // 双刃剑（额外造成3%的伤害，额外受到1.5%的伤害）

            if (RanNum < CriticalHitRate) { // 暴击
                if (BreakDefence0 >= Defence) {
                    damage = (BaseDamage * (1.0F + CHitDamage) + ExDamage);
                    AttackEventModule.ToPlayerBlackForestGet1(data,hurter,CHitDamage);
                }
                else {
                    damage = (BaseDamage * (1.0F + CHitDamage) + ExDamage) * Compute.DefenceDamageDecreaseRate(Defence,BreakDefence,BreakDefence0);
                    AttackEventModule.ToPlayerBlackForestGet2(data,hurter,CHitDamage,BreakDefence0,BreakDefence,Defence);
                }
                data.putBoolean(StringUtils.DamageTypes.Crit, true);
            } else { //非暴击
                if (BreakDefence0 >= Defence) {
                    damage = BaseDamage;
                    AttackEventModule.ToPlayerBlackForestGet3(data,hurter,CHitDamage);
                }
                else {
                    damage = BaseDamage * Compute.DefenceDamageDecreaseRate(Defence,BreakDefence,BreakDefence0);
                    AttackEventModule.ToPlayerBlackForestGet4(data,hurter,CHitDamage,BreakDefence0,BreakDefence,Defence);
                }
                data.putBoolean(StringUtils.DamageTypes.Crit, false);
            }
            damage *= (1 + DamageEnhance);
            ExDamageIgnoreDefence *= (1 + DamageEnhance);

            data.putFloat(StringUtils.DamageTypes.ToPlayerDamage,(damage + ExDamageIgnoreDefence) * 0.1f);
            hurter.hurt(hurter.damageSources().playerAttack(player), (damage + ExDamageIgnoreDefence) * 0.1f);
        }
    }
}
