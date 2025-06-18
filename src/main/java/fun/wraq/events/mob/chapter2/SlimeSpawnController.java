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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlimeSpawnController extends MobSpawnController {

    public static String mobName = "史莱姆";
    private static SlimeSpawnController instance;

    public static SlimeSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1030, 63, -470),
                    new Vec3(1038, 63, -482),
                    new Vec3(1040, 67, -496),
                    new Vec3(1053, 64, -509),
                    new Vec3(1048, 67, -524),
                    new Vec3(1057, 64, -538),
                    new Vec3(1049, 71, -549),
                    new Vec3(1059, 64, -565),
                    new Vec3(1054, 66, -579)
            );
            instance = new SlimeSpawnController(spawnPos, 1072, -454, 1004, -631, world, 76);
        }
        return instance;
    }

    public SlimeSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("史莱姆", CustomStyle.styleOfLife), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(200, 50, 50, 0.35, 3, 0.2, 5, 15, 42000, 0.3);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Slime slime = new Slime(EntityType.SLIME, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfLife;
        MobSpawn.setMobCustomName(slime, Te.s(mobName, style), xpLevel);
        slime.setSize(2, true);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(slime), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(slime, getMobAttributes());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(slime), list);
        return slime;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.life, 3);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SLIME_BALL.get(), 0.4));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.1875));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.02));
            add(new ItemAndRate(ModItems.LIFE_ELEMENT_PIECE_0.get(), 0.15));
            add(new ItemAndRate(C2LootItems.SLIME_CHEST.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "Slime";
    }
}
