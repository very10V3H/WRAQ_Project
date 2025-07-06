package fun.wraq.events.mob.chapter2;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C2LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EvokerSpawnController extends MobSpawnController {

    public static String mobName = "唤魔者";
    private static EvokerSpawnController instance;

    public static EvokerSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1414, 70, -218),
                    new Vec3(1443, 67, -188),
                    new Vec3(1496, 68, -163),
                    new Vec3(1529, 69, -177),
                    new Vec3(1560, 67, -195),
                    new Vec3(1584, 66, -218),
                    new Vec3(1597, 70, -248),
                    new Vec3(1587, 69, -285),
                    new Vec3(1552, 69, -306),
                    new Vec3(1514, 72, -313),
                    new Vec3(1473, 69, -318),
                    new Vec3(1433, 69, -309)
            );
            instance = new EvokerSpawnController(spawnPos, 1649, -137, 1374, -381, world, 1, 56);
        }
        return instance;
    }

    public EvokerSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                 int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(Te.s("唤魔者", CustomStyle.styleOfMana), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ,
                16, level, mobPlayerRate, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(200, 40, 40, 0.3, 2, 0.1, 3, 10, 5500, 0.3);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Evoker evoker = new Evoker(EntityType.EVOKER, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfMana;
        MobSpawn.setMobCustomName(evoker, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(evoker), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(evoker, getMobAttributes());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(evoker), list);
        return evoker;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.lightning, 2);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.EVOKER_SOUL.get(), 1));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.375));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.02));
            add(new ItemAndRate(ModItems.LIFE_ELEMENT_PIECE_0.get(), 0.2));
            add(new ItemAndRate(C2LootItems.EVOKER_SCEPTRE.get(), 0.005));
            add(new ItemAndRate(NewRuneItems.EVOKER_NEW_RUNE.get(), 0.001));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "Evoker";
    }
}
