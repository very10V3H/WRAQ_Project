package fun.wraq.events.mob.moontain;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.moontain.MoontainItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoontainChickenSpawnController extends MobSpawnController {

    public static String mobName = "望山翎凤";
    private static MoontainChickenSpawnController instance;

    public static MoontainChickenSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1958, 150, -804),
                    new Vec3(1973, 150, -797),
                    new Vec3(1990, 152, -801),
                    new Vec3(2004, 154, -792),
                    new Vec3(2020, 160, -795),
                    new Vec3(2028, 163, -799),
                    new Vec3(2022, 166, -812),
                    new Vec3(2032, 169, -817),
                    new Vec3(2043, 172, -817)
            );
            instance = new MoontainChickenSpawnController(spawnPos,
                    2066, 184, -773,
                    1933, 140, -834,
                    world, 210);
        }
        return instance;
    }

    public MoontainChickenSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                                          int boundaryDownX, int boundaryDownY, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("望山翎凤", CustomStyle.styleOfMoontain), canSpawnPos, boundaryUpX, boundaryUpY, boundaryUpZ, boundaryDownX, boundaryDownY, boundaryDownZ, level, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Chicken chicken = new Chicken(EntityType.CHICKEN, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfMoontain;
        MobSpawn.setMobCustomName(chicken, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(chicken, xpLevel, 0, 190,
                190, 0.4, 5, 0.3, 65, 25,
                400 * Math.pow(10, 4), 0.1);

        // 设置物品
        chicken.setItemSlot(EquipmentSlot.HEAD, Items.EMERALD_BLOCK.getDefaultInstance());

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(chicken), list);
        return chicken;
    }

    @Override
    public void tick() {
        if (Tick.get() % 100 == 0) {
            mobList.forEach(mob -> {
                mob.addEffect(new MobEffectInstance(MobEffects.LUCK, 200));
            });
        }
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(MoontainItems.FEATHER.get(), 0.1));
            add(new ItemAndRate(MoontainItems.STONE_FRAGMENT.get(), 0.4));
            add(new ItemAndRate(ModItems.silverCoin.get(), 1));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.06));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "MoontainChicken";
    }
}
