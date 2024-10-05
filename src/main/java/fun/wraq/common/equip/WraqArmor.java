package fun.wraq.common.equip;

import fun.wraq.blocks.blocks.forge.ForgeRecipe;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.loot.RandomLootEquip;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WraqArmor extends ArmorItem {

    public WraqArmor(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
        Utils.armorTag.put(this, 1d);
        Utils.armorList.add(this);
        if (this instanceof ForgeItem forgeItem) {
            ForgeRecipe.forgeDrawRecipe.put(this, forgeItem.forgeRecipe());
        }
    }

    public abstract Style getMainStyle();

    public abstract List<Component> getAdditionalComponents();

    public abstract Component getSuffix();

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = getMainStyle();
        Compute.forgingHoverName(stack);
        Map<Type, String> typeMap = new HashMap<>() {{
            put(Type.HELMET, "头盔");
            put(Type.CHESTPLATE, "胸甲");
            put(Type.LEGGINGS, "护腿");
            put(Type.BOOTS, "靴子");
        }};
        Map<Type, Style> styleMap = new HashMap<>() {{
            put(Type.HELMET, CustomStyle.styleOfMoon);
            put(Type.CHESTPLATE, Style.EMPTY.applyFormat(ChatFormatting.RED));
            put(Type.LEGGINGS, CustomStyle.styleOfWorld);
            put(Type.BOOTS, CustomStyle.styleOfFlexible);
        }};
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).
                append(Component.literal(typeMap.get(this.type)).withStyle(styleMap.get(this.type))));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        if (this instanceof RandomLootEquip randomLootEquip) {
            if (randomLootEquip.levelRequire() != 0) {
                int levelRequirement = randomLootEquip.levelRequire();
                if (levelRequirement != 0) {
                    components.add(Component.literal(" 等级需求: ").withStyle(ChatFormatting.AQUA).
                            append(Component.literal("Lv." + levelRequirement).withStyle(Utils.levelStyleList.get(levelRequirement / 25))));
                }
            }
        }
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        if (!getAdditionalComponents().isEmpty()) {
            ComponentUtils.descriptionOfAddition(components);
            components.addAll(getAdditionalComponents());
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        }
        components.add(getSuffix());
        super.appendHoverText(stack, level, components, flag);
    }

    @OnlyIn(Dist.CLIENT)
    public static int getSuitCount(Class<? extends WraqArmor> clazz) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return 0;
        return (int) InventoryOperation.getArmors(player)
                .stream().filter(itemStack -> itemStack.getItem().getClass().equals(clazz)).count();
    }
}
