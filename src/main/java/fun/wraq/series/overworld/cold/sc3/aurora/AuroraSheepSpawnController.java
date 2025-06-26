package fun.wraq.series.overworld.cold.sc3.aurora;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.cold.SuperColdItems;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class AuroraSheepSpawnController extends MobSpawnController {

    public static String mobName = "极光绵羊";
    public static Style style = CustomStyle.styleOfIce;
    private static AuroraSheepSpawnController instance;

    public static AuroraSheepSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2459, 138, -3447),
                    new Vec3(2443, 145, -3475),
                    new Vec3(2459, 145, -3491),
                    new Vec3(2471, 145, -3508),
                    new Vec3(2494, 141, -3535),
                    new Vec3(2528, 134, -3524),
                    new Vec3(2572, 136, -3541),
                    new Vec3(2598, 129, -3554),
                    new Vec3(2615, 116, -3559),
                    new Vec3(2644, 123, -3577),
                    new Vec3(2679, 128, -3599)
            );
            instance = new AuroraSheepSpawnController(spawnPos, 2700, -3400, 2413, -3642, world, 305);
        }
        return instance;
    }

    public AuroraSheepSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                      int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s(mobName, style), canSpawnPos, canSpawnPos.size() * 3, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, 32, level, 1, averageLevel, 24);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(0, 900, 900, 0, 0, 0, 0, 0, 20000 * Math.pow(10, 4), 0.45);
    }

    public static List<DyeColor> getColors() {
        return List.of(
                DyeColor.GREEN,
                DyeColor.BLUE,
                DyeColor.YELLOW,
                DyeColor.RED,
                DyeColor.PURPLE
        );
    }

    private final static Map<DyeColor, Item> colorToItemMap = new HashMap<>();

    public static Map<DyeColor, Item> getColorToItemMap() {
        if (colorToItemMap.isEmpty()) {
            colorToItemMap.put(DyeColor.GREEN, SuperColdItems.GREEN_WOOL.get());
            colorToItemMap.put(DyeColor.BLUE, SuperColdItems.BLUE_WOOL.get());
            colorToItemMap.put(DyeColor.YELLOW, SuperColdItems.YELLOW_WOOL.get());
            colorToItemMap.put(DyeColor.RED, SuperColdItems.RED_WOOL.get());
            colorToItemMap.put(DyeColor.PURPLE, SuperColdItems.PURPLE_WOOL.get());
        }
        return colorToItemMap;
    }

    public static void handleColorItemDrop(Mob mob, List<ItemAndRate> list) {
        if (mob instanceof Sheep sheep && MobSpawn.getMobOriginName(mob).equals(mobName)
                && getColorToItemMap().containsKey(sheep.getColor())) {
            list.add(new ItemAndRate(getColorToItemMap().get(sheep.getColor()), 0.33));
        }
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Sheep mob = new Sheep(EntityType.SHEEP, this.level);
        Random random = new Random();
        mob.setColor(getColors().get(random.nextInt(getColors().size())));
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, Te.s(mobName, style), xpLevel, getMobAttributes());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(mob), list);
        return mob;
    }

    @Override
    public void eachMobTick(Mob mob) {
        if (mob.isDeadOrDying()) {
            return;
        }
        mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 3));
        Compute.mobHealthRecover(mob, 0.04);
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.ice, 8);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 3));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.08));
            add(new ItemAndRate(ModItems.ICE_ELEMENT_PIECE_0.get(), 0.5));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "AuroraSheep";
    }
}
