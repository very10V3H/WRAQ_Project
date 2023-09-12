package com.Very.very.Projectile.Mana;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.SkillPackets.Charging.ChargedClearS2CPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.Charging.ManaSkill12S2CPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.Charging.ManaSkill13S2CPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.SkillImageS2CPacket;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Struct.ManaSkillStruct.ManaSkill3;
import com.Very.very.ValueAndTools.Utils.Struct.ManaSkillStruct.ManaSkill6;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Objects;

import static java.lang.Math.E;
import static java.lang.Math.log;

public class ManaAttackModule {
    public static void BasicAttack(Player player, Entity entity, float BaseDamage, float BreakDefence, float BreakDefence0, Level level) {
        CompoundTag data = player.getPersistentData();
        SnowRune0(data);

        if(entity instanceof Mob monster && !(entity instanceof Villager))
        {
            float damage;
            float Defence = Compute.MonsterManaDefence(monster);
            float ExDamage = 0;
            float DamageIgnoreDefence = 0;
            float DamageEnhance = 0;
            float HealSteal = Compute.PlayerManaHealSteal(player);

            ExDamage += ManaSkill12(data,player,BaseDamage); // 盈能攻击（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心的范围内造成100%伤害）

            DamageIgnoreDefence += Compute.ManaSkillLevelGet(data,0) * BaseDamage * 0.01; // 法术热诚（你的法术攻击额外造成法术攻击1%的真实伤害）
            DamageIgnoreDefence += ManaSKill6(data,player,BaseDamage); // 完美（持续命中目标，将至多造成50%额外真实伤害）
            if (Compute.ManaSkillLevelGet(data,5) > 0 && player.getHealth() / player.getMaxHealth() < 0.5) {
                DamageIgnoreDefence += BaseDamage * 0.02 * Compute.ManaSkillLevelGet(data,5);
            } // 危机意识（当生命值低于50%时，造成额外20%真实伤害）

            DamageEnhance += ManaSkill3(data,player,monster); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            DamageEnhance += Compute.ManaSkillLevelGet(data,4) * 0.03; // 法术专注（额外造成3%的伤害，额外受到1.5%的伤害）
            DamageEnhance += Compute.LevelSuppress(player,monster); // 等级压制

            if (Defence < BreakDefence0) Defence = 0;
            else Defence -= BreakDefence0;

            if (Defence < 0) Defence = (float) Objects.requireNonNull(monster.getAttribute(Attributes.ARMOR)).getValue();
            damage = BaseDamage * (1.0F-(0.25F*(float)log(((Defence)*(1.0F-BreakDefence))*(E*E/250)+1)));
            if (ManaRune2(data,player,monster,damage)) damage *= 3;

            damage += ExDamage;
            damage *= (1 + DamageEnhance);
            DamageIgnoreDefence *= (1 + DamageEnhance);
            data.putBoolean("IsMana",true);
            data.putFloat(StringUtils.DamageTypes.DamageAmount,damage);
            data.putFloat(StringUtils.DamageTypes.DamageIgnoreDefenceAmount,DamageIgnoreDefence);
            monster.hurt(monster.damageSources().playerAttack(player),(damage + DamageIgnoreDefence));

            player.heal((damage + DamageIgnoreDefence) * HealSteal);
            ManaSkill3Attack(data,player,monster); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            ManaSkill6Attack(data,player,true); // 完美（持续命中目标，将至多造成50%额外真实伤害）
            ManaSkill12Attack(data,player); // 盈能攻击（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心的范围内造成100%伤害）
            ManaSkill13Attack(data,player,monster); // 法术收集（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将基于目标周围实体数量提供至多1000%的范围伤害，并回复自身50%的法力值）

            MagmaPower(data,player,level,monster);
        }
        if(entity instanceof Player hurter)
        {
            float damage;
            float Defence = Compute.PlayerManaDefence(hurter);
            float ExDamage = 0;
            float DamageIgnoreDefence = 0;
            float DamageEnhance = 0;
            float HealSteal = Compute.PlayerManaHealSteal(player);

            ExDamage += ManaSkill12(data,player,BaseDamage); // 盈能攻击（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心的范围内造成100%伤害）

            DamageIgnoreDefence += Compute.ManaSkillLevelGet(data,0) * BaseDamage * 0.01; // 法术热诚（你的法术攻击额外造成法术攻击1%的真实伤害）
            if (Compute.ManaSkillLevelGet(data,5) > 0 && player.getHealth() / player.getMaxHealth() < 0.5) {
                DamageIgnoreDefence += BaseDamage * 0.02 * Compute.ManaSkillLevelGet(data,5);
            } // 危机意识（当生命值低于50%时，造成额外20%真实伤害）
            DamageIgnoreDefence += ManaSKill6(data,player,BaseDamage); // 完美（持续命中目标，将至多造成50%额外真实伤害）

            DamageEnhance += ManaSkill3(data,player,hurter); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            DamageEnhance += Compute.ManaSkillLevelGet(data,4) * 0.03; // 法术专注（额外造成3%的伤害，额外受到1.5%的伤害）

            if (Defence < BreakDefence0) Defence = 0;
            else Defence -= BreakDefence0;

            if(Defence < 0) Defence = (float) Objects.requireNonNull(hurter.getAttribute(Attributes.ARMOR)).getValue();
            damage = BaseDamage*(1.0F-(0.25F*(float)log(((Defence)*(1.0F-BreakDefence))*(E*E/250)+1)));
            if (ManaRune2(data,player,hurter,damage)) damage *= 3;

            damage += ExDamage;
            damage *= (1 + DamageEnhance);
            DamageIgnoreDefence *= (1 + DamageEnhance);
            data.putBoolean("IsMana",true);
            data.putFloat(StringUtils.DamageTypes.DamageAmount,damage);
            data.putFloat(StringUtils.DamageTypes.DamageIgnoreDefenceAmount,DamageIgnoreDefence);
            hurter.hurt(hurter.damageSources().playerAttack(player),(damage + DamageIgnoreDefence) * 0.1f);

            player.heal((damage + DamageIgnoreDefence) * HealSteal);
            ManaSkill3Attack(data,player,hurter); // 机体解构（对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至2%，在5次攻击后达到最大值）
            ManaSkill6Attack(data,player,true); // 完美（持续命中目标，将至多造成50%额外真实伤害）
            ManaSkill12Attack(data,player); // 盈能攻击（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心的范围内造成100%伤害）
            ManaSkill13Attack(data,player,hurter); // 法术收集（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将基于目标周围实体数量提供至多1000%的范围伤害，并回复自身50%的法力值）

            MagmaPower(data,player,level,hurter);
        }
    }
    public static boolean ManaRune2 (CompoundTag data, Player player, LivingEntity monster, float damage) {
        if(data.contains("ManaRune") && data.getInt("ManaRune") == 2 && data.contains("ManaRune2") && data.getInt("ManaRune2") == 0)
        {
            Level level = player.level();
            data.putInt("ManaRune2",200);
            monster.getPersistentData().putInt("ManaRune2",60);
            Utils.MonsterAttributeDataProvider.add(monster);
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,level);
            lightningBolt.setCause((ServerPlayer) player);
            lightningBolt.setDamage(0);
            lightningBolt.setVisualOnly(true);
            lightningBolt.moveTo(monster.getPosition(1.0f));
            lightningBolt.setSilent(true);
            level.addFreshEntity(lightningBolt);
            if(!data.contains("IgnoreRune")||(!data.getBoolean("IgnoreRune"))) Compute.FormatMSGSend(player, Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                    Component.literal("魔源符石-苍雷之怒").withStyle(ChatFormatting.LIGHT_PURPLE).append(Component.literal("在本次攻击中，造成了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal(String.format("%.2f",damage*3))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE).
                            append(Component.literal("伤害").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

            return true;
        }
        return false;
    }
    public static void MagmaPower (CompoundTag data, Player player, Level level, LivingEntity hurter) {
        if (data.contains("MagmaPower") && data.getBoolean("MagmaPower")) {
            Compute.MagmaPower(hurter,level,player);
            data.putBoolean("MagmaPower",false);
        }
    }
    public static void SnowRune0 (CompoundTag data) {
        if (data.contains("snowRune") && data.getInt("snowRune") == 0) {
            if (!data.contains("snowRune0") || data.getInt("snowRune0") == 0) {
                data.putInt("snowRune0",1);
                data.putInt("snowRune0Time",100);
            }
            else {
                if (data.getInt("snowRune0") < 5) {
                    data.putInt("snowRune0",data.getInt("snowRune0")+1);
                    data.putInt("snowRune0Time",100);
                }
                else {
                    data.putInt("snowRune0Time",100);
                }
            }
        }
    }
    public static void ManaSkill3Attack (CompoundTag data, Player player, Entity entity) {
        int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
        if (Compute.ManaSkillLevelGet(data,3) > 0) {
            if (Utils.ManaSkill3Map.containsKey(player)) {
                ManaSkill3 manaSkill3 = Utils.ManaSkill3Map.get(player);
                if (manaSkill3.getTime() > TickCount && manaSkill3.getEntity().equals(entity)) {
                    if (manaSkill3.getCount() < 5) manaSkill3.setCount(manaSkill3.getCount() + 1);
                    manaSkill3.setTime(TickCount + 200);
                }
                else {
                    manaSkill3.setEntity(entity);
                    manaSkill3.setCount(1);
                    manaSkill3.setTime(TickCount + 200);
                }
            }
            else {
                ManaSkill3 manaSkill3 = new ManaSkill3(entity,1,TickCount + 200);
                Utils.ManaSkill3Map.put(player,manaSkill3);
            }
            ManaSkill3 manaSkill3 = Utils.ManaSkill3Map.get(player);
            ModNetworking.sendToClient(new SkillImageS2CPacket(4,10,10,manaSkill3.getCount(),2),(ServerPlayer) player);
        }
    }
    public static float ManaSkill3 (CompoundTag data, Player player, LivingEntity monster) {
        float DamageEnhance = 0;
        int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
        if (Compute.ManaSkillLevelGet(data,3) > 0 && Utils.ManaSkill3Map.containsKey(player)) {
            ManaSkill3 manaSkill3 = Utils.ManaSkill3Map.get(player);
            if (manaSkill3.getEntity().equals(monster) && manaSkill3.getTime() > TickCount) {
                DamageEnhance += Compute.ManaSkillLevelGet(data,3) * 0.04 * manaSkill3.getCount();
            }
        }
        return DamageEnhance;
    }
    public static void ManaSkill6Attack (CompoundTag data, Player player, boolean flag) {
        int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
        if (Compute.ManaSkillLevelGet(data,6) > 0) {
            if (flag) {
                if (Utils.ManaSkill6Map.containsKey(player)) {
                    ManaSkill6 manaSkill6 = Utils.ManaSkill6Map.get(player);
                    if (manaSkill6.getTime() > TickCount) {
                        if (manaSkill6.getCount() < 3) manaSkill6.setCount(manaSkill6.getCount() + 1);
                        manaSkill6.setTime(TickCount + 200);
                    }
                    else {
                        manaSkill6.setTime(TickCount + 200);
                        manaSkill6.setCount(1);
                    }
                }
                else {
                    ManaSkill6 manaSkill6 = new ManaSkill6(1,TickCount + 200);
                    Utils.ManaSkill6Map.put(player,manaSkill6);
                }
                ManaSkill6 manaSkill6 = Utils.ManaSkill6Map.get(player);
                ModNetworking.sendToClient(new SkillImageS2CPacket(7,10,10,manaSkill6.getCount(),2),(ServerPlayer) player);

            }
            else {
                if (Utils.ManaSkill6Map.containsKey(player)) {
                    ManaSkill6 manaSkill6 = Utils.ManaSkill6Map.get(player);
                    manaSkill6.setCount(0);
                }
                else {
                    ManaSkill6 manaSkill6 = new ManaSkill6(0,0);
                    Utils.ManaSkill6Map.put(player,manaSkill6);
                }
                ModNetworking.sendToClient(new SkillImageS2CPacket(7,0,0,0,2),(ServerPlayer) player);

            }
        }
    }
    public static float ManaSkill12 (CompoundTag data, Player player, float BaseDamage) {
        if (Compute.ManaSkillLevelGet(data,12) > 0 && Utils.ManaSkill12.containsKey(player)
                && Utils.ManaSkill12.get(player)) {
            return BaseDamage * Compute.ManaSkillLevelGet(data,12) * 0.4f;
        }
        return 0;
    }
    public static void ManaSkill12Attack (CompoundTag data, Player player) {
        if (Compute.ManaSkillLevelGet(data,12) > 0 && Utils.ManaSkill12.containsKey(player)
                && Utils.ManaSkill12.get(player)) {
            Level level = player.level();
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.getPosition(1.0f),10,10,10));
            for (Mob mob : mobList) {
                Compute.DamageToMonster(player,mob,0.2f * Compute.ManaSkillLevelGet(data,12));
            }
            List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(player.getPosition(1.0f),10,10,10));
            for (Player player1 : playerList) {
                Compute.DamageToPlayer(player,player1,0.2f * Compute.ManaSkillLevelGet(data,12));
            }
            Utils.ManaSkill12.put(player,false);
            ModNetworking.sendToClient(new ChargedClearS2CPacket(2),(ServerPlayer) player);
        }
    }

    public static void ManaSkill13Attack (CompoundTag data, Player player, LivingEntity livingEntity) {
        if (Compute.ManaSkillLevelGet(data,13) > 0 && Utils.ManaSkill13.containsKey(player)
                && Utils.ManaSkill13.get(player)) {
            Level level = player.level();
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(livingEntity.getPosition(1.0f),10,10,10));
            List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(livingEntity.getPosition(1.0f),10,10,10));
            int Count = mobList.size() + playerList.size();
            if (Count > 10) Count = 10;
            for (Mob mob : mobList) {
                Compute.DamageToMonster(player,mob,0.2f * Compute.ManaSkillLevelGet(data,13) * Count);
            }
            for (Player player1 : playerList) {
                Compute.DamageToPlayer(player,player1,0.2f * Compute.ManaSkillLevelGet(data,13) * Count);
            }
            Compute.PlayerManaAddOrCost(player, (int) (data.getInt("MAXMANA") * 0.5));
            Utils.ManaSkill13.put(player,false);
            ModNetworking.sendToClient(new ChargedClearS2CPacket(3),(ServerPlayer) player);
        }
    }
    public static float ManaSKill6 (CompoundTag data, Player player, float BaseDamage) {
        int TickCount = Objects.requireNonNull(player.getServer()).getTickCount();
        if (Compute.ManaSkillLevelGet(data,6) > 0 && Utils.ManaSkill6Map.containsKey(player) && Utils.ManaSkill6Map.get(player).getTime() > TickCount) {
            return Compute.ManaSkillLevelGet(data,6) * 0.0066f * BaseDamage * Utils.ManaSkill6Map.get(player).getCount();
        }
        return 0;
    }
}
