package com.very.wraq.Items.MainStory_1;

import com.very.wraq.process.system.forge.ForgeEquipUtils;
import com.very.wraq.series.newrunes.NewRuneItems;
import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
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

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            Compute.playerItemUseWithRecord(player);
            ItemStack sword = new ItemStack(ModItems.PlainSword0.get());
            ItemStack bow = new ItemStack(ModItems.PlainBow0.get());
            ItemStack sceptre = new ItemStack(ModItems.PlainSceptre0.get());
            ForgeEquipUtils.setForgeQualityOnEquip(sword, 6);
            ForgeEquipUtils.setForgeQualityOnEquip(bow, 6);
            ForgeEquipUtils.setForgeQualityOnEquip(sceptre, 6);

            ItemStack[] itemStacks = {new ItemStack(ModItems.BackPackTickets.get()), new ItemStack(ModItems.WoodHammer.get()), new ItemStack(ModItems.BackSpawn.get()), new ItemStack(Items.GOLDEN_CARROT, 64), new ItemStack(ModItems.windBottle.get()), sword, bow, sceptre, new ItemStack(NewRuneItems.endNewRune.get())};

            for (ItemStack stack : itemStacks) {
                Compute.itemStackGive(player, stack);
            }

            ItemStack itemStack = Items.ELYTRA.getDefaultInstance();
            itemStack.getOrCreateTag().putBoolean("Unbreakable", true);
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);
            map.put(Enchantments.UNBREAKING, 5);
            EnchantmentHelper.setEnchantments(map, itemStack);
            Compute.itemStackGive(player, itemStack);
            Compute.sendFormatMSG(player, Component.literal("引导-锻造").withStyle(ChatFormatting.AQUA), Component.literal("前往").withStyle(ChatFormatting.WHITE).append(Component.literal("平原村").withStyle(ChatFormatting.GREEN)).append(Component.literal("收集素材，前往平原村找到铁匠铺，锻造第一件装备吧！").withStyle(ChatFormatting.WHITE)));

            Compute.sendFormatMSG(player, Component.literal("引导-信息").withStyle(ChatFormatting.AQUA), Component.literal("在背包中查看物品的描述，若物品下方有").withStyle(ChatFormatting.WHITE).append(Component.literal("[按住shift以...]").withStyle(ChatFormatting.GRAY)).append(Component.literal("，你可以查看其用途或获取方式").withStyle(ChatFormatting.WHITE)));
            Compute.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP);
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("右键打开它!"));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("Present").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
