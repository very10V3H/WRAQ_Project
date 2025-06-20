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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SkyVexSpawnController extends MobSpawnController {

    public static String mobName = "天空城的不速之客";
    private static SkyVexSpawnController instance;

    public static SkyVexSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(941, 292, -34)
            );
            instance = new SkyVexSpawnController(spawnPos, spawnPos.size() * 10, 967, 320, -2, 909, 265, -53, 20, 60,
                    world, 10, 60);
        }
        return instance;
    }

    public SkyVexSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum, int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                                 int boundaryDownX, int boundaryDownY, int boundaryDownZ, double summonOffset, int detectionRange, Level level, int mobPlayerRate, int averageLevel) {
        super(Te.s("天空城的不速之客", CustomStyle.styleOfSky), canSpawnPos, oneZoneMaxMobNum, boundaryUpX, boundaryUpY, boundaryUpZ, boundaryDownX, boundaryDownY, boundaryDownZ, summonOffset, detectionRange, level, mobPlayerRate, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(200, 45, 45, 0.3, 3, 0.1, 3, 10, 6000, 0.3);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Vex vex = new Vex(EntityType.VEX, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfSky;
        MobSpawn.setMobCustomName(vex, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(vex), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(vex, getMobAttributes());
        // 设置物品
        vex.setItemInHand(InteractionHand.MAIN_HAND, Items.DIAMOND_SWORD.getDefaultInstance());
        vex.addEffect(new MobEffectInstance(MobEffects.GLOWING, 88888, 1, false, false));
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(vex), list);
        // 直接送至背包
        MobSpawn.dropsDirectToInventory.put(MobSpawn.getMobOriginName(vex), true);
        return vex;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.wind, 2);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.SKY_SOUL.get(), 0.8));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.375));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.02));
            add(new ItemAndRate(ModItems.SKY_CREST_0.get(), 0.02));
            add(new ItemAndRate(ModItems.SKY_CREST_1.get(), 0.005));
            add(new ItemAndRate(ModItems.SKY_CREST_2.get(), 0.001));
            add(new ItemAndRate(ModItems.SKY_CREST_3.get(), 0.0002));
            add(new ItemAndRate(ModItems.WIND_ELEMENT_PIECE_0.get(), 0.2));
            add(new ItemAndRate(C2LootItems.SKY_VEX_BOW.get(), 0.005));
            add(new ItemAndRate(NewRuneItems.SKY_NEW_RUNE.get(), 0.001));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "SkyVex";
    }
}
