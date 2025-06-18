package fun.wraq.events.mob.chapter3_nether;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C3LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MagmaSpawnController extends MobSpawnController {

    public static String mobName = "熔岩聚合物";
    private static MagmaSpawnController instance;

    public static MagmaSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(482, 63, -624),
                    new Vec3(480, 64, -614),
                    new Vec3(482, 64, -602),
                    new Vec3(489, 62, -598),
                    new Vec3(498, 60, -591),
                    new Vec3(510, 63, -593),
                    new Vec3(504, 67, -672),
                    new Vec3(577, 60, -614),
                    new Vec3(517, 60, -607)
            );
            instance = new MagmaSpawnController(spawnPos, 528, -582, 464, -645, world, 80);
        }
        return instance;
    }

    public MagmaSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("熔岩聚合物", CustomStyle.styleOfMagma), canSpawnPos, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(200, 50, 50, 0.35, 3, 0.2, 5, 15, 54000, 0.25);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        MagmaCube magmaCube = new MagmaCube(EntityType.MAGMA_CUBE, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfMagma;
        MobSpawn.setMobCustomName(magmaCube, Te.s(mobName, style), xpLevel);
        magmaCube.setSize(2, true);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(magmaCube), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(magmaCube, getMobAttributes());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(magmaCube), list);
        return magmaCube;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.fire, 4);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.RUBY.get(), 0.5));
            add(new ItemAndRate(ModItems.NETHER_QUARTZ.get(), 0.15));
            add(new ItemAndRate(ModItems.MAGMA_SOUL.get(), 0.4));
            add(new ItemAndRate(ModItems.COPPER_COIN.get(), 3));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.02));
            add(new ItemAndRate(ModItems.FIRE_ELEMENT_PIECE_0.get(), 0.2));
            add(new ItemAndRate(ModItems.NETHER_PEARL.get(), 0.005));
            add(new ItemAndRate(C3LootItems.MAGMA_LOOT_SCEPTRE.get(), 0.005));
            add(new ItemAndRate(NewRuneItems.NETHER_NEW_RUNE.get(), 0.001));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "Magma";
    }
}
