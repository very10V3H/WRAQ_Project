package fun.wraq.series.overworld.chapter2.evoker;

import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class EvokerSceptre extends WraqSceptre {
    public EvokerSceptre(Properties p_42964_, int tier) {
        super(p_42964_);
        Utils.manaDamage.put(this, new double[]{220, 260, 300, 360}[tier]);
        Utils.manaRecover.put(this, new double[]{13, 14, 15, 16}[tier]);
        Utils.manaPenetration0.put(this, new double[]{4, 5, 6, 7}[tier]);
        Utils.coolDownDecrease.put(this, new double[]{0.2, 0.2, 0.2, 0.2}[tier]);
        Element.LightningElementValue.put(this, new double[]{0.8, 0.9, 1, 1.2}[tier]);
        Utils.levelRequire.put(this, 56);
    }

    public static final int ManaCost = 60;

    @Override
    protected ManaArrow summonManaArrow(Player player, double rate) {
        Level level = player.level();
        ManaArrow manaArrow = new ManaArrow(ModEntityType.NEW_ARROW.get(), player, level,
                rate, PlayerAttributes.manaPenetration(player),
                PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.EVOKER);
        manaArrow.setSilent(true);
        manaArrow.setNoGravity(true);
        manaArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
        ProjectileUtil.rotateTowardsMovement(manaArrow, 0);
        WraqSceptre.adjustOrb(manaArrow, player);
        level.addFreshEntity(manaArrow);
        MySound.soundToNearPlayer(player, ModSounds.Mana.get());
        return manaArrow;
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
