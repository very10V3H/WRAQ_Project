package fun.wraq.series.overworld.newarea.stone;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.jungle.JungleMobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.newarea.NewAreaItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class StoneSpiderKingSpawnController extends JungleMobSpawnController {

    public static final String MOB_NAME = "石岸蜘蛛皇";
    public static final Style STYLE = CustomStyle.styleOfStone;
    public static final int XP_LEVEL = 300;

    public static StoneSpiderKingSpawnController instance;

    public static StoneSpiderKingSpawnController getInstance() {
        if (instance == null) {
            instance = new StoneSpiderKingSpawnController(Te.s(MOB_NAME, STYLE),
                    new Vec3(903, 101, 2378),
                    new Vec3(916, 110, 2388),
                    new Vec3(888, 64, 2358), XP_LEVEL, 48, Tick.min(3));
        }
        return instance;
    }

    public StoneSpiderKingSpawnController(Component name, Vec3 descriptionPos,
                                          Vec3 mobUpperBoundary, Vec3 mobLowerBoundary,
                                          int mobXpLevel, double detectionRadius, int refreshInterval) {
        super(name, descriptionPos, mobUpperBoundary, mobLowerBoundary, mobXpLevel, detectionRadius, refreshInterval);
    }

    @Override
    public void tryToReward(Player player) {
        getRewardItemList().forEach(itemAndRate -> {
            itemAndRate.sendWithMSG(player, 1);
        });
    }

    @Override
    public void spawnMob(Level level) {
        Spider mob = new Spider(EntityType.SPIDER, level);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), XP_LEVEL);
        MobSpawn.setMobCustomName(mob, Te.s(name, STYLE), XP_LEVEL);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, 200, 35, 35,
                0.3, 2, 0.1, 3, 10,
                4500, 0.3);
        mob.moveTo(new Vec3(903, 101, 2378));
        level.addFreshEntity(mob);
        mobs.add(mob);
    }

    @Override
    public void handleMobTick(Mob mob) {
        Element.provideElement(mob, Element.stone, 8);
        super.handleMobTick(mob);
    }

    @Override
    public List<ItemAndRate> getRewardItemList() {
        return List.of(
                new ItemAndRate(NewAreaItems.STONE_SPIDER_PIECE.get(), 1),
                new ItemAndRate(NewAreaItems.STONE_SPIDER_KNIFE_0.get(), 0.01),
                new ItemAndRate(NewAreaItems.STONE_SPIDER_GEM_ATTACK_0.get(), 0.01),
                new ItemAndRate(NewAreaItems.STONE_SPIDER_GEM_MANA_0.get(), 0.01),
                new ItemAndRate(ModItems.GOLD_COIN.get(), 1),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 1),
                new ItemAndRate(ModItems.STONE_ELEMENT_PIECE_1.get(), 0.1)
        );
    }
}
