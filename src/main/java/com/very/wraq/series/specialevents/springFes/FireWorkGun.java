package com.very.wraq.series.specialevents.springFes;

import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.common.Compute;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
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

public class FireWorkGun extends Item implements ActiveItem {

    public FireWorkGun(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 新年快乐！").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal(" 右键发射一枚烟花火箭").withStyle(ChatFormatting.WHITE));
        Compute.DescriptionActive(components, Component.literal("过年啦！").withStyle(CustomStyle.styleOfSpring));
        components.add(Component.literal(" 提升").withStyle(CustomStyle.styleOfSpring).
                append(Compute.AttributeDescription.AttackDamage("10%")).
                append(Component.literal("与").withStyle(CustomStyle.styleOfSpring)).
                append(Compute.AttributeDescription.ManaDamage("10%")).
                append(Component.literal(" 持续10s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("SpringFestival~2024").withStyle(ChatFormatting.ITALIC).withStyle(CustomStyle.styleOfSpring));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            Compute.use(player, this);
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    public static void RandomSummonFireworkRocket(Level level, Player player) {
        for (int i = 0; i < 2; i++) {
            CompoundTag compoundTag = new CompoundTag();
            byte a = 1;
            byte[] bytes = {0, 1, 2, 3, 4};
            String[] strings = {
                    "SMALL_BALL",
                    "LARGE_BALL",
                    "CREEPER",
                    "STAR",
                    "BURST"
            };
            Random random = new Random();
            compoundTag.putByte("Type", a);
            compoundTag.putByte("Trail", a);
            compoundTag.putIntArray("Colors", new int[]{14602026, 15790320});
            compoundTag.putByte("Flicker", a);
            compoundTag.putIntArray("FadeColors", new int[]{random.nextInt(20000000)});
            compoundTag.putString("forge:shape_type", strings[random.nextInt(5)]);
            ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET);
            itemStack.getOrCreateTagElement("Fireworks").putByte("Flight", bytes[random.nextInt(1, 3)]);
            ListTag listTag = new ListTag();
            listTag.add(compoundTag);
            itemStack.getOrCreateTagElement("Fireworks").put("Explosions", listTag);

            FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(level,
                    player, player.getX() + random.nextInt(10) - 5, player.getY() + 5,
                    player.getZ() + random.nextInt(10) - 5, itemStack);
            level.addFreshEntity(fireworkRocketEntity);
        }
    }

    @Override
    public void active(Player player) {
        Utils.PlayerFireWorkGunEffect.put(player, player.getServer().getTickCount() + 200);
        Compute.effectLastTimeSend(player, ModItems.FireWorkGun.get().getDefaultInstance(), 200);
        for (int i = 0; i < 2; i++) {
            CompoundTag compoundTag = new CompoundTag();
            byte a = 1;
            byte[] bytes = {0, 1, 2, 3, 4};
            String[] strings = {
                    "SMALL_BALL",
                    "LARGE_BALL",
                    "CREEPER",
                    "STAR",
                    "BURST"
            };
            Random random = new Random();
            compoundTag.putByte("Type", a);
            compoundTag.putByte("Trail", a);
            compoundTag.putIntArray("Colors", new int[]{14602026, 15790320});
            compoundTag.putByte("Flicker", a);
            compoundTag.putIntArray("FadeColors", new int[]{random.nextInt(20000000)});
            compoundTag.putString("forge:shape_type", strings[random.nextInt(5)]);
            ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET);
            itemStack.getOrCreateTagElement("Fireworks").putByte("Flight", bytes[random.nextInt(1, 3)]);
            ListTag listTag = new ListTag();
            listTag.add(compoundTag);
            itemStack.getOrCreateTagElement("Fireworks").put("Explosions", listTag);

            Vec3 vec3 = player.pick(5, 0, false).getLocation();
            FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(player.level(), player, vec3.x, vec3.y, vec3.z, itemStack);
            player.level().addFreshEntity(fireworkRocketEntity);
        }
        player.getCooldowns().addCooldown(this, 20);
    }
}
