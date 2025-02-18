package fun.wraq.process.system.randomevent.impl.killmob;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.system.randomevent.RandomAdditionalRewardEvent;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlimeKingEvent extends KillMobEvent {

    // 莱姆king免疫一切直接伤害 每过5s会在其周围生成小史莱姆，击杀小史莱姆将扣除其1点生命值。

    private Slime slimeKing;
    private List<Mob> smallSlimeList = new ArrayList<>();
    public SlimeKingEvent(ResourceKey<Level> dimension, Vec3 pos, List<Component> readyAnnouncement,
                          List<Component> beginAnnouncement, List<Component> endAnnouncement,
                          List<Component> overTimeAnnouncement, MinecraftServer server, List<ItemAndRate> rewardList,
                          RandomAdditionalRewardEvent randomAdditionalRewardEvent) {
        super(dimension, pos, readyAnnouncement, beginAnnouncement, endAnnouncement, overTimeAnnouncement,
                server, rewardList, randomAdditionalRewardEvent);
    }

    @Override
    protected void summonAndSetMobList() {
        Slime slime = new Slime(EntityType.SLIME, level());
        slime.setSize(6, false);
        MobSpawn.setMobCustomName(slime, Te.s("莱姆king", CustomStyle.styleOfLife), 40);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(slime, 40, 100, 20, 20,
                0, 0, 0, 0, 0, 100, 0.2);

        slimeKing = slime;
        mobList.add(slime);
        slime.moveTo(pos);
        level().addFreshEntity(slime);
    }

    @Override
    protected void tick() {
        if (slimeKing != null && slimeKing.isAlive()) {
            smallSlimeList.removeIf(LivingEntity::isDeadOrDying);
            if (Tick.get() % 20 == 0 && smallSlimeList.size() < 2) {
                Random random = new Random();
                for (int i = 0 ; i < 8 ; i ++) {
                    Slime smallSlime = setSmallSlimeAttributes();
                    smallSlimeList.add(smallSlime);
                    smallSlime.moveTo(slimeKing.position()
                            .add(3 - random.nextDouble(6), 1, 3 - random.nextDouble(6)));
                    level().addFreshEntity(smallSlime);
                }
            }
        }
        super.tick();
    }

    private Slime setSmallSlimeAttributes() {
        Slime smallSlime = new Slime(EntityType.SLIME, level());
        smallSlime.setSize(1, false);
        MobSpawn.setMobCustomName(smallSlime, Te.s("小史莱姆", CustomStyle.styleOfLife), 40);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(smallSlime, 40, 100, 20, 20,
                0, 0, 0, 0, 0, 10000, 0.2);
        return smallSlime;
    }

    @Override
    public void onKillMob(Player player, Mob mob) {
        if (slimeKing != null && slimeKing.isAlive() && MobSpawn.getMobOriginName(mob).equals("小史莱姆")) {
            slimeKing.setHealth(slimeKing.getHealth() - 1);
            players.add(player);
        }
        super.onKillMob(player, mob);
    }

    public static boolean isSlimeKing(Mob mob) {
        return MobSpawn.getMobOriginName(mob).equals("莱姆king") && ((Slime) mob).getSize() == 6;
    }

    @Override
    protected void finishAction() {
        smallSlimeList.clear();
    }
}
