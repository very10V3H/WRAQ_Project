package fun.wraq.series.events.spring2024;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.SpecialEventItems;
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

public class FireworkGun extends Item implements ActiveItem {

    public FireworkGun(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 新年快乐！").withStyle(ChatFormatting.GOLD));
        Compute.DescriptionActive(components, Component.literal("过年啦！").withStyle(CustomStyle.styleOfSpring));
        components.add(Component.literal(" 右键发射一枚烟花火箭").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" 提升").withStyle(CustomStyle.styleOfSpring).
                append(ComponentUtils.AttributeDescription.attackDamage("10%")).
                append(Component.literal("与").withStyle(CustomStyle.styleOfSpring)).
                append(ComponentUtils.AttributeDescription.manaDamage("10%")).
                append(Component.literal(" 持续10s").withStyle(ChatFormatting.WHITE)));
        components.add(ComponentUtils.getSuffixOfSpring2025());
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            Compute.use(player, this);
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    @Override
    public void active(Player player) {
        StableAttributesModifier.addM(player, StableAttributesModifier.playerPercentManaDamageModifier,
                "fireworkGunActiveEffect", 0.1, Tick.get() + Tick.s(10),
                SpecialEventItems.FIRE_WORK_GUN.get());
        StableAttributesModifier.addM(player, StableAttributesModifier.playerPercentAttackDamageModifier,
                "fireworkGunActiveEffect", 0.1, Tick.get() + Tick.s(10));
        Vec3 vec3 = player.pick(5, 0, false).getLocation();
        summonFireWork(player.level(), vec3);
        player.getCooldowns().addCooldown(this, Tick.s(1));
    }

    public static void summonFireWork(Level level, Vec3 pos) {
        FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(level,
                pos.x, pos.y, pos.z, getFireworkItemStack());
        fireworkRocketEntity.setDeltaMovement(0, 0.75, 0);
        level.addFreshEntity(fireworkRocketEntity);
    }

    public static ItemStack getFireworkItemStack() {
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
        compoundTag.putIntArray("Colors", new int[]{14602026, 15790320, 16733525});
        compoundTag.putByte("Flicker", a);
        compoundTag.putIntArray("FadeColors", new int[]{random.nextInt(20000000)});
        compoundTag.putString("forge:shape_type", strings[random.nextInt(5)]);
        ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET);
        itemStack.getOrCreateTagElement("Fireworks").putByte("Flight", bytes[random.nextInt(1, 3)]);
        ListTag listTag = new ListTag();
        listTag.add(compoundTag);
        itemStack.getOrCreateTagElement("Fireworks").put("Explosions", listTag);
        return itemStack;
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}
