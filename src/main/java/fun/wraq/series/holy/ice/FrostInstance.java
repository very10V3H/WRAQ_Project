package fun.wraq.series.holy.ice;

import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrostmaw;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class FrostInstance extends NoTeamInstance {

    private static FrostInstance instance;

    public static String mobName = "霜冻巨兽";
    public Mob boss;
    public static Style style = CustomStyle.styleOfIce;
    public static final double MAX_HEALTH = 20 * Math.pow(10, 8);
    public static final int XP_LEVEL = 300;

    public static FrostInstance getInstance() {
        if (instance == null) {
            instance = new FrostInstance(new Vec3(2076, 222, 1973), 32, 60,
                    new Vec3(2076, 222, 1973), Te.s(mobName, style));
        }
        return instance;
    }

    public FrostInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name, XP_LEVEL);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
        if (boss == null || boss.tickCount == 0 || boss.isDeadOrDying()) return;
        if (boss.tickCount % 20 == 0) {
            players.stream().min(new Comparator<Player>() {
                @Override
                public int compare(Player o1, Player o2) {
                    return (int) (o1.distanceTo(boss) - o2.distanceTo(boss));
                }
            }).ifPresent(player -> {
                boss.setTarget(player);
                boss.getMoveControl().setWantedPosition(
                        player.position().x,
                        player.position().y,
                        player.position().z,
                        1);
            });
        }
    }

    @Override
    public MobAttributes getMainMobAttributes() {
        return new MobAttributes(24000, 1100, 1100, 0.4, 3, 0.6, 750, 25, 20 * Math.pow(10, 8), 0.45);
    }

    @Override
    public Element.Unit getElementUnit() {
        return new Element.Unit(Element.ice, 8);
    }

    @Override
    public void summonModule(Level level) {
        EntityFrostmaw mob = new EntityFrostmaw(EntityHandler.FROSTMAW.get(), level);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, Te.s(name, style), XP_LEVEL, getMainMobAttributes());
        mob.moveTo(pos);
        level.addFreshEntity(mob);
        mobList.add(mob);
        boss = mob;
        MobSpawn.setCanNotAddSlowDownOrImprison(mob);
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getFinalRewardCondition(player);
    }

    @Override
    public Component allowRewardCondition() {
        return NoTeamInstanceModule.getFinalAllowRewardCondition();
    }

    @Override
    public int getRewardNeedItemCount() {
        return 8;
    }

    public List<ItemAndRate> getRewardList() {
        return List.of(
                new ItemAndRate(IceHolyItems.CHEST.get(), 1),
                new ItemAndRate(ModItems.WORLD_SOUL_2.get(), 2),
                new ItemAndRate(ModItems.GOLD_COIN_BAG.get(), 1),
                new ItemAndRate(ModItems.ICE_ELEMENT_PIECE_1.get(), 0.25)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "FrostMaw";
    }

    @Override
    public double onMobWithStandDamageRate(Player player, Mob mob) {
        if (player.distanceTo(mob) > 16) {
            return 0;
        }
        return super.onMobWithStandDamageRate(player, mob);
    }

    @Override
    public List<Component> getIntroduction() {
        return List.of(
                Te.s(" 1.", style, name, "拥有", "20%吸血抵抗", ChatFormatting.RED),
                Te.s(" 2.", style, name, "免疫", CustomStyle.styleOfStone, "16格以外的伤害")
        );
    }
}
