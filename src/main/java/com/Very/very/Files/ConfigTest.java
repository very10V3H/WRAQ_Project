package com.Very.very.Files;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigTest {
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec.IntValue VALUE;
    public static ForgeConfigSpec.DoubleValue Security0;
    public static ForgeConfigSpec.DoubleValue Security1;
    public static ForgeConfigSpec.DoubleValue Security2;
    public static ForgeConfigSpec.DoubleValue Security3;
    public static ForgeConfigSpec.IntValue ForgingLightningArmorHelmet;
    public static ForgeConfigSpec.IntValue ForgingLightningArmorChest;
    public static ForgeConfigSpec.IntValue ForgingLightningArmorLeggings;
    public static ForgeConfigSpec.IntValue ForgingLightningArmorBoots;
    public static ForgeConfigSpec.IntValue SkyArmorHelmet;
    public static ForgeConfigSpec.IntValue SkyArmorChest;
    public static ForgeConfigSpec.IntValue SkyArmorLeggings;
    public static ForgeConfigSpec.IntValue SkyArmorBoots;
    public static ForgeConfigSpec.IntValue SeaSword;
    public static ForgeConfigSpec.IntValue BlackForestSword;
    public static ForgeConfigSpec.IntValue NetherArmorHelmet;
    public static ForgeConfigSpec.IntValue NetherArmorChest;
    public static ForgeConfigSpec.IntValue NetherArmorLeggings;
    public static ForgeConfigSpec.IntValue NetherArmorBoots;
    public static ForgeConfigSpec.IntValue KazeSword;
    public static ForgeConfigSpec.IntValue KazeBoots;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.comment("General settings").push("general");
        VALUE = COMMON_BUILDER.comment("Test config value").defineInRange("value",10,0,Integer.MAX_VALUE);

        //COMMON_BUILDER.comment("General settings0").push("sec0");
        Security0 = COMMON_BUILDER.comment("Security0").defineInRange("value0",64,0,Double.MAX_VALUE);

        //COMMON_BUILDER.comment("General settings1").push("sec1");
        Security1 = COMMON_BUILDER.comment("Security1").defineInRange("value1",64,0,Double.MAX_VALUE);

        //COMMON_BUILDER.comment("General settings2").push("sec2");
        Security2 = COMMON_BUILDER.comment("Security2").defineInRange("value2",64,0,Double.MAX_VALUE);

        //COMMON_BUILDER.comment("General settings3").push("sec3");
        Security3 = COMMON_BUILDER.comment("Security3").defineInRange("value3",64,0,Double.MAX_VALUE);

        ForgingLightningArmorHelmet = COMMON_BUILDER.comment("LightningArmorHelmet").defineInRange("value4",1,0,Integer.MAX_VALUE);

        ForgingLightningArmorChest = COMMON_BUILDER.comment("LightningArmorChest").defineInRange("value5",1,0,Integer.MAX_VALUE);

        ForgingLightningArmorLeggings = COMMON_BUILDER.comment("LightningArmorLeggings").defineInRange("value6",1,0,Integer.MAX_VALUE);

        ForgingLightningArmorBoots = COMMON_BUILDER.comment("LightningArmorBoots").defineInRange("value7",1,0,Integer.MAX_VALUE);

        SkyArmorHelmet = COMMON_BUILDER.comment("SkyArmorHelmet").defineInRange("value8",1,0,Integer.MAX_VALUE);

        SkyArmorChest = COMMON_BUILDER.comment("SkyArmorChest").defineInRange("value9",1,0,Integer.MAX_VALUE);

        SkyArmorLeggings = COMMON_BUILDER.comment("SkyArmorLeggings").defineInRange("value10",1,0,Integer.MAX_VALUE);

        SkyArmorBoots = COMMON_BUILDER.comment("SkyArmorBoots").defineInRange("value11",1,0,Integer.MAX_VALUE);

        SeaSword = COMMON_BUILDER.comment("SeaSword").defineInRange("value12",1,0,Integer.MAX_VALUE);

        BlackForestSword = COMMON_BUILDER.comment("BlackForestSword").defineInRange("value13",1,0,Integer.MAX_VALUE);

        NetherArmorHelmet = COMMON_BUILDER.comment("NetherArmorHelmet").defineInRange("value14",1,0,Integer.MAX_VALUE);

        NetherArmorChest = COMMON_BUILDER.comment("NetherArmorChest").defineInRange("value15",1,0,Integer.MAX_VALUE);

        NetherArmorLeggings = COMMON_BUILDER.comment("NetherArmorLeggings").defineInRange("value16",1,0,Integer.MAX_VALUE);

        NetherArmorBoots = COMMON_BUILDER.comment("NetherArmorBoots").defineInRange("value17",1,0,Integer.MAX_VALUE);

        KazeSword = COMMON_BUILDER.comment("KazeSword").defineInRange("value18",1,0,Integer.MAX_VALUE);

        KazeBoots = COMMON_BUILDER.comment("KazeBoots").defineInRange("value19",1,0,Integer.MAX_VALUE);
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

}
