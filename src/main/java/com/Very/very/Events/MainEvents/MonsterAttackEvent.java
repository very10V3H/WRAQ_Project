package com.Very.very.Events.MainEvents;

import com.Very.very.Events.WaltzAndModule.HurtEventModule;
import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.SoundsPackets.SoundsS2CPacket;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorLeggings;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class MonsterAttackEvent {

    @SubscribeEvent
    public static void monsterAttackEvent(LivingAttackEvent event)
    {
        if(!event.getEntity().level().isClientSide)
        {
            if(event.getEntity() instanceof Player player && event.getSource().getEntity() instanceof Mob monster
                    && event.getEntity().hurtTime == 0)
            {
                event.setCanceled(true);
                CompoundTag data = player.getPersistentData();
                player.setLastHurtByMob(monster);
                Compute.ChargingModule(data,player); // 盈能攻击

                Item monsterHelmet = monster.getItemBySlot(EquipmentSlot.HEAD).getItem();
                float BreakDefence = Utils.BreakDefence.getOrDefault(monsterHelmet,0f);
                float BreakDefence0 = Utils.BreakDefence0.getOrDefault(monsterHelmet,0f);
                float CritRate = Utils.CriticalHitRate.getOrDefault(monsterHelmet,0f);
                float CritDamage = Utils.CriticalHitRate.getOrDefault(monsterHelmet,0f);
                float HealSteal = Utils.CriticalHitRate.getOrDefault(monsterHelmet,0f);


                double BaseDamage = 0;
                if (monster.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                    BaseDamage = monster.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
                }
                else BaseDamage = 0;

                float Defence = Compute.PlayerDefence(player);
                float ManaDefence = Compute.PlayerManaDefence(player);
                float ExDamage = 0;

                ExDamage += MonsterExDamage(monster,player); // 各种怪物伤害增益

                float DamageDecrease = Compute.SwordSkill1And4(data,player);
                DamageDecrease += Compute.SwordSkill14(data,player,monster);
                DamageDecrease += Compute.BowSkill4(data,player);
                DamageDecrease += Compute.ManaSkill4(data,player);
                DamageDecrease += Compute.LevelSuppress(player,monster); // 等级压制


                float Damage = (float) ((BaseDamage + ExDamage) * CritDamage(CritRate,CritDamage) *
                        Compute.DefenceDamageDecreaseRate(Defence,BreakDefence,BreakDefence0)) *
                        (1 - DamageDecrease);
                if(Utils.witherBonePowerCCMonster.contains(monster)) Damage *= 0.8;

                double DamageAfterShieldDecrease = Compute.PlayerShieldDecrease(player, Damage);

                if (DamageAfterShieldDecrease > 0) {
                    if (player.getHealth() / player.getMaxHealth() < 0.5) HurtEventModule.ManaSkill14(data, player);
                    player.setHealth((float) (player.getHealth() - DamageAfterShieldDecrease));
                    player.hurtTime = 10;
                    monster.heal((float) DamageAfterShieldDecrease * HealSteal);
                    if (player.getHealth() < DamageAfterShieldDecrease && player.isDeadOrDying()) {
                        Compute.FormatBroad(player.level(), Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                                Component.literal(player.getName().getString() + "在与").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(monster.getName()).
                                        append(Component.literal("的战斗中不幸重伤。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    }
                }
                ModNetworking.sendToClient(new SoundsS2CPacket(2),(ServerPlayer) player);
                ForestRuneAndLightningArmor(player,monster);
            }
        }
    }
    public static float MonsterExDamage (Mob monster, Player player) {
        float ExDamage = 0;
        Item monsterHelmet = monster.getItemBySlot(EquipmentSlot.HEAD).getItem();
        if (monsterHelmet.equals(Moditems.ArmorPlain.get())) {
            ExDamage += player.getMaxHealth() * 0.05;
        }
        return ExDamage;
    }
    public static float CritDamage (float CritRate, float CritDamage) {
        Random random = new Random();
        if (random.nextDouble(1) < CritRate) return 1 + CritDamage;
        return 1;
    }
    public static void ForestRuneAndLightningArmor (Player player, Mob monster) {
        CompoundTag data = player.getPersistentData();
        Item PlayerHelmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item PlayerChest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item PlayerLeggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item PlayerBoots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        int LightningArmorCount = 0;
        if (PlayerHelmet instanceof LightningArmorHelmet) LightningArmorCount ++;
        if (PlayerChest instanceof LightningArmorChest) LightningArmorCount ++;
        if (PlayerLeggings instanceof LightningArmorLeggings) LightningArmorCount ++;
        if (PlayerBoots instanceof LightningArmorBoots) LightningArmorCount ++;

        if(LightningArmorCount == 0 && player.getX() < 1523 && player.getX() > 1182 && player.getZ() < 633 && player.getZ() > 371) {
            player.getPersistentData().putBoolean("Lighting",true);
        }
        if(data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 1)
        {
            if(LightningArmorCount > 0)
            {
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,player.level());
                lightningBolt.setDamage(0);
                lightningBolt.setSilent(true);
                lightningBolt.setVisualOnly(true);
                lightningBolt.moveTo(monster.getPosition(1.0f));
                player.level().addFreshEntity(lightningBolt);
            }
            monster.hurt(monster.damageSources().playerAttack(player),(float)(player.getMaxHealth()*0.1+Compute.PlayerDefence(player) * 0.5 * LightningArmorCount));
            data.putInt("DGreen1",20);
        }
        else
        {
            if(LightningArmorCount > 0)
            {
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,player.level());
                lightningBolt.setDamage(0);
                lightningBolt.setSilent(true);
                lightningBolt.setVisualOnly(true);
                lightningBolt.moveTo(monster.getPosition(1.0f));
                player.level().addFreshEntity(lightningBolt);
                monster.hurt(monster.damageSources().playerAttack(player),(float)(Compute.PlayerDefence(player)*0.5*LightningArmorCount));
            }
            if(data.getInt(StringUtils.ForestRune.ForestRune) == 2 && player.getHealth() < player.getMaxHealth()*0.2 && data.contains(StringUtils.ForestRune.ForestRune))
            {
                if(data.contains(StringUtils.ForestRune.ForestRune2) && data.getInt(StringUtils.ForestRune.ForestRune2) == 0)
                {
                    player.setHealth(player.getMaxHealth());
                    data.putInt(StringUtils.ForestRune.ForestRune2,2400);
                    player.sendSystemMessage(Component.literal("森林符石-狂野生长 ").withStyle(ChatFormatting.DARK_GREEN).append(Component.literal("已为你恢复满生命值")));
                }
            }
        }
    }
}
