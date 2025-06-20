package fun.wraq.events.mob.chapter4_end;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C4LootItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShulkerSpawnController extends MobSpawnController {

    public static String mobName = "寂域遗骸";
    private static ShulkerSpawnController instance;

    public static ShulkerSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(38, 86, 557),
                    new Vec3(52, 86, -186),
                    new Vec3(74, 87, -187),
                    new Vec3(60.5, 86, -168.5),
                    new Vec3(7, 88, -178),
                    new Vec3(-3.5, 88, -154.5),
                    new Vec3(-11.5, 87, -168.5),
                    new Vec3(-26, 88, -178)
            );
            instance = new ShulkerSpawnController(spawnPos, 104, -128, -61, -289, world, 140);
        }
        return instance;
    }

    public ShulkerSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                   int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("寂域遗骸", CustomStyle.styleOfEnd), canSpawnPos, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(1200, 100, 100, 0.45, 3, 0.3, 30, 25, 32 * Math.pow(10, 4), 0.4);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Shulker shulker = new Shulker(EntityType.SHULKER, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfEnd;
        MobSpawn.setMobCustomName(shulker, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(shulker), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(shulker, getMobAttributes());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(shulker), list);
        return shulker;
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SHULKER_SOUL.get(), 0.8));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.75));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.05));
            add(new ItemAndRate(C4LootItems.SHULKER_CHEST.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "Shulker";
    }
}
