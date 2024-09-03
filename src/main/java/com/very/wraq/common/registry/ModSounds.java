package com.very.wraq.common.registry;

import com.very.wraq.common.util.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    private static final DeferredRegister<SoundEvent> SOUND = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Utils.MOD_ID);

    public static final RegistryObject<SoundEvent> Attack = registryObject("attack");
    public static final RegistryObject<SoundEvent> Lava = registryObject("lava");
    public static final RegistryObject<SoundEvent> Mana_Sword = registryObject("mana_sword");
    public static final RegistryObject<SoundEvent> Nether_Power = registryObject("nether_power");
    public static final RegistryObject<SoundEvent> Mana = registryObject("mana");
    public static final RegistryObject<SoundEvent> Use = registryObject("use");
    public static final RegistryObject<SoundEvent> Healing = registryObject("healing");
    public static final RegistryObject<SoundEvent> Rolling = registryObject("rolling");
    public static final RegistryObject<SoundEvent> Wind = registryObject("wind");
    public static final RegistryObject<SoundEvent> DingZhen = registryObject("dingzhen");
    public static final RegistryObject<SoundEvent> YueMa = registryObject("yuema");
    public static final RegistryObject<SoundEvent> IGotSmoke = registryObject("i_got_smoke");
    public static final RegistryObject<SoundEvent> MSG = registryObject("msg");
    public static final RegistryObject<SoundEvent> HeihuangAttack = registryObject("heihuang_attack");
    public static final RegistryObject<SoundEvent> Dice = registryObject("dice");
    public static final RegistryObject<SoundEvent> BreakDice = registryObject("break_dice");


    public static void register(IEventBus eventBus) {
        SOUND.register(eventBus);
    }

    public static RegistryObject<SoundEvent> registryObject(String name) {
        return SOUND.register(name, () ->
                SoundEvent.createVariableRangeEvent(new ResourceLocation(Utils.MOD_ID, name)));
    }
}
