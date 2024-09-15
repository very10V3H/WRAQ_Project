package com.very.wraq.series.nether.Equip.MagmaSceptre;

import com.very.wraq.blocks.blocks.forge.ForgeRecipe;
import com.very.wraq.common.Compute;
import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.registry.ModSounds;
import com.very.wraq.common.registry.MySound;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.projectiles.mana.NewArrowMagma;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class MagmaSceptre extends WraqSceptre {
    private final int Num;

    public MagmaSceptre(Properties p_42964_, int Num) {
        super(p_42964_.rarity(CustomStyle.MagmaItalic));
        this.Num = Num;
        Utils.manaDamage.put(this, MagmaSceptreAttributes.ManaDamage[Num]);
        Utils.manaRecover.put(this, MagmaSceptreAttributes.ManaRecover[Num]);
        Utils.manaPenetration0.put(this, MagmaSceptreAttributes.ManaDefencePenetration0[Num]);
        Utils.movementSpeedWithoutBattle.put(this, MagmaSceptreAttributes.MovementSpeed[Num]);
        Utils.manaCost.put(this, MagmaSceptreAttributes.ManaCost[Num]);
        Element.FireElementValue.put(this, MagmaSceptreAttributes.FireElementValue[Num]);
        ForgeRecipe.forgeDrawRecipe.put(this, new ArrayList<>() {{
            add(new ItemStack(ModItems.MagmaRune.get(), 4));
            add(new ItemStack(ModItems.Ruby.get(), 128));
            add(new ItemStack(ModItems.NetherQuartz.get(), 32));
            add(new ItemStack(Items.RAW_GOLD, 32));
            add(new ItemStack(ModItems.goldCoin.get(), 64));
        }});
    }

    @Override
    protected AbstractArrow summonManaArrow(Player player, double rate) {
        Level level = player.level();
        NewArrowMagma newArrow = new NewArrowMagma(player, level, PlayerAttributes.manaDamage(player) * rate,
                PlayerAttributes.manaPenetration(player), PlayerAttributes.expUp(player), PlayerAttributes.manaPenetration0(player));
        newArrow.setSilent(true);
        newArrow.setNoGravity(true);
        newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
        ProjectileUtil.rotateTowardsMovement(newArrow, 0);
        WraqSceptre.adjustOrb(newArrow, player);
        level.addFreshEntity(newArrow);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ModParticles.LONG_VOLCANO.get());
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ModParticles.LONG_VOLCANO.get());
        MySound.SoundToAll(player, ModSounds.Mana.get());
        return newArrow;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPower;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("爆裂").withStyle(style));
        components.add(Component.literal("法球在命中目标时会施加:").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术-熔岩能量附着").withStyle(CustomStyle.styleOfMana)));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterIII();
    }
}
