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
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EndermiteSpawnController extends MobSpawnController {

    public static String mobName = "寂域灵螨";
    private static EndermiteSpawnController instance;

    public static EndermiteSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(-28, 89, -220),
                    new Vec3(-11.5, 87, -240.5),
                    new Vec3(5, 87, -248),
                    new Vec3(0.5, 86, -223.5),
                    new Vec3(39.5, 89, -229.5),
                    new Vec3(46, 89, -252),
                    new Vec3(60.5, 87, -240.5),
                    new Vec3(71, 89, -224)
            );
            instance = new EndermiteSpawnController(spawnPos, 104, -128, -61, -289, world, 140);
        }
        return instance;
    }

    public EndermiteSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                   int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("寂域灵螨", CustomStyle.styleOfEnd), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(1200, 100, 100, 0.45, 3, 0.3, 30, 25, 32 * Math.pow(10, 4), 0.4);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Endermite endermite = new Endermite(EntityType.ENDERMITE, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfEnd;
        MobSpawn.setMobCustomName(endermite, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(endermite), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(endermite, getMobAttributes());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(endermite), list);
        return endermite;
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.ENDER_MITE_SOUL.get(), 0.8));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.75));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.05));
            add(new ItemAndRate(C4LootItems.ENDERMITE_SCEPTRE.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "Endermite";
    }
}
