package com.very.wraq.render.hud;

public class ClientSwiftData {

    private static double MaxSwift;
    private static double CurrentSwift;

    public static void setMaxSwift(double mana) {
        ClientSwiftData.MaxSwift = mana;
    }

    public static double getMaxSwift() {
        return MaxSwift;
    }

    public static void setCurrentSwift(double Value) {
        ClientSwiftData.CurrentSwift = Value;
    }

    public static double getCurrentSwift() {
        return CurrentSwift;
    }

}
