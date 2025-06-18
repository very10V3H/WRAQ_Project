package fun.wraq.events.mob.chapter6_castle;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C6LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlazeSpawnController extends MobSpawnController {

    public static String mobName = "熔岩湖溢出物";
    private static BlazeSpawnController instance;

    public static BlazeSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2437, 152, -1432),
                    new Vec3(2450, 152, -1434),
                    new Vec3(2461, 152, -1426),
                    new Vec3(2449, 149, -1420),
                    new Vec3(2429, 152, -1419),
                    new Vec3(2461, 152, -1409),
                    new Vec3(2433, 152, -1404),
                    new Vec3(2461, 152, -1409),
                    new Vec3(2447, 152, -1402)
            );
            instance = new BlazeSpawnController(spawnPos, 2476, -1395, 2420, -1448, world, 180);
        }
        return instance;
    }

    public BlazeSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("熔岩湖溢出物", CustomStyle.styleOfPower), canSpawnPos, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(2000, 160, 130, 0.4, 3, 0.25, 55, 20, 300 * Math.pow(10, 4), 0.35);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Blaze blaze = new Blaze(EntityType.BLAZE, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfPower;
        MobSpawn.setMobCustomName(blaze, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(blaze), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(blaze, getMobAttributes());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(blaze), list);
        return blaze;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.fire, 4);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.BLAZE_SOUL.get(), 0.8));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.58));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.06));
            add(new ItemAndRate(ModItems.FIRE_ELEMENT_PIECE_0.get(), 0.4));
            add(new ItemAndRate(C6LootItems.BLAZE_LOOT_SWORD.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "Blaze";
    }
}
