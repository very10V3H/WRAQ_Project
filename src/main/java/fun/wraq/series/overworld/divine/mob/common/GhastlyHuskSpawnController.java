package fun.wraq.series.overworld.divine.mob.common;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.divine.DivineIslandItems;
import fun.wraq.series.overworld.divine.DivineUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class GhastlyHuskSpawnController extends MobSpawnController {

    public static String mobName = "瑕光尸壳";
    public static Style style = CustomStyle.GHASTLY_STYLE;
    private static GhastlyHuskSpawnController instance;

    public static GhastlyHuskSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2394, 86, 713),
                    new Vec3(2404, 82, 703),
                    new Vec3(2419, 84, 711),
                    new Vec3(2429, 85, 719),
                    new Vec3(2438, 86, 719),
                    new Vec3(2451, 86, 729),
                    new Vec3(2434, 86, 733),
                    new Vec3(2443, 86, 746),
                    new Vec3(2449, 87, 712),
                    new Vec3(2461, 87, 704)
            );
            instance = new GhastlyHuskSpawnController(spawnPos, 2500, 800, 2300, 650, world, 290);
        }
        return instance;
    }

    public GhastlyHuskSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                      int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s(mobName, style), canSpawnPos, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, 16, level, 1, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(12000, 750, 750, 0.4, 3, 0.6, 550, 25, 8000 * Math.pow(10, 4), 0.4);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Husk mob = new Husk(EntityType.HUSK, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, Te.s(mobName, style), xpLevel, getMobAttributes());
        // 设置物品
        MobSpawn.setStainArmorOnMob(mob, style);
        mob.setItemInHand(InteractionHand.MAIN_HAND, ModItems.SKY_SWORD.get().getDefaultInstance());
        if (random.nextBoolean()) {
            mob.setItemSlot(EquipmentSlot.HEAD, DivineIslandItems.MOB_FANVER_IRON_HELMET.get().getDefaultInstance());
        } else {
            mob.setItemSlot(EquipmentSlot.HEAD, DivineIslandItems.MOB_FANVER_GOLDEN_HELMET.get().getDefaultInstance());
        }
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(mob), list);
        return mob;
    }

    @Override
    public void eachMobTick(Mob mob) {
        DivineUtils.handleMobTick(mob);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return List.of(
                new ItemAndRate(DivineIslandItems.GHASTLY_NUGGET.get(), 0.1),
                new ItemAndRate(DivineIslandItems.GHASTLY_SOUL.get(), 0.2),
                new ItemAndRate(ModItems.SILVER_COIN.get(), 1),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 0.07),
                new ItemAndRate(Element.getPiece0ItemMap().get(DivineUtils.currentDayElement), 0.5)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "GhastlyHusk";
    }
}
