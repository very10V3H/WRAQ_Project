package fun.wraq.items.prefix;

public class PrefixInfo {
    private String prefix;
    private String color;
    private int level;

    public PrefixInfo(String prefix, String color, int level) {
        this.prefix = prefix;
        this.color = color;
        this.level = level;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getLevel() {
        return level;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

