package fun.wraq.events.mob.jungle;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.sakura.bunker.BunkerItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class BunkerGhastSpawnController extends JungleMobSpawnController {

    public static final String MOB_NAME = "熔魂";
    public static final Style STYLE = CustomStyle.BUNKER_STYLE;
    public static final int XP_LEVEL = 270;

    public static BunkerGhastSpawnController instance;
    public static BunkerGhastSpawnController getInstance() {
        if (instance == null) {
            instance = new BunkerGhastSpawnController(Te.s(MOB_NAME, STYLE), new Vec3(3841, -9, 2001),
                    new Vec3(3922, 20, 2080), new Vec3(3782, -18, 1930), XP_LEVEL, 48, Tick.min(3));
        }
        return instance;
    }

    public BunkerGhastSpawnController(Component name, Vec3 descriptionPos,
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
    public void spawnMob(Level level) {
        Ghast ghast = new Ghast(EntityType.GHAST, level);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(ghast, Te.s(MOB_NAME, STYLE), 270,
                7500, 500, 500, 0.4, 3,
                0.6, 500, 25,
                10 * Math.pow(10, 8), 0.6);
        ghast.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GRASS_BLOCK));
        ghast.moveTo(new Vec3(3841, -5, 2001));
        level.addFreshEntity(ghast);
        mobs.add(ghast);
    }

    @Override
    public List<ItemAndRate> getRewardItemList() {
        return List.of(
                new ItemAndRate(new ItemStack(BunkerItems.BUNKER_SOUL.get(), 6), 1),
                new ItemAndRate(new ItemStack(ModItems.GOLD_COIN.get(), 2), 1),
                new ItemAndRate(new ItemStack(ModItems.GEM_PIECE.get(), 1), 1),
                new ItemAndRate(new ItemStack(ModItems.FireElementPiece0.get(), 8), 1)
        );
    }
}
