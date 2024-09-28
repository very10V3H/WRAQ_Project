package fun.wraq.render.hud;

public class ClientShieldData {
    private static int Shield = 0;
    private static int ShieldValue = 0;

    public static void set(int Shield) {
        ClientShieldData.Shield = Shield;
    }

    public static int get() {
        return Shield;
    }

    public static void setValue(int ShieldValue) {
        ClientShieldData.ShieldValue = ShieldValue;
    }

    public static int getValue() {
        return ShieldValue;
    }

}
