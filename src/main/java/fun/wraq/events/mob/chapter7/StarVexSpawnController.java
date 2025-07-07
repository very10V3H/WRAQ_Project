package fun.wraq.events.mob.chapter7;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C7LootItems;
import fun.wraq.render.toolTip.CustomStyle;
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

public class StarVexSpawnController extends MobSpawnController {

    public static String mobName = "梦灵";
    private static StarVexSpawnController instance;

    public static StarVexSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1048, 236, 598),
                    new Vec3(1032, 236, 602),
                    new Vec3(1053, 260, 601),
                    new Vec3(1052, 223, 604),
                    new Vec3(1069, 236, 605)
            );
            instance = new StarVexSpawnController(spawnPos, spawnPos.size() * 4,
                    1089, 275, 637, 1018, 215, 574, 3, 60, world, 15, 200);
        }
        return instance;
    }

    public StarVexSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum,
                                  int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                                  int boundaryDownX, int boundaryDownY, int boundaryDownZ,
                                  double summonOffset, int detectionRange, Level level,
                                  int mobPlayerRate, int averageLevel) {
        super(Te.s("梦灵", CustomStyle.styleOfMoon1), canSpawnPos, oneZoneMaxMobNum,
                boundaryUpX, boundaryUpY, boundaryUpZ,
                boundaryDownX, boundaryDownY, boundaryDownZ,
                summonOffset, detectionRange, level, mobPlayerRate, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(2500, 180, 180, 0.4, 3, 0.3, 70, 20, 500 * Math.pow(10, 4), 0.35);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Vex vex = new Vex(EntityType.VEX, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfMoon1;
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
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.STAR_SOUL.get(), 0.8));
            add(new ItemAndRate(ModItems.STAR_RUNE.get(), 0.1));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.87));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.06));
            add(new ItemAndRate(C7LootItems.STAR_LOOT_SWORD.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "Star";
    }
}
