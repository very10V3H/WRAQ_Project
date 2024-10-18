package fun.wraq.common.util.struct;

public class HudIcon {

    public int lastTick;
    public int maxTick;
    public String url;
    public int level;
    public boolean forever;

    public HudIcon(String url, int lastTick, int maxTick, int level) {
        this(url, lastTick, maxTick, level, false);
    }

    public HudIcon(String url, int lastTick, int maxTick, int level, boolean forever) {
        this.url = url;
        this.lastTick = lastTick;
        this.maxTick = maxTick;
        this.level = level;
        this.forever = forever;
    }
}
