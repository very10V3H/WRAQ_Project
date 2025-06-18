package fun.wraq.events.mob.chapter4_end;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.loot.C4LootItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnderManSpawnController extends MobSpawnController {

    public static String mobName = "终界使者";
    private static EnderManSpawnController instance;

    public static EnderManSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(18, 59, -67),
                    new Vec3(46, 58, -54),
                    new Vec3(69, 59, -41),
                    new Vec3(28, 62, -38),
                    new Vec3(11, 65, -31),
                    new Vec3(71, 60, -21),
                    new Vec3(47, 64, -15),
                    new Vec3(11, 65, -10),
                    new Vec3(71, 61, 1),
                    new Vec3(12, 64, 11),
                    new Vec3(44, 63, 16),
                    new Vec3(67, 61, 26),
                    new Vec3(23, 63, 37),
                    new Vec3(54, 61, 49)
            );
            instance = new EnderManSpawnController(spawnPos, 99, 62, -8, -114, world, 80);
        }
        return instance;
    }

    public EnderManSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                   int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("终界使者", CustomStyle.styleOfEnd), canSpawnPos, boundaryUpX, boundaryUpZ, boundaryDownX, boundaryDownZ, level, averageLevel);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(220, 50, 50, 0.35, 3, 0.2, 5, 15, 9900, 0.25);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        EnderMan enderMan = new EnderMan(EntityType.ENDERMAN, this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        // 设置颜色与名称
        Style style = CustomStyle.styleOfEnd;
        MobSpawn.setMobCustomName(enderMan, Te.s(mobName, style), xpLevel);
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(enderMan), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(enderMan, getMobAttributes());
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(enderMan), list);
        return enderMan;
    }

    @Override
    public void eachMobTick(Mob mob) {
        if (mob.isAlive()) {
            List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(mob.position(), 5, 5, 5));
            for (Player player : playerList) {
                if (player.position().distanceTo(mob.position()) <= 2.8) {
                    player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 3));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 3));
                    player.addEffect(new MobEffectInstance(MobEffects.WITHER, 40, 10));
                }
            }
        }
    }

    @Override
    public Element.Unit getElement() {
        return null;
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(ModItems.RECALL_PIECE.get(), 0.8));
            add(new ItemAndRate(ModItems.SILVER_COIN.get(), 0.625));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.05));
            add(new ItemAndRate(ModItems.END_PEARL.get(), 0.01));
            add(new ItemAndRate(NewRuneItems.END_NEW_RUNE.get(), 0.001));
            add(new ItemAndRate(C4LootItems.ENDERMAN_SWORD.get(), 0.005));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "EnderMan";
    }
}
