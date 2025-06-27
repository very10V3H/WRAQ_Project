package fun.wraq.series.overworld.chapter1.field;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.spur.events.CropSpur;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class FieldSword extends WraqSword implements ActiveItem {

    public FieldSword(Properties properties, int tier) {
        super(properties);
        Utils.attackDamage.put(this, new double[]{50, 50, 50, 60}[tier]);
        Utils.defencePenetration0.put(this, new double[]{4, 5, 6, 7}[tier]);
        Utils.healthSteal.put(this, 0.2);
        Utils.critRate.put(this, 0.5);
        Element.lifeElementValue.put(this, new double[]{0.2, 0.4, 0.6, 0.8}[tier]);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfField;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionActive(components, Component.literal("收获").withStyle(ChatFormatting.YELLOW));
        components.add(Component.literal(" 右键收获周围农作物!"));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public void active(Player player) {
        player.getCooldowns().addCooldown(this, 4);
        Vec3 vec3 = player.pick(5, 0, false).getLocation();
        BlockPos blockPos = new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                BlockPos blockPos1 = new BlockPos(blockPos.getX() - 1 + i, blockPos.getY(), blockPos.getZ() - 1 + j);
                CropSpur.harvestCrops(player, blockPos1, player.level());
            }
        }
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}
