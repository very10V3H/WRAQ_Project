package com.Very.very.Events.MainEvents;

import com.Very.very.Customize.Players.shangmengli.ShangMengLiSword;
import com.Very.very.Projectile.Mana.ShangMengLiSwordAir;
import com.Very.very.Projectile.Mana.SwordAir;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class BowEvent {
    @SubscribeEvent
    public static void Projectile(ProjectileImpactEvent event) {
        Entity entity = event.getProjectile();
        Level level = event.getEntity().level();
        List<Entity> list = level.getEntitiesOfClass(Entity.class,entity.getBoundingBox().expandTowards(entity.getDeltaMovement()).inflate(1.0D));
        if (list.size() > 0 && list.get(0) instanceof ArmorStand) event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
        if (entity instanceof SwordAir) {
            entity.setDeltaMovement(0, 0, 0);
            event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
        }
        if (entity instanceof ShangMengLiSwordAir shangMengLiSwordAir) {
            ShangMengLiSword.OnHitEntity(list, shangMengLiSwordAir);
            event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
        }
    }

    @SubscribeEvent
    public static void Bow(ArrowLooseEvent event)
    {
        if(!event.getLevel().isClientSide)
        {
            event.setCanceled(true);
/*            Player player = event.getEntity();
            
            Level level = event.getLevel();
            ModNetworking.sendToClient(new BowShootS2CPacket(true),(ServerPlayer) player);
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if(itemStack.is(ModItems.SkyBow.get()))
            {
                int time = event.getCharge();
                if(time >= 15) time = 15;
                float damage = Compute.PlayerAttributes.PlayerAttackDamage(player);
                double CriticalHitRate = Compute.PlayerAttributes.PlayerCriticalHitRate(player);
                double CHitDamage = Compute.PlayerAttributes.PlayerCriticalHitDamage(player);
                double BreakDefence = Compute.PlayerAttributes.PlayerBreakDefence(player);
                float ExpUp = Compute.ExpGetImprove(player);
                double BreakDefence0 = Compute.PlayerAttributes.PlayerBreakDefence0(player);
                MyArrow arrow = new MyArrow(EntityType.ARROW,player,level,player,damage*((float) time /10),CriticalHitRate,CHitDamage,BreakDefence,ExpUp,BreakDefence0,player.getItemInHand(InteractionHand.MAIN_HAND),true);
                if(time == 15)
                {
                    arrow.setCritArrow(true);
                    ModNetworking.sendToClient(new SkyBowShootS2CPacket(true),(ServerPlayer) player);
                }
                
                arrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0d,3.0d*((float) time /10),1.0d);
                level.addFreshEntity(arrow);
            }
            else
            {
                if(itemStack.is(ModItems.NetherBow.get()))
                {
                    int time = event.getCharge();
                    if(time >= 30) time = 30;
                    float damage = Compute.PlayerAttributes.PlayerAttackDamage(player);
                    double CriticalHitRate = Compute.PlayerAttributes.PlayerCriticalHitRate(player);
                    double CHitDamage = Compute.PlayerAttributes.PlayerCriticalHitDamage(player);
                    double BreakDefence = Compute.PlayerAttributes.PlayerBreakDefence(player);
                    float ExpUp = Compute.ExpGetImprove(player);
                    double BreakDefence0 = Compute.PlayerAttributes.PlayerBreakDefence0(player);
                    CompoundTag data = player.getPersistentData();
                    MyArrow arrow = new MyArrow(EntityType.ARROW,player,level,player,damage*((float) time /20),CriticalHitRate,CHitDamage,BreakDefence,ExpUp,BreakDefence0,player.getItemInHand(InteractionHand.MAIN_HAND),true);
                    if(time == 30 && data.getInt("MANA") >= 40)
                    {
                        arrow.setCritArrow(true);
                        arrow.setNoGravity(true);
                        
                        arrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0d,3.0d*((float) time /20),1.0d);
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
                        
                        arrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0d,3.0d*((float) time /20),1.0d);
                        level.addFreshEntity(arrow);
                    }
                }
                else
                {
                    int time = event.getCharge();
                    if(time >= 30) time = 30;
                    float damage = Compute.PlayerAttributes.PlayerAttackDamage(player);
                    double CriticalHitRate = Compute.PlayerAttributes.PlayerCriticalHitRate(player);
                    double CHitDamage = Compute.PlayerAttributes.PlayerCriticalHitDamage(player);
                    double BreakDefence = Compute.PlayerAttributes.PlayerBreakDefence(player);
                    float ExpUp = Compute.ExpGetImprove(player);
                    double BreakDefence0 = Compute.PlayerAttributes.PlayerBreakDefence0(player);
                    MyArrow arrow = new MyArrow(EntityType.ARROW,player,level,player,damage*((float) time /20),CriticalHitRate,CHitDamage,BreakDefence,ExpUp,BreakDefence0,player.getItemInHand(InteractionHand.MAIN_HAND),true);
                    if(time == 30) arrow.setCritArrow(true);
                    
                    arrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0d,3.0d*((float) time /20),1.0d);
                    level.addFreshEntity(arrow);
                }
            }
            event.setCanceled(true);
            player.getPersistentData().putBoolean("arrowflying",true);*/
        }
    }
}
