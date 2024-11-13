package fun.wraq.common.util;

import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.events.mob.loot.RandomLootEquip;
import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemAndRate {

    private ItemStack itemStack;
    private final double rate;

    public ItemAndRate(ItemStack itemStack, double rate) {
        this.itemStack = itemStack;
        this.rate = rate;
    }

    public ItemAndRate(Item item, double rate) {
        this.itemStack = new ItemStack(item);
        this.rate = rate;
    }

    public void give(Player player) {
        if (rate > 1) {
            int num = (int) Math.floor(rate);
            itemStack.setCount(num);
            InventoryOperation.itemStackGive(player, itemStack);
        } else {
            InventoryOperation.giveItemStackByRate(itemStack, rate, player);
        }
    }

    public void giveByNewObject(Player player) {
        Random random = new Random();
        ItemStack newStack = itemStack.copy();
        int num = (int) Math.floor(rate);
        if (random.nextDouble() < rate) num ++;
        newStack.setCount(itemStack.getCount() * num);
        newStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        InventoryOperation.itemStackGive(player, newStack);
    }

    public void drop(Mob mob) {
        ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, mob.level());
        itemEntity.setItem(itemStack);
        itemEntity.setPos(mob.position().add(0, 0.5, 0));
        itemEntity.setPickUpDelay(8);
        Random rand = new Random();
        itemEntity.setDeltaMovement(rand.nextDouble(0.2) - 0.1, 0.2, rand.nextDouble(0.2) - 0.1);
        mob.level().addFreshEntity(itemEntity);
    }

    public void drop(Mob mob, double num) {
        ItemStack dropItemStack = new ItemStack(itemStack.getItem());
        Random rand = new Random();
        double finalRate = rate * num;
        if (finalRate < 1 && rand.nextDouble() >= finalRate) return;
        if (finalRate > 1) {
            dropItemStack.setCount((int) finalRate);
            if (rand.nextDouble() < finalRate % 1) {
                dropItemStack.setCount(dropItemStack.getCount() + 1);
            }
        }
        summonItemEntity(dropItemStack, mob.position(), mob.level());
    }

    public boolean send(Player player, double num) {
        ItemStack dropItemStack;
        if (itemStack.getItem() instanceof RandomCurios randomCurios) {
            randomCurios.setAttribute(itemStack);
        }
        if (itemStack.getItem() instanceof RandomCurios || itemStack.getItem() instanceof RandomLootEquip)
            dropItemStack = itemStack;
        else dropItemStack = new ItemStack(itemStack.getItem());
        dropItemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        Random rand = new Random();
        double finalRate = rate * num;
        if (finalRate < 1 && rand.nextDouble() >= finalRate) return false;
        if (finalRate > 1) {
            dropItemStack.setCount((int) finalRate);
            if (rand.nextDouble() < finalRate % 1) {
                dropItemStack.setCount(dropItemStack.getCount() + 1);
            }
        }
        InventoryOperation.itemStackGive(player, dropItemStack);
        return true;
    }

    public boolean sendWithMSG(Player player, double num) {
        ItemStack dropItemStack;
        if (itemStack.getItem() instanceof RandomCurios randomCurios) {
            randomCurios.setAttribute(itemStack);
        }
        if (itemStack.getItem() instanceof RandomCurios || itemStack.getItem() instanceof RandomLootEquip)
            dropItemStack = itemStack;
        else dropItemStack = new ItemStack(itemStack.getItem());
        dropItemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        Random rand = new Random();
        double finalRate = rate * num;
        if (finalRate < 1 && rand.nextDouble() >= finalRate) return false;
        if (finalRate > 1) {
            dropItemStack.setCount((int) finalRate);
            if (rand.nextDouble() < finalRate % 1) {
                dropItemStack.setCount(dropItemStack.getCount() + 1);
            }
        }
        InventoryOperation.itemStackGiveWithMSG(player, dropItemStack);
        return true;
    }

    public static void summonItemEntity(ItemStack itemStack, Vec3 pos, Level level) {
        summonItemEntity(itemStack, pos, level, 8);
    }

    public static void summonItemEntity(ItemStack itemStack, Vec3 pos, Level level, int pickUpDelay) {
        ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, level);
        itemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        itemEntity.setItem(itemStack);
        itemEntity.setPos(pos.add(0, 0.5, 0));
        itemEntity.setPickUpDelay(pickUpDelay);
        Random rand = new Random();
        itemEntity.setDeltaMovement(rand.nextDouble(0.2) - 0.1, 0.2, rand.nextDouble(0.2) - 0.1);
        level.addFreshEntity(itemEntity);
    }

    public double getRate() {
        return rate;
    }

    public boolean dropWithBounding(Mob mob, double num, Player player) {
        ItemStack dropItemStack;
        if (itemStack.getItem() instanceof RandomCurios randomCurios) {
            randomCurios.setAttribute(itemStack);
        }

        if (itemStack.getItem() instanceof RandomCurios
                || itemStack.getItem() instanceof RandomLootEquip) dropItemStack = itemStack;
        else dropItemStack = new ItemStack(itemStack.getItem(), itemStack.getCount());
        dropItemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        Random rand = new Random();
        double finalRate = rate * num;
        if (finalRate < 1 && rand.nextDouble() >= finalRate) return false;
        if (finalRate > 1) {
            dropItemStack.setCount((int) finalRate);
            if (rand.nextDouble() < finalRate % 1) {
                dropItemStack.setCount(dropItemStack.getCount() + 1);
            }
        }
        InventoryCheck.addOwnerTagToItemStack(player, dropItemStack);
        summonBoundingItemEntity(mob, dropItemStack, player);
        return true;
    }

    public void dropWithoutBounding(Mob mob, double num, Player player) {
        ItemStack dropItemStack;
        if (itemStack.getItem() instanceof RandomCurios randomCurios) {
            randomCurios.setAttribute(itemStack);
        }
        if (itemStack.getItem() instanceof RandomCurios
                || itemStack.getItem() instanceof RandomLootEquip) dropItemStack = itemStack;
        else dropItemStack = new ItemStack(itemStack.getItem(), itemStack.getCount());
        dropItemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        Random rand = new Random();
        double finalRate = rate * num;
        if (finalRate < 1 && rand.nextDouble() >= finalRate) return;
        if (finalRate > 1) {
            dropItemStack.setCount((int) finalRate);
            if (rand.nextDouble() < finalRate % 1) {
                dropItemStack.setCount(dropItemStack.getCount() + 1);
            }
        }
        summonBoundingItemEntity(mob, dropItemStack, player);
    }

    public static void summonBoundingItemEntity(Mob mob, ItemStack itemStack, Player player) {
        Random rand = new Random();
        ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, mob.level());
        itemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        itemEntity.setItem(itemStack);
        itemEntity.setPos(mob.position().add(0, 0.5, 0));
        itemEntity.setPickUpDelay(8);
        itemEntity.setDeltaMovement(rand.nextDouble(0.2) - 0.1, 0.2, rand.nextDouble(0.2) - 0.1);
        itemEntity.setCustomName(Component.literal("").withStyle(ChatFormatting.WHITE).
                append(itemStack.getDisplayName()).
                append(Component.literal("(").withStyle(ChatFormatting.WHITE)).
                append(player.getName().getString()).withStyle(ChatFormatting.AQUA).
                append(Component.literal(")").withStyle(ChatFormatting.WHITE)));
        itemEntity.setCustomNameVisible(true);
        mob.level().addFreshEntity(itemEntity);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static String expRate = "expRate";

    public static void dropOrbs(int xpLevel, double rate, Level level, Vec3 pos, String tag) {
        Random rand = new Random();
        int orbNum = rand.nextInt(5);
        for (int i = 0; i < orbNum; i++) {
            ExperienceOrb orb = new ExperienceOrb(EntityType.EXPERIENCE_ORB, level);
            CompoundTag data = orb.getPersistentData();
            data.putBoolean(tag, true);
            data.putDouble(expRate, rate);
            orb.value = xpLevel;
            orb.setPos(pos.add(0, 0.5, 0).add(rand.nextDouble(0.5) - 0.25, 0, rand.nextDouble(0.5) - 0.25));
            orb.setDeltaMovement((rand.nextDouble() * 0.20000000298023224 - 0.10000000149011612) * 2.0,
                    rand.nextDouble() * 0.2 * 2.0, (rand.nextDouble() * 0.20000000298023224 - 0.10000000149011612) * 2.0);
            level.addFreshEntity(orb);
        }
    }

    public static List<ItemStack> getOneTimeLoot(List<ItemAndRate> list) {
        List<ItemStack> result = new ArrayList<>();
        Random random = new Random();
        list.forEach(itemAndRate -> {
            if (random.nextDouble() < itemAndRate.rate) {
                result.add(itemAndRate.itemStack);
            }
        });
        return result;
    }

}
