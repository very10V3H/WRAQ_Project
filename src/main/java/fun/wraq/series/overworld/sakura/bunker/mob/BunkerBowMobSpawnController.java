package fun.wraq.series.overworld.sakura.bunker.mob;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
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
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class BunkerBowMobSpawnController extends MobSpawnController {

    public static String mobName = "地堡弓手";
    public static Style style = CustomStyle.BUNKER_STYLE;
    private static BunkerBowMobSpawnController instance;

    public static BunkerBowMobSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(3841, -16, 1955),
                    new Vec3(3841, -8, 1984),
                    new Vec3(3809, -8, 2000),
                    new Vec3(3826, -8, 2001),
                    new Vec3(3857, -8, 2001),
                    new Vec3(3873, -8, 2001),
                    new Vec3(3894, -8, 2002),
                    new Vec3(3811, -8, 2032),
                    new Vec3(3811, -8, 2064),
                    new Vec3(3840, -8, 2063)
            );
            instance = new BunkerBowMobSpawnController(spawnPos, 3938, 2095, 3718, 1889, world, 265);
        }
        return instance;
    }

    public BunkerBowMobSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                       int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s(mobName, style), canSpawnPos, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, 16, level, 1, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(6500, 450, 450, 0.4, 3, 0.5, 350, 25, 2500 * Math.pow(10, 4), 0.4);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        WitherSkeleton mob = new WitherSkeleton(EntityType.WITHER_SKELETON, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, Te.s(mobName, style), xpLevel, getMobAttributes());
        // 设置物品
        MobSpawn.setStainArmorOnMob(mob, style);
        mob.setItemSlot(EquipmentSlot.HEAD, Compute.getSkullByName("MHF_LavaSlime"));
        mob.setItemInHand(InteractionHand.MAIN_HAND, Items.BOW.getDefaultInstance());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(mob), list);
        return mob;
    }

    @Override
    public LivingEntity getMounts() {
        Phantom phantom = new Phantom(EntityType.PHANTOM, level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(phantom, Component.literal("地堡幻翼").withStyle(style), xpLevel,
                7500, 500, 500,
                0.4, 3, 0.5, 375, 25,
                3500 * Math.pow(10, 4), 0.4);
        return phantom;
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.fire, 5);
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
        return "BunkerBow";
    }
}
