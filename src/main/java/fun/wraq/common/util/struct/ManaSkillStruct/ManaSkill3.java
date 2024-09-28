package fun.wraq.common.util.struct.ManaSkillStruct;


import net.minecraft.world.entity.Entity;

public class ManaSkill3 {
    private Entity entity;
    private int count;
    private int time;

    public ManaSkill3(Entity entity, int count, int time) {
        this.entity = entity;
        this.count = count;
        this.time = time;
    }

    public Entity getEntity() {
        return entity;
    }

    public int getTime() {
        return time;
    }

    public int getCount() {
        return count;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
