package com.very.wraq.Items.DevelopmentTools;

import net.minecraft.network.chat.Component;
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

import java.util.Calendar;
import java.util.Map;

public class GetTime extends Item {
    public GetTime(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            Calendar cal0 = Calendar.getInstance();
            int year = cal0.get(Calendar.YEAR);
            int month = cal0.get(Calendar.MONTH) + 1;
            int day = cal0.get(Calendar.DAY_OF_MONTH);
            int hour = cal0.get(Calendar.HOUR_OF_DAY);
            int minutes = cal0.get(Calendar.MINUTE);
            int seconds = cal0.get(Calendar.SECOND);
            player.sendSystemMessage(Component.literal(+year + "年" + month + "月" + day + "日" + hour + "时" + minutes + "分" + seconds + "秒"));
        }
        ItemStack itemStack = Items.BOW.getDefaultInstance();
        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);
        map.put(Enchantments.POWER_ARROWS, 10);
        EnchantmentHelper.setEnchantments(map, itemStack);
        player.addItem(itemStack);
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
