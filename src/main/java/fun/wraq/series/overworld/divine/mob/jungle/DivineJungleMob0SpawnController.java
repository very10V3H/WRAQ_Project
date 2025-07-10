package fun.wraq.series.overworld.divine.mob.jungle;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.jungle.JungleMobSpawnController;
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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class DivineJungleMob0SpawnController extends JungleMobSpawnController {

    public static final String MOB_NAME = "圣光男爵";
    public static final Style STYLE = CustomStyle.DIVINE_STYLE;
    public static final int XP_LEVEL = 295;
    public static final Vec3 spawnPos = new Vec3(2435, 89, 647);

    public static DivineJungleMob0SpawnController instance;

    public static DivineJungleMob0SpawnController getInstance() {
        if (instance == null) {
            instance = new DivineJungleMob0SpawnController(Te.s(MOB_NAME, STYLE), new Vec3(2435, 89, 647),
                    new Vec3(2453, 111, 663), new Vec3(2414, 72, 625), XP_LEVEL, 32, Tick.min(3));
        }
        return instance;
    }

    public DivineJungleMob0SpawnController(Component name, Vec3 descriptionPos,
                                           Vec3 mobUpperBoundary, Vec3 mobLowerBoundary,
                                           int mobXpLevel, double detectionRadius, int refreshInterval) {
        super(name, descriptionPos, mobUpperBoundary, mobLowerBoundary, mobXpLevel, detectionRadius, refreshInterval);
    }

    @Override
    public void tryToReward(Player player) {
        getRewardItemList().forEach(itemAndRate -> {
            itemAndRate.sendWithMSG(player, 1);
        });
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(20000, 1000, 1000, 0.4, 3, 0.6, 700, 25, 15 * Math.pow(10, 8), 0.45);
    }

    @Override
    public void spawnMob(Level level) {
        WitherSkeleton mob = new WitherSkeleton(EntityType.WITHER_SKELETON, level);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, Te.s(name, STYLE), XP_LEVEL, getMobAttributes());
        mob.setItemSlot(EquipmentSlot.HEAD,
                Compute.getSimpleFoiledItemStack(DivineIslandItems.MOB_FANVER_IRON_HELMET.get()));
        mob.setItemInHand(InteractionHand.MAIN_HAND, Compute.getSimpleFoiledItemStack(Items.IRON_SWORD));
        mob.moveTo(spawnPos);
        level.addFreshEntity(mob);
        mobs.add(mob);
        Horse horse = new Horse(EntityType.HORSE, level);
        horse.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 88888));
        horse.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.45);
        horse.setItemSlot(EquipmentSlot.CHEST, Items.IRON_HORSE_ARMOR.getDefaultInstance());
        horse.moveTo(spawnPos);
        level.addFreshEntity(horse);
        mob.startRiding(horse);
        MobSpawn.mountsMap.put(horse, mob);
    }

    @Override
    public void handleMobTick(Mob mob) {
        DivineUtils.handleMobTick(mob);
        players.stream().filter(player -> player.distanceTo(mob) > 8).forEach(player -> {
            Compute.causeGatherEffect(player, 10, mob.position());
        });
    }

    @Override
    public List<ItemAndRate> getRewardItemList() {
        return List.of(
                new ItemAndRate(DivineIslandItems.DIVINE_GEM_PIECE_0.get(), 0.5),
                new ItemAndRate(DivineIslandItems.DIVINE_SOUL.get(), 1),
                new ItemAndRate(ModItems.GOLD_COIN.get(), 1),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 1)
        );
    }
}
