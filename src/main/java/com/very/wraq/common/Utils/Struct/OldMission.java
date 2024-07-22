package com.very.wraq.common.Utils.Struct;

import net.minecraft.world.phys.Vec3;

public class OldMission {
    private String Title;
    private String Content;
    private Vec3 Des;

    public OldMission(String Title, String Content, Vec3 Des) {
        this.Title = Title;
        this.Content = Content;
        this.Des = Des;
    }

    public String getContent() {
        return Content;
    }

    public String getTitle() {
        return Title;
    }

    public Vec3 getDes() {
        return Des;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setDes(Vec3 des) {
        Des = des;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
