package fun.wraq.Items.MainStory_1.Mission;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.system.reason.Reason;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Main0 extends Item {

    public Main0(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("欢迎你来到").append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal("这是一个任务物品，你可以看到它有着一个感叹号。"));
        components.add(Component.literal("在维瑞阿契，任务可以引导你探索这个世界。"));
        components.add(Component.literal("如果这个物品属于任务物品，你将可以看到："));
        components.add(Component.literal("在它底部描述有着它所属的任务章节。"));
        components.add(Component.literal("本任务属于维瑞阿契的序章，不是剧情的正式开始。"));
        components.add(Component.literal("维瑞阿契的序章，将会介绍维瑞阿契的各种要素。"));
        components.add(Component.literal("关于其他序章没有提及的要素，请在游戏过程中积极探索。"));
        components.add(Component.literal("希望你能有一个美好的游戏体验！"));
        components.add(Component.literal("本章作者：").append(Component.literal("very_H").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("Prologue-O").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        ComponentUtils.suffixOfElement(components);
        super.appendHoverText(stack, p_41422_, components, flag);
    }

    public static int index = 0;
    public static List<SoundEvent> soundEventList = new ArrayList<>();

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        if (!level.isClientSide && !player.isShiftKeyDown()) {
            String name = player.getName().getString();
            CompoundTag data = player.getPersistentData();
            ServerPlayer serverPlayer = (ServerPlayer) player;

/*            for (int i = 0 ; i < player.getInventory().getMaxStackSize() ; i ++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (stack.getItem() instanceof BackpackItem) {
                    BackpackWrapper backpackWrapper = new BackpackWrapper(stack);
                    backpackWrapper.getInventoryHandler().setStackInSlot(0, Items.IRON_INGOT.getDefaultInstance());
                    backpackWrapper.refreshInventoryForInputOutput();

                    ItemStack newBackPack = new ItemStack(ModItems.NETHERITE_BACKPACK.get());
                    BackpackWrapper newBackpackWrapper = new BackpackWrapper(newBackPack);
                    backpackWrapper.getUpgradeHandler().copyTo(newBackpackWrapper.getUpgradeHandler());
                    for (int j = 0 ; j < backpackWrapper.getInventoryHandler().getSlots() ; j ++) {
                        newBackpackWrapper.getInventoryHandler().setSlotStack(j, new ItemStack(Items.IRON_INGOT, 128));
                    }
                    player.getInventory().setItem(i, newBackPack);
                }
            }*/

/*            ItemStack rod = HarbingerItems.HARBINGER_ROD.get().getDefaultInstance();
            ItemStack core = HarbingerItems.HARBINGER_WEAPON_CORE.get().getDefaultInstance();
            ItemStack swordBlade = HarbingerItems.HARBINGER_SWORD_BLADE.get().getDefaultInstance();
            ItemStack string = HarbingerItems.HARBINGER_STRING.get().getDefaultInstance();
            ItemStack mirror = HarbingerItems.HARBINGER_MIRROR.get().getDefaultInstance();

            HarbingerWeaponMaterial.setQualityTier(rod, 5);
            HarbingerWeaponMaterial.setQualityTier(core, 5);
            HarbingerWeaponMaterial.setQualityTier(swordBlade, 5);
            HarbingerWeaponMaterial.setQualityTier(string, 5);
            HarbingerWeaponMaterial.setQualityTier(mirror, 5);

            List.of(rod, core, swordBlade, string, mirror).forEach(player::addItem);*/

            Reason.addOrCostPlayerReasonValue(player, 5);
        }

        if (!level.isClientSide && player.isShiftKeyDown()) {

        }

        if (level.isClientSide && !player.isShiftKeyDown()) {

        }

        if (level.isClientSide && player.isShiftKeyDown()) {

        }

        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    public static void clientTick(Player player) {
        if (player.tickCount % 30 == 0 && index < soundEventList.size()) {
            player.playSound(soundEventList.get(index));
            player.sendSystemMessage(Component.literal("" + soundEventList.get(index).getLocation()));
            index ++;
        }
    }
}
