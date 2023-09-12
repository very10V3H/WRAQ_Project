package com.Very.very.Series.OverWorldSeries.MainStory_I.Gems;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SkyGem extends Item {
    private final float AttackDamage = 20;
    private final float SpeedUp = 0.2f;
    public SkyGem(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsAttackDamage.put("skyGem",AttackDamage);
        Utils.GemsSpeedUp.put("skyGem",SpeedUp);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        components.add(Component.literal("天空意志的具象，凝聚于此石。").withStyle(Utils.styleOfSky));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfSky,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionBaseAttackDamage(components,AttackDamage);
        Compute.EmojiDescriptionMovementSpeed(components,SpeedUp);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfSky,ChatFormatting.WHITE);
        components.add(Component.literal("Sky-Gem-0").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.UNDERLINE));
        components.add(Component.literal("MainStoryII").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }

/*    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if(interactionHand.equals(InteractionHand.OFF_HAND) && player.isShiftKeyDown())
        {
            ItemStack mainhand = player.getItemInHand(InteractionHand.MAIN_HAND);
            CompoundTag data = mainhand.getOrCreateTagElement(Utils.MOD_ID);
            if(!Utils.BaseDamage.containsKey(mainhand.getItem()))
            {
                if(!level.isClientSide) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("宝石").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("宝石似乎不能镶嵌在这个物品上。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

            }
            else
            {
                player.playSound(SoundEvents.PLAYER_LEVELUP);
                if(!level.isClientSide)
                {
                    if(data.getInt("Slot") == 0)
                    {
                        player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("宝石").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("似乎没有对应的宝石孔位。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    }
                    else
                    {
                        data.putInt("Slot",data.getInt("Slot")-1);
                        player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("宝石").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("镶嵌成功！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        ItemStack offhand = player.getItemInHand(InteractionHand.OFF_HAND);
                        offhand.setCount(offhand.getCount()-1);
                        player.setItemInHand(InteractionHand.OFF_HAND,offhand);
                        if(!data.contains("Gems1")) data.putString("Gems1","skyGem");
                        else
                        {
                            if(!data.contains("Gems2")) data.putString("Gems2","skyGem");
                            else
                            {
                                if(!data.contains("Gems3")) data.putString("Gems3","skyGem");
                            }
                        }
                    }
                }
            }
        }
        return super.use(level, player, interactionHand);
    }*/

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
