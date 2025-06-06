package fun.wraq.series.overworld.chapter1.mine;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.damage.Dot;
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

public class MineBow extends WraqBow implements OnHitEffectEquip {

    private final int tier;

    public MineBow(Properties p_40524_, int tier) {
        super(p_40524_);
        this.tier = tier;
        Utils.attackDamage.put(this, new double[]{50, 55, 60, 75}[tier]);
        Utils.defencePenetration0.put(this, new double[]{3, 4, 5, 6}[tier]);
        Utils.critRate.put(this, new double[]{0.2, 0.2, 0.2, 0.25}[tier]);
        Element.StoneElementValue.put(this, new double[]{0.2, 0.4, 0.6, 0.8}[tier]);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMine;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("钢屑").withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" -命中目标后，使周围单位").withStyle(ChatFormatting.WHITE));
        components.add(ComponentUtils.getAttackDamageDotDescription(2, 4, new String[]{"10%", "15%", "20%", "25%"}[tier]));
        components.add(ComponentUtils.getCritDamageInfluenceDescription());
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        Level level = player.level();
        List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 13, 13, 13));
        mobList.forEach(mob1 -> {
            if (mob1.distanceTo(mob) <= 4) {
                Dot.addDotOnMob(mob1, new Dot(1, PlayerAttributes.attackDamage(player) * new double[]{0.1, 0.15, 0.2, 0.25}[tier],
                        2, Tick.get() + 40, player.getName().getString(), true, null));
            }
        });
    }
}
