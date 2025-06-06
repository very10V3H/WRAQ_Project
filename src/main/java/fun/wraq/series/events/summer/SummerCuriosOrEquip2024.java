package fun.wraq.series.events.summer;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.process.system.season.MySeason;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class SummerCuriosOrEquip2024 extends WraqCurios implements InCuriosOrEquipSlotAttributesModify {

    private final int tier;

    public SummerCuriosOrEquip2024(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.maxHealth.put(this, new double[]{600, 800, 1000, 1200, 1400, 1600}[tier]);
        Utils.expUp.put(this, new double[]{0.2, 0.25, 0.3, 0.35, 0.4, 0.5}[tier]);
        Utils.levelRequire.put(this, new int[]{60, 90, 120, 150, 180, 210}[tier]);
    }

    private final int[] exAttackDamage = new int[]{40, 80, 120, 160, 240, 320};
    private final int[] exManaDamage = new int[]{80, 160, 240, 320, 480, 640};
    private final double[] releaseSpeed = new double[]{0.05, 0.1, 0.15, 0.25, 0.35, 0.5};

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getComprehensiveTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("暑期就该摸鱼！").withStyle(CustomStyle.styleOfWater));
        components.add(Component.literal(" 在").withStyle(ChatFormatting.WHITE).
                append(Component.literal("夏季").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("的白天下水，每15s有概率获得一只鱼").withStyle(ChatFormatting.WHITE)));
        ComponentUtils.descriptionPassive(components, Component.literal("热切激昂").withStyle(CustomStyle.styleOfPower));
        components.add(Component.literal(" 获得以下属性:").withStyle(CustomStyle.styleOfWater));
        components.add(Component.literal(" 1.").withStyle(CustomStyle.styleOfWater).
                append(ComponentUtils.AttributeDescription.attackDamage(String.valueOf(exAttackDamage[tier]))));
        components.add(Component.literal(" 2.").withStyle(CustomStyle.styleOfWater).
                append(ComponentUtils.AttributeDescription.manaDamage(String.valueOf(exManaDamage[tier]))));
        components.add(Component.literal(" 3.").withStyle(CustomStyle.styleOfWater).
                append(ComponentUtils.AttributeDescription.releaseSpeed(String.format("%.0f", releaseSpeed[tier] * 100))));
        components.add(Component.literal(" 在").withStyle(ChatFormatting.WHITE).
                append(Component.literal("夏季").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("或").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("水中").withStyle(CustomStyle.styleOfWater)).
                append(Component.literal("获得双倍效能!").withStyle(ChatFormatting.LIGHT_PURPLE)));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return ForgeEquipUtils.getStyle(tier);
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSummerEvent();
    }

    private double getRate(Player player) {
        if (player.experienceLevel < Utils.levelRequire.get(this)) return 0;
        return (player.isInWater()
                || (player.level().dimension().equals(Level.OVERWORLD)
                && MySeason.currentSeason.contains(MySeason.summer))) ? 2 : 1;
    }

    @Override
    public void tick(Player player) {
        if (player.tickCount % 300 == 0 && player.level().dimension().equals(Level.OVERWORLD)
                && MySeason.currentSeason.contains(MySeason.summer) && player.isInWater()
                && player.level().isDay()) {
            InventoryOperation.giveItemStack(player, new ItemStack(Items.TROPICAL_FISH));
            Compute.sendFormatMSG(player, Component.literal("摸鱼!").withStyle(CustomStyle.styleOfWater),
                    Component.literal("你摸到了一条鱼！").withStyle(ChatFormatting.GOLD));
        }
    }

    @Override
    public void clientTick(Player player) {
        int rate = 1;
        if (player.isInWater()
                || (player.level().dimension().equals(Level.OVERWORLD)
                && MySeason.clientSeason != null && MySeason.clientSeason.contains(MySeason.summer))) rate = 2;
        Compute.sendEffectLastTimeToClientPlayer(this, rate, 20, false);
        super.clientTick(player);
    }

    @Override
    public List<Attribute> getAttributes(Player player, ItemStack stack) {
        double rate = getRate(player);
        return List.of(
                new Attribute(Utils.attackDamage, exAttackDamage[tier] * rate),
                new Attribute(Utils.manaDamage, exManaDamage[tier] * rate),
                new Attribute(Utils.coolDownDecrease, releaseSpeed[tier] * rate)
        );
    }
}
