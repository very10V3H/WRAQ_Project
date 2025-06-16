package fun.wraq.series.overworld.cold.sc2.stray;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.common.util.struct.BlockAndResetTime;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.cold.sc2.SuperColdItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
        super(Te.s(mobName, style), canSpawnPos, 1, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, 16, level, 1, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        Stray stray = new Stray(EntityType.STRAY, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        Style style = CustomStyle.styleOfSnow;
        MobSpawn.setMobCustomName(stray, Component.literal(mobName).withStyle(style), xpLevel);
        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(stray), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(stray, 500, 60, 60, 0.35,
                3, 0.2, 5, 15, 30000, 0.3);
        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            stray.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }
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
    public void tick() {
        mobList.forEach(mob -> {
            Element.provideElement(mob, Element.ice, 8);
            if (mob.tickCount % 100 == 99 && RandomUtils.nextDouble(0, 1) < 0.1) {
                skill(mob);
            }
        });
    }

    public void skill(Mob mob) {
        Compute.getNearPlayer(mob.level(), mob.position(), 20).forEach(player -> {
            Compute.createRangeEffectDot(mob.level(), player.position(), 4, new Compute.CauseDamageToPlayer() {
                @Override
                public void causeDamage(Player player) {
                    Damage.causeAttackDamageToPlayer(mob, player, 50000);
                    Damage.causeManaDamageToPlayer(mob, player, 50000);
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
                new ItemAndRate(ModItems.SILVER_COIN.get(), 1),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 0.065),
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
