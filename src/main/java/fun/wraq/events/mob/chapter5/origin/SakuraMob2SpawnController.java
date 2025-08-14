package fun.wraq.events.mob.chapter5.origin;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.entities.entities.SakuraMob.SakuraMob;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SakuraMob2SpawnController extends MobSpawnController {

    public static String mobName = "樱灵";
    private static SakuraMob2SpawnController instance;

    public static SakuraMob2SpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2249, 142, 1761),
                    new Vec3(2273, 144, 1763),
                    new Vec3(2297, 148, 1755),
                    new Vec3(2312, 152, 1739),
                    new Vec3(2326, 153, 1724),
                    new Vec3(2342, 155, 1708),
                    new Vec3(2358, 158, 1695),
                    new Vec3(2269, 144, 1737),
                    new Vec3(2296, 147, 1717),
                    new Vec3(2318, 148, 1699),
                    new Vec3(2339, 150, 1677)
            );
            instance = new SakuraMob2SpawnController(spawnPos, 2358, 1775, 2221, 1630, world, 132);
        }
        return instance;
    }

    public SakuraMob2SpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                   int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("樱灵", CustomStyle.styleOfSakura), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(0, 85, 85, 0.4, 3, 0.25, 10, 20, 300000, 0.1);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        SakuraMob sakuraMob = new SakuraMob(ModEntityType.SakuraMob.get(), this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfSakura;
        MobSpawn.setMobCustomName(sakuraMob, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(sakuraMob), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(sakuraMob, getMobAttributes());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(sakuraMob), list);
        return sakuraMob;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.fire, 3);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SAKURA_PETAL.get(), 0.8));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.615));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.03));
            add(new ItemAndRate(ModItems.LIFE_ELEMENT_PIECE_0.get(), 0.3));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "SakuraMob";
    }
}
