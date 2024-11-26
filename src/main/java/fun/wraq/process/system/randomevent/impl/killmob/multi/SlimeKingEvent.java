package fun.wraq.process.system.randomevent.impl.killmob.multi;

import fun.wraq.common.fast.Te;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.system.randomevent.impl.killmob.KillMobEvent;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class SlimeKingEvent extends KillMobEvent {

    // 莱姆king免疫一切直接伤害 每过5s会在其周围生成小史莱姆，击杀小史莱姆将扣除其1点生命值。

    private Slime slimeKing;

    public SlimeKingEvent(ResourceKey<Level> dimension, Vec3 pos, List<Component> beginAnnouncement, MinecraftServer server) {
        super(dimension, pos, beginAnnouncement, server);
    }

    @Override
    protected void summonAndSetMobList() {
        Slime slime = new Slime(EntityType.SLIME, level);
        slime.setSize(6, false);
        MobSpawn.setMobCustomName(slime, Te.s("莱姆king", CustomStyle.styleOfLife), 40);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(slime, 40, 100, 20, 20,
                0, 0, 0, 0, 0, 100, 0.2);
        slimeKing = slime;
        mobList.add(slime);
        slime.moveTo(pos);
        level.addFreshEntity(slime);
    }

    @Override
    protected void tick() {
        if (slimeKing != null && slimeKing.isAlive()) {
            Random random = new Random();
            for (int i = 0 ; i < 8 ; i ++) {
                Slime smallSlime = setSmallSlimeAttributes();
                smallSlime.moveTo(slimeKing.position().add(4 - random.nextDouble(2), 1, 4 - random.nextDouble(2)));
                level.addFreshEntity(smallSlime);
            }
        }
        super.tick();
    }

    private Slime setSmallSlimeAttributes() {
        Slime smallSlime = new Slime(EntityType.SLIME, level);
        smallSlime.setSize(1, false);
        MobSpawn.setMobCustomName(smallSlime, Te.s("小史莱姆", CustomStyle.styleOfLife), 40);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(smallSlime, 40, 100, 20, 20,
                0, 0, 0, 0, 0, 10000, 0.2);
        return smallSlime;
    }

    @Override
    public void onKillMob(Player player, Mob mob) {
        if (slimeKing.isAlive() && MobSpawn.getMobOriginName(mob).equals("小史莱姆")) {
            slimeKing.setHealth(slimeKing.getHealth() - 1);
        }
        super.onKillMob(player, mob);
    }

    public static boolean isSlimeKing(Mob mob) {
        return MobSpawn.getMobOriginName(mob).equals("莱姆king");
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
