package fun.wraq.series.overworld.chapter2.lavender;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.impl.display.UsageOrGetWayDescriptionItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.Shield;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.castle.RandomCuriosAttributesUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LavenderBracelet extends WraqCurios implements RandomCurios, UsageOrGetWayDescriptionItem, Decomposable {

    public LavenderBracelet(Properties properties) {
        super(properties);
        Utils.levelRequire.put(this, 80);
    }

    @Override
    public Component getTypeDescription() {
        return Component.literal("").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.getFuncTypeDescriptionOfCurios()).
                append(Component.literal(" v = 3 * " + BigDecimal.valueOf(fullRate())).withStyle(CustomStyle.styleOfPlain));
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("纳德斯膜").withStyle(style));
        components.add(Component.literal(" 每秒为你提供持续1.5s的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("5%").withStyle(style)).
                append(ComponentUtils.AttributeDescription.maxHealth("")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("护盾").withStyle(ChatFormatting.GRAY)));
        return components;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide() && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack stack = player.getItemInHand(interactionHand);
            if (stack.getTagElement(Utils.MOD_ID) == null) RandomCuriosAttributesUtil.randomAttributeProvide(stack, 4, 0.5);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfJacaranda;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("击杀薰楠村东侧，薰衣草田西侧紫晶妖妇概率获取"));
        components.add(Component.literal("基础属性为随机三词条").withStyle(ChatFormatting.WHITE).
                append(Component.literal("防御型").withStyle(CustomStyle.styleOfPlain)).
                append(Component.literal("属性").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public void tick(Player player) {
        if (player.tickCount % 20 == 0 && Compute.hasCurios(player, ModItems.lavenderBracelet.get())) {
            Shield.providePlayerShield(player, 30, PlayerAttributes.maxHealth(player) * 0.05);
        }
    }

    @Override
    public void setAttribute(ItemStack stack) {
        RandomCuriosAttributesUtil.randomDefenceAttributeProvide(stack, 3, fullRate());
    }

    @Override
    public double fullRate() {
        return 0.5;
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(ModItems.PurpleIronPiece.get(), 8);
    }

    public static boolean resetBugAttributes(ItemStack itemStack) {
        Item item = itemStack.getItem();
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        if (item instanceof LavenderBracelet) {
            List<String> tag = List.of(
                    StringUtils.RandomCuriosAttribute.maxHealth,
                    StringUtils.RandomCuriosAttribute.defence,
                    StringUtils.RandomCuriosAttribute.manaDefence,
                    StringUtils.RandomCuriosAttribute.healthRecover,
                    StringUtils.RandomCuriosAttribute.healEffectUp);
            int count = 0;
            for (String s : tag) {
                if (data.contains(s)) {
                    count ++;
                }
            }
            if (count > 3) {
                tag.forEach(data::remove);
                RandomCurios randomCurios = (RandomCurios) item;
                randomCurios.setAttribute(itemStack);
                return true;
            }
        }
        return false;
    }
}
