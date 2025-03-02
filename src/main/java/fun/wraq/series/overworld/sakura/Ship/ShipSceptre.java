package fun.wraq.series.overworld.sakura.Ship;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class ShipSceptre extends WraqSceptre implements InCuriosOrEquipSlotAttributesModify {

    public ShipSceptre(Properties p_42964_) {
        super(p_42964_.rarity(CustomStyle.ShipItalic));
        Utils.manaDamage.put(this, 900d);
        Utils.manaRecover.put(this, 20d);
        Utils.coolDownDecrease.put(this, 0.2);
        Utils.manaPenetration0.put(this, 22d);
        Element.WaterElementValue.put(this, 1d);
        Utils.levelRequire.put(this, 140);
    }

    @Override
    protected ManaArrow summonManaArrow(Player player, double rate) {
        Level level = player.level();
        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_WORLD.get(), player,
                level, rate, PlayerAttributes.manaPenetration(player),
                PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Sky);
        newArrow.setSilent(true);
        newArrow.setNoGravity(true);
        newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
        ProjectileUtil.rotateTowardsMovement(newArrow, 0);
        WraqSceptre.adjustOrb(newArrow, player);
        level.addFreshEntity(newArrow);
        MySound.soundToNearPlayer(player, ModSounds.Mana.get());
        return newArrow;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfShip;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("潮涌").withStyle(style));
        components.add(Component.literal(" 手持该武器时，会根据周围").withStyle(ChatFormatting.WHITE).
                append(Component.literal("水方块的数量").withStyle(style)).
                append(Component.literal("为你提供至多").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaPenetration("40")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfSakura();
    }

    public static final Map<Player, Integer> waterBlockCount = new WeakHashMap<>();

    @Override
    public void tick(Player player) {
        if (waterBlockCount.getOrDefault(player, 0) >= 10 && player.getMainHandItem().is(this)) {
            Compute.sendEffectLastTime(player, this, Math.min(40, waterBlockCount.getOrDefault(player, 0) / 10), true);
        }
        else {
            Compute.removeEffectLastTime(player, this);
        }
    }

    @Override
    public List<Attribute> getAttributes(Player player) {
        return List.of(new Attribute(Utils.manaPenetration0, Math.min(40, waterBlockCount.getOrDefault(player, 0) / 10d)));
    }
}
