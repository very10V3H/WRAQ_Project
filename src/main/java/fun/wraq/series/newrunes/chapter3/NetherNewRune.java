package fun.wraq.series.newrunes.chapter3;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.impl.display.UsageOrGetWayDescriptionItem;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import fun.wraq.series.newrunes.RuneItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class NetherNewRune extends WraqCurios implements RuneItem, UsageOrGetWayDescriptionItem {

    public NetherNewRune(Properties properties) {
        super(properties);
        Utils.critDamage.put(this, 0.1);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("熔岩爆裂").withStyle(style));
        components.add(Component.literal(" 击杀一名敌人时，会在其位置造成一次").withStyle(ChatFormatting.WHITE).
                append(Component.literal("小范围爆炸").withStyle(style)));
        components.add(Component.literal(" 爆炸").withStyle(style).
                append(Component.literal("将直接对范围内的目标造成所击杀敌人的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.maxHealth("8%")).
                append(Component.literal("物理伤害").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" 并且附带").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.attackDamageValue("100%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
        components.add(Component.literal(" 物理伤害仅会受护甲影响，不受任何伤害提升").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfNether;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfNether();
    }

    public static WeakHashMap<Mob, Boolean> trigMob = new WeakHashMap<>();

    public static void onKill(Player player, Mob mob) {
        if (!Compute.hasCurios(player, NewRuneItems.NETHER_NEW_RUNE.get())) return;
        if (trigMob.containsKey(mob)) return;
        trigMob.put(mob, true);
        List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 6, 6, 6));
        List<Player> players = mob.level().getEntitiesOfClass(Player.class, AABB.ofSize(mob.position(), 16, 16, 16));
        ParticleProvider.createSpaceRangeParticle((ServerLevel) player.level(), mob.position(), 3, 10, ParticleTypes.FLAME);

        players.forEach(player1 -> {
            MySound.soundToPlayer(player1, SoundEvents.BLAZE_SHOOT, mob.getEyePosition());
        });

        mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 3 || !mob1.isAlive());
        mobList.forEach(mob1 -> {
            Damage.causeAttackDamageToMonsterOnlyComputeDefence(player, mob1, mob.getMaxHealth() * 0.08);
            Damage.causeTrueDamageToMonster(player, mob1, PlayerAttributes.attackDamage(player));
        });
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal("下界怪物").withStyle(CustomStyle.styleOfNether)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
