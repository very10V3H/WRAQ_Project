package fun.wraq.series.overworld.chapter1.waterSystem.equip;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class LakeSceptre extends WraqSceptre implements OnHitEffectEquip {
    private final int num;

    public LakeSceptre(Properties properties, int num) {
        super(properties);
        this.num = num;
        Utils.manaDamage.put(this, new double[]{50, 60, 70, 80}[num]);
        Utils.manaRecover.put(this, new double[]{10, 11, 12, 13}[num]);
        Utils.manaPenetration0.put(this, new double[]{2, 2, 3, 3}[num]);
        Utils.coolDownDecrease.put(this, new double[]{0.2, 0.2, 0.2, 0.2}[num]);
        Element.WaterElementValue.put(this, new double[]{0.2, 0.4, 0.6, 0.8}[num]);
    }

    @Override
    protected AbstractArrow summonManaArrow(Player player, double rate) {
        Level level = player.level();
        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_WORLD.get(), player,
                level, PlayerAttributes.manaDamage(player) * rate,
                PlayerAttributes.manaPenetration(player),
                PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Sky);
        newArrow.setSilent(true);
        newArrow.setNoGravity(true);
        newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
        ProjectileUtil.rotateTowardsMovement(newArrow, 0);
        WraqSceptre.adjustOrb(newArrow, player);
        level.addFreshEntity(newArrow);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.DRIPPING_WATER);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.DRIPPING_WATER);
        MySound.soundToNearPlayer(player, ModSounds.Mana.get());
        return newArrow;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfWater;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("青碧无暇").withStyle(style));
        components.add(Component.literal(" 法球").withStyle(CustomStyle.styleOfMana).
                append(Component.literal("命中目标后，获得持续4s的:").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.releaseSpeed(String.valueOf(10 * (1 + num)))));
        components.add(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.movementSpeed((3 * (1 + num)) + "%")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        StableAttributesModifier.addAttributeModifier(player, StableAttributesModifier.playerCooldownModifier,
                new StableAttributesModifier("lakeSceptrePassiveCooldown", (this.num + 1) * 0.1, Tick.get() + 80));
        StableAttributesModifier.addAttributeModifier(player, StableAttributesModifier.playerMovementSpeedModifier,
                new StableAttributesModifier("lakeSceptrePassiveMovementSpeed", (this.num + 1) * 0.03, Tick.get() + 80));
        Compute.sendEffectLastTime(player, ModItems.lakeSceptre0.get(), 40);
    }
}
