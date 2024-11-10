package fun.wraq.series.nether.equip.attack.bow;

import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.impl.onshoot.OnShootArrowEquip;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class NetherKnife extends WraqOffHandItem implements OnShootArrowEquip, ForgeItem {

    public NetherKnife(Properties properties, Component type) {
        super(properties, type);
        Utils.attackDamage.put(this, 80d);
        Utils.critDamage.put(this, 0.15);
        Utils.expUp.put(this, 0.6);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfNether;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.m("朱雀吐息", getMainStyle()));
        components.add(Te.s(" 普通箭矢攻击", CustomStyle.styleOfFlexible, "15%掉落", "朱雀翎", getMainStyle()));
        components.add(Te.s(" 拾取", "朱雀翎", getMainStyle(), "将使下次命中目标的箭矢提升", "100%基础伤害", CustomStyle.styleOfPower));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfNether();
    }

    @Override
    public void onShoot(Player player) {
        if (RandomUtils.nextDouble(0, 1) <= 0.15) {
            Vec3 summonPos = player.pick(1, 0, false).getLocation();
            ItemAndRate.summonItemEntity(ModItems.PHOENIX_LEATHER.get().getDefaultInstance(),
                    summonPos, player.level());
            MySound.soundToPlayer(player, SoundEvents.BLAZE_SHOOT);
            ParticleProvider.createBallDisperseParticle(ParticleTypes.FLAME, (ServerLevel) player.level(), summonPos, 0.25, 8);
        }
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.NetherRune.get(), 2),
                new ItemStack(ModItems.QuartzRune.get(), 1),
                new ItemStack(ModItems.goldCoin.get(), 192),
                new ItemStack(ModItems.completeGem.get(), 8),
                new ItemStack(ModItems.ReputationMedal.get(), 8),
                new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4)
        );
    }
}
