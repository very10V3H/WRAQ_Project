package com.Very.very.ValueAndTools;

import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class HAttribute {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Utils.MOD_ID);
    public static final RegistryObject<Attribute> MAXHEALTH = ATTRIBUTES.register("maxhealth",() -> (Attribute) (new RangedAttribute("attribute.name.generic.max_health", 20.0D, 1.0D, 38400.0D)).setSyncable(true));
}
