package com.Very.very.Events.MainEvents;

import com.Very.very.Projectile.BowTest.MyArrow;
import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.BowSoundParticle.BowShootS2CPacket;
import com.Very.very.NetWorking.Packets.BowSoundParticle.SkyBowShootS2CPacket;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BowEvent {
    @SubscribeEvent
    public static void Bow(ArrowLooseEvent event)
    {
        if(!event.getLevel().isClientSide)
        {
            Player player = event.getEntity();
            player.getPersistentData().putBoolean("IsAttack",false);
            Level level = event.getLevel();
            ModNetworking.sendToClient(new BowShootS2CPacket(true),(ServerPlayer) player);
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if(itemStack.is(Moditems.skybow.get()))
            {
                int time = event.getCharge();
                if(time >= 15) time = 15;
                float damage = Compute.PlayerAttackDamage(player);
                float CriticalHitRate = Compute.PlayerCriticalHitRate(player);
                float CHitDamage = Compute.PlayerCriticalHitDamage(player);
                float BreakDefence = Compute.PlayerBreakDefence(player);
                float ExpUp = Compute.ExpGetImprove(player);
                float BreakDefence0 = Compute.PlayerBreakDefence0(player);
                MyArrow arrow = new MyArrow(EntityType.ARROW,player,level,player,damage*((float) time /10),CriticalHitRate,CHitDamage,BreakDefence,ExpUp,BreakDefence0,player.getItemInHand(InteractionHand.MAIN_HAND),true);
                if(time == 15)
                {
                    arrow.setCritArrow(true);
                    ModNetworking.sendToClient(new SkyBowShootS2CPacket(true),(ServerPlayer) player);
                }
                player.getPersistentData().putBoolean("IsAttack",false);
                arrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0F,3.0F*((float) time /10),1.0F);
                level.addFreshEntity(arrow);
            }
            else
            {
                if(itemStack.is(Moditems.NetherBow.get()))
                {
                    int time = event.getCharge();
                    if(time >= 30) time = 30;
                    float damage = Compute.PlayerAttackDamage(player);
                    float CriticalHitRate = Compute.PlayerCriticalHitRate(player);
                    float CHitDamage = Compute.PlayerCriticalHitDamage(player);
                    float BreakDefence = Compute.PlayerBreakDefence(player);
                    float ExpUp = Compute.ExpGetImprove(player);
                    float BreakDefence0 = Compute.PlayerBreakDefence0(player);
                    CompoundTag data = player.getPersistentData();
                    MyArrow arrow = new MyArrow(EntityType.ARROW,player,level,player,damage*((float) time /20),CriticalHitRate,CHitDamage,BreakDefence,ExpUp,BreakDefence0,player.getItemInHand(InteractionHand.MAIN_HAND),true);
                    if(time == 30 && data.getInt("MANA") >= 40)
                    {
                        arrow.setCritArrow(true);
                        arrow.setNoGravity(true);
                        player.getPersistentData().putBoolean("IsAttack",false);
                        arrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0F,3.0F*((float) time /20),1.0F);
                        arrow.getPersistentData().putBoolean("IsNetherBow",true);
                        arrow.getPersistentData().putDouble("PosX",player.getX());
                        arrow.getPersistentData().putDouble("PosY",player.getY());
                        arrow.getPersistentData().putDouble("PosZ",player.getZ());
                        level.addFreshEntity(arrow);
                        data.putInt("MANA",data.getInt("MANA")-40);
                        Compute.ManaStatusUpdate(player);
                    }
                    else
                    {
                        player.getPersistentData().putBoolean("IsAttack",false);
                        arrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0F,3.0F*((float) time /20),1.0F);
                        level.addFreshEntity(arrow);
                    }
                }
                else
                {
                    int time = event.getCharge();
                    if(time >= 30) time = 30;
                    float damage = Compute.PlayerAttackDamage(player);
                    float CriticalHitRate = Compute.PlayerCriticalHitRate(player);
                    float CHitDamage = Compute.PlayerCriticalHitDamage(player);
                    float BreakDefence = Compute.PlayerBreakDefence(player);
                    float ExpUp = Compute.ExpGetImprove(player);
                    float BreakDefence0 = Compute.PlayerBreakDefence0(player);
                    MyArrow arrow = new MyArrow(EntityType.ARROW,player,level,player,damage*((float) time /20),CriticalHitRate,CHitDamage,BreakDefence,ExpUp,BreakDefence0,player.getItemInHand(InteractionHand.MAIN_HAND),true);
                    if(time == 30) arrow.setCritArrow(true);
                    player.getPersistentData().putBoolean("IsAttack",false);
                    arrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0F,3.0F*((float) time /20),1.0F);
                    level.addFreshEntity(arrow);
                }
            }
            event.setCanceled(true);
            player.getPersistentData().putBoolean("arrowflying",true);
        }
    }
}
