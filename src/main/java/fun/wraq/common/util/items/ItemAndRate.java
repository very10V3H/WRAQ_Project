package fun.wraq.common.util.items;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.events.mob.loot.RandomLootEquip;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.xp.MyExpSystem;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ItemAndRate {

    private ItemStack itemStack;
    private final double rate;
    private boolean expDrop = false;
    private int expValue = 0;
    private int expLevel = 0;

    public ItemAndRate(ItemStack itemStack, double rate) {
        this.itemStack = itemStack;
        this.rate = rate;
    }

    public ItemAndRate(Item item, double rate) {
        this.itemStack = new ItemStack(item);
        this.rate = rate;
    }

    /** 创建一个经验掉落项，rate 含义与普通物品相同：&lt;1 为概率，&gt;1 为保底倍率 */
    public static ItemAndRate ofExp(int expValue, int expLevel) {
        ItemAndRate instance = new ItemAndRate(Items.AIR, 1);
        instance.expDrop = true;
        instance.expValue = expValue;
        instance.expLevel = expLevel;
        return instance;
    }

    public static ItemAndRate ofExp(int expValue) {
        return ofExp(expValue, 0);
    }

    public void give(Player player) {
        if (expDrop) {
            if (new Random().nextDouble() < rate) {
                MyExpSystem.giveExpToPlayer(player, expValue, PlayerAttributes.expUp(player), expLevel);
            }
            return;
        }
        if (rate > 1) {
            int num = (int) Math.floor(rate);
            itemStack.setCount(num);
            InventoryOperation.giveItemStack(player, itemStack);
        } else {
            InventoryOperation.giveItemStackByRate(itemStack, rate, player);
        }
    }

    public void giveByNewObject(Player player) {
        if (expDrop) {
            Random random = new Random();
            int num = (int) Math.floor(rate);
            if (random.nextDouble() < rate) num++;
            if (num > 0) {
                MyExpSystem.giveExpToPlayer(player, expValue * num, PlayerAttributes.expUp(player), expLevel);
            }
            return;
        }
        Random random = new Random();
        ItemStack newStack = itemStack.copy();
        int num = (int) Math.floor(rate);
        if (random.nextDouble() < rate) num ++;
        newStack.setCount(itemStack.getCount() * num);
        newStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        InventoryOperation.giveItemStack(player, newStack);
    }

    public void drop(Mob mob) {
        if (expDrop) {
            return;
        }
        ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, mob.level());
        itemEntity.setItem(itemStack);
        itemEntity.setPos(mob.position().add(0, 0.5, 0));
        itemEntity.setPickUpDelay(8);
        Random rand = new Random();
        itemEntity.setDeltaMovement(rand.nextDouble(0.2) - 0.1, 0.2, rand.nextDouble(0.2) - 0.1);
        mob.level().addFreshEntity(itemEntity);
    }

    public void drop(Mob mob, double num) {
        Random rand = new Random();
        double finalRate = rate * num;
        if (expDrop) {
            return;
        }
        ItemStack dropItemStack = new ItemStack(itemStack.getItem());
        if (finalRate < 1 && rand.nextDouble() >= finalRate) return;
        if (finalRate > 1) {
            dropItemStack.setCount((int) finalRate);
            if (rand.nextDouble() < finalRate % 1) {
                dropItemStack.setCount(dropItemStack.getCount() + 1);
            }
        }
        summonItemEntity(dropItemStack, mob.position(), mob.level());
    }

    public void handleRandomAttributeBeforeDrop(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (item instanceof RandomCurios randomCurios) {
            randomCurios.setAttribute(itemStack);
        }
        if (item instanceof RandomLootEquip) {
            RandomLootEquip.setRandomAttribute(itemStack);
        }
    }

    public boolean send(Player player, double num) {
        return send(player, num, null);
    }

    public boolean send(Player player, double num, AdjustStackBeforeGive adjustStackBeforeGive) {
        Random rand = new Random();
        double finalRate = rate * num;
        if (expDrop) {
            if (finalRate < 1 && rand.nextDouble() >= finalRate) return false;
            int value = expValue;
            if (finalRate > 1) {
                value = (int) (expValue * finalRate);
                if (rand.nextDouble() < finalRate % 1) value += expValue;
            }
            MyExpSystem.giveExpToPlayer(player, value, PlayerAttributes.expUp(player), expLevel);
            return true;
        }
        ItemStack dropItemStack = new ItemStack(itemStack.getItem(), itemStack.getCount());
        handleRandomAttributeBeforeDrop(dropItemStack);
        dropItemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        if (finalRate < 1 && rand.nextDouble() >= finalRate) return false;
        if (finalRate > 1) {
            dropItemStack.setCount((int) finalRate);
            if (rand.nextDouble() < finalRate % 1) {
                dropItemStack.setCount(dropItemStack.getCount() + 1);
            }
        }
        if (adjustStackBeforeGive != null) {
            adjustStackBeforeGive.adjust(dropItemStack);
        }
        InventoryOperation.giveItemStack(player, dropItemStack);
        return true;
    }

    public static void send(Player player, Item item) {
        send(player, item.getDefaultInstance());
    }

    public static void send(Player player, ItemStack itemStack) {
        ItemStack dropItemStack = new ItemStack(itemStack.getItem(), itemStack.getCount());
        dropItemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        InventoryOperation.giveItemStack(player, dropItemStack);
    }

    public ItemStack sendWithMSG(Player player, double num) {
        return sendWithMSG(player, num, null);
    }

    public ItemStack sendWithMSG(Player player, double num, AdjustStackBeforeGive adjustStackBeforeGive) {
        Random rand = new Random();
        double finalRate = rate * num;
        if (expDrop) {
            if (finalRate < 1 && rand.nextDouble() >= finalRate) {
                return Items.AIR.getDefaultInstance();
            }
            int value = expValue;
            if (finalRate > 1) {
                value = (int) (expValue * finalRate);
                if (rand.nextDouble() < finalRate % 1) value += expValue;
            }
            MyExpSystem.giveExpToPlayer(player, value, PlayerAttributes.expUp(player), expLevel);
            Compute.sendFormatMSG(player, Te.s("掉落", ChatFormatting.GOLD),
                    Te.s("经验 +" + value, ChatFormatting.WHITE));
            return Items.AIR.getDefaultInstance();
        }
        ItemStack dropItemStack = new ItemStack(itemStack.getItem(), itemStack.getCount());
        handleRandomAttributeBeforeDrop(dropItemStack);
        dropItemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        if (finalRate < 1 && rand.nextDouble() >= finalRate) {
            return Items.AIR.getDefaultInstance();
        }
        if (finalRate > 1) {
            dropItemStack.setCount((int) finalRate);
            if (rand.nextDouble() < finalRate % 1) {
                dropItemStack.setCount(dropItemStack.getCount() + 1);
            }
        }
        if (adjustStackBeforeGive != null) {
            adjustStackBeforeGive.adjust(dropItemStack);
        }
        ItemStack copyStack = dropItemStack.copy();
        InventoryOperation.giveItemStackWithMSG(player, dropItemStack);
        return copyStack;
    }

    public static ItemEntity summonItemEntity(ItemStack itemStack, Vec3 pos, Level level) {
        return summonItemEntity(itemStack, pos, level, 8);
    }

    public static ItemEntity summonItemEntity(ItemStack itemStack, Vec3 pos, Level level, int pickUpDelay) {
        return summonItemEntity(itemStack, pos, level, pickUpDelay, true);
    }

    public static ItemEntity summonItemEntity(ItemStack itemStack, Vec3 pos, Level level, boolean delta) {
        return summonItemEntity(itemStack, pos, level, 8, delta);
    }

    public static ItemEntity summonItemEntity(ItemStack itemStack, Vec3 pos, Level level, int pickUpDelay, boolean delta) {
        ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, level);
        itemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        itemEntity.setItem(itemStack);
        itemEntity.setPos(pos.add(0, 0.5, 0));
        itemEntity.setPickUpDelay(pickUpDelay);
        Random rand = new Random();
        if (delta) {
            itemEntity.setDeltaMovement(rand.nextDouble(0.2) - 0.1, 0.2, rand.nextDouble(0.2) - 0.1);
        }
        level.addFreshEntity(itemEntity);
        return itemEntity;
    }

    public double getRate() {
        return rate;
    }

    public boolean dropWithBounding(Mob mob, double num, Player player) {
        Random rand = new Random();
        double finalRate = rate * num;
        if (expDrop) {
            if (finalRate < 1 && rand.nextDouble() >= finalRate) return false;
            int value = expValue;
            if (finalRate > 1) {
                value = (int) (expValue * finalRate);
                if (rand.nextDouble() < finalRate % 1) value += expValue;
            }
            MyExpSystem.giveExpToPlayer(player, value, PlayerAttributes.expUp(player), expLevel);
            return true;
        }
        ItemStack dropItemStack = new ItemStack(itemStack.getItem());
        handleRandomAttributeBeforeDrop(dropItemStack);
        dropItemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
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
        Random rand = new Random();
        double finalRate = rate * num;
        if (expDrop) {
            if (finalRate < 1 && rand.nextDouble() >= finalRate) return;
            int value = expValue;
            if (finalRate > 1) {
                value = (int) (expValue * finalRate);
                if (rand.nextDouble() < finalRate % 1) value += expValue;
            }
            MyExpSystem.giveExpToPlayer(player, value, PlayerAttributes.expUp(player), expLevel);
            return;
        }
        ItemStack dropItemStack = new ItemStack(itemStack.getItem());
        handleRandomAttributeBeforeDrop(dropItemStack);
        dropItemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
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
    public static String fromMobExpDropTag = "fromMobExpDrop";

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

    /**
     * 合并列表中所有由 {@link #ofExp(int)} 创建的经验给予项。
     * 合并后保留一个条目，其 expValue 为所有条目之和。
     */
    public static void mergeExpEntries(List<ItemAndRate> list) {
        int total = 0;
        boolean hasExp = false;
        Iterator<ItemAndRate> iter = list.iterator();
        while (iter.hasNext()) {
            ItemAndRate item = iter.next();
            if (item.expDrop) {
                if (!hasExp) {
                    hasExp = true;
                }
                total += item.expValue;
                iter.remove();
            }
        }
        if (hasExp) {
            list.add(ofExp(total));
        }
    }

}
