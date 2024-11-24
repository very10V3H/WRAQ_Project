package fun.wraq.series.overworld.sun;

import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.projectiles.mana.swordair.SwordAir;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class BrokenBlade extends WraqCurios {

    private final int tier;
    public BrokenBlade(Properties properties, int tier) {
        super(properties);
        Utils.attackDamage.put(this, 70d);
        this.tier = tier;
    }

    private final double[] effectRate = new double[]{2, 2.5, 3, 3.5};

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("剑气", hoverMainStyle()));
        components.add(Te.s(" 你的", "居合", CustomStyle.styleOfSakura, "将释放一道", "剑气", hoverMainStyle()));
        components.add(Te.s(" 剑气", hoverMainStyle(), "会对沿途的敌人造成",
                String.format("%.0f%%", getEffectRate() * 100) + "物理伤害", CustomStyle.styleOfPower));
        components.add(ComponentUtils.getCritDamageInfluenceDescription());
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfGold;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSunIsland();
    }

    public double getEffectRate() {
        return effectRate[tier];
    }

    public static void onPlayerReleaseBlade(Player player) {
        ItemStack stack = isOnWithStack(BrokenBlade.class, player);
        if (!stack.is(Items.AIR)) {
            BrokenBlade brokenBlade = (BrokenBlade) stack.getItem();
            SwordAir swordAir = new SwordAir(ModEntityType.SWORD_AIR.get(), player, player.level()) {
                @Override
                public void onHitEntity(Mob mob) {
                    if (player != null && !level().isClientSide) {
                        Damage.causeAdDamageToMonsterWithCritJudge(player, mob,
                                PlayerAttributes.attackDamage(player) * brokenBlade.getEffectRate());
                    }
                    super.onHitEntity(mob);
                }

                @Override
                protected void onHitBlock(BlockHitResult hitResult) {
                    this.remove(RemovalReason.KILLED);
                    super.onHitBlock(hitResult);
                }

                @Override
                public void clientTick() {
                    ParticleOptions particleOptions = new DustParticleOptions(
                            Vec3.fromRGB24(CustomStyle.styleOfGold.getColor().getValue()).toVector3f(), 1);
                    this.level().addParticle(particleOptions,
                            this.getX() + (random.nextInt(3) - 1.5) * random.nextDouble(),
                            this.getY() + random.nextDouble(),
                            this.getZ() + (random.nextInt(3) - 1.5) * random.nextDouble(),
                            0, 0, 0);
                    super.clientTick();
                }
            };
            swordAir.setNoGravity(true);
            swordAir.shootFromRotation(3);
        }
    }
}
