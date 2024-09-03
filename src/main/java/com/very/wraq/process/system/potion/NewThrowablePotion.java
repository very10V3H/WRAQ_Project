package com.very.wraq.process.system.potion;

import com.very.wraq.common.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NewThrowablePotion extends ThrowablePotionItem {

    public static Item getSplashPotion(Item item) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(Utils.MOD_ID, "splash_" + item.toString()));
    }

    private final String type;

    public NewThrowablePotion(Properties p_41383_, String type) {
        super(p_41383_);
        this.type = type;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        String mainType = "";
        int index = 0;
        for (int i = 0; i < type.length(); i++) {
            if (type.charAt(i) == '_') {
                if (index == 0) index = i;
            }
        }

        StringBuilder stringBuilder = new StringBuilder(type);
        boolean isCon = type.startsWith("con");
        boolean isLong = type.startsWith("long");
        if (!isCon && !isLong) mainType = stringBuilder.substring(0, type.length() - 7);
        else mainType = stringBuilder.substring(index + 1, type.length() - 7);
        String time = "";
        if (!isCon && !isLong) time = "4min";
        if (isCon) time = "2min";
        if (isLong) time = "8min";
        Component component = isCon ? NewPotion.effect1DescriptionMap.get(mainType) : NewPotion.effect0DescriptionMap.get(mainType);
        if (component != null) {
            components.add(Component.literal(" 提供: ").withStyle(ChatFormatting.WHITE).
                    append(component).
                    append(Component.literal(" 持续" + time).withStyle(ChatFormatting.WHITE)));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        if (!level.isClientSide()) {
            ItemStack itemStack = player.getItemInHand(interactionHand);
            CompoundTag data = itemStack.getOrCreateTag();
            data.putString("Potion", "vmd:" + type);
        }

        return super.use(level, player, interactionHand);
    }

    public String getType() {
        return this.type;
    }
}
