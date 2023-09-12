package com.Very.very.Render.Hud;

public class ClientManaData {
    private static int playerMana;
    private static int playerManaValue;
    public static void set(int mana)
    {
        ClientManaData.playerMana = mana;
    }
    public static int get()
    {
        return playerMana;
    }
    public static void setValue(int Value)
    {
        ClientManaData.playerManaValue = Value;
    }
    public static int getValue()
    {
        return playerManaValue;
    }
}
