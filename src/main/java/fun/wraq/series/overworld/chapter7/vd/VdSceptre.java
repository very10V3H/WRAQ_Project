package fun.wraq.series.overworld.chapter7.vd;

import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter7.C7Items;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class VdSceptre extends WraqSceptre implements ForgeItem, ActiveItem, VdWeaponCommon {
    public VdSceptre(Properties properties) {
        super(properties);
        Utils.manaDamage.put(this, 3600d);
        Utils.manaRecover.put(this, 30d);
        Utils.manaPenetration0.put(this, 40d);
        Utils.coolDownDecrease.put(this, 0.35);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfWorld;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return VdWeaponCommon.additionalDescriptions(stack);
    }

    @Override
    public Component oneLineDescription() {
        return Component.literal("「至高科技结晶」").withStyle(getMainStyle()).withStyle(ChatFormatting.BOLD);
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix2Wraq();
    }

    @Override
    public void active(Player player) {
        VdWeaponCommon.active(player, this);
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.DevilSceptre.get(), 1));
            add(new ItemStack(C7Items.vdSoul.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 32));
            add(new ItemStack(ModItems.ReputationMedal.get(), 128));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 16));
            add(new ItemStack(ModItems.WorldSoul3.get(), 8));
        }};
    }

    @Override
    protected AbstractArrow summonManaArrow(Player player, double rate) {
        Level level = player.level();
        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_WORLD.get(), player, level,
                PlayerAttributes.manaDamage(player) * rate, PlayerAttributes.manaPenetration(player),
                PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Sky);
        newArrow.setSilent(true);
        newArrow.setNoGravity(true);
        newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
        ProjectileUtil.rotateTowardsMovement(newArrow, 0);
        WraqSceptre.adjustOrb(newArrow, player);
        level.addFreshEntity(newArrow);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ModParticles.WORLD.get());
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ModParticles.WORLD.get());
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 2, 0.25, 12, ModParticles.WORLD.get());
        MySound.soundToNearPlayer(player, ModSounds.Mana.get());
        return newArrow;
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void tick(Player player) {
        VdWeaponCommon.tick(player);
    }

}
