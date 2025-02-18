package fun.wraq.process.system.endlessinstance.instance;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.endlessinstance.DailyEndlessInstance;
import fun.wraq.process.system.reason.Reason;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class MansionInstance extends DailyEndlessInstance {

    public static MansionInstance instance;
    public static MansionInstance getInstance() {
        if (instance == null) {
            instance = new MansionInstance(Te.s("炼雨府邸一层", CustomStyle.styleOfMoontain),
                    new Vec3(1440, 75, -1056), Tick.min(1), posList.size(), Tick.s(30));
        }
        return instance;
    }

    public MansionInstance(Component name, Vec3 pos, int lastTick, int maxMobNum, int refreshDelayTick) {
        super(name, pos, lastTick, maxMobNum, refreshDelayTick);
    }

    public static List<Vec3> posList = new ArrayList<>() {{
        add(new Vec3(1444, 75, -1084));
        add(new Vec3(1460, 75, -1084));
        add(new Vec3(1468, 75, -1082));
        add(new Vec3(1484, 75, -1084));
        add(new Vec3(1492, 75, -1084));
        add(new Vec3(1508, 75, -1084));
        add(new Vec3(1444, 75, -1072));
        add(new Vec3(1452, 75, -1068));
        add(new Vec3(1502, 75, -1068));
        add(new Vec3(1482, 75, -1064));
        add(new Vec3(1459, 75, -1060));
        add(new Vec3(1496, 75, -1060));
        add(new Vec3(1464, 75, -1052));
        add(new Vec3(1492, 75, -1052));
        add(new Vec3(1508, 75, -1052));
        add(new Vec3(1444, 75, -1048));
        add(new Vec3(1444, 75, -1036));
        add(new Vec3(1452, 75, -1044));
        add(new Vec3(1460, 75, -1036));
        add(new Vec3(1468, 75, -1040));
        add(new Vec3(1476, 75, -1044));
        add(new Vec3(1482, 75, -1042));
        add(new Vec3(1492, 75, -1036));
        add(new Vec3(1508, 75, -1042));
    }};

    @Override
    protected List<Mob> summonMob(Level level) {
        List<Mob> mobs = new ArrayList<>();
        posList.forEach(pos -> {
            if (RandomUtils.nextBoolean()) {
                mobs.add(spawnEvoker(level, pos));
            } else {
                mobs.add(spawnPillager(level, pos));
            }
        });
        return mobs;
    }

    private Mob spawnEvoker(Level level, Vec3 pos) {
        Evoker evoker = new Evoker(EntityType.EVOKER, level);
        MobSpawn.setMobCustomName(evoker, Te.s("府邸唤魔者", CustomStyle.styleOfMoontain),
                getPlayerXpLevel());
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(evoker, getPlayerXpLevel(),
                getPlayerXpLevel() * 10, getPlayerXpLevel(), getPlayerXpLevel(), 0, 0,
                getPlayerXpLevel(), getPlayerXpLevel(), 0, getPlayerXpLevel() * 100, 0.3);
        evoker.moveTo(pos);
        level.addFreshEntity(evoker);
        return evoker;
    }

    private Mob spawnPillager(Level level, Vec3 pos) {
        Pillager pillager = new Pillager(EntityType.PILLAGER, level);
        MobSpawn.setMobCustomName(pillager, Te.s("府邸掠夺者", CustomStyle.styleOfMoontain),
                getPlayerXpLevel());
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(pillager, getPlayerXpLevel(),
                getPlayerXpLevel() * 10, getPlayerXpLevel(), getPlayerXpLevel(), 0, 0,
                getPlayerXpLevel(), getPlayerXpLevel(), 0, getPlayerXpLevel() * 100, 0.3);
        pillager.moveTo(pos);
        level.addFreshEntity(pillager);
        return pillager;
    }

    @Override
    protected void reward(Player player) {
        int count = getKillCount() * getPlayerXpLevel() / 400 + 8;
        InventoryOperation.giveItemStackWithMSG(player, ModItems.GOLD_COIN.get(), count * 3);
        InventoryOperation.giveItemStackWithMSG(player, ModItems.GEM_PIECE.get(), count);
    }

    @Override
    public void onFreshNotice() {
        super.onFreshNotice();
        Player player = Compute.getPlayerByName(getChallengingPlayerName());
        sendFormatMSG(player, Te.s("新的怪物已出现!"));
    }

    @Override
    protected boolean onRightClickTrig(Player player) {
        if (player.isShiftKeyDown()) {
            if (player.experienceLevel < 75) {
                sendFormatMSG(player, Te.s("需要达到", Utils.getLevelDescription(75), "，才能开始挑战."));
                return false;
            }
            if (Reason.getPlayerReasonValue(player) < 20) {
                sendFormatMSG(player, Te.s("需要至少拥有",
                        "20理智", CustomStyle.styleOfFlexible, "，才能开始挑战."));
                return false;
            }
            Reason.addOrCostPlayerReasonValue(player, -20);
            return true;
        }
        return false;
    }

    @Override
    protected List<Component> getTrigConditionDescription() {
        return List.of(
                Te.s("奖励大量", ModItems.GOLD_COIN, "/", ModItems.GEM_PIECE),
                Te.s("手持任意物品shift右击", ChatFormatting.AQUA, "消耗", ChatFormatting.RED,
                        "20理智", CustomStyle.styleOfFlexible, "开始挑战"),
                Te.s("等级需求:", Utils.getLevelDescription(75))
        );
    }
}
