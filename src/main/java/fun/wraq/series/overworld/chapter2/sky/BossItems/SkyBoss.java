package fun.wraq.series.overworld.chapter2.sky.BossItems;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkyBoss {
    public static String Name = "天空";

    public static class SkyBossBow extends WraqBow {
        private final double BaseDamage = 100.0d;
        private final double BreakDefence = 0.25F;
        private final double CriticalHitRate = 0.5F;

        public SkyBossBow(Properties properties) {
            super(properties);
            Utils.attackDamage.put(this, this.BaseDamage);
            Utils.defencePenetration.put(this, this.BreakDefence);
            Utils.critRate.put(this, this.CriticalHitRate);
            Element.WindElementValue.put(this, 1.25);
        }

        @Override
        public Style getMainStyle() {
            return CustomStyle.styleOfSky;
        }

        @Override
        public List<Component> getAdditionalComponents(ItemStack stack) {
            List<Component> components = new ArrayList<>();
            Style style = getMainStyle();
            components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("天空维度展开: ").withStyle(style)).
                    append(Component.literal("天空制造者释放制造天空次元的能量，使持有者具有凌驾天空的力量。").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("∰1.使持有者至多获得").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.critDamage("320%")));

            Compute.DescriptionPassive(components, Component.literal("鹰隼之速").withStyle(ChatFormatting.AQUA));
            components.add(Component.literal("每").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.exMovementSpeed("8%")).
                    append(Component.literal("提供").withStyle(ChatFormatting.WHITE)).
                    append(ComponentUtils.AttributeDescription.defencePenetration("3%")));
            return components;
        }

        @Override
        public Component getSuffix() {
            return ComponentUtils.getSuffixOfChapterII();
        }

        @Override
        public boolean isFoil(ItemStack p_41453_) {
            return true;
        }
    }
}

