package fun.wraq.series.overworld.chapter2.sea;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeaBow extends WraqBow implements OnHitEffectEquip {

    public SeaBow(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 400d);
        Utils.defencePenetration0.put(this, 18d);
        Utils.critRate.put(this, 0.25);
        Element.WaterElementValue.put(this, 1d);
        Utils.levelRequire.put(this, 100);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSea;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("破碎晶石").withStyle(CustomStyle.styleOfSea));
        components.add(Component.literal(" 箭矢命中目标后，会对目标周围所有单位造成").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.attackDamage("50%")).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)).
                append(Component.literal("包括目标本身").withStyle(ChatFormatting.GRAY)));
        components.add(ComponentUtils.getCritDamageInfluenceDescription());
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        Level level = player.level();
        List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 10, 10, 10));
        Random random = new Random();
        mobList.forEach(mob1 -> {
            double damage = PlayerAttributes.attackDamage(player) * 0.5;
            if (random.nextDouble() < PlayerAttributes.critRate(player)) damage *= (1 + PlayerAttributes.critDamage(player));
            if (mob1.distanceTo(mob) <= 3) {
                Damage.causeTrueDamageToMonster(player, mob1, damage);
            }
        });
    }
}
