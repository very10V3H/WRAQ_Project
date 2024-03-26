package com.Very.very.Items.Prefix;

public class PrefixInfo {
    private String Prefix;
    private int level;
    public PrefixInfo(String prefix, int level) {
        this.Prefix =  prefix;
        this.level = level;
    }


    public String getPrefix() {
        return Prefix;
    }

    public int getLevel() {
        return level;
    }


    public void setPrefix(String prefix) {
        Prefix = prefix;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
