package fun.wraq.series.overworld.cold.sc5.dragon;

import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.jungle.JungleMobSpawnController;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.cold.SuperColdItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SimulateIceDragonSpawnController extends JungleMobSpawnController {

    public static final String MOB_NAME = "驯化过的极寒冰龙";
    public static final Style STYLE = CustomStyle.styleOfIce;
    public static final int XP_LEVEL = 330;

    public static SimulateIceDragonSpawnController instance;

    public static SimulateIceDragonSpawnController getInstance() {
        if (instance == null) {
            instance = new SimulateIceDragonSpawnController(Te.s(MOB_NAME, STYLE),
                    new Vec3(1053, 66, -2758),
                    new Vec3(1110, 90, -2700),
                    new Vec3(1000, 40, -2818),
                    XP_LEVEL, 64, Tick.s(30));
        }
        return instance;
    }

    public SimulateIceDragonSpawnController(Component name, Vec3 descriptionPos,
                                            Vec3 mobUpperBoundary, Vec3 mobLowerBoundary,
                                            int mobXpLevel, double detectionRadius, int refreshInterval) {
        super(name, descriptionPos, mobUpperBoundary, mobLowerBoundary, mobXpLevel, detectionRadius, refreshInterval);
    }

    public static final String DRAGON_MEDAL_DATA_KEY = "DragonMedal";

    @Override
    public void tryToReward(Player player) {
        if (!player.getPersistentData().contains(DRAGON_MEDAL_DATA_KEY)) {
            player.getPersistentData().putBoolean(DRAGON_MEDAL_DATA_KEY, true);
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(SuperColdItems.ICE_DRAGON_MEDAL.get()));
            sendMSG(player, Te.s("你成功完成了极寒冰龙的模拟训练!"));
        }
    }

    @Override
    public MobAttributes getMobAttributes() {
        return IceDragonSpawnController.getAttributes();
    }

    @Override
    public Element.Unit getElementUnit() {
        return new Element.Unit(Element.ice, 10);
    }

    @Override
    public void spawnMob(Level level) {
        EntityIceDragon mob  = new EntityIceDragon(IafEntityRegistry.ICE_DRAGON.get(), level);
        mob.setAgeInDays(49);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), XP_LEVEL);
        MobSpawn.setMobCustomName(mob, Te.s(name, STYLE), XP_LEVEL);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, getMobAttributes());
        mob.moveTo(new Vec3(1053, 66, -2758));
        level.addFreshEntity(mob);
        mobs.add(mob);
        MobSpawn.setCanNotAddSlowDownOrImprison(mob);
    }

    @Override
    public void handleMobTick(Mob mob) {
        if (mob.isDeadOrDying()) {
            return;
        }
        if (mob.tickCount % 80 == 15) {
            rangeEffectAttack(mob);
        }
        if (mob.tickCount % 100 == 88 || mob.tickCount % 100 == 90 || mob.tickCount % 100 == 92) {
            commonAttack(mob);
        }
        super.handleMobTick(mob);
    }

    // 每2s一次，随机选定玩家
    public void rangeEffectAttack(Mob mob) {
        IceDragonSpawnController.skill1(mob, players);
    }

    public void commonAttack(Mob mob) {
        IceDragonSpawnController.skill2(mob, players);
    }

    @Override
    public double modifyMobWithstandDamage(Mob mob, Player player) {
        return mob.distanceTo(player) > 32 ? 0 : 1;
    }

    @Override
    public List<ItemAndRate> getRewardItemList() {
        return List.of();
    }

    @Override
    public List<Component> getSpecialDescription() {
        return IceDragonSpawnController.getDescription();
    }
}
