public class Father {
    public int num;
    public Father(int num) {
        this.num = num;
    }

    public static String test() {
        return Thread.currentThread().getStackTrace()[1].getClassName();
    }

    public static boolean isOn(Class<? extends Father> clazz, Father father) {
        return father.getClass().getName().equals(clazz.getName());
    }
}
