package fun.wraq.events.mob.chapter2;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GuardianSpawnController extends MobSpawnController {

    public static String mobName = "神殿守卫";
    private static GuardianSpawnController instance;

    public static GuardianSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1103, 30, 923),
                    new Vec3(1073, 30, 912),
                    new Vec3(1103, 30, 903),
                    new Vec3(1073, 30, 893),
                    new Vec3(1102, 30, 882),
                    new Vec3(1122, 22, 887),
                    new Vec3(1122, 22, 911),
                    new Vec3(1054, 22, 909),
                    new Vec3(1054, 22, 890),
                    new Vec3(1089, 20, 932),
                    new Vec3(1089, 20, 888)
            );
            instance = new GuardianSpawnController(spawnPos, 1168, 949, 1007, 801, world, 100);
        }
        return instance;
    }

    public GuardianSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                   int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("神殿守卫", CustomStyle.styleOfSea), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(500, 60, 60, 0.35, 3, 0.2, 5, 15, 30000, 0.3);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Guardian guardian = new Guardian(EntityType.GUARDIAN, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        Style style = CustomStyle.styleOfSea;
        // 设置属性
        MobSpawn.setMobCustomName(guardian, Te.s(mobName, style), xpLevel);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(guardian), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(guardian, getMobAttributes());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(guardian), list);
        return guardian;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.water, 2);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SEA_SOUL.get(), 1.5));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.4375));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.02));
            add(new ItemAndRate(ModItems.WATER_ELEMENT_PIECE_0.get(), 0.2));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "Guardian";
    }
}
