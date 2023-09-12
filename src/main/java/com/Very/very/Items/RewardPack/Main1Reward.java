package com.Very.very.Items.RewardPack;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.Map;

public class Main1Reward extends Item {

    public Main1Reward(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        player.playSound(SoundEvents.PLAYER_LEVELUP);
        if(!level.isClientSide)
        {
            ItemStack RewardPack = player.getItemInHand(InteractionHand.MAIN_HAND);
            int num = RewardPack.getCount();
            ItemStack RewardPack0 = Moditems.main1reward.get().getDefaultInstance();
            RewardPack0.setCount(num-1);
            player.setItemInHand(InteractionHand.MAIN_HAND,RewardPack0);
            player.addItem(Moditems.backspawn.get().getDefaultInstance());
            player.addItem(Moditems.Main1_5.get().getDefaultInstance());
            Compute.ExpPercentGetAndMSGSend(player,1,0,5);
            ItemStack itemStack = Items.ELYTRA.getDefaultInstance();
            itemStack.getOrCreateTag().putBoolean("Unbreakable",true);
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);
            map.put(Enchantments.UNBREAKING,5);
            EnchantmentHelper.setEnchantments(map,itemStack);
            player.addItem(itemStack);
        }
        return super.use(level, player, interactionHand);
    }
}
