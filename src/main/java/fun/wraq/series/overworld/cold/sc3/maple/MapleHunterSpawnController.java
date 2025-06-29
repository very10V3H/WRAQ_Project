package fun.wraq.series.overworld.cold.sc3.maple;

import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.hostile.HauntedArmor;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.fight.MonsterAttackEvent;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.cold.SuperColdItems;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class MapleHunterSpawnController extends MobSpawnController {

    public static String mobName = "枫林猎手";
    public static Style style = CustomStyle.styleOfIce;
    private static MapleHunterSpawnController instance;

    public static MapleHunterSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2676, 142, -3626),
                    new Vec3(2666, 150, -3644),
                    new Vec3(2706, 136, -3648),
                    new Vec3(2644, 153, -3654),
                    new Vec3(2668, 152, -3667),
                    new Vec3(2695, 144, -3668),
                    new Vec3(2707, 138, -3696),
                    new Vec3(2673, 149, -3695),
                    new Vec3(2685, 147, -3708),
                    new Vec3(2668, 146, -3726)
            );
            instance = new MapleHunterSpawnController(spawnPos, 2747, -3588, 2618, -3746, world, 310);
        }
        return instance;
    }

    public MapleHunterSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpZ,
                                      int boundaryDownX, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s(mobName, style), canSpawnPos, canSpawnPos.size() * 3, boundaryUpX, boundaryUpZ,
                boundaryDownX, boundaryDownZ, 32, level, 1, averageLevel, 24);
    }

    @Override
    public MobAttributes getMobAttributes() {
        return new MobAttributes(18000, 950, 950, 0.4, 3, 0.6, 700, 25, 15000 * Math.pow(10, 4), 0.45);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        HauntedArmor mob = new HauntedArmor(ModEntityType.HAUNTED_ARMOR.get(), this.level);
        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        Style style = CustomStyle.styleOfSnow;
        // 设置属性
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, Te.s(mobName, style), xpLevel, getMobAttributes());
        // 设置物品
        mob.setItemSlot(EquipmentSlot.HEAD,
                new ItemStack(com.Polarice3.Goety.common.items.ModItems.CURSED_KNIGHT_HELMET.get()));
        mob.setItemSlot(EquipmentSlot.CHEST,
                new ItemStack(com.Polarice3.Goety.common.items.ModItems.CURSED_KNIGHT_CHESTPLATE.get()));
        mob.setItemSlot(EquipmentSlot.LEGS,
                new ItemStack(com.Polarice3.Goety.common.items.ModItems.CURSED_KNIGHT_LEGGINGS.get()));
        mob.setItemSlot(EquipmentSlot.FEET,
                new ItemStack(com.Polarice3.Goety.common.items.ModItems.CURSED_KNIGHT_BOOTS.get()));
        mob.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        mob.setItemSlot(EquipmentSlot.MAINHAND,
                new ItemStack(com.Polarice3.Goety.common.items.ModItems.FELL_BLADE.get()));
        // 设置掉落
        List<ItemAndRate> list = getDropList();
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(mob), list);
        return mob;
    }

    @Override
    public void eachMobTick(Mob mob) {
        if (mob.isDeadOrDying()) {
            return;
        }
        if (mob.getMaxHealth() < 1000) {
            mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(getMobAttributes().maxHealth);
            mob.heal(mob.getMaxHealth());
        }
        if (mob.tickCount % 20 == (mobList.indexOf(mob) % 20)) {
            commonAttack(mob);
        }
        Compute.mobHealthRecover(mob, 0.02);
    }

    @Override
    public Element.Unit getElement() {
        return new Element.Unit(Element.ice, 8);
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return List.of(
                new ItemAndRate(SuperColdItems.MAPLE_SOUL.get(), 0.3),
                new ItemAndRate(ModItems.SILVER_COIN.get(), 3),
                new ItemAndRate(ModItems.GEM_PIECE.get(), 0.08),
                new ItemAndRate(ModItems.ICE_ELEMENT_PIECE_0.get(), 0.5)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "MapleHunter";
    }

    public void commonAttack(Mob mob) {
        Player player = Compute.getNearestPlayer(mob, 16);
        if (player != null) {
            MonsterAttackEvent.causeCommonAttackToPlayer(mob, player);
            ParticleProvider.createLineEffectParticle(mob.level(), mob, player, CustomStyle.styleOfStone);
        }
    }
}
