package fun.wraq.process.system.randomevent.impl.killmob.multi;

import fun.wraq.process.system.randomevent.impl.killmob.KillMobEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public abstract class MultiMobEvent extends KillMobEvent {

    private int leftMobCount;
    private final List<Vec3> summonPosList;

    private final Random random = new Random();

    public MultiMobEvent(ResourceKey<Level> dimension, Vec3 pos, List<Component> beginAnnouncement,
                         MinecraftServer server, List<Vec3> summonPosList) {
        super(dimension, pos, beginAnnouncement, server);
        this.summonPosList = summonPosList;
    }

    @Override
    protected void summonAndSetMobList() {
        for (int i = 0 ; i < 10 ; i ++) {
            Mob mob = setMobAttributesAndEquip();
            mob.moveTo(summonPosList.get(random.nextInt(summonPosList.size())));
            mobList.add(mob);
        }
    }

    @Override
    protected void tick() {
        while (leftMobCount > 0 && mobList.stream().filter(LivingEntity::isAlive).count() < 5) {
            --leftMobCount;
            Mob mob = setMobAttributesAndEquip();
            mob.moveTo(summonPosList.get(random.nextInt(summonPosList.size())));
            mobList.add(mob);
        }
        super.tick();
    }

    @Override
    protected boolean endCondition() {
        if (leftMobCount > 0) return false;
        return super.endCondition();
    }

    protected abstract Mob setMobAttributesAndEquip();
    protected abstract int getTotalMobCount();

    @Override
    protected void beginAction() {
        leftMobCount = getTotalMobCount();
        super.beginAction();
    }
}