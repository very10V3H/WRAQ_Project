package fun.wraq.series.overworld.sakura.bunker.armor;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.cold.ColdSystem;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.crystal.CrystalItems;
import fun.wraq.series.instance.series.harbinger.HarbingerItems;
import fun.wraq.series.overworld.sakura.bunker.BunkerItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BunkerArmor extends WraqArmor implements Decomposable, ForgeItem {

    public final int tier;
    public final int heatTier;
    public BunkerArmor(ArmorMaterial armorMaterial, Type type, Properties properties, int tier, int heatTier) {
        super(armorMaterial, type, properties);
        this.tier = tier;
        this.heatTier = heatTier;
        if (type.equals(Type.HELMET)) {
            Utils.percentHealthRecover.put(this, new double[]{0.012, 0.018, 0.025}[tier]);
            Utils.healthRecover.put(this, new double[]{140, 220, 300}[tier]);
            Utils.defence.put(this, new double[]{60, 80, 100}[tier]);
            Utils.manaDefence.put(this, new double[]{30, 40, 55}[tier]);
        }
        if (type.equals(Type.CHESTPLATE)) {
            Utils.defence.put(this, new double[]{100, 150, 200}[tier]);
            Utils.manaDefence.put(this, new double[]{50, 80, 110}[tier]);
            Utils.maxHealth.put(this, new double[]{21000, 30000, 40000}[tier]);
        }
        if (type.equals(Type.LEGGINGS)) {
            Utils.maxHealth.put(this, new double[]{42000, 65000, 80000}[tier]);
            Utils.defence.put(this, new double[]{60, 80, 100}[tier]);
            Utils.manaDefence.put(this, new double[]{30, 45, 60}[tier]);
        }
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.16);
            Utils.maxHealth.put(this, new double[]{21000, 30000, 40000}[tier]);
        }
        Utils.levelRequire.put(this, 225);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.BUNKER_STYLE;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("聚变能量", getMainStyle()));
        components.add(Te.s(" 产热 + ", heatTier, getMainStyle()));
        ComponentUtils.descriptionPassive(components, Te.s("枯竭", getMainStyle()));
        components.add(Te.s(" 在极寒地区死亡，此装备将会消失.", ChatFormatting.RED));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfBunker();
    }

    public static void onPlayerDead(Player player) {
        if (ColdSystem.getPlayerColdLevel(player) > 1) {
            List<ItemStack> list = new ArrayList<>();
            MutableComponent mutableComponent = Component.literal("");
            for (ItemStack stack : player.getArmorSlots()) {
                if (stack.getItem() instanceof BunkerArmor) {
                    list.add(stack);
                    mutableComponent.append(Te.s(stack));
                    stack.shrink(1);
                }
            }
            if (!list.isEmpty()) {
                Compute.sendFormatMSG(player, Te.s("枯竭", CustomStyle.BUNKER_STYLE),
                        Te.s("在极寒地区死亡，你失去了", mutableComponent));
            }
        }
    }

    @Override
    public ItemStack getProduct() {
        if (tier == 0) {
            return new ItemStack(BunkerItems.BUNKER_ARMOR_PIECE.get());
        } else if (tier == 1) {
            return new ItemStack(BunkerItems.BUNKER_ARMOR_PIECE.get(), 4);
        }
        return null;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        if (tier < 2) {
            return List.of();
        }
        return List.of(
                new ItemStack(BunkerItems.BUNKER_ARMOR_PIECE.get(), 16),
                new ItemStack(CrystalItems.RED_CRYSTAL_C.get()),
                new ItemStack(CrystalItems.YELLOW_CRYSTAL_C.get()),
                new ItemStack(BunkerItems.BUNKER_BOSS_SOUL.get(), 16),
                new ItemStack(HarbingerItems.RAW_IRON_NUGGET.get(), 256),
                new ItemStack(HarbingerItems.RAW_IRON_INGOT.get(), 64)
        );
    }
}
