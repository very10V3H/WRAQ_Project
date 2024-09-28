package fun.wraq.events.mob.chapter2;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ItemAndRate;
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
            instance = new JorogumoSpawnController(spawnPos, spawnPos.size() * 4, 1200, -1150, 1050, -1330, world, 4, 92);
        }
        return instance;
    }

    public JorogumoSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpZ,
                                   int boundaryDownX, int boundaryDownZ, Level level, int mobPlayerRate, int averageLevel) {
        super(canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, 60, level, mobPlayerRate, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        JorogumoEntity jorogumoEntity = new JorogumoEntity(ModEntityTypes.JOROGUMO.get(), this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfJacaranda;
        MobSpawn.setMobCustomName(jorogumoEntity, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(jorogumoEntity), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(jorogumoEntity, 450, 10, 10, 0.35, 3, 0.2, 5, 15, 18000, 0.3);

        // 设置物品


        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(jorogumoEntity), list);
        return jorogumoEntity;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Element.stone, 2);
        });
    }

    public static List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.PurpleIronPiece.get(), 1));
            add(new ItemAndRate(ModItems.silverCoin.get(), 0.375));
            add(new ItemAndRate(ModItems.gemPiece.get(), 0.02));
            add(new ItemAndRate(ModItems.StoneElementPiece0.get(), 0.2));
            add(new ItemAndRate(ModItems.lavenderBracelet.get(), 0.005));
            add(new ItemAndRate(C2LootItems.jorogumoLeggings.get(), 0.005));
        }};
    }
}
