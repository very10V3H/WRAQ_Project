package com.very.wraq.series.overworld.chapter1.Field;

import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.system.spur.events.CropSpur;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.WraqSword;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FieldSword extends WraqSword implements ActiveItem {

    public FieldSword(Properties properties, int tier) {
        super(properties);
        Utils.attackDamage.put(this, new double[]{50, 50, 50, 60}[tier]);
        Utils.defencePenetration0.put(this, new double[]{400, 450, 500, 550}[tier]);
        Utils.healthSteal.put(this, 0.2);
        Utils.critRate.put(this, 0.5);
        Utils.critDamage.put(this, 0.35);
        Utils.movementSpeedWithoutBattle.put(this, new double[]{0.2, 0.3, 0.4, 0.5}[tier]);
        Element.LifeElementValue.put(this, new double[]{0.2, 0.4, 0.6, 0.8}[tier]);
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
                try {
                    CropSpur.harvestCrops(player, blockPos1, player.level());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
