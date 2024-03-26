package com.Very.very.Series.SpringEvent;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class SpringScale extends Item {

    private final int Num;
    public SpringScale(Properties p_41383_,int Num) {
        super(p_41383_);
        this.Num = Num;
    }

    private final String[] Attack = {
            "10%","20%","30%","40%"
    };
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 金龙身上的一片龙鳞").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpring));
        Compute.DescriptionActive(components,Component.literal("锣鼓驱年！").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpring));
        components.add(Component.literal(" 根据精通等级的最高值，提供").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" 1.剑术精通:").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPower).
                append(Compute.AttributeDescription.AttackDamage(Attack[Num])));
        components.add(Component.literal(" 2.弓术精通:").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPower).
                append(Compute.AttributeDescription.Swiftness("" + (Num + 1))));
        components.add(Component.literal(" 3.法术精通:").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPower).
                append(Compute.AttributeDescription.ManaDamage(Attack[Num])));
        components.add(Component.literal(" 效果持续60s").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpring));
        Compute.CoolDownTimeDescription(components,60);
        components.add(Component.literal("SpringFestival~2024").withStyle(ChatFormatting.ITALIC).withStyle(Utils.styleOfSpring));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

/*    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @Nullable Font getFont(ItemStack stack, FontContext context) {
                return IClientItemExtensions.super.getFont(stack, context);
            }
        });
        super.initializeClient(consumer);
    }*/


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) Compute.USE(player,this);
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    public int getNum() {
        return Num;
    }
}
