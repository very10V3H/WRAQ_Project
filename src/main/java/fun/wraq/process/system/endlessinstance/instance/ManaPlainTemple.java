package fun.wraq.process.system.endlessinstance.instance;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.endlessinstance.DailyEndlessInstance;
import fun.wraq.process.system.endlessinstance.item.EndlessInstanceItems;
import fun.wraq.process.system.reason.Reason;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ManaPlainTemple extends DailyEndlessInstance {

    public static ManaPlainTemple instance;
    public static ManaPlainTemple getInstance() {
        if (instance == null) instance = new ManaPlainTemple(Te.s("炼魔涌溢", CustomStyle.styleOfMana),
                new Vec3(1573.5, 54, 149.5), 1200, 8);
        return instance;
    }

    public ManaPlainTemple(Component name, Vec3 pos, int lastTick, int maxMobNum) {
        super(name, pos, lastTick, maxMobNum);
    }

    List<Vec3> spawnPos = List.of(
            new Vec3(1575, 54, 130),
            new Vec3(1583, 54, 129),
            new Vec3(1574, 54, 140),
            new Vec3(1582, 54, 139),
            new Vec3(1573, 54, 149),
            new Vec3(1582, 54, 149),
            new Vec3(1573, 54, 160),
            new Vec3(1583, 54, 159)
    );
    int lastSpawnIndex = -1;

    @Override
    protected Mob summonMob(Level level) {
        Skeleton skeleton = new Skeleton(EntityType.SKELETON, level);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(skeleton, getPlayerXpLevel(),
                getPlayerXpLevel() * 10, getPlayerXpLevel(), getPlayerXpLevel(), 0, 0,
                getPlayerXpLevel(), getPlayerXpLevel(), 0, getPlayerXpLevel() * 100, 0.3);
        Style style = CustomStyle.styleOfMana;
        MobSpawn.setMobCustomName(skeleton, Te.s("炼魔遗骸", CustomStyle.styleOfMana), getPlayerXpLevel());
        ItemStack[] itemStacks = {new ItemStack(Items.IRON_HELMET), new ItemStack(Items.LEATHER_CHESTPLATE),
                new ItemStack(Items.LEATHER_LEGGINGS), new ItemStack(Items.CHAINMAIL_BOOTS)};
        EquipmentSlot[] equipmentSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (int i = 0; i < itemStacks.length; i++) {
            ItemStack equip = itemStacks[i];
            equip.enchant(Enchantments.UNBREAKING, 1);
            CompoundTag tag = equip.getTag();
            CompoundTag tag1 = new CompoundTag();
            tag1.putInt("color", style.getColor().getValue());
            tag.put("display", tag1);
            skeleton.setItemSlot(equipmentSlots[i], equip);
        }
        skeleton.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
        lastSpawnIndex = (lastSpawnIndex + 1) % spawnPos.size();
        skeleton.moveTo(spawnPos.get(lastSpawnIndex));
        level.addFreshEntity(skeleton);
        MobSpawn.setMobDropList(skeleton, getDropList());
        return skeleton;
    }

    private static List<ItemAndRate> dropList = new ArrayList<>();
    public static List<ItemAndRate> getDropList() {
        if (dropList.isEmpty()) {
            dropList.addAll(List.of(
                    new ItemAndRate(EndlessInstanceItems.MANA_PLAIN_PLANT.get(), 0.06)
            ));
        }
        return dropList;
    }

    @Override
    protected void reward(Player player) {
        ItemStack stack = new ItemStack(ModItems.RevelationBook.get());
        stack.setCount(5 + Math.min(20, getKillCount() / 15));
        InventoryOperation.giveItemStackWithMSG(player, stack);
    }

    @Override
    protected boolean onRightClickTrig(Player player) {
        if (player.isShiftKeyDown()) {
            if (Reason.getPlayerReasonValue(player) < 20) {
                sendFormatMSG(player, Te.s("理智", CustomStyle.styleOfFlexible, "不足"));
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
                Te.s("手持任意物品shift右击", ChatFormatting.AQUA, "消耗", ChatFormatting.RED, "20理智", "开始挑战")
        );
    }
}
