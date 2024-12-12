package fun.wraq.process.system.randomevent.impl.killmob.multi;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class VillageAttack extends MultiMobEvent {

    private final Random random = new Random();

    private final String mobName1;
    private final String mobName2;
    private final List<ItemAndRate> eachMobDropList;
    private final List<Vec3> summonPosList;

    public VillageAttack(ResourceKey<Level> dimension, Vec3 pos, List<Component> readyAnnouncement,
                         List<Component> beginAnnouncement, List<Component> endAnnouncement,
                         List<Component> overTimeAnnouncement, MinecraftServer server,
                         String mobName1, String mobName2, List<ItemAndRate> eachMobDropList,
                         List<Vec3> summonPosList, List<ItemAndRate> rewardList) {
        super(dimension, pos, readyAnnouncement, beginAnnouncement, endAnnouncement, overTimeAnnouncement,
                server, summonPosList, rewardList, (player -> {
                    Compute.playerReputationAddOrCost(player, 20);
                }));
        this.mobName1 = mobName1;
        this.mobName2 = mobName2;
        this.eachMobDropList = eachMobDropList;
        this.summonPosList = summonPosList;
    }

    @Override
    protected void summonAndSetMobList() {
        for (int i = 0 ; i < 10 ; i ++) {
            Mob mob = setMobAttributesAndEquip();
            mobList.add(mob);
            mob.moveTo(summonPosList.get(random.nextInt(summonPosList.size())));
            level().addFreshEntity(mob);
        }
    }

    protected Mob setMobAttributesAndEquip() {
        Mob mob;
        if (random.nextBoolean()) {
            mob = new Pillager(EntityType.PILLAGER, level());
            MobSpawn.setMobCustomName(mob, Te.s(mobName1, CustomStyle.styleOfStone), 20);
            MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, 20, 50, 10,
                    10, 0, 0, 5, 5, 0,
                    1000, 0.1);
            mob.setItemInHand(InteractionHand.MAIN_HAND, Items.CROSSBOW.getDefaultInstance());
            MobSpawn.setMobDropList(mob, eachMobDropList);
        } else {
            mob = new Vindicator(EntityType.VINDICATOR, level());
            MobSpawn.setMobCustomName(mob, Te.s(mobName2, CustomStyle.styleOfStone), 20);
            MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, 20, 75, 15,
                    15, 0.5, 1, 10, 10, 0,
                    1500, 0.2);
            mob.setItemInHand(InteractionHand.MAIN_HAND, Items.IRON_AXE.getDefaultInstance());
            MobSpawn.setMobDropList(mob, eachMobDropList);
        }
        mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 8888));
        return mob;
    }

    @Override
    protected int getTotalMobCount() {
        return 20;
    }
}
