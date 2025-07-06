package fun.wraq.events.mob.instance.instances.tower;

import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.hostile.BoneSpider;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ender_Golem_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.obscuria.aquamirae.common.entities.TorturedSoul;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Calendar;
import java.util.List;

public class ManaTowerEachFloorMob {
    public static final int MOB_XP_LEVEL = 250;
    public static final double BASE_MAX_HEALTH = 10000 * Math.pow(10, 4);
    public static Style style = CustomStyle.MANA_TOWER_STYLE;
    public static final String FLOOR_1_MOB_NAME = "低等魔物";
    public static final List<Vec3> FLOOR_1_MOB_POS = List.of(
            new Vec3(1511, 109, -552),
            new Vec3(1528, 109, -535),
            new Vec3(1511, 109, -518),
            new Vec3(1494, 109, -535)
    );

    public static double getBaseMaxHealth(int count) {
        return BASE_MAX_HEALTH * (1 + count * 0.1);
    }

    public static Mob spawnFloor1Mob(Level level, Vec3 pos, int count) {
        Zombie mob = new Zombie(EntityType.ZOMBIE, level);
        mob.setBaby(true);
        MobSpawn.MobBaseAttributes
                .setMobBaseAttributes(mob, Te.s(FLOOR_1_MOB_NAME, style), MOB_XP_LEVEL,
                        4000, 300, 300, 0.4, 5,
                        0.5, 250, 25,
                        getBaseMaxHealth(count), 0.4);
        setUniformArmor(mob);
        mob.setItemInHand(InteractionHand.MAIN_HAND, Compute.getSimpleFoiledItemStack(Items.STONE_SWORD));
        mob.moveTo(pos);
        level.addFreshEntity(mob);
        return mob;
    }

    public static final String FLOOR_2_MOB_NAME = "魔塔哨卫";
    public static final List<Vec3> FLOOR_2_MOB_POS = List.of(
            new Vec3(1511, 135, -553),
            new Vec3(1524, 135, -548),
            new Vec3(1529, 135, -535),
            new Vec3(1524, 135, -522),
            new Vec3(1511, 135, -517),
            new Vec3(1498, 135, -522),
            new Vec3(1493, 135, -535),
            new Vec3(1498, 135, -548)
    );
    public static Mob spawnFloor2Mob(Level level, Vec3 pos, int count) {
        Stray mob = new Stray(EntityType.STRAY, level);
        MobSpawn.MobBaseAttributes
                .setMobBaseAttributes(mob, Te.s(FLOOR_2_MOB_NAME, style), MOB_XP_LEVEL,
                        4000, 300, 300, 0.4, 5,
                        0.5, 250, 25,
                        getBaseMaxHealth(count), 0.4);
        setUniformArmor(mob);
        mob.setItemInHand(InteractionHand.MAIN_HAND, Compute.getSimpleFoiledItemStack(Items.BOW));
        mob.moveTo(pos);
        level.addFreshEntity(mob);
        return mob;
    }

    public static final String FLOOR_3_MOB_NAME = "元素魔物";
    public static final List<Vec3> FLOOR_3_MOB_POS = List.of(
            new Vec3(1511, 158, -544),
            new Vec3(1520, 158, -535),
            new Vec3(1511, 158, -526),
            new Vec3(1502, 158, -535)
    );
    public static Mob spawnFloor3Mob(Level level, Vec3 pos, int count) {
        TorturedSoul mob = new TorturedSoul(AquamiraeEntities.TORTURED_SOUL.get(), level);
        MobSpawn.MobBaseAttributes
                .setMobBaseAttributes(mob, Te.s(FLOOR_3_MOB_NAME, style), MOB_XP_LEVEL,
                        4000, 300, 300, 0.4, 5,
                        0.5, 250, 25,
                        getBaseMaxHealth(count), 0.4);
        mob.moveTo(pos);
        level.addFreshEntity(mob);
        return mob;
    }

    public static String getCurrentFloor3MobElementType() {
        int index = Calendar.getInstance().get(Calendar.DATE) % 7;
        return Element.elementList.get(index);
    }

    public static void provideElementOnFloor3Mob(Mob mob) {
        Element.provideElement(mob, getCurrentFloor3MobElementType(), 500);
    }

    public static final String FLOOR_4_MOB_NAME = "骸骨蜘蛛";
    public static Vec3 FLOOR_4_MOB_POS = new Vec3(1511, 188, -540);
    public static Mob spawnFloor4Mob(Level level, Vec3 pos, int count) {
        BoneSpider mob = new BoneSpider(ModEntityType.BONE_SPIDER.get(), level);
        MobSpawn.MobBaseAttributes
                .setMobBaseAttributes(mob, Te.s(FLOOR_4_MOB_NAME, style), MOB_XP_LEVEL,
                        15000, 300, 300, 0.4, 5,
                        0.5, 250, 25,
                        getBaseMaxHealth(count) * 2, 0.4);
        mob.moveTo(pos);
        level.addFreshEntity(mob);
        return mob;
    }

    public static final String FLOOR_5_MOB_NAME = "突变魔兽";
    public static Vec3 FLOOR_5_MOB_POS = new Vec3(1511, 218, -540);
    public static Mob spawnFloor5Mob(Level level, Vec3 pos, int count) {
        Ender_Golem_Entity mob = new Ender_Golem_Entity(ModEntities.ENDER_GOLEM.get(), level);
        MobSpawn.MobBaseAttributes
                .setMobBaseAttributes(mob, Te.s(FLOOR_5_MOB_NAME, style), MOB_XP_LEVEL,
                        4000, 400, 400, 0.4, 5,
                        0.5, 250, 25,
                        getBaseMaxHealth(count) * 20, 0.4);
        mob.moveTo(pos);
        level.addFreshEntity(mob);
        return mob;
    }

    public static void setUniformArmor(Mob mob) {
        MobSpawn.setStainArmorOnMob(mob, style);
        mob.setItemSlot(EquipmentSlot.CHEST, Compute.getSimpleFoiledItemStack(Items.NETHERITE_CHESTPLATE));
        mob.setItemSlot(EquipmentSlot.LEGS, Compute.getSimpleFoiledItemStack(Items.CHAINMAIL_LEGGINGS));
    }
}
