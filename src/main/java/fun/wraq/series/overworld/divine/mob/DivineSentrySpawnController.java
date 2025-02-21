package fun.wraq.series.overworld.divine.mob;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.divine.DivineIslandItems;
import fun.wraq.series.overworld.divine.DivineUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class DivineSentrySpawnController extends MobSpawnController {

    public static String mobName = "圣光边防军";
    public static Style style = CustomStyle.DIVINE_STYLE;
    private static DivineSentrySpawnController instance;

    public static DivineSentrySpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2230, 81, 806),
                    new Vec3(2271, 79, 814),
                    new Vec3(2302, 74, 821),
                    new Vec3(2230, 81, 823),
                    new Vec3(2240, 77, 834),
                    new Vec3(2263, 73, 830),
                    new Vec3(2277, 73, 833),
                    new Vec3(2290, 74, 837),
                    new Vec3(2300, 73, 849),
                    new Vec3(2263, 73, 853)
            );
            instance = new DivineSentrySpawnController(spawnPos, 2348, 875, 2218, 749, world, 275);
        }
        return instance;
    }

    public DivineSentrySpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                       int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s(mobName, style), canSpawnPos, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Stray mob = new Stray(EntityType.STRAY, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, Component.literal(mobName).withStyle(style), xpLevel,
                9000, 600, 600,
                0.4, 5, 0.6, 400, 25,
                5000 * Math.pow(10, 4), 0.4);
        // 设置物品
        MobSpawn.setStainArmorOnMob(mob, style);
        mob.setItemInHand(InteractionHand.MAIN_HAND, Items.BOW.getDefaultInstance());
        mob.setItemSlot(EquipmentSlot.CHEST, DivineIslandItems.MOB_FANVER_DIAMOND_CHEST.get().getDefaultInstance());
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
    public void tick() {
        mobList.forEach(mob -> {
            DivineUtils.handleMobTick(mob);
            mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 600));
            mob.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 600));
        });
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return List.of(
                new ItemAndRate(DivineIslandItems.DIVINE_SOUL.get(), 0.08),
                new ItemAndRate(ModItems.silverCoin.get(), 1),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 0.06),
                new ItemAndRate(Element.getPiece0ItemMap().get(DivineUtils.currentDayElement), 0.5)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "DivineSentry";
    }
}
