package fun.wraq.series.overworld.cold.sc2.stone;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.common.util.struct.BlockAndResetTime;
import fun.wraq.events.fight.MonsterAttackEvent;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.newarea.NewAreaItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Random;

public class StoneSpiderSpawnController extends MobSpawnController {

    public static String mobName = "石岸蜘蛛";
    public static Style style = CustomStyle.styleOfStone;
    private static StoneSpiderSpawnController instance;

    public static StoneSpiderSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2236, 104, -3200),
                    new Vec3(2190, 107, -3177),
                    new Vec3(2207, 109, -3184),
                    new Vec3(2224, 106, -3182),
                    new Vec3(2197, 106, -3163),
                    new Vec3(2212, 107, -3163),
                    new Vec3(2227, 107, -3167),
                    new Vec3(2240, 104, -3160),
                    new Vec3(2198, 105, -3145),
                    new Vec3(2210, 105, -3145),
                    new Vec3(2225, 104, -3145),
                    new Vec3(2242, 103, -3146)
            );
            instance = new StoneSpiderSpawnController(spawnPos, 2261, -3136, 2172, -3222, world, 300);
        }
        return instance;
    }

    public StoneSpiderSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                      int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s(mobName, style), canSpawnPos, canSpawnPos.size() * 3, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, 32, level, 1, averageLevel, 24);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(14000, 850, 850, 0.4, 3, 0.6, 650, 25, 10000 * Math.pow(10, 4), 0.45);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Spider mob = new Spider(EntityType.SPIDER, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, Te.s(mobName, style), xpLevel, getMobAttributes());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(mob), list);
        return mob;
    }

    @Override
    public void eachMobTick(Mob mob) {
        if (mob.isDeadOrDying()) {
            return;
        }
        if (mob.tickCount % 20 == (mobList.indexOf(mob) % 20)) {
            commonAttack(mob);
        }
        Compute.mobHealthRecover(mob, 0.02);
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.stone, 8);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return List.of(
                new ItemAndRate(NewAreaItems.STONE_SPIDER_PIECE.get(), 0.08),
                new ItemAndRate(ModItems.SILVER_COIN.get(), 3),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 0.065),
                new ItemAndRate(ModItems.STONE_ELEMENT_PIECE_0.get(), 0.5)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "StoneSpider";
    }

    public static void onMobDead(Mob mob) {
        if (!MobSpawn.getMobOriginName(mob).equals(mobName)) {
            return;
        }
        if (RandomUtils.nextDouble(0, 1) < 0.1) {
            if (RandomUtils.nextBoolean()) {
                skill1(mob);
            } else {
                skill2(mob);
            }
        }
    }

    // 亡语:网拽
    public static void skill1(Mob mob) {
        Player player = Compute.getNearestPlayer(mob, 24);
        if (player != null) {
            Compute.causeGatherEffect(player, 10, mob.position());
        }
    }

    // 亡语:束缚
    public static void skill2(Mob mob) {
        Player player = Compute.getNearestPlayer(mob, 24);
        if (player != null) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    BlockPos blockPos = player.blockPosition().west(1).south(1).east(i).north(j);
                    if (mob.level().getBlockState(blockPos).is(Blocks.AIR)) {
                        BlockAndResetTime.createThenDestroy(mob.level(),
                                new BlockAndResetTime(Blocks.COBWEB.defaultBlockState(),
                                        blockPos, Tick.get() + Tick.s(10)));
                    }
                }
            }
        }
    }

    // 普通毒液攻击
    public void commonAttack(Mob mob) {
        Player player = Compute.getNearestPlayer(mob, 24);
        if (player != null) {
            MonsterAttackEvent.causeCommonAttackToPlayer(mob, player);
            ParticleProvider.createLineEffectParticle(mob.level(), mob, player, CustomStyle.styleOfStone);
        }
    }
}
