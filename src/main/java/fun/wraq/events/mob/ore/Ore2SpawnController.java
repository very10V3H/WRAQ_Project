package fun.wraq.events.mob.ore;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.ore.OreItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

import java.util.List;
import java.util.Random;

public class Ore2SpawnController extends MobSpawnController {

    public static String mobName = "被遗忘的晶钻矿工";
    private static Ore2SpawnController instance;

    public static Ore2SpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(1388, 11, -2887),
                    new Vec3(1403, 11, -2869),
                    new Vec3(1427, 11, -2862),
                    new Vec3(1380, 11, -2855),
                    new Vec3(1397, 11, -2850),
                    new Vec3(1413, 11, -2848),
                    new Vec3(1414, 11, -2835),
                    new Vec3(1430, 11, -2819),
                    new Vec3(1452, 13, -2814)
            );
            instance = new Ore2SpawnController(spawnPos, 1458, 30, -2810, 1372, -5, -2899, world, 112);
        }
        return instance;
    }

    public Ore2SpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                               int boundaryDownX, int boundaryDownY, int boundaryDownZ,
                               Level level, int averageLevel) {
        super(Te.s("被遗忘的晶钻矿工", CustomStyle.styleOfIce), canSpawnPos, boundaryUpX, boundaryUpY, boundaryUpZ, boundaryDownX, boundaryDownY, boundaryDownZ,
                15, level, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        ZombieVillager zombieVillager = new ZombieVillager(EntityType.ZOMBIE_VILLAGER, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));
        Style style = CustomStyle.styleOfIce;

        MobSpawn.setMobCustomName(zombieVillager, Component.literal(mobName).withStyle(style), xpLevel);

        // 需要验证
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(zombieVillager), xpLevel);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombieVillager, 500, 60, 60, 0.35,
                3, 0.2, 5, 15, 100000, 0.3);

        zombieVillager.getAttribute(ForgeMod.SWIM_SPEED.get()).setBaseValue(3);
        // 设置物品
        zombieVillager.setItemSlot(EquipmentSlot.HEAD, Items.DIAMOND_HELMET.getDefaultInstance());
        zombieVillager.setItemInHand(InteractionHand.MAIN_HAND, Items.DIAMOND_PICKAXE.getDefaultInstance());
        ItemStack[] itemStacks = {new ItemStack(Items.LEATHER_CHESTPLATE)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.CHEST};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            zombieVillager.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }
        zombieVillager.setVillagerData(new VillagerData(VillagerType.SNOW, VillagerProfession.TOOLSMITH, 3));

        // 设置掉落
        List<ItemAndRate> list = getDropList();
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(zombieVillager), list);
        return zombieVillager;
    }

    @Override
    public void tick() {
        mobList.forEach(mob -> {
            Element.ElementProvider(mob, Tick.get() % 100 < 50 ? Element.water : Element.stone, 3);
            mob.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING));
        });
    }

    @Override
    public List<ItemAndRate> getDropList() {
        return List.of(
                new ItemAndRate(ModItems.StoneElementPiece0.get(), 0.03),
                new ItemAndRate(ModItems.WaterElementPiece0.get(), 0.03),
                new ItemAndRate(Items.RAW_GOLD, 0.08),
                new ItemAndRate(Items.DIAMOND, 0.05),
                new ItemAndRate(Items.EMERALD, 0.05),
                new ItemAndRate(Items.REDSTONE, 0.2),
                new ItemAndRate(Items.LAPIS_LAZULI, 0.2),
                new ItemAndRate(OreItems.WRAQ_ORE_2_ITEM.get(), 0.01)
        );
    }

    @Override
    public String getKillCountDataKey() {
        return "Ore2";
    }
}
