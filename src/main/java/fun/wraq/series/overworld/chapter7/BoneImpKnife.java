package fun.wraq.series.overworld.chapter7;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BoneImpKnife extends WraqOffHandItem implements ForgeItem {

    public BoneImpKnife(Properties properties, Component type) {
        super(properties, type);
        Utils.attackDamage.put(this, 277d);
        Utils.defencePenetration0.put(this, 6d);
        Utils.critRate.put(this, 0.17);
        Utils.critDamage.put(this, 0.1);
        Utils.expUp.put(this, 0.77);
        Utils.levelRequire.put(this, 210);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfWither;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("诡压").withStyle(style));
        components.add(Component.literal(" 于目标").withStyle(ChatFormatting.WHITE).
                append(Component.literal("上方").withStyle(style)).
                append(Component.literal("造成的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("普通攻击").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" 将").withStyle(ChatFormatting.WHITE).
                append(Component.literal("必定暴击").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("并造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%额外真实伤害").withStyle(CustomStyle.styleOfSea)));
        components.add(Component.literal(" 远程攻击").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal("造成的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("额外真实伤害").withStyle(CustomStyle.styleOfSea)).
                append(Component.literal("仅有60%的效能").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfBoneImp();
    }

    public static boolean passive(Player player, Mob mob) {
        return player.getOffhandItem().is(fun.wraq.series.overworld.chapter7.C7Items.BONE_IMP_KNIFE.get()) && player.position().y > mob.position().y;
    }

    public static double exTrueDamage(Player player, Mob mob) {
        if (passive(player, mob)) {
            Item mainHand = player.getMainHandItem().getItem();
            if (Utils.swordTag.containsKey(mainHand) || Utils.bowTag.containsKey(mainHand)) {
                ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0.5, 0.4, 8, ParticleTypes.LAVA, 0);
            }
            if (Utils.swordTag.containsKey(mainHand)) return 0.25;
            if (Utils.bowTag.containsKey(mainHand)) return 0.15;
        }
        return 0;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.MANA_KNIFE.get(), 1));
            add(new ItemStack(C7Items.BONE_IMP_SOUL.get(), 576));
            add(new ItemStack(ModItems.GOLD_COIN.get(), 288));
            add(new ItemStack(ModItems.COMPLETE_GEM.get(), 16));
            add(new ItemStack(ModItems.REPUTATION_MEDAL.get(), 64));
            add(new ItemStack(PickaxeItems.TINKER_GOLD.get(), 12));
            add(new ItemStack(ModItems.WORLD_SOUL_3.get(), 4));
        }};
    }
}
