package fun.wraq.series.overworld.cold.sc2.stray;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.common.util.struct.BlockAndResetTime;
import fun.wraq.events.fight.MonsterAttackEvent;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.cold.SuperColdItems;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Random;

public class SuperColdStraySpawnController extends MobSpawnController {

    public static String mobName = "极寒遗忘者";
    public static Style style = CustomStyle.styleOfIce;
    private static SuperColdStraySpawnController instance;

    public static SuperColdStraySpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2194, 149, -3305),
                    new Vec3(2178, 144, -3299),
                    new Vec3(2213, 147, -3299),
                    new Vec3(2224, 139, -3288),
                    new Vec3(2166, 138, -3283),
                    new Vec3(2212, 140, -3275),
                    new Vec3(2168, 137, -3270),
                    new Vec3(2205, 136, -3265),
                    new Vec3(2176, 135, -3259),
                    new Vec3(2192, 133, -3259)
            );
            instance = new SuperColdStraySpawnController(spawnPos, 2234, -3250, 2156, -3319, world, 300);
        }
        return instance;
    }

    public SuperColdStraySpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                         int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s(mobName, style), canSpawnPos, canSpawnPos.size() * 3, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, 32, level, 1, averageLevel, 24);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(56000, 850, 850, 0.4, 3, 0.6, 650, 25, 10000 * Math.pow(10, 4), 0.45);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Stray stray = new Stray(EntityType.STRAY, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        Style style = CustomStyle.styleOfSnow;
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(stray), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(stray, Te.s(mobName, style), xpLevel, getMobAttributes());
        // 设置物品
        MobSpawn.setStainArmorOnMob(stray, style);
        stray.setItemSlot(EquipmentSlot.CHEST, Compute.getSimpleFoiledItemStack(Items.DIAMOND_CHESTPLATE));
        stray.setItemSlot(EquipmentSlot.FEET, Compute.getSimpleFoiledItemStack(Items.DIAMOND_BOOTS));
        stray.setItemInHand(InteractionHand.MAIN_HAND, Compute.getSimpleFoiledItemStack(Items.BOW));
        stray.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(SuperColdItems.FLOWER.get()));
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(stray), list);
        return stray;
    }

    @Override
    public void eachMobTick(Mob mob) {
        if (mob.isDeadOrDying()) {
            return;
        }
        if (mob.tickCount % 100 == 99 && RandomUtils.nextDouble(0, 1) < 0.1) {
            skill(mob);
        }
        Compute.mobHealthRecover(mob, 0.02);
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.ice, 8);
    }

    public void skill(Mob mob) {
        Compute.getNearPlayer(mob.level(), mob.position(), 24).forEach(player -> {
            Compute.createRangeEffectDot(mob.level(), player.position(), 4, new Compute.CauseDamageToPlayer() {
                @Override
                public void causeDamage(Player player) {
                    MonsterAttackEvent.causeCommonAttackToPlayer(mob, player);
                    Compute.createIceParticle(player);
                }
            }, CustomStyle.styleOfIce, Tick.get() + Tick.s(2), 20, Tick.s(10));
            Compute.createIceParticle(player);
            if (player.level().getBlockState(player.blockPosition()).is(Blocks.AIR)) {
                BlockAndResetTime.createThenDestroy(mob.level(), new BlockAndResetTime(Blocks.ICE.defaultBlockState(),
                        player.blockPosition(), Tick.get() + Tick.s(10)));
            } else if (player.level().getBlockState(player.blockPosition().above()).is(Blocks.AIR)) {
                BlockAndResetTime.createThenDestroy(mob.level(), new BlockAndResetTime(Blocks.ICE.defaultBlockState(),
                        player.blockPosition().above(), Tick.get() + Tick.s(10)));
            }
        });
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return List.of(
                new ItemAndRate(SuperColdItems.FLOWER.get(), 0.05),
                new ItemAndRate(ModItems.SNOW_SOUL.get(), 3),
                new ItemAndRate(ModItems.SILVER_COIN.get(), 3),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 0.08),
                new ItemAndRate(ModItems.ICE_ELEMENT_PIECE_0.get(), 0.5),
                new ItemAndRate(ModItems.SNOW_CREST_0.get(), 0.04),
                new ItemAndRate(ModItems.SNOW_CREST_1.get(), 0.02),
                new ItemAndRate(ModItems.SNOW_CREST_2.get(), 0.002),
                new ItemAndRate(ModItems.SNOW_CREST_3.get(), 0.0004)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "SuperColdStray";
    }
}
