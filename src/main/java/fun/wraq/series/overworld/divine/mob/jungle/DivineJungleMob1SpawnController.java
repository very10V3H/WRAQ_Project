package fun.wraq.series.overworld.divine.mob.jungle;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.jungle.JungleMobSpawnController;
import fun.wraq.process.func.damage.Damage;
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

public class DivineJungleMob1SpawnController extends JungleMobSpawnController {

    public static final String MOB_NAME = "圣光子爵";
    public static final Style STYLE = CustomStyle.DIVINE_STYLE;
    public static final int XP_LEVEL = 300;
    public static final Vec3 spawnPos = new Vec3(2473, 87, 600);

    public static DivineJungleMob1SpawnController instance;

    public static DivineJungleMob1SpawnController getInstance() {
        if (instance == null) {
            instance = new DivineJungleMob1SpawnController(Te.s(MOB_NAME, STYLE), new Vec3(2473, 87, 600),
                    new Vec3(2489, 156, 615), new Vec3(2452, 68, 579), XP_LEVEL, 32, Tick.min(3));
        }
        return instance;
    }

    public DivineJungleMob1SpawnController(Component name, Vec3 descriptionPos,
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
        return new MobAttributes(24000, 1100, 1100, 0.4, 3, 0.6, 750, 25, 20 * Math.pow(10, 8), 0.45);
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
        horse.setItemSlot(EquipmentSlot.CHEST, Items.GOLDEN_HORSE_ARMOR.getDefaultInstance());
        horse.moveTo(spawnPos);
        level.addFreshEntity(horse);
        mob.startRiding(horse);
        MobSpawn.mountsMap.put(horse, mob);
    }

    @Override
    public void handleMobTick(Mob mob) {
        DivineUtils.handleMobTick(mob);
        if (mob.tickCount % 10 == 0) {
            Player player = null;
            double distance = Double.MAX_VALUE;
            for (Player eachPlayer : players) {
                if (eachPlayer.distanceTo(mob) < distance) {
                    player = eachPlayer;
                    distance = eachPlayer.distanceTo(mob);
                }
            }
            if (player != null) {
                DivineUtils.createDivineParticle(player.level(), mob.getEyePosition(),
                        player.position().add(0, 1, 0));
                Damage.causeAttackDamageToPlayer(mob, player, 24000);
            }
        }
    }

    @Override
    public List<ItemAndRate> getRewardItemList() {
        return List.of(
                new ItemAndRate(DivineIslandItems.DIVINE_GEM_PIECE_1.get(), 0.5),
                new ItemAndRate(DivineIslandItems.DIVINE_ARROW.get(), 1),
                new ItemAndRate(ModItems.SILVER_COIN.get(), 9),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 1.1),
                new ItemAndRate(Element.getPiece0ItemMap().get(DivineUtils.currentDayElement), 6)
        );
    }
}
