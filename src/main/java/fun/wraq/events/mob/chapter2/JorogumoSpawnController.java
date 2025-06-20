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
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.veroxuniverse.epicsamurai.entity.ModEntityTypes;
import net.veroxuniverse.epicsamurai.entity.custom.JorogumoEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JorogumoSpawnController extends MobSpawnController {

    public static String mobName = "紫晶妖妇";
    private static JorogumoSpawnController instance;

    public static JorogumoSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1088, 74, -1174),
                    new Vec3(1114, 77, -1176),
                    new Vec3(1156, 78, -1174),
                    new Vec3(1144, 78, -1196),
                    new Vec3(1091, 76, -1201),
                    new Vec3(1119, 78, -1211),
                    new Vec3(1142, 78, -1217),
                    new Vec3(1098, 78, -1223),
                    new Vec3(1126, 79, -1237),
                    new Vec3(1107, 79, -1243),
                    new Vec3(1146, 80, -1268),
                    new Vec3(1118, 79, -1263)
            );
            instance = new JorogumoSpawnController(spawnPos, 1200, -1150, 1050, -1330, world, 92);
        }
        return instance;
    }

    public JorogumoSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                   int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("紫晶妖妇", CustomStyle.styleOfJacaranda), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(450, 55, 55, 0.35, 3, 0.2, 5, 15, 18000, 0.3);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        JorogumoEntity jorogumoEntity = new JorogumoEntity(ModEntityTypes.JOROGUMO.get(), this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfJacaranda;
        MobSpawn.setMobCustomName(jorogumoEntity, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(jorogumoEntity), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(jorogumoEntity, getMobAttributes());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(jorogumoEntity), list);
        return jorogumoEntity;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.stone, 2);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.PURPLE_IRON_PIECE.get(), 1));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.375));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.02));
            add(new ItemAndRate(ModItems.STONE_ELEMENT_PIECE_0.get(), 0.2));
            add(new ItemAndRate(ModItems.LAVENDER_BRACELET.get(), 0.005));
            add(new ItemAndRate(C2LootItems.JOROGUMO_LEGGINGS.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "Jorogumo";
    }
}
