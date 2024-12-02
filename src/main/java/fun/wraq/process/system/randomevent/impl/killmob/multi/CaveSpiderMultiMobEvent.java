package fun.wraq.process.system.randomevent.impl.killmob.multi;

import fun.wraq.common.fast.Te;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CaveSpiderMultiMobEvent extends MultiMobEvent {
    public CaveSpiderMultiMobEvent(ResourceKey<Level> dimension, Vec3 pos, List<Component> beginAnnouncement,
                                   List<Component> endAnnouncement, List<Component> overTimeAnnouncement, MinecraftServer server, List<Vec3> summonPosList) {
        super(dimension, pos, beginAnnouncement, endAnnouncement, overTimeAnnouncement, server, summonPosList);
    }

    @Override
    protected Mob setMobAttributesAndEquip() {
        CaveSpider caveSpider = new CaveSpider(EntityType.CAVE_SPIDER, level());
        MobSpawn.setMobCustomName(caveSpider, Te.s("洞穴蜘蛛", CustomStyle.styleOfStone), 20);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(caveSpider, 20, 50, 10,
                10, 0, 0, 5, 5, 0,
                1000, 0.2);
        MobSpawn.setMobDropList(caveSpider, List.of(

        ));
        return caveSpider;
    }

    @Override
    protected int getTotalMobCount() {
        return 50;
    }

    @Override
    protected List<ItemStack> getRewardList() {
        return List.of(

        );
    }

    @Override
    protected void additionReward(Player player) {

    }
}
