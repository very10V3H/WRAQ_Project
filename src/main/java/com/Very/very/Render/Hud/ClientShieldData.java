package com.Very.very.Render.Hud;

public class ClientShieldData {
    private static int Shield = 0;
    private static int ShieldValue = 0;
    public static void Set(int Shield)
    {
        ClientShieldData.Shield = Shield;
    }
    public static int Get()
    {
        return Shield;
    }
    public static void setValue(int ShieldValue)
    {
        ClientShieldData.ShieldValue = ShieldValue;
    }
    public static int getValue()
    {
        return ShieldValue;
    }

}
