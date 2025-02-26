package fun.wraq.events.mob.moontain;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.process.system.ore.OreItems;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.moontain.MoontainItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoontainMinerSpawnController extends MobSpawnController {

    public static String mobName = "望山矿工";
    private static MoontainMinerSpawnController instance;

    public static MoontainMinerSpawnController getInstance(Level world) {
        if (instance == null) {
            List<Vec3> spawnPos = List.of(
                    new Vec3(2040, 171, -816),
                    new Vec3(2051, 169, -825),
                    new Vec3(2065, 162, -828),
                    new Vec3(2080, 161, -828),
                    new Vec3(2092, 163, -825),
                    new Vec3(2104, 164, -824),
                    new Vec3(2112, 164, -816),
                    new Vec3(2123, 163, -812),
                    new Vec3(2134, 161, -810),
                    new Vec3(2150, 156, -804),
                    new Vec3(2162, 148, -792)
            );
            instance = new MoontainMinerSpawnController(spawnPos,
                    2200, 190, -780,
                    2000, 120, -848,
                    world, 230);
        }
        return instance;
    }

    public MoontainMinerSpawnController(List<Vec3> canSpawnPos, int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                                        int boundaryDownX, int boundaryDownY, int boundaryDownZ, Level level, int averageLevel) {
        super(Te.s("望山矿工", CustomStyle.styleOfMoontain), canSpawnPos, boundaryUpX, boundaryUpY, boundaryUpZ, boundaryDownX, boundaryDownY, boundaryDownZ, level, averageLevel);
    }

    @Override
    public Mob mobItemAndAttributeSet() {
        ZombieVillager zombieVillager = new ZombieVillager(EntityType.ZOMBIE_VILLAGER, this.level);

        Random random = new Random();
        int xpLevel = Math.max(1, averageLevel + 5 - random.nextInt(11));

        // 设置颜色与名称
        Style style = CustomStyle.styleOfMoontain;
        MobSpawn.setMobCustomName(zombieVillager, Component.literal(mobName).withStyle(style), xpLevel);
        zombieVillager.setVillagerData(new VillagerData(VillagerType.SNOW, VillagerProfession.ARMORER, 0));

        // 需要验证
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(zombieVillager, xpLevel, 3500, 210,
                210, 0.4, 3, 0.3, 80, 25,
                600 * Math.pow(10, 4), 0.4);

        // 设置物品
        ItemStack[] itemStacks = {new ItemStack(Items.IRON_HELMET), new ItemStack(Items.CHAINMAIL_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.LEATHER_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            CompoundTag tag = itemStacks[i].getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            zombieVillager.setItemSlot(equipmentSlots[i], itemStacks[i]);
        }

        ItemStack diamondPickaxe = Items.DIAMOND_PICKAXE.getDefaultInstance();
        diamondPickaxe.enchant(Enchantments.UNBREAKING, 5);
        zombieVillager.setItemSlot(EquipmentSlot.MAINHAND, diamondPickaxe);
        zombieVillager.setItemSlot(EquipmentSlot.OFFHAND, Items.TORCH.getDefaultInstance());

        // 设置掉落
        List<ItemAndRate> list = getDropList();

        // 添加至掉落物列表
        MobSpawn.dropList.put(MobSpawn.getMobOriginName(zombieVillager), list);
        return zombieVillager;
    }

    @Override
    public void tick() {

    }

    @Override
    public List<ItemAndRate> getDropList() {
        return new ArrayList<>() {{
            add(new ItemAndRate(OreItems.MOONTAIN_ORE_ITEM.get(), 0.1));
            add(new ItemAndRate(MoontainItems.STONE_FRAGMENT.get(), 0.4));
            add(new ItemAndRate(ModItems.silverCoin.get(), 1));
            add(new ItemAndRate(ModItems.GEM_PIECE.get(), 0.06));
        }};
    }

    @Override
    public String getKillCountDataKey() {
        return "MoontainMiner";
    }
}
