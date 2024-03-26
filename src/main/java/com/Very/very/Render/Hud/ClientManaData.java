package com.Very.very.Render.Hud;

public class ClientManaData {

    private static int MaxMana;
    private static int CurrentMana;

    public static void setMaxMana(int mana)
    {
        ClientManaData.MaxMana = mana;
    }

    public static int getMaxMana()
    {
        return MaxMana;
    }

    public static void setCurrentMana(int Value)
    {
        ClientManaData.CurrentMana = Value;
    }

    public static int getCurrentMana()
    {
        return CurrentMana;
    }

}
