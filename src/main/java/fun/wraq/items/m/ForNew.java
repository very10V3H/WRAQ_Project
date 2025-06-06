package fun.wraq.items.m;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.series.newrunes.NewRuneItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class ForNew extends Item {
    public ForNew(Properties p_41383_) {
        super(p_41383_);
    }

    private List<ItemStack> getItemList() {
        ItemStack sword = new ItemStack(ModItems.PLAIN_SWORD_0.get());
        ItemStack bow = new ItemStack(ModItems.PLAIN_BOW_0.get());
        ItemStack sceptre = new ItemStack(ModItems.LIFE_SCEPTRE_0.get());
        ForgeEquipUtils.setForgeQualityOnEquip(sword, 6);
        ForgeEquipUtils.setForgeQualityOnEquip(bow, 6);
        ForgeEquipUtils.setForgeQualityOnEquip(sceptre, 6);
        ItemStack elyTra = Items.ELYTRA.getDefaultInstance();
        elyTra.getOrCreateTag().putBoolean("Unbreakable", true);
        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(elyTra);
        map.put(Enchantments.UNBREAKING, 5);
        EnchantmentHelper.setEnchantments(map, elyTra);

        return List.of(
                sword, bow, sceptre,
                new ItemStack(ModItems.BAMBOO_KANATA.get()),
                new ItemStack(ModItems.ORIGIN_KNIFE_PLAIN.get()),
                new ItemStack(ModItems.PLAIN_MANA_BOOK.get()),
                new ItemStack(NewRuneItems.PLAIN_NEW_RUNE.get()),
                new ItemStack(NewRuneItems.FOREST_NEW_RUNE.get()),
                new ItemStack(NewRuneItems.LAKE_NEW_RUNE.get()),
                new ItemStack(NewRuneItems.VOLCANO_NEW_RUNE.get()),
                new ItemStack(NewRuneItems.END_NEW_RUNE.get()),
                new ItemStack(ModItems.BACKPACK_TICKETS.get()),
                new ItemStack(ModItems.COPPER_HAMMER.get()),
                new ItemStack(ModItems.BACK_SPAWN_TICKET.get()),
                new ItemStack(Items.GOLDEN_CARROT, 64),
                new ItemStack(ModItems.NOTE_PAPER.get(), 128),
                elyTra,
                new ItemStack(ModItems.TP_PASS_3DAY.get(), 1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            Compute.playerItemUseWithRecord(player);
            getItemList().forEach(stack -> InventoryOperation.giveItemStack(player, stack));
            Compute.sendFormatMSG(player, Component.literal("引导-锻造").withStyle(ChatFormatting.AQUA), Component.literal("前往").withStyle(ChatFormatting.WHITE).append(Component.literal("平原村").withStyle(ChatFormatting.GREEN)).append(Component.literal("收集素材，前往平原村找到铁匠铺，锻造第一件装备吧！").withStyle(ChatFormatting.WHITE)));
            Compute.sendFormatMSG(player, Component.literal("引导-信息").withStyle(ChatFormatting.AQUA), Component.literal("在背包中查看物品的描述，若物品下方有").withStyle(ChatFormatting.WHITE).append(Component.literal("[按住shift以...]").withStyle(ChatFormatting.GRAY)).append(Component.literal("，你可以查看其用途或获取方式").withStyle(ChatFormatting.WHITE)));
            MySound.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Te.s("右键打开!", ChatFormatting.AQUA));
        components.add(Te.s(" 内含:", ChatFormatting.GOLD));
        getItemList().forEach(itemStack -> {
            components.add(itemStack.getDisplayName());
        });
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
