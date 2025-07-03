package fun.wraq.events.mob.chapter7;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C7LootItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StarRabbitSpawnController extends MobSpawnController {

    public static String mobName = "小玉兔";
    public static final Style style = CustomStyle.styleOfMoon;
    private static StarRabbitSpawnController instance;

    public static StarRabbitSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1046, 226, 599),
                    new Vec3(1062, 223, 613),
                    new Vec3(1067, 223, 634),
                    new Vec3(1043, 220, 635),
                    new Vec3(1021, 223, 620)
            );
            instance = new StarRabbitSpawnController(spawnPos, spawnPos.size() * 4,
                    1090, 275, 650, 1000, 200, 574, 3, 60, world, 15, 200);
        }
        return instance;
    }

    public StarRabbitSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum,
                                     int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                                     int boundaryDownX, int boundaryDownY, int boundaryDownZ,
                                     double summonOffset, int detectionRange, Level level,
                                     int mobPlayerRate, int averageLevel) {
        super(Te.s(mobName, style), canSpawnPos, oneZoneMaxMobNum,
                boundaryUpX, boundaryUpY, boundaryUpZ,
                boundaryDownX, boundaryDownY, boundaryDownZ,
                summonOffset, detectionRange, level, mobPlayerRate, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(0, 200, 200, 0, 0, 0, 0, 0, 750 * Math.pow(10, 4), 0.35);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Rabbit mob = new Rabbit(EntityType.RABBIT, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        MobSpawn.setMobCustomName(mob, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, getMobAttributes());
        // 设置物品
        mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 88888, 1, false, false));
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(mob), list);
        // 直接送至背包
        MobSpawn.dropsDirectToInventory.put(MobSpawn.getMobOriginName(mob), true);
        return mob;
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.STAR_SOUL.get(), 0.8));
            add(new ItemAndRate(ModItems.STAR_RUNE.get(), 0.1));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.87));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.06));
            add(new ItemAndRate(C7LootItems.STAR_LOOT_SWORD.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "StarRabbit";
    }
}
