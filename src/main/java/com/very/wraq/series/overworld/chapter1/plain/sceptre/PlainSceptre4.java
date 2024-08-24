package com.very.wraq.series.overworld.chapter1.plain.sceptre;

import com.very.wraq.common.MySound;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.ModEntityType;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.registry.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class PlainSceptre4 extends WraqSceptre {
    public PlainSceptre4(Properties p_42964_) {
        super(p_42964_);
        Utils.manaDamage.put(this, this.ManaDamage);
        Utils.manaRecover.put(this, this.ManaReply);
        Utils.manaPenetration0.put(this, this.ManaPenetration0);
        Utils.coolDownDecrease.put(this, 0.2);
        Utils.movementSpeedWithoutBattle.put(this, this.SpeedUp);
        Utils.manaCost.put(this, (double) ManaCost);
        Element.LifeElementValue.put(this, 1d);
    }

    private double ManaDamage = 60;
    private final double ManaPenetration0 = 200;
    private double ManaReply = 15;
    private double SpeedUp = 0.4F;
    public static final int ManaCost = 45;

    @Override
    public void shoot(Player player) {
        CompoundTag data = player.getPersistentData();
        Level level = player.level();
        if (Compute.ManaSkillLevelGet(data, 10) > 0 || Compute.playerManaCost(player, PlainSceptre4.ManaCost)) {
            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_PLAIN.get(),
                    player, level, PlayerAttributes.manaDamage(player),
                    PlayerAttributes.manaPenetration(player),
                    PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Plain);
            newArrow.setSilent(true);
            newArrow.setNoGravity(true);

            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
            WraqSceptre.adjustOrb(newArrow, player);
            level.addFreshEntity(newArrow);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.COMPOSTER);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.COMPOSTER);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 2, 0.25, 12, ParticleTypes.COMPOSTER);
            MySound.SoundToAll(player, ModSounds.Mana.get());
        }
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPlain;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("生机涌动").withStyle(CustomStyle.styleOfHealth));
        components.add(Component.literal("白天额外获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("45")).
                append(Component.literal("与")).
                append(Compute.AttributeDescription.ManaRecover("15")));
        PlainSceptre0.PlainSceptreDescription(components);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }
}
