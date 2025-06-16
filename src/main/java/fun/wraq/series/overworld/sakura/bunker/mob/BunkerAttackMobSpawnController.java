package fun.wraq.series.overworld.sakura.bunker.mob;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.sakura.bunker.BunkerItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class BunkerAttackMobSpawnController extends MobSpawnController {

    public static String mobName = "地堡骑士";
    public static Style style = CustomStyle.BUNKER_STYLE;
    private static BunkerAttackMobSpawnController instance;

    public static BunkerAttackMobSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(3872, -8, 2017),
                    new Vec3(3841, -8, 2023),
                    new Vec3(3855, -8, 2032),
                    new Vec3(3875, -8, 2032),
                    new Vec3(3903, -8, 2033),
                    new Vec3(3917, -8, 2032),
                    new Vec3(3879, -8, 2062),
                    new Vec3(3902, -8, 2062)
            );
            instance = new BunkerAttackMobSpawnController(spawnPos, 3938, 2095, 3718, 1889, world, 270);
        }
        return instance;
    }

    public BunkerAttackMobSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                          int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s(mobName, style), canSpawnPos, 1, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, 16, level, 1, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        WitherSkeleton mob = new WitherSkeleton(EntityType.WITHER_SKELETON, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, Component.literal(mobName).withStyle(style), xpLevel,
                7500, 500, 500,
                0.4, 3, 0.5, 375, 25,
                3500 * Math.pow(10, 4), 0.4);
        // 设置物品
        MobSpawn.setStainArmorOnMob(mob, style);
        mob.setItemSlot(EquipmentSlot.HEAD, Compute.getSkullByName("MHF_LavaSlime"));
        mob.setItemInHand(InteractionHand.MAIN_HAND, Items.STONE_SWORD.getDefaultInstance());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(mob), list);
        return mob;
    }

    @Override
    public LivingEntity getMounts() {
        Hoglin hoglin = new Hoglin(EntityType.HOGLIN, level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(hoglin, Te.s("地堡疣猪", style), xpLevel,
                6500, 450, 450,
                0.4, 3, 0.5, 350, 25,
                2500 * Math.pow(10, 4), 0.4);
        return hoglin;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.provideElement(mob, Element.fire, 5);
        });
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return List.of(
                new ItemAndRate(BunkerItems.BUNKER_SOUL.get(), 0.3),
                new ItemAndRate(ModItems.SILVER_COIN.get(), 1),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 0.065),
                new ItemAndRate(ModItems.FIRE_ELEMENT_PIECE_0.get(), 0.5),
                new ItemAndRate(BunkerItems.BUNKER_HELMET_0.get(), 0.00025),
                new ItemAndRate(BunkerItems.BUNKER_CHEST_0.get(), 0.00025),
                new ItemAndRate(BunkerItems.BUNKER_LEGGINGS_0.get(), 0.00025),
                new ItemAndRate(BunkerItems.BUNKER_BOOTS_0.get(), 0.00025)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "BunkerAttack";
    }
}
