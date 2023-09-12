package com.Very.very.Events.MainEvents;

import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Armor.LakeArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Armor.LakeArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Armor.LakeArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Armor.LakeArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Armor.ForestArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Armor.ForestArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Armor.ForestArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Armor.ForestArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Armor.PlainArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Armor.PlainArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Armor.PlainArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Armor.PlainArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Armor.VolcanoArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Armor.VolcanoArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Armor.VolcanoArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Armor.VolcanoArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.LifeMana.LifeManaArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.LifeMana.LifeManaArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.LifeMana.LifeManaArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.LifeMana.LifeManaArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.ObsiMana.ObsiManaArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.ObsiMana.ObsiManaArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.ObsiMana.ObsiManaArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.ObsiMana.ObsiManaArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.Armor.SkyArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.Armor.SkyArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.Armor.SkyArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.Armor.SkyArmorLeggings;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.Render.Effects.ModEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EffectEvent {
    @SubscribeEvent
    public static void Effects(TickEvent.PlayerTickEvent event)
    {
        if(event.side.isServer() && event.phase.equals(TickEvent.Phase.START))
        {
            Player player = event.player;
            if(player.tickCount % 400 == 0)
            {
                CompoundTag data = player.getPersistentData();
                if((data.contains("SeaSword3") && data.getBoolean("SeaSword3"))
                        || (data.contains("SeaSword0") && data.getBoolean("SeaSword0")) ) {
                    player.removeEffect(ModEffects.SEASWORD.get());
                    player.addEffect(new MobEffectInstance(ModEffects.SEASWORD.get(),400));
                }
                if((data.contains("BlackForestSword0") && data.getBoolean("BlackForestSword0"))
                        || (data.contains("BlackForestSword3") && data.getBoolean("BlackForestSword3"))){
                    player.removeEffect(ModEffects.BLACKFORESTSWORD.get());
                    player.addEffect(new MobEffectInstance(ModEffects.BLACKFORESTSWORD.get(),400));
                }
            }
            if(player.tickCount % 20 == 0)
            {
                Item head = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
                Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
                Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
                Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
                if(Utils.MobEffectMap.isEmpty()) Utils.MobEffectMapInit();
                if(head instanceof ForestArmorHelmet && chest instanceof ForestArmorChest && leggings instanceof ForestArmorLeggings && boots instanceof ForestArmorBoots){
                    player.addEffect(new MobEffectInstance(ModEffects.FORESTARMOR.get(),400));
                    for(MobEffect key : Utils.MobEffectMap.keySet()){
                        if(key!=ModEffects.FORESTARMOR.get())
                            player.removeEffect(key);
                    }
                }
                if(head instanceof PlainArmorHelmet && chest instanceof PlainArmorChest && leggings instanceof PlainArmorLeggings && boots instanceof PlainArmorBoots){
                    player.addEffect(new MobEffectInstance(ModEffects.PLAINARMOR.get(),400));
                    for(MobEffect key : Utils.MobEffectMap.keySet()){
                        if(key!=ModEffects.PLAINARMOR.get())
                            player.removeEffect(key);
                    }
                }
                if(head instanceof LakeArmorHelmet && chest instanceof LakeArmorChest && leggings instanceof LakeArmorLeggings && boots instanceof LakeArmorBoots){
                    player.addEffect(new MobEffectInstance(ModEffects.LAKEARMOR.get(),400));
                    for(MobEffect key : Utils.MobEffectMap.keySet()){
                        if(key!=ModEffects.LAKEARMOR.get())
                            player.removeEffect(key);
                    }
                }
                if(head instanceof VolcanoArmorHelmet && chest instanceof VolcanoArmorChest && leggings instanceof VolcanoArmorLeggings && boots instanceof VolcanoArmorBoots){
                    player.addEffect(new MobEffectInstance(ModEffects.VOLCANOARMOR.get(),400));
                    for(MobEffect key : Utils.MobEffectMap.keySet()){
                        if(key!=ModEffects.VOLCANOARMOR.get())
                            player.removeEffect(key);
                    }
                }
                if(boots instanceof LifeManaArmorBoots && leggings instanceof LifeManaArmorLeggings
                        && chest instanceof LifeManaArmorChest && head instanceof LifeManaArmorHelmet) {
                    player.addEffect(new MobEffectInstance(ModEffects.LIFEMANA.get(),400));
                    for(MobEffect key : Utils.MobEffectMap.keySet()){
                        if(key!=ModEffects.LIFEMANA.get())
                            player.removeEffect(key);
                    }
                }
                if(boots instanceof ObsiManaArmorBoots && leggings instanceof ObsiManaArmorLeggings
                        && chest instanceof ObsiManaArmorChest && head instanceof ObsiManaArmorHelmet) {
                    player.addEffect(new MobEffectInstance(ModEffects.OBSIMANA.get(),400));
                    for(MobEffect key : Utils.MobEffectMap.keySet()){
                        if(key!=ModEffects.OBSIMANA.get())
                            player.removeEffect(key);
                    }
                }
                if(boots instanceof SkyArmorBoots && leggings instanceof SkyArmorLeggings
                        && chest instanceof SkyArmorChest && head instanceof SkyArmorHelmet) {
                    player.addEffect(new MobEffectInstance(ModEffects.SKYARMOR.get(),400));
                    for(MobEffect key : Utils.MobEffectMap.keySet()){
                        if(key!=ModEffects.SKYARMOR.get())
                            player.removeEffect(key);
                    }
                }
                if(boots instanceof LightningArmorBoots && leggings instanceof LightningArmorLeggings
                        && chest instanceof LightningArmorChest && head instanceof LightningArmorHelmet) {
                    player.addEffect(new MobEffectInstance(ModEffects.LIGHTNINGARMOR.get(),400));
                    for(MobEffect key : Utils.MobEffectMap.keySet()){
                        if(key!=ModEffects.LIGHTNINGARMOR.get())
                            player.removeEffect(key);
                    }
                }
            }
        }
    }
}
