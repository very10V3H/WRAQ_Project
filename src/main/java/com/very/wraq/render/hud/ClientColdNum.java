package com.very.wraq.render.hud;

public class ClientColdNum {

    private static double MaxCold;
    private static double CurrentCold;

    public static void setMaxCold(double mana) {
        ClientColdNum.MaxCold = mana;
    }

    public static double getMaxCold() {
        return MaxCold;
    }

    public static void setCurrentCold(double Value) {
        ClientColdNum.CurrentCold = Value;
    }

    public static double getCurrentCold() {
        return CurrentCold;
    }

}
