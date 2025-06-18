package fun.wraq.events.mob.chapter7;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.mushroom.MushroomItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MushroomLinSpawnController extends MobSpawnController {

    public static String mobName = "菌菇灵";
    private static MushroomLinSpawnController instance;

    public static MushroomLinSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2010, 126, -1814),
                    new Vec3(2033, 126, -1800),
                    new Vec3(2034, 126, -1786),
                    new Vec3(2033, 126, -1773),
                    new Vec3(2021, 127, -1758),
                    new Vec3(2006, 126, -1759),
                    new Vec3(1993, 126, -1759),
                    new Vec3(1978, 126, -1780),
                    new Vec3(1979, 126, -1799),
                    new Vec3(1996, 126, -1798)
            );
            instance = new MushroomLinSpawnController(spawnPos, 2059, -1738, 1968, -1836, world, 225);
        }
        return instance;
    }

    public MushroomLinSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                      int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s(mobName, CustomStyle.MUSHROOM_STYLE), canSpawnPos, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(3000, 200, 200, 0.4, 3, 0.3, 75, 25, 500 * Math.pow(10, 4), 0.4);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Piglin mob = new Piglin(EntityType.PIGLIN, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.MUSHROOM_STYLE;
        MobSpawn.setMobCustomName(mob, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, getMobAttributes());
        // 设置物品
        MobSpawn.setStainArmorOnMob(mob, CustomStyle.styleOfRed);
        mob.setItemSlot(EquipmentSlot.HEAD, Compute.getSkullByName("MHF_MushroomCow"));
        if (random.nextBoolean()) {
            mob.setItemInHand(InteractionHand.MAIN_HAND, Compute.getSimpleFoiledItemStack(Items.GOLDEN_SWORD));
        } else {
            mob.setItemInHand(InteractionHand.MAIN_HAND, Compute.getSimpleFoiledItemStack(Items.CROSSBOW));
        }
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(mob), list);
        return mob;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.life, 5);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(MushroomItems.BROWN_MUSHROOM.get(), 0.8));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.95));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.06));
            add(new ItemAndRate(ModItems.LIFE_ELEMENT_PIECE_0.get(), 0.5));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "MushroomLin";
    }
}
