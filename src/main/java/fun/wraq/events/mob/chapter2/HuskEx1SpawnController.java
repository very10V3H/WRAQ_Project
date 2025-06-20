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
import fun.wraq.series.newrunes.NewRuneItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HuskEx1SpawnController extends MobSpawnController {

    public static String mobName = "脆弱的岩灵";
    private static HuskEx1SpawnController instance;

    public static HuskEx1SpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1067, 69, 334),
                    new Vec3(1106, 67, 324)
            );
            instance = new HuskEx1SpawnController(spawnPos, world, 1, 84);
        }
        return instance;
    }

    public HuskEx1SpawnController(List<Vec3> canSpawnPos, Level level, int mobPlayerRate, int averageLevel) {
        super(Te.s("脆弱的岩灵", CustomStyle.styleOfHusk), canSpawnPos,16, level, mobPlayerRate, averageLevel,
                List.of(new Boundary(new Vec3(933, 1000, 415), new Vec3(760, -100, 289)),
                        new Boundary(new Vec3(1230, 1000, 453), new Vec3(1034, -100, 270))));
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(400, 50, 50, 0.35, 3, 0.2, 5, 15, 12000, 0.3);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Husk husk = new Husk(EntityType.HUSK, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfHusk;
        MobSpawn.setMobCustomName(husk, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(husk), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(husk, getMobAttributes());
        // 设置物品
        husk.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(husk), list);
        return husk;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.stone, 2);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.HUSK_SOUL.get(), 1));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.4375));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.02));
            add(new ItemAndRate(ModItems.STONE_ELEMENT_PIECE_0.get(), 0.2));
            add(new ItemAndRate(C2LootItems.HUSK_SWORD.get(), 0.005));
            add(new ItemAndRate(NewRuneItems.HUSK_NEW_RUNE.get(), 0.001));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "Husk";
    }
}
