package com.very.wraq.render.hud;

public class ClientManaData {

    private static double MaxMana;
    private static double CurrentMana;

    public static void setMaxMana(double mana) {
        ClientManaData.MaxMana = mana;
    }

    public static double getMaxMana() {
        return MaxMana;
    }

    public static void setCurrentMana(double Value) {
        ClientManaData.CurrentMana = Value;
    }

    public static double getCurrentMana() {
        return CurrentMana;
    }

}
