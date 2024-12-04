package fun.wraq.series.nether.equip.attack.sword;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.EnhanceNormalAttackModifier;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class NetherSword extends WraqSword implements ActiveItem, ForgeItem {

    public NetherSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 240d);
        Utils.defencePenetration0.put(this, 12d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 0.5);
        Element.FireElementValue.put(this, 1d);
        Utils.levelRequire.put(this, 80);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfNether;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionActive(components, Te.m("玄幔基岩", getMainStyle()));
        components.add(Te.s(" 下一次的", "普通近战攻击", CustomStyle.styleOfAttack, "的基础伤害提升", "100%", getMainStyle()));
        components.add(Te.s(" 并在命中后掉落一枚", "玄武岩", getMainStyle()));
        components.add(Te.s(" 玄武岩", getMainStyle(), "在1s后可以被拾取，拾取后", "重置本武器冷却时间", ChatFormatting.AQUA));
        ComponentUtils.coolDownTimeDescription(components, 10);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfNether();
    }

    @Override
    public void active(Player player) {
        Compute.playerItemCoolDown(player, this, 10);
        Compute.sendEffectLastTime(player, this, 0, true);
        EnhanceNormalAttackModifier.addModifier(player,
                new EnhanceNormalAttackModifier("Nether sword active", true, 1, 0,
                        (p, mob) -> {
                            Compute.removeEffectLastTime(p, this);
                            ItemAndRate.summonItemEntity(ModItems.BASALT_ROCK.get().getDefaultInstance(),
                                    mob.getEyePosition(), mob.level(), 20);
                            ParticleProvider.createBreakBlockParticle(mob, Blocks.BASALT);
                        }));
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.NetherRune.get(), 2),
                new ItemStack(ModItems.QuartzRune.get(), 1),
                new ItemStack(ModItems.GOLD_COIN.get(), 192),
                new ItemStack(ModItems.completeGem.get(), 8),
                new ItemStack(ModItems.ReputationMedal.get(), 8),
                new ItemStack(PickaxeItems.TINKER_GOLD.get(), 4)
        );
    }
}
