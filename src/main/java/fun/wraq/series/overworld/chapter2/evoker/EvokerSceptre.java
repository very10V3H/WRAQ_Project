package fun.wraq.series.overworld.chapter2.evoker;

import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.projectiles.mana.NewArrow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class EvokerSceptre extends WraqSceptre {
    public EvokerSceptre(Properties p_42964_, int tier) {
        super(p_42964_);
        Utils.manaDamage.put(this, new double[]{200, 300, 400, 600}[tier]);
        Utils.manaRecover.put(this, new double[]{20, 25, 30, 40}[tier]);
        Utils.manaPenetration0.put(this, new double[]{4, 5, 6, 7}[tier]);
        Utils.manaCost.put(this, 60d);
        Utils.coolDownDecrease.put(this, new double[]{0.1, 0.2, 0.3, 0.4}[tier]);
        Element.LightningElementValue.put(this, new double[]{0.8, 0.9, 1, 1.2}[tier]);
    }

    public static final int ManaCost = 60;

    @Override
    protected AbstractArrow summonManaArrow(Player player, double rate) {
        Level level = player.level();
        NewArrow newArrow = new NewArrow(player, level, PlayerAttributes.manaDamage(player) * rate
                , PlayerAttributes.manaPenetration(player)
                , PlayerAttributes.expUp(player)
                , false
                , PlayerAttributes.manaPenetration0(player));
        newArrow.setSilent(true);
        newArrow.setNoGravity(true);
        newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
        ProjectileUtil.rotateTowardsMovement(newArrow, 0);
        WraqSceptre.adjustOrb(newArrow, player);
        level.addFreshEntity(newArrow);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.WITCH);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.WITCH);
        MySound.soundToNearPlayer(player, ModSounds.Mana.get());
        return newArrow;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMana;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }
}
