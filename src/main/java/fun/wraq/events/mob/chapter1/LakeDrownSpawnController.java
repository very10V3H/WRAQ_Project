package fun.wraq.events.mob.chapter1;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C1LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LakeDrownSpawnController extends MobSpawnController {

    public static String mobName = "河流故灵";
    private static LakeDrownSpawnController instance;

    public static LakeDrownSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1225, 63, 18),
                    new Vec3(1240, 63, 23),
                    new Vec3(1211, 63, 28),
                    new Vec3(1226, 63, 34),
                    new Vec3(1241, 63, 41),
                    new Vec3(1209, 63, 43),
                    new Vec3(1226, 63, 49),
                    new Vec3(1241, 63, 171),
                    new Vec3(1203, 63, 55),
                    new Vec3(1233, 63, 61),
                    new Vec3(1215, 63, 63)
            );
            instance = new LakeDrownSpawnController(spawnPos, 1262, 71, 1182, -4, world, 18);
        }
        return instance;
    }

    public LakeDrownSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                    int boundaryDownX, int boundaryDownZ,
                                    Level level, int averageLevel) {
        super(Te.s("河流故灵", CustomStyle.styleOfWater), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(80, 10, 10, 0.2, 1, 0, 0, 0, 750, 0.2);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Drowned drowned = new Drowned(EntityType.DROWNED, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        Style style = CustomStyle.styleOfWater;
        MobSpawn.setMobCustomName(drowned, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(drowned), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(drowned, getMobAttributes());
        // 设置物品
        MobSpawn.setStainArmorOnMob(drowned, style);
        drowned.setItemInHand(InteractionHand.MAIN_HAND, Items.TRIDENT.getDefaultInstance());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(drowned), list);
        return drowned;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.water, 1);
    }

    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.LAKE_SOUL.get(), 1.5));
            add(new ItemAndRate(ModItems.COPPER_COIN.get(), 3));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.01));
            add(new ItemAndRate(ModItems.LAKE_CREST_0.get(), 0.02));
            add(new ItemAndRate(ModItems.LAKE_CREST_1.get(), 0.005));
            add(new ItemAndRate(ModItems.LAKE_CREST_2.get(), 0.001));
            add(new ItemAndRate(ModItems.LAKE_CREST_3.get(), 0.0002));
            add(new ItemAndRate(ModItems.WATER_ELEMENT_PIECE_0.get(), 0.1));
            add(new ItemAndRate(NewRuneItems.LAKE_NEW_RUNE.get(), 0.001));
            add(new ItemAndRate(C1LootItems.LAKE_DROWN_HELMET.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "LakeDrown";
    }
}
