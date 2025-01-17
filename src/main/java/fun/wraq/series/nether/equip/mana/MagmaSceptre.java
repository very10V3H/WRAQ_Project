package fun.wraq.series.nether.equip.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.projectiles.mana.ManaArrowHitEntity;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.nether.power.MagmaPower;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class MagmaSceptre extends WraqSceptre implements ForgeItem {

    public MagmaSceptre(Properties p_42964_, int tier) {
        super(p_42964_.rarity(CustomStyle.MagmaItalic));
        Utils.manaDamage.put(this, new double[]{160, 200, 240, 280}[tier]);
        Utils.manaRecover.put(this, new double[]{13, 14, 15, 16}[tier]);
        Utils.manaPenetration0.put(this, new double[]{4, 5, 5, 6}[tier]);
        Utils.manaCost.put(this, new double[]{150, 150, 150, 150}[tier]);
        Element.FireElementValue.put(this, new double[]{0.2, 0.4, 0.6, 0.8}[tier]);
        Utils.levelRequire.put(this, 80);
    }

    @Override
    protected ManaArrow summonManaArrow(Player player, double rate) {
        Level level = player.level();
        ManaArrow manaArrow = new ManaArrow(ModEntityType.NEW_ARROW_MAGMA.get(), player, level,
                PlayerAttributes.manaDamage(player) * rate, PlayerAttributes.manaPenetration(player),
                PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Entropy);
        manaArrow.manaArrowHitEntity = new ManaArrowHitEntity() {
            @Override
            public void onHit(ManaArrow manaArrow, Entity entity) {
                if (entity instanceof Mob mob) {
                    MagmaPower.onHit(player, mob, 3);
                }
            }
        };
        manaArrow.setSilent(true);
        manaArrow.setNoGravity(true);
        manaArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
        ProjectileUtil.rotateTowardsMovement(manaArrow, 0);
        WraqSceptre.adjustOrb(manaArrow, player);
        level.addFreshEntity(manaArrow);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ModParticles.LONG_VOLCANO.get());
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ModParticles.LONG_VOLCANO.get());
        MySound.soundToNearPlayer(player, ModSounds.Mana.get());
        return manaArrow;
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
        components.add(Component.literal(" 法球在命中目标时会施加:").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术-熔岩能量附着").withStyle(CustomStyle.styleOfPower)));
        components.add(Te.s(" 附加的法术数值为", "300%", CustomStyle.styleOfMana));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfNether();
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.MagmaRune.get(), 4),
                new ItemStack(ModItems.Ruby.get(), 128),
                new ItemStack(ModItems.NetherQuartz.get(), 32),
                new ItemStack(Items.RAW_GOLD, 32),
                new ItemStack(ModItems.GOLD_COIN.get(), 64)
        );
    }
}
