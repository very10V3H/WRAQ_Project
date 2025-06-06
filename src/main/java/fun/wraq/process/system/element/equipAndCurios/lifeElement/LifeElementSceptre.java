package fun.wraq.process.system.element.equipAndCurios.lifeElement;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LifeElementSceptre extends WraqSceptre implements ActiveItem {

    public LifeElementSceptre(Properties p_42964_) {
        super(p_42964_);
        Utils.manaDamage.put(this, 1774d);
        Utils.manaRecover.put(this, 30d);
        Utils.manaPenetration0.put(this, 40d);
        Utils.coolDownDecrease.put(this, 0.2);
        Element.LifeElementValue.put(this, 2d);
    }

    public static void Tick(Player player) {
        if (fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword.lifeElementActiveLastTick.containsKey(player) && fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword.lifeElementActiveLastTick.get(player) >= Tick.get()) {
            int tickCount = fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword.lifeElementActiveLastTick.get(player) - Tick.get();
            Compute.sendEffectLastTime(player, ModItems.LIFE_ELEMENT_SWORD.get().getDefaultInstance(), tickCount, tickCount, true);
            Compute.playerHeal(player, fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword.lifeElementActiveHealth.get(player) * 0.01);
        }
    }

    public static void StoreToList(Player player, double num) {
        if (fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword.lifeElementActiveLastTick.containsKey(player) && fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword.lifeElementActiveLastTick.get(player) > Tick.get()) {
            if (!fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword.playerShortTimeStoreHealthMap.containsKey(player))
                fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword.playerShortTimeStoreHealthMap.put(player, new ArrayList<>());
            List<fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword.ShortTimeStoreHealth> list = fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword.playerShortTimeStoreHealthMap.get(player);
            list.removeIf(shortTimeStoreHealth -> shortTimeStoreHealth.tickCount() < Tick.get());
            list.add(new fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword.ShortTimeStoreHealth(Tick.get() + 100, num));
        }
    }

    public static double ComputeStoreNum(Player player) {
        if (!fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword.playerShortTimeStoreHealthMap.containsKey(player)) return 0;
        List<fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword.ShortTimeStoreHealth> list = fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword.playerShortTimeStoreHealthMap.get(player);
        double sum = 0;
        list.removeIf(shortTimeStoreHealth -> shortTimeStoreHealth.tickCount() < Tick.get());
        for (fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword.ShortTimeStoreHealth shortTimeStoreHealth : list) sum += shortTimeStoreHealth.num();
        return sum;
    }

    public static double ExManaDamage(Player player) {
        if (Utils.sceptreTag.containsKey(player.getMainHandItem().getItem())) return ComputeStoreNum(player) * 0.5;
        return 0;
    }

    @Override
    protected EntityType<ManaArrow> getArrowType() {
        return ModEntityType.NEW_ARROW_PLAIN.get();
    }

    @Override
    protected String getParticleType() {
        return StringUtils.ParticleTypes.LifeElement1TickParticle;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfLife;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionActive(components, Component.literal("化作春泥").withStyle(style));
        components.add(Component.literal(" 失去").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.health("80%当前")).
                append(Component.literal("，并在10s内为你回复").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.health("200%消耗的等额")));
        ComponentUtils.coolDownTimeDescription(components, 25);

        Compute.DescriptionPassive(components, Component.literal("护花").withStyle(style));
        components.add(Component.literal(" 根据5s内回复的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.health("")).
                append(Component.literal("，为你提供等同于回复量50%的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamage("")));
        components.add(Component.literal(" 多件生机武器的效果将不会叠加").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" 落红不是无情物，化作春泥更护花\uD83C\uDF37").withStyle(ChatFormatting.ITALIC).withStyle(style));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfElement();
    }

    @Override
    public void active(Player player) {
        if (Compute.PlayerUseWithHud(player, fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword.lifeElementActiveCoolDown, this, fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword.lifeElementActiveLastTick, 100, 0, 25)) {
            Compute.playerItemCoolDown(player, this, 25);
            LifeElementSword.lifeElementActiveHealth.put(player, player.getHealth() * 0.8);
            Compute.decreasePlayerHealth(player, player.getHealth() * 0.8, Component.literal(" 被生机元素吞噬了。").withStyle(CustomStyle.styleOfLife));
        }
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}
