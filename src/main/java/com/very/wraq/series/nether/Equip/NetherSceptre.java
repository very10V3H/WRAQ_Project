package com.very.wraq.series.nether.Equip;

import com.very.wraq.common.MySound;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.ModEntityType;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.registry.ModSounds;
import net.minecraft.ChatFormatting;
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

public class NetherSceptre extends WraqSceptre {
    private final int Num;

    public NetherSceptre(Properties p_42964_, int Num) {
        super(p_42964_.rarity(CustomStyle.NetherItalic));
        this.Num = Num;
        Utils.manaDamage.put(this, 1024d);
        Utils.manaRecover.put(this, 20d);
        Utils.coolDownDecrease.put(this, 0.35);
        Utils.manaPenetration0.put(this, 2400d);
        Utils.movementSpeedWithoutBattle.put(this, 0.5);
        Utils.manaCost.put(this, 45d);
        Element.FireElementValue.put(this, 1d);
    }

    @Override
    public void shoot(Player player) {
        double ManaCost = 45;
        CompoundTag data = player.getPersistentData();
        Level level = player.level();
        if (Compute.ManaSkillLevelGet(data, 10) > 0 || Compute.playerManaCost(player, (int) ManaCost)) {
            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_NETHER.get(),
                    player, level, PlayerAttributes.manaDamage(player),
                    PlayerAttributes.manaPenetration(player),
                    PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Entropy);
            newArrow.setSilent(true);
            newArrow.setNoGravity(true);

            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
            WraqSceptre.adjustOrb(newArrow, player);
            level.addFreshEntity(newArrow);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ModParticles.LONG_ENTROPY.get());
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ModParticles.LONG_ENTROPY.get());
            MySound.SoundToAll(player, ModSounds.Mana.get());
        }
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfNether;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("下界混沌解构术法").withStyle(CustomStyle.styleOfMana));
        components.add(Component.literal(" -你的普通法球攻击与法术攻击将基于目标的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDefence("")).
                append(Component.literal("提供70%的伤害提升").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" -当目标的魔法抗性达到500时给予满额伤害提升").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterIII();
    }
}
