package fun.wraq.Items.KillPaper;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KillPaper extends Item {

    public KillPaper(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack itemStack = player.getMainHandItem();
            List<ItemAndRate> list = getDropList(itemStack);
            if (!list.isEmpty()) {
                list.forEach(itemAndRate -> {
                    itemAndRate.send(player, 32);
                });
            }
            Compute.playerItemUseWithRecord(player);
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        CompoundTag tag = stack.getTagElement(Utils.MOD_ID);
        if (tag != null) {
            if (tag.contains(killPaperType)) {
                String type = tag.getString(killPaperType);
                components.add(Component.literal("使用以征讨32只 ").withStyle(ChatFormatting.WHITE).
                        append(Component.literal(type).withStyle(ChatFormatting.RED)));
            }
        }
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    public static String killPaperType = "killPaperType";

    public static List<ItemAndRate> getDropList(ItemStack itemStack) {
        if (itemStack.is(ModItems.killPaper.get())) {
            CompoundTag tag = itemStack.getTagElement(Utils.MOD_ID);
            if (tag != null) {
                if (tag.contains(killPaperType)) {
                    String type = tag.getString(killPaperType);
                    Map<String, List<ItemAndRate>> map = getDropListMap();
                    return map.get(type);
                }
            }
        }
        return new ArrayList<>();
    }

    public static Map<String, List<ItemAndRate>> getDropListMap() {
        if (!MobSpawn.overWolrdList.isEmpty() && !MobSpawn.netherList.isEmpty() && !MobSpawn.endList.isEmpty()) {
            if (!dropListMap.isEmpty()) {
                return dropListMap;
            }
            Map<String, List<ItemAndRate>> map = new HashMap<>();
            MobSpawn.overWolrdList.forEach(mobSpawnController -> map.put(mobSpawnController.mobName.getString(),
                    mobSpawnController.getDropList()));
            MobSpawn.netherList.forEach(mobSpawnController -> map.put(mobSpawnController.mobName.getString(),
                    mobSpawnController.getDropList()));
            MobSpawn.endList.forEach(mobSpawnController -> map.put(mobSpawnController.mobName.getString(),
                    mobSpawnController.getDropList()));
            dropListMap = map;
            return dropListMap;
        }
        return new HashMap<>();
    }

    public static Map<String, List<ItemAndRate>> dropListMap = new HashMap<>();
}
