package fun.wraq.process.system.endlessinstance.instance;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.endlessinstance.DailyEndlessInstance;
import fun.wraq.process.system.endlessinstance.item.EndlessInstanceItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.mcreator.borninchaosv.entity.BarrelZombieEntity;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class EasternTower extends DailyEndlessInstance {

    private static EasternTower instance;
    public static EasternTower getInstance() {
        if (instance == null) instance = new EasternTower(new Vec3(2336.5, 148, 17.5), 1200, 4);
        return instance;
    }

    public static Component name = Component.literal("东洋塔").withStyle(CustomStyle.styleOfHusk);

    private EasternTower(Vec3 pos, int lastTick, int maxMobNum) {
        super(name, pos, lastTick, maxMobNum, 5);
    }

    public static String mobName = "无尽熵增怪物";

    @Override
    protected List<Mob> summonMob(Level level) {
        BarrelZombieEntity mob = new BarrelZombieEntity(BornInChaosV1ModEntities.BARREL_ZOMBIE.get(), level);
        int levelDifference = getPlayerXpLevel() - 150;
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, getPlayerXpLevel(),
                1000 + levelDifference * 5, 200 + levelDifference * 5, 200 + levelDifference * 5,
                0.5, 10, 0.01 * levelDifference, 2000 * 100 * levelDifference,
                0, getMobMaxHealth(), 0.2);
        Style style = CustomStyle.styleOfHusk;
        MobSpawn.setMobCustomName(mob, Te.s(mobName, style), getPlayerXpLevel());
        Random random = new Random();
        mob.moveTo(getPos().add(0.5 - random.nextDouble(),
                0.5 - random.nextDouble(), 0.5 - random.nextDouble()));
        level.addFreshEntity(mob);
        return List.of(mob);
    }

    @Override
    protected void reward(Player player) {
        List.of(new ItemStack(ModItems.GOLD_COIN.get(), getKillCount() / 10),
                new ItemStack(EndlessInstanceItems.ENDLESS_INSTANCE_CORE.get(), Math.min(16, getKillCount() / 20)))
                .forEach(itemStack -> InventoryOperation.giveItemStack(player, itemStack));
    }

    @Override
    protected boolean onRightClickTrig(Player player) {
        if (player.getMainHandItem().is(EndlessInstanceItems.EASTERN_TOWER_PAPER.get())) {
            Compute.playerItemUseWithRecord(player);
            return true;
        }
        sendFormatMSG(player, Te.s("手持", EndlessInstanceItems.EASTERN_TOWER_PAPER, "右击来开始挑战"));
        return false;
    }

    @Override
    protected List<Component> getTrigConditionDescription() {
        return List.of(
                Te.s("手持", EndlessInstanceItems.EASTERN_TOWER_PAPER, "右击来开始挑战")
        );
    }

    private double getMobMaxHealth() {
        double num_10k = 10000;
        double killCount = Math.min(200, getKillCount());
        double rate = (killCount / 200.0);
        if (getPlayerXpLevel() < 170) {
            return (10 + 20 * rate) * num_10k;
        } else if (getPlayerXpLevel() < 190) {
            return (30 + 270 * rate) * num_10k;
        } else if (getPlayerXpLevel() < 210) {
            return (100 + 900 * rate) * num_10k;
        } else {
            return (1000 + 2700 * rate) * num_10k;
        }
    }
}
